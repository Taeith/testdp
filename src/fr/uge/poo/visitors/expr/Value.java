package fr.uge.poo.visitors.expr;

public class Value implements Expr {
	
    private final int value;

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    
	@Override
	public <T> T accept(ExprVisitor<T> visitor) {
		return visitor.visitValue(this);
	}

}
