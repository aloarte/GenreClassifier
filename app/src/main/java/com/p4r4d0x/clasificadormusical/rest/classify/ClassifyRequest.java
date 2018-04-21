
package com.p4r4d0x.clasificadormusical.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClassifyRequest implements Parcelable {

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
    @SerializedName("songInfo")
    @Expose
    private SongInfo songInfo;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("date")
    @Expose
    private Long date;

    protected ClassifyRequest(Parcel in) {
        this.songInfo = ((SongInfo) in.readValue((SongInfo.class.getClassLoader())));
        this.user = ((User) in.readValue((User.class.getClassLoader())));
        this.date = ((Long) in.readValue((Long.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ClassifyRequest() {
    }

    /**
     * @param songInfo
     * @param date
     * @param user
     */
    public ClassifyRequest(SongInfo songInfo, User user, Long date) {
        super();
        this.songInfo = songInfo;
        this.user = user;
        this.date = date;
    }

    public SongInfo getSongInfo() {
        return songInfo;
    }

    public void setSongInfo(SongInfo songInfo) {
        this.songInfo = songInfo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(songInfo);
        dest.writeValue(user);
        dest.writeValue(date);
    }

    public int describeContents() {
        return 0;
    }

}
