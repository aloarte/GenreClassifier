package com.p4r4d0x.clasificadormusical;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by PCCom on 14/06/2017.
 */

public class DataClasifySongRequest {

    @SerializedName("songName")
    @Expose
    public String songName;

    @SerializedName("songGenre")
    @Expose
    public Object songData;

    public DataClasifySongRequest (String name, Object data){
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
