package com.example.leguidebienpnard.Modele.MVC.presentation.view;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.leguidebienpnard.Modele.MVC.Singletons;
import com.example.leguidebienpnard.Modele.MVC.presentation.controler.DetailsObjectController;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.Objet;
import com.example.leguidebienpnard.R;
import com.google.gson.GsonBuilder;

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
    private ConstraintLayout contantLayout;
    private DetailsObjectController controller;

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller = new DetailsObjectController(
                this,
                new GsonBuilder()
                        .setLenient()
                        .create(),
                Singletons.getSharedPreferencesInstance(view.getContext()));

        controller.onStart();



        contantLayout = view.findViewById(R.id.constraintLayoutObjet);
        nameObject = view.findViewById(R.id.nameObject);
        importanceDescription = view.findViewById(R.id.importanceDescription);
        localisationDescription = view.findViewById(R.id.localisationDesciption);
        descriptionText = view.findViewById(R.id.descriptionText);

        view.findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
        String stringNameObject = getArguments().getString("nameObject");
        nameObject.setText(stringNameObject);
        displayObect(controller.getDetailledObject(stringNameObject));
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
        nameObject.setVisibility(View.VISIBLE);
        contantLayout.setVisibility(View.VISIBLE);
    }


}
