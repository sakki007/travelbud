package com.example.saksham.travelbud;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.Map;

public class CheckInsOwn extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser mUser;
    private ListView lv;
    private ArrayList<String> wordList;
    private ArrayList<String> al1;
    private String haanValue;
    private ArrayList<String> long1;
    private ArrayList<String> lat1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_ins_own);

        mFirebaseInstance = FirebaseDatabase.getInstance();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        Intent intentfinal3 = getIntent();
        final String travelId1 = intentfinal3.getStringExtra("travelId");



        lv = (ListView) findViewById(R.id.lv69);
        wordList = new ArrayList<>();
        long1 = new ArrayList<>();
        lat1 = new ArrayList<>();

        al1 = new ArrayList<>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al1);

        lv.setAdapter(arrayAdapter);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId = mUser.getUid();

        DatabaseReference child1 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(travelId1).child("CheckInList");
        child1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String value111 = dataSnapshot.getValue(String.class);
                wordList.add(value111);
                DatabaseReference child2 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(travelId1).child("CheckIns").child(value111).child("time");

                haanValue="";
                child2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String val = dataSnapshot.getValue().toString();
                        haanValue += val;
                        haanValue += "\n";
//                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                DatabaseReference child3 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(travelId1).child("CheckIns").child(value111).child("Latitude");

                haanValue="";
                child3.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String val = dataSnapshot.getValue().toString();
                        haanValue += val;
                        haanValue += "\n";
                        lat1.add(val);
//                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


                DatabaseReference child4 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(travelId1).child("CheckIns").child(value111).child("Longitude");

                haanValue="";
                child4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String val = dataSnapshot.getValue().toString();
                        haanValue += val;
                        long1.add(val);
                        haanValue += "\n";
                        al1.add(haanValue);
                        haanValue = "";
                        arrayAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


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

        final Intent intent67 = new Intent(this, MapsActivity.class);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int position1 = position;
                String long2 = long1.get(position1);
                String lat2 = lat1.get(position1);
                intent67.putExtra("longit1",long2);
                intent67.putExtra("latit1",lat2);
                startActivity(intent67);

            }
        });

    }
}
