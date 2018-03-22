package com.wtwd.standard.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wtwd.standard.R;
import com.wtwd.standard.utils.stepline.AbstractSportLine;

import java.util.List;

import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by Administrator on 2018/3/15 0015.
 */

public class SportRecyclerAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private AbstractSportLine mLine;

    public SportRecyclerAdapter(int layoutResId, List<String> data, AbstractSportLine mLine) {
        super(layoutResId, data);
        this.mLine = mLine;
    }

    public SportRecyclerAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        if (null == mLine) {
            return;
        }
        mLine.displayLine((LineChartView) helper.getView(R.id.line_chart_view_sport_fm), item);
    }

    public void setAbstractSportLine(AbstractSportLine mLine) {
        this.mLine = mLine;
    }

}
