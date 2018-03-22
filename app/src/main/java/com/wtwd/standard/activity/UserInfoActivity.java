package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

public class UserInfoActivity extends BaseActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_user_info;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);
        text_tool_bar_title.setText("个人信息");
    }

    @Override
    public View getSnackView() {
        return null;
    }
}
