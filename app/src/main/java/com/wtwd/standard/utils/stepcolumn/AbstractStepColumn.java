package com.wtwd.standard.utils.stepcolumn;

import android.accounts.AbstractAccountAuthenticator;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.wtwd.standard.R;
import com.wtwd.standard.entity.StepEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.view.ColumnChartView;

/**
 * Created by Administrator on 2018/3/8 0008.
 */

public abstract class AbstractStepColumn {
    private static final String TAG = "AbstractStepColumn";
    private Context mContext;
    public int mYLeftAxis;

    public AbstractStepColumn(Context mContext) {
        this.mContext = mContext;
    }


    public void displayDataOnWindow(ColumnChartView mColumnChartView, String mPeriod) {
        ColumnChartData mColumnChartData;
        int mSubColumnNum = 1;
        List<Column> mColumns = new ArrayList<>();
        List<SubcolumnValue> mSubcolumnValues;

        float maxNum = 0;

        List<StepEntity> mSteps = getSteps(mPeriod);

        /**设置每个柱子的数据*/
        for (int i = 0; i < mSteps.size(); i++) {
            mSubcolumnValues = new ArrayList<>();
            float mCurrentStep = Float.parseFloat(mSteps.get(i).getStep_num());
            for (int i1 = 0; i1 < mSubColumnNum; i1++) {
                SubcolumnValue mValue = new SubcolumnValue(mCurrentStep, ContextCompat.getColor(mContext, R.color.step_blue));

                if (mValue.getValue() > maxNum) {
                    maxNum = mValue.getValue();
                }
                mSubcolumnValues.add(mValue);
            }

            Column mColumn = new Column(mSubcolumnValues);
            mColumn.setHasLabels(true);
            mColumn.setHasLabelsOnlyForSelected(true);
            mColumns.add(mColumn);
        }

        mColumnChartData = new ColumnChartData(mColumns);
        mColumnChartData.setFillRatio(0.6f);

        //设置Y轴Label
        setYAxisLabel(mColumnChartData, maxNum);
        //设置X轴Label
        setXAxisLabel(mColumnChartData, mSteps);

        //将数据放入视图
        mColumnChartView.setColumnChartData(mColumnChartData);

        //设置ViewPort
        setViewport(mColumnChartView, mSteps);
    }


    private void setYAxisLabel(ColumnChartData mColumnChartData, float maxNum) {
        //Y轴

        List<AxisValue> mAxisYValues = new ArrayList<>();
        if (((int) maxNum % 400) > 0) {
            mYLeftAxis = (((int) (maxNum / 400) + 1) * 400);
        } else {
            mYLeftAxis = (int) ((maxNum / 400) + 1) * 400;
        }
        Log.e(TAG, "mYLeftAxis : " + mYLeftAxis);

        int average = mYLeftAxis / 4;

        for (int i = 0; i <= mYLeftAxis; i += average) {
            Log.e(TAG, "i : " + i);
            if ((0 == (i % average)) && (i > 0)) {
                mAxisYValues.add(new AxisValue(i).setLabel((i) + ""));
            } else {
                mAxisYValues.add(new AxisValue(i).setLabel(""));
            }
        }

        Axis mYLeft = new Axis(mAxisYValues);

        mYLeft.setMaxLabelChars(5)
                .setHasLines(true)
                .setLineColor(ContextCompat.getColor(mContext, R.color.alpha_black_8))
                .setInside(false)
                .setTextSize((int) mContext.getResources().getDimension(R.dimen.text_6))
                .setYAxisColor(Color.TRANSPARENT)
                .setTextColor(ContextCompat.getColor(mContext, R.color.color_black));

        mColumnChartData.setAxisYLeft(mYLeft);

    }

    private void setXAxisLabel(ColumnChartData mColumnChartData, List<StepEntity> mSteps) {
        //X轴
        List<AxisValue> mAxisXValues = new ArrayList<>();
        for (int i = 0; i < mSteps.size(); i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(mSteps.get(i).getWhole_time()));
        }

        Axis mXBottom = new Axis(mAxisXValues);
        mXBottom.setTextSize((int) mContext.getResources().getDimension(R.dimen.text_6))
                .setXAxisColor(ContextCompat.getColor(mContext, R.color.alpha_black_6))
                .setTextColor(ContextCompat.getColor(mContext, R.color.color_black));

        mColumnChartData.setAxisXBottom(mXBottom);
    }

    abstract List<StepEntity> getSteps(String mPeriod);


    abstract void setViewport(ColumnChartView mColumnChartView, List<StepEntity> mSteps);


}
