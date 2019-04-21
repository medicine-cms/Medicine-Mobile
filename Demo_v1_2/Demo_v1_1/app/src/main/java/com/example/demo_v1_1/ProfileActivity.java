package com.example.demo_v1_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProfileActivity extends AppCompatActivity {

    Connection con;
    DBConnector dbConnector;

    String msg = "";
    Boolean isSuccess = false;
    Button logout;
    TextView name,lname,card;
    String hasta_id,hasta_tc,hasta_name, hasta_lname, hasta_card;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbConnector = new DBConnector();

        logout = (Button) findViewById(R.id.btnLogout);
        name = (TextView) findViewById(R.id.txtName);
        lname = (TextView) findViewById(R.id.txtLastname);
        card = (TextView) findViewById(R.id.txtCard);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();

        NewActivity newActivity = new NewActivity();
        hasta_id = newActivity.getVariable();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(ProfileActivity.this,NewActivity.class);
                        i1.putExtra("name",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(ProfileActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(ProfileActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });

        logout.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                ProfileActivity.this.startActivity(i);
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
                String query = "select HASTA.* from HASTA where Hasta_ID='" + hasta_id + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    hasta_name = rs.getString("Hasta_Ad");
                    hasta_lname = rs.getString("Hasta_Soyad");
                    hasta_card = rs.getString("Kart_ID");

                    name.setText(hasta_name); //ilaclar.get(0)
                    lname.setText(hasta_lname);
                    card.setText(hasta_card);

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
