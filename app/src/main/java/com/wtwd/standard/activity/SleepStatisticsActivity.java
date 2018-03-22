package com.wtwd.standard.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.entity.SleepStatisticEntity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.utils.DateUtil;
import com.wtwd.standard.utils.Utils;
import com.wtwd.standard.widget.hscorll.ScrollPickerView;
import com.wtwd.standard.widget.hscorll.SleepScrollPicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SleepStatisticsActivity extends BaseActivity implements View.OnClickListener {
    private CollapsingToolbarLayoutState state;

    private AppBarLayout app_bar_sleep;
    private LinearLayout lin_sleep_time;
    private LinearLayout lin_sleep_bottom_recf;
    private LinearLayout lin_column;
    private LinearLayout lin_column_title;
    private LinearLayout lin_selector_date;

    private LinearLayout lin_sleep_day;
    private LinearLayout lin_sleep_week;
    private LinearLayout lin_sleep_month;

    private TextView text_sleep_day;
    private TextView text_sleep_week;
    private TextView text_sleep_month;
    private TextView text_date;

    private SleepScrollPicker picker_sleep_statistic;

    /**
     * 测试数据
     */
    private List<Float> verticalList = new ArrayList<>();
    private List<String> horizontalList = new ArrayList<>();
    private Random random;

    private int count = 100;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_sleep_statistics;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);
        initToolBar();
        initView();

    }

    private void initView() {
        random = new Random();
        app_bar_sleep = (AppBarLayout) findViewById(R.id.app_bar_sleep);
        lin_sleep_time = (LinearLayout) findViewById(R.id.lin_sleep_time);
        lin_sleep_bottom_recf = (LinearLayout) findViewById(R.id.lin_sleep_bottom_recf);

        lin_column = (LinearLayout) findViewById(R.id.lin_column);
        lin_column_title = (LinearLayout) findViewById(R.id.lin_column_title);
        lin_selector_date = (LinearLayout) findViewById(R.id.lin_selector_date);

        lin_sleep_day = (LinearLayout) findViewById(R.id.lin_sleep_day);
        lin_sleep_week = (LinearLayout) findViewById(R.id.lin_sleep_week);
        lin_sleep_month = (LinearLayout) findViewById(R.id.lin_sleep_month);

        text_sleep_day = (TextView) findViewById(R.id.text_sleep_day);
        text_sleep_week = (TextView) findViewById(R.id.text_sleep_week);
        text_sleep_month = (TextView) findViewById(R.id.text_sleep_month);
        text_date = (TextView) findViewById(R.id.text_date);

        picker_sleep_statistic = (SleepScrollPicker) findViewById(R.id.picker_sleep_statistic);


        addListener();

        chooseDataDisplay(DISPLAY_FOR_DAY);
    }

    private void addListener() {
        lin_sleep_day.setOnClickListener(this);
        lin_sleep_week.setOnClickListener(this);
        lin_sleep_month.setOnClickListener(this);

        app_bar_sleep.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        lin_sleep_time.setVisibility(View.VISIBLE);
                        text_tool_bar_title.setText("睡眠统计");

                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        lin_sleep_time.setVisibility(View.GONE);
                        text_tool_bar_title.setText("8时35分");
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if (state == CollapsingToolbarLayoutState.COLLAPSED) {
                            lin_sleep_time.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });

        picker_sleep_statistic.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
                if (mDateList.size() <= 0) {
                    return;
                }
                text_date.setText(mDateList.get(position));
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, (app_bar_sleep.getHeight() - (Utils.getActionBarHeight(this)) + Utils.getStatusBarHeight(this)));
        lin_sleep_bottom_recf.setLayoutParams(mLayoutParams);
    }

    private void initToolBar() {
        text_tool_bar_title.setText("睡眠统计");
        img_tool_bar_right.setImageResource(R.mipmap.main_page_share);
        img_tool_bar_right.setVisibility(View.VISIBLE);
    }

    @Override
    public View getSnackView() {
        return app_bar_sleep;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.lin_sleep_day == id) {
            chooseDataDisplay(DISPLAY_FOR_DAY);
        } else if (R.id.lin_sleep_week == id) {
            chooseDataDisplay(DISPLAY_FOR_WEEK);
        } else if (R.id.lin_sleep_month == id) {
            chooseDataDisplay(DISPLAY_FOR_MONTH);
        }


    }

    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private void setLinSleepDaySelectorState(boolean mSelected) {
        lin_sleep_day.setSelected(mSelected);
        lin_sleep_day.setEnabled(!mSelected);
        if (mSelected) {
            text_sleep_day.setTextColor(ContextCompat.getColor(this, R.color.sleep_purple));
        } else {
            text_sleep_day.setTextColor(ContextCompat.getColor(this, R.color.alpha_black_6));
        }
    }

    private void setLinSleepWeekSelectorState(boolean mSelected) {
        lin_sleep_week.setSelected(mSelected);
        lin_sleep_week.setEnabled(!mSelected);
        if (mSelected) {
            text_sleep_week.setTextColor(ContextCompat.getColor(this, R.color.sleep_purple));
        } else {
            text_sleep_week.setTextColor(ContextCompat.getColor(this, R.color.alpha_black_6));
        }
    }

    private void setLinSleepMonthSelectorState(boolean mSelected) {
        lin_sleep_month.setSelected(mSelected);
        lin_sleep_month.setEnabled(!mSelected);
        if (mSelected) {
            text_sleep_month.setTextColor(ContextCompat.getColor(this, R.color.sleep_purple));
        } else {
            text_sleep_month.setTextColor(ContextCompat.getColor(this, R.color.alpha_black_6));
        }
    }

    private static final int DISPLAY_FOR_DAY = 0x01;
    private static final int DISPLAY_FOR_WEEK = 0x02;
    private static final int DISPLAY_FOR_MONTH = 0x03;
    private int mCurrentChooseType;

    private void chooseDataDisplay(int type) {
        if (DISPLAY_FOR_DAY == type) {
            setLinSleepDaySelectorState(true);
            setLinSleepWeekSelectorState(false);
            setLinSleepMonthSelectorState(false);
        } else if (DISPLAY_FOR_WEEK == type) {
            setLinSleepDaySelectorState(false);
            setLinSleepWeekSelectorState(true);
            setLinSleepMonthSelectorState(false);
        } else if (DISPLAY_FOR_MONTH == type) {
            setLinSleepDaySelectorState(false);
            setLinSleepWeekSelectorState(false);
            setLinSleepMonthSelectorState(true);
        }
        setStringScrollPicker(type);
        mCurrentChooseType = type;
    }

    private List<SleepStatisticEntity> mPrimaryList = new ArrayList<>();
    List<String> mDateList = new ArrayList<>();

    private void setStringScrollPicker(int type) {

        String mStartDate = "2017/02/15";
        String mEndDate = "2018/03/08";
        if (DISPLAY_FOR_DAY == type) {
            mDateList.clear();
            mPrimaryList.clear();

            mDateList = DateUtil.betweenDays(mStartDate, mEndDate);

            for (int i = 0; i < mDateList.size(); i++) {
//                String mDateStr = mPrimaryList.get(i);
//                mDateList.add(i, mDateStr.substring(5, mDateStr.length()));
                String time = mDateList.get(i);
                SleepStatisticEntity mEn = new SleepStatisticEntity();
                mEn.setmPeriod(time.substring(5, time.length()));
                mEn.setmDeepSleepTime((int) (6 * Math.random()));
                mEn.setmLightSleepTime((int) (6 * Math.random()));
                mPrimaryList.add(mEn);
            }
            picker_sleep_statistic.setVisibleItemCount(7);
        } else if (DISPLAY_FOR_WEEK == type) {
            mDateList.clear();
            mPrimaryList.clear();
            mDateList = DateUtil.betweenDaysForWeek(mStartDate, mEndDate);
            for (int i = 0; i < mDateList.size(); i++) {
                String mWeekStr = mDateList.get(i);
                String[] mSplitStr = mWeekStr.split("-");

                SleepStatisticEntity mEn = new SleepStatisticEntity();

                mEn.setmPeriod(mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mSplitStr[1].substring(5, mEndDate.length()));
//                mEn.setmPeriod(mDateList.get(i));
                mEn.setmDeepSleepTime((int) (6 * Math.random()));
                mEn.setmLightSleepTime((int) (6 * Math.random()));
                mPrimaryList.add(mEn);
            }

            picker_sleep_statistic.setVisibleItemCount(5);
        } else if (DISPLAY_FOR_MONTH == type) {

            mDateList.clear();
            mPrimaryList.clear();
            mDateList = DateUtil.betweenDaysForMonth(mStartDate, mEndDate);
            for (int i = 0; i < mDateList.size(); i++) {
                String mMonthStr = mDateList.get(i);


                SleepStatisticEntity mEn = new SleepStatisticEntity();

                mEn.setmPeriod(mMonthStr.substring(0, mMonthStr.length() - 3));
//                mEn.setmPeriod(mDateList.get(i));
                mEn.setmDeepSleepTime((int) (6 * Math.random()));
                mEn.setmLightSleepTime((int) (6 * Math.random()));
                mPrimaryList.add(mEn);
            }
            picker_sleep_statistic.setVisibleItemCount(7);
        }
        picker_sleep_statistic.setHorizontal(true);
        picker_sleep_statistic.setData(mPrimaryList);
        picker_sleep_statistic.setSelectedPosition(mPrimaryList.size() - 1);
    }

}
