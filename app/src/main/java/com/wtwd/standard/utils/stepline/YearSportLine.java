package com.wtwd.standard.utils.stepline;

import android.content.Context;

import com.wtwd.standard.R;
import com.wtwd.standard.entity.SportDistanceEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class YearSportLine extends AbstractSportLine {
    private static YearSportLine mInstance;
    private String[] mYearPoint;

    public static YearSportLine getInstance(Context mContext) {
        if (null == mInstance) {
            mInstance = new YearSportLine(mContext);
        }
        return mInstance;
    }

    private YearSportLine(Context mContext) {
        super(mContext);
        mYearPoint = mContext.getResources().getStringArray(R.array.month_point);
    }

    @Override
    List<SportDistanceEntity> getSportDistances(String mPeriod) {
        List<SportDistanceEntity> mList = new ArrayList<>();
        for (int i = 0; i < mYearPoint.length; i++) {
            SportDistanceEntity mEn = new SportDistanceEntity();
            mEn.setmPeriod(mYearPoint[i]);
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
        for (int i = 0; i < mYearPoint.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(mYearPoint[i]));
        }
    }

    @Override
    void setViewPort(LineChartView mLineChartView, List<SportDistanceEntity> mSportDistances) {
        Viewport v = new Viewport(mLineChartView.getMaximumViewport());
        v.top = (float) (mYLeftAxis + (int) (mYLeftAxis / 4));
        v.bottom = 0;
        mLineChartView.setMaximumViewport(v);
        v.left = 0;
        v.right = mYearPoint.length;
        mLineChartView.setCurrentViewport(v);
    }
}
