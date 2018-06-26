
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SongInfo implements Parcelable {

    @SerializedName("sampleType")
    @Expose
    private String sampleType;
    @SerializedName("sampleTime")
    @Expose
    private Integer sampleTime;
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
    @SerializedName("songData")
    @Expose
    private String songData;
    @SerializedName("songName")
    @Expose
    private String songName;
    @SerializedName("songFormat")
    @Expose
    private String songFormat;

    protected SongInfo(Parcel in) {
        this.sampleType = ((String) in.readValue((String.class.getClassLoader())));
        this.sampleTime = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.songName = ((String) in.readValue((String.class.getClassLoader())));
        this.songData = ((String) in.readValue((String.class.getClassLoader())));
        this.songFormat = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public SongInfo() {
    }

    /**
     * 
     * @param songData
     * @param songFormat
     * @param songName
     * @param sampleTime
     * @param sampleType
     */
    public SongInfo(String sampleType, Integer sampleTime, String songName, String songData, String songFormat) {
        super();
        this.sampleType = sampleType;
        this.sampleTime = sampleTime;
        this.songName = songName;
        this.songData = songData;
        this.songFormat = songFormat;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public Integer getSampleTime() {
        return sampleTime;
    }

    public void setSampleTime(Integer sampleTime) {
        this.sampleTime = sampleTime;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongData() {
        return songData;
    }

    public void setSongData(String songData) {
        this.songData = songData;
    }

    public String getSongFormat() {
        return songFormat;
    }

    public void setSongFormat(String songFormat) {
        this.songFormat = songFormat;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sampleType);
        dest.writeValue(sampleTime);
        dest.writeValue(songName);
        dest.writeValue(songData);
        dest.writeValue(songFormat);
    }

    public int describeContents() {
        return 0;
    }

}
