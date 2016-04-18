package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.example.adapter.ADPagerAdapter;
import com.example.bean.SellerJavaBean;
import com.example.listview.FirstPageListItemOnClickLitener;
import com.example.listview.XListView;
import com.example.listview.XListView.IXListViewListener;
import com.example.listview.XListViewBaseAdapter;
import com.example.mymeituan.HomeActivity;
import com.example.mymeituan.R;
import com.example.service.YFService;
import com.example.util.JudgeNetWork;
import com.example.util.RequestNetWork;

/**
 * @author john
 * 
 */
public class FirstPageFragment extends Fragment implements IXListViewListener,
		BDLocationListener {
	private static List<SellerJavaBean> sellerInfoList = new ArrayList<SellerJavaBean>();
	private static List<TextView> rblist = new ArrayList<TextView>();
	public static List<String> rbData = new ArrayList<String>();
	public static List<String> rbSname = new ArrayList<String>();
	private String sellerInfo;
	private JudgeNetWork netWorkState;
	private static XListView mListView;

	private static ArrayList<String> items = new ArrayList<String>();
	private Handler mHandler;

	private static XListViewBaseAdapter xListAdapter;

	private ViewPager viewPager;
	private ImageView ivPoint1;
	private ImageView ivPoint2;
	private ImageView ivPoint3;
	private ImageView ivPoint4;
	private ImageView ivPoint5;
	private ImageView[] ivPoints;

	private View view;
	private static HomeActivity activity;
	private static TextView tv;

	private MapView mv;
	private BaiduMap bm;
	private static LocationClient mClient;

	private MyBroadcast broadReceiver;

	private static Random data = new Random();
	private static List<Integer> fordata = new ArrayList<Integer>();

	// 当广播接收到有网络状态时，会发送一个消息
	public static Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				mClient.start();
				//Toast.makeText(activity, "有网络", 2000).show();
			} else if (msg.what == 1) {
				sellerInfoList = rNW.sellerInfo();
				xListAdapter = new XListViewBaseAdapter(activity,
						sellerInfoList);
				mListView.setAdapter(xListAdapter);
				FirstPageFragment.changed();

			} else if (msg.what == 2) {
				sellerInfoList = (List<SellerJavaBean>) msg.obj;
				xListAdapter = new XListViewBaseAdapter(activity,
						sellerInfoList);
				mListView.setAdapter(xListAdapter);
				Toast.makeText(activity, "无法获取item", 1000).show();
			}
			super.handleMessage(msg);
		}
	};

	// 自定义toastView
	private View viewToast;
	private Toast toast;
	private View viewHeader;
	private ADPagerAdapter ad_Adapter;
	private static RequestNetWork rNW;
	private RadioButton rBRestart;
	private static FirstPageListItemOnClickLitener listItem;
	private TextView rbfirst;
	private TextView rbsec;
	private TextView rbthird;
	private TextView rbfor;
	private LinearLayout llForth;
	private LinearLayout llThrid;
	private LinearLayout llSecond;
	private LinearLayout llFirst;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		activity = (HomeActivity) getActivity();
		netWorkState = new JudgeNetWork(activity);
