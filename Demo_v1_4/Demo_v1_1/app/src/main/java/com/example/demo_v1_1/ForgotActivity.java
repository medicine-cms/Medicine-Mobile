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

public class ForgotActivity extends AppCompatActivity {

    Button back, newpwd;
    EditText tcno,serino;
    private static String hasta_id, hasta_seri;

    Connection con;
    DBConnector dbConnector;

    String z = "";
    Boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        back = (Button) findViewById(R.id.btnBack);
        newpwd = (Button) findViewById(R.id.btnNew);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tcno = (EditText) findViewById(R.id.editText3);
        serino = (EditText) findViewById(R.id.editText4);

        newpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasta_id = tcno.getText().toString();
                hasta_seri = serino.getText().toString();
                if (hasta_id.trim().equals("") || hasta_seri.trim().equals(""))
                    z = "Please enter Username and Password";
                else {
                    try {
                        con = dbConnector.connectionclass(); // Connect to database
                        if (con == null) {
                            z = "Check Your Internet Access!";
                        } else {
                            // Change below query according to your own database.
                            String query = "select * from HASTA where Hasta_TC_Kimlik_No= '" + hasta_id.toString() + "' and Hasta_SeriNo = '" + hasta_seri.toString() + "'  ";
                            Statement stmt = con.createStatement();
                            ResultSet rs = stmt.executeQuery(query);
                            if (rs.next()) {
                                z = "Bilgiler Doğrulandı";
                                isSuccess = true;
                                con.close();
                                Intent intent = new Intent(ForgotActivity.this,ForgotPasswordActivity.class);
                                intent.putExtra("name",String.valueOf(hasta_id));
                                ForgotActivity.this.startActivity(intent);
                            } else {
                                z = "Invalid Credentials!";
                                isSuccess = false;
                            }
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        z = ex.getMessage();
                    }
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
