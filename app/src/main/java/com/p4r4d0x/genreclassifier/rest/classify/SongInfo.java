
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongInfo implements Parcelable {

    public final static Creator<SongInfo> CREATOR = new Creator<SongInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SongInfo createFromParcel(Parcel in) {
            return new SongInfo(in);
        }

        public SongInfo[] newArray(int size) {
            return (new SongInfo[size]);
        }

    };
    @SerializedName("sampleType")
    @Expose
    private String sampleType;
    @SerializedName("songName")
    @Expose
    private String songName;
    @SerializedName("sampleTime")
    @Expose
    private Integer sampleTime;
    @SerializedName("songData")
    @Expose
    private String songData;

    protected SongInfo(Parcel in) {
        this.sampleType = ((String) in.readValue((String.class.getClassLoader())));
        this.songName = ((String) in.readValue((String.class.getClassLoader())));
        this.sampleTime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.songData = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public SongInfo() {
    }

    /**
     * @param songData
     * @param songName
     * @param sampleTime
     * @param sampleType
     */
    public SongInfo(String sampleType, String songName, Integer sampleTime, String songData) {
        super();
        this.sampleType = sampleType;
        this.songName = songName;
        this.sampleTime = sampleTime;
        this.songData = songData;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Integer getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(Integer sampleTime) {
        this.sampleTime = sampleTime;
    }

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sampleType);
        dest.writeValue(songName);
        dest.writeValue(sampleTime);
        dest.writeValue(songData);
    }

    public int describeContents() {
        return 0;
    }

}
