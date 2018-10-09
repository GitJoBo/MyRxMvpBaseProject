package com.jobo.myrxmvpbaseproject.widget.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import com.jobo.httplib.utils.LogUtils;

/**
 * 百分比进度条  中间显示百分比(会ANR//TODO)
 * Created by JoBo on 2018/6/8.
 */

public class PercentProgressbar extends ProgressBar {

    String text;
    Paint mTextPaint;

    public PercentProgressbar(Context context) {
        super(context);
        initText();
    }

    public PercentProgressbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initText();
    }

    /**
     * @param progress 过大会出现负值
     */
    @Override
    public synchronized void setProgress(int progress) {
        super.setProgress(progress);
//        setText(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect();
//        try {
        this.mTextPaint.getTextBounds(this.text, 0, this.text.length(), rect);
//        }catch (Exception e){
//            this.mTextPaint.getTextBounds(this.text, 0, 0, rect);
//        }
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mTextPaint);
    }

    private void initText() {
        this.text = "";
        this.mTextPaint = new TextPaint();
        this.mTextPaint.setColor(Color.RED);
        //还能够设置字体的其它属性
    }

    private void setText(int progress) {
        int i = (progress * 100) / this.getMax();
        this.text = String.valueOf(i) + "%";
    }

    public void setTextProgress(int progress) {
        if (progress > 50) {
            this.mTextPaint.setColor(Color.WHITE);
        } else {
            this.mTextPaint.setColor(Color.RED);
        }
        this.text = String.valueOf(progress) + "%";
    }


}
