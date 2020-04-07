//
//package com.p4r4d0x.edmgenreclassifier.model.rest.stats;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//import com.p4r4d0x.edmgenreclassifier.model.rest.classify.MusicGenre;
//import com.p4r4d0x.edmgenreclassifier.model.rest.classify.SongDetail;
//
//import java.util.List;
//
//public class StatsResponse implements Parcelable {
//
//    public final static Creator<StatsResponse> CREATOR = new Creator<StatsResponse>() {
//
//
//        @SuppressWarnings({
//                "unchecked"
//        })
//        public StatsResponse createFromParcel(Parcel in) {
//            return new StatsResponse(in);
//        }
//
//        public StatsResponse[] newArray(int size) {
//            return (new StatsResponse[size]);
//        }
//
//    };
//    @SerializedName("classifyStats")
//    @Expose
//    private ClassifyStats classifyStats;
//    @SerializedName("userId")
//    @Expose
//    private String userId;
//    @SerializedName("relatedGenre")
//    @Expose
//    private List<MusicGenre> relatedGenre = null;
//    @SerializedName("lastClassify")
//    @Expose
//    private LastClassify lastClassify;
//    @SerializedName("relatedSongName")
//    @Expose
//    private List<String> relatedSongName = null;
//    @SerializedName("error")
//    @Expose
//    private Error error;
//
//    private StatsResponse(Parcel in) {
//        this.userId = ((String) in.readValue((String.class.getClassLoader())));
//        this.classifyStats = ((ClassifyStats) in.readValue((ClassifyStats.class.getClassLoader())));
//        in.readList(this.relatedGenre, (String.class.getClassLoader()));
//        in.readList(this.relatedSongName, (String.class.getClassLoader()));
//        this.lastClassify = ((LastClassify) in.readValue((LastClassify.class.getClassLoader())));
//        this.error = ((Error) in.readValue((SongDetail.class.getClassLoader())));
//
//    }
//
//    /**
//     * No args constructor for use in serialization
//     *
//     */
//    public StatsResponse() {
//    }
//
//    public StatsResponse(String userId, ClassifyStats classifiyStats, List<MusicGenre> relatedGenre, List<String> relatedSongName, LastClassify lastClassify) {
//        super();
//        this.userId = userId;
//        this.classifyStats = classifiyStats;
//        this.relatedGenre = relatedGenre;
//        this.relatedSongName = relatedSongName;
//        this.lastClassify = lastClassify;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public ClassifyStats getClassifyStats() {
//        return classifyStats;
//    }
//
//    public void setClassifyStats(ClassifyStats classifyStats) {
//        this.classifyStats = classifyStats;
//    }
//
//    public List<MusicGenre> getRelatedGenre() {
//        return relatedGenre;
//    }
//
//    public void setRelatedGenre(List<MusicGenre> relatedGenre) {
//        this.relatedGenre = relatedGenre;
//    }
//
//    public List<String> getRelatedSongName() {
//        return relatedSongName;
//    }
//
//    public void setRelatedSongName(List<String> relatedSongName) {
//        this.relatedSongName = relatedSongName;
//    }
//
//    public LastClassify getLastClassify() {
//        return lastClassify;
//    }
//
//    public void setLastClassify(LastClassify lastClassify) {
//        this.lastClassify = lastClassify;
//    }
//
//    public Error getError() {
//        return error;
//    }
//
//    public void setError(Error error) {
//        this.error = error;
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(userId);
//        dest.writeValue(classifyStats);
//        dest.writeList(relatedGenre);
//        dest.writeList(relatedSongName);
//        dest.writeValue(lastClassify);
//        dest.writeValue(error);
//
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//}
