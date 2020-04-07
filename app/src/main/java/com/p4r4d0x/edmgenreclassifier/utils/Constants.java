package com.p4r4d0x.edmgenreclassifier.utils;

public class Constants {

    /**
     * Remote Config key for the back endpoint
     */
    public static final String PROPERTY_BACK_ENDPOINT = "endpoint";

    /**
     * Remote Config key for the user stats method in the backend
     */
    public static final String PROPERTY_CRASH_REPORT_ENABLED = "crashReportEnabled";

    /**
     * Value of the cache expiration in the firebase fetch phase. This allows that each fetch will retrieve values from the service.
     */
    public static final int FETCH_FIREBASE_CACHE_EXPIRATION = 0;

    /**
     * Constant to get the code for the StartActivityForResult in the google auth
     */
    public static final int GOOGLE_AUTH_SAFR = 8888;

    /**
     * Bottom limit of the response code for the OK status
     */
    public static final int SERVER_CONTENT_BOT = 200;

    /**
     * Upper limit of the response code for the OK status
     */
    public static final int SERVER_CONTENT_TOP = 299;

    /**
     * Max timeout retries of the services
     */
    public static final int MAX_SERVICE_TIMEOUT_RETRIES = 1;

}
