package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

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

        Restaurant r1 = new Restaurant("St-Hubert", "10 rue duquet", 3, 4, 3, 25.00f);
        Restaurant r2 = new Restaurant("Pizza 900", "21 boul. du faubourg", 2, 3, 4, 30.00f);
        Restaurant r3 = new Restaurant("Le petit poucet", "55 chemin campagne", 5, 5, 5, 20.00f);
        mRestaurants = new Restaurant[]{r1, r2, r3};

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
    }
}
