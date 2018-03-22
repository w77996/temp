package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

public class RegisterActivity extends BaseActivity {


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_register;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);


    }

    @Override
    public View getSnackView() {
        return null;
    }
}
