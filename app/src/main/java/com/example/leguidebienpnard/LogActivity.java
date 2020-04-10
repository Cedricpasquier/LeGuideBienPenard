package com.example.leguidebienpnard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LogActivity extends AppCompatActivity {

    EditText editTextID;
    EditText editTextMDP;
    Button boutonValider;
    final Toast toast[] = new Toast[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_main);

        editTextID = (EditText) findViewById(R.id.editTextId);
        editTextMDP = (EditText) findViewById(R.id.editTextMDP);
        boutonValider = (Button) findViewById(R.id.boutonValider);
        toast[0] = null;
        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextID.getText().toString().equals("") || editTextMDP.getText().toString().equals("")){
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0] = Toast.makeText(getApplicationContext(),"Champs vide",Toast.LENGTH_SHORT);
                    toast[0].show();
                } else {
                    String user = editTextID.getText().toString();
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0] = Toast.makeText(getApplicationContext(),"Bienvenue "+ user,Toast.LENGTH_SHORT);
                    toast[0].show();
                }
            }
        });

    }
}
