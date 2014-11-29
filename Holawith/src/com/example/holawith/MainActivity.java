package com.example.holawith;

import java.util.ArrayList;
import java.util.List;

import com.holawith.Mychatroom.R;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	List<View> listViews;

	Context context = null;

	LocalActivityManager manager = null;

	TabHost tabHost = null;

	private ViewPager pager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);
		
		context = MainActivity.this;
		
		pager  = (ViewPager) findViewById(R.id.viewpager);
		
		//定放一个放view的list，用于存放viewPager用到的view
		listViews = new ArrayList<View>();
		
		manager = new LocalActivityManager(this, true);
		manager.dispatchCreate(savedInstanceState);
		
		Intent i1 = new Intent(context, ActionActivity.class);
		listViews.add(getView("A", i1));
		Intent i2 = new Intent(context,CommunityActivity.class );
		listViews.add(getView("B", i2));
		Intent i3 = new Intent(context, Friendcirle.class);
		listViews.add(getView("C", i3));
        Intent i4 = new Intent(context,Myinfo.class);
        listViews.add(getView("D", i4));
		tabHost = (TabHost) findViewById(R.id.tabhost);
		tabHost.setup();
		tabHost.setup(manager);
		
		
		//这儿主要是自定义一下tabhost中的tab的样式
		RelativeLayout tabIndicator1 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabwidget, null);  
		ImageView img1 = (ImageView)tabIndicator1.findViewById(R.id.iv_mark);
		img1.setBackgroundResource(R.drawable.actiontabbg);
		TextView tvTab1 = (TextView)tabIndicator1.findViewById(R.id.tv_title);
		tvTab1.setText("活动");
		tvTab1.setTextColor(R.drawable.textview_selector);
		RelativeLayout tabIndicator2 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabwidget,null);  
		ImageView img2 = (ImageView)tabIndicator2.findViewById(R.id.iv_mark);
		img2.setBackgroundResource(R.drawable.community);
		TextView tvTab2 = (TextView)tabIndicator2.findViewById(R.id.tv_title);
		tvTab2.setText("社区");
		tvTab2.setTextColor(R.drawable.textview_selector);
		RelativeLayout tabIndicator3 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabwidget,null);  
		ImageView img3 = (ImageView)tabIndicator3.findViewById(R.id.iv_mark);
		img3.setBackgroundResource(R.drawable.friendcircle);
		TextView tvTab3 = (TextView)tabIndicator3.findViewById(R.id.tv_title);
		tvTab3.setText("朋友圈");
		tvTab3.setTextColor(R.drawable.textview_selector);
		RelativeLayout tabIndicator4 = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.tabwidget,null);  
		ImageView img4 = (ImageView)tabIndicator4.findViewById(R.id.iv_mark);
		img4.setBackgroundResource(R.drawable.aboutme);
		TextView tvTab4 = (TextView)tabIndicator4.findViewById(R.id.tv_title);
		tvTab4.setText("我");
		tvTab4.setTextColor(R.drawable.textview_selector);
		Intent intent = new Intent(context,EmptyActivity.class);
		//注意这儿Intent中的activity不能是自身 比如“A”对应的是T1Activity，后面intent就new的T3Activity的。
		tabHost.addTab(tabHost.newTabSpec("A").setIndicator(tabIndicator1).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("B").setIndicator(tabIndicator2).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("C").setIndicator(tabIndicator3).setContent(intent));
		tabHost.addTab(tabHost.newTabSpec("D").setIndicator(tabIndicator4).setContent(intent));
		
		pager .setAdapter(new MyPageAdapter(listViews));
		pager .setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				//当viewPager发生改变时，同时改变tabhost上面的currentTab
				tabHost.setCurrentTab(position);
			}
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		
		
	 //点击tabhost中的tab时，要切换下面的viewPager
	 tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
            	
            	if ("A".equals(tabId)) {
                    pager.setCurrentItem(0);
                } 
                if ("B".equals(tabId)) {
                	
                    pager.setCurrentItem(1);
                } 
                if ("C".equals(tabId)) {
                    pager.setCurrentItem(2);
                } 
                if("D".equals(tabId))  {
                	pager.setCurrentItem(3);
                }
            }
        });
	
		
		
	}

	private View getView(String id, Intent intent) {
		return manager.startActivity(id, intent).getDecorView();
	}

	private class MyPageAdapter extends PagerAdapter {
		
		private List<View> list;

		private MyPageAdapter(List<View> list) {
			this.list = list;
		}

		@Override
        public void destroyItem(View view, int position, Object arg2) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.removeView(list.get(position));
        }

        @Override
        public void finishUpdate(View arg0) {
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object instantiateItem(View view, int position) {
            ViewPager pViewPager = ((ViewPager) view);
            pViewPager.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

	}

}
