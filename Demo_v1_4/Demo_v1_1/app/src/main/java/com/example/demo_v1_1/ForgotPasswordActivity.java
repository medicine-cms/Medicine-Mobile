package com.example.demo_v1_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ForgotPasswordActivity extends AppCompatActivity {

    Button back, savepwd;
    EditText pwd,pwd2;
    private static String hasta_pwd, hasta_pwd2, hasta_tc;

    Connection con;
    DBConnector dbConnector;

    String z = "";
    Boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        back = (Button) findViewById(R.id.btnBack);
        savepwd = (Button) findViewById(R.id.btnSave);
        Intent intent = getIntent();
        hasta_tc = intent.getStringExtra("name");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        pwd = (EditText) findViewById(R.id.editText3);
        pwd2 = (EditText) findViewById(R.id.editText4);

        savepwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasta_pwd = pwd.getText().toString();
                hasta_pwd2 = pwd2.getText().toString();
                if (hasta_pwd.trim().equals("") || hasta_pwd2.trim().equals("")) {
                    Toast.makeText(ForgotPasswordActivity.this , "Lütfen Yeni Parolanızı Giriniz" , Toast.LENGTH_LONG).show();

                }
                else if (!hasta_pwd.trim().equals(hasta_pwd2.trim()) || !hasta_pwd2.trim().equals(hasta_pwd.trim())) {
                    Toast.makeText(ForgotPasswordActivity.this , "Parolanız Uyuşmuyor" , Toast.LENGTH_LONG).show();

                }
                else {

                    try {
                        con = dbConnector.connectionclass(); // Connect to database
                        if (con == null) {
                            Toast.makeText(ForgotPasswordActivity.this , "İnternet Bağlantınızı Kontrol Edin" , Toast.LENGTH_LONG).show();
                        } else {
                            // Change below query according to your own database.
                            String query = "update HASTA set Hasta_Password= '" + hasta_pwd.toString() + "' where Hasta_TC_Kimlik_No = '" + hasta_tc.toString() + "'  ";
                            Statement stmt = con.createStatement();
                            stmt.executeUpdate(query);
                            Toast.makeText(ForgotPasswordActivity.this , "Parolanız Değişti" , Toast.LENGTH_LONG).show();
                            con.close();
                            Intent i = new Intent(ForgotPasswordActivity.this,MainActivity.class);
                            ForgotPasswordActivity.this.startActivity(i);
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        Toast.makeText(ForgotPasswordActivity.this , ex.getMessage() , Toast.LENGTH_LONG).show();
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
