package com.example.pharma;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    Button logout, newpwd;
    TextView name,lname,card;
    String hasta_id,hasta_tc,hasta_name, hasta_lname, hasta_card;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        dbConnector = new DBConnector();

        logout = (Button) findViewById(R.id.btnLogout);
        newpwd = (Button) findViewById(R.id.btnPwd);
        name = (TextView) findViewById(R.id.txtName);
        lname = (TextView) findViewById(R.id.txtLastname);
        card = (TextView) findViewById(R.id.txtCard);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();

        DoctorActivity doctorActivity = new DoctorActivity();
        hasta_id = doctorActivity.getVariable();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(ProfileActivity.this,DoctorActivity.class);
                        i1.putExtra("hastatc",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(ProfileActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Toast.makeText(ProfileActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_info:
                        Intent i3 = new Intent(ProfileActivity.this,AboutActivity.class);
                        startActivity(i3);
                        Toast.makeText(ProfileActivity.this, "Hakkında", Toast.LENGTH_SHORT).show();


                }
                return false;
            }
        });

        logout.setOnClickListener(new View.OnClickListener()    {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setCancelable(false);
                builder.setMessage("Çıkış Yapmak İstiyor Musunuz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ProfileActivity.this,MainActivity.class);
                        ProfileActivity.this.startActivity(i);
                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        newpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,ForgotPasswordActivity.class);
                i.putExtra("hastatc",String.valueOf(hasta_tc));
                ProfileActivity.this.startActivity(i);
            }
        });

        try
        {
            con = dbConnector.connectionclass();        // Connect to database
            if (con == null)
            {
                msg = "İnternet Bağlantınızı Kontrol Edin!";
                Toast.makeText(ProfileActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            else
            {

                String query = "select HASTA.*, KART.* from HASTA left join KART on HASTA.Hasta_ID = KART.Hasta_ID where HASTA.Hasta_ID='" + hasta_id + "' ";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    hasta_name = rs.getString("Hasta_Ad");
                    hasta_lname = rs.getString("Hasta_Soyad");
                    hasta_card = rs.getString("Kart_ID");

                    name.setText(hasta_name);
                    name.setGravity(Gravity.CENTER);
                    lname.setText(hasta_lname);
                    lname.setGravity(Gravity.CENTER);
                    card.setText(hasta_card);
                    card.setGravity(Gravity.CENTER);

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