package com.wtwd.standard.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wtwd.standard.R;
import com.wtwd.standard.utils.stepline.AbstractSportLine;

import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/3/14 0014.
 */

public class SportFragmentViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<String> mLists;
    private AbstractSportLine mSportLine;
    LineChartView line_chart_view_sport_fm;

    public SportFragmentViewPagerAdapter(Context mContext, List<String> mLists) {
        this.mContext = mContext;
        this.mLists = mLists;
    }

    @Override
    public int getCount() {
        return mLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("TAG", "viewpager adapter instantiateItem : ------> " + position);
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_sport_view_pager, null);
        line_chart_view_sport_fm = mView.findViewById(R.id.line_chart_view_sport_fm);
        initColumnChartView(line_chart_view_sport_fm);
//        mSportLine.displayLine(line_chart_view_sport_fm, mLists.get(position));
        container.addView(mView);
        return mView;
    }

//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        Log.e("TAG", "viewpager adapter setPrimaryItem : ------> " + position);
////        if (position < mLists.size() && position > 0) {
////            mSportLine.displayLine(line_chart_view_sport_fm, mLists.get(position - 1));
////        } else {
////            mSportLine.displayLine(line_chart_view_sport_fm, mLists.get(position));
////        }
//    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        ((ViewGroup) container).removeView((View) object);
        container.removeView((View) object);
//        object = null;
    }

    public void setSportLine(AbstractSportLine mSportLine) {
        this.mSportLine = mSportLine;
    }

    public void displayLine(String mPeriod) {
        Log.e("TAG", "viewpager adapter displayLine");
        if (null == mSportLine) {
            return;
        }
        mSportLine.displayLine(line_chart_view_sport_fm, mPeriod);
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

}
