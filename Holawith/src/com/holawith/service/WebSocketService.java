package com.holawith.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class WebSocketService extends Service {
	
	private MyBinder binder = new MyBinder();

	public class MyBinder extends Binder {
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

}
