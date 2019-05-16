package com.example.pharma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class PrescriptionActivity extends AppCompatActivity {
    ArrayList<String> items=new ArrayList<>();
    SpinnerDialog spinnerDialog;
    String hasta_tc, fulldoktor, ilacdate;
    TextView doktor;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    String doktorx, id;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        Log.d("mesaj", "create");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        dbConnector = new DBConnector();
        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_ilac);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Intent intent = getIntent();
        id = intent.getStringExtra("id"); //
        doktorx = intent.getStringExtra("doktor");
        fulldoktor = intent.getStringExtra("doktor2"); //
        ilacdate = intent.getStringExtra("ilacdate"); //
        doktor = (TextView) findViewById(R.id.txtDoktor);
        doktor.setText(doktorx);

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(PrescriptionActivity.this,DoctorActivity.class);
                        i1.putExtra("hastatc",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(PrescriptionActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(PrescriptionActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(PrescriptionActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_info:
                        Intent i3 = new Intent(PrescriptionActivity.this,AboutActivity.class);
                        startActivity(i3);
                        Toast.makeText(PrescriptionActivity.this, "Hakkında", Toast.LENGTH_SHORT).show();


                }
                return false;
            }
        });

        try
        {
            // Connect to database
            con = dbConnector.connectionclass();
            if (con == null)
            {
                msg = "İnternet Bağlantınızı Kontrol Edin!";
                Toast.makeText(PrescriptionActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
            else
            {
                String query = "select ILAC.* from ILAC WHERE Doktor_ID = '" + fulldoktor.trim() +
                        "'  AND Hasta_ID = '" + id.trim() + "' AND Verildigi_Tarih= '" + ilacdate.trim() + "' ";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                ArrayList<String> ilaclar = new ArrayList<String>();
                int c = 0;
                final String[] ilaclarrr = new String[rs.getMetaData().getColumnCount()];
                final String[] ilaclarrrid = new String[rs.getMetaData().getColumnCount()];

                while(rs.next()){
                    ilaclarrr[c] = rs.getString("Ilac_Adi");
                    ilaclarrrid[c] = rs.getString("Ilac_ID");
                    c++;
                }

                Button btn[] = new Button[c];

                for (int i = 0; i < c; i++) {
                    items.add(ilaclarrr[i]);
                    btn[i] = new Button(this);
                    btn[i].setText(ilaclarrr[i]);
                    btn[i].setTextColor(getResources().getColorStateList(R.color.White));
                    btn[i].setBackgroundResource(R.drawable.buttondynamic);
                    btn[i].setGravity(Gravity.CENTER);
                    ll.addView(btn[i], lp);
                    final int finalI = i;
                    btn[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View columnName6) {
                            Intent intent = new Intent(PrescriptionActivity.this,DetailActivity.class);
                            intent.putExtra("iid",String.valueOf(ilaclarrrid[finalI]));
                            PrescriptionActivity.this.startActivity(intent);
                        }
                    });

                    spinnerDialog=new SpinnerDialog(PrescriptionActivity.this,items,"İlaç Seç veya Ara","Kapat");// With No Animation
                    spinnerDialog=new SpinnerDialog(PrescriptionActivity.this,items,"İlaç Seç veya Ara",R.style.DialogAnimations_SmileWindow,"Kapat");// With 	Animation

                    spinnerDialog.setCancellable(true); // for cancellable
                    spinnerDialog.setShowKeyboard(false);// for open keyboard by default
                    spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                        @Override
                        public void onClick(String item, int position) {
                            Intent intent3 = new Intent(PrescriptionActivity.this,DetailActivity.class);
                            intent3.putExtra("iid",String.valueOf(ilaclarrrid[position]));
                            PrescriptionActivity.this.startActivity(intent3);

                        }
                    });
                    findViewById(R.id.show).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            spinnerDialog.showSpinerDialog();
                        }
                    });
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