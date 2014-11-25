package com.holawith.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Jsondecode {
	 public static Map json2Map(String jstr) throws JSONException{
		    JSONObject jsonObject = new JSONObject(jstr);
		    Map rel = new HashMap();
		    Iterator iter = jsonObject.keys();
		    String key = null;
		    String value = null;
		    while(iter.hasNext()){
		      key = (String)iter.next();
		      value = jsonObject.getString(key);
		      rel.put(key, value);
		    }
		    return rel;
		  }
	 public static ArrayList<Map> json2MapArray(String jstr)throws JSONException{
			JSONArray ja = new JSONArray(jstr);
			ArrayList<Map> rel = new ArrayList<Map>();
			for(int i = 0; i<ja.length(); i++){
			  Map item = new HashMap();
			  item = json2Map(ja.getString(i));
			  rel.add(item);
			}
			return rel;
		  }
}
