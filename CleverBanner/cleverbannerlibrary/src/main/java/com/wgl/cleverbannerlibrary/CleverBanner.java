package com.wgl.cleverbannerlibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.shapes.Shape;
import android.os.Message;
import android.support.annotation.Px;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.TimerTask;
import java.util.logging.Handler;

/**
 * Banner轮询图
 * Author：Biligle.
 */

public class CleverBanner extends RelativeLayout {

    private Context context;
    /**
     * 开始自动播放的消息标志
     */
    private final int PAGECHANGE = 1;

    /**
     * banner默认高度
     */
    private int banner_height = 200;

    /**
     * 指示layout默认高度
     */
    private int indicator_layout_height = 20;

    /**
     * 指示点默认高度
     */
    private int indicator_height = 10;

    private int bigger_height = 10;

    /**
     * 圆点间距
     */
    private int margin = 10;

    /**
     * 占位图和加载出错图
     */
    private int placeIcon,errorIcon;

    /**
     * 选中颜色（默认红色）
     */
    private int selectedColor = Color.parseColor("#FF3333");

    /**
     * 未选中颜色（默认白色）
     */
    private int unSelectedColor = Color.parseColor("#FFFFFF");

    /**
     * 指示布局颜色
     */
    private int indicator_background_color = Color.GRAY;

    /**
     * 指示布局透明度
     */
    private float indicator_alpha = 0.5f;

    /**
     * true:自动播放banner
     */
    private boolean isDisplay = true;

    /**
     * 轮播时间间隔
     */
    private int time = 4000;

    /**
     * 图片url集合
     */
    private ArrayList<String> urlList;

    /**
     * 点击事件接口
     */
    private BannerAdapter.BannerClickListener listener;

    /**
     * 是否有指示点 true:有指示点
     */
    private boolean isIndicator = true;

    public CleverBanner(Context context) {
        super(context);
        this.context = context;
    }

