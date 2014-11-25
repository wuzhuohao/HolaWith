package com.example.holawith;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONObject;

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
import android.os.Message;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.holawith.basic.GLOBAL;
import com.holawith.common.AsyncSendData;
import com.holawith.common.IHandlerCallback;
import com.holawith.common.MyHandler;
import com.network.holawith.Transfer;

public class IssueAction extends Activity implements IHandlerCallback {
	public EditText issuetitle;
	public EditText fyear, fmonth, fday, fyt, fmt, fdt;
	public EditText tyear, tmonth, tday, tyt, tmt, tdy;
	public EditText ddyear, ddmonth, ddday;
	public EditText issuecontent;
	public EditText pos;
	public EditText label;
	public ImageView ri;
	public String createdate;
	public Button cancel, sendaction, uploadpic;
	private Uri fileUri;
	private Bitmap bmp;
	public Calendar calendar;
	public static String str, cr;
	private File file;
	private Button upload;
	private ImageView finish_;
	public String to,id;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/fj_image";
	private MyHandler handler;

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == 1) {
			ContentResolver resolver = this.getContentResolver();
			try {
				Uri originalUri = data.getData();// 获得图片的uri
				bmp = BitmapFactory.decodeStream(resolver
						.openInputStream(originalUri));
				// bitmap = data.getParcelableExtra("data");
				ri.setImageBitmap(bmp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
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

	public void init() {
		handler = new MyHandler(this);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
          id = bundle.getString("activity_id");
          to = bundle.getString("to");
        }
		calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		Date date = calendar.getTime();
		final DateFormat d = new SimpleDateFormat("yyyyMMddHHmmss");
		str = d.format(date) + ".jpg";
		setContentView(R.layout.soponsoraction);
		issuecontent = (EditText) findViewById(R.id.sponsocnt);
		issuetitle = (EditText) findViewById(R.id.sopontitle);
		fyear = (EditText) findViewById(R.id.syear);
		fmonth = (EditText) findViewById(R.id.smonth);
		fday = (EditText) findViewById(R.id.sday);
		tyear = (EditText) findViewById(R.id.eyear);
		tmonth = (EditText) findViewById(R.id.emonth);
		tday = (EditText) findViewById(R.id.eday);
		ddyear = (EditText) findViewById(R.id.ddyear);
		ddmonth = (EditText) findViewById(R.id.ddmonth);
		ddday = (EditText) findViewById(R.id.ddday);
		pos = (EditText) findViewById(R.id.position);
		label = (EditText) findViewById(R.id.label);
		cancel = (Button) findViewById(R.id.cancel);
		sendaction = (Button) findViewById(R.id.spon);
		uploadpic = (Button) findViewById(R.id.uploadplacepic);
		ri = (ImageView) findViewById(R.id.placepic);
		File savePath = new File(saveDir);
		if (!savePath.exists()) {
			savePath.mkdirs();
		}
		sendaction.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String f_year = fyear.getText().toString();
				String issue_title = issuetitle.getText().toString();
				String issue_content = issuecontent.getText().toString();
				String f_month = fmonth.getText().toString();
				String f_day = fday.getText().toString();
				String t_year = tyear.getText().toString();
				String t_month = tmonth.getText().toString();
				String t_day = tday.getText().toString();
				String tag = label.getText().toString();
				// global.map.put("location", location);
				GLOBAL.map.put("issue_title", issue_title);
				GLOBAL.map.put("issue_content", issue_content);
				// global.map.put("location", location);
				// GLOBAL.map.put("issue_title", issue_title);
				// GLOBAL.map.put("issue_content", issue_content);
				// GLOBAL.isupdateactionissue = true;
				// finish();
				// Log.e("gobal",String.valueOf(global.isupdateactionissue));
				// new Thread(run).start();
				
				HashMap<String, Object> params = new HashMap<String, Object>();
				params.put(GLOBAL.TAG_FOR_ACTION, GLOBAL.URL_FOR_ACTIVITY_CREATE);
				params.put("title", issue_title);
				params.put("content", issue_content);

				HashMap<String, Object> position = new HashMap<String, Object>();
				position.put("address", "桂城");
				HashMap<String, Object> geo = new HashMap<String, Object>();
				geo.put("lng", Float.valueOf("0"));
				geo.put("lat", Float.valueOf("0"));
				position.put("geo", geo);
				params.put("position", position);

				calendar.set(Integer.valueOf(f_year), Integer.valueOf(f_month) - 1,
						Integer.valueOf(f_day));
				final DateFormat tmp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String createDate = tmp.format(calendar.getTime());
				params.put("date", createDate);

				calendar.set(Integer.valueOf(t_year), Integer.valueOf(t_month) - 1,
						Integer.valueOf(t_day));
				String deadline = tmp.format(calendar.getTime());
				params.put("deadline", deadline);
				params.put("created_by", GLOBAL.username);
                if(id==null)    
				new AsyncSendData(IssueAction.this).execute(params);
                else{
                  new Thread(runnable2).start();
                }
                	
				// handler.post(runnable);
				// new Thread(runnable).start();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setResult(RESULT_CANCELED);
				finish();
			}
		});

		uploadpic.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(IssueAction.this)
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
												IssueAction.this,
												"sdcard无效或没有插入!",
												Toast.LENGTH_SHORT)
												.show();
									}

								}
							}
						}).show();
			}
		});
	}

	public void onDestroy() {
		destoryImage();
		super.onDestroy();
	}

	final Runnable runnable = new Runnable() {

		public void run() {
			// calendar = GregorianCalendar.getInstance();
			// calendar.setTimeInMillis(System.currentTimeMillis());
			// Date date = calendar.getTime();
			// DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// createdate = d.format(date);
			// String issue_title = issuetitle.getText().toString();
			// String issue_content = issuecontent.getText().toString();
			// String dd_year = ddyear.getText().toString();
			// String dd_month = ddmonth.getText().toString();
			// String dd_day = ddday.getText().toString();
			// String ddl = dd_year + "-" + dd_month + "-" + dd_day;
			// String category = "[旅游，休闲]";
			// String created_by = GLOBAL.username;
			// String location = pos.getText().toString();
			// String position = "{\"address\":\"" + location + "\"" + ","
			// + "\"geo\":{\"lng\":\"120E\",\"lat\":\"30N\"}" + "}";
			// ByteArrayOutputStream baos = new ByteArrayOutputStream();
			// bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			// byte[] image = baos.toByteArray();
			// cr = Transfer.CreateAction(issue_title, issue_content, image,
			// position, createdate, ddl, category, created_by);
			// Bundle data = new Bundle();
			// data.putString("value", cr);
			// Message msg = new Message();
			// msg.setData(data);
			// handler.sendMessage(msg);
			String f_year = fyear.getText().toString();
			String issue_title = issuetitle.getText().toString();
			String issue_content = issuecontent.getText().toString();
			String f_month = fmonth.getText().toString();
			String f_day = fday.getText().toString();
			String t_year = tyear.getText().toString();
			String t_month = tmonth.getText().toString();
			String t_day = tday.getText().toString();
			String tag = label.getText().toString();
			// global.map.put("location", location);
			// GLOBAL.map.put("issue_title", issue_title);
			// GLOBAL.map.put("issue_content", issue_content);
			// global.map.put("location", location);
			// GLOBAL.map.put("issue_title", issue_title);
			// GLOBAL.map.put("issue_content", issue_content);
			// GLOBAL.isupdateactionissue = true;
			// finish();
			// Log.e("gobal",String.valueOf(global.isupdateactionissue));
			// new Thread(run).start();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("title", issue_title);
			params.put("content", issue_content);

			HashMap<String, Object> position = new HashMap<String, Object>();
			position.put("address", "桂城");
			HashMap<String, Object> geo = new HashMap<String, Object>();
			geo.put("lng", Float.valueOf("0"));
			geo.put("lat", Float.valueOf("0"));
			position.put("geo", geo);
			params.put("position", position);

			DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			calendar.set(Integer.valueOf(f_year), Integer.valueOf(f_month),
					Integer.valueOf(f_day));
			String createDate = d.format(calendar.getTime());
			params.put("date", createDate);

			calendar.set(Integer.valueOf(t_year), Integer.valueOf(t_month),
					Integer.valueOf(t_day));
			String deadline = d.format(calendar.getTime());
			params.put("deadline", deadline);
			params.put("created_by", GLOBAL.username);

			Transfer.CreateAction(params);
		}
	};
	final Runnable runnable2 = new Runnable(){
		public void run(){
			String f_year = fyear.getText().toString();
			String issue_title = issuetitle.getText().toString();
			String issue_content = issuecontent.getText().toString();
			String f_month = fmonth.getText().toString();
			String f_day = fday.getText().toString();
			String t_year = tyear.getText().toString();
			String t_month = tmonth.getText().toString();
			String t_day = tday.getText().toString();
			String tag = label.getText().toString();
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("title", issue_title);
			params.put("content", issue_content);

			HashMap<String, Object> position = new HashMap<String, Object>();
			position.put("address", "桂城");
			HashMap<String, Object> geo = new HashMap<String, Object>();
			geo.put("lng", Float.valueOf("0"));
			geo.put("lat", Float.valueOf("0"));
			position.put("geo", geo);
			params.put("position", position);

			DateFormat d = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			calendar.set(Integer.valueOf(f_year), Integer.valueOf(f_month),
					Integer.valueOf(f_day));
			String createDate = d.format(calendar.getTime());
			params.put("date", createDate);

			calendar.set(Integer.valueOf(t_year), Integer.valueOf(t_month),
					Integer.valueOf(t_day));
			String deadline = d.format(calendar.getTime());
			params.put("deadline", deadline);
			params.put("created_by", GLOBAL.username);
            String cont = new JSONObject(params).toString();
			Transfer.Edit("",to,cont);
		}
	};

	private void destoryImage() {
		bmp = null;
	}

	@Override
	public void handleMessage(Message msg) {
		// TODO Auto-generated method stub

	}
}
