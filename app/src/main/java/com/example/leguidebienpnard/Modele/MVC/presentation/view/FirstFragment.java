package com.example.leguidebienpnard.Modele.MVC.presentation.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.leguidebienpnard.Modele.MVC.Singletons;
import com.example.leguidebienpnard.Modele.MVC.presentation.controler.MainController;
import com.example.leguidebienpnard.Modele.MVC.presentation.model.User;
import com.example.leguidebienpnard.R;


public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private View viewFragment;
    public ProgressBar mProgressBar;

    private MainController controller;

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
        viewFragment = view;
        mProgressBar = view.findViewById(R.id.progressBarObjet);

        controller = new MainController(
                this,
                Singletons.getGsonInstance(),
                Singletons.getSharedPreferencesInstance(view.getContext()));

        controller.onStart();
    }



    public void showRecycler(User user){
        hideProgressBar();
        recyclerView = viewFragment.findViewById(R.id.recycler_view);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(user,controller);
        recyclerView.setAdapter(mAdapter);
    }

    public void showError(){
        Toast.makeText(getActivity(),"API Error",Toast.LENGTH_SHORT).show();
    }

    public void showNoInternet(){
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

    public void hideProgressBar() {
        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

}
