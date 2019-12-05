package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class EditRestaurantActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mAddress;
    private RadioGroup mMealQuality;
    private RadioGroup mServiceQuality;
    private RatingBar mGeneralRating;
    private TextView mAveragePrice;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_restaurant);

        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mMealQuality = findViewById(R.id.meal_quality);
        mServiceQuality = findViewById(R.id.service_quality);
        mGeneralRating = findViewById(R.id.general_rating);
        mAveragePrice = findViewById(R.id.average_price);

        Intent intent = getIntent();

        mId = intent.getIntExtra("id", 0);

        String name = intent.getStringExtra("restaurant");
        mName.setText(name);

        String address = intent.getStringExtra("address");
        mAddress.setText(address);

        String mealQuality = intent.getStringExtra("mealQuality");
        for(int i = 1; i < mMealQuality.getChildCount(); i++)
        {
            RadioButton rb = (RadioButton)mMealQuality.getChildAt(i);
            String ratingInWord = Restaurant.RATING_IN_WORDS.get(i - 1);
            rb.setText(ratingInWord);
            if(rb.getText().equals(mealQuality)){
                rb.setChecked(true);
            }
        }

        String serviceQuality = intent.getStringExtra("serviceQuality");
        for(int i = 1; i < mServiceQuality.getChildCount(); i++)
        {
            RadioButton rb = (RadioButton)mServiceQuality.getChildAt(i);
            String ratingInWord = Restaurant.RATING_IN_WORDS.get(i - 1);
            rb.setText(ratingInWord);
            if(rb.getText().equals(serviceQuality)){
                rb.setChecked(true);
            }
        }

        float rating = intent.getFloatExtra("rating", 0f);
        mGeneralRating.setRating(rating);

        String price = intent.getStringExtra("price");
        mAveragePrice.setText(price);
    }

    public void edit(View view) {
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

        int rating = (int)mGeneralRating.getRating();

        String error = validateData(mealQuality, serviceQuality, rating);
        if(error != null){
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
        else {
            MainActivity.getDB().execSQL("update Restaurants set qualiteBouffe ='" + mealQuality + "', qualiteService = '" + serviceQuality + "', nbEtoiles = " + rating + "  where idRestaurant = " + mId);

            Intent intent = new Intent();
            setResult(MainActivity.ACTIVITY_RESULT_UPDATE_UI,intent);

            this.finish();
        }
    }

    private String validateData(String mealQuality, String serviceQuality, int generalRating){
        if(mealQuality.equals("")){
            return "Indiquez la qualité des plats";
        }
        if(serviceQuality.equals("")){
            return "Indiquez la qualité du service";
        }
        if(generalRating < 1 || generalRating > 5){
            return "Donnez une évaluation générale";
        }
        return null;
    }

    public void leave(View view) {
        this.finish();
    }
}
