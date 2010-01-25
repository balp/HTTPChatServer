package nu.balp;


public class Debug {
	private static Debug debug;
	
	private Debug() {
	}
	
	public static synchronized Debug getLogger() {
		if(null == debug) {
			debug = new Debug();
		}
		return debug;
	}

	public void println(String message) {
		// TODO Auto-generated method stub
		
	}

	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
