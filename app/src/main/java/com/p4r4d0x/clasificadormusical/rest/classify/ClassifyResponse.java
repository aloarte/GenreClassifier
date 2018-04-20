
package com.p4r4d0x.clasificadormusical.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClassifyResponse implements Parcelable {

    public final static Creator<ClassifyResponse> CREATOR = new Creator<ClassifyResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ClassifyResponse createFromParcel(Parcel in) {
            return new ClassifyResponse(in);
        }

        public ClassifyResponse[] newArray(int size) {
            return (new ClassifyResponse[size]);
        }

    };
    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    @SerializedName("songName")
    @Expose
    private String songName;

    protected ClassifyResponse(Parcel in) {
        in.readList(this.genres, (Genre.class.getClassLoader()));
        this.songName = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public ClassifyResponse() {
    }

    /**
     * @param genres
     * @param songName
     */
    public ClassifyResponse(List<Genre> genres, String songName) {
        super();
        this.genres = genres;
        this.songName = songName;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(genres);
        dest.writeValue(songName);
    }

    public int describeContents() {
        return 0;
    }

}
