package fr.uge.poo.visitors.expr;

public class EvalExprVisitor implements ExprVisitor<Integer> {

	@Override
	public Integer visitValue(Value value) {
		return value.getValue();
	}

	@Override
	public Integer visitBinOp(BinOp binOp) {
		return binOp.getOperator().applyAsInt(((Integer) binOp.getLeft().accept(this)).intValue(), ((Integer) binOp.getRight().accept(this)).intValue());
	}

}
