package com.example.holawith;

import com.holawith.Mychatroom.R;

import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.View;
import android.content.Intent;

public class Judge extends Activity {
	public Button bussiness;
	public Button notbuss;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void init() {
		setContentView(R.layout.judge);
		bussiness = (Button) findViewById(R.id.business);
		notbuss = (Button) findViewById(R.id.notbusiness);
		bussiness.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Judge.this, Registerbussiness.class);
				Bundle data = new Bundle();
				data.putString("usertype", "business");
				intent.putExtras(data);
				startActivity(intent);
			}

		});
		notbuss.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(Judge.this, registerActivity.class);
				Bundle data = new Bundle();
				data.putString("usertype", "normal");
				intent.putExtras(data);
				startActivity(intent);
			}
		});
	}
}
