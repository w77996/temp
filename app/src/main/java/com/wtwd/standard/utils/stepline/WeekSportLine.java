package com.wtwd.standard.utils.stepline;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.wtwd.standard.R;
import com.wtwd.standard.entity.SportDistanceEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class WeekSportLine extends AbstractSportLine {

    private static WeekSportLine mWeekSportLine;
    private String[] mWeekPoint;


    public static WeekSportLine getInstance(Context mContext) {
        if (null == mWeekSportLine) {
            mWeekSportLine = new WeekSportLine(mContext);
        }
        return mWeekSportLine;
    }


    private WeekSportLine(Context mContext) {
        super(mContext);
        mWeekPoint = mContext.getResources().getStringArray(R.array.week_point);
    }

    @Override
    List<SportDistanceEntity> getSportDistances(String mPeriod) {

        List<SportDistanceEntity> mDistances = new ArrayList<>();
        for (int i = 0; i < mWeekPoint.length; i++) {

            SportDistanceEntity mEn = new SportDistanceEntity();
            mEn.setmPeriod(mPeriod);
            mEn.setmBurnCalories((int) (200 * Math.random()));
            mEn.setmDistance((float) (20 * Math.random()));
//            mEn.setmDistance((float) (20 * (0.1 * i)));
            mEn.setmDurationExercise((int) (90 * Math.random()));
            mEn.setmHeartRate((int) (60 + 60 * Math.random()));
            mDistances.add(mEn);
        }
        return mDistances;
    }

    @Override
    void setAxisXValues(String mPeriod) {
        String[] mPeriods = mPeriod.split("-");
        mAxisXValues.clear();
//        List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
        if (mPeriod.length() <= 0) {
            return;
        }
        for (int i = 0; i < mWeekPoint.length; i++) {
            if (0 == i) {
                mAxisXValues.add(new AxisValue(i).setLabel(mPeriods[0]));
            } else if ((mWeekPoint.length - 1) == i) {
                mAxisXValues.add(new AxisValue(i).setLabel(mPeriods[1]));
            } else {
                mAxisXValues.add(new AxisValue(i).setLabel(mWeekPoint[i]));
            }
        }


    }

    @Override
    void setViewPort(LineChartView mLineChartView, List<SportDistanceEntity> mSportDistances) {
        Viewport v = new Viewport(mLineChartView.getMaximumViewport());
        v.top = (float) (mYLeftAxis + (int) (mYLeftAxis / 4));
        v.bottom = 0;
        mLineChartView.setMaximumViewport(v);
        v.left = 0;
        v.right = mWeekPoint.length;
        mLineChartView.setCurrentViewport(v);
    }
}
