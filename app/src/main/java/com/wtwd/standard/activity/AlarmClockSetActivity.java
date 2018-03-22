package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.widget.hscorll.ScrollPickerView;
import com.wtwd.standard.widget.hscorll.StringScrollPicker;

import java.util.ArrayList;
import java.util.List;

public class AlarmClockSetActivity extends BaseActivity {
    private StringScrollPicker string_scroll_hour;
    private StringScrollPicker string_scroll_minute;


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_alarm_clock_set;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        initToolBar();
        initView();
    }

    private void initToolBar() {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        text_tool_bar_title.setText("设置闹钟");
    }

    private void initView() {
        string_scroll_hour = (StringScrollPicker) findViewById(R.id.string_scroll_hour);
        string_scroll_minute = (StringScrollPicker) findViewById(R.id.string_scroll_minute);
        string_scroll_minute.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                Log.e("TAG", "minute");
            }
        });

        string_scroll_hour.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                Log.e("TAG", "hour");
            }
        });


        showTimeChoose();
    }

    private void showTimeChoose() {
        List<String> mHours = new ArrayList<>();
        List<String> mMinutes = new ArrayList<>();

        for (int i = 0; i < 24; i++) {
            mHours.add(addZeroToSingleNumber(i + ""));
        }

        for (int i = 0; i < 60; i++) {
            mMinutes.add(addZeroToSingleNumber(i + ""));
        }


        string_scroll_hour.setData(mHours);
        string_scroll_minute.setData(mMinutes);

    }


    @Override
    public View getSnackView() {
        return null;
    }

    private String addZeroToSingleNumber(String number) {
        String mStr = number;
        if (1 == mStr.length()) {
            mStr = "0" + number;
        }
        return mStr;
    }

}
