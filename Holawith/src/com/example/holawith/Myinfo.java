package com.example.holawith;
import com.holawith.Mychatroom.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
public class Myinfo extends Activity {
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  init();
  }
  public void init(){
	  setContentView(R.layout.aboutme);
	  RelativeLayout adressbook = (RelativeLayout)findViewById(R.id.adresslayout);
	  RelativeLayout layout = (RelativeLayout)findViewById(R.id.groupchatac);	  
	  layout.setOnClickListener(new OnClickListener(){
		 public void onClick(View v){
			 startActivity(new Intent(Myinfo.this,Groupchat.class));
		 }
	  });
	  RelativeLayout person = (RelativeLayout)findViewById(R.id.personallayou);
	  person.setOnClickListener(new OnClickListener(){
		 public void onClick(View v){
			 startActivity(new Intent(Myinfo.this,Person.class));
		 }
	  });
	  RelativeLayout strategy = (RelativeLayout)findViewById(R.id.aboutstrategy);
	  strategy.setOnClickListener(new OnClickListener(){
		  public void onClick(View v){
			  startActivity(new Intent(Myinfo.this,Strategy.class));
		  }
	  });
	  adressbook.setOnClickListener(new OnClickListener(){
		 public void onClick(View v){
		   startActivity(new Intent(Myinfo.this,IndexActivity.class));
		 }
	  });
  }
}
