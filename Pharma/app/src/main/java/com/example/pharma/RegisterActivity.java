package com.example.pharma;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    String Name, LName, Pass, Pass2, TC, Birth, Seri ;
    Button login, signup;
    ImageButton pickdate;
    EditText ad, soyad, tc, pass, pass2, seri;
    TextView birth;
    // Declaring connection variables
    Connection con;
    DBConnector dbConnector;
    //End Declaring connection variables
    String msg = "";
    Calendar c;
    DatePickerDialog dpd;
    Boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Log.d("mesaj", "create");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();

        login = (Button) findViewById(R.id.btnLogin);
        signup = (Button) findViewById(R.id.btnSign);
        pickdate = (ImageButton) findViewById(R.id.btnPick);
        ad = (EditText) findViewById(R.id.editName);
        soyad = (EditText) findViewById(R.id.editLast);
        tc = (EditText) findViewById(R.id.editTC);
        pass = (EditText) findViewById(R.id.editPass);
        pass2 = (EditText) findViewById(R.id.editPass2);
        birth = (TextView) findViewById(R.id.editBirth);
        birth.setEnabled(false);
        birth.setFocusable(false);
        seri = (EditText) findViewById(R.id.editSeri);



        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c= Calendar.getInstance();
                int mYear = c.get(Calendar.DAY_OF_MONTH);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.YEAR);


                dpd = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        birth.setText(mDay + "." + (mMonth+1) +"." + mYear);

                    }
                }, mDay,mMonth,mYear);
                dpd.getDatePicker().setMaxDate(c.getTimeInMillis());
                dpd.show();
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name = ad.getText().toString();
                LName = soyad.getText().toString();
                TC = tc.getText().toString();
                Pass = pass.getText().toString();
                Pass2 = pass2.getText().toString();
                Birth = birth.getText().toString();
                Seri = seri.getText().toString();
                if(TcKimlikNo.Dogrula(TC)==true) {
                    if (Seri.length()==9) {
                        if (Pass.trim().equals("") || Pass2.trim().equals("")) {
                            Toast.makeText(RegisterActivity.this, "Lütfen Parolanızı Giriniz", Toast.LENGTH_LONG).show();

                        } else if (!Pass.trim().equals(Pass2.trim()) || !Pass2.trim().equals(Pass.trim())) {
                            Toast.makeText(RegisterActivity.this, "Parolanız Uyuşmuyor", Toast.LENGTH_LONG).show();

                        }
                        else if (Name.trim().equals("") || LName.trim().equals("")) {
                            Toast.makeText(RegisterActivity.this, "Lütfen Tam Adınızı Giriniz", Toast.LENGTH_LONG).show();
                        }
                        else if (Birth.trim().equals("")) {
                            Toast.makeText(RegisterActivity.this, "Lütfen Doğum Tarihinizi Giriniz", Toast.LENGTH_LONG).show();

                        }

                        else {

                            try {
                                con = dbConnector.connectionclass();        // Connect to database
                                if (con == null) {
                                    msg = "Check Your Internet Access!";
                                } else {
                                    Statement stmt = con.createStatement();
                                    String query2 = "select * from HASTA where Hasta_TC_Kimlik_No='" +TC+"'";
                                    ResultSet rs2 = stmt.executeQuery(query2);
                                    if (rs2.next()==true) {
                                        Toast.makeText(RegisterActivity.this, "Zaten Üyesiniz", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        String query = "INSERT INTO HASTA(Hasta_Ad, Hasta_Soyad, Hasta_TC_Kimlik_No, Hasta_Password, Hasta_Dogum_Tarihi, Hasta_SeriNo) " +
                                                "VALUES ('" + Name + "','" + LName + "','" + TC + "','" + Pass + "','" + Birth + "','" + Seri + "') ";
                                        stmt.executeUpdate(query);
                                        Toast.makeText(RegisterActivity.this, "Tebrikler! Artık Giriş Yapabilirsiniz", Toast.LENGTH_LONG).show();
                                        finish();
                                    }
                                }
                                con.close();
                            } catch (Exception ex) {
                                isSuccess = false;
                                msg = ex.getMessage();
                            }

                        }
                    }
                    else{
                        Toast.makeText(RegisterActivity.this , "Yanlış Seri No" , Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(RegisterActivity.this , "Yanlış T.C No" , Toast.LENGTH_LONG).show();

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