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

	private static UserMap userMap;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			userMap = new UserMap();
			HttpServer server = HttpServer.create(new InetSocketAddress(9876), 0);
			server.createContext("/register", new RegisterHandler(userMap));
			server.createContext("/send", new ChatHandler(userMap));
			server.setExecutor(null); // creates a default executor
			server.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
