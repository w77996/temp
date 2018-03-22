package com.wtwd.standard.entity;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class HeartRateHeadEntity extends SectionEntity<HeartRateEntity> {
    public HeartRateHeadEntity(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public HeartRateHeadEntity(HeartRateEntity mHeartRateEntity) {
        super(mHeartRateEntity);
    }
}
