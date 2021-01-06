package com.evilcorp.stp;

import fr.uge.poo.visitors.stp.STPCommand;
import fr.uge.poo.visitors.stp.STPCommandVisitor;

public class ExitCmd implements STPCommand {

	@Override
	public void accept(STPCommandVisitor visitor) {
		visitor.visit(this);
	}

}
