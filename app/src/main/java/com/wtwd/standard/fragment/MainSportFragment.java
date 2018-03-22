package com.wtwd.standard.fragment;

import android.media.Image;
import android.media.MediaCasStateException;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.view.menu.MenuPresenter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wtwd.standard.R;
import com.wtwd.standard.activity.SportOutDoorActivity;
import com.wtwd.standard.adapter.SportFragmentViewPagerAdapter;
import com.wtwd.standard.adapter.SportRecyclerAdapter;
import com.wtwd.standard.adapter.SportTabFragmentAdapter;
import com.wtwd.standard.base.BaseFragment;
import com.wtwd.standard.utils.DateUtil;
import com.wtwd.standard.utils.MainPageStepColumnDisplay;
import com.wtwd.standard.utils.Utils;
import com.wtwd.standard.utils.stepline.AbstractSportLine;
import com.wtwd.standard.utils.stepline.MonthSportLine;
import com.wtwd.standard.utils.stepline.WeekSportLine;
import com.wtwd.standard.utils.stepline.YearSportLine;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/a1/26 0026.
 */

public class MainSportFragment extends BaseFragment implements View.OnClickListener {
    private static MainSportFragment mInstance;

    private static final int DISPLAY_FOR_WEEK = 0x01;
    private static final int DISPLAY_FOR_MONTH = 0x02;
    private static final int DISPLAY_FOR_YEAR = 0x03;

    private RelativeLayout relative_out_door_sport;
    private RelativeLayout relative_iner_door_sport;
    private LinearLayout lin_sport_title;
    private LineChartView line_chart_view_sport_fm;
    private RecyclerView rv_line_chart;

    private ViewPager viewpager_main_sport;
    private SportFragmentViewPagerAdapter mViewPagerAdapter;
    String mStartDate = "2017/02/15";
    String mEndDate = "2018/03/08";

    private LinearLayout lin_week;
    private LinearLayout lin_month;
    private LinearLayout lin_year;

    private TextView text_week;
    private TextView text_month;
    private TextView text_year;

    private AbstractSportLine ma;
    private int mPos, mPos1 = 1;

    private int mPastItem;
    private SportRecyclerAdapter mAdapter;
    private LinearLayoutManager mLm;

    private List<String> mPeriods = new ArrayList<>();

    public static MainSportFragment getMainSportFragment() {
        if (null == mInstance) {
            mInstance = new MainSportFragment();
        }
        return mInstance;
    }


    @Override
    public int getLayoutResourceId() {
        return R.layout.fragment_main_sport;
    }

    @Override
    public void initFragmentView(View mView) {
        initToolBar();
        initView(mView);
    }


    private void initView(View mView) {
        relative_out_door_sport = (RelativeLayout) mView.findViewById(R.id.relative_out_door_sport);
        relative_iner_door_sport = (RelativeLayout) mView.findViewById(R.id.relative_iner_door_sport);
        lin_sport_title = (LinearLayout) mView.findViewById(R.id.lin_sport_title);
        line_chart_view_sport_fm = (LineChartView) mView.findViewById(R.id.line_chart_view_sport_fm);
        rv_line_chart = (RecyclerView) mView.findViewById(R.id.rv_line_chart);
        mAdapter = new SportRecyclerAdapter(R.layout.item_sport_view_pager, mPeriods);
        mLm = new LinearLayoutManager(getActivity());
        mLm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_line_chart.setLayoutManager(mLm);
        rv_line_chart.setAdapter(mAdapter);
        rv_line_chart.addOnScrollListener(mOnScrollListener);

        lin_week = (LinearLayout) mView.findViewById(R.id.lin_week);
        lin_month = (LinearLayout) mView.findViewById(R.id.lin_month);
        lin_year = (LinearLayout) mView.findViewById(R.id.lin_year);

        text_week = (TextView) mView.findViewById(R.id.text_week);
        text_month = (TextView) mView.findViewById(R.id.text_month);
        text_year = (TextView) mView.findViewById(R.id.text_year);

        viewpager_main_sport = (ViewPager) mView.findViewById(R.id.viewpager_main_sport);
        mViewPagerAdapter = new SportFragmentViewPagerAdapter(getActivity(), mPeriods);
        viewpager_main_sport.setAdapter(mViewPagerAdapter);
        viewpager_main_sport.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mPos = position;
//                mViewPagerAdapter.displayLine(mPeriods.get(position));
//                viewpager_main_sport.setCurrentItem(position);
                Log.e("TAG", "on page selected position : " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("TAG", "on page scroll state changed" + state);
                if (ViewPager.SCROLL_STATE_IDLE == state) {
                    Log.e("TAG", "mPos : " + mPos + " mPos1 : " + mPos1);
                    if (mPos != mPos1) {
                        mViewPagerAdapter.displayLine(mPeriods.get(mPos));
                        mPos1 = mPos;
                    }
                }
            }
        });


