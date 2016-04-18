package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.adapter.MyCommantAdapter;
import com.example.bean.Comment;
import com.example.mymeituan.R;
import com.example.util.MenuSave;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class CommantFragment extends Fragment{
	
	private static ListView lv_menu;
	@Override
	public View onCreateView(LayoutInflater inflater,
			 ViewGroup container,  Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.commantlist,container,false);
		lv_menu = (ListView) v.findViewById(R.id.lv_commantmenu);
		
		
		MyCommantAdapter adapter1 = new MyCommantAdapter(getActivity(), setcommantAdapterSource(MenuSave.commentdata));
		lv_menu.setAdapter(adapter1);
		return v;
	}
	
	public static List<Map<String, Object>> setcommantAdapterSource(List<Comment> list1) {
		
		List<Map<String,Object>> data1 = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < list1.size(); i++) {
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("name",list1.get(i).getU_name());
			map1.put("time", list1.get(i).getC_time());
			map1.put("content", list1.get(i).getC_content());
			data1.add(map1);
		}
		
		return data1;
		
	}

}
