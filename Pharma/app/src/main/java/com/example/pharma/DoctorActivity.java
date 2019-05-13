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
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class DoctorActivity extends AppCompatActivity {

    ArrayList<String> items=new ArrayList<>();
    SpinnerDialog spinnerDialog;
    TextView welcome;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    private static String hasta_tc,fullName,hasta_ad,hasta_soyad,hasta_id;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Log.d("mesaj", "create");

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();

        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_doktor);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

        Intent intent = getIntent();
        hasta_tc = intent.getStringExtra("hastatc");

        welcome = (TextView) findViewById(R.id.textView);

//        AlertDialog dialog = new AlertDialog.Builder(this)
//                .setTitle("Deneme")
//                .setMessage("Deneme1")
//                .setPositiveButton("Evet", this)
//                .setNegativeButton("Hayır", this)
//                .show();
//
//        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
//        positiveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DoctorActivity.this,MainActivity.class);
//                DoctorActivity.this.startActivity(intent);
//            }
//        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Toast.makeText(DoctorActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(DoctorActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(DoctorActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_info:
                        Intent i3 = new Intent(DoctorActivity.this,AboutActivity.class);
                        startActivity(i3);
                        Toast.makeText(DoctorActivity.this, "Hakkında", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });


        try
        {
            con = dbConnector.connectionclass(); // Connect to database
            if (con == null)
            {
                msg = "İnternet Bağlantınızı Kontrol Edin!";
                Toast.makeText(DoctorActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
            else
            {
                String query = "select * from HASTA where Hasta_TC_Kimlik_No='" + hasta_tc + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    hasta_ad = rs.getString("Hasta_Ad");
                    hasta_soyad = rs.getString("Hasta_Soyad");
                    hasta_id = rs.getString("Hasta_ID");
                }
                try
                {
                    con = dbConnector.connectionclass();// Connect to database
                    if (con == null)
                    {
                        msg = "İnternet Bağlantınızı Kontrol Edin!";
                        Toast.makeText(DoctorActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        String query1 ="select " +
                                "DOKTOR.Doktor_ID, Doktor_Unvan, DOKTOR.Doktor_Ad, DOKTOR.Doktor_Soyad, DOKTOR.Doktor_Brans, ILAC.Verildigi_Tarih from DOKTOR " +
                                "left join ILAC on ILAC.Doktor_ID=DOKTOR.Doktor_ID where Hasta_ID = '" + hasta_id + "' " +
                                "group by DOKTOR.Doktor_ID , Doktor_Unvan, DOKTOR.Doktor_Ad , DOKTOR.Doktor_Soyad, DOKTOR.Doktor_Brans, ILAC.Verildigi_Tarih " +
                                "order by Verildigi_Tarih desc";
                        Statement stmt1 = con.createStatement();
                        ResultSet rs1 = stmt1.executeQuery(query1);
                        int c = 0;
                        final String[] options = new String[rs1.getMetaData().getColumnCount()];
                        final String[] DoktorID = new String[rs1.getMetaData().getColumnCount()];
                        final String[] IlacDate = new String[rs1.getMetaData().getColumnCount()];
                        final String[] x = new String[rs1.getMetaData().getColumnCount()];
                        while (rs1.next()) {
                            IlacDate[c] = rs1.getString("Verildigi_Tarih");
                            DoktorID[c] = rs1.getString("Doktor_ID");
                            options[c] = (rs1.getString("Doktor_Unvan") +" " + rs1.getString("Doktor_Ad") + " " + rs1.getString(
                                    "Doktor_Soyad") + " - " + rs1.getString("Doktor_Brans")+ " \n " +rs1.getString("Verildigi_Tarih"));
//                                    .replaceAll("\\s+"," ");

                            c++;
                        }
                        Button btn[] = new Button[c];

                        for (int i = 0; i < c; i++) {
                            items.add(options[i]);
                            btn[i] = new Button(this);
                            btn[i].setSingleLine(false);
                            btn[i].setImeOptions(EditorInfo.IME_FLAG_NO_ENTER_ACTION);
                            btn[i].setTextColor(getResources().getColorStateList(R.color.White));
                            btn[i].setBackgroundResource(R.drawable.buttondynamic);
                            btn[i].setGravity(Gravity.CENTER);
                            btn[i].setText(options[i]);
                            ll.addView(btn[i], lp);
                            final int finalI = i;
                            btn[i].setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View columnName6) {
                                    Intent intent = new Intent(DoctorActivity.this,PrescriptionActivity.class);
                                    intent.putExtra("id",String.valueOf(hasta_id));
                                    intent.putExtra("doktor",String.valueOf(options[finalI]));
                                    intent.putExtra("doktor2",String.valueOf(DoktorID[finalI]));
                                    intent.putExtra("ilacdate",String.valueOf(IlacDate[finalI]));
                                    DoctorActivity.this.startActivity(intent);
                                }
                            });

                            spinnerDialog=new SpinnerDialog(// With No Animation
                                    DoctorActivity.this,items,"Doktor Seç veya Ara","Kapat");
                            spinnerDialog=new SpinnerDialog(// With Animation
                                    DoctorActivity.this,items,"Doktor Seç veya Ara",R.style.DialogAnimations_SmileWindow,"Kapat");

                            spinnerDialog.setCancellable(true); // for cancellable
                            spinnerDialog.setShowKeyboard(false);// for open keyboard by default
                            spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                @Override
                                public void onClick(String item, int position) {
                                    Intent intent3 = new Intent(DoctorActivity.this,PrescriptionActivity.class);
                                    intent3.putExtra("id",String.valueOf(hasta_id));
                                    intent3.putExtra("doktor",String.valueOf(items.get(position)));
                                    intent3.putExtra("doktor2",String.valueOf(DoktorID[position]));
                                    intent3.putExtra("ilacdate",String.valueOf(IlacDate[position]));
                                    DoctorActivity.this.startActivity(intent3);

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
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }
        fullName = hasta_ad+ " " + hasta_soyad;
        fullName = fullName.replaceAll("\\s+"," ");
        welcome.setText("HOŞGELDİNİZ " + fullName);
        welcome.setGravity(Gravity.CENTER);

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Çıkış Yapmak İstiyor Musunuz?");
        builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(DoctorActivity.this,MainActivity.class);
                DoctorActivity.this.startActivity(i);
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
