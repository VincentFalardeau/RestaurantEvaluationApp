package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewRestaurantDetailsActivity extends AppCompatActivity {

    private TextView mName;
    private TextView mAddress;
    private TextView mMealQuality;
    private TextView mServiceQuality;
    private RatingBar mGeneralRating;
    private TextView mAveragePrice;
    private TextView mVoteCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_restaurant_details);

        mName = findViewById(R.id.name);
        mAddress = findViewById(R.id.address);
        mMealQuality = findViewById(R.id.meal_quality);
        mServiceQuality = findViewById(R.id.service_quality);
        mGeneralRating = findViewById(R.id.general_rating);
        mAveragePrice = findViewById(R.id.average_price);
        mVoteCount = findViewById(R.id.vote_count);

        Intent intent = getIntent();

        String name = intent.getStringExtra("restaurant");
        mName.setText(name);

        String address = intent.getStringExtra("address");
        mAddress.setText(address);

        String mealQuality = intent.getStringExtra("mealQuality");
        mMealQuality.setText(mealQuality);


        String serviceQuality = intent.getStringExtra("serviceQuality");
        mServiceQuality.setText(serviceQuality);

        int rating = intent.getIntExtra("rating", 0);
        mGeneralRating.setRating(rating);
        mGeneralRating.setIsIndicator(true);

        String price = intent.getStringExtra("price");
        mAveragePrice.setText(price);

        String voteCount = intent.getStringExtra("voteCount");
        mVoteCount.setText(voteCount);
    }

    public void leave(View view) {
        this.finish();
    }
}
