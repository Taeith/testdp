package fr.uge.poo.visitors.expr;

public class ToStringExprVisitor implements ExprVisitor<String> {

	@SuppressWarnings("unchecked")
	@Override
	public String visitValue(Value value) {
		return Integer.toString(value.getValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public String visitBinOp(BinOp binOp) {
		var stringBuilder = new StringBuilder();
		stringBuilder.append("(");
		stringBuilder.append(binOp.getLeft().accept(this));
		stringBuilder.append(" ");
		stringBuilder.append(binOp.getOpName());
		stringBuilder.append(" ");
		stringBuilder.append(binOp.getRight().accept(this));
		stringBuilder.append(")");
		return stringBuilder.toString();
	}

}
