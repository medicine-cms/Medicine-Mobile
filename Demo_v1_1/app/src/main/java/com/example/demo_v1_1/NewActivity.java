package com.example.demo_v1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class NewActivity extends AppCompatActivity {

    Button doctor,doctor1,doctor2,doctor3,doctor4,doctor5,doctor6,doctor7,doctor8,doctor9,doctor10;
    TextView welcome;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    String name,fullName,Doktor1,columnName1,columnName2,columnName3,columnName5,columnName4,columnName6,columnName7;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();


        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        doctor = (Button) findViewById(R.id.buttondoktor);
        doctor1 = (Button) findViewById(R.id.buttondoktor1);
        doctor2 = (Button) findViewById(R.id.buttondoktor2);
        doctor3 = (Button) findViewById(R.id.buttondoktor3);
        doctor4 = (Button) findViewById(R.id.buttondoktor4);
        doctor5 = (Button) findViewById(R.id.buttondoktor5);
        doctor6 = (Button) findViewById(R.id.buttondoktor6);
        doctor7 = (Button) findViewById(R.id.buttondoktor7);
        doctor8 = (Button) findViewById(R.id.buttondoktor8);
        doctor9 = (Button) findViewById(R.id.buttondoktor9);
        doctor10 = (Button) findViewById(R.id.buttondoktor10);
        welcome = (TextView) findViewById(R.id.textView);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(NewActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(NewActivity.this,ProfileActivity.class);
                        NewActivity.this.startActivity(i2);
                        Toast.makeText(NewActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View columnName6) {
                Intent intent = new Intent(NewActivity.this,PrescriptionActivity.class);
                intent.putExtra("id",String.valueOf(columnName3));
                NewActivity.this.startActivity(intent);
            }
        });
        

        try
        {
            con = dbConnector.connectionclass(); // Connect to database
            if (con == null)
            {
                msg = "Check Your Internet Access!";
            }
            else
            {
                // Change below query according to your own database.
                String query = "select * from HASTA where Hasta_TC_Kimlik_No='" + name + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    columnName1 = rs.getString("Hasta_Ad");
                    columnName2 = rs.getString("Hasta_Soyad");
                    columnName3 = rs.getString("Hasta_ID");
                }
                try
                {
                    con = dbConnector.connectionclass();// Connect to database
                    if (con == null)
                    {
                        msg = "Check Your Internet Access!";
                    }
                    else
                    {
                        // Change below query according to your own database.
                        String query1 = "select DOKTOR.* from DOKTOR left join ILAC on ILAC.Doktor_ID=DOKTOR.Doktor_ID where Hasta_ID='" + columnName3 + "'";
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        ArrayList<String> doktorlar = new ArrayList<String>();

                        int j =0;
                        while(rs1.next()){
                            columnName4 = rs1.getString("Doktor_Unvan");
                            columnName5 = rs1.getString("Doktor_Ad");
                            columnName6 = rs1.getString("Doktor_Soyad");
                            columnName7 = rs1.getString("Doktor_Brans");
                            Doktor1 = (columnName4+columnName5+columnName6+"- "+columnName7).replaceAll("\\s+"," ");
                            doktorlar.add(Doktor1);
                            doctor.setText(doktorlar.get(1));
                            j++;

                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    msg = ex.getMessage();
                }
                //doctor.setText((columnName4+columnName5+columnName6+"- "+columnName7).replaceAll("\\s+"," "));
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }
        fullName = columnName1+columnName2;
        fullName = fullName.replaceAll("\\s+"," ");
        welcome.setText("HOŞGELDİNİZ " + fullName);
        welcome.setGravity(Gravity.CENTER);
        
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
