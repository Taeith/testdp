package fr.uge.poo.cmdline.ex6;

import static org.junit.Assert.assertThrows;

import fr.uge.poo.cmdline.ex6.Option.OptionBuilder;

import org.junit.jupiter.api.Test;

class CmdLineParserTest {
	
	@Test
	public void processRequiredOption() {
	    var cmdParser = new CmdLineParser();
	    cmdParser.addOption(new OptionBuilder("-test", 0, l -> {}).required().build());
	    cmdParser.addFlag("-test1", () -> {});
	    String[] arguments = {"-test1", "a", "b"};
	    assertThrows(ParseException.class, () -> cmdParser.process(arguments));
	}
		
	@Test
	public void processConflicts() {
	    var cmdParser = new CmdLineParser();
	    cmdParser.addOption(new OptionBuilder("-test", 0, l -> {}).conflictWith("-test1").build());
	    cmdParser.addOption(new OptionBuilder("-test1", 0, l -> {}).build());
	    String[] arguments = {"-test","-test1"};
	    assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
	}
	
	@Test
	public void processHelp() {
	    var cmdParser = new CmdLineParser();
	    OptionBuilder help = new OptionBuilder("-help", 0, l -> System.out.println(cmdParser.usage()));
	    help.setDocumentation("");
	    OptionBuilder container = new OptionBuilder("-container", 0, l -> {});
	    container.setDocumentation("Une longue explication sur les containers.");
	    OptionBuilder images = new OptionBuilder("-images", 0, l -> {});
	    images.setDocumentation("Une longue explication sur les images.");
	    cmdParser.addOption(help.build());
	    cmdParser.addOption(container.build());
	    cmdParser.addOption(images.build());
	    String[] arguments = {"-help"};
	    cmdParser.process(arguments);
	}
	
	@Test
	public void processConflicts2() {
	    var cmdParser = new CmdLineParser();
	    cmdParser.addOption(new OptionBuilder("-test",0, l->{}).conflictWith("-test1").build());
	    cmdParser.addOption(new OptionBuilder("-test1",0, l->{}).build());
	    String[] arguments = {"-test1","-test"};
	    assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
	}
	
	@Test
	public void processConflictsAndAliases() {
	    var cmdParser = new CmdLineParser();
	    var option= new OptionBuilder("-test",0, l->{}).conflictWith("-test2").build();
	    cmdParser.addOption(option);
	    var option2= new OptionBuilder("-test1",0, l->{}).alias("-test2").build();
	    cmdParser.addOption(option2);
	    String[] arguments = {"-test1","-test"};
	    assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
	}

	@Test
	public void processConflictsAndAliases2() {
	    var cmdParser = new CmdLineParser();
	    var option= new OptionBuilder("-test",0, l->{}).conflictWith("-test2").build();
	    cmdParser.addOption(option);
	    var option2= new OptionBuilder("-test1",0, l->{}).alias("-test2").build();
	    cmdParser.addOption(option2);
	    String[] arguments = {"-test","-test1"};
	    assertThrows(ParseException.class,()->{cmdParser.process(arguments);});
	}
	
    
}
