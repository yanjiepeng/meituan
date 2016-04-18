package com.example.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.application.MyApplication;
import com.example.mymeituan.HomeActivity;
import com.example.mymeituan.R;
import com.example.util.UpData;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class CustomerIndentListView extends ListView{
	View header;
	private float oldPositionY;
	private float result;
	HttpSkipDialog skipDialog;
	Context context;
	private static final int HEADER_POSITION = 0;
	private static final int HEADER_MAX = 1000;
	private static final int HEADER_ACTION = 600;
	public String request_code = "REQUEST_CODE";
	public String code_number = "105";
	List<Map<String, Object>> indentData;
	UpData upData;

	public CustomerIndentListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init(context);
		this.context = context;
	}

	public CustomerIndentListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
		this.context = context;
	}

	public CustomerIndentListView(Context context) {
		super(context);
		init(context);
		this.context = context;
	}

	private void init(Context context) {
		header = LayoutInflater.from(getContext()).inflate(
				R.layout.header_listview, this, false);
		header.setTag((TextView) header.findViewById(R.id.tv_listview_header));
		addHeaderView(header);
		indentData = new ArrayList<Map<String, Object>>();
		
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setSelection(1);
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldPositionY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (getFirstVisiblePosition() == HEADER_POSITION
					&& (ev.getY() - oldPositionY) >= 0) {
				float temp;
				temp = ev.getY() - oldPositionY;
				result = Math.abs(temp);

				if (result <= HEADER_MAX) {
					((TextView) header.getTag()).setHeight((int) result);
					((TextView) header.getTag()).setTextSize(result / 15);
				} else {
					((TextView) header.getTag()).setHeight(HEADER_MAX);
					((TextView) header.getTag()).setTextSize(HEADER_MAX / 15);
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (getFirstVisiblePosition() == HEADER_POSITION) {
				setSelection(1);
				upData = new UpData(context,MyApplication.getInstance().getU_id());
				if (result >= HEADER_ACTION) {
					upData.initHttp();
				}
				System.out.println("--------------------ACTION_UP"
						+ getFirstVisiblePosition());

			}
			break;
		default:
			break;
		}

		return super.onTouchEvent(ev);
	}

	

}
