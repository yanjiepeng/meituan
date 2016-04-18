package com.example.mymeituan;

import com.example.fragment.RegisterCodeFragment;
import com.example.fragment.RegisterPasswordFragment;
import com.example.fragment.RegisterPhoneFragment;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;

/**
 * @author sujunhua 这是注册界面的activity，其中包含3个fragment
 * 
 */
public class RegisterActivity extends Activity implements OnClickListener {

	LinearLayout linearlayoutRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_register);
		init();

		// 注册事件监听器
		SMSSDK.registerEventHandler(mHandler1);

		// 初始的界面为获取验证码的fragment
		FragmentTransaction ts = getFragmentManager().beginTransaction();
		ts.add(R.id.linearlayout_activity_register, new RegisterPhoneFragment());
		ts.commit();

	}

	private void init() {
		// TODO Auto-generated method stub

		linearlayoutRegister = (LinearLayout) findViewById(R.id.linearlayout_activity_register_bar);
		linearlayoutRegister.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearlayout_activity_register_bar:
			finish();
			break;
		default:
			break;
		}
	}

	private EventHandler mHandler1 = new EventHandler() {

		@Override
		public void afterEvent(int arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
			super.afterEvent(arg0, arg1, arg2);
			switch (arg0) {
			// 获取到验证码后被回调
			case SMSSDK.EVENT_GET_VERIFICATION_CODE:

				if (arg1 == SMSSDK.RESULT_COMPLETE) {
					Log.i("FSLog",
							"注册：EVENT_GET_VERIFICATION_CODE RESULT_COMPLETE");
					// 跳转到提交验证码的fragment
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();

					ft.replace(R.id.linearlayout_activity_register,
							new RegisterCodeFragment());
					ft.commit();
				} else {
					Log.i("FSLog", "注册：EVENT_GET_VERIFICATION_CODE ERROR");
				}
				break;
			// 提交验证码后的回调，表明结果成功与否
			case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:

				if (arg1 == SMSSDK.RESULT_COMPLETE) {
					Log.i("FSLog", "注册：验证成功");
					// 验证成功后跳转到设置密码的fragment
					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();

					ft.replace(R.id.linearlayout_activity_register,
							new RegisterPasswordFragment());
					ft.commit();
				} else {
					Log.i("FSLog", "注册：验证失败");

				}

				break;
			default:
				break;
			}

		}

		@Override
		public void beforeEvent(int arg0, Object arg1) {
			// TODO Auto-generated method stub
			super.beforeEvent(arg0, arg1);

		}

		@Override
		public void onRegister() {
			// TODO Auto-generated method stub
			super.onRegister();
			Log.i("FSLog", "注册：onRegister");
		}

		@Override
		public void onUnregister() {
			// TODO Auto-generated method stub
			super.onUnregister();
			Log.i("FSLog", "注册：onUnregister");
		}

	};

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 注销事件监听器
		SMSSDK.unregisterAllEventHandler();
	}

}
