package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.adapter.MyAdapter;
import com.example.application.MyApplication;
import com.example.bean.CurrentFood;
import com.example.mymeituan.AddressActivity;
import com.example.mymeituan.LoginActivity;
import com.example.mymeituan.R;
import com.example.util.MenuSave;
import com.example.util.YFEncoder;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MenuFragment extends Fragment{

	private static ListView lv_menu;
	List<Map<String, Object>> data1;
	@Override
	public View onCreateView(LayoutInflater inflater,
			ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.menulist,container,false);
		lv_menu = (ListView) v.findViewById(R.id.lv_menu);

		data1 = setMenuAdapterSource(MenuSave.data);
//		Log.i("bt",s.length+"");
		MyAdapter adapter = new MyAdapter(getActivity(), data1);
		lv_menu.setAdapter(adapter);
		lv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final int index = position;
				Dialog purcharseDialog =  new AlertDialog.Builder(getActivity()).setTitle("选择操作").setMessage("购买").setIcon(R.drawable.goumai).setPositiveButton("购买", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						purchaseFood((Integer) data1.get(index).get("f_id"),(Integer) data1.get(index).get("price"));
					}


				}).setNegativeButton("取消", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

					}
				}).create();
				purcharseDialog.show();


			}



		});
		return v;
	}
	private void purchaseFood(int id,int price) {
		// TODO Auto-generated method stub
		HttpUtils mHttpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addQueryStringParameter("REQUEST_CODE", "300");
		int uid = MyApplication.getInstance().getU_id();
		String uaddress = MyApplication.getInstance().getAddress();
		if (uid==0) {

			startActivity(new Intent(getActivity(), LoginActivity.class));
			Toast.makeText(getActivity(), "请先登录", Toast.LENGTH_SHORT).show();
		}

		if (uid!=0 )
		{
			if (uaddress==null) {
				startActivity(new Intent(getActivity(), AddressActivity.class));
				Toast.makeText(getActivity(), "完善收货信息", Toast.LENGTH_SHORT).show();
			}
			if (uaddress!=null) {

				params.addQueryStringParameter("u_id",MyApplication.getInstance().getU_id()+"");
				params.addQueryStringParameter("f_id",id+"");
				params.addQueryStringParameter("b_price",price+"");
				params.addQueryStringParameter("b_status",YFEncoder.EncodeToUTF("1"));
				mHttpUtils.send(HttpMethod.POST,
						"http://yanjiepeng.eicp.net:24310/YueFanServer/login", params,
						new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0,
							String arg1) {
						// TODO Auto-generated method stub
						Log.i("tag", "failed" + arg0.toString());
						Toast.makeText(getActivity(), "下单失败",Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onStart() {
						// TODO Auto-generated method stub
						super.onStart();
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						Log.i("FSLog", "post success");
						Log.i("FSLog", arg0.result);
						Toast.makeText(getActivity(), "下单成功",Toast.LENGTH_SHORT).show();
					}
				});
			}
		}

	}
	public static List<Map<String, Object>> setMenuAdapterSource(List<CurrentFood> list) {

		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("f_id", list.get(i).getF_id());
			map.put("name",list.get(i).getF_name());
			map.put("price", list.get(i).getF_price());
			map.put("f_img",list.get(i).getF_img());
			data.add(map);
		}

		return data;

	}

}
