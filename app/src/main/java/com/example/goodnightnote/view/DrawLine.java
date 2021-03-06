package com.example.goodnightnote.view;

/**

 *Time:2019/04/18
 *Author: xiaoxi
 *Description:

 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

@SuppressLint("AppCompatCustomView")
public class DrawLine extends EditText {
	private Paint mPaint;

	public DrawLine(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint();

		mPaint.setColor(Color.BLACK);
		mPaint.setStyle(Paint.Style.STROKE);

	}

	// 此处线字交叉是由于行间距引起,注意使用getLineHeight()得到行高。
	public void onDraw(Canvas canvas) {
		int count = getLineCount();
		for (int i = 0; i < count + 11; i++) {
			float[] pts = { 15.0F, (i + 1) * getLineHeight(), this.getWidth() - 20.0F, (i + 1) * getLineHeight() };

			// i*50-280,50增加则行间距大285增加则线靠上。
			canvas.drawLines(pts, mPaint);
		}
		super.onDraw(canvas);
	}
}
