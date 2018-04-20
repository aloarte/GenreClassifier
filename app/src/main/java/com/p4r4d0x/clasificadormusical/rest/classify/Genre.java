
package com.p4r4d0x.clasificadormusical.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre implements Parcelable {

    public final static Creator<Genre> CREATOR = new Creator<Genre>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Genre createFromParcel(Parcel in) {
            return new Genre(in);
        }

        public Genre[] newArray(int size) {
            return (new Genre[size]);
        }

    };
    @SerializedName("songGenre")
    @Expose
    private String songGenre;
    @SerializedName("precision")
    @Expose
    private Double precision;

    protected Genre(Parcel in) {
        this.songGenre = ((String) in.readValue((String.class.getClassLoader())));
        this.precision = ((Double) in.readValue((Double.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public Genre() {
    }

    /**
     * @param songGenre
     * @param precision
     */
    public Genre(String songGenre, Double precision) {
        super();
        this.songGenre = songGenre;
        this.precision = precision;
    }

    public String getSongGenre() {
        return songGenre;
    }

    public void setSongGenre(String songGenre) {
        this.songGenre = songGenre;
    }

    public Double getPrecision() {
        return precision;
    }

    public void setPrecision(Double precision) {
        this.precision = precision;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(songGenre);
        dest.writeValue(precision);
    }

    public int describeContents() {
        return 0;
    }

}
