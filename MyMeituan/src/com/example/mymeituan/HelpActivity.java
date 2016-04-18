package com.example.mymeituan;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HelpActivity extends Activity implements OnClickListener {

	TextView tvService, tvSuggest, tvQuestion;
	LinearLayout linearlayoutBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_help);
		
		init();
	}

	private void init() {
		// TODO Auto-generated method stub

		tvService = (TextView) findViewById(R.id.tv_service_activity_help);
		tvSuggest = (TextView) findViewById(R.id.tv_suggest_activity_help);
		tvQuestion = (TextView) findViewById(R.id.tv_question_activity_help);

		tvService.setOnClickListener(this);
		tvSuggest.setOnClickListener(this);
		tvQuestion.setOnClickListener(this);
		
		linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_help);
		linearlayoutBar.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_service_activity_help:
			break;
		case R.id.tv_suggest_activity_help:
			break;
		case R.id.tv_question_activity_help:
			break;
		case R.id.linearlayout_activity_help:
			finish();
			break;
		default:
			break;
		}
	}
}
