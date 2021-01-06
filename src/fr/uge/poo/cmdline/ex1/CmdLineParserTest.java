package fr.uge.poo.cmdline.ex1;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

@SuppressWarnings("static-method")
class CmdLineParserTest {
	
	private static class PaintSettings {
    	
        private boolean legacy = false;
        private boolean bordered = true;

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

}