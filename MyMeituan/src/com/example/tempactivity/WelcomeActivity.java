package com.example.tempactivity;

import com.example.mymeituan.HomeActivity;
import com.example.mymeituan.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends Activity {
	private static int userState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		SharedPreferences sp = getSharedPreferences("version",
				Context.MODE_PRIVATE);
		userState = sp.getInt("userState", GuideActivity.CUSTOMER_STATE);
		new MyAsyncTask().execute();
	}

	public class MyAsyncTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			switch (WelcomeActivity.userState) {
			case GuideActivity.CUSTOMER_STATE:
				Intent intent_customer = new Intent(WelcomeActivity.this,
						HomeActivity.class);
				startActivity(intent_customer);
				finish();
				break;
			case GuideActivity.CELLER_STATE:
				Intent intent_celler = new Intent(WelcomeActivity.this,
						CellerActivity.class);
				startActivity(intent_celler);
				finish();
				break;
			default:
				break;
			}

		}

	}
}
