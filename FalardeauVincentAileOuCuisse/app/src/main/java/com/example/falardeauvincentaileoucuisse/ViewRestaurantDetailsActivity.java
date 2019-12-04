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
    private TextView mOnestar;
    private TextView mTwoStar;
    private TextView mTreeStar;
    private TextView mFourStar;
    private TextView mFiveStar;

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
        mOnestar = findViewById(R.id.onestar);
        mTwoStar = findViewById(R.id.twostar);
        mTreeStar = findViewById(R.id.treestar);
        mFourStar = findViewById(R.id.fourstar);
        mFiveStar = findViewById(R.id.fivestar);

        Intent intent = getIntent();

        String name = intent.getStringExtra("restaurant");
        mName.setText(name);

        String address = intent.getStringExtra("address");
        mAddress.setText(address);

        String mealQuality = intent.getStringExtra("mealQuality");
        mMealQuality.setText(mealQuality);


        String serviceQuality = intent.getStringExtra("serviceQuality");
        mServiceQuality.setText(serviceQuality);

        float rating = intent.getIntExtra("rating", 0);
        mGeneralRating.setRating(rating);
        mGeneralRating.setIsIndicator(true);

        String price = intent.getStringExtra("price");
        mAveragePrice.setText(price);

        String voteCount = intent.getStringExtra("voteCount");
        mVoteCount.setText(voteCount);




        String star = intent.getStringExtra("onestar");
        mOnestar.setText("1 étoile:   " + star);

        star = intent.getStringExtra("twostar");
        mTwoStar.setText("2 étoile:  " +star);

        star = intent.getStringExtra("treestar");
        mTreeStar.setText("3 étoile:  " +star);

        star = intent.getStringExtra("fourstar");
        mFourStar.setText("4 étoile:  " +star);

        star = intent.getStringExtra("fivestar");
        mFiveStar.setText("5 étoiles:  " +star);
    }

    public void leave(View view) {
        this.finish();
    }
}
