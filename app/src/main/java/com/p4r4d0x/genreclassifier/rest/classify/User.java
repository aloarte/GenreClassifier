
package com.p4r4d0x.genreclassifier.rest.classify;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {

    public final static Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };
    @SerializedName("userLogged")
    @Expose
    private Boolean userLogged;
    @SerializedName("clientId")
    @Expose
    private String clientId;

    protected User(Parcel in) {
        this.userLogged = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.clientId = ((String) in.readValue((String.class.getClassLoader())));
    }

    /**
     * No args constructor for use in serialization
     * 
     */
    public User() {
    }

    /**
     * 
     * @param userLogged
     * @param clientId
     */
    public User(Boolean userLogged, String clientId) {
        super();
        this.userLogged = userLogged;
        this.clientId = clientId;
    }

    public Boolean getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(Boolean userLogged) {
        this.userLogged = userLogged;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(userLogged);
        dest.writeValue(clientId);
    }

    public int describeContents() {
        return 0;
    }

}
