package com.holawith.basic;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;

public class GLOBAL {
	public static String username;
	public static String password;
	public static Bitmap bmp;
	public static boolean isupdateactionissue = false;
	public static Map<String, String> map = new HashMap<String, String>();

	public static final String TAG_FOR_HOST = "http://wuzhuohao.eicp.net:9000/";
	
	public static final String TAG_FOR_ACTION = "action";

	public static final String TAG_FOR_REGISTER = "register";
	public static final String URL_FOR_REGISTER_UPLOAD_ICON = "icon/update/";
	
	public static final String TAG_FOR_ACTIVITY = "activity";
	public static final String URL_FOR_ACTIVITY_CREATE = "activity/create/";

}
