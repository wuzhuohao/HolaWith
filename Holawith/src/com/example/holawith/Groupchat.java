package com.example.holawith;
import com.holawith.Mychatroom.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.LinearLayout;
public class Groupchat extends Activity {
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  init();
  }
  public void init(){
	  setContentView(R.layout.groupchat);
	  LinearLayout layout = (LinearLayout)findViewById(R.id.groupchatpad);
	  for(int i = 0; i<3; i++){
		  RelativeLayout item = (RelativeLayout)LayoutInflater.from(this).inflate(R.layout.acctionitem, null);
		  item.setOnClickListener(new OnClickListener(){
			 public void onClick(View v){
				 startActivity(new Intent(Groupchat.this,Grouproom.class));
			 }
		  });
		  layout.addView(item);
	  }
  }
}
