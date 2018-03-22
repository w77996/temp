package com.wtwd.standard.activity;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.entity.StepEntity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.utils.DateUtil;
import com.wtwd.standard.utils.StepStatisticColumnDisplay;
import com.wtwd.standard.utils.stepcolumn.AbstractStepColumn;
import com.wtwd.standard.utils.stepcolumn.DayStepColumnDisPlay;
import com.wtwd.standard.utils.stepcolumn.MonthStepCoulumn;
import com.wtwd.standard.utils.stepcolumn.WeekStepColumnDisplay;
import com.wtwd.standard.widget.RingProgressView;
import com.wtwd.standard.widget.hscorll.ScrollPickerView;
import com.wtwd.standard.widget.hscorll.StringScrollPicker;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.view.ColumnChartView;

public class StepStatisticsActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout lin_step_day;
    private LinearLayout lin_step_week;
    private LinearLayout lin_step_month;

    /**
     * 切换日，周，月时文字对象
     */
    private TextView text_step_day;
    private TextView text_step_week;
    private TextView text_step_month;

    /**
     * 显示当前日期
     */
    private TextView text_current_date;


    private RingProgressView ring_progress_step;

    private ColumnChartView column_chart_step_statistic;
    private StringScrollPicker scroll_picker_choose;
    private String[] whole_time;

    private StepStatisticColumnDisplay mStepStatisticColumnDisplay;

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_step_statistics;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);
        initToolBar();
        initView();
    }

    private void initToolBar() {
        mStepStatisticColumnDisplay = new StepStatisticColumnDisplay(this);
        whole_time = getResources().getStringArray(R.array.whole_point);
        text_tool_bar_title.setText("步数统计");
        img_tool_bar_right.setImageResource(R.mipmap.main_page_share);
        img_tool_bar_right.setVisibility(View.VISIBLE);
    }

    private void initView() {
        lin_step_day = (LinearLayout) findViewById(R.id.lin_step_day);
        lin_step_week = (LinearLayout) findViewById(R.id.lin_step_week);
        lin_step_month = (LinearLayout) findViewById(R.id.lin_step_month);

        text_step_day = (TextView) findViewById(R.id.text_step_day);
        text_step_week = (TextView) findViewById(R.id.text_step_week);
        text_step_month = (TextView) findViewById(R.id.text_step_month);

        text_current_date = (TextView) findViewById(R.id.text_current_date);


        scroll_picker_choose = (StringScrollPicker) findViewById(R.id.scroll_picker_choose);
        ring_progress_step = (RingProgressView) findViewById(R.id.ring_progress_step);
        column_chart_step_statistic = (ColumnChartView) findViewById(R.id.column_chart_step_statistic);

        initColumnChartView(column_chart_step_statistic);
        mStepStatisticColumnDisplay.displayDataOnTheWindow(column_chart_step_statistic, getSteps());

        ring_progress_step.setDisplayPercentageSymbol(false);
        ring_progress_step.setCurrentNumAndTargetNum(4994, 8000);

        chooseDataDisplay(DISPLAY_FOR_DAY);

        addListener();

    }

    private void addListener() {
        lin_step_day.setOnClickListener(this);
        lin_step_week.setOnClickListener(this);
        lin_step_month.setOnClickListener(this);

        scroll_picker_choose.setOnSelectedListener(new ScrollPickerView.OnSelectedListener() {
            @Override
            public void onSelected(ScrollPickerView scrollPickerView, int position) {
//                mStepStatisticColumnDisplay.displayDataOnTheWindow(column_chart_step_statistic, getSteps());

//                if (DISPLAY_FOR_DAY == mCurrentChooseType) {
//                    DayStepColumnDisPlay.getDayStepInstance(StepStatisticsActivity.this).displayDataOnWindow(column_chart_step_statistic, mPrimaryList.get(position));
//                } else if (DISPLAY_FOR_WEEK == mCurrentChooseType) {
//                    WeekStepColumnDisplay.getWeekInstance(StepStatisticsActivity.this).displayDataOnWindow(column_chart_step_statistic, mPrimaryList.get(position));
//                } else if (DISPLAY_FOR_MONTH == mCurrentChooseType) {
//                    MonthStepCoulumn.getInstance(StepStatisticsActivity.this).displayDataOnWindow(column_chart_step_statistic, mPrimaryList.get(position));
//                }
                if (mPrimaryList.size() <= 0) {
//                    text_current_date.setText(mPrimaryList.get(position));
                    return;
                }
                mStepColumn.displayDataOnWindow(column_chart_step_statistic, mPrimaryList.get(position));


                String mDate = mPrimaryList.get(position);
                if (mCurrentChooseType == DISPLAY_FOR_MONTH) {
                    text_current_date.setText(mDate.substring(0, mDate.length() - 3));
                } else {
                    text_current_date.setText(mDate);
                }
            }
        });

    }


    /**
     * 初始化柱状图
     */
    private void initColumnChartView(ColumnChartView mColumnChartView) {
        mColumnChartView.setZoomEnabled(false);//手势缩放
        mColumnChartView.setInteractive(true);//设置图表是可以交互的（拖拽，缩放等效果的前提）
        mColumnChartView.setZoomType(ZoomType.HORIZONTAL);
        mColumnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mColumnChartView.setMaxZoom((float) 4);
    }

    private List<StepEntity> getSteps() {
        List<StepEntity> mSteps = new ArrayList<>();
        for (String aWhole_time : whole_time) {
            StepEntity mEn = new StepEntity();
            mEn.setWhole_time(aWhole_time);
            mEn.setStep_num((int) (Math.random() * 2000) + "");
            mSteps.add(mEn);
        }
        return mSteps;

    }


    @Override
    public View getSnackView() {
        return text_tool_bar_title;
    }


    private void setLinStepDayState(boolean mSelected) {
        if (mSelected) {
            lin_step_day.setSelected(true);
            text_step_day.setTextColor(ContextCompat.getColor(this, R.color.step_blue));
        } else {
            lin_step_day.setSelected(false);
            text_step_day.setTextColor(ContextCompat.getColor(this, R.color.alpha_black_6));
        }
    }

    private void setLinStepWeekState(boolean mSelected) {
        if (mSelected) {
            lin_step_week.setSelected(true);
            text_step_week.setTextColor(ContextCompat.getColor(this, R.color.step_blue));
        } else {
            lin_step_week.setSelected(false);
            text_step_week.setTextColor(ContextCompat.getColor(this, R.color.alpha_black_6));
        }
    }

    private void setLinStepMonthState(boolean mSelected) {
        if (mSelected) {
            lin_step_month.setSelected(true);
            text_step_month.setTextColor(ContextCompat.getColor(this, R.color.step_blue));
        } else {
            lin_step_month.setSelected(false);
            text_step_month.setTextColor(ContextCompat.getColor(this, R.color.alpha_black_6));
        }
    }

    private static final int DISPLAY_FOR_DAY = 0x01;
    private static final int DISPLAY_FOR_WEEK = 0x02;
    private static final int DISPLAY_FOR_MONTH = 0x03;
    private int mCurrentChooseType;

    private void chooseDataDisplay(int type) {
        if (DISPLAY_FOR_DAY == type) {
            setLinStepDayState(true);
            setLinStepWeekState(false);
            setLinStepMonthState(false);
        } else if (DISPLAY_FOR_WEEK == type) {
            setLinStepDayState(false);
            setLinStepWeekState(true);
            setLinStepMonthState(false);
        } else if (DISPLAY_FOR_MONTH == type) {
            setLinStepDayState(false);
            setLinStepWeekState(false);
            setLinStepMonthState(true);
        }
        setStringScrollPicker(type);
        mCurrentChooseType = type;
    }

    private List<String> mPrimaryList = new ArrayList<>();
    private AbstractStepColumn mStepColumn;

    private void setStringScrollPicker(int type) {
        List<String> mDateList = new ArrayList<>();
        String mStartDate = "2017/02/15";
        String mEndDate = "2018/03/08";
        if (DISPLAY_FOR_DAY == type) {
            mStepColumn = DayStepColumnDisPlay.getDayStepInstance(this);
            mDateList.clear();
            mPrimaryList.clear();

            mPrimaryList = DateUtil.betweenDays(mStartDate, mEndDate);

            for (int i = 0; i < mPrimaryList.size(); i++) {
                String mDateStr = mPrimaryList.get(i);
                mDateList.add(i, mDateStr.substring(5, mDateStr.length()));
            }
            scroll_picker_choose.setVisibleItemCount(7);
        } else if (DISPLAY_FOR_WEEK == type) {
            mStepColumn = WeekStepColumnDisplay.getWeekInstance(this);
            mDateList.clear();
            mPrimaryList.clear();
            mPrimaryList = DateUtil.betweenDaysForWeek(mStartDate, mEndDate);
            for (int i = (mPrimaryList.size() - 1); i >= 0; i--) {
                String mWeekStr = mPrimaryList.get(i);
                String[] mSplitStr = mWeekStr.split("-");
//                if (0 == i) {
//                    mDateList.add(0, mStartDate.substring(5, mStartDate.length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
//                } else if (i == (mPrimaryList.size() - 1)) {
//                    mDateList.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mEndDate.substring(5, mEndDate.length()));
//                } else {
//                    mDateList.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
//                }
                mDateList.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
            }

            scroll_picker_choose.setVisibleItemCount(3);
        } else if (DISPLAY_FOR_MONTH == type) {
            mStepColumn = MonthStepCoulumn.getInstance(this);
            mDateList.clear();
            mPrimaryList.clear();
            mPrimaryList = DateUtil.betweenDaysForMonth(mStartDate, mEndDate);
            for (int i = 0; i < mPrimaryList.size(); i++) {
                String mCurrentMonth = mPrimaryList.get(i);
                mDateList.add(mCurrentMonth.substring(0, mCurrentMonth.length() - 3));
            }
            scroll_picker_choose.setVisibleItemCount(5);
        }
        scroll_picker_choose.setHorizontal(true);
        scroll_picker_choose.setData(mDateList);
        scroll_picker_choose.setSelectedPosition(mDateList.size() - 1);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.lin_step_day == id) {
            chooseDataDisplay(DISPLAY_FOR_DAY);
        } else if (R.id.lin_step_week == id) {
            chooseDataDisplay(DISPLAY_FOR_WEEK);
        } else if (R.id.lin_step_month == id) {
            chooseDataDisplay(DISPLAY_FOR_MONTH);
        }


    }
}
