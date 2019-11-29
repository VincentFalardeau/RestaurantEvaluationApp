package com.example.falardeauvincentaileoucuisse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoadDistantDataActivity extends AppCompatActivity {

    private ResultSet mResultSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_distant_data);

        mResultSet = null;
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
                String sql = "select * from Restaurants";
                try {
                    PreparedStatement ps = connection.prepareStatement(sql);
                    mResultSet = ps.executeQuery();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        while(mResultSet == null){
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        SQLiteDatabase db = openOrCreateDatabase(MainActivity.SQLITE_DB_NAME, Context.MODE_PRIVATE,null);
        db.execSQL("delete from restaurantsD");
        while (true){
            try {
                if (!mResultSet.next()) break;
                //int id = mResultSet.getInt(0);
                String name = mResultSet.getString(2);
                String address = mResultSet.getString(3);
                String mealQualityStr = mResultSet.getString(4);
                String serviceQualityStr = mResultSet.getString(5);
                Float averagePrice = mResultSet.getFloat(6);
                int generalRating = mResultSet.getInt(7);
                db.execSQL("insert into RestaurantsD values(" +
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

        finish();
    }
}
