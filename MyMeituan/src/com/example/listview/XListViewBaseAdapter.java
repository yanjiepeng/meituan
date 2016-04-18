package com.example.listview;

import java.util.List;

import com.example.bean.SellerJavaBean;
import com.example.mymeituan.R;
import com.example.util.JudgeNetWork;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class XListViewBaseAdapter extends BaseAdapter {

	private Context context;
	private List<SellerJavaBean> xListItems;
	private LayoutInflater inflater;
	private ViewHolder holder;
	private FirstPageListItemOnClickLitener flc;

	public XListViewBaseAdapter(Context context,
			List<SellerJavaBean> sellerInfoList) {
		this.context = context;
		this.xListItems = sellerInfoList;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return xListItems.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return xListItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		JudgeNetWork jnw = new JudgeNetWork();

		if (xListItems.size() != 0) {

			if (convertView == null) {
				convertView = inflater.inflate(
						R.layout.list_listviewfragment_item, null);
				holder = new ViewHolder();
				holder.imageItem = (ImageView) convertView
						.findViewById(R.id.iv_xlist_item);
				holder.tvItemstartprivce = (TextView) convertView
						.findViewById(R.id.list_item_sendprice);
				holder.tvItemshopId = (TextView) convertView
						.findViewById(R.id.list_item_s_id);
				holder.tvItemshopname = (TextView) convertView
						.findViewById(R.id.list_item_shopname);
				holder.tvItemshopAddress = (TextView) convertView
						.findViewById(R.id.list_item_textviewshop);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			if (xListItems.get(arg0).getS_sellerpic() == null) {
				holder.imageItem.setImageResource(R.drawable.ic_launcher);
			} else {
				Bitmap bm = BitmapFactory.decodeByteArray(xListItems.get(arg0)
						.getS_sellerpic(), 0, xListItems.get(arg0)
						.getS_sellerpic().length);
				holder.imageItem.setImageBitmap(bm);
			}

			Log.i("yjlog", arg0 + "--------"
					+ xListItems.get(arg0).getS_sellerpic());

			holder.tvItemstartprivce.setText("起步价"
					+ xListItems.get(arg0).getS_starting());
			holder.tvItemshopId.setText(xListItems.get(arg0).getS_id());
			holder.tvItemshopname.setText(xListItems.get(arg0).getS_name());
			holder.tvItemshopAddress.setText(xListItems.get(arg0)
					.getS_address());
			return convertView;
		} else {
			Log.i("yjlog", "else" + "-----------");
			View view = inflater.inflate(R.layout.nodata_list_item, null);
			return view;
		}
	}

	public class ViewHolder {
		ImageView imageItem;
		TextView tvItemshopAddress;
		TextView tvItemstartprivce;
		TextView tvItemshopname;
		TextView tvItemshopId;
	}
}
