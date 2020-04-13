package com.example.leguidebienpnard.Model;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.leguidebienpnard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controler.BaseActivity;

public class LogActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "EmailPassword";
    private ProgressBar progressBar;
    private EditText editTextEmail;
    private EditText editTextMDP;

    private Button buttonSwitch;

    private FirebaseAuth mAuth;

    final Toast toast[] = new Toast[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_main);
        setProgressBar(R.id.progressBar);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // Views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextMDP = (EditText) findViewById(R.id.editTextMDP);


        //Buttons
        buttonSwitch = (Button) findViewById(R.id.buttonSwich);
        findViewById(R.id.buttonSignedIn).setOnClickListener(this);
        findViewById(R.id.buttonCreateAccount).setOnClickListener(this);
        buttonSwitch.setOnClickListener(this);



        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }
        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            sendEmailVerification();
                            updateUI();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            if(toast[0]!=null){
                                toast[0].cancel();
                            }
                            toast[0].makeText(getApplicationContext(), "Creation failed",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password){
        if (!validateForm()) {
            return;
        }

        showProgressBar();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            if(mAuth.getCurrentUser().isEmailVerified()){
                                Log.d(TAG, "signInWithEmail:success");
                                if(toast[0]!=null){
                                    toast[0].cancel();
                                }
                                toast[0].makeText(getApplicationContext(), "Authentication réussi.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            } else {
                                if(toast[0]!=null){
                                    toast[0].cancel();
                                }
                                toast[0].makeText(getApplicationContext(), "Votre adresse email doit être vérifier.",
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            if(toast[0]!=null){
                                toast[0].cancel();
                            }
                            toast[0].makeText(getApplicationContext(), "L'authentication a échoué.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI();
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });

    }

    private void sendEmailVerification() {

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button

                        if (task.isSuccessful()) {
                            if(toast[0]!=null){
                                toast[0].cancel();
                            }
                            toast[0].makeText(getApplicationContext(),
                                    "Email de verification envoyé à " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            if(toast[0]!=null){
                                toast[0].cancel();
                            }
                            toast[0].makeText(getApplicationContext(),
                                    "Erreur lors de l'envoie du mail.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }


    private void updateUI() {
        hideProgressBar();
        findViewById(R.id.editTextEmail).clearFocus();
        findViewById(R.id.editTextEmail).clearAnimation();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.signOut();
    }

    private boolean validateForm(){
        if(isEmailValid(editTextEmail.getText().toString())){
            if(!editTextMDP.getText().toString().equals("") || editTextMDP.getText().length()>6){
                return true;
            } else {
                editTextMDP.setError("Le mot de passe doit faire minimum 6 charactères.");
            }
        } else {
            editTextEmail.setError("Veuilez entrer une adresse valide.");
        }
        return false;
    }

    @Override
    public void onClick(View v){
        int i = v.getId();
        if(i == R.id.buttonSignedIn){
            signIn(editTextEmail.getText().toString(), editTextMDP.getText().toString());
        } else if (i == R.id.buttonCreateAccount) {
            createAccount(editTextEmail.getText().toString(), editTextMDP.getText().toString());
            buttonSwitch.setText("Créer un compte");
            findViewById(R.id.buttonSignedIn).setVisibility(View.VISIBLE);
            findViewById(R.id.buttonCreateAccount).setVisibility(View.GONE);
        } else if(i == R.id.buttonSwich){
            if(buttonSwitch.getText().toString().equals("Créer un compte")){
                buttonSwitch.setText("S'authentifier");
                findViewById(R.id.buttonSignedIn).setVisibility(View.GONE);
                findViewById(R.id.buttonCreateAccount).setVisibility(View.VISIBLE);
            } else if(buttonSwitch.getText().toString().equals("S'authentifier")){
                buttonSwitch.setText("Créer un compte");
                findViewById(R.id.buttonSignedIn).setVisibility(View.VISIBLE);
                findViewById(R.id.buttonCreateAccount).setVisibility(View.GONE);
            }
        }
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
