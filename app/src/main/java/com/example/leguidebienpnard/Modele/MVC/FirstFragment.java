package com.example.leguidebienpnard.Modele.MVC;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leguidebienpnard.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.leguidebienpnard.Modele.MVC.LogActivity.userName;

public class FirstFragment extends Fragment {
    private FirebaseUser user;
    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

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
        makeApiCall();
        // use this setting to
        // improve performance if you know that changes
        // in content do not change the layout size
        // of the RecyclerView
    }

    private void showRecycler(List<User> listUsers){
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        HashMap mMap = new HashMap();
        ArrayList list = new ArrayList();

        for(int index = 0 ; index<listUsers.size();index++){
            if(listUsers.get(index).getNameUser().equals(userName)){
                for(Objet o: listUsers.get(index).getObjets()){
                    mMap.put(o.name,o.own);
                    list.add(mMap);
                    mMap = new HashMap();
                }
            }
        }

        mAdapter = new ListAdapter(list);
        recyclerView.setAdapter(mAdapter);
    }

    private static final String BASE_URL = "https://leguidebienpenard.firebaseio.com/ObjetsDeConfinement/";
    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GbpApi gbpApi = retrofit.create(GbpApi.class);

        Call<List<User>> call = gbpApi.getUserList();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<User> listUsers = response.body();
                    Toast.makeText(getActivity(),"API Success",Toast.LENGTH_SHORT).show();
                    showRecycler(listUsers);
                } else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                showError();
            }
        });
    }

    private void showError(){
        Toast.makeText(getActivity(),"API Error",Toast.LENGTH_SHORT).show();
    }

}
