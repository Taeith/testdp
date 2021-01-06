package fr.uge.poo.visitors.stp;

public interface STPCommand {

	void accept(STPCommandVisitor visitor);
	
}
