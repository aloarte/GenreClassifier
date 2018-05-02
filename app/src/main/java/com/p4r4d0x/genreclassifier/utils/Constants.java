package com.p4r4d0x.genreclassifier.utils;

public class Constants {

    /**
     * Remote Config key for the back endpoint
     */
    public static final String PROPERTY_BACK_ENDPOINT = "endpoint";

    /**
     * Remote Config key for the classify method in the backend
     */
    public static final String PROPERTY_BACK_CLASSIFY = "resourceClassify";

    /**
     * Remote Config key for the user stats method in the backend
     */
    public static final String PROPERTY_BACK_STATS = "resourceStats";

    /**
     * Value of the cache expiration in the firebase fetch phase. This allows that each fetch will retrieve values from the service.
     */
    public static final int FETCH_FIREBASE_CACHE_EXPIRATION = 0;

    /**
     * Constant to get the code for the StartActivityForResult in the google auth
     */
    public static final int GOOGLE_AUTH_SAFR = 8888;
}
