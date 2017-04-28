package com.wgl.cleverbanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wgl.cleverbannerlibrary.BannerAdapter;
import com.wgl.cleverbannerlibrary.CleverBanner;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CleverBanner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> urllist = new ArrayList<>();
        urllist.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493695097&di=2f4cf75856a613f9103f937ef1472175&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.gamemei.com%2Fbackground%2Fuploads%2Fallimg%2F170328%2F24-1F32QJ303-50.jpg");
        urllist.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493695155&di=b3e452ae453d99b2ae531a69b47fb12c&imgtype=jpg&er=1&src=http%3A%2F%2Fi4.073img.com%2F170422%2F17598502_182153_1_lit.jpg");
        urllist.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1493695200&di=7b584c04029727eee98b5b67a95b2292&imgtype=jpg&er=1&src=http%3A%2F%2Fwww.gamemei.com%2Fbackground%2Fuploads%2Fallimg%2F170416%2F24-1F416224J50-L.jpg");
        banner = (CleverBanner) findViewById(R.id.banner);
        banner
                //传递图片url
                .setUrl(urllist)
                //触发点击事件
                .setBannerListener(new BannerAdapter.BannerClickListener() {
                    @Override
                    public void bannerClick(int position) {
                        Toast.makeText(
                                MainActivity.this,
                                "当前是第"+(position+1)+"页",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
}
