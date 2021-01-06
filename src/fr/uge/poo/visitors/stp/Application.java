package fr.uge.poo.visitors.stp;

import com.evilcorp.stp.*;

import java.util.Optional;
import java.util.Scanner;

public class Application {

	private static STPCommandVisitor visitor = new ComplexSTPCommandVisitor();
	
    public static void main(String[] args) {
        var scan = new Scanner(System.in);
        while(scan.hasNextLine()) {
            var line = scan.nextLine();
            if (line.equals("quit")) {
                break;
            }
            Optional<STPCommand> answer = STPParser.parse(line);
            if (!answer.isPresent()) {
                System.out.println("Unrecognized command");
                continue;
            }
            answer.get().accept(visitor);
        }
        scan.close();
    }
}
