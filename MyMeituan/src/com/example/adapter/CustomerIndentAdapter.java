package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.application.MyApplication;
import com.example.fragment.CustomerIndentFragment;
import com.example.mymeituan.HomeActivity;
import com.example.mymeituan.R;
import com.example.tempactivity.OrderActivity;
import com.example.tempactivity.StoreActivity;
import com.example.util.UpData;
import com.example.widget.DeletDialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomerIndentAdapter extends BaseAdapter {
	public List<Map<String, Object>> indentData = new ArrayList<Map<String, Object>>();
	Context context;
	LayoutInflater inflater;
	public int state = CustomerIndentFragment.STATE_EDIE;
	int position;
	UpData upData;

	public CustomerIndentAdapter(List<Map<String, Object>> indentData,
			Context context) {
		this.context = context;
		this.indentData = indentData;
		this.inflater = LayoutInflater.from(context);
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public int getCount() {
		return indentData.size();
	}

	@Override
	public Object getItem(int position) {
		return indentData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view;
		if (convertView == null) {
			ViewHolder viewHolder = new ViewHolder();
			view = inflater.inflate(R.layout.item_customer_indent, null);
			viewHolder.v_item = view.findViewById(R.id.v_customer_indent_item);
			viewHolder.rl_onemore = (RelativeLayout) view
					.findViewById(R.id.rl_customer_indent_item_onemore);
			viewHolder.name = (TextView) view
					.findViewById(R.id.tv_customer_indent_item_name);
			viewHolder.state = (TextView) view
					.findViewById(R.id.tv_customer_indent_item_state);
			viewHolder.price = (TextView) view
					.findViewById(R.id.tv_customer_indent_item_price);
			viewHolder.time = (TextView) view
					.findViewById(R.id.tv_customer_indent_item_time);
			viewHolder.distribution = (TextView) view
					.findViewById(R.id.tv_customer_indent_item_distribution);
			viewHolder.icon = (ImageView) view
					.findViewById(R.id.iv_customer_indent_item_icon);
			viewHolder.title = (RelativeLayout) view
					.findViewById(R.id.rl_customer_indent_item_title);

			viewHolder.sketch = (RelativeLayout) view
					.findViewById(R.id.rl_customer_indent_item_sketch);
			viewHolder.onemore = (Button) view
					.findViewById(R.id.btn_customer_indent_item_onemore);

			viewHolder.delet = (Button) view
					.findViewById(R.id.btn_customer_indent_item_delet);
			viewHolder.v_listview_bottom = view
					.findViewById(R.id.v_listview_bottom);
			view.setTag(viewHolder);
		} else {
			view = convertView;
		}
		ViewHolder viewHolder = (ViewHolder) view.getTag();
		viewHolder.name.setText(indentData.get(position).get("s_name")
				.toString());
		int order_state = Integer.parseInt((String) indentData.get(position)
				.get("b_status"));
		switch (order_state) {
		case 1:
			viewHolder.state.setText(R.string.order_state_isCommit);
			break;
		case 2:
			viewHolder.state.setText(R.string.order_state_isPayed);
			break;
		case 3:
			viewHolder.state.setText(R.string.order_state_isAccepted);
			break;
		case 4:
			viewHolder.state.setText(R.string.order_state_isCompleted);
			break;

		default:
			break;
		}
		viewHolder.price.setText(indentData.get(position).get("b_price")
				.toString());
		viewHolder.time.setText(indentData.get(position).get("b_time")
				.toString());
		viewHolder.title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//Intent intent_store = new Intent(context, StoreActivity.class);
				//				int s_id = Integer.parseInt((String) indentData.get(position)
				//						.get("s_id"));
				//				intent_store.putExtra("s_id", s_id);
				//context.startActivity(intent_store);
			}
		});
		viewHolder.sketch.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_order = new Intent(context, OrderActivity.class);
				intent_order.putExtra("position", position);
				context.startActivity(intent_order);
			}
		});
		viewHolder.onemore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent_submitorder = new Intent(context,
						HomeActivity.class);
				int b_id = Integer.parseInt((String) indentData.get(position)
						.get("b_id"));
				intent_submitorder.putExtra("b_id", b_id);
				context.startActivity(intent_submitorder);
			}
		});
		viewHolder.delet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DeletDialog.Builder builder = new DeletDialog.Builder(context);
				String message = context.getString(R.string.delet_order_dialog);
				builder.setMessage(message);
				builder.setPositiveButton("删除",
						new DialogInterface.OnClickListener() {



					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						indentData.remove(position);
						deletData();
						CustomerIndentAdapter.this
						.notifyDataSetChanged();

						dialog.dismiss();
					}
				});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog,
							int which) {
						dialog.dismiss();
					}
				});
				builder.create().show();
			}
		});
		switch (state) {
		case CustomerIndentFragment.STATE_EDIE:
			viewHolder.delet.setVisibility(View.INVISIBLE);
			viewHolder.v_item.setVisibility(View.VISIBLE);
			viewHolder.rl_onemore.setVisibility(View.VISIBLE);
			break;
		case CustomerIndentFragment.STATE_CANCLE:
			viewHolder.delet.setVisibility(View.VISIBLE);
			viewHolder.v_item.setVisibility(View.GONE);
			viewHolder.rl_onemore.setVisibility(View.GONE);
		default:
			break;
		}
		if (position == indentData.size()) {
			viewHolder.v_listview_bottom.setVisibility(View.GONE);
		}
		return view;
	}

	public void deletData() {
		int b_id = Integer.parseInt((String) indentData
				.get(position).get("b_id"));
		upData = new UpData(context, MyApplication.getInstance().getU_id());
		upData.deletFromData(b_id);
	}
	class ViewHolder {
		TextView name, state, price, time, distribution;
		ImageView icon;
		RelativeLayout title, sketch;
		Button onemore, delet;
		View v_item, v_listview_bottom;
		RelativeLayout rl_onemore;

	}

}
