package com.example.widget;

import com.example.mymeituan.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.Toast;

public class HttpSkipDialog extends Dialog {
	private ProgressBar pb_loading;
	Context context;
	private final int LOAD_SUCC = 1;
	private final int LOAD_FAIL = 2;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOAD_SUCC:
				dismiss();
				break;
			case LOAD_FAIL:
				dismiss();
				break;
			default:
				break;
			}
		};
	};

	public HttpSkipDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		this.context = context;
	}

	public HttpSkipDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	public HttpSkipDialog(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.commom_loading_layout);
		pb_loading = (ProgressBar) findViewById(R.id.pb_loading);
	}

	public void dimissSuc() {
		mHandler.sendEmptyMessageDelayed(LOAD_SUCC, 0);
	}

	public void dimissFail() {
		mHandler.sendEmptyMessageDelayed(LOAD_FAIL, 0);
		Toast.makeText(context, "请检查网络连接", Toast.LENGTH_LONG).show();
	}
}
