
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassifyRequest implements Parcelable {

    @SerializedName("songInfo")
    @Expose
    private SongInfo songInfo;
    public final static Creator<ClassifyRequest> CREATOR = new Creator<ClassifyRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ClassifyRequest createFromParcel(Parcel in) {
            return new ClassifyRequest(in);
        }

        public ClassifyRequest[] newArray(int size) {
            return (new ClassifyRequest[size]);
        }

    };
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("accuracy")
    @Expose
    private Double accuracy;
    @SerializedName("date")
    @Expose
    private long date;

    protected ClassifyRequest(Parcel in) {
        this.songInfo = ((SongInfo) in.readValue((SongInfo.class.getClassLoader())));
        this.accuracy = ((Double) in.readValue((Double.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.date = ((Long) in.readValue((Integer.class.getClassLoader())));
    }

    /**
     * 
     * @param songInfo
     * @param date
     * @param user
     * @param accuracy
     */
    public ClassifyRequest(SongInfo songInfo, Double accuracy, User user, long date) {
        super();
        this.songInfo = songInfo;
        this.accuracy = accuracy;
        this.user = user;
        this.date = date;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public Double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Double accuracy) {
        this.accuracy = accuracy;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(songInfo);
        dest.writeValue(accuracy);
        dest.writeValue(user);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}
