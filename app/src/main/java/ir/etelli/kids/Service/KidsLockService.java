package ir.etelli.kids.Service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import ir.etelli.kids.BroadCast.UsageBroadCast;
import ir.etelli.kids.Fragment.FragmentSetting;
import ir.etelli.kids.MainActivity;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.R;
import ir.etelli.kids.Utils.Utils;

public class KidsLockService extends Service {

    NotificationManager mNotifyManager;
    NotificationCompat.Builder mBuilder;
    NotificationChannel notificationChannel;
    String NOTIFICATION_CHANNEL_ID = "1";
    //    DataBase db;
    boolean startServiceFlag = false;

    int popupTime = 1;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences =
                getSharedPreferences("startService", MODE_PRIVATE);
        popupTime = sharedPreferences.getInt("popupTime", 1);

//        db = new DataBase(this);
//        db.openDB();


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("NOT_YET_IMPLEMENTED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createNotification();

        return START_STICKY;
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {

//        Utils.readSharedPreferences(getSharedPreferences("startService", MODE_PRIVATE));
//        Utils.startService(getBaseContext());
//        Log.i("onTaskRemoved2","KidsLockService2 " + Constant.startServiceFlag  + " " +  Constant.accessEnabled  + " " + Constant.bringTopEnabled  );

//        Context baseContext = getBaseContext();
//        baseContext.startService(new Intent(baseContext, KidsLockService.class));
////        baseContext.startService(new Intent(baseContext, LockLogService.class));
//        Utils.startAlarmLogToDBServiceStart(baseContext);
//
//
//        IntentFilter screenStateFilter = new IntentFilter();
//        screenStateFilter.setPriority(999);
//        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
//        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        try {
//            baseContext.registerReceiver(Constant.usageBroadCast,screenStateFilter);
//        }catch (Exception e){
//            Log.i("registerReceiver","registerReceiver is ok"+e.getMessage());
//        }

//        Intent i = new Intent(getBaseContext(), MainActivity.class);
//        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(i);
//        Log.i("onTaskRemoved3","KidsLockService3");
//        Log.i("onTaskRemoved2",rootIntent + " " + rootIntent.getAction() + " " + Build.MANUFACTURER.toLowerCase() + " " + "KidsLockService2 " + Constant.startServiceFlag + " " + Constant.accessEnabled + " " + Constant.bringTopEnabled);

        super.onTaskRemoved(rootIntent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, UsageBroadCast.class);
        int requestCode = 2;
        intent.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(this, requestCode, intent, 0);
        }


        alarmManager.cancel(pendingIntent);

        requestCode = 1;
        intent.putExtra("requestCode", requestCode);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(this, requestCode, intent, 0);
        }


        alarmManager.cancel(pendingIntent);

    }


    @SuppressLint("WrongConstant")
    void createNotification() {
        Bitmap IconLg = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);

        mNotifyManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(getApplicationContext(), (String) null);
        mBuilder.setContentTitle("محافظ کودک فعال است")
                .setContentText("همیشه فعال...")
                .setTicker("Always running...")
                .setSmallIcon(R.drawable.kidslock_logo)
                .setLargeIcon(IconLg)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000})
                .setVisibility(Notification.VISIBILITY_PUBLIC)
                .setOngoing(true)
                .setAutoCancel(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID
                    , "پیام من"
                    , NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("توضیحات کانال");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.setVibrationPattern(new long[]{1000});
            notificationChannel.enableVibration(true);
            notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            mNotifyManager.createNotificationChannel(notificationChannel);

            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            startForeground(1, mBuilder.build());
        } else {
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotifyManager.notify(1, mBuilder.build());
        }
    }


}
