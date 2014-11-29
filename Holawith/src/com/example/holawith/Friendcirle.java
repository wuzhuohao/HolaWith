package com.example.holawith;

import com.holawith.Mychatroom.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class Friendcirle extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void init() {
		setContentView(R.layout.friendcircle);
		LinearLayout layout = (LinearLayout) findViewById(R.id.fcontentpad);
		for (int i = 0; i < 10; i++) {
			RelativeLayout item = (RelativeLayout) LayoutInflater.from(this)
					.inflate(R.layout.fitem, null);
			layout.addView(item);
		}
	}
}
