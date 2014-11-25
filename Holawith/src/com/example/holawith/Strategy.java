package com.example.holawith;
import android.app.Activity;
import android.os.Bundle;
public class Strategy extends Activity{
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  init();
  }
  public void init(){
	  setContentView(R.layout.introduction_page);
  }
}
