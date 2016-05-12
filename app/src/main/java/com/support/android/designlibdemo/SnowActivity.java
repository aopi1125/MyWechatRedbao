package com.support.android.designlibdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.support.android.designlibdemo.view.MySurfaceView;

/**
 * Created by pc on 2016/5/12.
 */
public class SnowActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 隐藏状态栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 把Activity的标题去掉
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置布局
        this.setContentView(new MySurfaceView(this));
    }
}