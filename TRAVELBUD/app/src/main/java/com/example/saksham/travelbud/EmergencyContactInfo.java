package com.example.saksham.travelbud;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class EmergencyContactInfo extends AppCompatActivity {

    private EditText ed1,ed2,ed3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_contact_info);

        ed1 = (EditText) findViewById(R.id.ed91);
        ed2 = (EditText) findViewById(R.id.ed92);
        ed3 = (EditText) findViewById(R.id.ed93);

        int flag=0;
        SQLiteDatabase mydatabase = openOrCreateDatabase("database11",MODE_PRIVATE,null);
        Cursor cursor1 = mydatabase.rawQuery("SELECT * FROM Emergency_Numbers",null);
        cursor1.moveToFirst();
        do {

            if(flag==0)
            {
                ed1.setText(cursor1.getString(0));
            }

            if(flag==1)
            {
                ed2.setText(cursor1.getString(0));
            }

            if(flag==2)
            {
                ed3.setText(cursor1.getString(0));
            }
            flag++;
        }while(cursor1.moveToNext());
    }

    public void UpdateContacts(View view)
    {
        ed1 = (EditText) findViewById(R.id.ed91);
        ed2 = (EditText) findViewById(R.id.ed92);
        ed3 = (EditText) findViewById(R.id.ed93);
        String val1 = ed1.getText().toString();
        String val2 = ed2.getText().toString();
        String val3 = ed3.getText().toString();

        SQLiteDatabase mydatabase = openOrCreateDatabase("database11",MODE_PRIVATE,null);
        mydatabase.execSQL("DELETE FROM Emergency_numbers;");

        if(!TextUtils.isEmpty(val1))
        {
            mydatabase.execSQL("INSERT INTO Emergency_numbers VALUES(" + val1 + ");");
        }
        if(!TextUtils.isEmpty(val2))
        {
            mydatabase.execSQL("INSERT INTO Emergency_numbers VALUES(" + val2 + ");");
        }
        if(!TextUtils.isEmpty(val3))
        {
            mydatabase.execSQL("INSERT INTO Emergency_numbers VALUES(" + val3 + ");");

        }


    }
}
