package com.example.leguidebienpnard.Modele.MVC.presentation.controler;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.leguidebienpnard.Modele.MVC.data.GbpApi;
import com.example.leguidebienpnard.Modele.MVC.Singletons;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.Objet;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.User;
import com.example.leguidebienpnard.Modele.MVC.presentation.view.FirstFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.leguidebienpnard.Modele.MVC.Constants.BASE_URL;
import static com.example.leguidebienpnard.Modele.MVC.Constants.userName;


public class MainController {

    private User savedUser;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Retrofit retrofit;

    private FirstFragment firstFragment;


    public MainController(FirstFragment firstFragment, Gson gson, SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        this.gson = gson;
        this.firstFragment = firstFragment;
    }

    public void onStart(){
        savedUser = getUserDataFromCache();

        makeApiCallGetUser();
        makeApiCallGetObjectsDetails();
    }

    private User getUserDataFromCache(){
        String jsonUserList = sharedPreferences.getString("jsonUserList",null);

        if(jsonUserList == null){
            return null;
        } else {
            Type type = new TypeToken<User>(){}.getType();
            return gson.fromJson(jsonUserList,type);
        }
    }

    private void saveUser(User user) {
        String jsonString = gson.toJson(user);
        sharedPreferences
                .edit()
                .putString("jsonUserList", jsonString)
                .apply();
    }

    private void saveObjectDetailsList(HashMap<String,Objet> objectDetailsList) {
        String jsonString = gson.toJson(objectDetailsList);
        sharedPreferences
                .edit()
                .putString("jsonObjectsList", jsonString)
                .apply();
    }


    private void makeApiCallGetUser(){

        Call<User> call = Singletons.getGbpApiInstance().getUserList(userName);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null){
                    User user = response.body();
                    saveUser(user);
                    firstFragment.showRecycler(user);
                } else{
                    firstFragment.showError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                firstFragment.showRecycler(savedUser);
                firstFragment.showNoInternet();
            }
        });
    }

    private void makeApiCallGetObjectsDetails(){

        Call<HashMap<String,Objet>> call = Singletons.getGbpApiInstance().getListObjectDetails();
        call.enqueue(new Callback<HashMap<String,Objet>>() {
            @Override
            public void onResponse(Call<HashMap<String,Objet>> call, Response<HashMap<String,Objet>> response) {
                if(response.isSuccessful() && response.body() != null){
                    HashMap<String,Objet> objectsDetailsList = response.body();
                    saveObjectDetailsList(objectsDetailsList);
                } else{

                }
            }

            @Override
            public void onFailure(Call<HashMap<String,Objet>> call, Throwable t) {
                //showRecycler(savedUser);
            }
        });

    }

    public void makeApiCallPost(List<Objet> listeObjet){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GbpApi gbpApi = retrofit.create(GbpApi.class);

        Call<ResponseBody> call = gbpApi.putUserList(userName,listeObjet);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(firstFragment.getContext(),"Donnée sauvegardée",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(firstFragment.getContext(),"Une erreur est survenue",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(firstFragment.getContext(),"Pas de connexion internet",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
