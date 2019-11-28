package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
        int mealRating = Restaurant.RATING_IN_WORDS.indexOf(mealQuality) + 1;
        mMealQuality.check(mealRating);
        String serviceQuality = intent.getStringExtra("serviceQuality");
        int serviceRating = Restaurant.RATING_IN_WORDS.indexOf(serviceQuality) + 6;
        mServiceQuality.check(serviceRating);
        int rating = intent.getIntExtra("rating", 0);
        mGeneralRating.setRating(rating);
        String price = intent.getStringExtra("price");
        mAveragePrice.setText(price);
    }

    public void edit(View view) {

        int mealQuality = mMealQuality.getCheckedRadioButtonId();
        int serviceQuality = mServiceQuality.getCheckedRadioButtonId();
        int rating = (int)mGeneralRating.getRating();

        String error = validateData(mealQuality, serviceQuality, rating);
        if(error != null){
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
        }
        else {
            String mealQualityStr = Restaurant.RATING_IN_WORDS.get(mealQuality % 5 - 1);
            String serviceQualityStr = Restaurant.RATING_IN_WORDS.get(serviceQuality % 5 - 1);

            SQLiteDatabase db = openOrCreateDatabase("dbRestaurants", Context.MODE_PRIVATE,null);
            db.execSQL("update Restaurants set qualiteBouffe ='" + mealQualityStr + "', qualiteService = '" + serviceQualityStr + "', nbEtoiles = " + rating + "  where idRestaurant = " + mId);

            Intent intent = new Intent();
            setResult(1,intent);

            this.finish();
        }
    }

    private String validateData(int mealQuality, int serviceQuality, int generalRating){
        if(mealQuality == -1){
            return "Indiquez la qualité des plats";
        }
        if(serviceQuality == -1){
            return "Indiquez la qualité du service";
        }
        //Peu probable a cause de l'interface
        if(generalRating < 1 || generalRating > 5){
            return "Donnez une évaluation générale";
        }
        return null;
    }

    public void leave(View view) {
        this.finish();
    }
}
