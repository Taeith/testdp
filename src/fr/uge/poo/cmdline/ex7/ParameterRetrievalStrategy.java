package fr.uge.poo.cmdline.ex7;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import fr.uge.poo.cmdline.ex7.CmdLineParser.OptionsManager;

@FunctionalInterface
interface TriFunction<A,B,C,R> {

    R apply(A a, B b, C c);

    default <V> TriFunction<A, B, C, V> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (A a, B b, C c) -> after.apply(apply(a, b, c));
    }
    
}

public enum ParameterRetrievalStrategy {

	STANDARD((iterator, option, optionManager) -> {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < option.getNbArguments(); i++) {
			String parameter = iterator.next();
			if (parameter.startsWith("-")) {
				throw new ParseException();
			}
			list.add(parameter);
		}
		return list;
	}), 
	RELAXED((iterator, option, optionManager) -> {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < option.getNbArguments(); i++) {
			String parameter = iterator.next();
			if (parameter.startsWith("-")) {
				break;
			}
			list.add(parameter);
		}
		return list;
	}), 
	OLDSCHOOL((iterator, option, optionManager) -> {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < option.getNbArguments(); i++) {
			String parameter = iterator.next();
			list.add(parameter);
		}
		return list;
	}), 
	SMARTRELAXED((iterator, option, optionManager) -> {
		List<String> list = new ArrayList<>();
		for (int i = 0; i < option.getNbArguments(); i++) {
			String parameter = iterator.next();
			if (!optionManager.processOption(parameter).isEmpty()) {
				break;
			}
			list.add(parameter);
		}
		return list;
	});

	private final TriFunction<Iterator<String>, Option, OptionsManager, List<String>> strategy;

	private ParameterRetrievalStrategy(TriFunction<Iterator<String>, Option, OptionsManager, List<String>> strategy) {
		this.strategy = strategy;
	}

	public TriFunction<Iterator<String>, Option, OptionsManager, List<String>> getStrategy() {
		return strategy;
	}

}
