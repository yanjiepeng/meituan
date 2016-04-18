package com.example.mymeituan;

import com.example.application.SysApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WalletActivity extends Activity implements OnClickListener {

	LinearLayout linearlayoutAccount, linearlayoutMycard,
			linearlayoutCashCoupon, linearlayoutPayManage, linearlayoutSnatch,
			linearlayoutBar;

	TextView tvRecharge, tvWithdraw;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_walletmeituan);

		init();
		
		//将这个activity加入到list中
		SysApplication.getInstance().addActivity(this); 

	}

	private void init() {
		// TODO Auto-generated method stub

		linearlayoutAccount = (LinearLayout) findViewById(R.id.linearlayout_account_walletmeituan);
		linearlayoutAccount.setOnClickListener(this);
		linearlayoutMycard = (LinearLayout) findViewById(R.id.linearlayout_mycard_walletmeituan);
		linearlayoutMycard.setOnClickListener(this);
		linearlayoutCashCoupon = (LinearLayout) findViewById(R.id.linearlayout_mycash_coupon_walletmeituan);
		linearlayoutCashCoupon.setOnClickListener(this);
		linearlayoutPayManage = (LinearLayout) findViewById(R.id.linearlayout_pay_manage_walletmeituan);
		linearlayoutPayManage.setOnClickListener(this);
		linearlayoutSnatch = (LinearLayout) findViewById(R.id.linearlayout_snatch_walletmeituan);
		linearlayoutSnatch.setOnClickListener(this);
		linearlayoutBar = (LinearLayout) findViewById(R.id.linearlayout_activity_walletmeituan);
		linearlayoutBar.setOnClickListener(this);

		tvRecharge = (TextView) findViewById(R.id.tv_recharge_walletmeituan);
		tvRecharge.setOnClickListener(this);
		tvWithdraw = (TextView) findViewById(R.id.tv_withdraw_walletmeituan);
		tvWithdraw.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.linearlayout_account_walletmeituan:
			break;
		case R.id.linearlayout_mycard_walletmeituan:
			break;
		case R.id.linearlayout_mycash_coupon_walletmeituan:
			break;
		case R.id.linearlayout_pay_manage_walletmeituan:
			break;
		case R.id.linearlayout_snatch_walletmeituan:
			break;
		case R.id.tv_recharge_walletmeituan:
			break;
		case R.id.tv_withdraw_walletmeituan:
			break;
		case R.id.linearlayout_activity_walletmeituan:
			finish();
			break;
		default:
			break;

		}
	}

}
