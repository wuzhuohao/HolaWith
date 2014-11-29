package com.network.holawith;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

public class Communicator {
	// private static String backendIP = "192.168.1.73:9000/test/";
	// private static String backendIP = "192.168.137.1:10005/";
	private static String backendIP = "wuzhuohao.eicp.net:9000/";

	public static String sendGet(String method, String content) {
		String result = "";

		URL url = null;
		HttpURLConnection connection = null;
		InputStream in = null;
		try {
			url = new URL("http://" + backendIP + method + content/* + "?index_from=0"*/);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
//			connection.setDoOutput(true);
			in = connection.getInputStream();
			InputStreamReader r = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(r);
			String line = "";
			while ((line = br.readLine()) != null) {
				result += line;
			}
			Log.e("rel", result);
			if (result == null)
				Log.e("noresponse", "nores");
			r.close();
			connection.disconnect();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;

	}

	public static String sendPost(String method, String content) {
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			url = new URL("http://" + backendIP + method);
			connection = (HttpURLConnection) url.openConnection();
			Log.e("connection", "con");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			OutputStream os = connection.getOutputStream();
			DataOutputStream dop = new DataOutputStream(os);
			// String md5Pass = getMD5Str("lalala");
			// dop.writeBytes("username=844526975@qq.com&password="+md5Pass);
			byte[] buffer = content.getBytes();
			dop.write(buffer);
			dop.flush();
			dop.close();
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("error", e.toString());
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("error1", e.toString());
				}
			}
		}
		return result;

	}

	public static String sendPostMulti(String method,
			Map<String, Object> params, byte[] image) {
		String result = "";

		String end = "\r\n";
		String uploadUrl = "http://" + backendIP + method;
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // ��ݷָ���

		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// conn.setUseCaches(false);
			conn.setConnectTimeout(6000);
			conn.setReadTimeout(6000);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ "; boundary=" + BOUNDARY);

			StringBuilder sb = new StringBuilder();

			for (Map.Entry<String, Object> entry : params.entrySet()) {// �����?�ֶ�����
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}

			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.write(sb.toString().getBytes());

			dos.writeBytes("Content-Disposition: form-data; name=\"picture\"\r\n\r\n");
			dos.write(image, 0, image.length);
			dos.writeBytes(end);

			dos.writeBytes("--" + BOUNDARY + "--\r\n");
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();

		} catch (Exception e) {
			result = e.toString();
		}

		return result;

	}

	public static String sendPostMulti_again(String method,
			Map<String, Object> params, byte[] image, String type) {
		String result = "";

		String end = "\r\n";
		String uploadUrl = "http://" + backendIP + method;
		String MULTIPART_FORM_DATA = "multipart/form-data";
		String BOUNDARY = "---------7d4a6d158c9"; // ��ݷָ���

		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);// ��������
			conn.setDoOutput(true);// �������
			// //conn.setUseCaches(false);//��ʹ��Cache
			// conn.setConnectTimeout(6000);// 6�������ӳ�ʱ
			// conn.setReadTimeout(6000);// 6���Ӷ���ݳ�ʱ
			conn.setRequestMethod("POST");
			// conn.setRequestProperty("Connection", "Keep-Alive");
			// conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Type", MULTIPART_FORM_DATA
					+ "; boundary=" + BOUNDARY);

			StringBuilder sb = new StringBuilder();

			// �ϴ��ı?����֣���ʽ��ο�����
			for (Map.Entry<String, Object> entry : params.entrySet()) {// �����?�ֶ�����
				sb.append("--");
				sb.append(BOUNDARY);
				sb.append("\r\n");
				sb.append("Content-Disposition: form-data; name=\""
						+ entry.getKey() + "\"\r\n\r\n");
				sb.append(entry.getValue());
				sb.append("\r\n");
			}

			sb.append("--");
			sb.append(BOUNDARY);
			sb.append("\r\n");

			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.write(sb.toString().getBytes());

			dos.writeBytes("Content-Disposition: form-data; name= \" " + type
					+ "\"; filename = \"icon.jpg\"" + "\r\n");
			dos.writeBytes("Content-Type: image/jpeg\r\n");
			dos.writeBytes("\r\n");
			dos.write(image, 0, image.length);
			dos.writeBytes(end);

			dos.writeBytes("--" + BOUNDARY + "--\r\n");
			dos.flush();

			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			result = br.readLine();

		} catch (Exception e) {
			result = e.toString();
		}

		return result;

	}

}
