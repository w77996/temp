package com.wtwd.standard.utils.stepline;

import android.content.Context;
import android.media.MediaDataSource;
import android.util.Log;

import com.wtwd.standard.entity.SportDistanceEntity;
import com.wtwd.standard.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class MonthSportLine extends AbstractSportLine {
    private static final String TAG = "MonthSportLine";
    private static MonthSportLine mInstance;

    public static MonthSportLine getInstance(Context mContext) {
        if (null == mInstance) {
            mInstance = new MonthSportLine(mContext);
        }
        return mInstance;
    }


    private MonthSportLine(Context mContext) {
        super(mContext);
    }

    @Override
    List<SportDistanceEntity> getSportDistances(String mPeriod) {
        int mDays = DateUtil.getDaysOfMonth(mPeriod);
        List<SportDistanceEntity> mList = new ArrayList<>();
        for (int i = 0; i < mDays; i++) {
            SportDistanceEntity mEn = new SportDistanceEntity();
            mEn.setmPeriod(mPeriod);
            mEn.setmBurnCalories((int) (200 * Math.random()));
            mEn.setmDistance((float) (20 * Math.random()));
            mEn.setmDurationExercise((int) (90 * Math.random()));
            mEn.setmHeartRate((int) (60 + 60 * Math.random()));
            mList.add(mEn);
        }
        return mList;
    }

    @Override
    void setAxisXValues(String mPeriod) {
        mAxisXValues.clear();
        int mDays = DateUtil.getDaysOfMonth(mPeriod);
        if (mDays >= 32) {
            throw new IllegalArgumentException("the month is wrong!");
        }

        for (int i = 0; i < mDays; i++) {
            String mStr = mPeriod.substring(5, 7) + "/" + addZeroToNum(i + 1 + "");
            Log.e(TAG, "mStr : " + mStr);
            mAxisXValues.add(new AxisValue(i).setLabel(mStr));
        }


    }

    @Override
    void setViewPort(LineChartView mLineChartView, List<SportDistanceEntity> mSportDistances) {
        Viewport v = new Viewport(mLineChartView.getMaximumViewport());
        v.top = (float) (mYLeftAxis + (int) (mYLeftAxis / 4));
        v.bottom = 0;
        mLineChartView.setMaximumViewport(v);
        v.left = 0;
        v.right = mSportDistances.size();
        mLineChartView.setCurrentViewport(v);
    }

    private String addZeroToNum(String num) {

        if (1 == num.length()) {
            num = "0" + num;
        }
        return num;
    }

}
