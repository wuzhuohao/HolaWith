package com.example.holawith;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import com.holawith.Mychatroom.R;
import com.network.holawith.Transfer;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
public class Registerbussiness extends Activity{
  public EditText una,pas,ema;
  public Button btnSubmit;
  public Button btnUpload;
  SelectPicPopupWindow menuWindow;
  public Calendar calendar;
  public static String str;
  private File file;
  public ImageView ri;
  public Bitmap bmp;
  private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/tmp_image";
  public void onCreate(Bundle savedInstanceState){
	  super.onCreate(savedInstanceState);
	  init();
  }
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == 1) {

			ContentResolver resolver = this.getContentResolver();
			try {
				Uri originalUri = data.getData(); // 获得图片的uri
				bmp = BitmapFactory.decodeStream(resolver
						.openInputStream(originalUri));
				// bitmap = data.getParcelableExtra("data");
				ri.setImageBitmap(bmp);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
			if (file != null && file.exists()) {
				BitmapFactory.Options option = new BitmapFactory.Options();
				option.inSampleSize = 2;
				bmp = BitmapFactory.decodeFile(file.getPath(), option);
				Matrix matrix = new Matrix();
				matrix.postScale(1f, 1f);
				matrix.postRotate(90);
				bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
						bmp.getHeight(), matrix, true);

				int w = bmp.getWidth();
				int h = (int) ((1 - 0.4f) * bmp.getHeight());
				bmp = Bitmap.createBitmap(bmp, 0, 0, w, h, null, false);
				ri.setImageBitmap(bmp);

			}
		}
	}
 public  OnClickListener  itemsOnClick = new OnClickListener(){

		public void onClick(View v) {
			
			menuWindow.dismiss();
			File savePath = new File(saveDir);
			if (!savePath.exists()) {
				savePath.mkdirs();
			}
			switch (v.getId()) {
			case R.id.btn_take_photo:
				destoryImage();
				String state = Environment.getExternalStorageState();
				if (state.equals(Environment.MEDIA_MOUNTED)) {
					file = new File(saveDir, str);
				
					if (!file.exists()) {
						try {
							file.createNewFile();
						} catch (IOException e) {
							e.printStackTrace();
							Toast.makeText(Registerbussiness.this, "照片创建失败!",
									Toast.LENGTH_LONG).show();
							return;
						}
					}
					Intent intent = new Intent(
							"android.media.action.IMAGE_CAPTURE");
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
					startActivityForResult(intent, 2);
				} else {
					Toast.makeText(Registerbussiness.this, "sdcard无效或没有插入!",
							Toast.LENGTH_SHORT).show();
				}
				break;
			case R.id.btn_pick_photo:	
				 Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
				  startActivityForResult(intent, 1);
				break;
			default:
				break;
			}
			
				
		}
  	
  };
  final Handler handler = new Handler(){
	  public void handleMessage(){
		  finish();
	  }
  };
  final Runnable runnable = new Runnable(){
	  public void run(){
		String nickname = "abc";
		String region="地球上的某处";
		String address="广州大学城中山大学";
		String uname = una.getText().toString();
		String password = pas.getText().toString();
		String email = ema.getText().toString();
	    ArrayList<String> category = new ArrayList<String>();
	    category.add("休闲");
	    category.add("旅游");
	    category.add("运动");
	    ArrayList<String> products = new ArrayList<String>();
	    products.add("强力老鼠药");
	    products.add("一剂就见效");
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		byte[] photo = baos.toByteArray();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("username", uname);
		map.put("password", password);
		map.put("email", email);
		map.put("nickname",nickname);
		map.put("region", region);
		map.put("address", address);
		map.put("category", category);
		map.put("products", products);
		map.put("user_type", "business");
		map.put("description", "中大便当店");
		String re = Transfer.register(map);
		String re2 = Transfer.uploadImage("license/upload/", uname, photo);
		Bundle data = new Bundle();
		Message msg = new Message();
		data.putString("value", re);
		data.putString("value2", re2);
		msg.setData(data);
		Log.e("handler", "sendhere");
		handler.sendMessage(msg);
	  }
  };
  public void init(){
	    setContentView(R.layout.register_business);
	    calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date date = calendar.getTime();
        DateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
        ri = (ImageView)findViewById(R.id.workpic);
        str = d.format(date)+".jpg";
		una = (EditText) this.findViewById(R.id.username_);
		pas = (EditText) this.findViewById(R.id.password_);
		ema = (EditText) this.findViewById(R.id.email);
		btnSubmit = (Button) this.findViewById(R.id.submit);
		btnSubmit.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				new Thread(runnable).start();
			}
		});
		btnUpload = (Button) this.findViewById(R.id.piclayout);
		btnUpload.setOnClickListener(new OnClickListener() {			
				public void onClick(View v) {
					//ʵ��SelectPicPopupWindow
					menuWindow = new SelectPicPopupWindow(Registerbussiness.this,itemsOnClick);
					//��ʾ����
					menuWindow.showAtLocation(Registerbussiness.this.findViewById(R.id.main), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //����layout��PopupWindow����ʾ��λ��
				}
			});
		  
  }
  public void onDestroy() {
		destoryImage();
		super.onDestroy();
	}

	private void destoryImage() {
		bmp = null;
	}
}
