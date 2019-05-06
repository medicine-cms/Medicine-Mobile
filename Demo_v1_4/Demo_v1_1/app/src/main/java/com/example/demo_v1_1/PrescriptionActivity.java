package com.example.demo_v1_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class PrescriptionActivity extends AppCompatActivity {
//    Button back, ilac,ilac1,ilac2,ilac3,ilac4,ilac5,ilac6,ilac7,ilac8,ilac9,ilac10;
    ImageButton show;
    ArrayList<String> items=new ArrayList<>();
    SpinnerDialog spinnerDialog;
    String hasta_tc, ilid, hasta_id, fulldoktor, ilad;
    TextView doktor;
    Connection con;
    DBConnector dbConnector;
    String msg = "";
    Boolean isSuccess = false;
    String doktorx, id,columnName1, columnName2 ;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        dbConnector = new DBConnector();
        LinearLayout ll = (LinearLayout)findViewById(R.id.linear_ilac);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Intent intent = getIntent();
        id = intent.getStringExtra("id"); //
        doktorx = intent.getStringExtra("doktor");
        fulldoktor = intent.getStringExtra("doktor2"); //
        doktor = (TextView) findViewById(R.id.txtDoktor);
        doktor.setText(doktorx);
        /*ilac = (Button) findViewById(R.id.buttonilac);
        ilac1 = (Button) findViewById(R.id.buttonilac1);
        ilac2 = (Button) findViewById(R.id.buttonilac2);
        ilac3 = (Button) findViewById(R.id.buttonilac3);
        ilac4 = (Button) findViewById(R.id.buttonilac4);
        ilac5 = (Button) findViewById(R.id.buttonilac5);
        ilac6 = (Button) findViewById(R.id.buttonilac6);
        ilac7 = (Button) findViewById(R.id.buttonilac7);
        ilac8 = (Button) findViewById(R.id.buttonilac8);
        ilac9 = (Button) findViewById(R.id.buttonilac9);
        ilac10 = (Button) findViewById(R.id.buttonilac10);*/

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();

//        NewActivity newActivity = new NewActivity();
//        id = newActivity.getVariable();
//        fulldoktor = newActivity.getVariable1();
        //hasta_tc="123456789";

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(PrescriptionActivity.this,NewActivity.class);
                        i1.putExtra("name",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(PrescriptionActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(PrescriptionActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(PrescriptionActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });
/*        ilac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PrescriptionActivity.this,DetailActivity.class);
                //intent.putExtra("id",String.valueOf(columnName3));
                intent.putExtra("iid",String.valueOf(columnName2));
                PrescriptionActivity.this.startActivity(intent);
            }
        });*/




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
//                String query = "select ILAC.* from ILAC left join DOKTOR on DOKTOR.DOKTOR_ID=ILAC.DOKTOR_ID where Doktor_ID='" + fulldoktor + "'";
                String query = "select ILAC.* from ILAC WHERE Doktor_ID = '" + fulldoktor.trim() +
                        "'  AND Hasta_ID = '" + id.trim() + "' "; //where (Doktor_ID='" + fulldoktor + "'x" + "AND Hasta_ID = '" + id + ")"
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                //ArrayList<String> ilaclar = new ArrayList<String>();
                //int j = 0;
                List<Ilac> ilacs = new ArrayList<Ilac>();
                ArrayList<String> ilaclar = new ArrayList<String>();
                int c = 0;
                final String[] ilaclarrr = new String[rs.getMetaData().getColumnCount()];
                final String[] ilaclarrrid = new String[rs.getMetaData().getColumnCount()];

                while(rs.next()){
                    ilaclarrr[c] = rs.getString("Ilac_Adi");
                    ilaclarrrid[c] = rs.getString("Ilac_ID");
                    c++;
//                    Ilac ilac = new Ilac();
//                    ilac.setIad(rs.getString("Ilac_Adi"));
//                    ilac.setIlacid(rs.getString("Ilac_ID")) ;
//                    ilacs.add(ilac);
//
//                    ilid = ilac.getIlacid();
//                    ilad = ilac.getIad();
//                    /*columnName1 = rs.getString("Ilac_Adi");
//                    columnName2 = rs.getString("Ilac_ID");*/
//                    ilaclar.add(ilad);

                    //ilac.setText(columnName1); //ilaclar.get(0)


                    //j++;
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

//                for (int i = 0; i < ilacs.size(); i++) {
//                    Ilac ilac = ilacs.get(i);
//                    int size = ilacs.size(); // Do with it whatever you want.
//                    btn[i] = new Button(this);
//                    btn[i].setText(ilaclar.get(i));
//                    ll.addView(btn[i], lp);
//                    btn[i].setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View columnName6) {
//                            Intent intent = new Intent(PrescriptionActivity.this,DetailActivity.class);
//                            //intent.putExtra("id",String.valueOf(columnName3));
//                            intent.putExtra("iid",String.valueOf(ilid));
//                            PrescriptionActivity.this.startActivity(intent);
//                        }
//                    });
//                }
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }
//        ArrayList<String> ilaclar = new ArrayList<String>();
//        ilaclar.add(columnName1);


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
