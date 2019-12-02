package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.SQLITE_DB_NAME, Context.MODE_PRIVATE,null);
        db.execSQL("delete from Restaurants where idRestaurant = " + mId);


        Intent intent = new Intent();
        setResult(MainActivity.UPDATE_DB_ACTIVITY_RESULT,intent);

        this.finish();
    }

    public void leave(View view) {
        this.finish();
    }
}
