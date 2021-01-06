package fr.uge.poo.cmdline.ex5;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import fr.uge.poo.cmdline.ex5.Option.OptionBuilder;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class CmdLineParserTest {
	
	private boolean legacy = false;
    private boolean bordered = false;
    private int borderWidth = 0;
    private String borderName = "";
    private int x = 0;
    private int y = 0;

    @Test
    public void processShouldFailFastOnNullArgument() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.process(null));
    }
    
    @Test
    public void processShouldRunRunnable() {
    	String[] arguments = {"-legacy"};
        var cmdParser = new CmdLineParser();
        var list = new ArrayList<Integer>();
        cmdParser.addOption(new OptionBuilder("-legacy", 0, __ -> list.add(1)).build());
        cmdParser.process(arguments);
        assertFalse(list.isEmpty());
    }
    
    @Test
    public void processShouldUpdateOptions() {
    	String[] arguments = {"-with-borders"};
    	CmdLineParser cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-with-borders", 0, __ -> bordered = true).build());
        cmdParser.process(arguments);
        assertFalse(legacy);
        assertTrue(bordered);
    }
    
    @Test
    public void processShouldUpdateOptionsWithOneParameter() {
    	// create builder
    	String[] arguments = {"-legacy","-border-width","500","-window-name","minecraft"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-legacy", 0, __ -> this.legacy = true).build());
        cmdParser.addOption(new OptionBuilder("-border-width",  1, list -> this.borderWidth = Integer.parseInt(list.get(0))).build());
        cmdParser.addOption(new OptionBuilder("-window-name", 1, list -> this.borderName = String.valueOf(list.get(0))).build());
        cmdParser.process(arguments);
        assertEquals(500, borderWidth);
        assertEquals("minecraft", borderName);
    }
    
    @Test
    public void processShouldUpdateOptionsWithParameters() {
    	String[] arguments = {"-legacy", "-position", "69", "420"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-legacy", 0, __ -> this.legacy = true).build());
        cmdParser.addOption(new OptionBuilder("-position", 2, list -> {
        	x = Integer.parseInt(list.get(0));
        	y = Integer.parseInt(list.get(1));
        }).build());
        cmdParser.process(arguments);
        assertEquals(69, x);
        assertEquals(420, y);
    }
    
    @Test
    public void processShouldNotWorkWithParameters() {
    	String[] arguments = {"-window-name", "-position", "69", "420"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-window-name", 1, list -> this.borderName = String.valueOf(list.get(0))).build());
        cmdParser.addOption(new OptionBuilder("-position", 2, list -> {
        	x = Integer.parseInt(list.get(0));
        	y = Integer.parseInt(list.get(1));
        }).build());
        assertThrows(ParseException.class, () ->  cmdParser.process(arguments));
    }
    
    @Test
    public void mustCheckRequiredOptions() {
    	String[] arguments = {"-position", "69", "420"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-legacy", 0, __ -> this.legacy = true).required().build());
        cmdParser.addOption(new OptionBuilder("-position", 2, list -> {
        	x = Integer.parseInt(list.get(0));
        	y = Integer.parseInt(list.get(1));
        }).build());
        assertThrows(ParseException.class, () ->  cmdParser.process(arguments));
    }
    
    @Test
    public void mustWorkWithAliases() {
    	String[] arguments = {"-ycagel"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-legacy", 0, __ -> this.legacy = true).required().alias("-ycagel").build());
        cmdParser.process(arguments);
        assertTrue(legacy);
    }
    
    @Test
    public void mustWorkWithAliases2() {
    	String[] arguments = {""};
        var cmdParser = new CmdLineParser();
        cmdParser.addOption(new OptionBuilder("-legacy", 0, __ -> this.legacy = true).required().alias("-ycagel").build());
        assertThrows(ParseException.class, () ->  cmdParser.process(arguments));
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
    
}
