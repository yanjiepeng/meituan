package com.example.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.LocationClient;
import com.example.fragment.FirstPageFragment;
import com.example.mymeituan.R;
import com.example.tempactivity.CellerActivity;

public class FirstPageListItemOnClickLitener implements OnItemClickListener,
		OnClickListener {
	private Activity activity;

	public FirstPageListItemOnClickLitener(Context context,
			LocationClient mClient) {
		this.activity = (Activity) context;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {

		TextView tv_s_id = (TextView) view.findViewById(R.id.list_item_s_id);
		TextView tv_s_name = (TextView) view
				.findViewById(R.id.list_item_shopname);
		Intent intent = new Intent(activity,
				CellerActivity.class);
		intent.putExtra("s_id", Integer.parseInt(tv_s_id.getText().toString().trim()));
		intent.putExtra("s_name", tv_s_name.getText());
		activity.startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_restartlocation_main:
			Message msg = Message.obtain();
			msg.what = 0;
			FirstPageFragment.handler.sendMessage(msg);
			break;
		case R.id.ll_left_firstfoodrecommend_firstpager:
			Intent intent = new Intent(activity,
					CellerActivity.class);
			if (FirstPageFragment.rbData.size() == 0)
				return;
			intent.putExtra("s_id", FirstPageFragment.rbData.get(0));
			intent.putExtra("s_name", FirstPageFragment.rbSname.get(0));
			activity.startActivity(intent);
			break;
		case R.id.ll_left_secondfoodrecommend_firstpager:
			Intent intent1 = new Intent(activity,
					CellerActivity.class);

			if (FirstPageFragment.rbData.size() == 0)
				return;
			intent1.putExtra("s_id", FirstPageFragment.rbData.get(1));
			intent1.putExtra("s_name", FirstPageFragment.rbSname.get(1));
			activity.startActivity(intent1);
			break;
		case R.id.ll_left_thirdfoodrecommend_firstpager:
			Intent intent2 = new Intent(activity,
					CellerActivity.class);
			if (FirstPageFragment.rbData.size() == 0)
				return;
			intent2.putExtra("s_id", FirstPageFragment.rbData.get(2));
			intent2.putExtra("s_name", FirstPageFragment.rbSname.get(2));
			activity.startActivity(intent2);
			break;

		case R.id.ll_left_forthfoodrecommend_firstpager:
			Intent intent3 = new Intent(activity,
					CellerActivity.class);
			if (FirstPageFragment.rbData.size() == 0)
				return;
			intent3.putExtra("s_id", FirstPageFragment.rbData.get(3));
			intent3.putExtra("s_name", FirstPageFragment.rbSname.get(3));
			activity.startActivity(intent3);
			break;
		default:
			break;
		}

	}
}
