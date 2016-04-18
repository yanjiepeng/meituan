package com.example.adapter;

import com.example.mymeituan.R;

import android.content.Context;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OrderStateAdapter extends BaseAdapter {
	Context context;
	private int state = 4;
	boolean isCancle = false;
	boolean isPayed = true;
	LayoutInflater inflater;

	public OrderStateAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
	}

	public void setState(int state) {
		this.state = state;
	}

	public void isCancle(boolean cancle) {
		this.isCancle = cancle;
	}

	public void isPayed(boolean isPayed) {
		this.isPayed = isPayed;
	}

	@Override
	public int getCount() {
		if (isCancle) {
			int temp = state + 1;
			return temp;
		}
		return state;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			view = inflater.inflate(R.layout.item_order_state, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.order_state = (TextView) view
					.findViewById(R.id.tv_order_item_state);
			viewHolder.order_time = (TextView) view
					.findViewById(R.id.tv_order_item_time);
			viewHolder.order_remarks = (TextView) view
					.findViewById(R.id.tv_order_item_remarks);
			viewHolder.v = view.findViewById(R.id.v_order_state);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		Time t = new Time(); // or Time t=new Time("GMT+8"); ����Time Zone���ϡ�
		t.setToNow(); // 
		int month = t.month;
		int date = t.monthDay;
		int hour = t.hour; // 0-23
		int minute = t.minute;
		viewHolder.order_time.setText(month + "月" + date + "日   " + hour
				+ ":" + minute);
		initItem(position,viewHolder);
		return view;
	}

	private void initItem(int position,ViewHolder viewHolder) {
		switch (position) {
		case 0:
			viewHolder.order_state.setText(R.string.order_state_isCommit);
			viewHolder.order_remarks
					.setText(R.string.order_state_isCommit_remarks);
			viewHolder.v.setVisibility(View.INVISIBLE);
			break;
		case 1:
			if (isPayed) {
				viewHolder.order_state
						.setText(R.string.order_state_isPayed);
				viewHolder.order_remarks
						.setText(R.string.order_state_isPayed_remarks);
			} else {
				viewHolder.order_state
						.setText(R.string.order_state_notPayed);
				viewHolder.order_remarks
						.setText(R.string.order_state_notPayed_remarks);
			}
			break;
		case 2:
			if (isCancle) {
				viewHolder.order_state
						.setText(R.string.order_state_isCancled);
				viewHolder.order_remarks
						.setText(R.string.order_state_isCancled_remarks);
			} else {
				viewHolder.order_state
						.setText(R.string.order_state_isAccepted);
				viewHolder.order_remarks
						.setText(R.string.order_state_isAccepted_remarks);
			}
			break;
		case 3:
			if (isCancle) {
				viewHolder.order_state
						.setText(R.string.order_state_isCancled);
				viewHolder.order_remarks
						.setText(R.string.order_state_isCancled_remarks);
			} else {
				viewHolder.order_state
						.setText(R.string.order_state_isCompleted);
				viewHolder.order_remarks
						.setText(R.string.order_state_isCompleted_remarks);
			}
			break;

		default:
			break;
		}
	}

	class ViewHolder {
		TextView order_state, order_time, order_remarks;
		View v;
	}
}
