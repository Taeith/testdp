package fr.uge.poo.cmdline.ex5;

import java.util.ArrayList;
import java.util.HashSet;
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
		
		String name;
		int nbArguments;
		HashSet<String> aliases = new HashSet<>();
		Consumer<List<String>> consumer;
		List<String> conflicts = new ArrayList<>();
		boolean isRequired = false;
		String documentation = "";
		
		
		public OptionBuilder(String name, int nbArguments, Consumer<List<String>> consumer) {
			this.name = name;
			this.nbArguments = nbArguments;
			this.consumer = consumer;
		}
		
		public OptionBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public OptionBuilder setNbArguments(int nbArguments) {
	        this.nbArguments = nbArguments;
	        return this;
	    }
		
		public OptionBuilder setAliases(HashSet<String> aliases) {
	        this.aliases = aliases;
	        return this;
	    }
		
		public OptionBuilder setConsumer(Consumer<List<String>> consumer) {
			this.consumer = consumer;
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
		
		public void setDocumentation(String documentation) {
			this.documentation = documentation;
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

	public void setName(String name) {
		this.name = name;
	}

	public int getNbArguments() {
		return nbArguments;
	}

	public void setNbArguments(int nbArguments) {
		this.nbArguments = nbArguments;
	}

	public Consumer<List<String>> getConsumer() {
		return consumer;
	}

	public void setConsumer(Consumer<List<String>> consumer) {
		this.consumer = consumer;
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

	public HashSet<String> getAliases() {
		return aliases;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aliases == null) ? 0 : aliases.hashCode());
		result = prime * result + ((conflicts == null) ? 0 : conflicts.hashCode());
		result = prime * result + ((consumer == null) ? 0 : consumer.hashCode());
		result = prime * result + ((documentation == null) ? 0 : documentation.hashCode());
		result = prime * result + (isRequired ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + nbArguments;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Option other = (Option) obj;
		if (aliases == null) {
			if (other.aliases != null)
				return false;
		} else if (!aliases.equals(other.aliases))
			return false;
		if (conflicts == null) {
			if (other.conflicts != null)
				return false;
		} else if (!conflicts.equals(other.conflicts))
			return false;
		if (consumer == null) {
			if (other.consumer != null)
				return false;
		} else if (!consumer.equals(other.consumer))
			return false;
		if (documentation == null) {
			if (other.documentation != null)
				return false;
		} else if (!documentation.equals(other.documentation))
			return false;
		if (isRequired != other.isRequired)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nbArguments != other.nbArguments)
			return false;
		return true;
	}
	
	
	
}
