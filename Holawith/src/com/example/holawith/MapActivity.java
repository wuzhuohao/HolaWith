package com.example.holawith;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.lbsapi.BMapManager;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapDoubleClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapLongClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMapStatusChangeListener;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.SupportMapFragment;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.baidu.nplatform.comapi.map.MapController;
import com.holawith.Mychatroom.R;

public class MapActivity extends FragmentActivity implements
		OnGetPoiSearchResultListener, OnGetSuggestionResultListener {
	private PoiSearch mPoiSearch = null;
	private SuggestionSearch mSuggestionSearch = null;
	private final Handler handler = new Handler();
	private BMapManager mapManager;
	private MapView mMapView;
	private MapController mapController;
	private BaiduMap mBaiduMap;
	private static final LatLng GEO_GUANGZHOU = new LatLng(23.155, 113.264);
	private AutoCompleteTextView keyWorldsView = null;
	private AutoCompleteTextView cityview = null;
	private ArrayAdapter<String> sugAdapter = null;
	private int load_Index = 0;
	private String touchType;
	private LatLng currentPt;
	private final static double PI = Math.PI;
	private final static double r = 6371.229;
	private Map<Integer, LatLng> map = new HashMap<Integer, LatLng>();
	private static int ind = 0;
	private ImageView back;
	private double dis[] = { 0.06456, 0.25032, 4.30667, 4.98820, 3.77589,
			3.78765, 4.29949, 3.96496, 4.11127, 0.20261 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(com.holawith.Mychatroom.R.layout.map);
		LayoutInflater inflater = LayoutInflater.from(this);

		mBaiduMap = ((SupportMapFragment) (getSupportFragmentManager()
				.findFragmentById(R.id.map1))).getBaiduMap();
		mPoiSearch = PoiSearch.newInstance();
		mPoiSearch.setOnGetPoiSearchResultListener(this);
		mSuggestionSearch = SuggestionSearch.newInstance();
		mSuggestionSearch.setOnGetSuggestionResultListener(this);
		keyWorldsView = (AutoCompleteTextView) findViewById(R.id.search_detail);
		cityview = (AutoCompleteTextView) findViewById(R.id.search_province);
		sugAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line);
		back = (ImageView) findViewById(R.id.maparrow);
		back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finish();
			}
		});
		keyWorldsView.setAdapter(sugAdapter);
		cityview.setAdapter(sugAdapter);
		/*
		 * Intent intent = getIntent(); if (intent.hasExtra("x") &&
		 * intent.hasExtra("y")) {
		 * 
		 * Bundle b = intent.getExtras(); LatLng p = new
		 * LatLng(b.getDouble("y"), b.getDouble("x")); mMapView = new
		 * MapView(this, new BaiduMapOptions().mapStatus(new MapStatus.Builder()
		 * .target(p).build())); } else { mMapView = new MapView(this, new
		 * BaiduMapOptions()); } setContentView(mMapView); mBaiduMap =
		 * mMapView.getMap();
		 */
		initlistener();
		findViewById(R.id.mapban).bringToFront();
		findViewById(R.id.maparrow).bringToFront();
		findViewById(R.id.maptext).bringToFront();
		findViewById(R.id.searchbanlayout).bringToFront();
		findViewById(R.id.search_province).bringToFront();
		findViewById(R.id.search_detail).bringToFront();
		findViewById(R.id.map_next_data).bringToFront();
		findViewById(R.id.tbtnlayout).bringToFront();
		keyWorldsView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {

			}

			@Override
			public void onTextChanged(CharSequence cs, int arg1, int arg2,
					int arg3) {
				if (cs.length() <= 0) {
					return;
				}
				String city = cityview.getText().toString();

				mSuggestionSearch
						.requestSuggestion((new SuggestionSearchOption())
								.keyword(cs.toString()).city(city));
			}
		});
	}

	public void initlistener() {
		mBaiduMap.setOnMapClickListener(new OnMapClickListener() {
			public void onMapClick(LatLng point) {
				touchType = "单击";
				currentPt = point;
				updateMapState();
			}

			public boolean onMapPoiClick(MapPoi poi) {
				return false;
			}
		});
		mBaiduMap.setOnMapLongClickListener(new OnMapLongClickListener() {
			public void onMapLongClick(LatLng point) {
				touchType = "长按";

				currentPt = point;
				updateMapState();
			}
		});
		mBaiduMap.setOnMapDoubleClickListener(new OnMapDoubleClickListener() {
			public void onMapDoubleClick(LatLng point) {
				touchType = "双击";
				currentPt = point;
				updateMapState();
			}
		});
		mBaiduMap.setOnMapStatusChangeListener(new OnMapStatusChangeListener() {
			public void onMapStatusChangeStart(MapStatus status) {
				updateMapState();
			}

			public void onMapStatusChangeFinish(MapStatus status) {
				updateMapState();
			}

			public void onMapStatusChange(MapStatus status) {
				updateMapState();
			}
		});
	}

	// mWebView = (WebView) findViewById(R.id.webview);
	// WebSettings setting=mWebView.getSettings();

	// setting.setPluginState(PluginState.ON);
	// setting.setJavaScriptEnabled(true);
	/*
	 * if(check()){
	 * 
	 * }else{ install(); }
	 */

	private void updateMapState() {

		String state = "";
		if (currentPt == null) {
			state = "点击、长按、双击地图以获取经纬度和地图状态";
		} else {
			state = String.format(touchType + ",当前经度： %f 当前纬度：%f",
					currentPt.longitude, currentPt.latitude);
			map.put(ind, currentPt);
			Log.e("success", String.valueOf(currentPt.longitude));
		}
		state += "\n";
		MapStatus ms = mBaiduMap.getMapStatus();
		state += String.format("zoom=%.1f rotate=%d overlook=%d", ms.zoom,
				(int) ms.rotate, (int) ms.overlook);
		// mStateBar.setText(state);
	}

	protected boolean isRouteDisplayed() {
		return false;
	}

	public void onResume() {
		super.onResume();

	}

	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
	}

	public void onAttach(Activity activity) {
	}

	public void onDestroy() {
		mPoiSearch.destroy();
		mSuggestionSearch.destroy();
		super.onDestroy();
	}

	public void searchButtonProcess(View v) {
		EditText editSearchKey = (EditText) findViewById(R.id.search_province);
		EditText editSearchdetail = (EditText) findViewById(R.id.search_detail);
		mPoiSearch.searchInCity((new PoiCitySearchOption())
				.city(editSearchKey.getText().toString())
				.keyword(editSearchdetail.getText().toString())
				.pageNum(load_Index));
	}

	public void goToNextPage(View v) {
		load_Index++;

		searchButtonProcess(null);
	}

	public void onGetPoiResult(PoiResult result) {
		if (result == null
				|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
			return;
		}
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			mBaiduMap.clear();
			PoiOverlay overlay = new MyPoiOverlay(mBaiduMap);
			mBaiduMap.setOnMarkerClickListener(overlay);
			overlay.setData(result);
			overlay.addToMap();
			overlay.zoomToSpan();
			return;
		}
		if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {

			// 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
			String strInfo = "在";
			for (CityInfo cityInfo : result.getSuggestCityList()) {
				strInfo += cityInfo.city;
				strInfo += ",";
			}
			strInfo += "找到结果";
			Toast.makeText(MapActivity.this, strInfo, Toast.LENGTH_LONG).show();
		}
	}

	public void onGetPoiDetailResult(PoiDetailResult result) {
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(MapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
					.show();
		} else {
			Toast.makeText(MapActivity.this,
					result.getName() + ": " + result.getAddress(),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onGetSuggestionResult(SuggestionResult res) {
		if (res == null || res.getAllSuggestions() == null) {
			return;
		}
		sugAdapter.clear();
		for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
			if (info.key != null)
				sugAdapter.add(info.key);
		}
		sugAdapter.notifyDataSetChanged();
	}

	private class MyPoiOverlay extends PoiOverlay {

		public MyPoiOverlay(BaiduMap baiduMap) {
			super(baiduMap);
		}

		@Override
		public boolean onPoiClick(int index) {
			super.onPoiClick(index);
			Log.e("index", String.valueOf(index));
			LatLng pt = currentPt;
			ind = index;
			// Log.e("pt",String.valueOf(currentPt.longitude)+","+String.valueOf(currentPt.latitude));
			PoiInfo poi = getPoiResult().getAllPoi().get(index);
			// if (poi.hasCaterDetails) {
			mPoiSearch.searchPoiDetail((new PoiDetailSearchOption())
					.poiUid(poi.uid));
			// }
			return true;
		}
	}

	public double getDistance(double longt1, double lat1, double longt2,
			double lat2) {

		double x, y, distance;

		x = (longt2 - longt1) * PI * r
				* Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;

		y = (lat2 - lat1) * PI * r / 180;

		distance = Math.hypot(x, y);

		return distance;

	}
	/*
	 * private class AndroidBridge { public void goMarket() { handler.post(new
	 * Runnable() { public void run() { Intent installIntent = new Intent(
	 * "android.intent.action.VIEW"); installIntent.setData(Uri
	 * .parse("market://details?id=com.adobe.flashplayer"));
	 * startActivity(installIntent); } }); } } private void install() {
	 * mWebView.addJavascriptInterface(new AndroidBridge(), "android");
	 * mWebView.loadUrl("http://172.18.183.118/map");
	 * mWebView.setWebViewClient(new WebViewClientDemo()); } private class
	 * WebViewClientDemo extends WebViewClient {
	 * 
	 * public boolean shouldOverrideUrlLoading(WebView view, String url) {
	 * view.loadUrl(url); return true; } }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * private boolean check() { PackageManager pm = getPackageManager();
	 * List<PackageInfo> infoList = pm
	 * .getInstalledPackages(PackageManager.GET_SERVICES); for (PackageInfo info
	 * : infoList) { if ("com.adobe.flashplayer".equals(info.packageName)) {
	 * return true; } } return false; }
	 */
}