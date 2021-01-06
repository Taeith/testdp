package fr.uge.poo.cmdline.ex4;

import java.util.List;
import java.util.function.Consumer;

public class Option {
	
	String name;
	private int nbArguments;
	private Consumer<List<String>> consumer;
	boolean isRequired;
	
	public Option(String name, int nbArguments, Consumer<List<String>> consumer, boolean isRequired) {
		this.name = name;
		this.nbArguments = nbArguments;
		this.consumer = consumer;
		this.isRequired = isRequired;
	}
	
	public static class OptionBuilder {
		
		String name;
		int nbArguments;
		Consumer<List<String>> consumer;
		boolean isRequired = false;
		
		
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
		
		public OptionBuilder setConsumer(Consumer<List<String>> consumer) {
			this.consumer = consumer;
			return this;
		}
		
		public OptionBuilder required() {
			this.isRequired = true;
			return this;
		}
		
		public Option build() {
			if (name == null || consumer == null) {
				throw new IllegalStateException();
			}
			return new Option(name, nbArguments, consumer, isRequired);
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
	
	public boolean isRequired() {
		return isRequired;
	}
	
}
