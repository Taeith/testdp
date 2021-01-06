package fr.uge.poo.cmdline.ex6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

public class Option {
	
	String name;
	private int nbArguments;
	HashSet<String> aliases;
	private Consumer<List<String>> consumer;
	private List<String> conflicts;
	boolean isRequired;
	String documentation;
	
	public Option(String name, int nbArguments, HashSet<String> aliases, Consumer<List<String>> consumer, List<String> conflicts, boolean isRequired, String documentation) {
		this.name = name;
		this.nbArguments = nbArguments;
		this.consumer = consumer;
		this.aliases = aliases;
		this.conflicts = conflicts;
		this.isRequired = isRequired;
		this.documentation = documentation;
	}
	
	public static class OptionBuilder {
		
		private String name;
		private int nbArguments;
		private HashSet<String> aliases = new HashSet<>();
		private Consumer<List<String>> consumer;
		private List<String> conflicts = new ArrayList<>();
		private boolean isRequired = false;
		private String documentation = "";
		
		
		public OptionBuilder(String name, int nbArguments, Consumer<List<String>> consumer) {
			this.name = name;
			this.nbArguments = nbArguments;
			this.consumer = consumer;
		}
		
		public OptionBuilder setAliases(HashSet<String> aliases) {
	        this.aliases = aliases;
	        return this;
	    }
		
		public OptionBuilder required() {
			this.isRequired = true;
			return this;
		}
		
		public OptionBuilder conflictWith(String name) {
			conflicts.add(name);
			return this;
		}
		
		public OptionBuilder alias(String name) {
			aliases.add(name);
			return this;
		}
		
		public OptionBuilder setDocumentation(String documentation) {
			this.documentation = documentation;
			return this;
		}
		
		public Option build() {
			if (name == null || consumer == null) {
				throw new IllegalStateException();
			}
			return new Option(name, nbArguments, aliases, consumer, conflicts, isRequired, documentation);
		}
		
	}

	public String getName() {
		return name;
	}

	public int getNbArguments() {
		return nbArguments;
	}

	public Consumer<List<String>> getConsumer() {
		return consumer;
	}

	public List<String> getConflicts() {
		return conflicts;
	}
	
	public boolean isRequired() {
		return isRequired;
	}
	
	public String getDocumentation() {
		return documentation;
	}
	
}
