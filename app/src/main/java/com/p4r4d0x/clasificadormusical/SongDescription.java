package com.p4r4d0x.clasificadormusical;

import android.content.Context;
import android.net.Uri;

/**
 * Created by aloarte on 17/06/2017.
 */

public class SongDescription {

    private String  sonngName;
    private Uri     songURI;
    private Context parentContext;

    public SongDescription(String sonngName, Uri songURI, Context ctx) {
        this.sonngName = sonngName;
        this.songURI = songURI;
        this.parentContext = ctx;
    }

    public String getSonngName() {
        return sonngName;
    }

    public void setSonngName(String sonngName) {
        this.sonngName = sonngName;
    }

    public Uri getSongURI() {
        return songURI;
    }

    public void setSongURI(Uri songURI) {
        this.songURI = songURI;
    }

    public Context getParentContext() {
        return parentContext;
    }

    public void setParentContext(Context parentContext) {
        this.parentContext = parentContext;
    }
}
