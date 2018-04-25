
package com.p4r4d0x.genreclassifier.rest.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassifiyStats implements Parcelable {

    public final static Creator<ClassifiyStats> CREATOR = new Creator<ClassifiyStats>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ClassifiyStats createFromParcel(Parcel in) {
            return new ClassifiyStats(in);
        }

        public ClassifiyStats[] newArray(int size) {
            return (new ClassifiyStats[size]);
        }

    };
    @SerializedName("avgSampleTime")
    @Expose
    private Integer avgSampleTime;
    @SerializedName("avgAudioType")
    @Expose
    private String avgAudioType;
    @SerializedName("avgGenre")
    @Expose
    private String avgGenre;
    @SerializedName("consecutiveAudioType")
    @Expose
    private String consecutiveAudioType;
    @SerializedName("consecutiveGenre")
    @Expose
    private String consecutiveGenre;

    protected ClassifiyStats(Parcel in) {
        this.avgSampleTime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.avgAudioType = ((String) in.readValue((String.class.getClassLoader())));
        this.avgGenre = ((String) in.readValue((String.class.getClassLoader())));
        this.consecutiveAudioType = ((String) in.readValue((String.class.getClassLoader())));
        this.consecutiveGenre = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ClassifiyStats() {
    }

    /**
     * @param consecutiveAudioType
     * @param consecutiveGenre
     * @param avgAudioType
     * @param avgGenre
     * @param avgSampleTime
     */
    public ClassifiyStats(Integer avgSampleTime, String avgAudioType, String avgGenre, String consecutiveAudioType, String consecutiveGenre) {
        super();
        this.avgSampleTime = avgSampleTime;
        this.avgAudioType = avgAudioType;
        this.avgGenre = avgGenre;
        this.consecutiveAudioType = consecutiveAudioType;
        this.consecutiveGenre = consecutiveGenre;
    }

    public Integer getAvgSampleTime() {
        return avgSampleTime;
    }

    public void setAvgSampleTime(Integer avgSampleTime) {
        this.avgSampleTime = avgSampleTime;
    }

    public String getAvgAudioType() {
        return avgAudioType;
    }

    public void setAvgAudioType(String avgAudioType) {
        this.avgAudioType = avgAudioType;
    }

    public String getAvgGenre() {
        return avgGenre;
    }

    public void setAvgGenre(String avgGenre) {
        this.avgGenre = avgGenre;
    }

    public String getConsecutiveAudioType() {
        return consecutiveAudioType;
    }

    public void setConsecutiveAudioType(String consecutiveAudioType) {
        this.consecutiveAudioType = consecutiveAudioType;
    }

    public String getConsecutiveGenre() {
        return consecutiveGenre;
    }

    public void setConsecutiveGenre(String consecutiveGenre) {
        this.consecutiveGenre = consecutiveGenre;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(avgSampleTime);
        dest.writeValue(avgAudioType);
        dest.writeValue(avgGenre);
        dest.writeValue(consecutiveAudioType);
        dest.writeValue(consecutiveGenre);
    }

    public int describeContents() {
        return 0;
    }

}
