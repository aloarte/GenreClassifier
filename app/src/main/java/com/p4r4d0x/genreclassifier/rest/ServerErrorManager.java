package com.p4r4d0x.genreclassifier.rest;

import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.p4r4d0x.genreclassifier.GenreClassificatorApplication;

/**
 * Manage the errors from the server.
 * Register them into the firebase
 */
public class ServerErrorManager {

    /**
     * InternalServerError: Unexpected error from service
     */
    private final static int SERVER_ERROR_CODE_INTERNAL_SERVER_ERROR = 500;

    /**
     * BadGateway: problem with some proxy/gateway
     */
    private final static int SERVER_ERROR_CODE_BAD_GATEWAY = 501;

    /**
     * Forbidden: the service is unavailable. Mainteinance or busy
     */
    private final static int SERVER_ERROR_CODE_SERVICE_UNAVAILABLE = 503;

    /**
     * BadRequest: The request had wrong sintaxis
     */
    private final static int CLIENT_ERROR_CODE_BAD_REQUEST = 400;

    /**
     * Unauthorized: not authenticated
     */
    private final static int CLIENT_ERROR_CODE_UNAUTHORIZED = 401;

    /**
     * Forbidden: request refused by role
     */
    private final static int CLIENT_ERROR_CODE_FORBIDDEN = 403;

    /**
     * NotFound: Resource not found
     */
    private final static int CLIENT_ERROR_CODE_NOT_FOUND = 404;

    /**
     * Classify method
     */
    private final static String SERVICE_METHOD_CLASSIFY = "/classifier/clasifygenre/";

    /**
     * User stats method
     */
    private final static String SERVICE_METHOD_STATS = "/classifier/stats/";


    /**
     * Manage the server errror produced
     *
     * @param errorCode     int with the errorcode
     * @param serviceMethod service method that was called
     */
    public static void manageServerError(int errorCode, String serviceMethod) {
        switch (errorCode) {
            case SERVER_ERROR_CODE_INTERNAL_SERVER_ERROR:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Internal server error.");
                break;
            case SERVER_ERROR_CODE_BAD_GATEWAY:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Bad gateway.");
                break;
            case SERVER_ERROR_CODE_SERVICE_UNAVAILABLE:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Service unavailable.");
                break;
            case CLIENT_ERROR_CODE_BAD_REQUEST:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Bad request.");
                break;
            case CLIENT_ERROR_CODE_UNAUTHORIZED:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Unauthorized.");
                break;
            case CLIENT_ERROR_CODE_FORBIDDEN:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Forbidden.");
                break;
            case CLIENT_ERROR_CODE_NOT_FOUND:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Not found.");
                break;
            default:
//                Crashlytics.log(Log.ERROR, "ServerError", "Error "+errorCode +" in "+ serviceMethod+" Server error.");
                break;
        }
    }

    /**
     * Manage the exceptions obtained in the connection with the server
     *
     * @param serverException    Exception to track
     * @param serviceMethod      Method called
     * @param applicationContext Application context
     */
    public static void manageServerException(Throwable serverException, String serviceMethod, GenreClassificatorApplication applicationContext) {
        String exceptionMessage = serverException.getMessage();
        if (applicationContext.isCrashReportEnabled()) {
            Crashlytics.log(Log.ERROR, "ServerError", "Exception in " + serviceMethod + ". Reason: " + exceptionMessage);
            Crashlytics.logException(serverException);

        }
    }

    /**
     * Getter of the classify service method name
     *
     * @return method name
     */
    public static String getServiceMethodClassify() {
        return SERVICE_METHOD_CLASSIFY;
    }

    /**
     * Getter of the user stats service method name
     *
     * @return method name
     */
    public static String getServiceMethodStats() {
        return SERVICE_METHOD_STATS;
    }
}
