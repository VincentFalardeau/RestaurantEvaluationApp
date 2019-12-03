package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteRestaurantActivity extends AppCompatActivity {

    private String mName;
    private int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_restaurant);
        Intent intent = getIntent();
        mName = intent.getStringExtra("restaurant");
        mId = intent.getIntExtra("id", 0);
        TextView message = (TextView)findViewById(R.id.message);
        message.setText("Voulez-vous vraiment supprimer le restaurant nommé " + mName +  "?");
    }

    public void delete(View view) {
        //SQLiteDatabase db = openOrCreateDatabase(MainActivity.SQLITE_DB_NAME, Context.MODE_PRIVATE,null);
        //if(MainActivity.usingLocalData){
            MainActivity.getDB().execSQL("delete from Restaurants where idRestaurant = " + mId);
            Intent intent = new Intent();
            setResult(MainActivity.ACTIVITY_RESULT_UPDATE_UI,intent);

            leave(null);
        //}
        //else{
            //DistantData dd = new DistantData();
            //dd.execute();
        //}




    }

//    private class DistantData extends AsyncTask<Void, Integer, Void> {
//
//        @Override
//        protected Void doInBackground(Void... args) {
//            try {
//                Class.forName("oracle.jdbc.driver.OracleDriver");
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            String user = "FALARDEA";
//            String pwd = "oracle1";
//            String url = "jdbc:oracle:thin:@mercure.clg.qc.ca:1521:orcl";
//            Connection connection = null;
//            try {
//                connection = DriverManager.getConnection(url,user,pwd);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//            String sql = "delete from restaurants where idRestaurant =";
//            try {
//                PreparedStatement ps = connection.prepareStatement(sql);
//                ps.setInt(1, mId);
//                ps.executeUpdate();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//            MainActivity.getDB().execSQL("delete from restaurantsD where id = " + mId);
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void resultat){
//            Intent intent = new Intent();
//            setResult(MainActivity.ACTIVITY_RESULT_UPDATE_UI,intent);
//
//            leave(null);
//        }
   // }


    public void leave(View view) {
        this.finish();
    }
}
