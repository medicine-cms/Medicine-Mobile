package com.example.pharma;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.widget.Toast;


public class AlarmReceiver extends BroadcastReceiver{
    private static final String CHANNEL_ID = "com.example.demo_notification.channelId";
    String ilac;


    @Override
    public void onReceive(Context context, Intent intent) {


        //AlarmReceiver classı ile NotificationActivity classının uygulama başlangıcında birleştirilmesini sağlayan yapı (RunTime Binding)
        Intent notificationIntent = new Intent(context, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        //Uygulama kapalı olsa dahi bildirimin uygulama yetkilerine sahip olmasını sağlayan yapı
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Bildirim yapısının oluşturulması
        Notification.Builder builder = new Notification.Builder(context);

        //Bildirim paneli üzerinden gönderilen bildirimde gösterilecek yazılar
        try {

        DetailActivity detailActivity = new DetailActivity();
        ilac = detailActivity.getVariable();
        }
        catch (Exception ex) {

        }
        Notification notification = builder.setContentTitle("HATIRLATICI")
                .setLights(Color.RED, 5000, 5000)
                .setContentText(ilac + " " + "İlacını Alma Vakti!!!")
                .setTicker("Pharma")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_logo_noti)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .build();

        //Android versiyon kontrolü bölümü
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Android Oreo ve üzeri versiyonlar için gruplandırılmış bildirim kanalı tanımlanması
            builder.setChannelId(CHANNEL_ID);
        }

        //Bildirim servisi aktivasyonu
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { //Android Oreo ve üzeri versiyonlar için tanımlanmış kanal üzerine bildirimin yönlendirilmesi
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    NotificationManager.IMPORTANCE_HIGH
            );
            if(notificationManager != null) //--> Kullanılması gerekli değil fakat NullPointerException hatasını önlemek için kullanıldı.
                notificationManager.createNotificationChannel(channel);
        }

        //Asıl bildirimin gönderilmesi
        if(notificationManager != null) //--> Kullanılması gerekli değil fakat NullPointerException hatasını önlemek için kullanıldı.
            notificationManager.notify(0, notification);
    }
}