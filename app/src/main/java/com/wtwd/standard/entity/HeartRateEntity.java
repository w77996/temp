package com.wtwd.standard.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/2/28 0028.
 */

public class HeartRateEntity implements Parcelable{

    private String mMeasureDate;
    private String mMeasureTime;
    private String mHeartRate;

    public HeartRateEntity(){

    }

    protected HeartRateEntity(Parcel in) {
        mMeasureDate = in.readString();
        mMeasureTime = in.readString();
        mHeartRate = in.readString();
    }

    public static final Creator<HeartRateEntity> CREATOR = new Creator<HeartRateEntity>() {
        @Override
        public HeartRateEntity createFromParcel(Parcel in) {
            return new HeartRateEntity(in);
        }

        @Override
        public HeartRateEntity[] newArray(int size) {
            return new HeartRateEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMeasureDate);
        dest.writeString(mMeasureTime);
        dest.writeString(mHeartRate);
    }

    public String getmMeasureDate() {
        return mMeasureDate;
    }

    public void setmMeasureDate(String mMeasureDate) {
        this.mMeasureDate = mMeasureDate;
    }

    public String getmMeasureTime() {
        return mMeasureTime;
    }

    public void setmMeasureTime(String mMeasureTime) {
        this.mMeasureTime = mMeasureTime;
    }

    public String getmHeartRate() {
        return mHeartRate;
    }

    public void setmHeartRate(String mHeartRate) {
        this.mHeartRate = mHeartRate;
    }

    @Override
    public String toString() {
        return "HeartRateEntity{" +
                "mMeasureDate='" + mMeasureDate + '\'' +
                ", mMeasureTime='" + mMeasureTime + '\'' +
                ", mHeartRate='" + mHeartRate + '\'' +
                '}';
    }
}
