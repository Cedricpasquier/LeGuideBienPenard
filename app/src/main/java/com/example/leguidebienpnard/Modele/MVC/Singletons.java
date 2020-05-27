package com.example.leguidebienpnard.Modele.MVC;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.leguidebienpnard.Modele.MVC.data.GbpApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.leguidebienpnard.Modele.MVC.Constants.BASE_URL;

public class Singletons {

    private static Gson gsonInstance;
    private static GbpApi gbpApiInstance;
    private static SharedPreferences sharedPreferencesInstance;

    public static Gson getGsonInstance(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
            return gsonInstance;
        } else {
            return gsonInstance;
        }
    }

    public static GbpApi getGbpApiInstance(){
        if(gbpApiInstance == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGsonInstance()))
                    .build();

            gbpApiInstance = retrofit.create(GbpApi.class);
            return gbpApiInstance;
        } else {
            return gbpApiInstance;
        }
    }

    public static SharedPreferences getSharedPreferencesInstance(Context context) {
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
            return sharedPreferencesInstance;
        } else {
            return sharedPreferencesInstance;
        }

    }
}
