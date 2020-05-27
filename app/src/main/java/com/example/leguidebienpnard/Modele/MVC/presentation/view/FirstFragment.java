package com.example.leguidebienpnard.Modele.MVC.presentation.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leguidebienpnard.Modele.MVC.data.GbpApi;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.User;
import com.example.leguidebienpnard.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.leguidebienpnard.Modele.MVC.presentation.view.MainActivity.userName;

public class FirstFragment extends Fragment {
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private User savedUser;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler_view);
        sharedPreferences = view.getContext().getSharedPreferences("user_prefs",Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        savedUser = getDataFromCache();
        makeApiCallGet();
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
    }

    private User getDataFromCache(){
        String jsonUserList = sharedPreferences.getString("jsonUserList",null);

        if(jsonUserList == null){
            return null;
        } else {
            Type type = new TypeToken<User>(){}.getType();
            return gson.fromJson(jsonUserList,type);
        }
    }

    private void showRecycler(User user){
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(user,this.getContext());
        recyclerView.setAdapter(mAdapter);
    }

    public static final String BASE_URL = "https://leguidebienpenard.firebaseio.com/ObjetsDeConfinement/Mf2r9sNvX3rLJpGkpwVD/Users/";
    private void makeApiCallGet(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GbpApi gbpApi = retrofit.create(GbpApi.class);

        Call<User> call = gbpApi.getUserList( userName+ ".json");
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful() && response.body() != null){
                    User user = response.body();
                    saveList(user);
                    showRecycler(user);
                } else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                showRecycler(savedUser);
                showNoInternet();
            }
        });
    }



    private void saveList(User user) {
        String jsonString = gson.toJson(user);
        sharedPreferences
                .edit()
                .putString("jsonUserList", jsonString)
                .apply();
    }

    private void showError(){
        Toast.makeText(getActivity(),"API Error",Toast.LENGTH_SHORT).show();
    }

    private void showNoInternet(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setCancelable(false);
        builder.setTitle("Pas de connexion internet");
        builder.setMessage("Sans connexion internet les changements effectués ne seront pas sauvegardés");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

}
