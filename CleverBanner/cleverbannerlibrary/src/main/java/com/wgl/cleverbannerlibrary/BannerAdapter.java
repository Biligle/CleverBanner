package com.wgl.cleverbannerlibrary;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * CleverBanner的适配器
 * Author：Biligle.
 */

public class BannerAdapter extends PagerAdapter {

    private ArrayList<String> urlList;
    private Context context;
    private int placeIcon, errorIcon;
    private BannerClickListener listener;

    public BannerAdapter(Context context, ArrayList<String> urlList, int placeIcon, int errorIcon){
        this.context = context;
        this.placeIcon = placeIcon;
        this.errorIcon = errorIcon;
        if(null == urlList){
            this.urlList = new ArrayList<>();
        }else{
            this.urlList = urlList;
        }
    }

    @Override
    public int getCount() {
        //可以无限右滑
        return urlList.size() > 1 ? Integer.MAX_VALUE : urlList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if(urlList.size() >= 1){
            position %= urlList.size();
            try {
                Glide.with(context)
                        .load(urlList.get(position))
                        .placeholder(placeIcon)
                        .error(errorIcon)
                        .into(imageView);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            imageView.setImageResource(placeIcon);
        }

        final int pos = position;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != listener){
                    //给banner图添加点击事件
                    listener.bannerClick(pos);
                }
            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public void setListener(BannerClickListener listener){
        this.listener = listener;
    }

    public interface BannerClickListener {
        void bannerClick(int position);
    }
}
