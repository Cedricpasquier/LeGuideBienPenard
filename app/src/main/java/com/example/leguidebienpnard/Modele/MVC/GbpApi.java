package com.example.leguidebienpnard.Modele.MVC;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface GbpApi {

    @GET
    Call<User> getUserList(@Url String nameUser);

    @GET
    Call<Objet> getObjectDetails(@Url String nameObject);

    @PATCH("{userName}.json")
    Call<ResponseBody> postNewUserData(@Body User user,@Path("userName") String userName);

    @PUT("{userName}/listeObjets.json")
    Call<ResponseBody> postUserList(@Path("userName") String objectRoot, @Body List<Objet> listeObjets);


}
