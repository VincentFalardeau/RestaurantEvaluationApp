package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private boolean firstUse = true;
    private Restaurant[] restaurants;
    private int[] restaurantsIndex;

    //Pour developpement
    Restaurant r1;
    Restaurant r2;
    Restaurant r3;


    private Spinner mList;
    private TextView mDetails;
    private RatingBar mRating;

    @Override
    public void onItemSelected (AdapterView<?> adapter, View vue, int position, long id) {

        if (!firstUse) {
            //String name = adapter.getItemAtPosition(position).toString();
            mDetails.setText(restaurants[restaurantsIndex[position]].toString());
            //Toast.makeText(this, "Vous avez choisi " + name, Toast.LENGTH_LONG).show();
        } else {
            firstUse = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adaptateur) {
        Toast.makeText(this, "Aucune sélection", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (Spinner) findViewById(R.id.restaurant_list);
        mDetails = (TextView)findViewById(R.id.details);
        mRating = (RatingBar)findViewById(R.id.rating);

        r1 = new Restaurant("St-Hubert", "10 rue duquet", 3, 4, 3, 25.00f);
        r2 = new Restaurant("Pizza 900", "21 boul. du faubourg", 2, 3, 4, 30.00f);
        r3 = new Restaurant("Le petit poucet", "55 chemin campagne", 5, 5, 5, 20.00f);
        restaurants = new Restaurant[]{r1, r2, r3};
        restaurantsIndex = new int[restaurants.length];
        //restaurantsName = new String[]{r1.getName(), r2.getName(), r2.getName()};
        //mDetails.setText(restaurants[0].toString());

        // construction d'un adaptateur qui fait un lien entre le tableau de
        // chaînes et le spinner
        //ArrayAdapter<String> adaptateur = new ArrayAdapter<String>(this, R.layout.restaurant, R.id.restaurant, restaurantsName);
        filterRestaurants(null);

        // on applique l'adaptateur au spinner
        ///mList.setAdapter(adaptateur);

        // enregistrement de l'activité comme écouteur
        mList.setOnItemSelectedListener(this);
    }

    public void startAddRestaurantActivity(View view) {
        Intent intent = new Intent(this, AddRestaurantActivity.class);
        startActivity(intent);
    }

    public void emptyLocalDataBase(View view) {
        Toast.makeText(getApplicationContext(), "La bd locale a été vidée avec succès", Toast.LENGTH_LONG).show();
    }

    public void transferLocalDataBaseToCentralDataBase(View view) {
        Toast.makeText(getApplicationContext(), "La bd locale a été transférée à la bd centrale avec succès", Toast.LENGTH_LONG).show();
    }

    public void filterRestaurants(View view) {
        ArrayList<String> currentRestaurants = new ArrayList<>();
        restaurantsIndex = new int[restaurants.length];
        int rIndex = 0;
        int rating = (int)mRating.getRating();
        for(int i = 0; i < restaurants.length; i++){
            if(restaurants[i].getGeneralRating() >= rating){
                currentRestaurants.add(restaurants[i].getName());
                restaurantsIndex[rIndex] = i;
                rIndex++;
            }
        }
        ArrayAdapter<String> adaptateur = new ArrayAdapter<String>(this, R.layout.restaurant, R.id.restaurant, currentRestaurants);
        mList.setAdapter(adaptateur);
    }
}
