package nu.balp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
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
				connection.setRequestProperty("x-name", names.get(hashKey));
				connection.setDoOutput(true);
				if (connection instanceof HttpURLConnection) {
				    ((HttpURLConnection)connection).setRequestMethod("POST");
				    System.out.println("HTTP Connection");
				}
				System.out.println("connected");
				final OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());
				String fullMessage = name + " on radio:" + message;
				String encMessage = URLEncoder.encode(name + " on radio:" + message, "UTF-8");
				out.write(fullMessage);
				out.flush();
				out.close();
				System.out.println("Sent: \""+ fullMessage + "\"");
				BufferedReader in = new BufferedReader(
					new InputStreamReader(
					    connection.getInputStream()));

				String decodedString;

				while ((decodedString = in.readLine()) != null) {
				    System.out.println(decodedString);
				}
				in.close();
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
