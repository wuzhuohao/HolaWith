package com.holawith.common;

import java.lang.ref.WeakReference;

import android.os.Handler;
import android.os.Message;

public class MyHandler extends Handler {

	private WeakReference<IHandlerCallback> callback;

	public MyHandler(IHandlerCallback ihc) {
		callback = new WeakReference<IHandlerCallback>(ihc);
	}
	
	@Override
	public void handleMessage(Message msg) {
		callback.get().handleMessage(msg);
	}
}
