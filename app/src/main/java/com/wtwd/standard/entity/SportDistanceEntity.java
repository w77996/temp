package com.wtwd.standard.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/3/13 0013.
 */

public class SportDistanceEntity implements Parcelable {
    private String mPeriod;
    private float mDistance;
    private int mBurnCalories;
    private int mHeartRate;
    private int mDurationExercise;

    public SportDistanceEntity() {

    }

    protected SportDistanceEntity(Parcel in) {
        mPeriod = in.readString();
        mDistance = in.readFloat();
        mBurnCalories = in.readInt();
        mHeartRate = in.readInt();
        mDurationExercise = in.readInt();
    }

    public static final Creator<SportDistanceEntity> CREATOR = new Creator<SportDistanceEntity>() {
        @Override
        public SportDistanceEntity createFromParcel(Parcel in) {
            return new SportDistanceEntity(in);
        }

        @Override
        public SportDistanceEntity[] newArray(int size) {
            return new SportDistanceEntity[size];
        }
    };

    public String getmPeriod() {
        return mPeriod;
    }

    public void setmPeriod(String mPeriod) {
        this.mPeriod = mPeriod;
    }

    public float getmDistance() {
        return mDistance;
    }

    public void setmDistance(float mDistance) {
        this.mDistance = mDistance;
    }

    public int getmBurnCalories() {
        return mBurnCalories;
    }

    public void setmBurnCalories(int mBurnCalories) {
        this.mBurnCalories = mBurnCalories;
    }

    public int getmHeartRate() {
        return mHeartRate;
    }

    public void setmHeartRate(int mHeartRate) {
        this.mHeartRate = mHeartRate;
    }

    public int getmDurationExercise() {
        return mDurationExercise;
    }

    public void setmDurationExercise(int mDurationExercise) {
        this.mDurationExercise = mDurationExercise;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPeriod);
        dest.writeFloat(mDistance);
        dest.writeInt(mBurnCalories);
        dest.writeFloat(mHeartRate);
        dest.writeInt(mDurationExercise);
    }
}
