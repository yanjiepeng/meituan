package com.example.service;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class YFService extends Service {
	private Context context;
	private String imageJson;
	public static String imageUri[] = new String[] {
			"http://g.hiphotos.baidu.com/image/pic/item/58ee3d6d55fbb2fb45c241fa4b4a20a44723dc68.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/18d8bc3eb13533fafc868bc6acd3fd1f41345b21.jpg",
			"http://g.hiphotos.baidu.com/image/pic/item/4034970a304e251f13c4fc10a386c9177f3e5322.jpg",
			"http://d.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee931017f129f5e0fe99257e23.jpg",
			"http://img5q.duitang.com/uploads/item/201503/21/20150321143833_FzJZy.thumb.700_0.jpeg" 
	};

	@Override
	public IBinder onBind(Intent arg0) {

		return null;
	}

	@Override
	public void onCreate() {
		this.context = getApplicationContext();
		super.onCreate();
	}

	/**
	 * 
	 * 发送post请求服务端，得到viewpager需要的适配图片
	 * */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		HttpUtils utils = new HttpUtils();
		RequestParams request = new RequestParams();

		request.addQueryStringParameter("REQUEST_CODE", "109");
		utils.send(HttpMethod.POST,
				"http://yanjiepeng.eicp.net:24310/YueFanServer/login", request,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(context, "请求失败", Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						Toast.makeText(context, "请求陈功", Toast.LENGTH_SHORT).show();
						Log.i("yjlog", arg0.result);
						imageJson = arg0.result;
						imageJson();
					}
				});
		return super.onStartCommand(intent, flags, startId);
	}

	private void imageJson() {
		JSONObject jo;
		try {
			jo = new JSONObject(imageJson);
			System.out.println(jo.length());
			for (int i = 0; i < jo.length(); i++) {
				String uri = jo.getString("img" + (i + 1));
				imageUri[i] = uri;
				Log.i("yjlog", uri);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
