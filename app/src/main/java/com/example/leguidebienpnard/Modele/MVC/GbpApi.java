package com.example.leguidebienpnard.Modele.MVC;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GbpApi {

    @GET("Mf2r9sNvX3rLJpGkpwVD/Users.json")
    Call<List<User>> getUserList();


}
