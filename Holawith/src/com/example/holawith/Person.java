package com.example.holawith;

import android.app.Activity;
import android.os.Bundle;

public class Person extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void init() {
		setContentView(R.layout.myinfo);
		findViewById(R.id.arrow_info).bringToFront();
	}
}
