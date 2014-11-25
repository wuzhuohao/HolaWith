package com.holawith.common;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.holawith.basic.GLOBAL;
import com.network.holawith.Transfer;

public class AsyncSendData extends
		AsyncTask<HashMap<String, Object>, String, String> {
	
	private ProgressDialog pDialog;
	private Context context;
	
	public AsyncSendData(Context ctx){
		context = ctx;
	}

	@Override
	protected void onPreExecute() {
		pDialog = new ProgressDialog(context);
		pDialog.setMessage("正在发布活动");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
		super.onPreExecute();
	}

	@Override
	protected String doInBackground(HashMap<String, Object>... params) {
		String result = "";
		HashMap<String, Object> param = params[0];
		switch ((String) (param.get(GLOBAL.TAG_FOR_ACTION))) {
		case GLOBAL.URL_FOR_ACTIVITY_CREATE: {
			result = Transfer.CreateAction(param);
		}
			break;

		default:
			break;
		}
		return result;
	}
	
	@Override
	protected void onPostExecute(String result) {
		pDialog.dismiss();
		super.onPostExecute(result);
	}
}
