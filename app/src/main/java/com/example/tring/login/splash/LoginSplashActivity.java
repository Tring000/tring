package com.example.tring.login.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

import com.example.tring.R;
import com.example.tring.firebase.FireStoreService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginSplashActivity extends AppCompatActivity
{
    Context context;

    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);

        context=this;
        getIntent();

        firebaseUser=auth.getCurrentUser();

        FireStoreService.Auth.signUpOrNot(context, firebaseUser.getEmail());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

            }
        },2800);
    }
}