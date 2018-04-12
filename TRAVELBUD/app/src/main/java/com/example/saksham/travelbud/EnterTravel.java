package com.example.saksham.travelbud;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.UUID;

public class EnterTravel extends AppCompatActivity implements SensorEventListener {

    TextView tv, tv1, mLocationTextView,tv333,tv444,tv555;
    private Sensor mSensorProximity;
    private Sensor mSensorLight;
    private SensorManager mSensorManager;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private FloatingActionButton button;
    private EditText editText;

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;
    private FirebaseAuth mAuth;
    private String userId;
    private FirebaseUser mUser;

    private double prev_value = 0.0;
    private long time1=0;
    private long time2 = 0;
    private String longitude1="74.79550961769719";
    private String latitude1="13.34504092924014";




    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_travel);


        mFirebaseInstance = FirebaseDatabase.getInstance();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationTextView = (TextView) findViewById(R.id.textView411);
        tv444 = (TextView) findViewById(R.id.textView444);
        tv555 = (TextView) findViewById(R.id.textView555);
        tv555.setText("LATITUDE - " + longitude1);
        tv444.setText("LONGITUDE - " + latitude1);


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
               // mLocationTextView.append("\n" + location.getLatitude() + " "  + location.getLongitude());
               // mLocationTextView.append("lol");
                latitude1 = String.valueOf(location.getLatitude());
                longitude1 = String.valueOf(location.getLongitude());
                tv444.setText("LATITUDE - " + latitude1);
                tv555.setText("LONGITUDE - " + longitude1);


            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        configure_button();





        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        tv = (TextView) findViewById(R.id.textView2);
        tv1 = (TextView) findViewById(R.id.textView3);


        mSensorProximity =
                mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        if (mSensorLight == null) {
            tv1.setText("sensor_error2");
        }

        if (mSensorProximity == null) {
            tv.setText("sensor_error");
        }

        if (mSensorProximity != null) {
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (mSensorLight != null) {
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        button = (FloatingActionButton) findViewById(R.id.fl1);



    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getValue(View view)
    {

        SQLiteDatabase mydatabase = openOrCreateDatabase("database11",MODE_PRIVATE,null);
        Cursor cursor1 = mydatabase.rawQuery("SELECT * FROM Emergency_Numbers",null);
        cursor1.moveToFirst();

        do {
            String messageToSend = "Someone you know is in trouble. Contact them. Address in long/lat is " + latitude1 + "  " + longitude1;
            String number = cursor1.getString(0);
            SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);

        }while(cursor1.moveToNext());



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        tv = (TextView) findViewById(R.id.textView2);
        tv1 = (TextView) findViewById(R.id.textView3);
        tv333 = (TextView) findViewById(R.id.textView333);

        switch (sensorType) {
            // Event came from the light sensor.
            case Sensor.TYPE_LIGHT: {
                tv1.setText(String.valueOf(currentValue));
                if(currentValue<10.0)
                {
                    tv333.setText("very low light");
                }
                else if(currentValue<50.0)
                {
                    tv333.setText("low light");
                }
                else if(currentValue<100.0)
                {
                    tv333.setText("moderate illuminance");
                }
                else
                {
                    tv333.setText("OUTDOOR ILLUMINANCE LEVEL");
                }

            }
                break;
            case Sensor.TYPE_PROXIMITY: {
                tv.setText(String.valueOf(currentValue));
                //

                if(currentValue==0.0 && prev_value == 5.0)
                {
                    prev_value = 0.0;
                   // mLocationTextView.append("laoadfljadfsljads");
                }
                else if(currentValue==5.0 && prev_value==0.0)
                {
                    prev_value = 5.0;
                    long time= System.currentTimeMillis();
                    time2 = time;
                    long timesal = time2-time1;
                    if((timesal)<1000)
                    {
                     //   mLocationTextView.append("\n" + String.valueOf(time2) +"aa\n"+ String.valueOf(time1) + "bb\n" + String.valueOf(timesal) + "\n");
                    ////

                        DateFormat df = new SimpleDateFormat("h:mm a");
                        String date = df.format(Calendar.getInstance().getTime());


                        Intent intent91 = getIntent();
                        String travelId = intent91.getStringExtra("travelId");


                        mFirebaseDatabase = mFirebaseInstance.getReference("users");
                        mAuth=FirebaseAuth.getInstance();
                        mUser=mAuth.getCurrentUser();
                        userId = mUser.getUid();
                        DatabaseReference mFirebaseDatabaseChild = mFirebaseDatabase.child(userId);
                        String uuid = UUID.randomUUID().toString();
                        mFirebaseDatabaseChild.child("Experiences").child("travels").child(travelId).child("CheckInList").child(uuid).setValue(uuid);
                        mFirebaseDatabaseChild.child("Experiences").child("travels").child(travelId).child("CheckIns").child(uuid).child("Latitude").setValue(latitude1);
                        mFirebaseDatabaseChild.child("Experiences").child("travels").child(travelId).child("CheckIns").child(uuid).child("Longitude").setValue(longitude1);
                        mFirebaseDatabaseChild.child("Experiences").child("travels").child(travelId).child("CheckIns").child(uuid).child("time").setValue(date);

                        Toast.makeText(this,"CHECK-IN WAS SUCCESSFULLY ADDED", Toast.LENGTH_LONG).show();




                        ////
                    }
                    time1 = time2;
                }






                //
            }
                break;
            default:
                // do nothing
        }


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    void configure_button()
    {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
                     requestPermissions(new String[]{
                             android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION,
                           android.Manifest.permission.INTERNET
                 },10);
            return;
        }

        locationManager.requestLocationUpdates("gps", 2000, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);




    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void addWording(View view)
    {
        configure_button();
        Intent intent91 = getIntent();
        String travelId = intent91.getStringExtra("travelId");

        mFirebaseDatabase = mFirebaseInstance.getReference("users");
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        userId = mUser.getUid();
        DatabaseReference mFirebaseDatabaseChild = mFirebaseDatabase.child(userId);
        String uuid = UUID.randomUUID().toString();
        editText = (EditText) findViewById(R.id.editText);
        mFirebaseDatabaseChild.child("Experiences").child("travels").child(travelId).child("wordings").child(uuid).setValue(editText.getText().toString());
        Toast.makeText(this,"SAVED SUCCESFULLY",Toast.LENGTH_LONG).show();
        editText.setText("");
    }

    public void GoTo(View view)
    {
        Intent newIntent78 = new Intent(this, MapsActivity.class);
        newIntent78.putExtra("longit1",longitude1);
        newIntent78.putExtra("latit1",latitude1);
        startActivity(newIntent78);

    }




}
