package com.example.saksham.travelbud;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class SignUp extends AppCompatActivity {
    private EditText inputEmail, inputPassword;
    private Button btnSignUp;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        auth.signOut();
        //btnSignIn = (Button) findViewById(R.id.loginButtonEe);
        btnSignUp = (Button) findViewById(R.id.signupButtonEe);
        inputEmail = (EditText) findViewById(R.id.signupEmailEe);
        inputPassword = (EditText) findViewById(R.id.signupPasswordEe);
        // progressBar = (ProgressBar) findViewById(R.id.progressBar);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //       progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(SignUp.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                //progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUp.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    Intent intent2 = getIntent();
                                    String post = intent2.getStringExtra("post");


                                    Intent intent = new Intent(SignUp.this,Dashboard.class);





                                    //

                                    mFirebaseDatabase = mFirebaseInstance.getReference("users");
                                    mAuth=FirebaseAuth.getInstance();
                                    mUser=mAuth.getCurrentUser();
                                    userId = mUser.getUid();
                                    DatabaseReference mFirebaseDatabaseChild = mFirebaseDatabase.child(userId);
                                    mFirebaseDatabaseChild.child("secureNo").setValue("no");

                                    int index = email.indexOf('@');
                                    String email1 = email.substring(0,index);

                                    mFirebaseDatabase.child("extra").child(email1).setValue(userId);




                                    //
                                    //CREATING LOCAL DATABASE
                                    SQLiteDatabase mydatabase = openOrCreateDatabase("database11",MODE_PRIVATE,null);
                                    mydatabase.execSQL("CREATE TABLE IF NOT EXISTS Emergency_numbers(Contact NUMERIC);");



                                    startActivity(intent);

                                    //startActivity(new Intent(SignupActivity.this, UserProfile.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    public void userLogin(View view) {


        Intent intent2 = getIntent();
        String post = intent2.getStringExtra("post");

        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);

    }
}
