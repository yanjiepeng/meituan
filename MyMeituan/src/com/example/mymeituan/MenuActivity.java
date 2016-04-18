package com.example.mymeituan;

import java.util.ArrayList;
import java.util.List;

import com.example.bean.Food;
import com.example.util.YFEncoder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MenuActivity extends Activity {

	public static String urlstring = "http://yanjiepeng.eicp.net:24310/YueFanServer/login";
	HttpUtils mHttpUtils = new HttpUtils();
	static List<Food> foodlist;
	static Gson gson;
	private TextView tv;
	 static boolean flag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_load);
		tv = (TextView) findViewById(R.id.textView1);

		//new GetMenuThread().start();


//		if (foodlist!=null) {
//			tv.setText(foodlist.get(1).getF_name());
//		}


	}



	class GetMenuThread extends Thread {


		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			/**
			 * 获取菜单信息
			 */

			
			RequestParams params = new RequestParams();
			params.addQueryStringParameter("REQUEST_CODE", "103");
			params.addQueryStringParameter("s_id", YFEncoder.EncodeToUTF("1"));
			while(flag){
				mHttpUtils.send(HttpMethod.POST, urlstring, params,
						new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.i("tag", "failed" + arg0.toString());
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub

						flag = false;
						Log.i("FSLog", arg0.result);
						foodlist = gson.fromJson(arg0.result,
								new TypeToken<List<Food>>() {
						}.getType());

						Log.i("FSLog", "post success");
					}
				});	
			}
		}
	}

	
	/*
	public List<Food> getMenuInfo() {
		final Gson gson = new Gson();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("REQUEST_CODE", "103");
		params.addQueryStringParameter("s_id", YFEncoder.EncodeToUTF("1"));
		mHttpUtils.send(HttpMethod.POST, urlstring, params,
				new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.i("tag", "failed" + arg0.toString());
			}

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub

				Log.i("FSLog", arg0.result);
				foodlist = gson.fromJson(arg0.result,
						new TypeToken<List<Food>>() {
				}.getType());

				Log.i("FSLog", "post success");
			}
		});

		return foodlist;

	}
	 */
}
