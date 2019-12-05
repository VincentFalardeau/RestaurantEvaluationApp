package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
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
    private ProgressBar mOnestarPb;
    private ProgressBar mTwoStarPb;
    private ProgressBar mTreeStarPb;
    private ProgressBar mFourStarPb;
    private ProgressBar mFiveStarPb;

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

        mOnestarPb = findViewById(R.id.onestarpb);
        mTwoStarPb = findViewById(R.id.twostarpb);
        mTreeStarPb = findViewById(R.id.treestarpb);
        mFourStarPb = findViewById(R.id.fourstarpb);
        mFiveStarPb = findViewById(R.id.fivestarpb);

        Intent intent = getIntent();

        String name = intent.getStringExtra("restaurant");
        mName.setText(name);

        String address = intent.getStringExtra("address");
        mAddress.setText(address);

        String mealQuality = intent.getStringExtra("mealQuality");
        mMealQuality.setText(mealQuality);


        String serviceQuality = intent.getStringExtra("serviceQuality");
        mServiceQuality.setText(serviceQuality);

        float rating = intent.getFloatExtra("rating", 0);
        mGeneralRating.setRating(rating);
        mGeneralRating.setIsIndicator(true);

        String price = intent.getStringExtra("price");
        mAveragePrice.setText(price);

        String voteCount = intent.getStringExtra("voteCount");
        mVoteCount.setText("(" + rating + ") " + voteCount + " votes");





        String starStr = intent.getStringExtra("onestar");
        float star = Float.parseFloat(starStr) / Float.parseFloat(voteCount) * 100;
        mOnestarPb.setProgress( Math.round(star));
        mOnestar.setText("  " + starStr + " votes (" + String.format ("%,.0f", star) + "%)");

        starStr = intent.getStringExtra("twostar");
        star = Float.parseFloat(starStr) / Float.parseFloat(voteCount) * 100;
        mTwoStarPb.setProgress( Math.round(star));
        mTwoStar.setText("  " + starStr + " votes (" + String.format ("%,.0f", star) + "%)");

        starStr = intent.getStringExtra("treestar");
        star = Float.parseFloat(starStr) / Float.parseFloat(voteCount) * 100;
        mTreeStarPb.setProgress( Math.round(star));
        mTreeStar.setText("  " + starStr + " votes (" + String.format ("%,.0f", star) + "%)");

        starStr = intent.getStringExtra("fourstar");
        star = Float.parseFloat(starStr) / Float.parseFloat(voteCount) * 100;
        mFourStarPb.setProgress( Math.round(star));
        mFourStar.setText("  " + starStr + " votes (" + String.format ("%,.0f", star) + "%)");

        starStr = intent.getStringExtra("fivestar");
        star = Float.parseFloat(starStr) / Float.parseFloat(voteCount) * 100;
        mFiveStarPb.setProgress( Math.round(star));
        mFiveStar.setText("  " + starStr + " votes (" + String.format ("%,.0f", star) + "%)");
    }

    public void leave(View view) {
        this.finish();
    }
}
