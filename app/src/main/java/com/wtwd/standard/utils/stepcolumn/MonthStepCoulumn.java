package com.wtwd.standard.utils.stepcolumn;

import android.content.Context;

import com.wtwd.standard.entity.StepEntity;
import com.wtwd.standard.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Administrator on 2018/3/19 0019.
 */

public class MonthStepCoulumn extends AbstractStepColumn {

    private static MonthStepCoulumn mInstance;

    public static MonthStepCoulumn getInstance(Context mContext) {
        if (null == mInstance) {
            mInstance = new MonthStepCoulumn(mContext);
        }
        return mInstance;
    }

    private MonthStepCoulumn(Context mContext) {
        super(mContext);
    }

    @Override
    List<StepEntity> getSteps(String mPeriod) {
        int mDateInMonth = DateUtil.getDaysOfMonth(mPeriod);
        List<StepEntity> mSteps = new ArrayList<>();
        if (mDateInMonth >= 32) {
            throw new IllegalArgumentException("the month is wrong!");
        }

        for (int i = 0; i < mDateInMonth; i++) {
            String mStr = mPeriod.substring(5, 7) + "/" + addZeroToNum(i + 1 + "");
            StepEntity mEn = new StepEntity();
            mEn.setWhole_time(mStr);
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

    private String addZeroToNum(String num) {

        if (1 == num.length()) {
            num = "0" + num;
        }
        return num;
    }
}
