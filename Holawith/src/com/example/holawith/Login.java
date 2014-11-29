package com.example.holawith;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.holawith.Mychatroom.R;
import com.holawith.basic.GLOBAL;
import com.holawith.common.IHandlerCallback;
import com.holawith.common.MyHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class Login extends Activity implements IHandlerCallback {

	private EditText pas;
	private EditText uname;
	private Button loginbtn;
	private Button registerbtn;
	private MyHandler handler;
    public ImageView qqbtn;
    public String thirdToken;
    public String thirdId;
    public static Tencent mTencent;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	@Override
	public void handleMessage(Message msg) {
		startActivity(new Intent(Login.this, MainActivity.class));
	}

	final Runnable runnable = new Runnable() {
		public void run() {
			String rel = "sdfsdf";
			GLOBAL.username = uname.getText().toString();
			GLOBAL.password = pas.getText().toString();
			Bundle data = new Bundle();
			Message msg = new Message();
			data.putString("value", rel);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	public void init() {
		handler = new MyHandler(this);

		setContentView(R.layout.login);
		pas = (EditText) this.findViewById(R.id.inputpassword);
		uname = (EditText) this.findViewById(R.id.inputusername);
		loginbtn = (Button) this.findViewById(R.id.Login);
		registerbtn = (Button) this.findViewById(R.id.register);
		qqbtn = (ImageView)findViewById(R.id.qq);
		qqbtn.setOnClickListener(new OnClickListener(){
	    	 public void onClick(View v){
	    		  mTencent = Tencent.createInstance("1103476141", Login.this);	
				  onQQClickLogin();
				  new Thread(runnableQQ).start();
	    	 }
	      });
		loginbtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					loginbtn.setBackgroundColor(Color.rgb(255, 180, 0));
					return true;
				case MotionEvent.ACTION_UP:
					loginbtn.setBackgroundResource(R.drawable.buttonbk);
					new Thread(runnable).start();
					return true;
				default:
					break;
				}
				// weibo_layout.getLocationOnScreen(location);
				// Toast.makeText(login.this,
				// String.valueOf(location[0])+" "+String.valueOf(location[1]),
				// Toast.LENGTH_LONG).show();
				return false;
			}
		});

		registerbtn.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					registerbtn.setBackgroundColor(Color.rgb(255, 180, 0));
					startActivity(new Intent(Login.this, Judge.class));
					return true;
				case MotionEvent.ACTION_UP:
					registerbtn.setBackgroundResource(R.drawable.buttonbk);
					return true;
				default:
					break;
				}
				// weibo_layout.getLocationOnScreen(location);
				// Toast.makeText(login.this,
				// String.valueOf(location[0])+" "+String.valueOf(location[1]),
				// Toast.LENGTH_LONG).show();
				return false;
			}
		});
		
		final WebSocketConnection wsc = new WebSocketConnection();
		try {
			wsc.connect("ws://wuzhuohao.eicp.net:9000/say/", new WebSocketHandler(){
				
				@Override
				public void onOpen() {
//					data = {"action": "who_am_I", "from": "吴卓豪"}
					HashMap<String, String> hashmap = new HashMap<String, String>();
					hashmap.put("action", "who_am_I");
					hashmap.put("from", "吴卓豪");
					JSONObject json = new JSONObject(hashmap);
					wsc.sendTextMessage(json.toString());
				}
				
				@Override
				public void onTextMessage(String payload) {
					Toast.makeText(Login.this, payload, Toast.LENGTH_LONG).show();
				}
			});
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	final Handler handlerQQ = new Handler(){
		  public void handleMessage(Message msg){
			 //Log.e("userinfo2",userinformation.toString());
			  startActivity(new Intent(Login.this,MainActivity.class));
		  }
	  };
	  final Runnable runnableQQ = new Runnable(){
		
			  public void run(){
		   			 while(TextUtils.isEmpty(thirdToken)||TextUtils.isEmpty(thirdId)){
		  			   thirdToken = mTencent.getAccessToken();
		  			   thirdId = mTencent.getOpenId(); 
		  		   }
		   			
		   		    Bundle data = new Bundle();
		   	        UserInfo mInfo = new UserInfo(Login.this, mTencent.getQQToken());
		   			mInfo.getUserInfo(new BaseUiListener(Login.this,"get_simple_userinfo"));
		   			
		   			Message msg = new Message();
		   			 
		   			 data.putString("value", "sddf");
		   			 msg.setData(data);
		   			 
			     }  
		  
	  };
	 private void onQQClickLogin() {
			// 检测实例是否已经登录
			if (!mTencent.isSessionValid()) {
				// 如果没有登录的话，执行登录操作
				mTencent.login(this, "All", loginListener);
				Toast.makeText(this, "正在登陆...", Toast.LENGTH_SHORT).show();
			} else {
				// 登录的话就退出，并且更新用户信息和按钮
				mTencent.logout(this);
			
			}
			
		 }
	  private void initOpenidAndToken(JSONObject jsonObject) {
			try {
				String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
				String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
				String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
				if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
						&& !TextUtils.isEmpty(openId)) {
					mTencent.setAccessToken(token, expires);
					mTencent.setOpenId(openId);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	  IUiListener loginListener = new BaseUiListener() {
			
			protected void doComplete(JSONObject values) {
				Log.d("SDKQQAgentPref",
						"AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
				// 初始化openid和token
				initOpenidAndToken(values);
				
			}
		};
		private class BaseUiListener implements IUiListener {

			@Override
			public void onCancel() {
				Util.toastMessage(Login.this, "onCancel: ");
				Util.dismissDialog();
			}
			private Context mContext;  
	        private String mScope;  
	  
	        public BaseUiListener() {  
	        }  
	  
	        public BaseUiListener(Context mContext) {  
	            super();  
	            this.mContext = mContext;  
	        }  
	  
	        public BaseUiListener(Context mContext, String mScope) {  
	            super();  
	            this.mContext = mContext;  
	            this.mScope = mScope;  
	        }  
			@Override
			public void onComplete(Object arg0) {
				//Util.showResultDialog(login.this, arg0.toString(), "登录成功");
			    //Log.e("userinfo",arg0.toString());
			    //userinformation = (JSONObject)arg0;
				doComplete((JSONObject) arg0);
			}

			protected void doComplete(JSONObject values) {
			
			   

	          //Log.e("JSONObject",values.toString());
			}

			@Override
			public void onError(UiError arg0) {
				Util.toastMessage(Login.this, "onError: " + arg0.errorDetail);
				Util.dismissDialog();
			}

		}
}
