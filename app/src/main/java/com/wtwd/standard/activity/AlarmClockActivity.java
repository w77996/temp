package com.wtwd.standard.activity;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.wtwd.standard.R;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.utils.CommonConstants;

import java.util.ArrayList;
import java.util.List;

public class AlarmClockActivity extends BaseActivity {
    private RecyclerView rv_alarm_clock;

    private AlarmClockAdapter mAlarmClockAdapter;
    private List<String> mClockList = new ArrayList<>();

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_alarm_clock;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.SOLID_COLOR_TITLE);
        initToolBar();
        initView();

        initData();
    }

    private void initView() {
        rv_alarm_clock = (RecyclerView) findViewById(R.id.rv_alarm_clock);
        rv_alarm_clock.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration mDivider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mDivider.setDrawable(ContextCompat.getDrawable(this, R.drawable.alarm_recycler_divider));
        rv_alarm_clock.addItemDecoration(mDivider);
        mAlarmClockAdapter = new AlarmClockAdapter(R.layout.item_rv_alarm_clock, mClockList);
        rv_alarm_clock.setAdapter(mAlarmClockAdapter);

        mAlarmClockAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                int id = view.getId();
                if (R.id.lin_delete_clock == id) {
                    showSnackBarLong("delete " + position);
                } else if (R.id.lin_edit_clock == id) {
                    showSnackBarLong("edit " + position);
                    readyGo(AlarmClockSetActivity.class);
                }
            }
        });
    }

    private void initToolBar() {
        text_tool_bar_title.setText("手环闹钟");
        img_tool_bar_right.setImageResource(R.mipmap.alarm_clock_add);
        img_tool_bar_right.setVisibility(View.VISIBLE);
    }

    private void initData() {
        mClockList.clear();
        for (int i = 0; i < 10; i++) {
            mClockList.add("string " + i);
        }
//        mAlarmClockAdapter.addData(mClockList);
        mAlarmClockAdapter.notifyDataSetChanged();
    }


    @Override
    public View getSnackView() {
        return rv_alarm_clock;
    }


    private class AlarmClockAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public AlarmClockAdapter(int layoutResId, @Nullable List<String> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.addOnClickListener(R.id.lin_delete_clock)
                    .addOnClickListener(R.id.lin_edit_clock);
        }
    }
}
