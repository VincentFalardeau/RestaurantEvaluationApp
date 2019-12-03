package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
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
    private EditText mAveragePriceDecimals;

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
        mAveragePriceDecimals = findViewById(R.id.avg_price_decimals);

        for(int i = 1; i < mMealQuality.getChildCount(); i++)
        {
            RadioButton rb = (RadioButton)mMealQuality.getChildAt(i);
            String ratingInWord = Restaurant.RATING_IN_WORDS.get(i - 1);
            rb.setText(ratingInWord);
        }

        for(int i = 1; i < mServiceQuality.getChildCount(); i++)
        {
            RadioButton rb = (RadioButton)mServiceQuality.getChildAt(i);
            String ratingInWord = Restaurant.RATING_IN_WORDS.get(i - 1);
            rb.setText(ratingInWord);
        }

    }



    public void leave(View view) {
        finish();
    }

    public void add(View view) {
        String name = mName.getText().toString();
        String address = mAddress.getText().toString();

        String mealQuality = "";
        for(int i = 1; i < mMealQuality.getChildCount(); i++)
        {
            RadioButton rb = (RadioButton)mMealQuality.getChildAt(i);
            if(rb.isChecked()){
                mealQuality = rb.getText().toString();
                break;
            }
        }

        String serviceQuality = "";
        for(int i = 1; i < mServiceQuality.getChildCount(); i++)
        {
            RadioButton rb = (RadioButton)mServiceQuality.getChildAt(i);
            if(rb.isChecked()){
                serviceQuality = rb.getText().toString();
                break;
            }
        }

        int generalRating = (int)mGeneralRating.getRating();

        String averagePrice = mAveragePrice.getText().toString();
        String decimals = mAveragePriceDecimals.getText().toString();
        if(!averagePrice.equals("") && !decimals.equals("")){
            averagePrice = averagePrice + "." + decimals;
        }
        else if(averagePrice.equals("") && !decimals.equals("")){
            averagePrice = "0." + decimals;
        }

        String error = validateData(name, address, mealQuality, serviceQuality, generalRating, averagePrice);

        if(error != null){
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
        else{
            //String mealQualityStr = Restaurant.RATING_IN_WORDS.get(mealQuality - 1);
            //String serviceQualityStr = Restaurant.RATING_IN_WORDS.get(serviceQuality - 1);

            //SQLiteDatabase db = openOrCreateDatabase(MainActivity.SQLITE_DB_NAME, Context.MODE_PRIVATE,null);
            try{
                MainActivity.getDB().execSQL("insert into Restaurants values(" +
                        "null, '" +
                        name + "', '" +
                        address + "', '" +
                        mealQuality + "', '" +
                        serviceQuality + "', " +
                        Float.parseFloat(averagePrice) + ", " +
                        generalRating + ");");
            }
            catch (Exception e){
                e.printStackTrace();
            }


            Intent intent = new Intent();
            setResult(MainActivity.ACTIVITY_RESULT_UPDATE_UI,intent);

            finish();
        }
    }

    private String validateData(String name, String address, String mealQuality, String serviceQuality, int generalRating, String averagePrice){
        if(name.length() <= 0){
            return "Veuillez entrer un nom";
        }
        if(address.length() <= 0){
            return "Veuillez entrer une adresse";
        }
        if(mealQuality.equals("")){
            return "Indiquez la qualité des plats";
        }
        if(serviceQuality.equals("")){
            return "Indiquez la qualité du service";
        }
        //Peu probable a cause de l'interface
        if(generalRating < 1 || generalRating > 5){
            return "Donnez une évaluation générale";
        }
        if(averagePrice.length() <= 0){
            return "Veuillez entrer un prix moyen";
        }
        try{
            float averagePriceFloat = Float.parseFloat(averagePrice);
            if(averagePriceFloat <= 0.0f){
                return "Le prix moyen ne peut pas être égal à 0 ou négatif";
            }
        }catch (NumberFormatException nfe){
            return "Le prix moyen n'est pas un nombre valide";
        }
        return null;
    }


}
