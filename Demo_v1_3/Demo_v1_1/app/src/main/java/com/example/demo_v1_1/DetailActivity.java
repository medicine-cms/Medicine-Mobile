package com.example.demo_v1_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String hasta_tc, iid, columnName1,columnName2,columnName3,columnName4, columnName5;
    TextView Way , Dose , Max , Range, Ilac;
    Connection con;
    String msg = "";
    Boolean isSuccess = false;
    DBConnector dbConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        Intent intent = getIntent();
        iid = intent.getStringExtra("iid");

        Way = (TextView) findViewById(R.id.txtWay);
        Dose = (TextView) findViewById(R.id.txtDose);
        Max = (TextView) findViewById(R.id.txtMax);
        Range = (TextView) findViewById(R.id.txtRange);
        Ilac = (TextView) findViewById(R.id.txtIlac);

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(DetailActivity.this,NewActivity.class);
                        i1.putExtra("name",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(DetailActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(DetailActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(DetailActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
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
                String query = "select ILAC.* from ILAC where Ilac_ID='" + iid + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                List<Detail> details = new ArrayList<Detail>();
                //ArrayList<String> ilaclar = new ArrayList<String>();
                //int j = 0;
                while(rs.next()){
                    Detail detail = new Detail();
                    detail.setKSekli(rs.getString("Kullanim_Sekli"));
                    detail.setKDozu(rs.getString("Kullanim_Dozu"));
                    detail.setKSure(rs.getString("Max_Kullanim_Suresi"));
                    detail.setKAralık(rs.getString("Kullanim_Araligi"));
                    detail.setIAdi(rs.getString("Ilac_Adi"));
                    details.add(detail);


/*                    columnName1 = rs.getString("Kullanim_Sekli");
                    columnName2 = rs.getString("Kullanim_Dozu");
                    columnName3 = rs.getString("Max_Kullanim_Suresi");
                    columnName4 = rs.getString("Kullanim_Araligi");
                    columnName5 = rs.getString("Ilac_Adi");*/
                    //ilaclar.add(columnName1);

                    Way.setText(detail.getKSekli()); //ilaclar.get(0)
                    Dose.setText(detail.getKDozu());
                    Max.setText(detail.getKSure());
                    Range.setText(detail.getKAralık());
                    Ilac.setText(detail.getIAdi());


                    //j++;
                }
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }

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
