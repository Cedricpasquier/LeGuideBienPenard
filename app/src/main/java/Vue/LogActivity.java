package Vue;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.leguidebienpnard.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Controler.Authentification;


public class LogActivity extends BaseActivity implements View.OnClickListener{


    private ProgressBar progressBar;
    public EditText editTextEmail;
    public EditText editTextMDP;
    private FirebaseAuth mAuth;
    private Button buttonSwitch;

    final Toast toast[] = new Toast[1];

    public Authentification authentification;

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
        mAuth = FirebaseAuth.getInstance();

        try {
            authentification = new Authentification(mAuth);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void updateUI() {
        hideProgressBar();
    }


    @Override
    public void onClick(View v){
        int i = v.getId();
        if(i == R.id.buttonSignedIn){
            switch(authentification.signIn(editTextEmail.getText().toString(), editTextMDP.getText().toString())){
                case "success":
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0].makeText(getApplicationContext(), "Authentication réussi.",
                            Toast.LENGTH_SHORT).show();
                    updateUI();
                    break;
                case "email":
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0].makeText(getApplicationContext(), "Votre adresse email doit être vérifier.",
                            Toast.LENGTH_SHORT).show();
                    updateUI();
                    break;
                case "fail":
                    // If sign in fails, display a message to the user.
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0].makeText(getApplicationContext(), "L'authentication a échoué.",
                            Toast.LENGTH_SHORT).show();
                    updateUI();
                    break;
                default:

            }
        } else if (i == R.id.buttonCreateAccount) {
            switch(authentification.createAccount(editTextEmail.getText().toString(), editTextMDP.getText().toString())){
                case "success":
                    // Sign in success, update UI with the signed-in user's information
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0].makeText(getApplicationContext(), "Compte créé, veuillez verifier l'email",
                            Toast.LENGTH_SHORT).show();
                    updateUI();
                    break;
                case "fail":
                    if(toast[0]!=null){
                        toast[0].cancel();
                    }
                    toast[0].makeText(getApplicationContext(), "La création du compte a échoué",
                            Toast.LENGTH_SHORT).show();
                    updateUI();
                    break;
                default:
            };

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

    protected boolean validateForm(){
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

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
