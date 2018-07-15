
package com.p4r4d0x.genreclassifier.rest.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.p4r4d0x.genreclassifier.rest.classify.MusicGenre;

public class ClassifyStats implements Parcelable {

    @SerializedName("avgSampleTime")
    @Expose
    private Integer avgSampleTime;
    @SerializedName("avgAudioType")
    @Expose
    private String avgAudioType;
    public final static Creator<ClassifyStats> CREATOR = new Creator<ClassifyStats>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ClassifyStats createFromParcel(Parcel in) {
            return new ClassifyStats(in);
        }

        public ClassifyStats[] newArray(int size) {
            return (new ClassifyStats[size]);
        }

    };
    @SerializedName("consecutiveAudioType")
    @Expose
    private String consecutiveAudioType;
    @SerializedName("avgGenre")
    @Expose
    private MusicGenre avgGenre;
    @SerializedName("consecutiveGenre")
    @Expose
    private MusicGenre consecutiveGenre;

    private ClassifyStats(Parcel in) {
        this.avgSampleTime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.avgAudioType = ((String) in.readValue((String.class.getClassLoader())));
        this.avgGenre = ((MusicGenre) in.readValue((MusicGenre.class.getClassLoader())));
        this.consecutiveAudioType = ((String) in.readValue((String.class.getClassLoader())));
        this.consecutiveGenre = ((MusicGenre) in.readValue((MusicGenre.class.getClassLoader())));

    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClassifyStats() {
    }

    /**
     *
     * @param consecutiveAudioType  Consecutive type of classify request that the user does
     * @param consecutiveGenre      Consecutive genre of classify request that the user does
     * @param avgAudioType          Avg of the type of classify request that the user does
     * @param avgGenre              Avg of the genre of classify request that the user does
     * @param avgSampleTime         Avg of the time of classify request that the user does
     */
    public ClassifyStats(Integer avgSampleTime, String avgAudioType, MusicGenre avgGenre, String consecutiveAudioType, MusicGenre consecutiveGenre) {
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

    public MusicGenre getAvgGenre() {
        return avgGenre;
    }

    public void setAvgGenre(MusicGenre avgGenre) {
        this.avgGenre = avgGenre;
    }

    public String getConsecutiveAudioType() {
        return consecutiveAudioType;
    }

    public void setConsecutiveAudioType(String consecutiveAudioType) {
        this.consecutiveAudioType = consecutiveAudioType;
    }

    public MusicGenre getConsecutiveGenre() {
        return consecutiveGenre;
    }

    public void setConsecutiveGenre(MusicGenre consecutiveGenre) {
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
