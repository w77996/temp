package com.wtwd.standard.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.utils.Utils;

public class FeedBackActivity extends BaseActivity {


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_feed_back;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        text_tool_bar_title.setText("意见反馈");
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);

    }

    @Override
    public View getSnackView() {
        return null;
    }
}
