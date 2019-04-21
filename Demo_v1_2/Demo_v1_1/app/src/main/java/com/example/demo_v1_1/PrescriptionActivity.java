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
    Button back, ilac,ilac1,ilac2,ilac3,ilac4,ilac5,ilac6,ilac7,ilac8,ilac9,ilac10;
    String hasta_tc;
    TextView doktor;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    String doktorx, id,columnName1, columnName2 ;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        dbConnector = new DBConnector();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        doktorx = intent.getStringExtra("doktor");
        doktor = (TextView) findViewById(R.id.txtDoktor);
        doktor.setText(doktorx);
        back = (Button) findViewById(R.id.btnNew);
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

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();
        //hasta_tc="123456789";

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(PrescriptionActivity.this,NewActivity.class);
                        i1.putExtra("name",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(PrescriptionActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(PrescriptionActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(PrescriptionActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });
        ilac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionActivity.this,DetailActivity.class);
                //intent.putExtra("id",String.valueOf(columnName3));
                intent.putExtra("iid",String.valueOf(columnName2));
                PrescriptionActivity.this.startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = new MainActivity();
                Intent intent = new Intent(PrescriptionActivity.this,NewActivity.class);
                intent.putExtra("name",String.valueOf(mainActivity.username.getText().toString()));
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
                String query = "select ILAC.* from ILAC left join DOKTOR on DOKTOR.DOKTOR_ID=ILAC.DOKTOR_ID where Hasta_ID='" + id + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                //ArrayList<String> ilaclar = new ArrayList<String>();
                //int j = 0;
                while(rs.next()){
                    columnName1 = rs.getString("Ilac_Adi");
                    columnName2 = rs.getString("Ilac_ID");
                    //ilaclar.add(columnName1);

                    ilac.setText(columnName1); //ilaclar.get(0)


                    //j++;
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
    protected void callValue () {


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
