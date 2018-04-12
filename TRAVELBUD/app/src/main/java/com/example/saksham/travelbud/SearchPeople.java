package com.example.saksham.travelbud;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchPeople extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser mUser;
    private EditText editText;
    private int flag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_people);



    }

    public void searchkarle(View view)
    {
        mFirebaseInstance = FirebaseDatabase.getInstance();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId = mUser.getUid();

        editText = (EditText) findViewById(R.id.editText222);
        final String searchvalue = editText.getText().toString();

        DatabaseReference child1 = mFirebaseDatabase.child("extra");


        final Intent intent = new Intent(this, SearchPeopleTravels.class);

        child1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String val = dataSnapshot.getKey().toString();
                String val2 = dataSnapshot.getValue().toString();
                    if(searchvalue.equals(val))
                    {
                     intent.putExtra("leValue",val2);
                     startActivity(intent);
                        flag=1;
                    }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


         Toast.makeText(this,"NO SUCH USER FOUND",Toast.LENGTH_LONG).show();

    }
}
