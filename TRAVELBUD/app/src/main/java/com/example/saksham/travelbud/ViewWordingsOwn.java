package com.example.saksham.travelbud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewWordingsOwn extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser mUser;
    private ListView lvfinal;

    private ArrayList<String> al2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_wordings_own);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        lvfinal = (ListView) findViewById(R.id.lvfinal);


        Intent intentfinal = getIntent();
        String receivedValue = intentfinal.getStringExtra("lelevalue");

        al2 = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al2);

        lvfinal.setAdapter(arrayAdapter);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId = mUser.getUid();

        DatabaseReference child1 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(receivedValue).child("wordings");
//        al2.add("hello");
  //      al2.add(receivedValue);
        child1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value111 = dataSnapshot.getValue(String.class);
                al2.add(value111);
                arrayAdapter.notifyDataSetChanged();

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


    }

    public void goToCheckIns(View view)
    {
        Intent intentfinal1 = getIntent();
        String receivedValue = intentfinal1.getStringExtra("lelevalue");

        Intent intentfinal2 = new Intent(this, CheckInsOwn.class);
        intentfinal2.putExtra("travelId",receivedValue);
        startActivity(intentfinal2);


    }
}
