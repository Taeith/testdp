package fr.uge.poo.cmdline.ex7;

import java.util.*;
import fr.uge.poo.cmdline.ex7.Option.OptionBuilder;

public class CmdLineParser {
	
	private final OptionsManager optionsManager = new OptionsManager();
	private final DocumentationObserver documentationObserver = new DocumentationObserver();
	
	public CmdLineParser() {
		optionsManager.register(new MandatoryOptionsObserver());
		optionsManager.register(new ConflictObserver());
	}
	
	public String usage() {
		return documentationObserver.usage();
	}
	
	interface OptionsObserver {

	    void onRegisteredOption(OptionsManager optionsManager,Option option);

	    void onProcessedOption(OptionsManager optionsManager,Option option);
	    
	    void onFinishedProcess(OptionsManager optionsManager);
	    
	}
	
	class ConflictObserver implements OptionsObserver {
		
		private final HashMap<String, Option> byName = new HashMap<>();

	    @Override
	    public void onRegisteredOption(OptionsManager optionsManager, Option option) {
	        // do nothing
	    }

	    @Override
	    public void onProcessedOption(OptionsManager optionsManager, Option option) {
	    	byName.put(option.getName(), option);
	    	for (String alias : option.getAliases()) {
	    		byName.put(alias, option);
	        }
	    }

	    @Override
	    public void onFinishedProcess(OptionsManager optionsManager) {
	    	for (Option option : byName.values()) {
	    		for (String conflict : option.getConflicts()) {
	    			if (byName.containsKey(conflict)) {
	    				throw new ParseException();
	    			}
	    		}
	    	}
	    }
	}	
	
	class MandatoryOptionsObserver implements OptionsObserver {
		
		private List<Option> requiredOptions = new ArrayList<Option>();

	    @Override
	    public void onRegisteredOption(OptionsManager optionsManager, Option option) {
	    	if (option.isRequired()) {
	        	requiredOptions.add(option);
	        }
	    }

	    @Override
	    public void onProcessedOption(OptionsManager optionsManager, Option option) {
	        if (requiredOptions.contains(option)) {
	        	requiredOptions.remove(option);
	        }
	    }

	    @Override
	    public void onFinishedProcess(OptionsManager optionsManager) {
	    	if (!requiredOptions.isEmpty()) {
	    		throw new ParseException();
	    	}
	    }
	    
	}
	
	class DocumentationObserver implements OptionsObserver {
		
		private final StringBuilder builder = new StringBuilder();
		
		public String usage() {
			return builder.toString();
		}

	    @Override
	    public void onRegisteredOption(OptionsManager optionsManager, Option option) {
	    	if (option.getDocumentation() != "") {
	    		builder.append(option.getName())
	    			.append(": ")
	    			.append(option.getDocumentation())
	    			.append("\n");
	    	}
	    }

	    @Override
	    public void onProcessedOption(OptionsManager optionsManager, Option option) {
	        // do nothing
	    }

	    @Override
	    public void onFinishedProcess(OptionsManager optionsManager) {
	    	// do nothing
	    }
	    
	}	
	
	class OptionsManager {

	    private final HashMap<String, Option> byName = new HashMap<>();
	    private final List<OptionsObserver> observers = new ArrayList<>();
	    
	    public void register(Option option) {
	        register(option.getName(), option);
	        for (var alias : option.getAliases()) {
	            register(alias, option);
	        }
	    }
	    
	    public void register(OptionsObserver observer){
	        observers.add(observer);
	    }
	    
	    public void notifyRegisteredOption(Option option) {
	    	observers.forEach(observer -> observer.onRegisteredOption(this, option));
	    	documentationObserver.onRegisteredOption(this, option);
	    }
	    
	    public void notifyProcessedOption(Option option) {
	    	observers.forEach(observer -> observer.onProcessedOption(this, option));
	    	documentationObserver.onProcessedOption(this, option);
	    }
	    
	    public void notifyFinishedProcess() throws ParseException {
	    	observers.forEach(observer -> observer.onFinishedProcess(this));
	    	documentationObserver.onFinishedProcess(this);
	    }
	    
	    private void register(String name, Option option) {
	        if (byName.containsKey(name)) {
	            throw new IllegalStateException("Option " + name + " is already registered.");
	        }
	        byName.put(name, option);
	        notifyRegisteredOption(option);
	    }
	    
	    public Optional<Option> processOption(String optionName) {
	        Optional<Option> optional = Optional.ofNullable(byName.get(optionName));
	        if (optional.isPresent()) {
	        	notifyProcessedOption(optional.get());
	        }
	        return optional;
	    }

	    public void finishProcess() {
	    	notifyFinishedProcess();
	    }
	    
	    public HashMap<String, Option> getByName() {
			return byName;
	    }
	    
	}
	
	public boolean isOption(String argument) {
		return argument.startsWith("-");
	}
    
    public void addOption(Option option) {
    	optionsManager.register(option);
    }
    
    public void addFlag(String name, Runnable runnable) {
    	addOption(new OptionBuilder(name, 0, __ -> runnable.run()).build());
    }
    
    public List<String> process(String[] arguments, ParameterRetrievalStrategy strategy) throws ParseException {
    	ArrayList<String> files = new ArrayList<>();
    	Iterator<String> iterator = Arrays.asList(arguments).iterator();
    	while (iterator.hasNext()) {
    		String argument = iterator.next();
    		if (!isOption(argument)) {    			
    			files.add(argument);
    			continue;
    		}
    		Option option = optionsManager.processOption(argument).orElseThrow(() -> new ParseException());
    		option.getConsumer().accept(strategy.getStrategy().apply(iterator, option, optionsManager));			
    	}
    	optionsManager.finishProcess();
        return files;
    }

    
}