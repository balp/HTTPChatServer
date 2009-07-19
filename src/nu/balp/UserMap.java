package nu.balp;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserMap {

	private Map<String, String> urls;

	private Map<String, String> names;

	public UserMap() {
		urls = new HashMap<String, String>();
		names = new HashMap<String, String>();

	}

	public void updateUrl(String key, String name, String clientUrl) {
		System.out.println("updateUrl(" + key + ", " + name + ", " + clientUrl
				+ ");");
		urls.put(key, clientUrl);
		names.put(key, name);

	}

	public String getUrl(String key) {
		return urls.get(key);
	}

	public String getName(String key) {
		return names.get(key);
	}

	public void sendMessage(String name, String key, String message) {
		Iterator<String> keys = urls.keySet().iterator();
		System.out.println("sendMessage(String "+name+", String "+key+", String "+message+")");
		while (keys.hasNext()) {
			
			String hashKey = keys.next();
			System.out.println("key:" + hashKey);
			try {
				String stringUrl = urls.get(hashKey);
				System.out.println("url:" + stringUrl);
				final URL url = new URL(stringUrl);
				final URLConnection connection = url.openConnection();
				connection.setDoOutput(true);
				System.out.println("connected");
				final OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());
				String fullMessage = name + " on radio:" + message;
				out.write(fullMessage);
				out.flush();
				out.close();
				System.out.println("Sent: \""+ fullMessage + "\"");
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