    public CleverBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs);
    }

    public CleverBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs);
    }

    /**
     * 初始化参数属性
     */
    private void init(Context context, AttributeSet attrs){
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CleverBanner);
        indicator_layout_height = PxUtil.dp2px(context, a.getLayoutDimension(R.styleable.CleverBanner_indicatorLayout_height, indicator_layout_height));
        indicator_alpha = a.getFloat(R.styleable.CleverBanner_indicator_alpha, indicator_alpha);
        indicator_background_color = a.getColor(R.styleable.CleverBanner_indicator_background_color, indicator_background_color);
        banner_height = PxUtil.dp2px(context, a.getLayoutDimension(R.styleable.CleverBanner_banner_height, banner_height));
        placeIcon = a.getResourceId(R.styleable.CleverBanner_placeIcon,placeIcon);
        errorIcon = a.getResourceId(R.styleable.CleverBanner_errorIcon,placeIcon);
        selectedColor = a.getColor(R.styleable.CleverBanner_selected_color, selectedColor);
        unSelectedColor = a.getColor(R.styleable.CleverBanner_unSelected_color, unSelectedColor);
        time = a.getInteger(R.styleable.CleverBanner_time, time);
        isDisplay = a.getBoolean(R.styleable.CleverBanner_isDisplay, isDisplay);
        isIndicator = a.getBoolean(R.styleable.CleverBanner_isIndicator, isIndicator);
        margin = PxUtil.dp2px(context, a.getInteger(R.styleable.CleverBanner_margin, margin));
        bigger_height = PxUtil.dp2px(context, a.getInteger(R.styleable.CleverBanner_bigger_height, bigger_height));
        a.recycle();
        initWidget();
    }

    /**
     * 初始化窗口
     */
    private MyViewPager banner;
    private LinearLayout indicatorlayout;
    private void initWidget(){
        /**---------------------加入Banner--------------------------------------------------------------------------------------------------------------*/
        banner = new MyViewPager(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, banner_height);
        banner.setLayoutParams(params);
        addView(banner);
        /**---------------------加入指示点--------------------------------------------------------------------------------------------------------------*/
        if (isIndicator) {//多张图才会显示指示点
            indicatorlayout = new LinearLayout(context);
            RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, indicator_layout_height);
            params1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            indicatorlayout.setLayoutParams(params1);
            indicatorlayout.setOrientation(LinearLayout.HORIZONTAL);
            if(indicator_background_color != Color.GRAY){
                indicatorlayout.setBackgroundColor(indicator_background_color);
                indicatorlayout.setAlpha(indicator_alpha);
            }
            indicatorlayout.setGravity(Gravity.CENTER);
            addView(indicatorlayout);
        }
    }

    private BannerAdapter adapter;
    /**
     * 传入图片URl
     * @param urlList
     */
    public CleverBanner setUrl(ArrayList<String> urlList){
        this.urlList = urlList;
        adapter = new BannerAdapter(context,urlList,placeIcon,errorIcon);
        banner.setAdapter(adapter);
        //图片数量 > 一张，可以无限左滑
        if(urlList.size() > 1){
            banner.setCurrentItem(100*urlList.size());
            addDot(urlList.size());
        }
        banner.addOnPageChangeListener(onPageChangeListener);
        if(urlList.size() > 1 && isDisplay){
            startDisplay();
        }
        return this;
    }

    /**
     * 添加小圆点
     * @param size
     */
    private void addDot(int size){

        if(isIndicator){//有指示点布局才会添加圆点
            for (int i = 0; i < size; i++){
                Dot dot = new Dot(context);
                dot.setColor(unSelectedColor);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(20, 20);
                params2.leftMargin = margin;
                dot.setLayoutParams(params2);
                indicatorlayout.addView(dot);
            }
            //默认第一个指示点为选中颜色
            if(bigger_height > 10 && bigger_height < 20){
                ((Dot) indicatorlayout.getChildAt(0)).setLayoutParams(new LinearLayout.LayoutParams(bigger_height, bigger_height));
            }
            ((Dot) indicatorlayout.getChildAt(0))
                    .setColor(selectedColor)
                    .postInvalidate();
            indicatorlayout.postInvalidate();//刷新indicatorlayout（LinearLayout）界面
        }

    }

    /**
     * 触发点击事件
     * @param listener
     * @return
     */
    public CleverBanner setBannerListener(BannerAdapter.BannerClickListener listener){
        if(null != adapter){
            adapter.setListener(listener);
        }
        return this;
    }

    /**
     * 开始播放bannerm,只播放一次
     */
    private boolean flag = false;
    public void startDisplay(){
        if(!flag && isDisplay){
            handler.sendEmptyMessageDelayed(1, (long)time);
            flag = true;
        }
    }

    /**
     * 停止播放banner
     */
    public void stopDisplay(){
        handler.removeMessages(PAGECHANGE);
    }

    /**
     * banner滑动事件
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            position %= urlList.size();
            if(isIndicator){
                LinearLayout.LayoutParams bigger = new LinearLayout.LayoutParams(bigger_height, bigger_height);
                bigger.leftMargin = margin;
                LinearLayout.LayoutParams smaller = new LinearLayout.LayoutParams(indicator_height, indicator_height);
                smaller.leftMargin = margin;
                for (int pos = 0; pos < urlList.size(); pos++) {
                    if(pos == position){
                        if(bigger_height > 10 && bigger_height < 20){
                            ((Dot) indicatorlayout.getChildAt(position)).setLayoutParams(bigger);
                        }
                        ((Dot) indicatorlayout.getChildAt(position))
                                .setColor(selectedColor)
                                .postInvalidate();
                    } else {
                        if (bigger_height > 10 && bigger_height < 20) {
                            ((Dot) indicatorlayout.getChildAt(pos)).setLayoutParams(smaller);
                        }
                        ((Dot) indicatorlayout.getChildAt(pos))
                                .setColor(unSelectedColor)
                                .postInvalidate();
                    }
                    indicatorlayout.postInvalidate();//刷新indicatorlayout（LinearLayout）界面
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    /**
     * handler发送消息，开始播放banner
     */
    private android.os.Handler handler = new android.os.Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case PAGECHANGE:
                    try {
                        int totalcount = urlList.size();// autoChangeViewPager.getChildCount();
                        int currentItem = banner.getCurrentItem();
                        int toItem = currentItem + 1 == totalcount
                                    ? 0
                                    : currentItem + 1;
                        banner.setCurrentItem(toItem, true);
                        this.sendEmptyMessageDelayed(1, (long)time);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };


    /**
     * 测量布局CleverBanner（为了设置宽高属性）
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        RelativeLayout.LayoutParams params = (LayoutParams) getLayoutParams();
        //CleverBanner布局高度，等于较小者
        params.height = banner_height < params.height ? banner_height : params.height;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setLayoutParams(params);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            //系统测量
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

}
