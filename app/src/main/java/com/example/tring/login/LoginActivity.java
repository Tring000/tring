package com.example.tring.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tring.R;
import com.example.tring.firebase.FireStoreService;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener
{

    private Button btn_ToList;


    private static final String TAG = "LoginActivity_TAG";
    private Context context=this;
    private FirebaseAuth mAuth;

    private EditText edit_PhoneNumber;

    private Button btn_Login;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;



    //private SignInButton btn_GoogleLogin;

    //private static final int RC_SIGN_IN=1000;
    //private GoogleSignInClient mGoogleSignInClient;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth=FirebaseAuth.getInstance();
        mAuth.setLanguageCode("kr");
       // setGoogleLogin();



        edit_PhoneNumber=findViewById(R.id.LoginActivity_Edit_PhoneNumber);

        btn_Login=findViewById(R.id.LoginActivity_Btn_Login);
        btn_Login.setOnClickListener(this);

        btn_ToList=findViewById(R.id.LoginActivity_Btn_List);
        btn_ToList.setOnClickListener(this);


        //btn_EmailSignUp=findViewById(R.id.LoginActivity_Btn_SignUp);
        //btn_EmailSignUp.setOnClickListener(this);

        //btn_GoogleLogin=findViewById(R.id.LoginActivity_Btn_GoogleLogin);
        //btn_GoogleLogin.setOnClickListener(this);

        mCallbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
        {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential)
            {
                Log.d(TAG, "onVerificationCompleted");
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e)
            {
                Log.d(TAG, "onVerificationFailed");
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token)
            {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                //mVerificationId = verificationId;
                //mResendToken = token;
            }
        };
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.LoginActivity_Btn_Login:
                startPhoneNumberAuth(edit_PhoneNumber.getText().toString());
                startActivity(new Intent(context, VerifyOTPActivity.class));
                break;

            /*case R.id.LoginActivity_Btn_GoogleLogin:
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                break;*/
            case R.id.LoginActivity_Btn_List:
                startActivity(new Intent(context, InitialUserInfoActivity.class));
            default:
        }

    }

    private void startPhoneNumberAuth(String phoneNumber)
    {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }




    /**
    private void setGoogleLogin()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try
            {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());

                FireStoreService.Auth.googleLogin(context, account.getIdToken());
            }
            catch (ApiException e)
            {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    **/
}