//
//package com.p4r4d0x.edmgenreclassifier.model.rest.stats;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import com.p4r4d0x.edmgenreclassifier.model.rest.classify.MusicGenre;
//
//public class LastClassify implements Parcelable {
//
//    @SerializedName("lastGenre")
//    @Expose
//    private MusicGenre lastGenre;
//    @SerializedName("lastAudioType")
//    @Expose
//    private String lastAudioType;
//    @SerializedName("lastSampleTime")
//    @Expose
//    private Integer lastSampleTime;
//    public final static Creator<LastClassify> CREATOR = new Creator<LastClassify>() {
//
//
//        @SuppressWarnings({
//                "unchecked"
//        })
//        public LastClassify createFromParcel(Parcel in) {
//            return new LastClassify(in);
//        }
//
//        public LastClassify[] newArray(int size) {
//            return (new LastClassify[size]);
//        }
//
//    };
//
//    protected LastClassify(Parcel in) {
//        this.lastGenre = ((MusicGenre) in.readValue((MusicGenre.class.getClassLoader())));
//        this.lastAudioType = ((String) in.readValue((String.class.getClassLoader())));
//        this.lastSampleTime = ((Integer) in.readValue((Integer.class.getClassLoader())));
//    }
//
//    /**
//     * No args constructor for use in serialization
//     *
//     */
//    public LastClassify() {
//    }
//
//    /**
//     *
//     * @param lastGenre
//     * @param lastAudioType
//     * @param lastSampleTime
//     */    public LastClassify(MusicGenre lastGenre, String lastAudioType, Integer lastSampleTime) {
//
//        super();
//        this.lastGenre = lastGenre;
//        this.lastAudioType = lastAudioType;
//        this.lastSampleTime = lastSampleTime;
//    }
//
//    public MusicGenre getLastGenre() {
//        return lastGenre;
//    }
//
//    public void setLastGenre(MusicGenre lastGenre) {
//        this.lastGenre = lastGenre;
//    }
//
//    public String getLastAudioType() {
//        return lastAudioType;
//    }
//
//    public void setLastAudioType(String lastAudioType) {
//        this.lastAudioType = lastAudioType;
//    }
//
//    public Integer getLastSampleTime() {
//        return lastSampleTime;
//    }
//
//    public void setLastSampleTime(Integer lastSampleTime) {
//        this.lastSampleTime = lastSampleTime;
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(lastGenre);
//        dest.writeValue(lastAudioType);
//        dest.writeValue(lastSampleTime);
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//}
