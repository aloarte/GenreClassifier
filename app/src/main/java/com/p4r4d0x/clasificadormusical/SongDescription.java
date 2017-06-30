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
    private String songStringUri;

    public SongDescription(String sonngName, Uri songURI, Context ctx, String stringUri) {
        this.sonngName = sonngName;
        this.songURI = songURI;
        this.parentContext = ctx;
        this.songStringUri = stringUri;
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

    public String getSongStringUri() {
        return songStringUri;
    }

    public void setSongStringUri(String songStringUri) {
        this.songStringUri = songStringUri;
    }
}
