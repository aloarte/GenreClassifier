package com.p4r4d0x.clasificadormusical;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

/*
 * Created by aloarte on 14/06/2017.
 */


public class AsynkTaskClasifySong extends AsyncTask<SongDescription, Void, DataClasifySongResponse> {

    private OnSongClassified callbackListener;


    public interface OnSongClassified{
        void onSongClassifiedSuccess(MusicGenres genre);
        void onSongClassifiedError(String errorMessage);

    }

    public AsynkTaskClasifySong(OnSongClassified listener){
        this.callbackListener =listener;
    }

    @Override
    protected DataClasifySongResponse doInBackground(SongDescription... songDescriptions) {
        URL url;
        DataClasifySongResponse pojoResponse = null;
        StringBuilder sb = new StringBuilder();
        String name="The island", song="Dubstep";
        String boundary = "*****";
        String lineEnd = "\r\n";
        String twoHyphens = "--";


        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        try {

            /*
             * Build the JSON from the GSON from the POJO
             */
            File sourceFile = new File(/*getPath(songDescriptions[0].getSongURI()*/songDescriptions[0].getSongStringUri()/*, songDescriptions[0].getParentContext())*/);
            String fileName = sourceFile.getName() ;
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            String request = "http://192.168.1.129:8081/clasify";
            byte[] bytes = readFileToByteArray(sourceFile);

            String audioEncoded = Base64.encodeToString(bytes, 0);

            Gson gsonBuilder                     = new GsonBuilder().create();
            DataClasifySongRequest pojoClasifySongRequest   = new DataClasifySongRequest(name,audioEncoded);
            String stringJSONClasifySongRequest             = gsonBuilder.toJson(pojoClasifySongRequest);
            JSONObject JSONClassifySongRequest              = new JSONObject(stringJSONClasifySongRequest);





            //byte[] decoded = Base64.decode(encoded, 0);

            /*
             * Build the connection
             */
            url = new URL(request);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                //connection.setUseCaches (false);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                //connection.setInstanceFollowRedirects(false);
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("charset", "utf-8");
               // connection.setRequestProperty("Connection", "Keep-Alive");


                /*
                 * Build the dataoutputstream with the json and aditional info
                 */
                DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
                wr.writeBytes(JSONClassifySongRequest.toString());
                wr.flush();
                wr.close();


                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();

                    System.out.println("" + sb.toString());
                    try {
                        pojoResponse = (DataClasifySongResponse) gsonBuilder.fromJson(sb.toString(), DataClasifySongResponse.class);
                    } catch (Exception e) {
                        return null;
                    }
                }


            return pojoResponse;
        }
        catch(IOException e){
            e.printStackTrace();
            return null;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    private byte[] readFileToByteArray(File sourceFile) {

        int size = (int) sourceFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(sourceFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            return bytes;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
            return bytes;
        }
    }


    @Override
    protected void onPreExecute(){
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(DataClasifySongResponse dataClasifySongResponse) {
        super.onPostExecute(dataClasifySongResponse);
        if(dataClasifySongResponse!=null){
            callbackListener.onSongClassifiedSuccess(dataClasifySongResponse.getSonGenre());
        }
        else{
            callbackListener.onSongClassifiedError("Song couldn't be classified");
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


