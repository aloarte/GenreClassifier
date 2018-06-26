package com.p4r4d0x.genreclassifier.rest.old_rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.p4r4d0x.genreclassifier.rest.classify.MusicGenre;


/**
 * Pojo to make the main classify response
 */
public class DataClassifySongResponse {

    @SerializedName("songName")
    @Expose
    public String songName;

    @SerializedName("songGenre")
    @Expose
    public MusicGenre songGenre;

    public DataClassifySongResponse(String name, MusicGenre genre) {
        this.songName= name;
        this.songGenre = genre;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public MusicGenre getSonGenre() {
        return songGenre;
    }

    public void setSongGenre(MusicGenre sonGenre) {
        this.songGenre = sonGenre;
    }
}
