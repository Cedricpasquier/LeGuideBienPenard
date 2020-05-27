package com.example.leguidebienpnard.Modele.MVC.presentation.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.example.leguidebienpnard.Modele.MVC.presentation.view.BaseActivity;
import com.example.leguidebienpnard.Modele.MVC.presentation.view.LogActivity;
import com.example.leguidebienpnard.R;

public class MainActivity extends BaseActivity {

    public static String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle extras = getIntent().getExtras();
        userName = extras.getString("userName");
        //Toast.makeText(this,userName, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.deconnexion :
                Intent intent = new Intent(this, LogActivity.class);
                startActivity(intent);
            case R.id.refresh:
                this.recreate();

        }
        return super.onOptionsItemSelected(item);
    }

    public String getCurrentUserName(){
        return userName;
    }
}
