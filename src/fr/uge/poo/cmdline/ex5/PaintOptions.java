package fr.uge.poo.cmdline.ex5;

public class PaintOptions {
	
	private String windowName;
	private boolean legacy = false;
	private boolean bordered = false;
	private int borderWidth = 0;		
	private int minWidth = 500;
	private int minHeight = 500;
	private String remoteServerHost;
	private int remoteServerPort;
	
	private class PaintBuilder {
		
		private String windowName;
		private boolean legacy = false;
		private boolean bordered = false;
		private int borderWidth = 0;		
		private int minWidth = 500;
		private int minHeight = 500;
		private String remoteServerHost;
		private int remoteServerPort;
		
		public PaintBuilder setWindowName(String windowName) {
			this.windowName = windowName;
			return this;
		}
		
		public PaintBuilder setLegacy(boolean legacy) {
	        this.legacy = legacy;
	        return this;
	    }
		
		public PaintBuilder setBordered(boolean bordered) {
	        this.bordered = bordered;
	        return this;
	    }
		
		public PaintBuilder setBorderWidth(int borderWidth) {
			this.borderWidth = borderWidth;
			return this;
		}
		
		public PaintBuilder setMinSize(int minWidth, int minHeight) {
			this.minWidth = minWidth;
			this.minHeight = minHeight;
			return this;
		}
		
		public PaintBuilder setRemoteServer(String remoteServerHost, int remoteServerPort) {
			this.remoteServerHost = remoteServerHost;
			this.remoteServerPort = remoteServerPort;
			return this;
		}
		
		public PaintOptions build() {
			if (windowName == null) {
				throw new IllegalStateException();
			}
			return new PaintOptions(windowName, legacy, bordered, borderWidth, minWidth, minHeight, remoteServerHost, remoteServerPort);
		}
		
	}
	
	public PaintOptions(String windowName, boolean legacy, boolean bordered, int borderWidth, int minWidth, int minHeight, String remoteServerHost, int remoteServerPort) {
		this.windowName = windowName;
		this.bordered = bordered;
		this.borderWidth = borderWidth;
		this.minWidth = minWidth;
		this.minHeight = minHeight;
		this.remoteServerHost = remoteServerHost;
		this.remoteServerPort = remoteServerPort;
	}
	
}
