package com.example.adapter;

import java.util.List;
import java.util.Map;

import com.example.mymeituan.R;
import com.example.util.YFImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {
	
	private Context context ;
	LayoutInflater inflater ;
	private List<Map<String, Object>> list;
	
	public MyAdapter(Context context,List<Map<String, Object>> list){
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
		// 1.获取布局文件?
		//ConvertView为空，就新建，不为空，就重复使用
		if (convertView == null) {
//			System.out.println("新建----------------------------------------------------------------");
			view = inflater.inflate(R.layout.item, null);
			//a.获取指定的控件对
			TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
			TextView tv_price = (TextView) view.findViewById(R.id.tv_price);
			ImageView iv_header = (ImageView) view.findViewById(R.id.iv_img) ;
			//创建出ViewHolder对象，帮助我们保持子控件�?
			ViewHolder viewHolder = new ViewHolder() ;
			viewHolder.tv_name = tv_name ;
			viewHolder.iv_header = iv_header ;
			viewHolder.tv_price = tv_price;
			//保存ViewHolder
			view.setTag(viewHolder) ;
		}else{
//			System.out.println("复用");
			view = convertView ;
		}
		// 无论是新建的视图还是复用的视图，这个view中都保存ViewHolder
		ViewHolder viewHolder = (ViewHolder) view.getTag() ;
		
		//b.获取数据，并设置到控件对象上显示
		// List<Map<String, Object>>
		Map map = list.get(position % list.size());
		Object object = map.get("name");
		String name = object.toString();
		// 将名字显示到界面
		viewHolder.tv_name.setText(name); // 从list中获取到map，再从map中获�?name",显示到TextView中�?
		
		if (map.get("f_img")!=null) {
			byte[] temp = (byte[]) map.get("f_img");
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = 2;
			Bitmap bt1 =YFImageUtil.getPicFromBytes(temp, opts);
			Bitmap bt2 = YFImageUtil.zoomBitmap(bt1, 100, 100);
			viewHolder.iv_header.setImageBitmap(bt2);
		}else{
			viewHolder.iv_header.setImageResource(R.drawable.ic_launcher) ;
		}
		viewHolder.tv_price.setText(map.get("price").toString());
		// 2.返回此视图对象，让listview显示
		return view;
		
		
	}
	
	class ViewHolder {
		TextView tv_name ;
		ImageView iv_header ;
		TextView tv_price;
	}


}
