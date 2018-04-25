
package com.p4r4d0x.genreclassifier.rest.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatsResponse implements Parcelable {

    public final static Creator<StatsResponse> CREATOR = new Creator<StatsResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public StatsResponse createFromParcel(Parcel in) {
            return new StatsResponse(in);
        }

        public StatsResponse[] newArray(int size) {
            return (new StatsResponse[size]);
        }

    };
    @SerializedName("userId")
    @Expose
    private Long userId;
    @SerializedName("classifiyStats")
    @Expose
    private ClassifiyStats classifiyStats;
    @SerializedName("lastClassify")
    @Expose
    private LastClassify lastClassify;

    protected StatsResponse(Parcel in) {
        this.userId = ((Long) in.readValue((Long.class.getClassLoader())));
        this.classifiyStats = ((ClassifiyStats) in.readValue((ClassifiyStats.class.getClassLoader())));
        this.lastClassify = ((LastClassify) in.readValue((LastClassify.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public StatsResponse() {
    }

    /**
     * @param classifiyStats
     * @param userId
     * @param lastClassify
     */
    public StatsResponse(Long userId, ClassifiyStats classifiyStats, LastClassify lastClassify) {
        super();
        this.userId = userId;
        this.classifiyStats = classifiyStats;
        this.lastClassify = lastClassify;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ClassifiyStats getClassifiyStats() {
        return classifiyStats;
    }

    public void setClassifiyStats(ClassifiyStats classifiyStats) {
        this.classifiyStats = classifiyStats;
    }

    public LastClassify getLastClassify() {
        return lastClassify;
    }

    public void setLastClassify(LastClassify lastClassify) {
        this.lastClassify = lastClassify;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userId);
        dest.writeValue(classifiyStats);
        dest.writeValue(lastClassify);
    }

    public int describeContents() {
        return 0;
    }

}
