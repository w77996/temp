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

public class DayStepColumnDisPlay extends AbstractStepColumn {
    private String[] whole_point;
    private static DayStepColumnDisPlay mInstance;


    public static DayStepColumnDisPlay getDayStepInstance(Context mContext) {
        if (null == mInstance) {
            mInstance = new DayStepColumnDisPlay(mContext);
        }
        return mInstance;
    }


    private DayStepColumnDisPlay(Context mContext) {
        super(mContext);
        whole_point = mContext.getResources().getStringArray(R.array.whole_point);
    }

    @Override
    List<StepEntity> getSteps(String mPeriod) {
        List<StepEntity> mSteps = new ArrayList<>();
        for (String aWhole_time : whole_point) {
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
        v.right = mSteps.size();
        v.left = mSteps.size() - 7;
        mColumnChartView.setCurrentViewport(v);
    }


}
