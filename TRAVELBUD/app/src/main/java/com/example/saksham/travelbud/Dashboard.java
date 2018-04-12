package com.example.saksham.travelbud;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        mFirebaseInstance = FirebaseDatabase.getInstance();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        //
        lv = (ListView) findViewById(R.id.lv);
        wordList = new ArrayList<>();
        al1 = new ArrayList<>();

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al1);

        lv.setAdapter(arrayAdapter);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId = mUser.getUid();

        DatabaseReference child1 = mFirebaseDatabase.child(userId).child("Experiences").child("travelList");

        child1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value111 = dataSnapshot.getValue(String.class);
                wordList.add(value111);
                DatabaseReference child2 = mFirebaseDatabase.child(userId).child("Experiences").child("travels").child(value111).child("time");
                child2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String val = dataSnapshot.getValue().toString();
                        al1.add(val);
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



        final Intent intentnew = new Intent(this, ViewWordingsOwn.class);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                      @Override
                                      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                          int itemPosition = i;
                                          String itemValue = wordList.get(itemPosition);
                                          intentnew.putExtra("lelevalue", itemValue);
                                          startActivity(intentnew);

                                      }
                                  });

            //
        if(arrayAdapter.getCount()==0)
        {
            Toast.makeText(this, "No travel details found", Toast.LENGTH_SHORT).show();
        }
    }


    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


       if (id == R.id.nav_manage) {
            Intent intent32 = new Intent(this, EmergencyContactInfo.class);
            startActivity(intent32);

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void travelpejaa(View view) {

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId = mUser.getUid();
        DatabaseReference mFirebaseDatabaseChild = mFirebaseDatabase.child(userId);
        String uuid = UUID.randomUUID().toString();
        mFirebaseDatabaseChild.child("Experiences").child("travelList").child(uuid).setValue(uuid);

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        mFirebaseDatabaseChild.child("Experiences").child("travels").child(uuid).child("time").setValue(formattedDate);


        Intent intent90 = new Intent(this, EnterTravel.class);
        intent90.putExtra("travelId",uuid);
        startActivity(intent90);
    }

    public void searchpejaa(View view)
    {
        Intent leleintent = new Intent(this, SearchPeople.class);
        startActivity(leleintent);
    }
}
