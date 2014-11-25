package com.example.holawith;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AdressbookActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.actionpage);
		((TextView) findViewById(R.id.tv_show)).setText("Adressbook");
	}
}