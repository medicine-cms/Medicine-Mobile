package com.example.demo_v1_1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity
{
    // Declaring layout button, edit texts
    Button login, signup, forgot;
    EditText username,password;
    private static String hasta_id, hasta_password;
    // End Declaring layout button, edit texts

    // Declaring connection variables
    Connection con;
    DBConnector dbConnector;
    //End Declaring connection variables

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        // Getting values from button, texts and progress bar
        login = (Button) findViewById(R.id.button);
        signup = (Button) findViewById(R.id.btnSignup);
        forgot = (Button) findViewById(R.id.btnForgot);

        username = (EditText) findViewById(R.id.editText3);
        password = (EditText) findViewById(R.id.editText4);
        // End Getting values from button, texts and progress bar

        // Declaring Server ip, username, database name and password

        // Declaring Server ip, username, database name and password

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                //intent.putExtra("id",String.valueOf(columnName3));
                MainActivity.this.startActivity(intent);
            }
        });
        // Setting up the function when button login is clicked
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");
            }
        });
        //End Setting up the function when button login is clicked
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ForgotActivity.class);
                MainActivity.this.startActivity(intent);

            }
        });
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute()
        {
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPostExecute(String r)
        {
            //progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_SHORT).show();
            if(isSuccess)
            {
                Toast.makeText(MainActivity.this , "Login Successfull" , Toast.LENGTH_LONG).show();
                //finish();
                Intent i = new Intent(MainActivity.this,NewActivity.class);
                i.putExtra("name",String.valueOf(hasta_id));
                MainActivity.this.startActivity(i);
            }
        }
        @Override
        protected String doInBackground(String... params)
        {
            hasta_id = username.getText().toString();
            hasta_password = password.getText().toString();
            if(hasta_id.trim().equals("")|| hasta_password.trim().equals(""))
                z = "Please enter Username and Password";
            else
            {
                try
                {
                    con = dbConnector.connectionclass(); // Connect to database
                    if (con == null)
                    {
                        z = "Check Your Internet Access!";
                    }
                    else
                    {
                        // Change below query according to your own database.
                        String query = "select * from HASTA where Hasta_TC_Kimlik_No= '" + hasta_id.toString() + "' and Hasta_Password = '"+ hasta_password.toString() +"'  ";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if(rs.next())
                        {
                            z = "Login successful";
                            isSuccess=true;
                            con.close();
                        }
                        else
                        {
                            z = "Invalid Credentials!";
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