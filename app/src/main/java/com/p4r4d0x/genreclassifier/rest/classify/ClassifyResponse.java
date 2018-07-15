
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.p4r4d0x.genreclassifier.rest.Error;

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
    @SerializedName("error")
    @Expose
    private Error error;


    private ClassifyResponse(Parcel in) {
        in.readList(this.genres, (Genre.class.getClassLoader()));
        this.genre = ((MusicGenre) in.readValue((MusicGenre.class.getClassLoader())));
        this.songDetail = ((SongDetail) in.readValue((SongDetail.class.getClassLoader())));
        this.error = ((Error) in.readValue((SongDetail.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public ClassifyResponse() {
    }

    /**
     *
     * @param genre      Enum with the genre classified
     * @param genres     List of enum with the consecutive related genres
     * @param songDetail Details of the song classified
     */
    public ClassifyResponse(List<Genre> genres, MusicGenre genre, SongDetail songDetail, Error error) {
        super();
        this.genres = genres;
        this.genre = genre;
        this.songDetail = songDetail;
        this.error = error;
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

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(genres);
        dest.writeValue(genre);
        dest.writeValue(songDetail);
        dest.writeValue(error);
    }

    public int describeContents() {
        return 0;
    }

}