//		rNW = new RequestNetWork(activity);
		// 初始化百度地图需要的相关信息
		initBaiduMap();

		// 获得自定义ViewToast布局
		initToast();
		// 注册监听网络动态的广播
		listenNetWorkBroadcast();

	}

	// 注册监听网络动态的广播
	private void listenNetWorkBroadcast() {
		IntentFilter intent = new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION);
		broadReceiver = new MyBroadcast();
		activity.registerReceiver(broadReceiver, intent);
	}

	// 初始化百度地图需要的相关信息
	private void initBaiduMap() {
		// 初始化百度sdk
		SDKInitializer.initialize(activity.getApplicationContext());

		mv = new MapView(activity);
		bm = mv.getMap();
		// 使能定位功能,必须
		bm.setMyLocationEnabled(true);
		mClient = new LocationClient(activity);

		// 设置定位相关参数
		LocationClientOption option = new LocationClientOption();
		// 设置定位模式，使用高精度会同时使用GPS和网络定位，选择精度更高的方式。
		option.setLocationMode(LocationMode.Hight_Accuracy);
		// 返回的定位结果是百度经纬度,默认值gcj02
		option.setCoorType("bd09ll");
		// 设置发起定位请求的间隔时间为5000ms
		option.setScanSpan(0);
		// 是否需要返回具体address
		option.setIsNeedAddress(true);
		mClient.setLocOption(option);
		mClient.registerLocationListener(this);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_firstpage, container, false);
		viewHeader = inflater.inflate(R.layout.listviewheader, null);

		// 初始化fragment_firstpage.xml中的控件
		initViewID();
		// 设置listview加载项中的items更多
		initXListViewLoad();

		// 设置广告轮滚页面的适配器
		setViewPagerAdapter();

		return view;

	}

	private void initToast() {

		// 获得自定义ViewToast布局
		viewToast = LayoutInflater.from(activity).inflate(
				R.layout.toast_broadcastlistener, null);
		toast = new Toast(activity);

		toast.setView(viewToast);
	}

	// 设置listview加载项中的items更多
	private void initXListViewLoad() {

		mListView.setPullLoadEnable(true);

		listItem = new FirstPageListItemOnClickLitener(activity, mClient);
		mListView.addHeaderView(viewHeader);
		mListView.setOnItemClickListener(listItem);
		mListView.setXListViewListener(this);
		mHandler = new Handler();
		rBRestart.setOnClickListener(listItem);
		setRButtonClick();
	}

	private void setRButtonClick() {
		FirstPageFragment.changed();
		rbfirst.setOnClickListener(listItem);
		rbsec.setOnClickListener(listItem);
		rbthird.setOnClickListener(listItem);
		rbfor.setOnClickListener(listItem);
		llFirst.setOnClickListener(listItem);
		llSecond.setOnClickListener(listItem);
		llThrid.setOnClickListener(listItem);
		llForth.setOnClickListener(listItem);
	}

	// 设置广告轮滚页面的适配器
	private void setViewPagerAdapter() {
		ad_Adapter = new ADPagerAdapter(activity, viewPager, ivPoints);
	}

	// 初始化fragment_firstpage.xml中的控件
	private void initViewID() {
		viewPager = (ViewPager) viewHeader.findViewById(R.id.viewpager_ad_main);
		ivPoint1 = (ImageView) viewHeader.findViewById(R.id.indicator1_main);

		ivPoint2 = (ImageView) viewHeader.findViewById(R.id.indicator2_main);
		ivPoint3 = (ImageView) viewHeader.findViewById(R.id.indicator3_main);

		ivPoint4 = (ImageView) viewHeader.findViewById(R.id.indicator4_main);
		ivPoint5 = (ImageView) viewHeader.findViewById(R.id.indicator5_main);
		rBRestart = (RadioButton) viewHeader
				.findViewById(R.id.bt_restartlocation_main);

		mListView = (XListView) view.findViewById(R.id.xListView);

		tv = (TextView) viewHeader.findViewById(R.id.et_showlocation_main);

		rbfirst = (TextView) viewHeader
				.findViewById(R.id.left_firstfoodrecommend_firstpager);

		rblist.add(rbfirst);
		rbsec = (TextView) viewHeader
				.findViewById(R.id.left_secondfoodrecommend_firstpager);
		rblist.add(rbsec);
		rbthird = (TextView) viewHeader
				.findViewById(R.id.left_thirdfoodrecommend_firstpager);
		rblist.add(rbthird);
		rbfor = (TextView) viewHeader
				.findViewById(R.id.left_forthfoodrecommend_firstpager);
		rblist.add(rbfor);

		llFirst = (LinearLayout) viewHeader
				.findViewById(R.id.ll_left_firstfoodrecommend_firstpager);
		llSecond = (LinearLayout) viewHeader
				.findViewById(R.id.ll_left_secondfoodrecommend_firstpager);
		llThrid = (LinearLayout) viewHeader
				.findViewById(R.id.ll_left_thirdfoodrecommend_firstpager);
		llForth = (LinearLayout) viewHeader
				.findViewById(R.id.ll_left_forthfoodrecommend_firstpager);

		ivPoints = new ImageView[] { ivPoint1, ivPoint2, ivPoint3, ivPoint4,
				ivPoint5 };

	}

	private void onLoad() {
		mListView.stopLoadMore();
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				rNW = new RequestNetWork(activity);
				RequestNetWork.getDataFromNetWork();
				
				xListAdapter.notifyDataSetChanged();
				onLoad();
				activity.runOnUiThread(new Runnable() {

					@Override
					public void run() {
						changed();
					}
				});
			}
		}, 2000);
	}

	public static void changed() {
		if (sellerInfoList.size() == 0)
			return;

		while (fordata.size() < 4) {

			int i = data.nextInt(sellerInfoList.size());

			if (!fordata.contains(i)) {
				fordata.add(i);
			}
		}
		rbData.clear();
		rbSname.clear();
		for (int j = 0; j < 4; j++) {
			rblist.get(j).setText(
					sellerInfoList.get(fordata.get(j)).getS_name());
			rbData.add(sellerInfoList.get(fordata.get(j)).getS_id());
			rbSname.add(sellerInfoList.get(fordata.get(j)).getS_name());
		}

		fordata.clear();

	}

	@Override
	public void onReceiveLocation(BDLocation arg0) {
		tv.setText(arg0.getCity() + arg0.getDistrict() + arg0.getStreet()
				+ arg0.getStreetNumber());
		mClient.stop();

	}

	@Override
	public void onDestroy() {
		mv.onDestroy();
		mClient.unRegisterLocationListener(this);
		activity.unregisterReceiver(broadReceiver);
		super.onDestroy();
	}

	@Override
	public void onPause() {
		mv.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {

		mv.onResume();
		super.onResume();
	}

	/**
	 * @author john 定义了一个内部类的广播，来监听系统发送的网络状态改变的广播 监听到有网络时就开启服务
	 * 
	 */

	public class MyBroadcast extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {

			View view = LayoutInflater.from(context).inflate(
					R.layout.toast_broadcastlistener, null);

			boolean judgenetwork = netWorkState.judgeNetWork();
			if (judgenetwork) {
				rNW = new RequestNetWork(activity);
				Toast.makeText(context, "有可用网络", 2000).show();
				Message msg = Message.obtain();
				msg.what = 0;
				FirstPageFragment.this.handler.sendMessage(msg);
				Intent intentService = new Intent(activity, YFService.class);
				activity.startService(intentService);
				viewPager.setAdapter(ad_Adapter);
			} else {
				toast.show();
				viewPager.setAdapter(ad_Adapter);
				mListView.setAdapter(xListAdapter);
			}
		}
	}

}
