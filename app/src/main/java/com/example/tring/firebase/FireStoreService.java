package com.example.tring.firebase;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.tring.MainActivity;
import com.example.tring.login.InitialUserInfoActivity;
import com.example.tring.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FireStoreService
{
    private static FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private static FirebaseUser firebaseUser;

    private static User user = User.getInstance();

    private static FirebaseFirestore db=FirebaseFirestore.getInstance();


    public static class Auth
    {
        private static final String TAG ="Auth_TAG";


        private static void enrollUser(Context context, String id)
        {

            db.collection("/INFO/User/UserList")
                    .document(id)
                    .set(user);
            // At final part of each login process, you should add idToken to user object.
        }

        public static void login(Context context, PhoneAuthCredential credential)
        {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                                if (task.isSuccessful())
                                {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithCredential:success");

                                    FirebaseUser user = task.getResult().getUser();
                                    // Update UI
                                }
                                else
                                {
                                    // Sign in failed, display a message and update the UI
                                    Log.w(TAG, "signInWithCredential:failure", task.getException());
                                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                                    {
                                    // The verification code entered was invalid
                                    }
                                }
                        }
                    });



        }

        /**
        public static void signUp(Context context, String email, String password)
        {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                firebaseUser = mAuth.getCurrentUser();

                                firebaseUser
                                        .sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task)
                                            {
                                                Log.d(TAG, "Email sent.");
                                                Toast.makeText(context, "Email sent. Please verify your email",
                                                        Toast.LENGTH_SHORT).show();

                                                // enrollUser(context, email);
                                            }
                                        });

                            }
                            else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(context, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }


        public static void googleLogin(Context context, String idToken)
        {

            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if (task.isSuccessful())
                            {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                firebaseUser = mAuth.getCurrentUser();

                                context.startActivity(new Intent(context, LoginSplashActivity.class));
                            }
                            else
                            {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                            }
                        }
                    });



        }**/

        public static void signUpOrNot(Context context, String email)
        {
            db.collection("/INFO/User/UserList")
                    .document(email)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {

                            if (task.isSuccessful())
                            {
                                if (task.getResult().exists())
                                {
                                    Toast.makeText(context,"email", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, MainActivity.class));
                                }
                                else
                                {
                                    Toast.makeText(context,"no email", Toast.LENGTH_SHORT).show();
                                    context.startActivity(new Intent(context, InitialUserInfoActivity.class));
                                }

                            }
                            else
                            {
                                Log.d(TAG,"Error_signUpOrNot");
                            }
                        }
                    });
        }

        public static void checkNickname(Context context, String nickname, final FireStoreCallback callback)
        {
            DocumentReference documentReference = db.collection("/INFO/User/NicknameList").document(nickname);

            documentReference
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task)
                        {

                            if (task.isSuccessful())
                            {
                                if (task.getResult().exists())
                                {
                                    Toast.makeText(context,"이미 존재하는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                    callback.callback(true);
                                }
                                else
                                {
                                    Toast.makeText(context,"사용 가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                    callback.callback(false);
                                }

                            }
                            else
                            {
                                Log.d(TAG,"Error_checkNickname");
                            }
                        }
                    });
        }
    }

}
