package com.wtwd.standard.activity;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

public class FindDeviceActivity extends BaseActivity {

    private ImageView img_find_device_animation;
    private TextView text_find_device;
    private boolean mAnimationStarted;

    private AnimationDrawable mAnimationDrawable;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_find_device;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        initView();
    }

    private void initView() {
        text_tool_bar_title.setText("寻找手环");
        img_find_device_animation = (ImageView) findViewById(R.id.img_find_device_animation);
        text_find_device = (TextView) findViewById(R.id.text_find_device);

        img_find_device_animation.setBackgroundResource(R.drawable.find_device_animation);

        mAnimationDrawable = (AnimationDrawable) img_find_device_animation.getBackground();

        text_find_device.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnimationStarted) {
                    mAnimationStarted = false;
                    text_find_device.setText("开始");
                    mAnimationDrawable.stop();
                } else {
                    mAnimationStarted = true;
                    text_find_device.setText("取消");
                    mAnimationDrawable.start();
                }
            }
        });

    }


    @Override
    public View getSnackView() {
        return text_find_device;
    }
}
