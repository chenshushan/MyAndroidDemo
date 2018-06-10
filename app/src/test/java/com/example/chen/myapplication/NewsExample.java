package com.example.chen.myapplication;

import org.json.JSONException;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

public class NewsExample {
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static void postRequestFromUrl(String url, String body) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setDoOutput(true);
		conn.setDoInput(true);
		PrintWriter out = new PrintWriter(conn.getOutputStream());
		out.print(body);
		out.flush();

		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			System.out.println(jsonText);
		} finally {
			instream.close();
		}
	}

	public static void getRequestFromUrl(String url) throws IOException, JSONException {
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		InputStream instream = conn.getInputStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			System.out.println(jsonText);
		} finally {
			instream.close();
		}
	}

	@Test
	public   void test() throws IOException, JSONException {

// 请求示例 url 默认请求参数已经做URL编码
		String url = "http://120.76.205.241:8000/news/qihoo?kw=%E7%BE%8E%E9%A3%9F&site=qq.com&pageToken=9&apikey=5ocMM9S1qZusEwbiTJzBE3kiSRj1cBWjxRWzKkmtHaCyWderapHNB2mILVvOcA67";
		getRequestFromUrl(url);
	}
}