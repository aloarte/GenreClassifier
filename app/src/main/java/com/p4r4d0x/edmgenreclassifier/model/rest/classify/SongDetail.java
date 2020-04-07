//
//package com.p4r4d0x.edmgenreclassifier.model.rest.classify;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class SongDetail implements Parcelable {
//
//    public final static Creator<SongDetail> CREATOR = new Creator<SongDetail>() {
//
//
//        @SuppressWarnings({
//                "unchecked"
//        })
//        public SongDetail createFromParcel(Parcel in) {
//            return new SongDetail(in);
//        }
//
//        public SongDetail[] newArray(int size) {
//            return (new SongDetail[size]);
//        }
//
//    };
//    @SerializedName("bpm")
//    @Expose
//    private Integer bpm;
//    @SerializedName("songName")
//    @Expose
//    private String songName;
//    @SerializedName("danceability")
//    @Expose
//    private Boolean danceability;
//    @SerializedName("singableRate")
//    @Expose
//    private Double singableRate;
//
//    protected SongDetail(Parcel in) {
//        this.bpm = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        this.songName = ((String) in.readValue((String.class.getClassLoader())));
//        this.danceability = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
//        this.singableRate = ((Double) in.readValue((Double.class.getClassLoader())));
//    }
//
//    /**
//     * No args constructor for use in serialization
//     */
//    public SongDetail() {
//    }
//
//    /**
//     * @param singableRate
//     * @param danceability
//     * @param songName
//     * @param bpm
//     */
//    public SongDetail(Integer bpm, String songName, Boolean danceability, Double singableRate) {
//        super();
//        this.bpm = bpm;
//        this.songName = songName;
//        this.danceability = danceability;
//        this.singableRate = singableRate;
//    }
//
//    public Integer getBpm() {
//        return bpm;
//    }
//
//    public void setBpm(Integer bpm) {
//        this.bpm = bpm;
//    }
//
//    public String getSongName() {
//        return songName;
//    }
//
//    public void setSongName(String songName) {
//        this.songName = songName;
//    }
//
//    public Boolean getDanceability() {
//        return danceability;
//    }
//
//    public void setDanceability(Boolean danceability) {
//        this.danceability = danceability;
//    }
//
//    public Double getSingableRate() {
//        return singableRate;
//    }
//
//    public void setSingableRate(Double singableRate) {
//        this.singableRate = singableRate;
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(bpm);
//        dest.writeValue(songName);
//        dest.writeValue(danceability);
//        dest.writeValue(singableRate);
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//}
