package com.example.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.BaseAdapter;
import com.example.adapter.CustomerIndentAdapter;
import com.example.application.MyApplication;
import com.example.mymeituan.HomeActivity;
import com.example.mymeituan.R;
import com.example.widget.HttpSkipDialog;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class UpData {
	public String request_code = "REQUEST_CODE";
	public String code_number = "105";
	public int currentID= 0;
	HttpSkipDialog skipDialog;
	
	Context context;
	static List<Map<String, Object>> indentData;
	BaseAdapter adapter;
	public UpData(Context context,int id) {
		this.context = context;
		adapter = new CustomerIndentAdapter(indentData, context);
		this.currentID = id;
		
	}

	public void initHttp() {
		
		RequestParams params = new RequestParams();
		int requestID = currentID;
		params.addQueryStringParameter(request_code, code_number);
		params.addQueryStringParameter("u_id",requestID+"");
		HttpUtils http = new HttpUtils();
		String url = "http://yanjiepeng.eicp.net:24310/YueFanServer/login";

		http.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {
			Intent intent = new Intent();
			
			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				super.onLoading(total, current, isUploading);
			}

			@Override
			public void onStart() {
				skipDialog = new HttpSkipDialog(context, R.style.myDialogTheme2);
				skipDialog.show();
				super.onStart();
			}

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				skipDialog.dimissFail();
				intent.setAction("UpData.info");
				context.sendOrderedBroadcast(intent, null);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				skipDialog.dimissSuc();
				indentData = new ArrayList<Map<String, Object>>();
				//Log.i("time", arg0.result);
				String str = arg0.result;
				String b_id, u_name, b_status, u_tele, u_address, f_name, b_price, b_time, s_name;
				try {
					JSONArray ja = new JSONArray(str);
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jo = (JSONObject) ja.get(i);
						b_id = jo.getString("b_id");
						u_name = jo.getString("u_name");
						u_address = jo.getString("u_address");
						b_price = jo.getString("b_price");
						b_status = jo.getString("b_status");
						f_name = jo.getString("f_name");
						b_time = jo.getString("b_time");
						u_tele = jo.getString("u_tele");
						s_name = jo.getString("s_name");
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("b_id", b_id);
						map.put("u_name", u_name);
						map.put("u_address", u_address);
						map.put("b_price", b_price);
						map.put("b_status", b_status);
						map.put("f_name", f_name);
						map.put("b_time", b_time);
						map.put("u_tele", u_tele);
						map.put("s_name", s_name);
						indentData.add(map);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				insertToData(indentData);
				
				intent.setAction("UpData.info");
				context.sendOrderedBroadcast(intent, null);
				
			}
		});
	}

	public SQLiteDatabase getDB() {
		
		MySqliteOpenHelper helper = new MySqliteOpenHelper(context, "data.db",
				null, 1);
		SQLiteDatabase db = helper.getReadableDatabase();
		return db;
	}

	public void insertToData(List<Map<String, Object>> indentData) {
		SQLiteDatabase db = this.getDB();
		db.delete("data_customer", "_id>?", new String[]{"0"});
		
		for (int i = indentData.size()-1; i > 0; i--) {
			ContentValues values = new ContentValues();
			values.put("b_id", (String) indentData.get(i).get("b_id"));
			values.put("u_name", (String) indentData.get(i).get("u_name"));
			values.put("u_address", (String) indentData.get(i).get("u_address"));
			values.put("b_price", (String) indentData.get(i).get("b_price"));
			values.put("b_status", (String) indentData.get(i).get("b_status"));
			values.put("f_name", (String) indentData.get(i).get("f_name"));
			values.put("b_time", (String) indentData.get(i).get("b_time"));
			values.put("u_tele", (String) indentData.get(i).get("u_tele"));
			values.put("s_name", (String) indentData.get(i).get("s_name"));
			db.insert("data_customer", null, values);
		}
		
		db.close();
	}

	public List<Map<String, Object>> queryStu() {
		SQLiteDatabase db = this.getDB();// x-utils
		List<Map<String, Object>> tempData = new ArrayList<Map<String, Object>>();
		Cursor c = db.query("data_customer", new String[] { "b_id", "u_name",
				"u_address", "b_price", "b_status", "f_name", "b_time",
				"u_tele", "s_name" }, "_id>?", new String[] { "0" }, null,
				null, null);
		while (c.moveToNext()) {
			String b_id = c.getString(c.getColumnIndex("b_id"));
			String u_name = c.getString(c.getColumnIndex("u_name"));
			String u_address = c.getString(c.getColumnIndex("u_address"));
			String b_price = c.getString(c.getColumnIndex("b_price"));
			String b_status = c.getString(c.getColumnIndex("b_status"));
			String f_name = c.getString(c.getColumnIndex("f_name"));
			String b_time = c.getString(c.getColumnIndex("b_time"));
			String u_tele = c.getString(c.getColumnIndex("u_tele"));
			String s_name = c.getString(c.getColumnIndex("s_name"));
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("b_id", b_id);
			map.put("u_name", u_name);
			map.put("u_address", u_address);
			map.put("b_price", b_price);
			map.put("b_status", b_status);
			map.put("f_name", f_name);
			map.put("b_time", b_time);
			map.put("u_tele", u_tele);
			map.put("s_name", s_name);
			tempData.add(map);
		}
		db.close();
		return tempData;
	}
	public void deletFromData(int b_id) {
		SQLiteDatabase db = this.getDB();
		db.delete("data_customer", "b_id=?", new String[] { "b_id" });
		db.close();
	}
}
