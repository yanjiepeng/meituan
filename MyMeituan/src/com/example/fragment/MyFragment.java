package com.example.fragment;

import com.example.application.MyApplication;
import com.example.mymeituan.AddressActivity;
import com.example.mymeituan.CashcouponActivity;
import com.example.mymeituan.HelpActivity;
import com.example.mymeituan.LoginActivity;
import com.example.mymeituan.MoreActivity;
import com.example.mymeituan.MycountActivity;
import com.example.mymeituan.MyfavoriteActivity;
import com.example.mymeituan.R;
import com.example.mymeituan.RedpacketActivity;
import com.example.mymeituan.WalletActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment implements OnClickListener {

	RelativeLayout relativelayoutIcon;
	LinearLayout linearlayoutWallet, linearlayoutRedPacket,
			linearlayoutCashCoupon, linearlayoutAddress,
			linearlayoutMyfavorite, linearlayoutHelp, linearlayoutMore;
	TextView tvTel, tvMycount;

	ImageView ivMycount;

	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		view = inflater.inflate(R.layout.fragment_my, container, false);

		init();

		return view;
	}

	private void init() {
		// TODO Auto-generated method stub

		relativelayoutIcon = (RelativeLayout) view
				.findViewById(R.id.relativelayout_mycount_my);
		relativelayoutIcon.setOnClickListener(this);

		linearlayoutWallet = (LinearLayout) view
				.findViewById(R.id.linearlayout_wallet_my);
		linearlayoutWallet.setOnClickListener(this);
		linearlayoutRedPacket = (LinearLayout) view
				.findViewById(R.id.linearlayout_red_packet_my);
		linearlayoutRedPacket.setOnClickListener(this);
		linearlayoutCashCoupon = (LinearLayout) view
				.findViewById(R.id.linearlayout_cash_coupon_my);
		linearlayoutCashCoupon.setOnClickListener(this);
		linearlayoutAddress = (LinearLayout) view
				.findViewById(R.id.linearlayout_address_my);
		linearlayoutAddress.setOnClickListener(this);
		linearlayoutMyfavorite = (LinearLayout) view
				.findViewById(R.id.linearlayout_myfavorite_my);
		linearlayoutMyfavorite.setOnClickListener(this);
		linearlayoutHelp = (LinearLayout) view
				.findViewById(R.id.linearlayout_help_my);
		linearlayoutHelp.setOnClickListener(this);
		linearlayoutMore = (LinearLayout) view
				.findViewById(R.id.linearlayout_more_my);
		linearlayoutMore.setOnClickListener(this);

		tvTel = (TextView) view.findViewById(R.id.tv_tel_my);
		tvTel.setOnClickListener(this);

		tvMycount = (TextView) view.findViewById(R.id.tv_mycount_my);
		ivMycount = (ImageView) view.findViewById(R.id.iv_mycount_my);
		if (MyApplication.getInstance().getEt_phone()!=null) {
			tvMycount.setText(MyApplication.getInstance().getEt_phone());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		Intent intent;
		switch (v.getId()) {
		case R.id.relativelayout_mycount_my:
			//如果没有登录，则跳转到登录界面所在的activity；如果已经登录，则跳转到MycountActivity
			if ((tvMycount.getText().toString()).equals("登陆/注册")) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivityForResult(intent, 0);
			} else {
				intent = new Intent(getActivity(), MycountActivity.class);
				startActivity(intent);
			}

			break;
		case R.id.linearlayout_wallet_my:
			//如果没有登录，则跳转到登录界面所在的activity；如果已经登录，则跳转到WalletActivity
			if ((tvMycount.getText().toString()).equals("登陆/注册")) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
			} else {
				intent = new Intent(getActivity(), WalletActivity.class);
				startActivity(intent);

			}
			break;
		case R.id.linearlayout_red_packet_my:
			//如果没有登录，则跳转到登录界面所在的activity；如果已经登录，则跳转到RedpacketActivity
			if ((tvMycount.getText().toString()).equals("登陆/注册")) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
			} else {
			intent = new Intent(getActivity(), RedpacketActivity.class);
			startActivity(intent);
			}
			break;
		case R.id.linearlayout_cash_coupon_my:
			//如果没有登录，则跳转到登录界面所在的activity；如果已经登录，则跳转到CashcouponActivity
			if ((tvMycount.getText().toString()).equals("登陆/注册")) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
			} else {
			intent = new Intent(getActivity(), CashcouponActivity.class);
			startActivity(intent);
			}
			break;
		case R.id.linearlayout_address_my:
			//如果没有登录，则跳转到登录界面所在的activity；如果已经登录，则跳转到AddressActivity
			if ((tvMycount.getText().toString()).equals("登陆/注册")) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
			} else {
			intent = new Intent(getActivity(), AddressActivity.class);
			startActivity(intent);
			}
			break;
		case R.id.linearlayout_myfavorite_my:
			//如果没有登录，则跳转到登录界面所在的activity；如果已经登录，则跳转到MyfavoriteActivity
			if ((tvMycount.getText().toString()).equals("登陆/注册")) {
				intent = new Intent(getActivity(), LoginActivity.class);
				startActivity(intent);
				Toast.makeText(getActivity(), "请先登陆", Toast.LENGTH_SHORT).show();
			} else {
			intent = new Intent(getActivity(), MyfavoriteActivity.class);
			startActivity(intent);
			}
			break;
		case R.id.linearlayout_help_my:

			intent = new Intent(getActivity(), HelpActivity.class);
			startActivity(intent);
			break;
		case R.id.linearlayout_more_my:

			intent = new Intent(getActivity(), MoreActivity.class);
			startActivity(intent);
			break;
		case R.id.tv_tel_my:

			// 执行拨号操作
			// 1.获取用户输入的号码
			String number = "4008507777";
			// 2.执行拨号操作
			// 创建一个拨号意图
			intent = new Intent();
			// 设置要拨打的号码 (URL:统一资源定位符,uri:统一资源标识符)
			intent.setData(Uri.parse("tel:" + number));
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// 设置动作,拨号动作
			intent.setAction(Intent.ACTION_DIAL);
			// 跳转到拨号界面
			startActivity(intent);
			break;
		default:
			break;

		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && data != null) {
			tvMycount.setText(data.getStringExtra("et_phone"));
		}
	}
}
