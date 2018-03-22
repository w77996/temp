package com.wtwd.standard.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wtwd.standard.R;
import com.wtwd.standard.adapter.HeartRateAdapter;
import com.wtwd.standard.base.BaseActivity;
import com.wtwd.standard.entity.HeartRateEntity;
import com.wtwd.standard.entity.HeartRateHeadEntity;
import com.wtwd.standard.utils.CommonConstants;
import com.wtwd.standard.widget.RingProgressView;

import java.util.ArrayList;
import java.util.List;

public class HeartRateActivity extends BaseActivity {

    private RecyclerView rv_heart_rate;
    private RingProgressView ring_progress_heart;

    private HeartRateAdapter mHeartRateAdapter;
    private List<HeartRateEntity> mHeartRates = new ArrayList<>();
    private List<HeartRateHeadEntity> mHeartRateHeads = new ArrayList<>();


    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_heart_rate;
    }

    @Override
    public void onCreateView(Bundle saveInstanceState) {
        setTitleToolbarStyle(CommonConstants.PURE_PICTURE_TITLE);
        initToolBar();
        initView();
        initData();
    }

    private void initToolBar() {
        text_tool_bar_title.setText("心率");
        img_tool_bar_right.setImageResource(R.mipmap.main_page_share);
        img_tool_bar_right.setVisibility(View.VISIBLE);
    }

    private void initView() {
        ring_progress_heart = (RingProgressView) findViewById(R.id.ring_progress_heart);
        rv_heart_rate = (RecyclerView) findViewById(R.id.rv_heart_rate);
        rv_heart_rate.setLayoutManager(new LinearLayoutManager(this));

        mHeartRateAdapter = new HeartRateAdapter(R.layout.item_heart_rate, R.layout.item_heart_rate_head, mHeartRateHeads);
        rv_heart_rate.setAdapter(mHeartRateAdapter);
        ring_progress_heart.setDisplayPercentageSymbol(false);
        ring_progress_heart.setCurrentNumAndTargetNum(80, 140);


    }

    /**
     * 测试数据
     */
    private void initData() {
        mHeartRates.clear();
        mHeartRateHeads.clear();
        for (int i = 0; i < 4; i++) {
            HeartRateEntity mRate = new HeartRateEntity();
            mRate.setmHeartRate("8" + i);
            mRate.setmMeasureDate("1月10日");
            mRate.setmMeasureTime("16:0" + (2 * i));
            mHeartRates.add(mRate);
        }

        for (int i = 0; i < 4; i++) {
            HeartRateEntity mRate = new HeartRateEntity();
            mRate.setmHeartRate("8" + i);
            mRate.setmMeasureDate("1月14日");
            mRate.setmMeasureTime("12:0" + (2 * i));
            mHeartRates.add(mRate);
        }

        List<String> mData = new ArrayList<>();
        for (int i = 0; i < mHeartRates.size(); i++) {
            if (0 == i) {
                mData.add(mHeartRates.get(i).getmMeasureDate());
            } else {
                if (!(mHeartRates.get(i).getmMeasureDate()).equals(mHeartRates.get(i - 1).getmMeasureDate())) {
                    mData.add(mHeartRates.get(i).getmMeasureDate());
                }
            }
        }

        for (int i = 0; i < mData.size(); i++) {
            mHeartRateHeads.add(new HeartRateHeadEntity(true, mData.get(i)));
            for (int i1 = 0; i1 < mHeartRates.size(); i1++) {
                if (mData.get(i).equals(mHeartRates.get(i1).getmMeasureDate())) {
                    mHeartRateHeads.add(new HeartRateHeadEntity(mHeartRates.get(i1)));
                }
            }
        }

        mHeartRateAdapter.notifyDataSetChanged();

    }


    @Override
    public View getSnackView() {
        return null;
    }
}
