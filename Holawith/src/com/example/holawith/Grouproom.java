package com.example.holawith;

import java.util.HashMap;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.network.holawith.Transfer;

public class Grouproom extends Activity {
	public EditText edit;
	public Button send;
	public static LinearLayout layout;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void init() {
		setContentView(R.layout.room);
		layout = (LinearLayout) findViewById(R.id.chatcontentarea);
		edit = (EditText) findViewById(R.id.editchatcontent);
		send = (Button) findViewById(R.id.send);
		send.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String rel = edit.getText().toString();
				edit.setText("");
				int size = (int) edit.getTextSize();
				RelativeLayout item = (RelativeLayout) LayoutInflater.from(
						Grouproom.this).inflate(R.layout.chatitem, null);
				TextView tv = (TextView) item.findViewById(R.id.chattext);
				tv.setText(rel);
				layout.addView(item);

				// init params
				SharedPreferences settings = getSharedPreferences("grouproom",
						Context.MODE_PRIVATE);
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put("action", "say_in_activity");
				params.put("from", settings.getString("name", ""));
				params.put("activity_id", settings.getString("activity_id", ""));
				params.put("content", rel);
				new SendData().execute(params);
			}
		});
	}

	class SendData extends AsyncTask<HashMap<String, Object>, String, String> {

		private ProgressDialog pDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Grouproom.this);
			pDialog.setMessage("Uploading Picture");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(HashMap<String, Object>... params) {
			// TODO Auto-generated method stub
			return Transfer.say(params);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			pDialog.dismiss();
		}

	}
}
