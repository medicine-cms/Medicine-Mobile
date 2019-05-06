package com.example.demo_v1_1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    String hasta_tc, iid, columnName1,columnName2,columnName3,columnName4, columnName5;
    TextView Way , Dose , Max , Range, Ilac;
    Connection con;
    String msg = "";
    Boolean isSuccess = false;
    DBConnector dbConnector;
    Button start;
    public final static int ID_FOR_GROUP = 9;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        dbConnector = new DBConnector();
        Intent intent = getIntent();
        iid = intent.getStringExtra("iid");

        Way = (TextView) findViewById(R.id.txtWay);
        Dose = (TextView) findViewById(R.id.txtDose);
        Max = (TextView) findViewById(R.id.txtMax);
        Range = (TextView) findViewById(R.id.txtRange);
        Ilac = (TextView) findViewById(R.id.txtIlac);
        start = (Button) findViewById(R.id.btnstart);

        MainActivity mainActivity = new MainActivity();
        hasta_tc = mainActivity.getVariable();




        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_home:
                        Intent i1 = new Intent(DetailActivity.this,NewActivity.class);
                        i1.putExtra("name",String.valueOf(hasta_tc));
                        startActivity(i1);
                        Toast.makeText(DetailActivity.this, "Anasayfa", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        Intent i2 = new Intent(DetailActivity.this,ProfileActivity.class);
                        startActivity(i2);
                        Toast.makeText(DetailActivity.this, "Profil", Toast.LENGTH_SHORT).show();
                        break;

                }
                return false;
            }
        });

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
                String query = "select ILAC.* from ILAC where Ilac_ID='" + iid + "'";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                List<Detail> details = new ArrayList<Detail>();
                //ArrayList<String> ilaclar = new ArrayList<String>();
                //int j = 0;
                while(rs.next()){
                    Detail detail = new Detail();
                    detail.setKSekli(rs.getString("Kullanim_Sekli"));
                    detail.setKDozu(rs.getString("Kullanim_Dozu"));
                    detail.setKSure(rs.getString("Max_Kullanim_Suresi"));
                    detail.setKAralık(rs.getString("Kullanim_Araligi"));
                    detail.setIAdi(rs.getString("Ilac_Adi"));
                    details.add(detail);


/*                    columnName1 = rs.getString("Kullanim_Sekli");
                    columnName2 = rs.getString("Kullanim_Dozu");
                    columnName3 = rs.getString("Max_Kullanim_Suresi");
                    columnName4 = rs.getString("Kullanim_Araligi");
                    columnName5 = rs.getString("Ilac_Adi");*/
                    //ilaclar.add(columnName1);

                    Way.setText(detail.getKSekli()); //ilaclar.get(0)
                    Dose.setText(detail.getKDozu());
                    Max.setText(detail.getKSure());
                    Range.setText(detail.getKAralık());
                    Ilac.setText(detail.getIAdi());


                    //j++;
                }
            }
        }
        catch (Exception ex)
        {
            isSuccess = false;
            msg = ex.getMessage();
        }

        int day = 0;
        day = Integer.parseInt(Max.getText().toString());
        final int finalDay = day*10000;

        int countdown = 0;
        countdown = Integer.parseInt(Range.getText().toString());
        final int finalCountdown = countdown*1000;
//        if(Build.VERSION.SDK_INT<=21) {

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CountDownTimer(finalCountdown, 1000) {

                    public void onTick(long millisUntilFinished) {
//                        timer.setText(millisUntilFinished / 1000+" Seconds Left");
                    }
                    public void onFinish() {
                        start.setText("Restart");
//                        timer.setText("Done");
                        sendNoti();
                    }
                }.start();
            }
        });

//        }
//        new Handler().postDelayed(new Runnable(){
//            public void run(){
//                start.setText("Bitti");
////                        timer.setText("Done");
//                sendNoti();
//
//            }
//        }, 5000);
    }

    public static void group(Context context) {
        String title = "This is inbox title";
        String text = "This is inbox message";

        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo_noti);

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClass(context, DetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        int requestCode = (int) SystemClock.uptimeMillis();
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setLargeIcon(largeIcon)
                .setSmallIcon(R.drawable.ic_logo_noti)
                .setTicker(context.getString(R.string.app_name))
                .setWhen(System.currentTimeMillis())
                .setContentTitle(title)
                .setContentText(text)
                .setGroup("smile")
                .setGroupSummary(true)
                .setContentIntent(pendingIntent);


        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_FOR_GROUP, builder.build());

        for (int i = 1; i <= 10; i++) {
            NotificationCompat.Builder noti = new NotificationCompat.Builder(context);
            noti.setSmallIcon(R.drawable.ic_logo_noti)
                    .setContentTitle("Smile" + i)
                    .setContentText("This is " + i + " group message")
                    .setGroup("smile")
                    .setLargeIcon(largeIcon);
            notificationManager.notify(ID_FOR_GROUP + i, noti.build());
        }
    }

    public void sendNoti() {
//        String title = "This is inbox title";
//        String text = "This is inbox message";
//
//        Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_logo_noti);
//
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setClass(context, DetailActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//
//        int requestCode = (int) SystemClock.uptimeMillis();
//        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
//        builder.setLargeIcon(largeIcon)
//                .setSmallIcon(R.drawable.ic_logo_noti)
//                .setTicker(context.getString(R.string.app_name))
//                .setWhen(System.currentTimeMillis())
//                .setContentTitle(title)
//                .setContentText(text)
//                .setGroup("smile")
//                .setGroupSummary(true)
//                .setContentIntent(pendingIntent);
//
//
//        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(ID_FOR_GROUP, builder.build());
//
//        for (int i = 1; i <= 10; i++) {
//            NotificationCompat.Builder noti = new NotificationCompat.Builder(context);
//            noti.setSmallIcon(R.drawable.ic_logo_noti)
//                    .setContentTitle("Smile" + i)
//                    .setContentText("This is " + i + " group message")
//                    .setGroup("smile")
//                    .setLargeIcon(largeIcon);
//            notificationManager.notify(ID_FOR_GROUP + i, noti.build());
//        }
        int tag = 12345;
        NotificationCompat.Builder notif = new NotificationCompat.Builder(this);
        notif.setAutoCancel(true);
        notif.setSmallIcon(R.drawable.ic_logo_noti);
        notif.setWhen(System.currentTimeMillis());
        notif.setContentTitle("HATIRLATICI");
        notif.setContentText(Ilac.getText().toString() + " " + "İlacını Alma Vakti!!!");
        Intent intent1 = new Intent(this,DetailActivity.class);
        PendingIntent pend = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        notif.setContentIntent(pend);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        nm.notify(tag, notif.build());
        notif.setAutoCancel(true);

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
