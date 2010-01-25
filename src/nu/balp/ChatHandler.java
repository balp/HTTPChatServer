package nu.balp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ChatHandler implements HttpHandler {

	private UserMap db;

	public ChatHandler(UserMap userMap) {
		db = userMap;
	}

	public void handle(HttpExchange arg0) throws IOException {
		Debug.getLogger().println("Http Request" + arg0.toString());
		InputStream is = arg0.getRequestBody();
		String message = StringUtils.read(is); // .. read the request body
		String name = StringUtils.readName(arg0);
		String key = StringUtils.readKey(arg0);
		String response = "";
		Debug.getLogger().println("Got, send, from \"" + name + "(" + key
				+ ")\" message \"" + message + "\"");
		if (db.sendMessage(name, key, message)) {
			response = "sent";
			arg0.getResponseHeaders().set("Cache-Control", "no-cache");
			arg0.sendResponseHeaders(200, response.length());
		} else {
			response = "error";
			arg0.getResponseHeaders().set("Cache-Control", "no-cache");
			arg0.sendResponseHeaders(500, response.length());

		}
		Debug.getLogger().println("headers");
		OutputStream os = arg0.getResponseBody();
		
		os.write(response.getBytes());
		os.close();

		Debug.getLogger().println("done");

	}
}
