package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    public static final int ACTIVITY_RESULT_UPDATE_UI = 1;

    public static final String SQLITE_DB_NAME = "dbRestaurants";

    public static boolean usingLocalData = true;

    private static SQLiteDatabase mDB;
    private ArrayList<Restaurant> mRestaurants;
    private int[] mRestaurantsIndex;
    private Restaurant mCurrentRestaurant;

    private ListView mList;
    private TextView mDetails;
    private RatingBar mRating;

    private ResultSet mResultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.restaurant_list);
        mDetails = (TextView) findViewById(R.id.details);
        mRating = (RatingBar) findViewById(R.id.rating);

        usingLocalData = true;;
        mDB = openOrCreateDatabase(SQLITE_DB_NAME, Context.MODE_PRIVATE,null);
        mDB.execSQL("create table if not exists Restaurants(" +
                "idrestaurant integer primary key autoincrement," +
                "nomRestaurant varchar," +
                "adresseRestaurant varchar," +
                "qualiteBouffe varchar," +
                "qualiteService varchar," +
                "prixMoyen real," +
                "nbEtoiles integer);");

        mDB.execSQL("create table if not exists RestaurantsD(" +
                "idrestaurant integer primary key autoincrement," +
                "nomRestaurant varchar," +
                "adresseRestaurant varchar," +
                "qualiteBouffe varchar," +
                "qualiteService varchar," +
                "prixMoyen real," +
                "nbEtoiles integer);");

        updateData();

        mList.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick (AdapterView<?> adapter, View vue, int position, long id) {
        mCurrentRestaurant = mRestaurants.get(mRestaurantsIndex[position]);
        mDetails.setText(mCurrentRestaurant.toString());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ACTIVITY_RESULT_UPDATE_UI)
        {
            updateData();
        }
    }

    public void filterRestaurants(View view) {
        if(mRestaurants != null)
        {
            int restaurantCount = mRestaurants.size();
            int currentRestaurantIndex = 0;
            int rating = (int)mRating.getRating();
            ArrayList<String> filteredRestaurantNameList = new ArrayList<>();
            mRestaurantsIndex = new int[restaurantCount];

            for(int i = 0; i < restaurantCount; i++){
                if(mRestaurants.get(i).getGeneralRating() >= rating){
                    filteredRestaurantNameList.add(mRestaurants.get(i).getName());
                    mRestaurantsIndex[currentRestaurantIndex] = i;
                    currentRestaurantIndex++;
                }
            }

            ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this, R.layout.restaurant, filteredRestaurantNameList);
            mList.setAdapter(adaptor);

            mCurrentRestaurant = null;
            mDetails.setText("");
        }

    }

    public void startAddRestaurantActivity(View view) {
        if(usingLocalData){
            Intent intent = new Intent(this, AddRestaurantActivity.class);
            startActivityForResult(intent, 1);
        }
        else{
            Toast.makeText(getApplicationContext(), "Impossible en mode Données distantes", Toast.LENGTH_SHORT).show();
        }

    }


    public void startEditRestaurantActivity(View view) {
        if(usingLocalData){
            if(mCurrentRestaurant != null){
                Intent intent = new Intent(this, EditRestaurantActivity.class);
                intent.putExtra("id", mCurrentRestaurant.getId());
                intent.putExtra("restaurant", mCurrentRestaurant.getName());
                intent.putExtra("address", mCurrentRestaurant.getAddress());
                intent.putExtra("mealQuality", mCurrentRestaurant.getMealQuality());
                intent.putExtra("serviceQuality", mCurrentRestaurant.getServiceQuality());
                intent.putExtra("rating", mCurrentRestaurant.getGeneralRating());
                intent.putExtra("price", Float.toString(mCurrentRestaurant.getAveragePrice()));

                startActivityForResult(intent, 1);
            }
            else{
                Toast.makeText(getApplicationContext(), "Sélectionnez un restaurant", Toast.LENGTH_SHORT).show();

            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Impossible en mode Données distantes", Toast.LENGTH_SHORT).show();
        }
    }

    public void startDeleteRestaurantActivity(View view) {

        if(usingLocalData){
            if(mCurrentRestaurant != null){
                Intent intent = new Intent(this, DeleteRestaurantActivity.class);
                intent.putExtra("restaurant", mCurrentRestaurant.getName());
                intent.putExtra("id", mCurrentRestaurant.getId());
                startActivityForResult(intent, 1);
            }
            else{
                Toast.makeText(getApplicationContext(), "Sélectionnez un restaurant", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Impossible en mode Données distantes", Toast.LENGTH_SHORT).show();
        }

    }

    public void emptyLocalDataBase(View view) {
        if(usingLocalData){
            mDB.execSQL("delete from Restaurants");
            updateData();

            Toast.makeText(getApplicationContext(), "La bd locale a été vidée avec succès", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Impossible en mode Données distantes", Toast.LENGTH_SHORT).show();
        }

    }

    public void transferLocalDataBaseToCentralDataBase(View view) {
        if(usingLocalData){
            transferDataBase();
            Toast.makeText(getApplicationContext(), "La bd locale a été transférée à la bd centrale avec succès", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Impossible en mode Données distantes", Toast.LENGTH_SHORT).show();
        }

    }

    private void transferDataBase(){
        new Thread( new Runnable() {
            public void run() {

                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                String user = "FALARDEA";
                String pwd = "oracle1";
                String url = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection(url,user,pwd);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                Cursor c = mDB.rawQuery("select * from Restaurants;", null);

                while(c.moveToNext()){
                    String sql = "insert into Restaurants values(?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = null;
                    try {
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, c.getInt(0));
                        preparedStatement.setString(2, c.getString(1));
                        preparedStatement.setString(3, c.getString(2));
                        preparedStatement.setString(4, c.getString(3));
                        preparedStatement.setString(5, c.getString(4));
                        preparedStatement.setFloat(6, c.getFloat(5));
                        preparedStatement.setInt(7, c.getInt(6));
                        preparedStatement.executeUpdate();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                c.close();
            }
        }).start();
    }

    public void changeDataSource(View view) {
        ToggleButton tb = (ToggleButton)view;
        usingLocalData = !tb.isChecked();
        updateData();
    }

    private void updateData() {

        Cursor c = null;
        if (usingLocalData) {
            c = mDB.rawQuery("select * from Restaurants;", null);
            updateRestaurants(c);
            filterRestaurants(null);

        } else {
            DistantData distantData = new DistantData();
            distantData.execute();
        }
    }

    private void updateRestaurants(Cursor c){
        mRestaurants = new ArrayList<Restaurant>();

        while (c.moveToNext()) {
            mRestaurants.add(new Restaurant(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4),
                    c.getFloat(5),
                    c.getInt(6)));
        }
        c.close();
    }

    private void populateDistantTable(){
        while (true){
            try {
                if (!mResultSet.next()) break;
                String name = mResultSet.getString(2);
                if(name.contains("'")){
                    int index = name.indexOf("'") - 1;
                    name = String.format("%s'%s", name.substring(0, index + 1), name.substring(index + 1));
                }
                String address = mResultSet.getString(3);
                String mealQualityStr = mResultSet.getString(4);
                String serviceQualityStr = mResultSet.getString(5);
                Float averagePrice = mResultSet.getFloat(6);
                int generalRating = mResultSet.getInt(7);
                mDB.execSQL("insert into RestaurantsD values(" +
                        "null, '" +
                        name + "', '" +
                        address + "', '" +
                        mealQualityStr + "', '" +
                        serviceQualityStr + "', " +
                        averagePrice + ", " +
                        generalRating + ");");

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void retreiveDistantData(){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String user = "FALARDEA";
        String pwd = "oracle1";
        String url = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url,user,pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select * from Restaurants";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            mResultSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private class DistantData extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... args) {
            mResultSet = null;

            retreiveDistantData();

            mDB.execSQL("delete from restaurantsD");

            populateDistantTable();

            Cursor c = mDB.rawQuery("select * from RestaurantsD;", null);
            updateRestaurants(c);
            return null;
        }

        @Override
        protected void onPostExecute(Void resultat) {
            filterRestaurants(null);
        }
    }

    public static SQLiteDatabase getDB(){
        return mDB;
    }
}
