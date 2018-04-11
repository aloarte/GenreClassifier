package com.p4r4d0x.clasificadormusical.rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Pojo to make the main classify response
 */
public class DataClassifySongResponse {

    @SerializedName("songName")
    @Expose
    public String songName;

    @SerializedName("songGenre")
    @Expose
    public MusicGenres songGenre;

    public DataClassifySongResponse(String name, MusicGenres genre){
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

    public void setSongGenre(MusicGenres sonGenre) {
        this.songGenre = sonGenre;
    }
}