//        ma = new AbstractSportLine(getActivity());
//        ma.displayLine(line_chart_view_sport_fm);
        LinearLayout.LayoutParams mLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (Utils.getStatusBarHeight(getActivity()) + Utils.getActionBarHeight(getActivity())));
        lin_sport_title.setLayoutParams(mLayoutParams);
        lin_sport_title.setBackgroundResource(R.mipmap.main_me_bg);


        addListener();
    }

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                if (mPastItem >= mPeriods.size()) {
                    mPastItem = mPeriods.size() - 1;
                }
                recyclerView.scrollToPosition(mPastItem);
                mAdapter.notifyItemChanged(mPastItem);
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dx > 0) {
                int visibleItemCount = mLm.getChildCount();

                int totalItemCount = mLm.getItemCount();

                int pastVisiblesItems = mLm.findFirstVisibleItemPosition();

                mPastItem = pastVisiblesItems + 1;


            } else if (dx < 0) {
                int visibleItemCount = mLm.getChildCount();

                int totalItemCount = mLm.getItemCount();

                int pastVisiblesItems = mLm.findFirstVisibleItemPosition();

                mPastItem = pastVisiblesItems;

            }
        }
    };


    private void addListener() {
        relative_iner_door_sport.setOnClickListener(this);
        relative_out_door_sport.setOnClickListener(this);

        lin_week.setOnClickListener(this);
        lin_month.setOnClickListener(this);
        lin_year.setOnClickListener(this);

        chooseDateDisplay(DISPLAY_FOR_WEEK);

    }

    private void initToolBar() {
        text_tool_bar_title.setText("运动");
        img_tool_bar_left.setVisibility(View.GONE);
        img_tool_bar_right.setVisibility(View.VISIBLE);
        img_tool_bar_right.setImageResource(R.mipmap.main_me_setting);
        img_tool_bar_left.setImageResource(R.mipmap.main_sport_history_record);

    }

    private void setLinWeekState(boolean mSelected) {
        lin_week.setSelected(mSelected);
        lin_week.setClickable(!mSelected);
        if (mSelected) {
            text_week.setTextColor(ContextCompat.getColor(getActivity(), R.color.step_blue));
        } else {

            text_week.setTextColor(ContextCompat.getColor(getActivity(), R.color.alpha_black_6));
        }
    }

    private void setLinMonthState(boolean mSelected) {
        lin_month.setSelected(mSelected);
        lin_month.setClickable(!mSelected);
        if (mSelected) {
            text_month.setTextColor(ContextCompat.getColor(getActivity(), R.color.step_blue));
        } else {
            text_month.setTextColor(ContextCompat.getColor(getActivity(), R.color.alpha_black_6));
        }
    }

    private void setLinYearState(boolean mSelected) {
        lin_year.setSelected(mSelected);
        lin_year.setClickable(!mSelected);
        if (mSelected) {
            text_year.setTextColor(ContextCompat.getColor(getActivity(), R.color.step_blue));
        } else {
            text_year.setTextColor(ContextCompat.getColor(getActivity(), R.color.alpha_black_6));
        }
    }

    private void chooseDateDisplay(int type) {
        mPeriods.clear();
        mPastItem = 0;
        if (DISPLAY_FOR_WEEK == type) {
            setLinWeekState(true);
            setLinMonthState(false);
            setLinYearState(false);
            List<String> mPrimaryList = DateUtil.betweenDaysForWeek(mStartDate, mEndDate);
            for (int i = (mPrimaryList.size() - 1); i >= 0; i--) {
                String mWeekStr = mPrimaryList.get(i);
                String[] mSplitStr = mWeekStr.split("-");
                if (0 == i) {
                    mPeriods.add(0, mStartDate.substring(5, mStartDate.length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
                } else if (i == (mPrimaryList.size() - 1)) {
                    mPeriods.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mEndDate.substring(5, mEndDate.length()));
                } else {
                    mPeriods.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
                }
            }

            mAdapter.setAbstractSportLine(WeekSportLine.getInstance(getActivity()));
//            mAdapter.notifyDataSetChanged();
//            rv_line_chart.scrollToPosition(mPeriods.size() - 1);


        } else if (DISPLAY_FOR_MONTH == type) {
            setLinWeekState(false);
            setLinMonthState(true);
            setLinYearState(false);
            mAdapter.setAbstractSportLine(MonthSportLine.getInstance(getActivity()));
            mPeriods.addAll(DateUtil.betweenDaysForMonth(mStartDate, mEndDate));
//            mAdapter.notifyDataSetChanged();
//            rv_line_chart.scrollToPosition(mPeriods.size() - 1);

        } else if (DISPLAY_FOR_YEAR == type) {
            setLinWeekState(false);
            setLinMonthState(false);
            setLinYearState(true);

            mAdapter.setAbstractSportLine(YearSportLine.getInstance(getActivity()));
            mPeriods.addAll(DateUtil.betweenYears(mStartDate, mEndDate));
            Log.e("TAG", "mYear size : " + mPeriods.size());

        }
        mAdapter.notifyDataSetChanged();
        rv_line_chart.scrollToPosition(mPeriods.size() - 1);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (R.id.relative_out_door_sport == id) {
            readyGo(SportOutDoorActivity.class);
        } else if (R.id.lin_week == id) {
            chooseDateDisplay(DISPLAY_FOR_WEEK);
        } else if (R.id.lin_month == id) {
            chooseDateDisplay(DISPLAY_FOR_MONTH);


        } else if (R.id.lin_year == id) {
            chooseDateDisplay(DISPLAY_FOR_YEAR);
        }
    }

//    private List<String> mPrimaryList = new ArrayList<>();

//    private void setStringScrollPicker(int type) {
//        List<String> mDateList = new ArrayList<>();
//        String mStartDate = "2017/02/15";
//        String mEndDate = "2018/03/08";
//        if (DISPLAY_FOR_YEAR == type) {
//
//            mDateList.clear();
//            mPrimaryList.clear();
//
//
//            mPrimaryList = DateUtil.betweenDays(mStartDate, mEndDate);
//
//            for (int i = 0; i < mPrimaryList.size(); i++) {
//                String mDateStr = mPrimaryList.get(i);
//                mDateList.add(i, mDateStr.substring(5, mDateStr.length()));
//            }
//        } else if (DISPLAY_FOR_WEEK == type) {
//            mDateList.clear();
//            mPrimaryList.clear();
//            mPrimaryList = DateUtil.betweenDaysForWeek(mStartDate, mEndDate);
//            for (int i = (mPrimaryList.size() - 1); i >= 0; i--) {
//                String mWeekStr = mPrimaryList.get(i);
//                String[] mSplitStr = mWeekStr.split("-");
//                if (0 == i) {
//                    mDateList.add(0, mStartDate.substring(5, mStartDate.length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
//                } else if (i == (mPrimaryList.size() - 1)) {
//                    mDateList.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mEndDate.substring(5, mEndDate.length()));
//                } else {
//                    mDateList.add(0, mSplitStr[0].substring(5, mSplitStr[0].length()) + "-" + mSplitStr[1].substring(5, mSplitStr[1].length()));
//                }
//            }
//            ma.displayLine(line_chart_view_sport_fm, mDateList.get((int) (mDateList.size() * Math.random())));
//
//        } else if (DISPLAY_FOR_MONTH == type) {
//            mDateList.clear();
//            mDateList.addAll(DateUtil.betweenDaysForMonth(mStartDate, mEndDate));
//            ma.displayLine(line_chart_view_sport_fm, mDateList.get((int) (mDateList.size() * Math.random())));
//        }
//    }


}
