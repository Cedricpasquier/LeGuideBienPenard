package com.example.leguidebienpnard.Model;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.leguidebienpnard.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextMDP;
    Button boutonValider;
    final Toast toast[] = new Toast[1];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_main);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextMDP = (EditText) findViewById(R.id.editTextMDP);
        boutonValider = (Button) findViewById(R.id.boutonValider);
        toast[0] = null;
        boutonValider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEmailValid(editTextEmail.getText().toString())){
                    if(!editTextMDP.getText().toString().equals("")){
                        String user = editTextEmail.getText().toString();
                        if(toast[0]!=null){
                            toast[0].cancel();
                        }
                        toast[0] = Toast.makeText(getApplicationContext(),"Bienvenue "+ user,Toast.LENGTH_SHORT);
                        toast[0].show();
                    } else {
                        editTextMDP.setError("Veuillez entrer un mot de passe.");
                    }
                } else {
                    editTextEmail.setError("Veuilez entrer une adresse valide.");
                }
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
