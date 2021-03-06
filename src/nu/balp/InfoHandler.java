package nu.balp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class InfoHandler implements HttpHandler {

	private UserMap userMap;

	public InfoHandler(UserMap userMap) {
		this.userMap = userMap;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		Debug.getLogger().println("Http Request" + arg0.toString());
		
		String response = "<html><head><title>HTTPChatServer</title></head><body><h1>HTTPChatServer</h1>";
		Iterator<String> it = userMap.keyIterator();
		response += "<dl>";
		while(it.hasNext()) {
			String key = it.next();
			response += "<dh>" + userMap.getName(key) + "</dh><dd>"
				+ userMap.getUrl(key) + "</dd>";
		}
		response += "</dl>";
		response += "</body></html>";
		Debug.getLogger().println("Http reply: " + response);
		arg0.getResponseHeaders().set("Cache-Control", "no-cache");
		arg0.sendResponseHeaders(200, response.length());
		OutputStream os = arg0.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

}
