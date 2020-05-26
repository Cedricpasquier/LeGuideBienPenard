package com.example.leguidebienpnard.Modele.MVC;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.leguidebienpnard.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SecondFragment extends Fragment {


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    private TextView nameObject;
    private TextView importanceDescription;
    private TextView localisationDescription;
    private TextView descriptionText;
    private Gson gson;
    private String stringNameObject;
    private ConstraintLayout contantLayout;

    public ProgressBar mProgressBar;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mProgressBar = view.findViewById(R.id.progressBarObjet);

        contantLayout = view.findViewById(R.id.constraintLayoutObjet);
        nameObject = view.findViewById(R.id.nameObject);
        importanceDescription = view.findViewById(R.id.importanceDescription);
        localisationDescription = view.findViewById(R.id.localisationDesciption);
        descriptionText = view.findViewById(R.id.descriptionText);

        stringNameObject = getArguments().getString("nameObject");
        nameObject.setText(stringNameObject);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });

        makeApiCallGet();
    }

    public void showProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    public static final String BASE_URL = "https://leguidebienpenard.firebaseio.com/ObjetsDeConfinement/Mf2r9sNvX3rLJpGkpwVD/Objets/";

    private void makeApiCallGet(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        GbpApi gbpApi = retrofit.create(GbpApi.class);

        Call<Objet> call = gbpApi.getObjectDetails( stringNameObject+ ".json");
        call.enqueue(new Callback<Objet>() {
            @Override
            public void onResponse(Call<Objet> call, Response<Objet> response) {
                if(response.isSuccessful() && response.body() != null){
                    Objet objet = response.body();
                    displayObect(objet);
                } else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<Objet> call, Throwable t) {
                //showRecycler(savedUser);
                showError();
            }
        });

    }

    private void displayObect(Objet objet){
        switch (objet.getImportance()){
            case "Haute":
                importanceDescription.setTextColor(Color.RED);
                break;
            case "Basse":
                importanceDescription.setTextColor(Color.BLUE);
                break;
        }
        importanceDescription.setText(objet.getImportance());
        localisationDescription.setText(objet.getLocalisation());
        descriptionText.setText(objet.getDescription());
        hideProgressBar();
        nameObject.setVisibility(View.VISIBLE);
        contantLayout.setVisibility(View.VISIBLE);
    }

    private void showError(){
        Toast.makeText(getActivity(),"API Error",Toast.LENGTH_SHORT).show();
    }


}
