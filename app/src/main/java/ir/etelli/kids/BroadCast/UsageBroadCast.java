package ir.etelli.kids.BroadCast;

import static android.content.Context.MODE_MULTI_PROCESS;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import ir.etelli.kids.Service.LockLogService;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.MainActivity;
import ir.etelli.kids.Utils.Utils;

public class UsageBroadCast extends BroadcastReceiver {
    DataBase db;

    @Override
    public void onReceive(Context context, Intent intent) {
        db = new DataBase(context);
        db.openDB();


        int requestCode = 0;
        if (intent.getExtras() != null) {
            requestCode = intent.getExtras().getInt("requestCode");
        }


        if (requestCode == Constant.alarmLogService) {
            Utils.insteadOfUsageBroadCastLog(context);
            Utils.startAlarmLogService(context, 1);
        }


////////////////////////////////////////////////alarmLogToDBService/////////////////////////////////////////////

        if (requestCode == Constant.alarmLogToDBService) {
//            Log.i("Cursor3", "calendar : (calendar.getTimeInMillis())");

            for (int i = 1; i <= db.readAppCount(); i++) {
                String label = db.readAppLabel(i);
                db.writeAppUsedTime(label, 0);
            }
            db.writePhoneUsedTime(0);
            Utils.startAlarmLogToDBService(context, Constant.dailyLogTimeIntervalMinute);
        }


////////////////////////////////////////////////ACTION_SCREEN_OFF/////////////////////////////////////////////
        if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
            Constant.appLabel = "NoApp";

//            if (!Constant.startServiceFlag) {
//                Utils.stopService(context);
//            }
//            SharedPreferences sharedPreferences =
//                    context.getSharedPreferences("startService", MODE_MULTI_PROCESS);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("screen_on",false);
//            editor.commit();

            db.writeAppSetting("screen_on", "NO", 31);
            Constant.screen_on = false;
//            Log.i("phoneUsedTime", "ACTION_SCREEN_OFF2  " + Constant.screen_on);
            Log.i("ACTION_SCREEN2", " " + "ACTION_SCREEN_OFF2");
            context.stopService(new Intent(context, LockLogService.class));
        }


////////////////////////////////////////////////ACTION_SCREEN_ON/////////////////////////////////////////////
        if (Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
//            SharedPreferences sharedPreferences =
//                    context.getSharedPreferences("startService", MODE_MULTI_PROCESS);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("screen_on",true);
//            editor.commit();

            db.writeAppSetting("screen_on", "YES", 32);

            Constant.screen_on = true;
            if (Constant.startServiceFlag) {
//                Utils.startService(context);
//                Log.i("phoneUsedTime", "ACTION_SCREEN_ON1  " + Constant.screen_on);
                context.startService(new Intent(context, LockLogService.class));
            }
//            Log.i("phoneUsedTime", "ACTION_SCREEN_ON2  " + Constant.screen_on);
            Log.i("ACTION_SCREEN2", " " + "ACTION_SCREEN_ON2");
        }


////////////////////////////////////////////////ACTION_BOOT_COMPLETED/////////////////////////////////////////////
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))  // فعال کردن برنامه پس از ری استارت گوشی
        {
            Toast.makeText(context, "راه اندازی مجدد به پایان رسید", Toast.LENGTH_LONG).show();

            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(i);
        }


    }


}
