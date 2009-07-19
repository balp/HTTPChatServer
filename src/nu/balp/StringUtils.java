package nu.balp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

public class StringUtils {

	static public String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 */
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
	
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
		return sb.toString();
	}

	public static String readKey(HttpExchange arg0) {
		Headers headers = arg0.getRequestHeaders();
		return headers.getFirst("X-SecondLife-Owner-Key");
	}

	public static String readName(HttpExchange arg0) {
		Headers headers = arg0.getRequestHeaders();
		return headers.getFirst("X-SecondLife-Owner-Name");
	}

	public static String read(InputStream is) {
		String message = convertStreamToString(is).trim();
		return message;
	}

}
