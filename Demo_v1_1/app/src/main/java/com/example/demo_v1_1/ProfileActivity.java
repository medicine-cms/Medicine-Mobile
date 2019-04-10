package com.example.demo_v1_1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;

public class ProfileActivity extends AppCompatActivity {

    Connection con;
    DBConnector dbConnector;

    Button logout;
    TextView name,lname,card;
    String hasta_name, hasta_lname, hasta_card;
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


        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(ProfileActivity.this,NewActivity.class);
                        ProfileActivity.this.startActivity(i1);
                        Toast.makeText(ProfileActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(ProfileActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return true;
            }
        });

        logout.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                ProfileActivity.this.startActivity(i);
            }
        });

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
