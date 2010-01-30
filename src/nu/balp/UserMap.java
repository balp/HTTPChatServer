package nu.balp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
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
		Debug.getLogger().println("updateUrl(" + key + ", " + name + ", " + clientUrl
				+ ");");
		urls.put(key, clientUrl);
		names.put(key, name);

	}
	public void delKey(String key) {
		urls.remove(key);
		names.remove(key);
	}
	public String getUrl(String key) {
		return urls.get(key);
	}

	public String getName(String key) {
		return names.get(key);
	}

	public boolean sendMessage(String name, String key, String message) {
		if(! urls.containsKey(key)) {
			return false;
		}
		Iterator<String> keys = urls.keySet().iterator();
		Debug.getLogger().println("sendMessage(String "+name+", String "+key+", String "+message+")");
		while (keys.hasNext()) {
			
			String hashKey = keys.next();
			System.out.println("key:" + hashKey);
			try {
				String stringUrl = urls.get(hashKey);
				Debug.getLogger().println("url:" + stringUrl);
				final URL url = new URL(stringUrl);
				final URLConnection connection = url.openConnection();
				connection.setRequestProperty("X-name", names.get(hashKey));
				connection.setDoOutput(true);
				if (connection instanceof HttpURLConnection) {
				    ((HttpURLConnection)connection).setRequestMethod("POST");
				    Debug.getLogger().println("HTTP Connection");
				}
				System.out.println("connected");
				final OutputStreamWriter out = new OutputStreamWriter(
						connection.getOutputStream());
				String fullMessage = name + " on radio: " + message;
//				String encMessage = URLEncoder.encode(name + " on radio: " + message, "UTF-8");
				out.write(fullMessage);
				out.flush();
				out.close();
				Debug.getLogger().println("Sent: \""+ fullMessage + "\"");
				try {
				BufferedReader in = new BufferedReader(
					new InputStreamReader(
					    connection.getInputStream()));

				String decodedString;

				while ((decodedString = in.readLine()) != null) {
					Debug.getLogger().println(decodedString);
				}
				in.close();
				} catch (FileNotFoundException e ) {
					delKey(key);
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;

	}

	public Iterator<String> keyIterator() {
		// TODO Auto-generated method stub
		return names.keySet().iterator();
	}



}
