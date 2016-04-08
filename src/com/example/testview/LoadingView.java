package com.example.testview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class LoadingView extends View {

	private int lineNumber = 3;
	private Paint defaultPaint;
	private Paint redPaint;
	private int height = 8; // 横条高度，
	private int width = 50; // 横条宽度
	private int spaceheight = 15; // 横条间隔
	private RectF r1, r2, r3, r4;
	int t1 = 0;
	int t2, t3, t4;
	int b1 = height;
	int viewWidth;
	int viewHeight;
	int x, y; // 起点坐标
	float sc = 0f; 
	private boolean success = false; // 是否加载完成
	private Bitmap successImg;

	int temp = 0;

	public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	public LoadingView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public LoadingView(Context context) {
		this(context, null);
	}

	private void init() {
		defaultPaint = new Paint();
		defaultPaint.setColor(Color.BLACK);
		redPaint = new Paint();
		redPaint.setColor(Color.RED);
		r1 = new RectF();
		r2 = new RectF();
		r3 = new RectF();
		successImg = BitmapFactory.decodeResource(getResources(),
				R.drawable.gou);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		x = (viewWidth - width) / 2;
		y = (viewHeight) / lineNumber;
		if (temp==3) {
			sc += 0.1;
			canvas.scale(sc, sc, (viewWidth - successImg.getWidth()) / 2,
					(viewHeight - successImg.getHeight()) / 2); //放大动画
			
			canvas.drawBitmap(successImg,
					(viewWidth - successImg.getWidth()) / 2,
					(viewHeight - successImg.getHeight()) / 2, redPaint); //打钩图片
			
			if (sc < 1.0f) {
				postInvalidateDelayed(20);
			}
			return;
		}
		r1.set(x, y, width + x, y + height);
		r2.set(x, y + spaceheight, width + x, y + height + spaceheight);
		r3.set(x, y + spaceheight * 2, width + x, y + height + spaceheight * 2);
		canvas.drawRect(r1, defaultPaint);
		canvas.drawRect(r2, defaultPaint);
		canvas.drawRect(r3, defaultPaint);
		canvas.save();
		if (t1 <= r1.right) {
			r1.set(r1.left, r1.top, t1++, r1.bottom);
			canvas.drawRect(r1, redPaint);
		} else if (t2 <= r2.right) {
			canvas.drawRect(r1, redPaint);
			r2.set(r2.left, r2.top, t2++, r2.bottom);
			canvas.drawRect(r2, redPaint);
		} else if (t3 <= r3.right) {
			canvas.drawRect(r1, redPaint);
			canvas.drawRect(r2, redPaint);
			r3.set(r3.left, r3.top, t3++, r3.bottom);
			canvas.drawRect(r3, redPaint);
		} else {
			t1 = 0;
			t2 = 0;
			t3 = 0;
			temp++;
		}

		postInvalidateDelayed(8);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		viewHeight = measureSpecHeight(heightMeasureSpec);
		viewWidth = measureSpecWidth(widthMeasureSpec);
		super.onMeasure(measureSpecWidth(widthMeasureSpec),
				measureSpecHeight(heightMeasureSpec));
	}

	public void isSuccess(boolean success) {
		this.success = success;
	}

	public void reset() {
		t1 = 0;
		t2 = 0;
		t3 = 0;
		success = false;
		sc = 0f;
	}

	private int measureSpecHeight(int heightMeasureSpec) {
		int resultSize = 100; // px
		int mode = MeasureSpec.getMode(heightMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		if (mode == MeasureSpec.EXACTLY) {
			resultSize = height;
		} else {
			if (mode == MeasureSpec.AT_MOST) {
				resultSize = Math.min(100, height);
			} else {
				resultSize = 100;
			}
		}
		return resultSize;
	}

	private int measureSpecWidth(int widthMeasureSpec) {
		int resultSize = 80; // px
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		if (mode == MeasureSpec.EXACTLY) {
			resultSize = width;
		} else {
			if (mode == MeasureSpec.AT_MOST) {
				resultSize = Math.min(80, width);
			} else {
				resultSize = 80;
			}
		}
		return resultSize;
	}

}
