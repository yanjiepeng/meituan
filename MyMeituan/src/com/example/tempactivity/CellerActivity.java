package com.example.tempactivity;

import java.util.List;

import com.example.bean.Comment;
import com.example.bean.CurrentFood;
import com.example.bean.Seller;
import com.example.fragment.CommantFragment;
import com.example.fragment.LoadFragment;
import com.example.fragment.MenuFragment;
import com.example.fragment.SellerFragment;
import com.example.mymeituan.R;
import com.example.util.MenuSave;
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
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CellerActivity extends FragmentActivity implements OnClickListener{
	private Button btn_menu, btn_comment, btn_info;
	private LinearLayout ll_content_container;
	private TextView tv_sellername;
    public int currentshowid;
    String currentshowName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_seller);
		
		
		currentshowid = getIntent().getIntExtra("s_id", 0);
		currentshowName= getIntent().getStringExtra("s_name");
		
		
		btn_menu = (Button) findViewById(R.id.btn_menu);
		btn_info = (Button) findViewById(R.id.btn_info);
		btn_comment = (Button) findViewById(R.id.btn_comment);
		ll_content_container = (LinearLayout) findViewById(R.id.ll_content);
		tv_sellername=(TextView) findViewById(R.id.tv_seller_name_main);
		tv_sellername.setText(currentshowName);
		
		btn_menu.setOnClickListener(this);
		btn_info.setOnClickListener(this);
		btn_comment.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_menu:

			FragmentManager manager = getSupportFragmentManager();
			FragmentTransaction transaction = manager.beginTransaction();
			transaction.add(R.id.ll_content, new LoadFragment());
			transaction.commit();
			getMenuData(currentshowid);

			break;
		case R.id.btn_comment:

			FragmentManager manager1 = getSupportFragmentManager();
			FragmentTransaction transaction1 = manager1.beginTransaction();
			transaction1.add(R.id.ll_content, new LoadFragment());
			transaction1.commit();
			getMeauComment(currentshowid);
			break;
		case R.id.btn_info:
			FragmentManager manager2= getSupportFragmentManager();
			FragmentTransaction transaction2 = manager2.beginTransaction();
			transaction2.add(R.id.ll_content, new LoadFragment());
			transaction2.commit();
			getSeller(currentshowid);
			break;

		}

	}
//商家信息
	private void getSeller(int sellerId){

		HttpUtils mUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("REQUEST_CODE", "104");
		params.addQueryStringParameter("s_id",sellerId + "");
		
		final Gson gson = new Gson();
		mUtils.send(HttpMethod.POST,
				"http://yanjiepeng.eicp.net:24310/YueFanServer/login", params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
					}
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.i("FSLog", "post failed " + arg0.toString());
					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Log.i("FSLog", arg0.result);
						MenuSave.s = gson.fromJson(arg0.result,Seller.class);
						FragmentManager manager = getSupportFragmentManager();
						FragmentTransaction transaction = manager
								.beginTransaction();
						transaction
								.replace(R.id.ll_content, new SellerFragment());
						transaction.commitAllowingStateLoss();

					}
				});
	
	}
	//评价
	private void getMeauComment(int sellerId){
		HttpUtils mUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("REQUEST_CODE", "107");
		params.addQueryStringParameter("s_id", sellerId + "");
		final Gson gson = new Gson();
		mUtils.send(HttpMethod.POST,
				"http://yanjiepeng.eicp.net:24310/YueFanServer/login", params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
					}
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}
					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Log.i("FSLog", "post failed " + arg0.toString());
					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Log.i("FSLog", arg0.result);
						for (int j = 0; j < MenuSave.commentdata.size(); j++) {
							MenuSave.commentdata.remove(j);
						}
						MenuSave.commentdata = gson.fromJson(arg0.result,
								new TypeToken<List<Comment>>() {
								}.getType());
						
						CommantFragment.setcommantAdapterSource(MenuSave.commentdata);
						FragmentManager manager = getSupportFragmentManager();
						FragmentTransaction transaction = manager
								.beginTransaction();
						transaction
								.replace(R.id.ll_content, new CommantFragment());
						transaction.commitAllowingStateLoss();

					}
				});
	}
	
	//菜单
	private void getMenuData(int sellerId) {
		// TODO Auto-generated method stub

		HttpUtils mUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("REQUEST_CODE", "103");
		params.addQueryStringParameter("s_id", sellerId + "");
		final Gson gson = new Gson();
		mUtils.send(HttpMethod.POST,
				"http://yanjiepeng.eicp.net:24310/YueFanServer/login", params,
				new RequestCallBack<String>() {
					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						// TODO Auto-generated method stub
						super.onLoading(total, current, isUploading);
					}
					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}
					@Override
					public void onFailure(HttpException arg0, String arg1) {
												Log.i("FSLog", "post failed " + arg0.toString());
					}
					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Log.i("FSLog", arg0.result);
						for (int j = 0; j < MenuSave.data.size(); j++) {
							MenuSave.data.remove(j);
						}
						MenuSave.data = gson.fromJson(arg0.result,
								new TypeToken<List<CurrentFood>>() {
								}.getType());
						Log.i("cos", MenuSave.data.size() + "");
						MenuFragment.setMenuAdapterSource(MenuSave.data);
						FragmentManager manager = getSupportFragmentManager();
						FragmentTransaction transaction = manager
								.beginTransaction();
						transaction
								.replace(R.id.ll_content, new MenuFragment());
						transaction.commitAllowingStateLoss();

					}
				});
	}
}
