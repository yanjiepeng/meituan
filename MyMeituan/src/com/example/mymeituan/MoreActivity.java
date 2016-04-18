package com.example.mymeituan;

import java.io.File;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MoreActivity extends Activity implements OnClickListener {

	TextView tvCheckUpdata, tvShare;
	LinearLayout linearlayoutBar;
	String url = "http://yanjiepeng.eicp.net:24310/YueFanServer/login";
	private BroadcastReceiver receiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE); //关闭系统自带标题
		setContentView(R.layout.activity_more);

		tvCheckUpdata = (TextView) findViewById(R.id.tv_check_updata_more);
		tvShare = (TextView) findViewById(R.id.tv_share_more);

		tvCheckUpdata.setOnClickListener(this);
		tvShare.setOnClickListener(this);

		linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_more);
		linearlayoutBar.setOnClickListener(this);
		
		receiver = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				intent = new Intent(Intent.ACTION_VIEW);
	            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	            intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/Download/yuefan.apk")),
	                    "application/vnd.android.package-archive");
	            startActivity(intent);
	            
			}
		};
		
		 registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_check_updata_more:

			CheckUpdate();
			break;
		case R.id.tv_share_more:
			break;
		case R.id.linearlayout_activity_more:
			finish();
			break;
		default:
			break;
		}
	}

	
	/**
	 * 检查是否有更新可使用
	 */
	private void CheckUpdate() {
		// TODO Auto-generated method stub
		HttpUtils mHttpUtils = new HttpUtils();

		RequestParams params = new RequestParams();
		params.addQueryStringParameter("REQUEST_CODE", "999");

		mHttpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(MoreActivity.this, "检查更新失败", 0).show();
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						String res = arg0.result;
						if (res != null) {
							DownloadUpdate(res);
						}
					}
				});
	}

	private void DownloadUpdate(String res) {
		// TODO Auto-generated method stub
		DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
		DownloadManager.Request req = new Request(Uri.parse(res));
		// 设置下载路径和文件名

		req.setDestinationInExternalPublicDir("download", "yuefan.apk");

		req.setDescription("约饭新版本下载");
		// 仅wifi下载
		// req.setAllowedNetworkTypes(Request.NETWORK_WIFI);
		// 获得下载任务的唯一id

		req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

		req.setMimeType("application/vnd.android.package-archive");

		// 设置为可被媒体扫描器找到

		req.allowScanningByMediaScanner();

		// 设置为可见和可管理

		req.setVisibleInDownloadsUi(true);

		long refernece = dm.enqueue(req);
		
		   // 把当前下载的ID保存起来

        SharedPreferences sPreferences = getSharedPreferences("downloadyf", 0);

        sPreferences.edit().putLong("yf", refernece).commit();

      
		
	}
}
