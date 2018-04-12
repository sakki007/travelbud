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

import java.util.ArrayList;
import java.util.List;

public class SearchPeopleTravelsDetails extends AppCompatActivity {

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
        setContentView(R.layout.activity_search_people_travels_details);

        lvfinal = (ListView)findViewById(R.id.lv198);

        Intent intenthehe = getIntent();
        String receivedValue = intenthehe.getStringExtra("lelevaluephirse");
        String receivedValue2 = intenthehe.getStringExtra("lelevaluephirse2");

        al2 = new ArrayList<>();
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al2);

        lvfinal.setAdapter(arrayAdapter);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseInstance = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");

        userId = receivedValue2;

        DatabaseReference child1 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(receivedValue).child("wordings");
        al2.add("hello");
   //     al2.add(receivedValue);
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

    public void searchCheckIns(View view)
    {
        Intent intenthehe = getIntent();
        String travelId = intenthehe.getStringExtra("lelevaluephirse");
        String userId = intenthehe.getStringExtra("lelevaluephirse2");

        Intent intent87 = new Intent(this,SearchPeopleCheckIns.class);
        intent87.putExtra("val1",userId);
        intent87.putExtra("val2",travelId);
        startActivity(intent87);

    }
}
