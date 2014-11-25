package com.example.holawith;
import android.app.Activity;
import android.widget.EditText;
import android.widget.Button;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.view.View.OnClickListener;
import android.view.View;
public class Issuestragy extends Activity {
	public EditText title,content;
	public Button cancel,issue;
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  init();
  }
  public void init(){
	  setContentView(R.layout.strategy);
	  title = (EditText)findViewById(R.id.strategy_title);
	  content = (EditText)findViewById(R.id.strategy_content);
	  cancel = (Button)findViewById(R.id.strategy_cancel);
	  issue = (Button)findViewById(R.id.strategy_spon);
	  Spinner s = (Spinner)findViewById(R.id.kind);
	  ArrayAdapter adapter = ArrayAdapter.createFromResource(Issuestragy.this,R.array.spinner_list, 
			  android.R.layout.simple_spinner_item);
	  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	  s.setAdapter(adapter);
	  issue.setOnClickListener(new OnClickListener(){
		 public void onClick(View v){
			 String rel_title = title.getText().toString();
			 String rel_content = content.getText().toString();
			 finish();
		 }
	  });
  }
}
