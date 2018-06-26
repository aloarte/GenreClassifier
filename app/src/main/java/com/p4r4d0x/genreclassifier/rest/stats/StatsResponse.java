
package com.p4r4d0x.genreclassifier.rest.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    @SerializedName("classifiyStats")
    @Expose
    private ClassifiyStats classifiyStats;
    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("relatedGenre")
    @Expose
    private List<String> relatedGenre = null;
    @SerializedName("lastClassify")
    @Expose
    private LastClassify lastClassify;
    @SerializedName("relatedSongName")
    @Expose
    private List<String> relatedSongName = null;

    protected StatsResponse(Parcel in) {
        this.userId = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.classifiyStats = ((ClassifiyStats) in.readValue((ClassifiyStats.class.getClassLoader())));
        in.readList(this.relatedGenre, (String.class.getClassLoader()));
        in.readList(this.relatedSongName, (String.class.getClassLoader()));
        this.lastClassify = ((LastClassify) in.readValue((LastClassify.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public StatsResponse() {
    }

    /**
     *
     * @param relatedSongName
     * @param classifiyStats
     * @param relatedGenre
     * @param userId
     * @param lastClassify
     */
    public StatsResponse(Integer userId, ClassifiyStats classifiyStats, List<String> relatedGenre, List<String> relatedSongName, LastClassify lastClassify) {
        super();
        this.userId = userId;
        this.classifiyStats = classifiyStats;
        this.relatedGenre = relatedGenre;
        this.relatedSongName = relatedSongName;
        this.lastClassify = lastClassify;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ClassifiyStats getClassifiyStats() {
        return classifiyStats;
    }

    public void setClassifiyStats(ClassifiyStats classifiyStats) {
        this.classifiyStats = classifiyStats;
    }

    public List<String> getRelatedGenre() {
        return relatedGenre;
    }

    public void setRelatedGenre(List<String> relatedGenre) {
        this.relatedGenre = relatedGenre;
    }

    public List<String> getRelatedSongName() {
        return relatedSongName;
    }

    public void setRelatedSongName(List<String> relatedSongName) {
        this.relatedSongName = relatedSongName;
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
        dest.writeList(relatedGenre);
        dest.writeList(relatedSongName);
        dest.writeValue(lastClassify);
    }

    public int describeContents() {
        return 0;
    }

}
