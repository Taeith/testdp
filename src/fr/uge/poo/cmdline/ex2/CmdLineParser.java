package fr.uge.poo.cmdline.ex2;

import java.util.*;
import java.util.function.Consumer;

public class CmdLineParser {

    private final HashMap<String, Consumer<Iterator<String>>> registeredOptions = new HashMap<>();
    
    public void registerOption(String option, Runnable runnable) {
    	registerWithParameter(option, __ -> runnable.run());
    }
    
    public void registerWithParameter(String option, Consumer<Iterator<String>> consumer) {
    	Objects.requireNonNull(option);
    	if (registeredOptions.containsKey(option)) {
    		throw new IllegalStateException();
    	}
    	registeredOptions.put(option, consumer);
    }

    public List<String> process(String[] arguments) {
    	Iterator<String> iterator = Arrays.stream(arguments).iterator();
        ArrayList<String> files = new ArrayList<>();
        var keys = registeredOptions.keySet();
        while (iterator.hasNext()) {
        	var argument = iterator.next();
        	if (keys.contains(argument)) {
            	try {
            		registeredOptions.get(argument).accept(iterator);
            	} catch (NumberFormatException | NoSuchElementException exception) {
            		throw new ParseException();
            	}
            } else {
                files.add(argument);
            }
        }
        return files;
    }
}