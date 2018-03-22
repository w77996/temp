package com.wtwd.standard.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wtwd.standard.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class MessageCenterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private List<String> data;

    public MessageCenterAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
        this.data = data;
    }


    @Override
    protected void convert(BaseViewHolder helper, String item) {
        int pos = helper.getLayoutPosition();

        if (0 == pos) {
            helper.getView(R.id.view_msg_short).setVisibility(View.INVISIBLE);
        } else {
            helper.getView(R.id.view_msg_short).setVisibility(View.VISIBLE);
        }

        if ((data.size() - 1) == pos) {
            helper.getView(R.id.view_msg_long).setVisibility(View.GONE);
        } else {
            helper.getView(R.id.view_msg_long).setVisibility(View.VISIBLE);
        }

        helper.setText(R.id.text_msg_title, item);

    }
}
