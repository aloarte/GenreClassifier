//package com.p4r4d0x.edmgenreclassifier.model.rest;
//
//import android.os.Parcel;
//import android.os.Parcelable;
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class Error implements Parcelable {
//
//    public final static Parcelable.Creator<Error> CREATOR = new Creator<Error>() {
//
//
//        @SuppressWarnings({
//                "unchecked"
//        })
//        public Error createFromParcel(Parcel in) {
//            return new Error(in);
//        }
//
//        public Error[] newArray(int size) {
//            return (new Error[size]);
//        }
//
//    };
//    @SerializedName("errorCode")
//    @Expose
//    private Integer errorCode;
//    @SerializedName("message")
//    @Expose
//    private String message;
//
//    private Error(Parcel in) {
//        this.errorCode = ((Integer) in.readValue((Integer.class.getClassLoader())));
//        this.message = ((String) in.readValue((String.class.getClassLoader())));
//    }
//
//    /**
//     * No args constructor for use in serialization
//     */
//    public Error() {
//    }
//
//    /**
//     * @param message
//     * @param errorCode
//     */
//    public Error(Integer errorCode, String message) {
//        super();
//        this.errorCode = errorCode;
//        this.message = message;
//    }
//
//    public Integer getErrorCode() {
//        return errorCode;
//    }
//
//    public void setErrorCode(Integer errorCode) {
//        this.errorCode = errorCode;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeValue(errorCode);
//        dest.writeValue(message);
//    }
//
//    public int describeContents() {
//        return 0;
//    }
//
//}