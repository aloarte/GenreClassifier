package com.p4r4d0x.clasificadormusical.async;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Base64;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.MalformedJsonException;
import com.p4r4d0x.clasificadormusical.rest.ClassifyRetrofitService;
import com.p4r4d0x.clasificadormusical.rest.StatsRetrofitService;
import com.p4r4d0x.clasificadormusical.rest.classify.CRequest;
import com.p4r4d0x.clasificadormusical.rest.classify.CResponse;
import com.p4r4d0x.clasificadormusical.rest.classify.ClassifyRequest;
import com.p4r4d0x.clasificadormusical.rest.classify.SongInfo;
import com.p4r4d0x.clasificadormusical.rest.old_rest.DataClassifySongRequest;
import com.p4r4d0x.clasificadormusical.rest.old_rest.DataClassifySongResponse;
import com.p4r4d0x.clasificadormusical.rest.old_rest.MusicGenres;
import com.p4r4d0x.clasificadormusical.rest.old_rest.SongDescription;
import com.p4r4d0x.clasificadormusical.rest.stats.SResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * AsyncTask to send the audio to the server
 */
public class AsynkTaskClasifySong extends AsyncTask<ClassifyRequest, Void, CResponse> {


    /**
     * Base URL of the service
     */
    private final String baseUrl;

    /**
     * By default null. Is setted in case of any error
     */
    private String errorMessage ;

    /**
     * Callback to notify the events holded by this asynctask
     */

    private OnSongClassified callbackListener;


    public interface OnSongClassified{
        void onSongClassifiedSuccess(CResponse classifyResponse);
        void onSongClassifiedError(String errorMessage);

    }

    public AsynkTaskClasifySong(OnSongClassified listener, String baseUrl){
        this.callbackListener =listener;
        this.baseUrl=baseUrl;
    }

    @Override
    protected CResponse doInBackground(ClassifyRequest... classifyRequest) {
        try{


            Gson gson = new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(baseUrl)
                    .build();

            ClassifyRetrofitService service = retrofit.create(ClassifyRetrofitService.class);

            Response<CResponse> response = service.classifySong(new CRequest(classifyRequest[0])).execute();
            if (response.isSuccessful()) {
                return response.body();
            }
            else{
                return null;
            }
        }
        //The json is malformed. Catch and log to track the dev error
            catch (MalformedJsonException exMalformed) {
            exMalformed.printStackTrace();
            errorMessage = exMalformed.getMessage();
            return null;
        }
            catch (IllegalArgumentException | IOException exIllegalIO){
            errorMessage = exIllegalIO.getMessage();
            return null;
        }
        //General catch exception
            catch (Exception ex) {
            errorMessage= ex.getMessage();
            ex.printStackTrace();
            return null;
        }

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(CResponse serviceResponse) {
        super.onPostExecute(serviceResponse);
        //Check if the response is not null
        if(serviceResponse!=null){
            callbackListener.onSongClassifiedSuccess(serviceResponse);
        }
        //If its null, there is an error
        else{
            //Check if the error is not null
            if(errorMessage!=null){
                callbackListener.onSongClassifiedError(errorMessage);
            }
            //If null, return a general error
            else{
                callbackListener.onSongClassifiedError("GENERAL_ERROR");
            }
        }

    }

    public String getPath(Uri uri, Context context){
        String[] projection = {
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.ARTIST_ID,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.TRACK,
        };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        cursor.moveToFirst();


        String s= cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        Long id             = cursor.getLong( cursor.getColumnIndex( MediaStore.Audio.Media._ID ));
        String path         = cursor.getString( cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        String title        = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.TITLE ));
        String album        = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM ));
        String artist       = cursor.getString( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST ));
        Long albumId        = cursor.getLong( cursor.getColumnIndex( MediaStore.Audio.Media.ALBUM_ID ));
        Long artistId       = cursor.getLong( cursor.getColumnIndex( MediaStore.Audio.Media.ARTIST_ID ));
        Long duration       = cursor.getLong( cursor.getColumnIndex( MediaStore.Audio.Media.DURATION ));
        int trackNo         = cursor.getInt( cursor.getColumnIndex( MediaStore.Audio.Media.TRACK ));
        Uri urir            = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);


        cursor.close();


        return s;
    }


}


