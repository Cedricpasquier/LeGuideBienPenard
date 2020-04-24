package Controler;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.example.leguidebienpnard.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Vue.LogActivity;
import Vue.BaseActivity;

public class Authentification extends LogActivity{

    private static final String TAG = "EmailPassword";
    final String[] answer = new String[1];
    private Context context;
    private FirebaseAuth mAuth;

    public Authentification(FirebaseAuth mAuth) throws JSONException {
        // Initialize Firebase Auth
        this.mAuth = mAuth;
    }

    public String signIn(String email, String password){
        if (!validateForm()) {
            return "unvalide";
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
                                answer[0] = "success";
                            } else {
                                answer[0] = "email";
                            }
                        } else {
                            Log.w(TAG, "signInWithEmail:failure",task.getException());
                            answer[0] = "fail";
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        return answer[0];
    }

    public String createAccount(String email, String password) {
        if (!validateForm()) {
            return "unvalide";
        }
        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            sendEmailVerification();
                            answer[0] = "success";
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            answer[0] = "fail";
                        }
                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        return answer[0];
    }

    public void sendEmailVerification() {

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
                            Log.d(TAG, "sendingEmail:success");
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        mAuth.signOut();
    }





}
