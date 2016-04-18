package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.mymeituan.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyCommantAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	private List<Map<String, Object>> list;
	
	public MyCommantAdapter(Context context,List<Map<String, Object>> list){
		this.context = context ;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
		this.list = list;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		
		View view = null ;
		// 1.获取布局文件定义的视�?
		//ConvertView为空，就新建，不为空，就重复使用
		if (convertView == null) {
//			System.out.println("新建----------------------------------------------------------------");
			view = inflater.inflate(R.layout.commantitem, null);
			//a.获取指定的控件对�?
			TextView tv_commantname = (TextView) view.findViewById(R.id.tv_commentname_fragmentjudge);
			TextView tv_commanttime = (TextView) view.findViewById(R.id.tv_commenttime_fragmentjudge);
			TextView tv_commantcontent = (TextView) view.findViewById(R.id.tv_comment_fragmentjudge) ;
			//创建出ViewHolder对象，帮助我们保持子控件�?
			ViewHolder viewHolder = new ViewHolder() ;
			viewHolder.tv_commantname = tv_commantname ;
			viewHolder.tv_commanttime = tv_commanttime ;
			viewHolder.tv_commantcontent = tv_commantcontent;
			//保存ViewHolder
			view.setTag(viewHolder) ;
		}else{
//			System.out.println("复用");
			view = convertView ;
		}
		// 无论是新建的视图还是复用的视图，这个view中都保存�?��个ViewHolder
		ViewHolder viewHolder = (ViewHolder) view.getTag() ;
		
		//b.获取数据，并设置到控件对象上显示�?
		// List<Map<String, Object>>
		Map map = list.get(position % list.size());
		Object object = map.get("name");
		String name = object.toString();
		System.out.println("name = " + name);
		//显示时间
		Map map1 = list.get(position % list.size());
		Object object1 = map.get("time");
		String time = object1.toString();
		System.out.println("time = " + time);
		//显示评论内容
		Map map2= list.get(position % list.size());
		Object object2 = map.get("content");
		String content = object2.toString();
		System.out.println("content = " + content);
		// 将名字显示到界面
		viewHolder.tv_commantname.setText(name); // 从list中获取到map，再从map中获�?name",显示到TextView中�?
		viewHolder.tv_commanttime.setText(time) ;
		viewHolder.tv_commantcontent.setText(content);
		// 2.返回此视图对象，让listview显示
		return view;
		
		
	}
	
	class ViewHolder {
		//评价用户名
		TextView tv_commantname ;
		//评价时间
		TextView tv_commanttime;
		//评价内容
		TextView tv_commantcontent;
	}


}
