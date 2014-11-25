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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.holawith.basic.GLOBAL;
import com.network.holawith.Transfer;

public class registerActivity extends Activity {
	public EditText una, pas, ema;
	public ImageView ri;
	public Button btnSubmit;
	private Bitmap bmp;
	public Calendar calendar;
	public static String str;
	String type;
	private File file;
	private Button btnUpload;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/tmp_image";

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

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String rel = msg.getData().getString("value");
			Log.e("rel", rel);
			finish();
		}
	};

	public void init() {
		calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date date = calendar.getTime();
	    DateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
		str = d.format(date)+".jpg";
		type = getIntent().getExtras().getString("usertype");
		setContentView(R.layout.register);
		una = (EditText) this.findViewById(R.id.username_);
		pas = (EditText) this.findViewById(R.id.password_);
		ema = (EditText) this.findViewById(R.id.email);
		btnSubmit = (Button) this.findViewById(R.id.submit);
		btnUpload = (Button) this.findViewById(R.id.upload);
		ri = (ImageView) this.findViewById(R.id.pic);
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		btnUpload.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(registerActivity.this)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setItems(new String[] { "从相册选择", "拍照" },
								new DialogInterface.OnClickListener() {

									public void onClick(DialogInterface dialog,
											int which) {
										if (which == 0) {
											Intent intent = new Intent(
													Intent.ACTION_PICK,
													android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
											startActivityForResult(intent, 1);
										} else {

											destoryImage();
											String state = Environment
													.getExternalStorageState();
											if (state
													.equals(Environment.MEDIA_MOUNTED)) {
												file = new File(saveDir, str);

												if (!file.exists()) {
													try {
														file.createNewFile();
													} catch (IOException e) {
														e.printStackTrace();

														return;
													}
												}
												Intent intent = new Intent(
														"android.media.action.IMAGE_CAPTURE");
												intent.putExtra(
														MediaStore.EXTRA_OUTPUT,
														Uri.fromFile(file));
												startActivityForResult(intent,
														2);
											} else {
												Toast.makeText(
														registerActivity.this,
														"sdcard无效或没有插入!",
														Toast.LENGTH_SHORT)
														.show();
											}

										}
									}
								}).show();
			}
		});
		final Runnable runnable = new Runnable() {
			public void run() {
				String uname = una.getText().toString();
				String paw = pas.getText().toString();
				String email = ema.getText().toString();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				byte[] photo = baos.toByteArray();
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("username", uname);
				map.put("password", paw);
				map.put("email", email);
				map.put("nickname", "kalvin");
				map.put("address", "桂城");
				map.put("user_type", type);
				map.put("region", "广东");
				ArrayList<String> signature = new ArrayList<String>();
				signature.add("1");
				signature.add("2");
				map.put("signature", signature);
				ArrayList<String> hobby = new ArrayList<String>();
				hobby.add("3");
				hobby.add("4");
				map.put("hobby", hobby);
				String re2 = Transfer.register(map);
				String re = Transfer.uploadImage(GLOBAL.URL_FOR_REGISTER_UPLOAD_ICON, uname, photo);
				Bundle data = new Bundle();
				Message msg = new Message();
				data.putString("value", re);
				data.putString("value2", re2);
				msg.setData(data);
				Log.e("handler", "sendhere");
				handler.sendMessage(msg);
			}
		};
		btnSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.e("submitbtn", "here");
				new Thread(runnable).start();
				
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
