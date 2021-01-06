package fr.uge.poo.visitors.expr2;

import java.util.HashMap;
import java.util.function.Function;

public class ExprVisitor<T> {
	
	private HashMap<Class<?>, Function<Expr, T>> map = new HashMap<>();

	public T visit(Expr e) {
		return map.get(e.getClass()).apply(e);
	}

	public ExprVisitor<T> when(Class<?> c, Function<Expr, T> f) {
		map.put(c, f);
		return this;
	}
        
}