/**
 * 
 */
package nu.balp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

/**
 * @author balp
 *
 */
public class RegisterHandler implements HttpHandler {

	private UserMap db;
	public RegisterHandler(UserMap db) {
		super();
		this.db = db;
	}

	/* (non-Javadoc)
	 * @see com.sun.net.httpserver.HttpHandler#handle(com.sun.net.httpserver.HttpExchange)
	 */
	public void handle(HttpExchange arg0) throws IOException {
		Debug.getLogger().println("Http Request" + arg0.toString());
        InputStream is = arg0.getRequestBody();
        String clientUrl = StringUtils.read(is); // .. read the request body
        String name = StringUtils.readName(arg0);
        String key = StringUtils.readKey(arg0);
        
        Debug.getLogger().println("Got, register, of \"" + name +"("+ key +")\" to \"" + clientUrl +"\"");
        db.updateUrl(key, name, clientUrl);
        String response = "done";
        Debug.getLogger().println("update");
        arg0.getResponseHeaders().set("Cache-Control", "no-cache");
        arg0.sendResponseHeaders(200, response.length());
        Debug.getLogger().println("headers");
        OutputStream os = arg0.getResponseBody();
        os.write(response.getBytes());
        os.close();
        Debug.getLogger().println("done");
        
        
	}
}
