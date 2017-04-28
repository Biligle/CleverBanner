package com.wgl.cleverbannerlibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * 小圆点(继承的V7包的ImageaView)
 * Author：Biligle.
 */

public class Dot extends AppCompatImageView {

    /**
     * 画笔
     */
    private Paint paint;

    /**
     * 放大放大倍数
     */
    public static float SCALE = 1.0f;



    public Dot(Context context) {
        super(context);
        init();
    }

    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Dot(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化画笔
     */
    private void init(){
        paint = new Paint();//创建画笔
        paint.setAntiAlias(true);//反锯齿
    }

    /**
     * 画笔颜色
     * @param color
     */
    public Dot setColor(int color) {
        if (null != paint) {
            paint.setColor(color);//画笔颜色
        }
        return this;
    }

    /**
     * 画圆
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float radius = getWidth() / 2;//半径
        float x = getWidth() / 2;//圆心的X坐标
        float y = getHeight() / 2;//圆心的Y坐标
        canvas.save();//保存设置
        float translateX = x;
        canvas.translate(translateX, y);//平移画笔起始点
        canvas.scale(SCALE, SCALE);//放大
        canvas.drawCircle(0, 0, radius, paint);//画圆
        canvas.restore();
    }
}
