package com.example.holawith;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.holawith.Mychatroom.R;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class SelectPicPopupWindow extends PopupWindow {


	private RelativeLayout btn_take_photo, btn_pick_photo, btn_cancel;
	public static Activity con;
	private View mMenuView;
	public Calendar calendar;
	public static String str;
	private File file;
	public ImageView ri;
	public Bitmap bmp;
	private String saveDir = Environment.getExternalStorageDirectory()
			.getPath() + "/tmp_image";
	public void init(){
	    
	}
	
	public SelectPicPopupWindow(Activity context,OnClickListener itemsOnClick) {
		super(context);
		con = context;
		init();
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mMenuView = inflater.inflate(R.layout.alert_dialog, null);
		btn_take_photo = (RelativeLayout) mMenuView.findViewById(R.id.btn_take_photo);
		btn_pick_photo = (RelativeLayout) mMenuView.findViewById(R.id.btn_pick_photo);
		btn_cancel = (RelativeLayout) mMenuView.findViewById(R.id.btn_cancel);
		ri = (ImageView)mMenuView.findViewById(R.id.workpic);
		//ȡ��ť
		btn_cancel.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				dismiss();
			}
		});
		
		btn_take_photo.setOnClickListener(itemsOnClick);
		btn_pick_photo.setOnClickListener(itemsOnClick);
		this.setContentView(mMenuView);
		this.setWidth(LayoutParams.MATCH_PARENT);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setAnimationStyle(R.style.AnimBottom);
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		this.setBackgroundDrawable(dw);
		mMenuView.setOnTouchListener(new OnTouchListener() {
			
			public boolean onTouch(View v, MotionEvent event) {
				
				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y=(int) event.getY();
				if(event.getAction()==MotionEvent.ACTION_UP){
					if(y<height){
						dismiss();
					}
				}				
				return true;
			}
		});

	}
	
}
