package com.example.falardeauvincentaileoucuisse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Restaurant {

    public static final ArrayList<String> RATING_IN_WORDS = new ArrayList<String>(Arrays.asList("horrible", "mediocre", "moyen", "bien", "excellent"));

    private int mId;
    private String mName;
    private String mAddress;
    private String mMealQuality;
    private String mServiceQuality;
    private float mAveragePrice;
    private int mGeneralRating;
    private int mVoteCount;
    private int[] mStars;

    @Override
    public String toString(){
        return "Nom: " + mName + "\n" +
                "Adresse: " + mAddress + "\n" +
                "Qualité des plats: " + mMealQuality + "\n" +
                "Qualité du service: " + mServiceQuality + "\n" +
                "Évaluation générale: " +  mGeneralRating +  " étoiles" + "\n" +
                "Prix moyen d'un plat: " + String.format ("%,.2f", mAveragePrice) + "\n";
    }

    public Restaurant(int id, String name, String address, String mealQuality, String serviceQuality, float averagePrice, int generalRating){
        setId(id);
        setName(name);
        setAddress(address);
        setMealQuality(mealQuality);
        setServiceQuality(serviceQuality);
        setGeneralRating(generalRating);
        setAveragePrice(averagePrice);
    }
    public Restaurant(int id, String name, String address, String mealQuality, String serviceQuality, float averagePrice, int generalRating, int voteCount, int[] stars){
        setId(id);
        setName(name);
        setAddress(address);
        setMealQuality(mealQuality);
        setServiceQuality(serviceQuality);
        setGeneralRating(generalRating);
        setAveragePrice(averagePrice);
        setVoteCount(voteCount);
        setStars(stars);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public String getMealQuality() {
        return mMealQuality;
    }

    public void setMealQuality(String mealQuality) {
        this.mMealQuality = mealQuality;
    }

    public String getServiceQuality() {
        return mServiceQuality;
    }

    public void setServiceQuality(String serviceQuality) {
        this.mServiceQuality = serviceQuality;
    }

    public int getGeneralRating() {
        return mGeneralRating;
    }

    public void setGeneralRating(int generalRating) {
        this.mGeneralRating = generalRating;
    }

    public float getAveragePrice() {
        return mAveragePrice;
    }

    public void setAveragePrice(float averagePrice) {
        this.mAveragePrice = averagePrice;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(int VoteCount) {
        this.mVoteCount = VoteCount;
    }

    public int[] getStars() {
        return mStars;
    }

    public void setStars(int[] stars) {
        this.mStars = stars;
    }
}
