package com.example.tring.login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chaos.view.PinView;
import com.example.tring.R;

public class VerifyOTPActivity extends AppCompatActivity
{

    private PinView pin_OTP;
    private Button btn_OK;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        pin_OTP= findViewById(R.id.VerifyOTPActivity_Pin_OTP);
        btn_OK= findViewById(R.id.VerifyOTPActivity_Btn_OK);

        btn_OK.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });
    }
}