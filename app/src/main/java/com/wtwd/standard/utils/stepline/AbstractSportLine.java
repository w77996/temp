package com.wtwd.standard.utils.stepline;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;

import com.wtwd.standard.R;
import com.wtwd.standard.entity.HeartRateEntity;
import com.wtwd.standard.entity.SportDistanceEntity;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public abstract class AbstractSportLine {
    private static final String TAG = "AbstractSportLine";
    private Context mContext;
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    public List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    public int mYLeftAxis;

    public AbstractSportLine(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 初始化柱状图
     */
    private void initColumnChartView(LineChartView mLineChartView) {
        mLineChartView.setZoomEnabled(false);//手势缩放
        mLineChartView.setInteractive(true);//设置图表是可以交互的（拖拽，缩放等效果的前提）
        mLineChartView.setZoomType(ZoomType.HORIZONTAL);
        mLineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
//        mLineChartView.setMaxZoom((float) 4);
    }

    public void displayLine(LineChartView mLineChartView, String mPeriod) {
        Log.e(TAG, "sport line period : " + mPeriod);
        if (null == mLineChartView) {
            return;
        }
        initColumnChartView(mLineChartView);

//        getAxisXLables();
        getAxisPoints(mPeriod);

        Line line = new Line(mPointValues).setColor(ContextCompat.getColor(mContext, R.color.color_white));  //折线的颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(true);//是否填充曲线的面积
        line.setPointRadius((int) mContext.getResources().getDimension(R.dimen.common_2) / 2);
        line.setHasGradientToTransparent(true);//填充颜色是否渐变
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        line.setStrokeWidth((int) mContext.getResources().getDimension(R.dimen.common_2) / 2);
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //x坐标轴
        setAxisXValues(mPeriod);
        Axis axisX = new Axis(); //X轴
        axisX.setMaxLabelChars(5);
        axisX.setTextColor(Color.WHITE);  //设置字体颜色
        axisX.setTextSize((int) mContext.getResources().getDimension(R.dimen.text_4));//设置字体大小
        axisX.setHasTiltedLabels(true);
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部

        //y坐标轴
        setYAxisLabel(data, mYLeftAxis);

//        Log.e(TAG, "data getLines size : " + data.getLines().size());
//        Log.e(TAG, "mLineChartView : " + mLineChartView);
//        data.getLines().get(0).getValues().size();
        mLineChartView.setLineChartData(data);
        setViewPort(mLineChartView, getSportDistances(mPeriod));
//        mLineChartView.setVisibility(View.VISIBLE);
    }


    abstract List<SportDistanceEntity> getSportDistances(String mPeriod);

    abstract void setAxisXValues(String mPeriod);

    abstract void setViewPort(LineChartView mLineChartView, List<SportDistanceEntity> mSportDistances);


    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints(String mPeriod) {
        List<SportDistanceEntity> mList = getSportDistances(mPeriod);
        mPointValues.clear();
        for (int i = 0; i < mList.size(); i++) {
            if (mYLeftAxis < mList.get(i).getmDistance()) {
                mYLeftAxis = (int) mList.get(i).getmDistance();
            }
            mPointValues.add(new PointValue(i, mList.get(i).getmDistance()));
        }
//        Log.e(TAG, "mPointValues size : " + mPointValues.size());
    }

    private void setYAxisLabel(LineChartData mColumnChartData, int maxNum) {
        //Y轴

        List<AxisValue> mAxisYValues = new ArrayList<>();
//        Log.e(TAG, "mYLeftAxis : " + mYLeftAxis);
        if (mYLeftAxis == 0) {
            return;
        }
        int average = mYLeftAxis / 4;
        for (int i = 0; i <= mYLeftAxis; i += average) {
//            Log.e(TAG, "i : " + i);
            if ((0 == (i % average)) && (i > 0)) {
                mAxisYValues.add(new AxisValue(i).setLabel((i) + ""));
            } else {
                mAxisYValues.add(new AxisValue(i).setLabel(""));
            }
        }

        Axis mYLeft = new Axis(mAxisYValues);

        mYLeft.setMaxLabelChars(3)
                .setHasLines(true)
                .setLineColor(ContextCompat.getColor(mContext, R.color.alpha_white_6))
                .setInside(false)
                .setTextSize((int) mContext.getResources().getDimension(R.dimen.text_4))
                .setYAxisColor(Color.TRANSPARENT)
                .setTextColor(ContextCompat.getColor(mContext, R.color.color_white))
                .setName("距离(单位:KM)");

        mColumnChartData.setAxisYLeft(mYLeft);

    }
}