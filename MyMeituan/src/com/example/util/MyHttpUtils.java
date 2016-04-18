package com.example.util;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class MyHttpUtils{
	private String str;
	public String getNetwork(String url,RequestParams params){
		HttpUtils mHttpUtils=new HttpUtils();
		mHttpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
				str=arg1;
			}
			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				super.onStart();
			}
			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				str=arg0.result;
			}
		});
		return str;
	}
}
