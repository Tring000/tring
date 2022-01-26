package com.example.tring;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity
{
    Context context;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context=this;

        FirebaseUser user=auth.getCurrentUser();
        textView=findViewById(R.id.MainActivity_test);
        textView.setText(user.getEmail());


    }
}