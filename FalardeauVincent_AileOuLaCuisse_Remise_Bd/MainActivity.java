package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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

    //Si une activité termine avec ce code, on met à jour l'interface avec les données (qui ont probablement changées)
    public static final int ACTIVITY_RESULT_UPDATE_UI = 1;

    public static final String SQLITE_DB_NAME = "dbRestaurants";

    public static boolean usingLocalData = true;

    private static SQLiteDatabase mDB;
    private ArrayList<Restaurant> mRestaurants;
    private int[] mRestaurantsIndex;
    private Restaurant mCurrentRestaurant;

    private ListView mList;
    private LinearLayout mListLayout;
    private TextView mDetails;
    private LinearLayout mDetailsLayout;

    private RatingBar mRating;

    private ResultSet mResultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ListView) findViewById(R.id.restaurant_list);
        mListLayout = (LinearLayout)findViewById(R.id.list_layout);
        mDetails = (TextView) findViewById(R.id.details);
        mDetailsLayout = (LinearLayout)findViewById(R.id.details_layout);
        mRating = (RatingBar) findViewById(R.id.rating);

        usingLocalData = true;

        //Création de la table dans sqlite
        mDB = openOrCreateDatabase(SQLITE_DB_NAME, Context.MODE_PRIVATE,null);
        mDB.execSQL("create table if not exists Restaurants(" +
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

        //assignation du nouveau restaurant courant
        mCurrentRestaurant = mRestaurants.get(mRestaurantsIndex[position]);

        if(usingLocalData){
            //Affichage du resto courant en mode local
            mDetails.setText(mCurrentRestaurant.toString());
        }
        else{
            //On démarre une activité pour voir le restaurant de façon plus détaillée en mode distant
            Intent intent = new Intent(this, ViewRestaurantDetailsActivity.class);
            intent.putExtra("restaurant", mCurrentRestaurant.getName());
            intent.putExtra("address", mCurrentRestaurant.getAddress());

            intent.putExtra("mealQuality", mCurrentRestaurant.getMealQuality());
            intent.putExtra("serviceQuality", mCurrentRestaurant.getServiceQuality());
            intent.putExtra("rating",mCurrentRestaurant.getGeneralRating());
            intent.putExtra("price", String.format ("%,.2f", mCurrentRestaurant.getAveragePrice()) + " $ CAD");
            intent.putExtra("voteCount", Integer.toString(mCurrentRestaurant.getVoteCount()));
            intent.putExtra("onestar", Integer.toString(mCurrentRestaurant.getStars()[0]));
            intent.putExtra("twostar", Integer.toString(mCurrentRestaurant.getStars()[1]));
            intent.putExtra("treestar", Integer.toString(mCurrentRestaurant.getStars()[2]));
            intent.putExtra("fourstar", Integer.toString(mCurrentRestaurant.getStars()[3]));
            intent.putExtra("fivestar", Integer.toString(mCurrentRestaurant.getStars()[4]));

            startActivity(intent);
        }

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

            //Pour chaque restaurant, on ajoute les restaurants qui correspondent au filtre dans une liste de restaurants filtée
            for(int i = 0; i < restaurantCount; i++){
                if(mRestaurants.get(i).getGeneralRating() >= rating){
                    filteredRestaurantNameList.add(mRestaurants.get(i).getName());
                    mRestaurantsIndex[currentRestaurantIndex] = i;
                    currentRestaurantIndex++;
                }
            }

            //Adatper pour la listeview
            ArrayAdapter<String> adaptor = new ArrayAdapter<String>(this, R.layout.restaurant, filteredRestaurantNameList);
            mList.setAdapter(adaptor);

            //Aucun restaurant ne sera sélectionné
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
            //Effecer tout de la table sqlite locale
            mDB.execSQL("delete from Restaurants");
            //mettre à jour les données courantes
            updateData();

            Toast.makeText(getApplicationContext(), "La bd locale a été vidée avec succès", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Impossible en mode Données distantes", Toast.LENGTH_SHORT).show();
        }

    }

    public void transferLocalDataBaseToCentralDataBase(View view) {
        if(usingLocalData){
            //transfer de la bd locale à la bd distante
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
                //Obtention des données locales
                Cursor c = mDB.rawQuery("select * from Restaurants;", null);

                while(c.moveToNext()){
                    //Pour chaque ligne de la table locale, on envoie à oracle avec un insert dans la table restaurants
                    String sql = "insert into Restaurants values(?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = null;
                    try {
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setInt(1, 0);
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
        //Changement du flag qui indique dans quel mode on est
        usingLocalData = !tb.isChecked();
        //Mise à jour des données
        updateData();
    }

    private void updateData() {

        Cursor c = null;
        if (usingLocalData) {
            //Obtention des données locales
            c = mDB.rawQuery("select * from Restaurants;", null);
            //Mise à jour des listes de restaurants courants
            updateRestaurants(c);
            //Filtre selon le filtre qui est mis en ce moment
            filterRestaurants(null);

            //Sert à ajuster la liste selon le mode
            //En mode local, on a un textview qui permet d'afficher des détails d'un resto
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                   ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    30.0f
            );
            mDetailsLayout.setLayoutParams(param);
            param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    40.0f
            );
            mListLayout.setLayoutParams(param);

        } else {
            //Exécution de la tâche qui va chercher des données distantes
            DistantData distantData = new DistantData();
            distantData.execute();

            //Sert à ajuster la liste selon le mode
            //En mode distant, on a seulement une liste de restaurants
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    0.0f
            );
            mDetailsLayout.setLayoutParams(param);
            param = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    70.0f
            );
            mListLayout.setLayoutParams(param);

        }
    }

    private void updateRestaurants(Cursor c){
        mRestaurants = new ArrayList<Restaurant>();
        if(usingLocalData){
            //En local, on initialise l'array de restaurants avec le cursor de la table sqlite locale
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
        else{
            while (true) {
                try {
                    //En distant, on initialise l'array de restaurants avec le resultset de la vue myrestaurants distante
                    if (!mResultSet.next()) break;
                    String name = mResultSet.getString(1);

                    String address = mResultSet.getString(2);
                    int mealQuality = Math.round(mResultSet.getFloat(3)) - 1;
                    String mealQualityStr = Restaurant.RATING_IN_WORDS.get(mealQuality);
                    int serviceQuality = Math.round(mResultSet.getFloat(4)) - 1;
                    String serviceQualityStr = Restaurant.RATING_IN_WORDS.get(serviceQuality);
                    float averagePrice = mResultSet.getFloat(5);
                    float generalRating = mResultSet.getFloat(6);
                    generalRating = (float) Math.round((double) generalRating * 10f) / 10.0f;
                    int nbVotes = mResultSet.getInt(7);
                    int[] stars = new int[5];
                    stars[0] = mResultSet.getInt(8);
                    stars[1] = mResultSet.getInt(9);
                    stars[2] = mResultSet.getInt(10);
                    stars[3] = mResultSet.getInt(11);
                    stars[4] = mResultSet.getInt(12);
                    mRestaurants.add(new Restaurant(
                            0,
                            name,
                            address,
                            mealQualityStr,
                            serviceQualityStr,
                            averagePrice,
                            generalRating,
                            nbVotes,
                            stars));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Connection retreiveDistantData(){
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
        String sql = "select * from myRestaurants";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            mResultSet = ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    private class DistantData extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... args) {

            mResultSet = null;

            Connection connection =  retreiveDistantData();

            updateRestaurants(null);

            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
