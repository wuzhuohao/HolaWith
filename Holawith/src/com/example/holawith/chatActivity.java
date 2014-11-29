package com.example.holawith;

import org.json.JSONException;
import org.json.JSONObject;

import com.holawith.Mychatroom.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

public class chatActivity extends Activity {

	private Button bt;
	private EditText ed_name;
	private EditText ed_text;
	private EditText ed_to;
	private EditText ed_con;
	private Button bt1;
	WebSocketConnection wsc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chatroompage);
		TextView tv = (TextView) this.findViewById(R.id.tv_show);
		tv.setText("chatroom");
		bt = (Button) findViewById(R.id.bt);
		ed_name = (EditText) findViewById(R.id.ed_name);
		ed_text = (EditText) findViewById(R.id.ed_text);
		ed_to = (EditText) findViewById(R.id.ed_toname);
		ed_con = (EditText) findViewById(R.id.content);
		bt1 = (Button) findViewById(R.id.bt1);
		bt.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String str = "{\"action\":\"say_to_somebody\",\"from\":\""
						+ ed_name.getText().toString() + "\",\"to\":["
						+ ed_to.getText().toString() + "],\"content\":\""
						+ ed_text.getText().toString() + "\"}";
				JSONObject jsonObject;
				String str1 = "{\"action\":\"who_am_I\",\"from\":\""
						+ ed_name.getText().toString() + "\"}";
				try {
					jsonObject = new JSONObject(str1);
					wsc.sendTextMessage(jsonObject.toString());
					jsonObject = new JSONObject(str);
					wsc.sendTextMessage(jsonObject.toString());

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		bt1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				connect();
			}
		});
		wsc = new WebSocketConnection();

	}

	private void connect() {
		// System.out.println("开始连接websocket///");

		try {

			wsc.connect("ws://wuzhuohao.eicp.net:9000/say/",
					new WebSocketHandler() {

						public void onBinaryMessage(byte[] payload) {

						}

						public void onClose(int code, String reason) {

						}

						public void onOpen() {

							showtext("连接成功");
							// wsc.sendTextMessage("Hello!");
							// wsc.disconnect();
						}

						public void onRawTextMessage(byte[] payload) {

						}

						public void onTextMessage(String payload) {
							ed_con.setText(payload);
							showtext(payload);
						}

					});
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * final Handler handler = new Handler(){ public void handleMessage(Message
	 * msg){ String rel = msg.getData().getString("wsc"); Log.e("result",rel); }
	 * }; final Runnable runnable = new Runnable(){ public void run(){
	 * connect(); Bundle bundle = new Bundle(); Message msg = new Message();
	 * bundle.putString("wsc", "success"); msg.setData(bundle); //
	 * handler.sendMessage(msg); } };
	 */

	/*
	 * public void onClick(View v) { // TODO Auto-generated method stub int id =
	 * v.getId(); switch (id) { case R.id.bt :
	 * wsc.sendTextMessage("我是客户端，我通过ws往服务器发数据"); break; case R.id.bt1 : new
	 * Thread(runnable).start(); break; default : break; } }
	 */
	private void showtext(String msg) {
		Toast.makeText(this, msg, 0).show();
	}
}
