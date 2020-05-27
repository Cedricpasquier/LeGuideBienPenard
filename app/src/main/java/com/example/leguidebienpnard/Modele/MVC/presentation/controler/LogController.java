package com.example.leguidebienpnard.Modele.MVC.presentation.controler;

import com.example.leguidebienpnard.Modele.MVC.Singletons;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.User;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.UserConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LogController {


    public void makeApiCallPatchNewUser(String email) {
        UserConstructor userConstructor = new UserConstructor();
        String[] userAdrress = email.split("@");
        String userName = userAdrress[0];
        User user = userConstructor.preFabUser(userName);


        Call<ResponseBody> call = Singletons.getGbpApiInstance().postNewUserData(user, userName);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful() && response.body() != null){

                } else{

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
