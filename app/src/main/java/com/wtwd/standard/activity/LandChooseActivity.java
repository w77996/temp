package com.wtwd.standard.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

public class LandChooseActivity extends BaseActivity {

    private Button btn_land_land;
    private Button btn_land_register;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_land;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);
        initView();
    }

    private void initView() {
        btn_land_land = (Button) findViewById(R.id.btn_land_land);
        btn_land_register = (Button) findViewById(R.id.btn_land_register);

        addListener();
    }

    private void addListener() {
        btn_land_land.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readyGo(LandActivity.class);

            }
        });

        btn_land_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            readyGo(RegisterAccountActivity.class);
            }
        });
    }


    @Override
    public View getSnackView() {
        return btn_land_land;
    }
}
