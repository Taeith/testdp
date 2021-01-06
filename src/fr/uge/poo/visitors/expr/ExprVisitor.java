package fr.uge.poo.visitors.expr;

public interface ExprVisitor<T> {
	
    public <T> T visitValue(Value value);
    public <T> T visitBinOp(BinOp binOp);
    
}