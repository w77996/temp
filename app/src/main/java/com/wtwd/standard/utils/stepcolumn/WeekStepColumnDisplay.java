package com.wtwd.standard.utils.stepcolumn;

import android.content.Context;

import com.wtwd.standard.R;
import com.wtwd.standard.entity.StepEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public class WeekStepColumnDisplay extends AbstractStepColumn {
    private String[] week_point;
    private static WeekStepColumnDisplay mInstance;

    public static WeekStepColumnDisplay getWeekInstance(Context mContext) {
        if (null == mInstance) {
            mInstance = new WeekStepColumnDisplay(mContext);
        }
        return mInstance;
    }

    private WeekStepColumnDisplay(Context mContext) {
        super(mContext);
        week_point = mContext.getResources().getStringArray(R.array.week_point);
    }

    @Override
    List<StepEntity> getSteps(String mPeriod) {

        List<StepEntity> mSteps = new ArrayList<>();
        for (String aWhole_time : week_point) {
            StepEntity mEn = new StepEntity();
            mEn.setWhole_time(aWhole_time);
            mEn.setStep_num((int) (Math.random() * 2000) + "");
            mSteps.add(mEn);
        }
        return mSteps;
    }

    @Override
    void setViewport(ColumnChartView mColumnChartView, List<StepEntity> mSteps) {
        Viewport v = new Viewport(mColumnChartView.getMaximumViewport());
        v.top = (float) (mYLeftAxis + (int) (mYLeftAxis / 25));
        mColumnChartView.setMaximumViewport(v);
//        v.right = mSteps.size();
//        v.left = mSteps.size() - 7;
        mColumnChartView.setCurrentViewport(v);
    }
}
