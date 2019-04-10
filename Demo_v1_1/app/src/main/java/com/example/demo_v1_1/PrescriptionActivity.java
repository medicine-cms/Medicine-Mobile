package com.example.demo_v1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PrescriptionActivity extends AppCompatActivity {
    Button ilac,ilac1,ilac2,ilac3,ilac4,ilac5,ilac6,ilac7,ilac8,ilac9,ilac10;
    TextView doktor;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    String id,columnName1 ;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        doktor = (TextView) findViewById(R.id.txtDoktor);
        ilac = (Button) findViewById(R.id.buttonilac);
        ilac1 = (Button) findViewById(R.id.buttonilac1);
        ilac2 = (Button) findViewById(R.id.buttonilac2);
        ilac3 = (Button) findViewById(R.id.buttonilac3);
        ilac4 = (Button) findViewById(R.id.buttonilac4);
        ilac5 = (Button) findViewById(R.id.buttonilac5);
        ilac6 = (Button) findViewById(R.id.buttonilac6);
        ilac7 = (Button) findViewById(R.id.buttonilac7);
        ilac8 = (Button) findViewById(R.id.buttonilac8);
        ilac9 = (Button) findViewById(R.id.buttonilac9);
        ilac10 = (Button) findViewById(R.id.buttonilac10);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(PrescriptionActivity.this,NewActivity.class);
                        PrescriptionActivity.this.startActivity(i1);
                        Toast.makeText(PrescriptionActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(PrescriptionActivity.this,ProfileActivity.class);
                        PrescriptionActivity.this.startActivity(i2);
                        Toast.makeText(PrescriptionActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });
        ilac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionActivity.this,DetailActivity.class);
                //intent.putExtra("id",String.valueOf(columnName3));
                PrescriptionActivity.this.startActivity(intent);
            }
        });


        try
        {
            con = dbConnector.connectionclass();        // Connect to database
            if (con == null)
            {
                msg = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = "select ILAC.* from ILAC left join ilac on ilac.ilac_ID=ILAC.ilac_ID where Hasta_ID='" + id + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ArrayList<String> ilaclar = new ArrayList<String>();
                int j = 0;
                while(rs.next()){
                    columnName1 = rs.getString("Ilac_Adi");
                    ilaclar.add(columnName1);

                    ilac.setText(ilaclar.get(0));


                    j++;
                }
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }
//        ArrayList<String> ilaclar = new ArrayList<String>();
//        ilaclar.add(columnName1);


    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
