package com.wtwd.standard.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wtwd.standard.R;
import com.wtwd.standard.activity.HeartRateActivity;
import com.wtwd.standard.activity.SleepStatisticsActivity;
import com.wtwd.standard.activity.StepStatisticsActivity;
import com.wtwd.standard.base.BaseFragment;
import com.wtwd.standard.entity.StepEntity;
import com.wtwd.standard.utils.MainPageStepColumnDisplay;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Administrator on 2018/a1/26 0026.
 */

public class MainPageFragment extends BaseFragment implements View.OnClickListener {
    private static MainPageFragment mInstance;
    private RelativeLayout relative_current_step;
    private LinearLayout lin_heart_rate;
    private LinearLayout lin_sleep_statistic;
    private ColumnChartView column_view_main_page_step;

    private MainPageStepColumnDisplay mDisplay;

    private String[] whole_time;

    public static MainPageFragment getMainPageFragment() {
        if (null == mInstance) {
            mInstance = new MainPageFragment();
        }
        return mInstance;
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_main_page;
    }

    @Override
    public void initFragmentView(View mView) {
        mDisplay = new MainPageStepColumnDisplay(getActivity());

        initToolBar();
        initView(mView);

    }

    private void initToolBar() {
        img_tool_bar_right.setVisibility(View.VISIBLE);
        img_tool_bar_left.setVisibility(View.VISIBLE);
        img_tool_bar_left.setImageResource(R.mipmap.main_page_msg);
        img_tool_bar_right.setImageResource(R.mipmap.main_page_share);
        text_tool_bar_title.setText("蓝牙手环");
    }

    private void initView(View mView) {
        whole_time = getActivity().getResources().getStringArray(R.array.whole_point);
        relative_current_step = (RelativeLayout) mView.findViewById(R.id.relative_current_step);
        lin_heart_rate = (LinearLayout) mView.findViewById(R.id.lin_heart_rate);
        lin_sleep_statistic = (LinearLayout) mView.findViewById(R.id.lin_sleep_statistic);
        column_view_main_page_step = (ColumnChartView) mView.findViewById(R.id.column_view_main_page_step);
        mDisplay.setColumnChartViewData(column_view_main_page_step, getSteps());
        addListener();
    }

    private List<StepEntity> getSteps() {
        List<StepEntity> mSteps = new ArrayList<>();
        for (String aWhole_time : whole_time) {
            StepEntity mEn = new StepEntity();
            mEn.setWhole_time(aWhole_time);
            mEn.setStep_num((int) (Math.random() * 1000) + "");
            mSteps.add(mEn);
        }
        return mSteps;
    }


    private void addListener() {
        relative_current_step.setOnClickListener(this);
        lin_heart_rate.setOnClickListener(this);
        lin_sleep_statistic.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (R.id.relative_current_step == id) {
            readyGo(StepStatisticsActivity.class);
        } else if (R.id.lin_heart_rate == id) {
            readyGo(HeartRateActivity.class);
        } else if (R.id.lin_sleep_statistic == id) {
            readyGo(SleepStatisticsActivity.class);
        }
    }
}
