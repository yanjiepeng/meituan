package com.example.adapter;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.Volley;
import com.example.data.FirstFragmentSQLite;
import com.example.mymeituan.R;
import com.example.service.YFService;

/**
 * @author yangjun
 * @text:这个是activity_main中viewpager的适配器，并且对viewpager设置了监听
 */

public class ADPagerAdapter extends PagerAdapter implements
		OnPageChangeListener, Runnable {

	/**
	 * ViewPager pagerAD:是从MainActivity中获取的对象，传到该类中 FAKE_BANNER_SIZE:设置
	 * getCount()的返回值，滚动的最大次数
	 * 
	 * DEFAULT_BANNER_SIZE:设置循环滚东的图片张数 mBannerPosition:为自动循环滚动的位置 ImageView[]
	 * ivPoints:从MainActivity中传过来的广告原点对象（imageView）
	 * 
	 */
	private ViewPager pagerAD;
	private LayoutInflater inflater;
	private final int FAKE_BANNER_SIZE = 100;
	private final int DEFAULT_BANNER_SIZE = 5;
	private static Activity context;
	private int mBannerPosition = 0;
	private ImageView[] ivPoints;
	private boolean Flag = false;
	LruCache<String, Bitmap> mMemoryCache;

	private List<String> list = new ArrayList<String>();
	/**
	 * @author john 这是一个定时器，循环播放广告
	 */
	public static Timer timer = new Timer();

	private TimerTask task = new TimerTask() {

		@Override
		public void run() {
			if (!Flag) {
				mBannerPosition = mBannerPosition % DEFAULT_BANNER_SIZE;
				context.runOnUiThread(ADPagerAdapter.this);

			}
		}
	};
	private RequestQueue mQueue;
	private static ContentValues values;

	public ADPagerAdapter(Context context, ViewPager viewPager,
			ImageView[] ivPoints) {

		mQueue = Volley.newRequestQueue(context);
		this.pagerAD = viewPager;
		this.ivPoints = ivPoints;
		this.context = (Activity) context;
		getSDB();

		inflater = LayoutInflater.from(context);
		timer.schedule(task, 2000, 2000);
		pagerAD.setOnPageChangeListener(this);
		getTouchAction();

	}

	public static SQLiteDatabase getSDB() {
		
		values = new ContentValues();
		FirstFragmentSQLite sqlite = new FirstFragmentSQLite(context,
				"firstfragment.db", null, 1);
		SQLiteDatabase db = sqlite.getReadableDatabase();
		return db;
	}

	// 该方法是对iewpager的触摸事件做处理：如果触摸就停止自动滚动

	private void getTouchAction() {
		pagerAD.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				if (event.getAction() == MotionEvent.ACTION_DOWN
						|| event.getAction() == MotionEvent.ACTION_SCROLL) {
					Flag = true;
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					Flag = false;
				}
				return false;
			}
		});
	}

	// 滚动的最大次数
	@Override
	public int getCount() {
		return FAKE_BANNER_SIZE;
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	// 销毁滚动后的前一个item
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	// 得到适配器需要的view
	@Override
	public Object instantiateItem(final ViewGroup container, int position) {

		// Log.i("yjlog", "position-------------------" + position);
		position %= DEFAULT_BANNER_SIZE;
		final int aa = position;
		final View view = inflater.inflate(R.layout.viewpager_fragment_ad_item,
				container, false);
		final ImageView ivAD = (ImageView) view
				.findViewById(R.id.iv_ad_viewpager);
		ImageLoader loader = new ImageLoader(mQueue, new CustomeImageCache());
		ImageLoader.ImageContainer containerI = loader.get(
				YFService.imageUri[position], new ImageListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(context, "无法获取到，请检查网络", 0).show();
						ivAD.setBackgroundColor(R.drawable.ic_launcher);
					}

					@Override
					public void onResponse(ImageContainer response,
							boolean isImmediate) {
						if (!isImmediate) {
							
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
							Bitmap bitmap = response.getBitmap();
						//	bitmap.compress(Bitmap.CompressFormat.JPEG, 100,baos);
							byte[] image = baos.toByteArray();
							values.put("viewpagerpic",image);
						}
						ivAD.setImageBitmap(response.getBitmap());
					}
				}, 100, 100);

		container.addView(view);
		return view;

	}

	@Override
	public void finishUpdate(ViewGroup container) {

		int position = pagerAD.getCurrentItem();
		if (position == 0) {
			position = DEFAULT_BANNER_SIZE;
			pagerAD.setCurrentItem(position, false);
		} else if (position == FAKE_BANNER_SIZE - 1) {
			position = DEFAULT_BANNER_SIZE - 1;
			pagerAD.setCurrentItem(position, false);
		}

	}

	// 滚动状态改变时候
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	// viewpager正在滚动
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	// 当前所选择的那一页
	@Override
	public void onPageSelected(int position) {

		mBannerPosition = position;

		// 调用此方法，实现动态图下的圆点颜色变化
		setPointChanged(position);
	}

	@Override
	public void run() {
		if (mBannerPosition == FAKE_BANNER_SIZE - 1) {
			pagerAD.setCurrentItem(DEFAULT_BANNER_SIZE - 1, false);
		} else {

			pagerAD.setCurrentItem(mBannerPosition);
		}
		mBannerPosition = mBannerPosition + 1;
	}

	public void setPointChanged(int position) {
		position %= DEFAULT_BANNER_SIZE;

		for (ImageView ivPoint : ivPoints) {
			ivPoint.setImageResource(R.drawable.indicator_unchecked);
		}

		ivPoints[position].setImageResource(R.drawable.indicator_checked);

	}

	static class CustomeImageCache implements ImageLoader.ImageCache {

		LruCache<String, Bitmap> mCache;

		public CustomeImageCache() {
			int max = 1024 * 1024 * 1024;
			mCache = new LruCache<String, Bitmap>(max);
		}

		@Override
		public Bitmap getBitmap(String url) {
			//System.out.println("getBitmap" + url);
			Log.w("url", url);
			return mCache.get(url);

		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mCache.put(url, bitmap);
			Log.w("url", url);
		}
	}

}
