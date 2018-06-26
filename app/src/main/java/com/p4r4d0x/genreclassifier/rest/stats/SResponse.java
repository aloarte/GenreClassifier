
package com.p4r4d0x.genreclassifier.rest.stats;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SResponse implements Parcelable {

    public final static Creator<SResponse> CREATOR = new Creator<SResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public SResponse createFromParcel(Parcel in) {
            return new SResponse(in);
        }

        public SResponse[] newArray(int size) {
            return (new SResponse[size]);
        }

    };
    @SerializedName("statsResponse")
    @Expose
    private StatsResponse statsResponse;

    protected SResponse(Parcel in) {
        this.statsResponse = ((StatsResponse) in.readValue((StatsResponse.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public SResponse() {
    }

    /**
     * 
     * @param statsResponse
     */
    public SResponse(StatsResponse statsResponse) {
        super();
        this.statsResponse = statsResponse;
    }

    public StatsResponse getStatsResponse() {
        return statsResponse;
    }

    public void setStatsResponse(StatsResponse statsResponse) {
        this.statsResponse = statsResponse;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(statsResponse);
    }

    public int describeContents() {
        return 0;
    }

}
