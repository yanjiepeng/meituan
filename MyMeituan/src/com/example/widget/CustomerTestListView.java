package com.example.widget;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;

public class CustomerTestListView extends ListView implements
		AbsListView.OnScrollListener {
	private static final Interpolator sInterpolator = new Interpolator() {
		public float getInterpolation(float paramAnonymousFloat) {
			float f = paramAnonymousFloat - 1.0F;
			return 1.0F + f * (f * (f * (f * f)));
		}
	};
	int mActivePointerId = -1;
	private FrameLayout mHeaderContainer;
	private int mHeaderHeight;
	private ImageView mHeaderImage;
	float mLastMotionY = -1.0F;
	float mLastScale = -1.0F;
	float mMaxScale = -1.0F;
	private AbsListView.OnScrollListener mOnScrollListener;
	private ScalingRunnalable mScalingRunnalable;
	private int mScreenHeight;
	private ImageView mShadow;
	public CustomerTestListView(Context paramContext) {
		super(paramContext);
		init(paramContext);
	}

	public CustomerTestListView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext);
	}

	public CustomerTestListView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init(paramContext);
	}

	private void endScraling() {
		if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight)
			this.mScalingRunnalable.startAnimation(200L);
	}

	private void init(Context paramContext) {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) paramContext).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		mScreenHeight = localDisplayMetrics.heightPixels;
		mHeaderContainer = new FrameLayout(paramContext);
		mHeaderImage = new ImageView(paramContext);
		int i = localDisplayMetrics.widthPixels;
		setHeaderViewSize(i, (int) (9.0F * (i / 16.0F)));
		mShadow = new ImageView(paramContext);
		FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(
				-1, -2);
		localLayoutParams.gravity = 80;
		mShadow.setLayoutParams(localLayoutParams);
		mHeaderContainer.addView(this.mHeaderImage);
		mHeaderContainer.addView(this.mShadow);
		addHeaderView(this.mHeaderContainer);

		mScalingRunnalable = new ScalingRunnalable();

		super.setOnScrollListener(this);
	}

	private void onSecondaryPointerUp(MotionEvent paramMotionEvent) {
		int i = (paramMotionEvent.getAction()) >> 8;
		if (paramMotionEvent.getPointerId(i) == this.mActivePointerId)
			if (i != 0) {
				this.mLastMotionY = paramMotionEvent.getY(0);
				this.mActivePointerId = paramMotionEvent.getPointerId(0);
				return;
			}
	}

	private void reset() {
		this.mActivePointerId = -1;
		this.mLastMotionY = -1.0F;
		this.mMaxScale = -1.0F;
		this.mLastScale = -1.0F;
	}

	public ImageView getHeaderView() {
		return this.mHeaderImage;
	}

	@Override
	protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2,
			int paramInt3, int paramInt4) {

		super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
		if (this.mHeaderHeight == 0)
			this.mHeaderHeight = this.mHeaderContainer.getHeight();
	}

	@Override
	public void onScroll(AbsListView paramAbsListView, int paramInt1,
			int paramInt2, int paramInt3) {
		
		float f = this.mHeaderHeight - this.mHeaderContainer.getBottom();
		if ((f > 0.0F) && (f < this.mHeaderHeight)) {
			int i = (int) (0.65D * f);
			this.mHeaderImage.scrollTo(0, -i);
		} else if (this.mHeaderImage.getScrollY() != 0) {
			this.mHeaderImage.scrollTo(0, 0);
		}
		if (this.mOnScrollListener != null) {
			this.mOnScrollListener.onScroll(paramAbsListView, paramInt1,
					paramInt2, paramInt3);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView paramAbsListView, int paramInt) {
		if (this.mOnScrollListener != null)
			this.mOnScrollListener.onScrollStateChanged(paramAbsListView,
					paramInt);
	}


	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_OUTSIDE:
		case MotionEvent.ACTION_DOWN:
			if (!this.mScalingRunnalable.mIsFinished) {
				this.mScalingRunnalable.abortAnimation();
			}
			this.mLastMotionY = ev.getY();
			this.mActivePointerId = ev.getPointerId(0);
			this.mMaxScale = (this.mScreenHeight / this.mHeaderHeight);
			this.mLastScale = (this.mHeaderContainer.getBottom() / this.mHeaderHeight);
			break;
		case MotionEvent.ACTION_MOVE:
			int j = ev.findPointerIndex(this.mActivePointerId);
			if (j == -1) {
				Log.e("PullToZoomListView", "Invalid pointerId="
						+ this.mActivePointerId + " in onTouchEvent");
			} else {
				if (this.mLastMotionY == -1.0F)
					this.mLastMotionY = ev.getY(j);
				if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight) {
					ViewGroup.LayoutParams localLayoutParams = this.mHeaderContainer
							.getLayoutParams();
					float f = ((ev.getY(j) - this.mLastMotionY + this.mHeaderContainer
							.getBottom()) / this.mHeaderHeight - this.mLastScale)
							/ 2.0F + this.mLastScale;
					if ((this.mLastScale <= 1.0D) && (f < this.mLastScale)) {
						localLayoutParams.height = this.mHeaderHeight;
						this.mHeaderContainer
								.setLayoutParams(localLayoutParams);
						return super.onTouchEvent(ev);
					}
					this.mLastScale = Math.min(Math.max(f, 1.0F),
							this.mMaxScale);
					localLayoutParams.height = ((int) (this.mHeaderHeight * this.mLastScale));
					if (localLayoutParams.height < this.mScreenHeight)
						this.mHeaderContainer
								.setLayoutParams(localLayoutParams);
					this.mLastMotionY = ev.getY(j);
					return true;
				}
				this.mLastMotionY = ev.getY(j);
			}
			break;
		case MotionEvent.ACTION_UP:
			reset();
			endScraling();
			
			break;
		case MotionEvent.ACTION_CANCEL:
			int i = ev.getActionIndex();
			this.mLastMotionY = ev.getY(i);
			this.mActivePointerId = ev.getPointerId(i);
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			onSecondaryPointerUp(ev);
			this.mLastMotionY = ev.getY(ev
					.findPointerIndex(this.mActivePointerId));
			break;
		case MotionEvent.ACTION_POINTER_UP:
		}
		return super.onTouchEvent(ev);
	}

	public void setHeaderViewSize(int paramInt1, int paramInt2) {
		Object localObject = this.mHeaderContainer.getLayoutParams();
		if (localObject == null)
			localObject = new AbsListView.LayoutParams(paramInt1, paramInt2);
		((ViewGroup.LayoutParams) localObject).width = paramInt1;
		((ViewGroup.LayoutParams) localObject).height = paramInt2;
		this.mHeaderContainer
				.setLayoutParams((ViewGroup.LayoutParams) localObject);
		this.mHeaderHeight = paramInt2;
	}

	public void setOnScrollListener(
			AbsListView.OnScrollListener paramOnScrollListener) {
		this.mOnScrollListener = paramOnScrollListener;
	}

	public void setShadow(int paramInt) {
		this.mShadow.setBackgroundResource(paramInt);
	}

	class ScalingRunnalable implements Runnable {
		long mDuration;
		boolean mIsFinished = true;
		float mScale;
		long mStartTime;

		ScalingRunnalable() {
		}

		public void abortAnimation() {
			this.mIsFinished = true;
		}

		public boolean isFinished() {
			return this.mIsFinished;
		}

		public void startAnimation(long paramLong) {
			this.mStartTime = SystemClock.currentThreadTimeMillis();
			this.mDuration = paramLong;
			this.mScale = ((float) (CustomerTestListView.this.mHeaderContainer
					.getBottom()) / CustomerTestListView.this.mHeaderHeight);
			this.mIsFinished = false;
			CustomerTestListView.this.post(this);
		}

		@Override
		public void run() {
			float f2;
			final ViewGroup.LayoutParams localLayoutParams;
			if ((!this.mIsFinished) && (this.mScale > 1.0D)) {
				float f1 = ((float) SystemClock.currentThreadTimeMillis() - (float) this.mStartTime)
						/ (float) this.mDuration;
				f2 = this.mScale
						- (this.mScale - 1.0F)
						* CustomerTestListView.sInterpolator
								.getInterpolation(f1);
				localLayoutParams = CustomerTestListView.this.mHeaderContainer
						.getLayoutParams();
				if (f2 > 1.0F) {
					localLayoutParams.height = ((int) (f2 * CustomerTestListView.this.mHeaderHeight));
					CustomerTestListView.this.mHeaderContainer
							.setLayoutParams(localLayoutParams);
					CustomerTestListView.this.post(this);
					return;
				}
				this.mIsFinished = true;
			} 
		}
	}
}
