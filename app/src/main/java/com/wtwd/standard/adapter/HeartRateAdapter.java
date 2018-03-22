package com.wtwd.standard.adapter;

import android.view.View;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wtwd.standard.R;
import com.wtwd.standard.entity.HeartRateEntity;
import com.wtwd.standard.entity.HeartRateHeadEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class HeartRateAdapter extends BaseSectionQuickAdapter<HeartRateHeadEntity, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    private List<HeartRateHeadEntity> data;

    public HeartRateAdapter(int layoutResId, int sectionHeadResId, List<HeartRateHeadEntity> data) {
        super(layoutResId, sectionHeadResId, data);
        this.data = data;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, HeartRateHeadEntity item) {
        helper.setText(R.id.text_heart_data, item.header);
        if (0 == helper.getLayoutPosition()) {
            helper.getView(R.id.view_heart_head_top).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.view_heart_head_top).setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void convert(BaseViewHolder helper, HeartRateHeadEntity item) {
        HeartRateEntity mEn = item.t;
        helper.setText(R.id.text_heart_rate_time, mEn.getmMeasureTime())
                .setText(R.id.text_heart_rate, mEn.getmHeartRate());
        if ((data.size() - 1) == helper.getLayoutPosition()) {
            helper.getView(R.id.view_heart_rate).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.view_heart_rate).setVisibility(View.VISIBLE);
        }
    }
}
