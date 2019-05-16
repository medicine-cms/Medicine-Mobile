package com.example.pharma;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class NewPasswordActivity extends AppCompatActivity {

    Button back, savepwd;
    EditText pwd,pwd2;
    private static String hasta_pwd, hasta_pwd2, hasta_tc;

    Connection con;
    DBConnector dbConnector;

    Boolean isSuccess = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        Log.d("mesaj", "create");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        back = (Button) findViewById(R.id.btnBack);
        savepwd = (Button) findViewById(R.id.btnSave);
        Intent intent = getIntent();
        hasta_tc = intent.getStringExtra("hastatc");

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
                    Toast.makeText(NewPasswordActivity.this , "Lütfen Yeni Parolanızı Giriniz" , Toast.LENGTH_LONG).show();

                }
                else if (!hasta_pwd.trim().equals(hasta_pwd2.trim()) || !hasta_pwd2.trim().equals(hasta_pwd.trim())) {
                    Toast.makeText(NewPasswordActivity.this , "Parolanız Uyuşmuyor" , Toast.LENGTH_LONG).show();

                }
                else {

                    try {
                        con = dbConnector.connectionclass(); // Connect to database
                        if (con == null) {
                            Toast.makeText(NewPasswordActivity.this , "İnternet Bağlantınızı Kontrol Edin!" , Toast.LENGTH_LONG).show();
                        } else {
                            Statement stmt = con.createStatement();
                            String query2 = "select * from HASTA where Hasta_TC_Kimlik_No='" +hasta_tc.toString()+"' " +
                                    "and Hasta_Password='"+hasta_pwd.toString()+"'";
                            ResultSet rs2 = stmt.executeQuery(query2);

                            if (rs2.next()==true) {
                                Toast.makeText(NewPasswordActivity.this , "Mevcut Parolanız ile Aynı Olamaz" , Toast.LENGTH_LONG).show();
                            }
                            else {
                                String query = "update HASTA set Hasta_Password= '" + hasta_pwd.toString() + "' where Hasta_TC_Kimlik_No = '" + hasta_tc.toString() + "'  ";
                                stmt.executeUpdate(query);
                                Toast.makeText(NewPasswordActivity.this , "Parolanız Değişti" , Toast.LENGTH_LONG).show();
                                con.close();
                                Intent i = new Intent(NewPasswordActivity.this,MainActivity.class);
                                NewPasswordActivity.this.startActivity(i);
                            }
                        }
                    } catch (Exception ex) {
                        isSuccess = false;
                        Toast.makeText(NewPasswordActivity.this , ex.getMessage() , Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mesaj", "start");

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mesaj", "stop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mesaj", "destroy");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mesaj", "pause");

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mesaj", "resume");

    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}