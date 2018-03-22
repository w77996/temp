package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

public class DeviceUnbundledActivity extends BaseActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_device_unbundled;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        text_tool_bar_title.setText("设备解绑");
        img_tool_bar_right.setVisibility(View.GONE);

    }

    @Override
    public View getSnackView() {
        return null;
    }
}
