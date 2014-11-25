package com.network.holawith;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.holawith.basic.GLOBAL;

import android.util.Log;

public class Transfer {
	public static String login(String uname, String paw) {
		String content = "username=" + uname + "&password=" + paw;
		String rel = Communicator.sendPost("login/", content);
		return rel;
	}

	public static String register(String uname, String paw, String email,
			String usertype) {
		String content = "username=" + uname + "&password=" + paw + "&email="
				+ email + "&usertype=" + usertype;
		String rel = "";
		try {
			JSONObject jsonObject = new JSONObject(content);
			content = jsonObject.toString();
			rel = Communicator.sendPost("register/", content);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rel;
	}

	public static String register(HashMap<String, Object> map) {
		String rel = "";
		JSONObject jsonObject = new JSONObject(map);
		String content = jsonObject.toString();
		Log.i("json", content);
		rel = Communicator.sendPost("register/", content);
		return rel;
	}

	public static String say(Map<String, Object>[] params) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String CreateAction(HashMap<String, Object> hashMap) {
		JSONObject jsonObject = new JSONObject(hashMap);
		String content = jsonObject.toString();
		Log.i("json", content);
		return Communicator.sendPost(GLOBAL.URL_FOR_ACTIVITY_CREATE, content);
	}

	public static String uploadImage(String path, String uname, byte[] img) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("username", uname);
		String rel = Communicator.sendPostMulti_again(path, map, img, "image");
		return rel;
	}

	public static String getimg(String uname) {
		String content = "name=" + uname;
		String rel = Communicator.sendGet("icon/get/", content);
		return rel;
	}

	public static String CreateAction(String title, String ac_con,
			byte[] image, String pos, String date, String ddl, String cate_,
			String created_by) {
		Map map = new HashMap();
		map.put("title", title);
		map.put("content", ac_con);
		map.put("position", pos);
		map.put("data", date);
		map.put("deadline", ddl);
		map.put("category", cate_);
		map.put("created_by", created_by);
		String content = Communicator.sendPostMulti_again("activity/create/",
				map, image, "image");
		return content;

	}

	public static String apply(String acid, String from, String to,
			String content) {
		String cont = "{\"action\":\"apply\",\"activity_id\":\"" + acid + "\""
				+ "," + "\"from\":\"" + from + "\""

				+ "," + "\"to\":\"" + to + "\"" + "," + "\"content\":\""
				+ content + "\"" + "}";
		String rel = "";
		try {
			JSONObject jsonObject = new JSONObject(cont);
			cont = jsonObject.toString();
			rel = Communicator.sendPost("register/", cont);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rel;
	}

	public static String Ack(String acid, String from, String to, String content) {
        Map map = new HashMap();
        map.put("action", "ack");
        map.put("activity_id", acid);
        map.put("from", from);
        map.put("to", to);
        map.put("content",content);
		String cont = new JSONObject(map).toString();
		String rel;
	    rel = Communicator.sendPost("activity/ack/", cont);
		
		return rel;
	}

	public static String Share(String from, String to, String acid) {
	
		    Map map = new HashMap();
	        map.put("action", "share");
	        map.put("activity_id", acid);
	        map.put("from", from);
	        map.put("to", to);
			String cont = new JSONObject(map).toString();
			String rel;
		    rel = Communicator.sendPost("activity/share/", cont);
		return rel;
	}

	public static String issue_Comment(String from, String to, String content) {
		
		 Map map = new HashMap();
	        map.put("action", "comment");
	        map.put("from", from);
	        map.put("to", to);
	        map.put("content",content);
			String cont = new JSONObject(map).toString();
			String rel;
		    rel = Communicator.sendPost("activity/comment/", cont);
		return rel;
	}

	public static String Edit(String from, String to, String content) {
		 Map map = new HashMap();
	        map.put("action", "edit");
	        map.put("from", from);
	        map.put("to", to);
	        map.put("content",content);
			String cont = new JSONObject(map).toString();
			String rel;
			rel = Communicator.sendPost("activity/edit/", cont);
		return rel;
	}
	public static String Get_action_item(int index_from){
		Map map = new HashMap();
		map.put("index_from", index_from);
		String cont = new JSONObject(map).toString();
		String rel = Communicator.sendGet("activity/get/", cont);
		return rel;
	}

	public static String Delete(String from) {
		
		 Map map = new HashMap();
	        map.put("action", "delete");
	        map.put("from", from);
			String cont = new JSONObject(map).toString();
			String rel;
			rel = Communicator.sendPost("activity/delete/", cont);
		return rel;
	}
}
