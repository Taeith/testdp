package fr.uge.poo.visitors.stp;

import com.evilcorp.stp.*;

public interface STPCommandVisitor {
	
	public void visit(HelloCmd helloCmd);
	public void visit(StopTimerCmd stopTimerCmd);
	public void visit(StartTimerCmd startTimerCmd);
	public void visit(ElapsedTimeCmd elapsedTimeCmd);
	public void visit(ExitCmd exitCmd);

}
