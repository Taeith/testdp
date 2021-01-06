package fr.uge.poo.cmdline.ex2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class CmdLineParserTest {
	
private static class PaintSettings {
    	
        private boolean legacy = false;
        private boolean bordered = true;
        private int borderWidth = 0;

        public void setLegacy(boolean legacy) {
            this.legacy = legacy;
        }

        public boolean isLegacy(){
            return legacy;
        }

        public void setBordered(boolean bordered){
            this.bordered=bordered;
        }

        public boolean isBordered(){
            return bordered;
        }
        
        public void setBorderWidth(int borderWidth) {
        	this.borderWidth = borderWidth;
        }
        
        public int getBorderWidth() {
        	return borderWidth;
        }

        @Override
        public String toString(){
            return "PaintSettings [ bordered = "+bordered+", legacy = "+ legacy +" ]";
        }
    }

    @Test
    public void processShouldFailFastOnNullArgument() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.process(null));
    }
    
    @Test
    public void processShouldUpdateFlag() {
    	var options = new PaintSettings();
    	String[] arguments = {"-with-borders"};
    	 var cmdParser = new CmdLineParser();
         cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
         cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
         cmdParser.registerOption("-no-borders",() -> options.setBordered(false));
    	 cmdParser.process(arguments);
        assertFalse(options.isLegacy());
        assertTrue(options.isBordered());        
    }
    
    @Test
    public void processShouldUpdateWithOneParameter() {
    	var options = new PaintSettings();
    	String[] arguments = {"-border-width", "10"};
    	 var cmdParser = new CmdLineParser();
         cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
         cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
         cmdParser.registerOption("-no-borders",() -> options.setBordered(false));
         cmdParser.registerWithParameter("-border-width", iterator -> options.setBorderWidth(Integer.parseInt(iterator.next())));
    	 cmdParser.process(arguments);
        assertEquals(10, options.getBorderWidth());        
    }
    
    @Test
    public void processFailWithoutParameter() {
    	var options = new PaintSettings();
    	String[] arguments = {"-border-width"};
    	 var cmdParser = new CmdLineParser();
         cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
         cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
         cmdParser.registerOption("-no-borders",() -> options.setBordered(false));
         cmdParser.registerWithParameter("-border-width", iterator -> options.setBorderWidth(Integer.parseInt(iterator.next())));
    	 assertThrows(ParseException.class, () -> cmdParser.process(arguments));
    }
    
    @Test
    public void processShouldFailWhenRegisterSameParameter() {
    	var options = new PaintSettings();
    	 var cmdParser = new CmdLineParser();
         cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
         assertThrows(IllegalStateException.class, 
        		 () -> cmdParser.registerOption("-legacy", () -> options.setLegacy(true)));
    }

}
