package com.example.holawith;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.holawith.basic.GLOBAL;
import com.holawith.common.IHandlerCallback;
import com.holawith.common.MyHandler;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class Login extends Activity implements IHandlerCallback {

	private EditText pas;
	private EditText uname;
	private Button loginbtn;
	private Button registerbtn;
	private MyHandler handler;

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
}
