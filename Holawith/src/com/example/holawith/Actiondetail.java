package com.example.holawith;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.holawith.basic.GLOBAL;
import com.network.holawith.Transfer;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;
public class Actiondetail extends Activity {
  public TextView fyear,fmonth,fday,toyear,tomonth,today,ddlyear,ddlmonth,ddlday;
  public String name;
  WebSocketConnection wsc;
  public Button assess;
  public  String acid;
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
      init();
  }
  public void init(){	  
	  
	  Bundle bundle = getIntent().getExtras();
	  if(bundle!=null){
	    acid = bundle.getString("activity_id");
	    Log.e("activity_id", acid);
	    name = bundle.getString("created_by");
	    Log.e("created_by", name);
	  }
	  connect();
	  setContentView(R.layout.action_detail);
	  ImageView arrow = (ImageView)findViewById(R.id.act_arrow);
	  ImageView more = (ImageView)findViewById(R.id.act_more);
	  fyear = (TextView)findViewById(R.id.fyear);
	  fmonth = (TextView)findViewById(R.id.fmonth);
	  fday = (TextView)findViewById(R.id.fday);
	  toyear = (TextView)findViewById(R.id.tyear);
	  tomonth = (TextView)findViewById(R.id.tmonth);
	  today = (TextView)findViewById(R.id.tday);
	  ddlyear = (TextView)findViewById(R.id.ddlyear);
	  ddlmonth = (TextView)findViewById(R.id.ddlmonth);
	  ddlday = (TextView)findViewById(R.id.ddlday);
	  assess = (Button)findViewById(R.id.assess);
 	  arrow.setOnClickListener(new OnClickListener(){
		 public void onClick(View v){
			 finish();
		 }
	  });
 	  assess.setOnClickListener(new OnClickListener(){
 		 public void onClick(View v){
 			 Map map = new HashMap();
 			 map.put("action", "apply");
 			 map.put("activity_id", acid);
// 			 map.put("to", name);
 			 List<String> toList = new ArrayList<String>();
 			 toList.add(name);
 			 map.put("to", toList);
 			 map.put("from", "test");
 			 map.put("content", "");
 			 JSONObject json = new JSONObject(map);
 			 Log.e("json", json.toString());
 			 wsc.sendTextMessage(json.toString());
 		}
 	  });
	  more.setOnClickListener(new OnClickListener(){
		  public void onClick(View v){
			  new AlertDialog.Builder(Actiondetail.this).setIcon(android.R.drawable.ic_dialog_info).
			  setItems(new String[]{"收获","分享到...","在地图上查看","修改","删除"},  new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(which==0){
						
					}
					else if(which==1){
					  
					}
					else if(which==2){
						
					}
					else if(which==3){
					  Intent intent = new Intent(Actiondetail.this,IssueAction.class);
					  intent.putExtra("fyear", fyear.getText().toString());
					  intent.putExtra("fmonth", fmonth.getText().toString());
					  intent.putExtra("fday", fday.getText().toString());
					  intent.putExtra("tyear", toyear.getText().toString());
					  intent.putExtra("tmonth", tomonth.getText().toString());
					  intent.putExtra("tday", today.getText().toString());
					  intent.putExtra("ddlyear", ddlyear.getText().toString());
					  intent.putExtra("ddlmonth", ddlmonth.getText().toString());
					  intent.putExtra("ddlday", ddlday.getText().toString());
					  intent.putExtra("activity_id", acid);
					  intent.putExtra("to", name);
					  startActivity(intent);
					}
					else {
						if(name.equals(GLOBAL.username)){
					  	   Transfer.Delete(name);
					       finish(); 	
						}
					}
				}
				  
			  }
			  ).show();
		  }
	  });
  }
  private void connect() {
		// System.out.println("开始连接websocket///");

		try {
			wsc = new WebSocketConnection();
			wsc.connect("ws://wuzhuohao.eicp.net:9000/say/",
					new WebSocketHandler() {

						public void onBinaryMessage(byte[] payload) {

						}

						public void onClose(int code, String reason) {

						}

						public void onOpen() {

							showtext("连接成功");
							// wsc.sendTextMessage("Hello!");
							// wsc.disconnect();
						}

						public void onRawTextMessage(byte[] payload) {

						}

						public void onTextMessage(String payload) {
						    if(String.valueOf(payload).equals("true"))
							showtext("申请成功");
						    else showtext("添加失败 ");
						}

					});
		} catch (WebSocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
  private void showtext(String msg) {
		Toast.makeText(this, msg, 0).show();
	}
}
