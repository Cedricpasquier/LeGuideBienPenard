package com.example.leguidebienpnard.Modele.MVC.presentation.controler;

import android.content.SharedPreferences;

import com.example.leguidebienpnard.Modele.MVC.presentation.model.Objet;
import com.example.leguidebienpnard.Modele.MVC.presentation.view.FirstFragment;
import com.example.leguidebienpnard.Modele.MVC.presentation.view.SecondFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;

public class DetailsObjectController {

    private HashMap<String, Objet> objectDetailsList;
    private SharedPreferences sharedPreferences;
    private SecondFragment secondFragment;
    private Gson gson;

    public DetailsObjectController(SecondFragment secondFragment, Gson gson, SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        this.secondFragment = secondFragment;
    }

    public void onStart(){
        objectDetailsList = getObjectsDataFromCache();
    }

    private HashMap<String, Objet> getObjectsDataFromCache(){
        String jsonObjectsList = sharedPreferences.getString("jsonObjectsList",null);

        if(jsonObjectsList == null){
            return null;
        } else {
            Type type = new TypeToken<HashMap<String, Objet>>(){}.getType();
            return gson.fromJson(jsonObjectsList,type);
        }
    }

    public Objet getDetailledObject(String nameObject){
        return objectDetailsList.get(nameObject);
    }

}
