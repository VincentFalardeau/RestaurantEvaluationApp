package com.example.falardeauvincentaileoucuisse;

public class Restaurant {

    private final String[] mRatingInWord = {"", "Horrible", "Médiocre", "Moyen", "Bien", "Excellent"};

    private String mName;
    private String mAddress;
    private int mMealQuality;
    private int mServiceQuality;
    private int mGeneralRating;
    private float mAveragePrice;

    @Override
    public String toString(){
        String string = "";
        if(isValid()){
            string = "Nom: " + mName + "\n" +
                    "Adresse: " + mAddress + "\n" +
                    "Qualité des plats: " + mRatingInWord[mMealQuality] + "\n" +
                    "Qualité du service: " + mRatingInWord[mServiceQuality] + "\n" +
                    "Évaluation générale: " +  mGeneralRating +  "étoiles: " + "\n" +
                    "Prix moyen d'un plat: " + String.format ("%,.2f", mAveragePrice) + "\n";
        }

        return string;
    }

    public boolean isValid(){
        return mName != "" &&
                mAddress != "" &&
                mMealQuality >= 0 && mMealQuality <= 5 &&
                mServiceQuality >= 0 && mServiceQuality <= 5 &&
                mGeneralRating >= 0 && mGeneralRating <= 5 &&
                mAveragePrice > 0.0f;
    }

    public Restaurant(String name, String address, int mealQuality, int serviceQuality, int generalRating, float averagePrice){
        mName = name;
        mAddress = address;
        mMealQuality = mealQuality;
        mServiceQuality = serviceQuality;
        mGeneralRating = generalRating;
        mAveragePrice = averagePrice;
    }

    public Restaurant(String name, String address, int mealQuality, int serviceQuality, int generalRating){
        mName = name;
        mAddress = address;
        mMealQuality = mealQuality;
        mServiceQuality = serviceQuality;
        mGeneralRating = generalRating;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = mName;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        this.mAddress = mAddress;
    }

    public int getMealQuality() {
        return mMealQuality;
    }

    public void setMealQuality(int mealQuality) {
        this.mMealQuality = mMealQuality;
    }

    public int getServiceQuality() {
        return mServiceQuality;
    }

    public void setServiceQuality(int serviceQuality) {
        this.mServiceQuality = mServiceQuality;
    }

    public int getGeneralRating() {
        return mGeneralRating;
    }

    public void setGeneralRating(int generalRating) {
        this.mGeneralRating = mGeneralRating;
    }

    public float getAveragePrice() {
        return mAveragePrice;
    }

    public void setAveragePrice(float averagePrice) {
        this.mAveragePrice = averagePrice;
    }

    public void setAveragePrice(String generalRating) throws NumberFormatException{

        this.mAveragePrice = Float.parseFloat(generalRating);
    }
}
