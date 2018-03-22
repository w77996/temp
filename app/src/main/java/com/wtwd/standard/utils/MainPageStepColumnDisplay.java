package com.wtwd.standard.utils;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.wtwd.standard.R;
import com.wtwd.standard.entity.StepEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.renderer.AxesRenderer;
import lecho.lib.hellocharts.view.ColumnChartView;


/**
 * Created by Administrator on 2018/3/2 0002.
 */

public class MainPageStepColumnDisplay {
    private static final String TAG = "StepColumnDisplay";

    private Context mContext;
    private String[] whole_time;

    public MainPageStepColumnDisplay(Context mContext) {
        this.mContext = mContext;
        whole_time = mContext.getResources().getStringArray(R.array.whole_point);
    }


    private void initColumnChartView(ColumnChartView mColumnChartView) {
        mColumnChartView.setZoomEnabled(false);//手势缩放
        mColumnChartView.setInteractive(true);//设置图表是可以交互的（拖拽，缩放等效果的前提）
        mColumnChartView.setZoomType(ZoomType.HORIZONTAL);
        mColumnChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        mColumnChartView.setMaxZoom((float) 4);


    }



    public void setColumnChartViewData(ColumnChartView mColumnChartView,List<StepEntity> mSteps) {

        initColumnChartView(mColumnChartView);

        ColumnChartData mColumnChartData;
        int mSubColumnNum = 1;
        List<Column> mColumns = new ArrayList<>();
        List<SubcolumnValue> mSubcolumnValues;

        float maxNum = 0;

//        List<StepEntity> mSteps = new ArrayList<>();
//        for (String aWhole_time : whole_time) {
//            StepEntity mEn = new StepEntity();
//            mEn.setWhole_time(aWhole_time);
//            mEn.setStep_num((int) (Math.random() * 1000) + "");
//            mSteps.add(mEn);
//        }

        /**设置每个柱子的数据*/
        for (int i = 0; i < mSteps.size(); i++) {
            mSubcolumnValues = new ArrayList<>();
            float mCurrentStep = Float.parseFloat(mSteps.get(i).getStep_num());
            for (int i1 = 0; i1 < mSubColumnNum; i1++) {
                SubcolumnValue mValue = new SubcolumnValue(mCurrentStep, ContextCompat.getColor(mContext, R.color.color_white));

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
        //Y轴
        int mYLeftAxis;
        List<AxisValue> mAxisYValues = new ArrayList<>();
        if (((int) maxNum % 400) > 0) {
            mYLeftAxis = (((int) (maxNum / 400) + 2) * 400);
        } else {
            mYLeftAxis = (int) ((maxNum / 400) + 2) * 400;
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
//        mYLeft.setHasLines(true);

        mYLeft.setMaxLabelChars(5)
                .setInside(false)
                .setTextSize((int) mContext.getResources().getDimension(R.dimen.text_4))
                .setYAxisColor(Color.TRANSPARENT)
                .setTextColor(ContextCompat.getColor(mContext, R.color.color_white));


//        mYLeft.setMaxLabelChars(5);
//        mYLeft.setTextSize((int) mContext.getResources().getDimension(R.dimen.text_4));
//        mYLeft.setHasSeparationLine(true);
//        mYLeft.setYAxisColor(Color.TRANSPARENT);
//        mYLeft.setTextColor(ContextCompat.getColor(mContext, R.color.color_white));
        mColumnChartData.setAxisYLeft(mYLeft);

        //X轴
        List<AxisValue> mAxisXValues = new ArrayList<>();
        for (int i = 0; i < mSteps.size(); i++) {
            if (0 == (i % 6)) {
                mAxisXValues.add(new AxisValue(i).setLabel(mSteps.get(i).getWhole_time()));
//                if (i == (mSteps.size() - 1)) {
//                    Log.e(TAG, "333333333333");
//                    mAxisXValues.add(new AxisValue(i).setLabel("23:59"));
//                }
            } else if (i == (mSteps.size() - 1)) {
                mAxisXValues.add(new AxisValue(i).setLabel("23:59"));
            } else {
                mAxisXValues.add(new AxisValue(i).setLabel(""));
            }
        }

        Axis mXBottom = new Axis(mAxisXValues);
//        mXBottom.setHasLines(true);
        mXBottom.setTextSize((int) mContext.getResources().getDimension(R.dimen.text_4))
                .setXAxisColor(Color.TRANSPARENT)
                .setTextColor(ContextCompat.getColor(mContext, R.color.color_white));


//        mXBottom.setHasSeparationLine(true);
//        mXBottom.setTextSize((int) mContext.getResources().getDimension(R.dimen.text_4));
//        mXBottom.setXAxisColor(Color.RED);
//        mXBottom.setTextColor(ContextCompat.getColor(mContext, R.color.color_white));

        mColumnChartData.setAxisXBottom(mXBottom);
        mColumnChartView.setColumnChartData(mColumnChartData);

        Viewport v = new Viewport(mColumnChartView.getMaximumViewport());
        v.top = (float) mYLeftAxis;
        mColumnChartView.setMaximumViewport(v);
        mColumnChartView.setCurrentViewport(v);
//        AxesRenderer
    }


    private void setYAxisLeft(float maxNum, ColumnChartData mColumnChartData) {
        int mYLeftAxis;
        List<AxisValue> mAxisYValues = new ArrayList<>();
        if (((int) maxNum % 400) > 0) {
            mYLeftAxis = (((int) (maxNum / 400) + 2) * 400);
        } else {
            mYLeftAxis = (int) ((maxNum / 400) + 2) * 400;
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
        mYLeft.setHasLines(true);
        mYLeft.setMaxLabelChars(5);
        mYLeft.setYAxisColor(Color.TRANSPARENT);
        mYLeft.setTextColor(ContextCompat.getColor(mContext, R.color.color_white));
        mColumnChartData.setAxisYLeft(mYLeft);
    }


}
