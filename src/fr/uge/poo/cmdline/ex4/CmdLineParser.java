package fr.uge.poo.cmdline.ex4;

import java.util.*;
import java.util.stream.Collectors;

import fr.uge.poo.cmdline.ex4.Option.OptionBuilder;

public class CmdLineParser {

    private final HashMap<String, Option> registeredOptions = new HashMap<>();
    
    private boolean isOption(String string) {
    	return string.startsWith("-");
    }
    
    public void addOption(Option option) {
    	if (registeredOptions.containsKey(option.getName())) {
            throw new IllegalStateException("Option " + option.getName() + " is already registered.");
        }
    	registeredOptions.put(option.getName(), option);
    }
    
    public void addFlag(String name, Runnable runnable) {
    	addOption(new OptionBuilder(name, 0, __ -> runnable.run()).build());
    }
        
    public List<String> process(String[] arguments) {
    	List<Option> mandatoryOptions = registeredOptions
    			.values().stream().filter(o -> o.isRequired()).collect(Collectors.toList());
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
    		mandatoryOptions.remove(option);
			for (int i = 0; i < option.getNbArguments(); i++) {
				String parameter = iterator.next();
				if (isOption(parameter)) {
					throw new ParseException();
				}
				list.add(parameter);   				
			}
			option.getConsumer().accept(list);
    	}
    	if (!mandatoryOptions.isEmpty()) {
    		throw new ParseException(); 
    	}
        return files;
    }
    
}