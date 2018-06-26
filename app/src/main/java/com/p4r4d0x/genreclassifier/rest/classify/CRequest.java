
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CRequest implements Parcelable {

    public final static Creator<CRequest> CREATOR = new Creator<CRequest>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CRequest createFromParcel(Parcel in) {
            return new CRequest(in);
        }

        public CRequest[] newArray(int size) {
            return (new CRequest[size]);
        }

    };
    @SerializedName("classifyRequest")
    @Expose
    private ClassifyRequest classifyRequest;

    protected CRequest(Parcel in) {
        this.classifyRequest = ((ClassifyRequest) in.readValue((ClassifyRequest.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public CRequest() {
    }

    /**
     * 
     * @param classifyRequest
     */
    public CRequest(ClassifyRequest classifyRequest) {
        super();
        this.classifyRequest = classifyRequest;
    }

    public ClassifyRequest getClassifyRequest() {
        return classifyRequest;
    }

    public void setClassifyRequest(ClassifyRequest classifyRequest) {
        this.classifyRequest = classifyRequest;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(classifyRequest);
    }

    public int describeContents() {
        return 0;
    }

}
