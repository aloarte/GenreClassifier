
package com.p4r4d0x.genreclassifier.rest.classify;

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
    @SerializedName("genre")
    @Expose
    private MusicGenre genre;
    @SerializedName("songDetail")
    @Expose
    private SongDetail songDetail;

    protected ClassifyResponse(Parcel in) {
        in.readList(this.genres, (Genre.class.getClassLoader()));
        this.genre = ((MusicGenre) in.readValue((MusicGenre.class.getClassLoader())));
        this.songDetail = ((SongDetail) in.readValue((SongDetail.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClassifyResponse() {
    }

    /**
     *
     * @param genre
     * @param genres
     * @param songDetail
     */
    public ClassifyResponse(List<Genre> genres, MusicGenre genre, SongDetail songDetail) {
        super();
        this.genres = genres;
        this.genre = genre;
        this.songDetail = songDetail;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public MusicGenre getGenre() {
        return genre;
    }

    public void setGenre(MusicGenre genre) {
        this.genre = genre;
    }

    public SongDetail getSongDetail() {
        return songDetail;
    }

    public void setSongDetail(SongDetail songDetail) {
        this.songDetail = songDetail;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(genres);
        dest.writeValue(genre);
        dest.writeValue(songDetail);
    }

    public int describeContents() {
        return 0;
    }

}
