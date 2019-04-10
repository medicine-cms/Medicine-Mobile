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
import android.widget.Toast;

import java.sql.Connection;

public class UserActivity extends AppCompatActivity {

//    Connection con;
//    DBConnector dbConnector;
//
//    Button logout;
//    EditText name,lname,card;
//    String hasta_name, hasta_lname, hasta_card;
//    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

//        dbConnector = new DBConnector();
//
//        logout = (Button) findViewById(R.id.btnLogout);
//        name = (EditText) findViewById(R.id.txtName);
//        lname = (EditText) findViewById(R.id.txtLastname);
//        card = (EditText) findViewById(R.id.txtCard);
//
//        logout = (Button) findViewById(R.id.btnLogout);
//
//        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.action_home:
//                        Intent i1 = new Intent(UserActivity.this,NewActivity.class);
//                        UserActivity.this.startActivity(i1);
//                        Toast.makeText(UserActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.action_profile:
//                        Toast.makeText(UserActivity.this, "Profil", Toast.LENGTH_SHORT).show();
//                        break;
//
//                }
//                return true;
//            }
//        });
//
//        logout.setOnClickListener(new View.OnClickListener()    {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(UserActivity.this,MainActivity.class);
//                UserActivity.this.startActivity(i);
//            }
//        });

    }
}
