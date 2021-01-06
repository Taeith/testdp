package fr.uge.poo.visitors.expr;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Main {
	
	private static EvalExprVisitor evalVisitor = new EvalExprVisitor();
	private static ToStringExprVisitor toStringVisitor = new ToStringExprVisitor();

	public static void main(String[] args) {
		Iterator<String> it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
		Expr expr = Expr.parseExpr(it);
		System.out.println(expr.accept(toStringVisitor));		
		System.out.println(expr.accept(evalVisitor));
	}

}