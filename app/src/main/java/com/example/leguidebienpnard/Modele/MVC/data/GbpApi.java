package com.example.leguidebienpnard.Modele.MVC.data;

import com.example.leguidebienpnard.Modele.MVC.presentation.model.Objet;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.User;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GbpApi {

    @GET("Users/{nameUser}.json")
    Call<User> getUserList(@Path("nameUser") String nameUser);

    @GET("Objets.json")
    Call<HashMap<String,Objet>> getListObjectDetails();

    @PATCH("Users/{userName}.json")
    Call<ResponseBody> postNewUserData(@Body User user,@Path("userName") String userName);

    @PUT("Users/{userName}/listeObjets.json")
    Call<ResponseBody> putUserList(@Path("userName") String objectRoot, @Body List<Objet> listeObjets);


}
