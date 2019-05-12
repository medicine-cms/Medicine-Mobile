package com.example.pharma;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity
{
    Button login, signup, forgot;
    EditText username,password;
    private static String hasta_id, hasta_password;

    // Declaring connection variables
    Connection con;
    DBConnector dbConnector;
    //End Declaring connection variables

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("mesaj", "create");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        // Getting values from button, texts and progress bar
        login = (Button) findViewById(R.id.button);
        signup = (Button) findViewById(R.id.btnSignup);
        forgot = (Button) findViewById(R.id.btnForgot);

        username = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        // End Getting values from button, texts and progress bar

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });
        // Setting up the function when button login is clicked
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // this is the Asynctask, which is used to process in background to reduce load on app process
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
        //End Setting up the function when button login is clicked
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotPasswordActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {
        }

        @Override
        public void onPostExecute(String r)
        {
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(MainActivity.this , "Anasayfa" , Toast.LENGTH_LONG).show();
                Intent i = new Intent(MainActivity.this,DoctorActivity.class);
                i.putExtra("hastaid",String.valueOf(hasta_id));
                MainActivity.this.startActivity(i);
            }
        }


        @Override
        protected String doInBackground(String... params)
        {
            hasta_id = username.getText().toString();
            hasta_password = password.getText().toString();
            if(hasta_id.trim().equals("")|| hasta_password.trim().equals(""))
                z = "Lütfen T.C No ve Parolanızı Giriniz";
            else
            {
                try
                {
                    con = dbConnector.connectionclass(); // Connect to database
                    if (con == null)
                    {
                        z = "İnternet Bağlantınızı Kontrol Edin!";
                    }
                    else
                    {
                        String query = "select * from HASTA where Hasta_TC_Kimlik_No= '" + hasta_id + "' and Hasta_Password = '"+ hasta_password +"'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z = "Giriş Başarılı";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Geçersiz T.C No veya Parola";
                            isSuccess = false;
                        }
                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = ex.getMessage();
                }
            }
            return z;
        }
    }

    public static String getVariable() {
        return hasta_id;
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