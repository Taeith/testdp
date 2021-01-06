package fr.uge.poo.visitors.expr2;

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
		return (T) visitor.visit(this);
	}

}
