package com.evilcorp.stp;

import fr.uge.poo.visitors.stp.STPCommand;
import fr.uge.poo.visitors.stp.STPCommandVisitor;

public class StartTimerCmd implements STPCommand {

    private int timerId;

    public StartTimerCmd(int timerId){
        this.timerId=timerId;
    }

    public int getTimerId() {
        return timerId;
    }
    
    @Override
	public void accept(STPCommandVisitor visitor) {
		visitor.visit(this);
	}
        
}
