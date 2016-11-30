package com.gehc.ns.sample.auth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestClient {
	public static String get(String urlToRead, String token) throws Throwable {
		HttpURLConnection conn = getHttpURLConnectionForURL(urlToRead);
		conn.setRequestMethod("GET");
		attachTokenToHttpURLConnection(conn, token);
		return getResultBodyFromHttpURLConnection(conn);
	}
	
	private static HttpURLConnection getHttpURLConnectionForURL(String urlToRead) throws Throwable {
		URL url = new URL(urlToRead);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		return conn;
	}
	
	private static void attachTokenToHttpURLConnection(HttpURLConnection conn, String token) {
		String OAuth2Header = "Bearer " + token; 
		conn.setRequestProperty("Authorization", OAuth2Header);
	}
	
	private static String getResultBodyFromHttpURLConnection(HttpURLConnection conn) throws Throwable {		
		BufferedReader rd = getBufferedReaderForHttpURLConnection(conn);
		String result = readStringFromBufferedReader(rd);      
		rd.close();
		
		return result;
	}
	
	private static BufferedReader getBufferedReaderForHttpURLConnection(HttpURLConnection conn) throws Throwable {
		InputStream inStr = conn.getInputStream();
		InputStreamReader inStrReader = new InputStreamReader(inStr);
		return new BufferedReader(inStrReader);
	}
	
	private static String readStringFromBufferedReader(BufferedReader rd) throws Throwable {
		StringBuilder result = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		return result.toString();
	}
}
