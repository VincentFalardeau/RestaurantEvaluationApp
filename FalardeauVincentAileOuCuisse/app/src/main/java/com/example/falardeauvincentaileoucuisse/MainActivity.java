package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private SQLiteDatabase mDB;
    private Restaurant[] mRestaurants;
    private int[] mRestaurantsIndex;
    private Restaurant mCurrentRestaurant;

    private ListView mList;
    private TextView mDetails;
    private RatingBar mRating;

    @Override
    public void onItemClick (AdapterView<?> adapter, View vue, int position, long id) {
        mCurrentRestaurant = mRestaurants[mRestaurantsIndex[position]];
        mDetails.setText(mCurrentRestaurant.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.restaurant_list);
        mDetails = (TextView) findViewById(R.id.details);
        mRating = (RatingBar) findViewById(R.id.rating);

        mDB = openOrCreateDatabase("dbRestaurants", Context.MODE_PRIVATE,null);
        mDB.execSQL("create table if not exists Restaurants(" +
                "idrestaurant integer primary key autoincrement," +
                "nomRestaurant varchar," +
                "adresseRestaurant varchar," +
                "qualiteBouffe varchar," +
                "qualiteService varchar," +
                "prixMoyen varchar," +
                "nbEtoiles varchar);");

        mDB.execSQL("insert into Restaurants values(null, 'St-Hubert', '10 rue duquet', 'moyen', 'bien', '25.00', 'moyen');");

        Cursor c = mDB.rawQuery("select * from Restaurants;", null);

        mRestaurants = new Restaurant[1];

        while(c.moveToNext()){
            mRestaurants[0] = new Restaurant(
                    c.getString(1),
                    c.getString(2),
                    3,
                    4,
                    3,
                   25.00f);
        }
        c.close();

        //Restaurant r1 = new Restaurant("St-Hubert", "10 rue duquet", 3, 4, 3, 25.00f);
        //Restaurant r2 = new Restaurant("Pizza 900", "21 boul. du faubourg", 2, 3, 4, 30.00f);
        //Restaurant r3 = new Restaurant("Le petit poucet", "55 chemin campagne", 5, 5, 5, 20.00f);
        //mRestaurants = new Restaurant[]{r1, r2, r3};

        filterRestaurants(null);

        mList.setOnItemClickListener(this);
    }

    public void startAddRestaurantActivity(View view) {
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        startActivity(intent);
    }

    public void startDeleteRestaurantActivity(View view) {

        if(mCurrentRestaurant != null){
            Intent intent = new Intent(this, DeleteRestaurantActivity.class);
            intent.putExtra("restaurant", mCurrentRestaurant.getName());
            startActivity(intent);
        }
    }

    public void emptyLocalDataBase(View view) {
        Toast.makeText(getApplicationContext(), "La bd locale a été vidée avec succès", Toast.LENGTH_LONG).show();
    }

    public void transferLocalDataBaseToCentralDataBase(View view) {
        Toast.makeText(getApplicationContext(), "La bd locale a été transférée à la bd centrale avec succès", Toast.LENGTH_LONG).show();
    }

    public void filterRestaurants(View view) {
        if(mRestaurants != null)
        {
            int restaurantCount = mRestaurants.length;
            int currentRestaurantIndex = 0;
            int rating = (int)mRating.getRating();
            ArrayList<String> filteredRestaurantNameList = new ArrayList<>();
            mRestaurantsIndex = new int[restaurantCount];

            for(int i = 0; i < restaurantCount; i++){
                if(mRestaurants[i].getGeneralRating() >= rating){
                    filteredRestaurantNameList.add(mRestaurants[i].getName());
                    mRestaurantsIndex[currentRestaurantIndex] = i;
                    currentRestaurantIndex++;
                }
            }

            ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this, R.layout.restaurant, filteredRestaurantNameList);
            mList.setAdapter(adaptor);

            mCurrentRestaurant = null;
            mDetails.setText("");
        }

    }
}
