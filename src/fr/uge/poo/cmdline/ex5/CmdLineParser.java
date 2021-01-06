package fr.uge.poo.cmdline.ex5;

import java.util.*;
import java.util.stream.Collectors;

import fr.uge.poo.cmdline.ex5.Option.OptionBuilder;

public class CmdLineParser {

    private final HashMap<String, Option> registeredOptions = new HashMap<>();
    
    public String usage() {
    	var builder = new StringBuilder();
    	for (var option : registeredOptions.values().stream().sorted((a, b) -> a.getName().compareTo(b.getName())).collect(Collectors.toList())) {
    		builder.append(option.getName())
    			.append(": ")
    			.append(option.getDocumentation())
    			.append("\n");
    	}
    	return builder.toString();
    }
    
    private boolean isOption(String string) {
    	return string.startsWith("-");
    }
    
    public void addOption(Option option) {
    	if (registeredOptions.containsKey(option.getName())) {
            throw new IllegalStateException("Option " + option.getName() + " is already registered.");
        }
    	registeredOptions.put(option.getName(), option);
    	for (var alias : option.aliases) {
    		registeredOptions.put(alias, option);
        }
    }
    
    public void addFlag(String name, Runnable runnable) {
    	addOption(new OptionBuilder(name, 0, __ -> runnable.run()).build());
    }
        
    public List<String> process(String[] arguments) {
    	List<Option> options = new ArrayList<Option>();
    	ArrayList<String> files = new ArrayList<>();
    	Iterator<String> iterator = Arrays.asList(arguments).iterator();
    	while (iterator.hasNext()) {
    		String argument = iterator.next();
    		if (!isOption(argument)) {    			
    			throw new ParseException();
    		}
    		List<String> list = new ArrayList<>();
    		if (!registeredOptions.containsKey(argument)) {
    			throw new ParseException();
    		}
    		Option option = registeredOptions.get(argument);
    		options.add(option);    		
			for (int i = 0; i < option.getNbArguments(); i++) {
				String parameter = iterator.next();
				if (isOption(parameter)) {
					throw new ParseException();
				}
				list.add(parameter);   				
			}
			option.getConsumer().accept(list);
    	}
    	// required elements
    	List<Option> mandatoryOptions = registeredOptions
    			.values().stream().filter(o -> o.isRequired()).collect(Collectors.toList());
    	boolean temp = true;
    	for (var o : mandatoryOptions) {
    		if (!options.contains(o)) {
    			boolean temp2 = false;
    			for (var alias : o.aliases) {
    				if (options.contains(alias)) {
    					temp2 = true;
    				}
    			}
    			if (temp2 == false) {
    				temp = false;
    			}
    		}
    	}
    	if (!temp) {
    		throw new ParseException(); 
    	}
        return files;
    }
    
}