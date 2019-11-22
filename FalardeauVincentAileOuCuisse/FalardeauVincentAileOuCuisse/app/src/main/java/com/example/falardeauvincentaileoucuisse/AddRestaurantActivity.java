package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class AddRestaurantActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mAddress;
    private RadioGroup mMealQuality;
    private RadioGroup mServiceQuality;
    private RatingBar mGeneralRating;
    private EditText mAveragePrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);

        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mMealQuality = findViewById(R.id.meal_quality);
        mServiceQuality = findViewById(R.id.service_quality);
        mGeneralRating = findViewById(R.id.general_rating);
        mAveragePrice = findViewById(R.id.avg_price);
    }



    public void leave(View view) {
        this.finish();
    }

    public void add(View view) {
        String name = mName.getText().toString();
        String address = mAddress.getText().toString();
        int mealQuality = mMealQuality.getCheckedRadioButtonId();
        int serviceQuality = mServiceQuality.getCheckedRadioButtonId();
        int generalRating = (int)mGeneralRating.getRating();
        String averagePrice = mAveragePrice.getText().toString();
        String error = validateData(name, address, mealQuality, serviceQuality, generalRating, averagePrice);
        if(error != null){
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Ajout du restaurant", Toast.LENGTH_LONG).show();
            //leave(view);
        }
    }

    private String validateData(String name, String address, int mealQuality, int serviceQuality, int generalRating, String averagePrice){
        if(name.length() <= 0){
            return "Veuillez entrer un nom";
        }
        if(address.length() <= 0){
            return "Veuillez entrer une adresse";
        }
        if(mealQuality == -1){
            return "Indiquez la qualité des plats";
        }
        if(serviceQuality == -1){
            return "Indiquez la qualité du service";
        }
        //Peu probable a cause de l'interface
        if(generalRating < 0 || generalRating > 5){
            return "Donnez une évaluation générale";
        }
        if(averagePrice.length() <= 0){
            return "Veuillez entrer un prix moyen";
        }
        try{
            float averagePriceFloat = Float.parseFloat(averagePrice);
            if(averagePriceFloat < 0.0f){
                return "Le prix moyen ne peut pas être négatif";
            }
        }catch (NumberFormatException nfe){
            return "Le prix moyen est de format invalide";
        }
        return null;
    }
}
