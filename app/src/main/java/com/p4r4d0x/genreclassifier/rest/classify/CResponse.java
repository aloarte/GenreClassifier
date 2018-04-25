
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CResponse implements Parcelable {

    public final static Creator<CResponse> CREATOR = new Creator<CResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CResponse createFromParcel(Parcel in) {
            return new CResponse(in);
        }

        public CResponse[] newArray(int size) {
            return (new CResponse[size]);
        }

    };
    @SerializedName("classifyResponse")
    @Expose
    private ClassifyResponse classifyResponse;

    protected CResponse(Parcel in) {
        this.classifyResponse = ((ClassifyResponse) in.readValue((ClassifyResponse.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     */
    public CResponse() {
    }

    /**
     * @param classifyResponse
     */
    public CResponse(ClassifyResponse classifyResponse) {
        super();
        this.classifyResponse = classifyResponse;
    }

    public ClassifyResponse getClassifyResponse() {
        return classifyResponse;
    }

    public void setClassifyResponse(ClassifyResponse classifyResponse) {
        this.classifyResponse = classifyResponse;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(classifyResponse);
    }

    public int describeContents() {
        return 0;
    }

}
