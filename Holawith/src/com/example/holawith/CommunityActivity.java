package com.example.holawith;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
public class CommunityActivity extends Activity {
	public ImageView plus;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}
public void init(){
	 setContentView(R.layout.communitypage);
	 plus = (ImageView)findViewById(R.id.complus);
	 plus.setOnClickListener(new OnClickListener(){
		public void onClick(View v){
		  new AlertDialog.Builder(CommunityActivity.this).setIcon(android.R.drawable.ic_dialog_info)
		  .setItems(new String []{"发布新策略"}, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
			   startActivity(new Intent(CommunityActivity.this,Issuestragy.class));
              				 
			}
		}).show();
		}
	 });
  }
}
