package com.p4r4d0x.clasificadormusical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PCCom on 14/06/2017.
 */

public class DataClasifySongResponse {

    @SerializedName("songName")
    @Expose
    public String songName;

    @SerializedName("songGenre")
    @Expose
    public MusicGenres songGenre;

    public DataClasifySongResponse (String name, MusicGenres genre){
        this.songName= name;
        this.songGenre = genre;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public MusicGenres getSonGenre() {
        return songGenre;
    }

    public void setSonGenre(MusicGenres sonGenre) {
        this.songGenre = sonGenre;
    }
}
