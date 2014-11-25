package com.example.holawith;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.holawith.adapter.gridviewadapter;
import com.holawith.basic.GLOBAL;
import com.holawith.common.Jsondecode;
import com.holawith.common.Utils;
import com.network.holawith.DataType.Action;
import com.network.holawith.Transfer;

public class ActionActivity extends Activity {
	public GridView mygridview;
	public LinearLayout contentlayout;
	public ImageView action_more;
	public ArrayList<Action> aclist;
	public ArrayList<RelativeLayout> layouts;
	private final int CODE_ISSUE_ACTION = 0;
	private LinearLayout hlinearlayout;

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 100: {
			Bundle bundle = data.getExtras();
			GLOBAL.isupdateactionissue = bundle.getBoolean("isupdate");
			Log.e("onA", String.valueOf(GLOBAL.isupdateactionissue));
		}
			break;
		case CODE_ISSUE_ACTION: {
			switch (resultCode) {
			case RESULT_OK: {

			}
				break;
			case RESULT_CANCELED: {

			}
				break;
			default:
				break;
			}
		}
			break;
		default:
			break;
		}
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		init();
	}

	public void init() {
		setContentView(R.layout.action);
		// mygridview = (GridView) findViewById(R.id.gridview);
		// mygridview.setAdapter((ListAdapter) new gridviewadapter(this));
		hlinearlayout = (LinearLayout) findViewById(R.id.hlinearlayout);
		int targetWidth = 180;
		int targetHeight = 180;
		ImageView imageview1 = new ImageView(ActionActivity.this);
		// imageview1.setImageBitmap(Utils.decodeSampledBitmapFromResource(getResources(),
		// R.drawable.icons_color_09, 100, 100));
		imageview1.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory
				.decodeResource(getResources(), R.drawable.icons_color_09),
				targetWidth, targetHeight));
		hlinearlayout.addView(imageview1, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ImageView imageview2 = new ImageView(ActionActivity.this);
		// imageview2.setImageBitmap(Utils.decodeSampledBitmapFromResource(getResources(),
		// R.drawable.icons_color_23, 100, 100));
		imageview2.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory
				.decodeResource(getResources(), R.drawable.icons_color_23),
				targetWidth, targetHeight));
		hlinearlayout.addView(imageview2, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ImageView imageview3 = new ImageView(ActionActivity.this);
		imageview3.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory
				.decodeResource(getResources(), R.drawable.icons_color_33),
				targetWidth, targetHeight));
		hlinearlayout.addView(imageview3, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ImageView imageview4 = new ImageView(ActionActivity.this);
		imageview4.setImageBitmap(ThumbnailUtils.extractThumbnail(
				BitmapFactory.decodeResource(getResources(), R.drawable.happy),
				targetWidth, targetHeight));
		hlinearlayout.addView(imageview4, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		contentlayout = (LinearLayout) findViewById(R.id.actionpad);
		action_more = (ImageView) findViewById(R.id.action_more);
		Log.e("isupdate", String.valueOf(GLOBAL.isupdateactionissue));

		// for (int i = 0; i < 10; i++) {
		// RelativeLayout actionitem = (RelativeLayout) LayoutInflater.from(
		// this).inflate(R.layout.acctionitem, null);
		// actionitem.setOnClickListener(new OnClickListener() {
		// public void onClick(View v) {
		// startActivity(new Intent(ActionActivity.this,
		// Actiondetail.class));
		// }
		// });
		// contentlayout.addView(actionitem);
		// }

		if (GLOBAL.isupdateactionissue == true) {
			Log.e("isupdate", String.valueOf(GLOBAL.isupdateactionissue));
			RelativeLayout actionitem = (RelativeLayout) LayoutInflater.from(
					this).inflate(R.layout.acctionitem, null);
			actionitem.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					startActivity(new Intent(ActionActivity.this,
							Actiondetail.class));
				}
			});
			TextView actiontitle = (TextView) actionitem
					.findViewById(R.id.actiontitle);
			TextView content = (TextView) actionitem.findViewById(R.id.sponsor);
			String title = GLOBAL.map.get("issue_title");
			String issuecontent = GLOBAL.map.get("issue_content");
			actiontitle.setText(title);
			content.setText(issuecontent);
			contentlayout.addView(actionitem);
			GLOBAL.isupdateactionissue = false;
		}
		action_more.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new AlertDialog.Builder(ActionActivity.this)
						.setIcon(android.R.drawable.ic_dialog_info)
						.setItems(new String[] { "地图上查看", "发布活动" },
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
										if (which == 0) {

										} else {
											Intent intent = new Intent(
													ActionActivity.this,
													IssueAction.class);
											// startActivity(intent);
											startActivityForResult(intent,
													CODE_ISSUE_ACTION);
											// finish();
											// new Thread(runnable2).start();
										}
									}
								}).show();
			}
		});
		new Thread(runnable3).start();
	}

	final Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// String urlpath = msg.getData().getString("url");
			// URL url;
			// try {
			// url = new URL(urlpath);
			// HttpURLConnection conn = (HttpURLConnection) url
			// .openConnection();
			// conn.setRequestMethod("GET");
			// conn.setConnectTimeout(5 * 1000);
			// Bitmap bitmap = null;
			// if (conn.getResponseCode() == 200) {
			// InputStream inputStream = conn.getInputStream();
			// bitmap = BitmapFactory.decodeStream(inputStream);
			// }
			// // img.setImageBitmap(bitmap);
			// } catch (MalformedURLException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (IOException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			String rel = msg.getData().getString("value");
			try {
				JSONObject json = new JSONObject(rel);
				final JSONArray a = json.getJSONArray("activities");
				RelativeLayout layout = (RelativeLayout) LayoutInflater.from(
						ActionActivity.this)
						.inflate(R.layout.acctionitem, null);
				TextView at = (TextView) layout.findViewById(R.id.actiontitle);
				TextView ss = (TextView) layout.findViewById(R.id.sponsor);
				at.setText(a.getJSONObject(0).getString("title"));
				ss.setText(a.getJSONObject(0).getString("content"));
				contentlayout.addView(layout);

				layout.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(ActionActivity.this,
								Actiondetail.class);
						try {
//							intent.putExtra("activity_id", a.getJSONObject(0).getString("id"));
							Bundle bundle = new Bundle();
							bundle.putString("activity_id", a.getJSONObject(0).getString("id"));
							bundle.putString("created_by", a.getJSONObject(0).getString("created_by"));
							intent.putExtras(bundle);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startActivity(intent);
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	};
	final Handler handler3 = new Handler() {
		public void handleMessage(Message msg) {
			// String rel = msg.getData().getString("value");
			// if (rel != null) {
			// try {
			// ArrayList<Map> list = Jsondecode.json2MapArray(rel);
			// for (Map map : list) {
			// RelativeLayout layout = (RelativeLayout) LayoutInflater
			// .from(ActionActivity.this).inflate(
			// R.layout.acctionitem, null);
			// Action item = new Action();
			// item.actionId = map.get("id").toString();
			// item.title = map.get("title").toString();
			// item.created_by = map.get("created_by").toString();
			// item.content = map.get("content").toString();
			// aclist.add(item);
			// layouts.add(layout);
			// }
			// showitem();
			// } catch (Exception e) {
			//
			// }
			// }

			String rel = msg.getData().getString("value");
			try {
				JSONObject json = new JSONObject(rel);
				final JSONArray a = json.getJSONArray("activities");
				RelativeLayout layout = (RelativeLayout) LayoutInflater.from(
						ActionActivity.this)
						.inflate(R.layout.acctionitem, null);
				TextView at = (TextView) layout.findViewById(R.id.actiontitle);
				TextView ss = (TextView) layout.findViewById(R.id.sponsor);
				at.setText(a.getJSONObject(0).getString("title"));
				ss.setText(a.getJSONObject(0).getString("content"));
				contentlayout.addView(layout);

				layout.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						Intent intent = new Intent(ActionActivity.this,
								Actiondetail.class);
						try {
//							intent.putExtra("activity_id", a.getJSONObject(0).getString("id"));
							Bundle bundle = new Bundle();
							bundle.putString("activity_id", a.getJSONObject(0).getString("id"));
							intent.putExtras(bundle);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						startActivity(intent);
					}
				});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	final Runnable runnable2 = new Runnable() {
		public void run() {
			while (GLOBAL.isupdateactionissue == false)
				;
			startActivity(new Intent(ActionActivity.this, MainActivity.class));
		}
	};
	final Runnable runnable3 = new Runnable() {
		public void run() {
			String rel = Transfer.Get_action_item(0);
			Log.e("activity get", rel);
			Bundle bundle = new Bundle();
			bundle.putString("value", rel);
			Message msg = new Message();
			msg.setData(bundle);
			handler.sendMessage(msg);
		}
	};
	final Runnable runnable = new Runnable() {
		public void run() {
			String rel = Transfer.getimg(GLOBAL.username);
			Bundle data = new Bundle();
			Message msg = new Message();
			data.putString("url", rel);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};

	public void refresh() {
		init();
	}

	public void showitem() {
		for (int i = 0; i < aclist.size(); i++) {
			final Action item = aclist.get(i);
			RelativeLayout tmp = layouts.get(i);
			TextView title = (TextView) tmp.findViewById(R.id.actiontitle);
			title.setText(item.title);
			String content = item.content;
			final String name = item.created_by;
			String all = name + "—" + content;
			TextView sp = (TextView) tmp.findViewById(R.id.sponsor);
			sp.setText(all);
			tmp.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Intent intent = new Intent(ActionActivity.this,
							Actiondetail.class);
					intent.putExtra("id", item.actionId);
					intent.putExtra("created_by", name);
					startActivity(intent);

				}
			});
		}
	}
}
