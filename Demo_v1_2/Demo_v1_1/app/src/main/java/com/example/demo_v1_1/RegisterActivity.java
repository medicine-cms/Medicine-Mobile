package com.example.demo_v1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    String Name, LName, Pass, TC, Birth;
//    int TC;
//    Date Birth;
    Button login, signup;
    EditText ad, soyad, tc, pass, birth;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();

        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnSign);
        ad = (EditText) findViewById(R.id.editName);
        soyad = (EditText) findViewById(R.id.editLast);
        tc = (EditText) findViewById(R.id.editTC);
        pass = (EditText) findViewById(R.id.editPass);
        birth = (EditText) findViewById(R.id.editBirth);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = ad.getText().toString();
                LName = soyad.getText().toString();
                TC = tc.getText().toString();
                Pass = pass.getText().toString();
                Birth = birth.getText().toString();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                Birth = new Date();
//                try {
//                    Birth = dateFormat.parse(birth.getText().toString());
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
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
                        String query = "INSERT INTO HASTA(Hasta_Ad, Hasta_Soyad, Hasta_TC_Kimlik_No, Hasta_Password, Hasta_Dogum_Tarihi) " +
                                "VALUES ('" + Name  + "','" +  LName + "','" + TC + "','" + Pass + "','" + Birth + "') "   ;
                        Statement stmt = con.createStatement();
                        stmt.executeUpdate(query);
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    msg = ex.getMessage();
                }
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
