package com.p4r4d0x.clasificadormusical.rest.old_rest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Pojo to make the main classify request
 */

public class DataClassifySongRequest {

    @SerializedName("songName")
    @Expose
    public String songName;

    @SerializedName("songData")
    @Expose
    public Object songData;

    public DataClassifySongRequest(String name, Object data){
        this.songName = name;
        this.songData = data;
    }


    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public Object getSongData() {
        return songData;
    }

    public void setSongData(Object songData) {
        this.songData = songData;
    }
}
