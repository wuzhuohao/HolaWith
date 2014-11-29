package com.example.holawith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.holawith.Mychatroom.R;
import com.holawith.basic.GLOBAL;
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
	private PullToRefreshListView pullToRefreshView;
	private SimpleAdapter listAdapter;
	private ArrayList<HashMap<String, Object>> listViewData;
	private int mIndexFrom;
	private ListView mListView;
	private int mLastFirstVisibleItem;
	private boolean mIsUp = false;

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
		hlinearlayout = (LinearLayout) findViewById(R.id.hlinearlayout);
		int perWidth = getResources().getDisplayMetrics().widthPixels / 4;
		int targetWidth = perWidth;
		int targetHeight = targetWidth;
		ImageView imageview1 = new ImageView(ActionActivity.this);
		imageview1.setImageBitmap(ThumbnailUtils.extractThumbnail(BitmapFactory
				.decodeResource(getResources(), R.drawable.icons_color_09),
				targetWidth, targetHeight));
		hlinearlayout.addView(imageview1, new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		ImageView imageview2 = new ImageView(ActionActivity.this);
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
		action_more = (ImageView) findViewById(R.id.action_more);
		Log.e("isupdate", String.valueOf(GLOBAL.isupdateactionissue));

		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		mListView = pullToRefreshView.getRefreshableView();
		listViewData = new ArrayList<HashMap<String, Object>>();
		listAdapter = new SimpleAdapter(ActionActivity.this, listViewData,
				R.layout.acctionitem,
				new String[] { "actiontitle", "sponsor" }, new int[] {
						R.id.actiontitle, R.id.sponsor });
		pullToRefreshView.setAdapter(listAdapter);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						DateFormat d = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						Calendar calendar = GregorianCalendar.getInstance();
						Date date = calendar.getTime();
						String str = d.format(date);

						if (mIsUp) {
							Log.e("onRefresh", "上拉加载");
							ILoadingLayout loadingLayout = pullToRefreshView
									.getLoadingLayoutProxy();
							loadingLayout.setRefreshingLabel("正在加载");
							loadingLayout.setPullLabel("上拉加载更多");
							loadingLayout.setReleaseLabel("释放开始加载");
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel("最后加载时间:" + str);
							loadingLayout = null;
						} else {
							Log.e("onRefresh", "下拉刷新");
							ILoadingLayout loadingLayout = pullToRefreshView
									.getLoadingLayoutProxy();
							loadingLayout.setRefreshingLabel("正在刷新");
							loadingLayout.setPullLabel("下拉刷新");
							loadingLayout.setReleaseLabel("释放开始刷新");
							refreshView.getLoadingLayoutProxy()
									.setLastUpdatedLabel("最后更新时间:" + str);
							mIndexFrom = 0;
							listViewData.clear();
							loadingLayout = null;
						}

						new GetDataTask().execute();
					}
				});
		pullToRefreshView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (view.getId() == mListView.getId()) {
					final int currentFirstVisibleItem = mListView
							.getFirstVisiblePosition();

					if (currentFirstVisibleItem > mLastFirstVisibleItem) {
						mIsUp = true;
					} else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
						mIsUp = false;
					}
					mLastFirstVisibleItem = currentFirstVisibleItem;
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (mIsUp) {
					Log.e("onScroll", "上拉加载");
					ILoadingLayout loadingLayout = pullToRefreshView
							.getLoadingLayoutProxy(false, true);
					loadingLayout.setRefreshingLabel("正在加载");
					loadingLayout.setPullLabel("上拉加载更多");
					loadingLayout.setReleaseLabel("释放开始加载");
					loadingLayout = null;
				} else {
					Log.e("onScroll", "下拉刷新");
					ILoadingLayout loadingLayout = pullToRefreshView
							.getLoadingLayoutProxy(true, false);
					loadingLayout.setRefreshingLabel("正在刷新");
					loadingLayout.setPullLabel("下拉刷新");
					loadingLayout.setReleaseLabel("释放开始刷新");
					loadingLayout = null;
				}
			}
		});

		pullToRefreshView
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {

					@Override
					public void onLastItemVisible() {
						mIsUp = true;
						Log.e("setOnLastItemVisibleListener", "上拉加载");
						pullToRefreshView.setMode(Mode.BOTH);
						ILoadingLayout loadingLayout = pullToRefreshView
								.getLoadingLayoutProxy();
						loadingLayout.setRefreshingLabel("正在加载");
						loadingLayout.setPullLabel("上拉加载更多");
						loadingLayout.setReleaseLabel("释放开始加载");
						loadingLayout = null;
					}
				});

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
											startActivity(new Intent(
													ActionActivity.this,
													MapActivity.class));
										} else {
											Intent intent = new Intent(
													ActionActivity.this,
													IssueAction.class);
											startActivityForResult(intent,
													CODE_ISSUE_ACTION);
										}
									}
								}).show();
			}
		});
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected void onPostExecute(String[] result) {
			listAdapter.notifyDataSetChanged();
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

		@Override
		protected String[] doInBackground(Void... params) {
			String rel = Transfer.Get_action_item(mIndexFrom);
			try {
				JSONObject json = new JSONObject(rel);
				final JSONArray a = json.getJSONArray("activities");
				Log.e("pulltorefresh", json.toString());
				int len = a.length();
				// listViewData.clear();
				HashMap<String, Object> map = null;
				for (int i = 0; i < len; ++i) {
					map = new HashMap<String, Object>();
					map.put("actiontitle", a.getJSONObject(i)
							.getString("title"));
					map.put("sponsor", a.getJSONObject(i).getString("content"));
					listViewData.add(map);
					mIndexFrom++;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
