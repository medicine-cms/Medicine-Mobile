package com.example.pharma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String hasta_tc, iid, timer, max;
    TextView Way, Dose, Max, Range;
    ImageView Timer;
    static TextView Ilac;
    Connection con;
    String msg = "";
    static String ilacadi;
    Boolean isSuccess = false;
    DBConnector dbConnector;
    Button start, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Log.d("mesaj", "create");
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        Intent intent = getIntent();
        iid = intent.getStringExtra("iid");

        Timer = (ImageView) findViewById(R.id.imageView4);
        Way = (TextView) findViewById(R.id.txtWay);
        Dose = (TextView) findViewById(R.id.txtDose);
        Max = (TextView) findViewById(R.id.txtMax);
        Range = (TextView) findViewById(R.id.txtRange);
        Ilac = (TextView) findViewById(R.id.txtIlac);
        start = (Button) findViewById(R.id.btnstart);
        stop = (Button) findViewById(R.id.btnstop);

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(DetailActivity.this,DoctorActivity.class);
                        i1.putExtra("hastatc",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(DetailActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(DetailActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(DetailActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_info:
                        Intent i3 = new Intent(DetailActivity.this,AboutActivity.class);
                        startActivity(i3);
                        Toast.makeText(DetailActivity.this, "Hakkında", Toast.LENGTH_SHORT).show();


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
                Toast.makeText(DetailActivity.this, msg, Toast.LENGTH_SHORT).show();

            }
            else
            {
                String query = "select ILAC.* from ILAC where Ilac_ID='" + iid + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                List<Detail> details = new ArrayList<Detail>();
                while(rs.next()){
                    Detail detail = new Detail();
                    detail.setKSekli(rs.getString("Kullanim_Sekli"));
                    detail.setKDozu(rs.getString("Kullanim_Dozu"));
                    detail.setKSure(rs.getString("Max_Kullanim_Suresi"));
                    detail.setKAralik(rs.getString("Kullanim_Araligi"));
                    detail.setIAdi(rs.getString("Ilac_Adi"));
                    details.add(detail);

                    String str = detail.getKSekli();
                    String cap = str.substring(0, 1).toUpperCase() + str.substring(1);
                    Way.setText(cap); //ilaclar.get(0)
                    Dose.setText(detail.getKDozu());
                    max = detail.getKSure();
                    Max.setText(max +" gün") ;
                    timer = detail.getKAralik();
                    Range.setText(timer + " saatte 1");
                    String str1 = detail.getIAdi();
                    String cap1 = str1.substring(0, 1).toUpperCase() + str1.substring(1);;
                    Ilac.setText(cap1);

                }
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }
        //BİLDİRİM
        // ------------------------------------
        //Zaman ayarlı offline bildirim gönderimi için alarm servisi aktivasyonu
        final AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //MainActivity classı ile AlarmReceiver classının uygulama başlangıcında birleştirilmesini sağlayan yapı (RunTime Binding)
        Intent notificationIntent = new Intent(this, AlarmReceiver.class);

        //Uygulama kapalı olsa dahi bildirimin uygulama yetkilerine sahip olmasını sağlayan yapı
        final PendingIntent broadcast = PendingIntent.getBroadcast(this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Uygulama başlatıldıktan 5 saniye sonrasında bildirim göndermek için Calendar kütüphanesinin kullanılması
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 5);

        long day = 0;
        day = Integer.parseInt(max);
        final long finalDay = day*1000*60*60*24;

        long countdown = 0;
        countdown = Integer.parseInt(timer);
        final long finalCountdown = countdown*1000*60*60;

        stop.setVisibility(View.GONE);
        Timer.setVisibility(View.GONE);
        start.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable(){
            public void run(){
                alarmManager.cancel(broadcast);

            }
        }, finalDay);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setVisibility(View.GONE);
                stop.setVisibility(View.VISIBLE);
                Timer.setVisibility(View.VISIBLE);

                //Bildirimin hangi aralıkta bir gönderilmesini sağlamak için kullanılan yapı
                if (alarmManager != null) {//--> Kullanılması gerekli değil fakat NullPointerException hatasını önlemek için kullanıldı.
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), finalCountdown, broadcast);
                }
                new Handler().postDelayed(new Runnable(){
            public void run(){
                stop.setVisibility(View.GONE);
                start.setVisibility(View.VISIBLE);
                alarmManager.cancel(broadcast);

            }
        }, finalDay);

                stop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        stop.setVisibility(View.GONE);
                        Timer.setVisibility(View.GONE);
                        start.setVisibility(View.VISIBLE);
                        alarmManager.cancel(broadcast);
                    }
                });
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

    public static String getVariable() {
        try {

            if (ilacadi!=null) {
                ilacadi = Ilac.getText().toString();
            }
            else {ilacadi="";}
        }
        catch (Exception ex){

        }
        return ilacadi ;
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
