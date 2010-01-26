package nu.balp;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class InfoHandler implements HttpHandler {

	private UserMap userMap;

	public InfoHandler(UserMap userMap) {
		this.userMap = userMap;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		// TODO Auto-generated method stub
		OutputStream os = arg0.getResponseBody();
		String response = "done";
		
		os.write(response.getBytes());
		os.close();
	}

}
