package com.example.dmsimple;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;


import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import java.util.Arrays;

public class MainActivity<googleApiClient> extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private EditText editTextEmail;
    private EditText editTextPass;
    private Button btnlogin_norm;
    private Button forgot;
    private Button register;
    private Button new_facebook;
    private Button new_googlebtn;
    boolean valid = true;
    private static final String EMAIL = "email";
    private static final String USER_POSTS = "user_posts";
    private static final String AUTH_TYPE = "rerequest";
    SignInButton google_signInButton;
    private GoogleApiClient googleApiClient;
    private static final int SIGN_IN=1;


    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso= new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder( this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        callbackManager = CallbackManager.Factory.create();


        /*Google Login*/
        google_signInButton=findViewById(R.id.google_login_button);
        new_googlebtn=findViewById(R.id.new_googlebtn);
        new_googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (v== new_googlebtn) {
                    google_signInButton.performClick();
                }
                    Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                    startActivityForResult(intent, SIGN_IN);

            }
        });


        /*Facebook Login*/
        LoginButton facebook_login_button = findViewById(R.id.facebook_login_button);
        new_facebook = (Button)  findViewById(R.id.new_facebook);
        new_facebook.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View view) {
                                                if (view == new_facebook)
                                                    facebook_login_button.performClick();
                                            }
                                        });

// Set the initial permissions to request from the user while logging in
        facebook_login_button.setReadPermissions(Arrays.asList(EMAIL,USER_POSTS));
        facebook_login_button.setAuthType(AUTH_TYPE);

// Register a callback to respond to the user
        facebook_login_button.registerCallback(callbackManager,new FacebookCallback<LoginResult>()

                                            {
                                                @Override
                                                public void onSuccess (LoginResult loginResult){
                                                setResult(RESULT_OK);
                                                finish();
                                            }

                                                @Override
                                                public void onCancel () {
                                                setResult(RESULT_CANCELED);
                                                finish();
                                            }

                                                @Override
                                                public void onError (FacebookException exception){
                                                // App code
                                            }
                                            });


        /*Email Login*/
        //email login with your credentials
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        btnlogin_norm = (Button) findViewById(R.id.btnlogin_norm);
        findViewById(R.id.btnlogin_norm).setOnClickListener(new View.OnClickListener() {

                                                                @Override
                                                                public void onClick (View v){
                                                                    startActivity(new Intent (getApplicationContext(), DMSimple_login.class));
                                                                }
                                                            }
        );


        /*Forgot Password*/

        forgot = (Button) findViewById(R.id.forgot);
        findViewById(R.id.forgot).setOnClickListener(new View.OnClickListener() {

                                                         //calling ForgotPassword class
                                                         @Override
                                                         public void onClick (View v){
                                                             startActivity(new Intent (getApplicationContext(),ForgotPassword.class));
                                                         }
                                                     }
        );


        /*Create New User*/

        register = (Button) findViewById(R.id.register);
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {

                                                           //calling register_user class
                                                           @Override
                                                           public void onClick (View v){
                                                               startActivity(new Intent (getApplicationContext(),Register_User.class));
                                                           }
                                                       }
        );


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);


            if (result.isSuccess()){
                startActivity(new Intent (MainActivity.this, DMSimple_login.class));
                finish();
            }else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
    }


}


    //public boolean checkField(EditText textField){


        //if(textField.getText().toString().isEmpty()) {
           // textField.setError("Error");
           // valid = false;
        //}else{
            //valid=true;
        //}
        //return valid;
   // }

//}