package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRestaurantActivity extends AppCompatActivity {

    private String mName;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_restaurant);
        Intent intent = getIntent();
        mName = intent.getStringExtra("restaurant");
        mId = intent.getIntExtra("id", 0);
        TextView message = (TextView)findViewById(R.id.message);
        message.setText("Voulez-vous vraiment supprimer le restaurant nommé " + mName +  "?");
    }

    public void delete(View view) {
        MainActivity.getDB().execSQL("delete from Restaurants where idRestaurant = " + mId);
            Intent intent = new Intent();
            setResult(MainActivity.ACTIVITY_RESULT_UPDATE_UI,intent);

            leave(null);




    }




    public void leave(View view) {
        this.finish();
    }
}
