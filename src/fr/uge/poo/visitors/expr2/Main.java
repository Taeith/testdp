package fr.uge.poo.visitors.expr2;

import java.util.Iterator;
import java.util.regex.Pattern;

public class Main {

	public static void main(String[] args) {

		Iterator<String> it = Pattern.compile(" ").splitAsStream("+ * 4 + 1 1 + 2 3").iterator();
		Expr expr = Expr.parseExpr(it);

		ExprVisitor<Integer> evalVisitor = new ExprVisitor<>();
		evalVisitor.when(Value.class, value -> {
			var v = (Value) value;
			return v.getValue();
		}).when(BinOp.class, binOp -> {
			var b = (BinOp) binOp;
			return b.getOperator().applyAsInt(((Integer) evalVisitor.visit(b.getLeft())).intValue(),
					((Integer) evalVisitor.visit(b.getRight())).intValue());
		});
		
		ExprVisitor<String> toStringVisitor = new ExprVisitor<>();
		toStringVisitor.when(Value.class, value -> {
			var v = (Value) value;
			return Integer.toString(v.getValue());
		}).when(BinOp.class, binOp -> {
			var b = (BinOp) binOp;
			var stringBuilder = new StringBuilder();
			stringBuilder.append("(");
			stringBuilder.append(toStringVisitor.visit(b.getLeft()));
			stringBuilder.append(" ");
			stringBuilder.append(b.getOpName());
			stringBuilder.append(" ");
			stringBuilder.append(toStringVisitor.visit(b.getRight()));
			stringBuilder.append(")");
			return stringBuilder.toString();
		});
		
		System.out.println(toStringVisitor.visit(expr));
		System.out.println(evalVisitor.visit(expr));

	}

}