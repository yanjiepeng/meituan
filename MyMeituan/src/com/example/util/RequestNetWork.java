package com.example.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.bean.SellerJavaBean;
import com.example.data.FirstFragmentSQLite;
import com.example.fragment.FirstPageFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class RequestNetWork {

	private static List<SellerJavaBean> sellerInfoList;
	private static String sellerInfo;
	private static String currentSellerInfo;
	private static Activity activity;
	private Gson gson;
	private ContentValues values = new ContentValues();
	private static HttpUtils utils;
	private static RequestParams request;

	public RequestNetWork(Context context) {
		gson = new Gson();

		this.activity = (Activity) context;
		sellerInfoList = new ArrayList<SellerJavaBean>();
		getSDB();
		utils = new HttpUtils();
		request = new RequestParams();

		getDataFromNetWork();

	}

	public static void getDataFromNetWork() {
		request.addQueryStringParameter("REQUEST_CODE", "102");
		utils.send(HttpMethod.POST,
				"http://yanjiepeng.eicp.net:24310/YueFanServer/login", request,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(activity, "请求失败+1", 2000).show();
						queryCursor();
						Message msg = Message.obtain();
						msg.what = 2;
						msg.obj = sellerInfoList;
						FirstPageFragment.handler.sendMessage(msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

						sellerInfo = arg0.result;
						Log.i("yjlog", "网络请求listitem------------" + arg0.result);
						if (sellerInfo.equals(currentSellerInfo)) {
							Toast.makeText(activity, "没有更多的数据了，敬请期待", 2000)
									.show();
							return;
						}
						currentSellerInfo = arg0.result;
						Log.i("yjlog",
								"网络请求listitem------currentSellerInfo------"
										+ currentSellerInfo);
						Message msg = Message.obtain();
						msg.what = 1;
						FirstPageFragment.handler.sendMessage(msg);
					}
				});
	}

	public List<SellerJavaBean> sellerInfo() {
		RequestNetWork.getSDB().delete("firstfragmentlistview", null, null);
		// Type type = new TypeToken<ArrayList<SellerJavaBean>>() {
		// }.getType();
		
		sellerInfoList = gson.fromJson(sellerInfo,
				new TypeToken<List<SellerJavaBean>>() {
				}.getType());

		for (int i = 0; i < sellerInfoList.size(); i++) {
			values.put("s_id", sellerInfoList.get(i).getS_id());
			values.put("s_name", sellerInfoList.get(i).getS_name());
			values.put("s_address", sellerInfoList.get(i).getS_address());
			values.put("s_starting", sellerInfoList.get(i).getS_starting());
			values.put("listviewpic", sellerInfoList.get(i).getS_sellerpic());
			RequestNetWork.getSDB().insert("firstfragmentlistview", null,
					values);
			values.clear();
			Log.i("yjlog", i + "");
		}

		return sellerInfoList;
	}

	public static void queryCursor() {
		if (sellerInfoList != null) {
			sellerInfoList.clear();
		}
		Cursor cursor = RequestNetWork.getSDB().query(
				"firstfragmentlistview",
				new String[] { "s_id", "s_name", "s_address", "s_starting",
						"listviewpic" }, null, null, null, null, null);
		while (cursor.moveToNext()) {
			String s_id = cursor.getString(cursor.getColumnIndex("s_id"));
			String s_name = cursor.getString(cursor.getColumnIndex("s_name"));
			String s_address = cursor.getString(cursor
					.getColumnIndex("s_address"));
			String s_starting = cursor.getString(cursor
					.getColumnIndex("s_starting"));
			byte[] listviewpic = cursor.getBlob(cursor
					.getColumnIndex("listviewpic"));
			SellerJavaBean sjb = new SellerJavaBean(s_id, s_name, s_address,
					s_starting, listviewpic);
			sellerInfoList.add(sjb);
			sjb.getS_sellerpic();
		}
		cursor.close();
		RequestNetWork.getSDB().close();
	}

	public static SQLiteDatabase getSDB() {
		FirstFragmentSQLite sqlite = new FirstFragmentSQLite(activity,
				"firstfragment.db", null, 1);
		SQLiteDatabase db = sqlite.getReadableDatabase();
		return db;
	}
}
