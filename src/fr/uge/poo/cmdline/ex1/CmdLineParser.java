package fr.uge.poo.cmdline.ex1;

import java.util.*;

public class CmdLineParser {

    private final HashMap<String, Runnable> registeredOptions = new HashMap<>();

    public void registerOption(String option, Runnable runnable) {
        registeredOptions.put(option, runnable);
    }

    public List<String> process(String[] arguments) {
        ArrayList<String> files = new ArrayList<>();
        var keys = registeredOptions.keySet();
        for (String argument : arguments) {
            if (keys.contains(argument)) {
            	registeredOptions.get(argument).run();
            } else {
                files.add(argument);
            }
        }        
        return files;
    }
}