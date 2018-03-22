package com.wtwd.standard.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.utils.DialogUtil;
import com.wtwd.standard.utils.Utils;
import com.wtwd.standard.widget.RingProgressView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Line;

public class MyDeviceActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_unbundled_device;
    private LinearLayout lin_call_phone;
    private LinearLayout lin_find_device;
    private LinearLayout lin_alarm_clock;
    private LinearLayout lin_wearing_method;
    private TextView text_wearing_method;

    private Dialog mSelectorDialog;
    private RingProgressView ring_progress_battery;
    List<Thread> mList = new ArrayList<>();

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_my_device;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);

        text_tool_bar_title.setText("我的设备");
        img_tool_bar_right.setVisibility(View.GONE);

        initView();
    }

    private void initView() {
        btn_unbundled_device = (Button) findViewById(R.id.btn_unbundled_device);
        lin_call_phone = (LinearLayout) findViewById(R.id.lin_call_phone);
        lin_find_device = (LinearLayout) findViewById(R.id.lin_find_device);
        lin_alarm_clock = (LinearLayout) findViewById(R.id.lin_alarm_clock);
        ring_progress_battery = (RingProgressView) findViewById(R.id.ring_progress_battery);

        lin_wearing_method = (LinearLayout) findViewById(R.id.lin_wearing_method);
        text_wearing_method = (TextView) findViewById(R.id.text_wearing_method);


        ring_progress_battery.setDisplayPercentageSymbol(true);
        ring_progress_battery.setBottomUnitText("设备未连接");
        ring_progress_battery.setCurrentNumAndTargetNum(80, 100);

        mSelectorDialog = new Dialog(this, R.style.MyCommonDialog);
        addListener();
    }

    private void addListener() {
        btn_unbundled_device.setOnClickListener(this);
        lin_call_phone.setOnClickListener(this);
        lin_find_device.setOnClickListener(this);
        lin_alarm_clock.setOnClickListener(this);
        lin_wearing_method.setOnClickListener(this);
        ring_progress_battery.setOnClickListener(this);
    }


    @Override
    public View getSnackView() {
        return tool_bar;
    }

    @Override
    public void onClick(View v) {
        int mViewId = v.getId();
        switch (mViewId) {
            case R.id.btn_unbundled_device:
                readyGo(DeviceUnbundledActivity.class);
                break;

            case R.id.lin_call_phone:
                readyGo(IncomingCallActivity.class);
                break;

            case R.id.lin_find_device:
                readyGo(FindDeviceActivity.class);
                break;

            case R.id.lin_alarm_clock:
                readyGo(AlarmClockActivity.class);  
                break;

            case R.id.lin_wearing_method:
                DialogUtil.chooseHandsDialog(MyDeviceActivity.this, mSelectorDialog
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                text_wearing_method.setText("左手");
                                mSelectorDialog.dismiss();
                            }
                        }

                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                text_wearing_method.setText("右手");
                                mSelectorDialog.dismiss();
                            }
                        });

            case R.id.ring_progress_battery:
                DialogUtil.commitDialog(MyDeviceActivity.this, mSelectorDialog, "设备未连接,是否打开蓝牙?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSelectorDialog.dismiss();
                    }
                });


                break;


        }


    }
}
