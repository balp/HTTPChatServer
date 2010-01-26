/**
 * 
 */
package nu.balp;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

/**
 * @author balp
 *
 */
public class ChatServer {
	public class HttpServerThread extends Thread {
		private HttpServer server;

		public HttpServerThread(HttpServer server) {
			this.server = server;
		}

		public void run() {
			server.start();		
		}
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String port = System.getProperty("port", "9876");
		int serverPort = Integer.parseInt(port);
		
		(new ChatServer()).start(serverPort);
	}

	/**
	 * @param serverPort 
	 * 
	 */
	private void start(int serverPort) {
		try {
			final UserMap userMap = new UserMap();
			
			HttpServer server = HttpServer.create(new InetSocketAddress(serverPort), 0);
			server.createContext("/register", new RegisterHandler(userMap));
			server.createContext("/send", new ChatHandler(userMap));
			server.createContext("/", new InfoHandler(userMap));
			server.setExecutor(null); // creates a default executor
			HttpServerThread thread = new HttpServerThread(server);
			thread.start();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
