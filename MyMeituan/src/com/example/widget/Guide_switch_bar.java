package com.example.widget;

import com.example.mymeituan.R;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class Guide_switch_bar extends RelativeLayout {
	View guide_switch_bar;
	Context context;
	

	public Guide_switch_bar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public Guide_switch_bar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public Guide_switch_bar(Context context) {
		super(context);
		init(context);
	}

	private void init(Context context) {
		this.context = context;
		guide_switch_bar = ((Activity) context).getLayoutInflater().inflate(
				R.layout.guide_switch_bar, null);
		this.addView(guide_switch_bar);
	}
}
