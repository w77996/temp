package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

public class LandActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_land_land;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_land2;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        initView();
    }

    private void initView() {
        btn_land_land = (Button) findViewById(R.id.btn_land_land);


        addListener();
    }

    private void addListener() {
        btn_land_land.setOnClickListener(this);
    }

    @Override
    public View getSnackView() {
        return btn_land_land;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (R.id.btn_land_land == id) {
            readyGo(MainActivity.class);
        }
    }
}
