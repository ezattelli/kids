package ir.etelli.kids.Utils;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;

import ir.etelli.kids.Activity.ActivityAskPermission;
import ir.etelli.kids.Activity.ActivityEndPhoneTimeAuth;
import ir.etelli.kids.Activity.ActivityEndProgramTimeAuth;
import ir.etelli.kids.Activity.ActivityPhoneSettingsRestricted;
import ir.etelli.kids.BroadCast.UsageBroadCast;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.Fragment.FragmentSetting;
import ir.etelli.kids.R;
import ir.etelli.kids.Service.KidsLockService;
import ir.etelli.kids.Service.LockLogService;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.DISPLAY_SERVICE;
import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static android.content.Context.MODE_MULTI_PROCESS;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;
import static android.content.Context.WINDOW_SERVICE;

import com.bumptech.glide.Glide;

public class Utils {


    public static void startAlarmLockService1(Context context, long second) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent1 = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLockService;
        intent1.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent1, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent1, 0);
        }

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(context, requestCode, intent1, 0);

        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + (second * 1000), pendingIntent), pendingIntent);
    }

    public static void startAlarmLogService(Context context, long minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent2 = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLogService;
        intent2.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, 0);
        }

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(context, requestCode, intent2, 0);

        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + (minute * 60000), pendingIntent), pendingIntent);
//        Log.i("appUssage","UssageBroadCast1");
    }


    public static void startAlarmLogToDBService(Context context, long minute) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent2 = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLogToDBService;
        intent2.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, 0);
        }

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(context, requestCode, intent2, 0);


        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(
                System.currentTimeMillis() + (minute * 60 * 1000)
                , pendingIntent), pendingIntent);
//        Log.i("appUssage","UssageBroadCast1");
    }


    public static void stopAlarmLogToDBService(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent2 = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLogToDBService;
        intent2.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, 0);
        }


        alarmManager.cancel(pendingIntent);
//        Log.i("appUssage","UssageBroadCast1");
    }


    public static void startAlarmLogToDBServiceStart(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent2 = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLogToDBService;
        intent2.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent2, 0);
        }

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(context, requestCode, intent2, 0);
//
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.AM_PM, Calendar.PM);
//        calendar.set(Calendar.HOUR_OF_DAY,24);
        calendar.set(Calendar.HOUR, 11);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 01);
        calendar.set(Calendar.MILLISECOND, 01);

//        Log.i("Cursor3", "calendar : " + (calendar.getTimeInMillis()));
//        Log.i("Cursor3", "System : " + (System.currentTimeMillis()));
//        Log.i("Cursor3", "time - : " + (calendar.getTimeInMillis() - System.currentTimeMillis()));

        alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(
                calendar.getTimeInMillis()
                , pendingIntent), pendingIntent);
//        Log.i("appUssage","UssageBroadCast1");
    }


    public static void stopAlarmLockService(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLockService;
        intent.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent, 0);
        }

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);

    }

    public static void stopAlarmLogService(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, UsageBroadCast.class);
        int requestCode = Constant.alarmLogService;
        intent.putExtra("requestCode", requestCode);
        PendingIntent pendingIntent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent =
                    PendingIntent.getBroadcast(context, requestCode, intent, 0);
        }

//        PendingIntent pendingIntent =
//                PendingIntent.getBroadcast(context, requestCode, intent, 0);

        alarmManager.cancel(pendingIntent);
    }


    public static void lockScreenActivity(Context context, String packageName, long phoneStartTime, long phoneAuthTime, long phoneUsedTime) {

//        Log.i("requestCode1l",packageName.trim());

//        isActivityInFront(context);
//        if (!isActivityInFront(context))
        {
            Intent i = new Intent(context, ActivityEndProgramTimeAuth.class);
            i.putExtra("packageName", packageName);
            i.putExtra("phoneStartTime", phoneStartTime);
            i.putExtra("phoneAuthTime", phoneAuthTime);
            i.putExtra("phoneUsedTime", phoneUsedTime);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            i.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP );
//            i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(i);
        }
    }


    public static void lockPhoneSettingsActivity(Context context, long phoneStartTime, long phoneAuthTime, long phoneUsedTime) {
//        Log.i("requestCode2l","packageName.trim()");
//        if (!isActivityInFront(context))
        {
//            Intent i = new Intent(context, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

//            @SuppressLint("ResourceType")
//            Dialog dialog = new Dialog(context,R.layout.activity_end_phone_time_auth);
//            dialog.show();

            Intent i2 = new Intent(context, ActivityPhoneSettingsRestricted.class);
            i2.putExtra("phoneStartTime", phoneStartTime);
            i2.putExtra("phoneAuthTime", phoneAuthTime);
            i2.putExtra("phoneUsedTime", phoneUsedTime);
            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i2.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            i2.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP );
//            i2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(i2);

        }
    }


    public static void lockPhoneActivity(Context context,
                                         long phoneStartTime,
                                         long phoneEndTime,
                                         long phoneAuthTime,
                                         long phoneUsedTime) {
//        Log.i("requestCode2l","packageName.trim()");
//        if (!isActivityInFront(context))
        {
//            Intent i = new Intent(context, MainActivity.class);
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i);

//            @SuppressLint("ResourceType")
//            Dialog dialog = new Dialog(context,R.layout.activity_end_phone_time_auth);
//            dialog.show();

            Intent i2 = new Intent(context, ActivityEndPhoneTimeAuth.class);
            i2.putExtra("phoneStartTime", phoneStartTime);
            i2.putExtra("phoneEndTime", phoneEndTime);
            i2.putExtra("phoneAuthTime", phoneAuthTime);
            i2.putExtra("phoneUsedTime", phoneUsedTime);
            i2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            i2.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//            i2.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP );
//            i2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            context.startActivity(i2);

        }
    }


    public static boolean isActivityInFront(Context context) {
        ArrayList<String> runningactivities = new ArrayList<String>();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (int i1 = 0; i1 < tasks.size(); i1++) {
            runningactivities.add(0, tasks.get(i1).topActivity.toString());
        }

//        for (int i = 0; i < runningactivities.size(); i++)
//            Log.i("isActivityInFront", i + ":" + runningactivities.get(i));

        int num = 0;
//        if (runningactivities.size() == 1)
//            num = 0;
//        if (runningactivities.get(num).contains("ComponentInfo{ir.etelli.kids/ir.etelli.kids.MainActivity}")) {
        if (runningactivities.size() >= 2) {
            if (runningactivities.get(num).contains("ir.etelli.kids")) {
                return true;
            } else {
                PowerManager powerManager = (PowerManager) context.getSystemService(POWER_SERVICE);

                if (powerManager.isScreenOn()) {
                    return false;
                }
                return false;
            }
        }
        return false;

    }


    public static String getTopActivityPackageNameFromLolipopOnwards(Context context) {
        String topPackageName = "NoAPP";
        String name = "";
//        Log.i("applicationInfo", "UssageBroadCast2" + "start");


//        UsageStatsManager usageStatsManager =
//                (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
//        ApplicationInfo applicationInfo = null;
//        String lable = "";

//        long time = System.currentTimeMillis();

//        UsageEvents usageEvents = usageStatsManager.queryEvents(time - 10000, time);
//
//        UsageEvents.Event event = new UsageEvents.Event();
//        int i = 0;
//        Log.i("getPackageName", "--------------------------------");
//        while (usageEvents.hasNextEvent()) {
//
//            if (event.getEventType() == UsageEvents.Event.MOVE_TO_FOREGROUND) {
//                Constant.appLable = (String) event.getPackageName();
//                Log.i("getPackageName1", i + " " + event.getTimeStamp() + "  " + (String) event.getPackageName() + " Type: " + event.getEventType());
//            }
//            Log.i("getPackageName2", i + " " + event.getTimeStamp() + "  " + (String) event.getPackageName() + " Type: " + event.getEventType());
//            usageEvents.getNextEvent(event);
//            i++;
//        }
//
//        Log.i("getPackageName--", Constant.appLable);
//
//
//
//
//
//        List<UsageStats> usageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,time - 1000, time);
//
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//
//        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfo =  activityManager.getRunningAppProcesses();
//
//        long lastTimeTempPrev = 0;
//        String appLableTempPrev = "";
//        for (int i2=0; i2<usageStats.size();){
////            if (usageStats.get(i2).getLastTimeUsed()>lastTimeTempPrev)
//            {
//                Constant.appLable = usageStats.get(i2).getPackageName();
//                lastTimeTempPrev = usageStats.get(i2).getLastTimeUsed();
//            }
//
//            if (Constant.appLable!=null && usageStats.get(i2).getLastTimeUsed()>0 )
////                Log.i("getPackageNameL",Constant.appLable );
//                Log.i("getPackageNameZ",i2 + " " + Constant.appLable + " Type: " + usageStats.get(i2).getLastTimeUsed());
//
//            i2++;
//        }


//        List<UsageStats> usageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,time - 1000, time);

//        String topPackageName = null;
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        UsageStatsManager mUsageStatsManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
        }
//        UsageStatsManager mUsageStatsManager = (UsageStatsManager) context.getSystemService("usagestats");
        List<UsageStats> stats = null;
        if (mUsageStatsManager != null) {
            long time = System.currentTimeMillis();
            stats = mUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 10000 * 10, time);
        }

//        String st = "";
//        for (int i=0;i<stats.size();i++){
//            if (stats.get(i).getPackageName().contains("chrome")){
//                st = stats.get(1).getPackageName();
//            }else
//                st = "---nnnnnoooo---";
//        }
//
//        Log.i("phoneUsedTime752","-------------------------------");
//        Log.i("phoneUsedTime752",st);
//        for (int i=0;i<stats.size();i++){
//        }
        if (stats != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
//            try {
//                if (!mySortedMap.isEmpty()) {
//                    Constant.appLabel = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
//                }
//            } catch (Exception e) {
//                Constant.appLabel = "NoApp";
//            }

            try {
                Log.i("phoneUsedTime752", "-------------------------------------------");
                long time = 0;
                for (UsageStats usageStats : stats) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                    if (mySortedMap.get(usageStats.getLastTimeUsed()).getLastTimeUsed() > time) {
                        time = mySortedMap.get(usageStats.getLastTimeUsed()).getLastTimeUsed();
                        Constant.appLabel = mySortedMap.get(usageStats.getLastTimeUsed()).getPackageName();
                    }
                }
                Log.i("phoneUsedTime752", " " + mySortedMap.size() + " " + Constant.appLabel);
            } catch (Exception e) {
                Constant.appLabel = "NoApp";
            }


        } else {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            Constant.appLabel = componentInfo.getPackageName();
        }

        Log.i("phoneUsedTime752", Constant.appLabel);

        return Constant.appLabel;
//        return name;
    }


    public static void startService(Context baseContext) {
        DataBase db = new DataBase(baseContext);
        db.openDB();

//        SharedPreferences sharedPreferences;
//        sharedPreferences =
//                baseContext.getSharedPreferences("startService", MODE_MULTI_PROCESS);
        Utils.readSharedPreferences(db);
//        Log.i("entranceDialogR_1", " R4111" + "  ");
        if (!Utils.isMyServiceRunning(baseContext, LockLogService.class) || !Utils.isMyServiceRunning(baseContext, KidsLockService.class)) {
//            Log.i("entranceDialogR_2", " R41222" + "  ");
            baseContext.startService(new Intent(baseContext, KidsLockService.class));
            baseContext.startService(new Intent(baseContext, LockLogService.class));
            Utils.startAlarmLogService(baseContext, 1);
            Utils.startAlarmLogToDBServiceStart(baseContext);


            IntentFilter screenStateFilter = new IntentFilter();
            screenStateFilter.setPriority(999);
            screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
            screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
            try {
                baseContext.registerReceiver(Constant.usageBroadCast, screenStateFilter);
            } catch (Exception e) {
                Log.i("registerReceiver", "registerReceiver is ok" + e.getMessage());
            }

        }
//        Toast.makeText(baseContext, "محافظ فعال است", Toast.LENGTH_SHORT).show();//todo
//        Constant.startServiceFlag = true;
        db.writeAppSetting("service", "YES", 28);
//        db.writeAppSetting("logging","YES");
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("service", true);
//        editor.commit();

//        Log.i("entranceDialogR", " R43" + "  ");

    }


    public static void stopService(Context baseContext) {
        baseContext.stopService(new Intent(baseContext, KidsLockService.class));
        baseContext.stopService(new Intent(baseContext, LockLogService.class));
        Utils.stopAlarmLogToDBService(baseContext);


//        baseContext.unregisterReceiver(Constant.usageBroadCast);
//        Toast.makeText(baseContext, "محافظ غیر فعال است", Toast.LENGTH_SHORT).show();//todo
        Constant.startServiceFlag = false;

        DataBase db = new DataBase(baseContext);
        db.openDB();
        db.writeAppSetting("service", "NO", 29);

//        SharedPreferences sharedPreferences =
//                baseContext.getSharedPreferences("startService", MODE_MULTI_PROCESS);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("service", false);
//        editor.commit();
    }


    public static void startServiceFromBoot(Context baseContext) {
        Log.i("entranceDialogR", " R12");
        SharedPreferences sharedPreferences =
                baseContext.getSharedPreferences("startService", MODE_PRIVATE);
//        Log.i("entranceDialogR", " R13" + "  " + sharedPreferences.getBoolean("service", false));
        boolean bit = Constant.startServiceFlag;

        if (bit) {

            baseContext.startService(new Intent(baseContext, KidsLockService.class));

            baseContext.startService(new Intent(baseContext, LockLogService.class));

            Utils.startAlarmLogToDBServiceStart(baseContext);


            IntentFilter screenStateFilter = new IntentFilter();
            screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
            screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);


//            UsageBroadCast usageBroadCast = new UsageBroadCast();
            baseContext.registerReceiver(new UsageBroadCast(), screenStateFilter);
//            Toast.makeText(baseContext, "محافظ فعال است", Toast.LENGTH_SHORT).show();//todo
            Constant.startServiceFlag = true;

        }


    }


    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public static void listOffInstalledPackage(Context context, DataBase db) {
        Log.i("appLable33", "packageInfo.packageName");
        Log.i("LockingForDelay", "LockingForDelay - 3");

        final PackageManager pm = context.getPackageManager();


//        db.writeAppPackageName("com.android.camera");
//        db.writeAppPackageLable("com.android.camera","camera");
//
//        db.writeAppPackageName("com.android.settings");
//        db.writeAppPackageLable("com.android.settings","settings");


        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);


        List<ResolveInfo> installedApps = context.getPackageManager()
                .queryIntentActivities(mainIntent, 0);
        Log.i("getApplicationLabel1", installedApps.size() + "  ");

        for (int i = 0; i < installedApps.size(); i++) {
            Log.i("getApplicationLabel0", installedApps.get(i).activityInfo.packageName + "  " + installedApps.get(i).activityInfo.loadLabel(pm).toString());
            String appLable = installedApps.get(i).activityInfo.loadLabel(pm).toString();
            db.writeAppPackageName(installedApps.get(i).activityInfo.packageName);
            db.writeAppPackageLabel(installedApps.get(i).activityInfo.packageName, appLable);
        }

//        db.writeAppPackageName("com.android.camera");
//        db.writeAppPackageLable("com.android.camera","دوربین");

//        db.writeAppPackageName("com.mi.android.globalFileexplorer");
//        db.writeAppPackageLable("com.mi.android.globalFileexplorer","فایلها");  //شیائومی
//
//        db.writeAppPackageName("com.huawei.hidisk");
//        db.writeAppPackageLable("com.huawei.hidisk","فایلها");  // هواوی
//
//        db.writeAppPackageName("myfiles");
//        db.writeAppPackageLable("myfiles","فایلها");  // سامسونگ
//
//
//        db.writeAppPackageName("gallery3d");
//        db.writeAppPackageLable("gallery3d","فایلها");  // هواوی
//
//        db.writeAppPackageName("gallery");
//        db.writeAppPackageLable("gallery","فایلها");  // سامسونگ
//


//        @SuppressLint("QueryPermissionsNeeded")
//        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
//
//        boolean returnValue = false;
////        Constant.appNameList = new ArrayList<>();
////        Constant.appIconList = new ArrayList<>();
//
//        db.writeAppPackageName("com.android.vending");
//        db.writeAppPackageLable("com.android.vending","گوگل پلی");
//
//        db.writeAppPackageName("com.facebook.katana");
//        db.writeAppPackageLable("com.facebook.katana","فیس بوک");
//
//        db.writeAppPackageName("com.android.chrome");
//        db.writeAppPackageLable("com.android.chrome","کروم");
//
//        db.writeAppPackageName("com.google.android.gm");
//        db.writeAppPackageLable("com.google.android.gm","جی میل");
//
//        db.writeAppPackageName("com.farsitel.bazaar");
//        db.writeAppPackageLable("com.farsitel.bazaar","کافه بازار");
//
//        db.writeAppPackageName("com.android.camera");
//        db.writeAppPackageLable("com.android.camera","دوربین");
//
//        db.writeAppPackageName("com.android.settings");
//        db.writeAppPackageLable("com.android.settings","گوگل پلی");
//
////        db.writeAppPackageName("com.google.android.dialer");
////        db.writeAppPackageLable("com.google.android.dialer","تماس"); // شیائومی
//
////        db.writeAppPackageName("com.google.android.contacts");  // بقیه برندها
////        db.writeAppPackageLable("com.google.android.contacts","تماس");
//
////        db.writeAppPackageName("com.google.android.apps.messaging");
////        db.writeAppPackageLable("com.google.android.apps.messaging","پیامک");
//
//        db.writeAppPackageName("com.mi.android.globalFileexplorer");
//        db.writeAppPackageLable("com.mi.android.globalFileexplorer","فایلها");  //شیائومی
//
//        db.writeAppPackageName("com.huawei.hidisk");
//        db.writeAppPackageLable("com.huawei.hidisk","فایلها");  // هواوی
//
//        db.writeAppPackageName("com.google.android.apps.messaging");
//        db.writeAppPackageLable("com.google.android.apps.messaging","پیامک");
//
//        int i = 0, j = 0;
//        for (ApplicationInfo packageInfo : packages) {
////            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
//                if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0)
//                {
//                String appLable = pm.getApplicationLabel(packageInfo).toString();
//
//                    db.writeAppPackageName(packageInfo.packageName.trim());
//                db.writeAppPackageLable(packageInfo.packageName.trim(), appLable);
////                db.writeAppLable(appLable.trim());
////                db.writeAppPackageName(appLable, packageInfo.packageName.trim());
//                returnValue = true;
//            }

//            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//                i++;
//                String appLable = pm.getApplicationLabel(packageInfo).toString();
//                Log.i("appLable3", appLable + " ; " + i);
//                Log.i("appLable3", packageInfo.packageName);
//            }
//        }
        Log.i("LockingForDelay", "LockingForDelay - 5");

    }


    public static void writeAppUsedTimeToDataBase(Context context, DataBase db) {
//        Log.i("states", "start");

        Calendar calendarStart;
        Calendar calendarEnd;
        int phoneUsedTime = 0;

        calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

        calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(System.currentTimeMillis());


        UsageStatsManager usageStatsManager =
                (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);


        Map<String, UsageStats> mapUsageState = usageStatsManager.queryAndAggregateUsageStats(
                calendarStart.getTimeInMillis(),
                calendarEnd.getTimeInMillis());

        phoneUsedTime = 0;
        PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String lable = "";
        int j = 1;
        for (String key : mapUsageState.keySet()) {

            int usedTime = (int) Objects.requireNonNull(mapUsageState.get(key)).getTotalTimeInForeground();

            usedTime = (usedTime / 60000);

            try {
                if (((usedTime) > 0)) {
                    applicationInfo = pm.getApplicationInfo(key, 0);
                    lable = pm.getApplicationLabel(applicationInfo).toString().trim();
                    if (db.appExistInDB(lable)) {
                        db.writeAppUsedTime(lable, usedTime);
//                        Log.i("states", lable + ":" + usedTime);
                    }
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }


//        List<UsageStats> states = usageStatsManager.queryUsageStats(
//                UsageStatsManager.INTERVAL_DAILY,
//                calendarStart.getTimeInMillis(),
//                System.currentTimeMillis());
//
//        PackageManager pm = context.getPackageManager();
//        ApplicationInfo applicationInfo = null;
//        String lable = "";
//        int appUsedTime = 0;
//        int j = 1;
//        for (int i = 0; i < states.size(); i++) {
//            try {
//                appUsedTime = (int) states.get(i).getTotalTimeInForeground() / 60000;
//                if (((appUsedTime) > 0)) {
//                    applicationInfo = pm.getApplicationInfo(states.get(i).getPackageName(), 0);
//                    lable = pm.getApplicationLabel(applicationInfo).toString().trim();
//                    if (db.appExistInDB(lable)) {
//                        db.writeAppUsedTime(lable, appUsedTime);
//                        Log.i("states", lable + ":" + appUsedTime);
//                    }
//                }
//
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        Log.i("states", "End");

    }


    public static int readPhoneUsedTotalTime(Context context) {
        int phoneUsedTime = 0;
        UsageStatsManager usageStatsManager =
                (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);


        Calendar calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(System.currentTimeMillis());


        Map<String, UsageStats> mapUsageState = usageStatsManager.queryAndAggregateUsageStats(
                calendarStart.getTimeInMillis(),
                calendarEnd.getTimeInMillis());


        PackageManager pm = context.getPackageManager();
        ApplicationInfo applicationInfo = null;
        String lable = "";
        int j = 1;
        for (String key : mapUsageState.keySet()) {

            int usedTime = (int) Objects.requireNonNull(mapUsageState.get(key)).getTotalTimeInForeground();

            usedTime = (usedTime / 60000);
            if (usedTime > 0) {
                try {
                    applicationInfo = pm.getApplicationInfo(key, 0);
                    lable = (String) pm.getApplicationLabel(applicationInfo);
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                assert applicationInfo != null;
                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {

                    phoneUsedTime = phoneUsedTime + usedTime;
//                    Log.i("phoneUsedTime", mapUsageState.size()+" : " +
//                            key + " : (" +
//                            phoneUsedTime + ") : " +
////                            Objects.requireNonNull(mapUsageState.get(key)).getPackageName() + " : " +
//                            Objects.requireNonNull(mapUsageState.get(key)).getTotalTimeInForeground() + " : " +
//                            calendarStart.getTimeInMillis()/60000 + ":"+
//                            + calendarEnd.getTimeInMillis()/60000 + ":"+
//                            (calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis())/360000);
//
//                    Log.i("phoneUsedTime",lable +":"+usedTime + ":" + j);
                    j++;
                }
            }
        }


        return phoneUsedTime;
    }

    public static long readAppStartMilliSecondTimeFromHourMinute(String packageName, DataBase db) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, db.readAppStartTimeHourPackage(packageName));
        calendar.set(Calendar.MINUTE, db.readAppStartTimeMinutePackage(packageName));
//        Log.i("TimeFromHourMinuteRead", "" + (calendar.getTimeInMillis() - System.currentTimeMillis()) + ":" +
//                calendar.getTimeInMillis() + ":" +
//                System.currentTimeMillis() + ":" +
//                db.readAppStartTimeHour(appLabl) + ":" +
//                db.readAppStartTimeMinute(appLabl));
        return calendar.getTimeInMillis();
    }


    public static long readAppStartMilliSecondTimeFromHourMinutePackage(String packageName, DataBase db) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, db.readAppStartTimeHourPackage(packageName));
        calendar.set(Calendar.MINUTE, db.readAppStartTimeMinutePackage(packageName));
//        Log.i("TimeFromHourMinuteRead", "" + (calendar.getTimeInMillis() - System.currentTimeMillis()) + ":" +
//                calendar.getTimeInMillis() + ":" +
//                System.currentTimeMillis() + ":" +
//                db.readAppStartTimeHour(appLabl) + ":" +
//                db.readAppStartTimeMinute(appLabl));
        return calendar.getTimeInMillis();
    }


    public static long readPhoneStartMilliSecondTimeFromHourMinute(DataBase db) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, db.readPhoneStartTimeHour());
        calendar.set(Calendar.MINUTE, db.readPhoneStartTimeMinute());
        return calendar.getTimeInMillis();
    }


    public static long readPhoneEndMilliSecondTimeFromHourMinute(DataBase db) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, db.readPhoneEndTimeHour());
        calendar.set(Calendar.MINUTE, db.readPhoneEndTimeMinute());
        return calendar.getTimeInMillis();
    }


    public static boolean isAppDeviceAdmin(View v) {
        DevicePolicyManager mDPM = (DevicePolicyManager) v.getContext().getSystemService(Context.DEVICE_POLICY_SERVICE);
        ComponentName mAdminName = new ComponentName(v.getContext(), DeviceAdminSample.class);

        return mDPM != null && mDPM.isAdminActive(mAdminName);
    }


    public static FragmentSetting.PermissionStatus getUsageStatsPermissionsStatus(Context context) {
        if (Build.VERSION.SDK_INT < Constant.apiVersionCode) {
            return FragmentSetting.PermissionStatus.CANNOT_BE_GRANTED;
        }
        AppOpsManager appOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        if (appOps == null) {
            return FragmentSetting.PermissionStatus.CANNOT_BE_GRANTED;
        }

        final int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, android.os.Process.myUid(), context.getPackageName());
        boolean granted = false;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            granted = mode == AppOpsManager.MODE_DEFAULT ?
                    (context.checkCallingOrSelfPermission(android.Manifest.permission.PACKAGE_USAGE_STATS)
                            == PackageManager.PERMISSION_GRANTED)
                    : (mode == AppOpsManager.MODE_ALLOWED);
        }

        return granted ? FragmentSetting.PermissionStatus.GRANTED : FragmentSetting.PermissionStatus.DENIED;
    }


    public static boolean checkDrawOverlayPermission(Context context) {
        // check if we already  have permission to draw over other apps

        Log.i("appOnTop40", " appOnTop40   " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            return Settings.canDrawOverlays(context);

        return true;
    }


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }


    public static void onTouchClick(Context context) {

        //        if (Utils.checkDrawOverlayPermission(this))
        {

//            DisplayMetrics metrics = getResources().getDisplayMetrics();
//            int width = metrics.widthPixels;
//            int height = metrics.heightPixels;

            WindowManager windowManager;
            ViewGroup floatView;
            LayoutInflater layoutInflater;
            WindowManager.LayoutParams layoutParams;

            layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            floatView = (ViewGroup) layoutInflater.inflate(R.layout.dialog_float, null);


            int LAYOUT_FLAG;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                LAYOUT_FLAG = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
            }

            layoutParams = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    LAYOUT_FLAG,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                            WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                    PixelFormat.TRANSLUCENT);


            floatView.setLayoutParams(layoutParams);

            windowManager = (WindowManager) context.getSystemService(WINDOW_SERVICE);

            floatView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
//                    Toast.makeText(v.getContext(), "لمس شد", Toast.LENGTH_SHORT).show();
//                    Utils.startAlarmLockService(context, Constant.popupTimeDefault);

//                    int requestCode = Constant.touch_event;
//                    Intent intent = new Intent(context,UssageBroadCast.class);
//                    intent.putExtra("requestCode", requestCode);
//
//                    context.sendBroadcast(intent);
                    return false;
                }
            });

            windowManager.addView(floatView, layoutParams);

        }
    }


    public static void settingsAnimateHelp(Context context, String description, String action1, String action2) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_lootie_settings);
        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);

        tvDescription.setText(description);

        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
        Glide.with(context).load(R.drawable.h_usage_data_access_light).into(imageView);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;

                intent = new Intent(action1);

                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context,
                            "لطفا تنظیمات را به صورت دستی اجرا کنید برنامه قادر به اجرای تنظیمات نیست.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();


    }


    private static void RequestPermission(View v) {
        // Check if Android M or higher


        String device = Build.MANUFACTURER;
        if (device.equals("Xiaomi")) {
            Log.i("appOnTop6", " appOnTop6");

//                        Log.i("RequestPermission", "- 2 -");
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", v.getContext().getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                Log.i("appOnTop9", " appOnTop9");
                v.getContext().startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
            }
        } else {
            Log.i("appOnTop7", " appOnTop7");
//                        Log.i("RequestPermission", "- 3 -");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + v.getContext().getPackageName()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                try {
                    Log.i("appOnTop8", " appOnTop8");
                    v.getContext().startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
                }
//                            Log.i("RequestPermission", "- 4 -");
            }
        }

    }


    public static void appOnTop(View v) {


        Display display = v.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        width = (float) (width * 0.7);
        height = (float) (height * 0.5);

        Log.i("DisplayMetrics", height + " heightPixels: " + displayMetrics.heightPixels + " " + width + " widthPixels: " + displayMetrics.widthPixels + " densityDpi: " + displayMetrics.densityDpi +
                " xdpi: " + displayMetrics.xdpi + " ydpi: " + displayMetrics.ydpi);

        Log.i("appOnTop1", " appOnTop1");
        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
        dialog.setContentView(R.layout.dialog_lootie_settings);
        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);

        DataBase db = new DataBase(v.getContext());
        db.openDB();

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);

        if (Build.MANUFACTURER.contains("Xiaomi") || Build.MANUFACTURER.contains("xiaomi")) {
            Log.i("appOnTop2", " appOnTop2");
            tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دو گزینه دسترسی را فعال نمائید");
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(480, 700);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) width, (int) height);
            ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
            imageView.setLayoutParams(layoutParams);
            if (db.readAppSetting("nightMode").equals("YES")) {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_dark_xiaomi).into(imageView);
            } else {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_light_xiaomi).into(imageView);
            }
//            if (sharedPreferences.getBoolean("nightMode", false)) {
//                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_dark_xiaomi).into(imageView);
//            } else {
//                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_light_xiaomi).into(imageView);
//            }
        } else {

            Log.i("appOnTop3", " appOnTop3");
            tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دسترسی را فعال نمائید");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(640, 140);
            ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
            imageView.setLayoutParams(layoutParams);

//            if (sharedPreferences.getBoolean("nightMode", false))
            if (db.readAppSetting("nightMode").equals("YES")) {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_dark).into(imageView);
            } else {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_light).into(imageView);
            }
        }


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("appOnTop12", " appOnTop12");

                String device = Build.MANUFACTURER;
                if (device.equals("Xiaomi")) {

                    Log.i("appOnTop4", " appOnTop4");
                    Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter",
                            "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", v.getContext().getPackageName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    try {
                        Log.i("appOnTop10", " appOnTop10");
                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.i("appOnTop5", " appOnTop5");
                    RequestPermission(v);
                }
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    public static void deviceAdminApps(View v) {

        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.dialog_lootie_settings, null);
//        dialog.setContentView(view, layoutParams);


        Display display = v.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        width = (float) (width * 0.7);
        height = (float) (height * 0.6);

        Log.i("DisplayMetrics", height + " heightPixels: " + displayMetrics.heightPixels + " " + width + " widthPixels: " + displayMetrics.widthPixels + " densityDpi: " + displayMetrics.densityDpi +
                " xdpi: " + displayMetrics.xdpi + " ydpi: " + displayMetrics.ydpi);

        dialog.setContentView(R.layout.dialog_lootie_settings);

        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvGlideTitle = dialog.findViewById(R.id.tvGlideTitle);

        tvGlideTitle.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دسترسی را فعال نمائید");

        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(480, 800);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) width, (int) height);
        imageView.setLayoutParams(layoutParams);
        DataBase db = new DataBase(v.getContext());
        db.openDB();

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//        if (sharedPreferences.getBoolean("nightMode", false))
        if (db.readAppSetting("nightMode").equals("YES")) {
            Glide.with(v.getContext()).load(R.drawable.h_device_admin_dark).into(imageView);
        } else {
            Glide.with(v.getContext()).load(R.drawable.h_device_admin_light).into(imageView);
        }
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent().setComponent(
                        new ComponentName("com.android.settings", "com.android.settings.DeviceAdminSettings"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try {
                    v.getContext().startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }


    public static void usageDataAccess(View v) {


        Display display = v.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        height = (float) (height * 0.3);
        width = (float) (width * 0.7);

        Log.i("DisplayMetrics", height + " heightPixels: " + displayMetrics.heightPixels + " " + width + " widthPixels: " + displayMetrics.widthPixels + " densityDpi: " + displayMetrics.densityDpi +
                " xdpi: " + displayMetrics.xdpi + " ydpi: " + displayMetrics.ydpi);

        //        dialog.getWindow().setLayout(640,480);
//        View view = View.inflate(v.getContext(), R.layout.dialog_lootie_settings, null);
//        dialog.setContentView(view, layoutParams);
        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
        dialog.setContentView(R.layout.dialog_lootie_settings);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) width, (int) height);
        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
        imageView.setLayoutParams(layoutParams);
        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);
        tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دسترسی را فعال نمائید");

        DataBase db = new DataBase(v.getContext());
        db.openDB();
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        if (db.readAppSetting("nightMode").equals("YES")) {
            Glide.with(v.getContext()).load(R.drawable.h_usage_data_access_dark).into(imageView);
        } else {
            Glide.with(v.getContext()).load(R.drawable.h_usage_data_access_light).into(imageView);
        }
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                String action = Settings.ACTION_USAGE_ACCESS_SETTINGS;

                intent = new Intent(action);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                try {
                    v.getContext().startActivity(intent);
                } catch (Exception e) {
                    Log.i("getDecorView", " " + e.getLocalizedMessage() + " " + e.getMessage());
                    Toast.makeText(v.getContext(),
                            "لطفا تنظیمات را به صورت دستی اجرا کنید برنامه قادر به اجرای تنظیمات نیست.",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    public static void turnOffBatteryOptimize(View v) {

        DataBase db = new DataBase(v.getContext());
        db.openDB();

        Display display = v.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        float height = displayMetrics.heightPixels;
        float width = displayMetrics.widthPixels;

        width = (float) (width * 0.7);
        height = (float) (height * 0.3);

        Log.i("DisplayMetrics", height + " heightPixels: " + displayMetrics.heightPixels + " " + width + " widthPixels: " + displayMetrics.widthPixels + " densityDpi: " + displayMetrics.densityDpi +
                " xdpi: " + displayMetrics.xdpi + " ydpi: " + displayMetrics.ydpi);

        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
        dialog.setContentView(R.layout.dialog_lootie_settings);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        View view = View.inflate(v.getContext(), R.layout.dialog_lootie_settings, null);
//        dialog.setContentView(view, layoutParams);
        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);
//        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);


        tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و بهینه سازی مصرف باطری را غیر فعال نمائید");
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(640, 340);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) width, (int) height);
        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
        imageView.setLayoutParams(layoutParams);

        if (db.readAppSetting("nightMode").equals("YES")) {
            Glide.with(v.getContext()).load(R.drawable.h_optimize_battery_dark).into(imageView);
        } else {
            Glide.with(v.getContext()).load(R.drawable.h_optimize_battery_light).into(imageView);
        }


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();

//                SharedPreferences sharedPreferences =
//                        v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//                @SuppressLint("CommitPrefEdits")
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("turnOffBatteryOptimize", true);
//                editor.commit();

                db.writeAppSetting("turnOffBatteryOptimize", "YES", 30);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.parse("package:"+v.getContext().getPackageName()));

                    try {
                        v.getContext().startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
                    }
                }
                Constant.turnOffBatteryOptimize = true;
            }
        });

        WindowManager.LayoutParams wlp = new WindowManager.LayoutParams();
        wlp.copyFrom(dialog.getWindow().getAttributes());
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wlp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();

        Log.i("getDecorView", " " + wlp.height + " " + wlp.width);
        dialog.getWindow().setAttributes(wlp);

    }


    public static void showFragmentSettingDialog(View v) {
        Log.i("showFragmentSetting3", Constant.bringTopEnabled + " " + Constant.accessEnabled);
//        if (!Constant.bringTopEnabled || !Constant.adminEnabled || !Constant.accessEnabled) {

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);

        if (Utils.getUsageStatsPermissionsStatus(v.getContext()) == FragmentSetting.PermissionStatus.GRANTED) {
            Constant.accessEnabled = true;
        } else {
            Constant.accessEnabled = false;
            if (Build.VERSION.SDK_INT <= 22) {
                Constant.accessEnabled = true;
            }
        }


        Constant.bringTopEnabled = Utils.checkDrawOverlayPermission(v.getContext());

        if (!Constant.bringTopEnabled || !Constant.accessEnabled) {

            Log.i("onStart123", "onStart---1");
            Intent intent = new Intent(v.getContext(), ActivityAskPermission.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            v.getContext().startActivity(intent);

        }
    }

    public static void readSharedPreferences(DataBase db) {

        Constant.startServiceFlag = db.readAppSetting("screen_on").equals("YES");
//        Constant.screen_on = sharedPreferences.getBoolean("screen_on", false);
        Constant.startServiceFlag = db.readAppSetting("service").equals("YES");
//        Constant.startServiceFlag = sharedPreferences.getBoolean("service", false);
        Constant.accessEnabled = db.readAppSetting("accessEnabled").equals("YES");
//        Constant.accessEnabled = sharedPreferences.getBoolean("accessEnabled", false);
        Constant.adminEnabled = db.readAppSetting("adminEnabled").equals("YES");
//        Constant.adminEnabled = sharedPreferences.getBoolean("adminEnabled", false);
        Constant.bringTopEnabled = db.readAppSetting("bringTopEnabled").equals("YES");
//        Constant.bringTopEnabled = sharedPreferences.getBoolean("bringTopEnabled", false);
        Constant.turnOffBatteryOptimize = db.readAppSetting("turnOffBatteryOptimize").equals("YES");
//        Constant.turnOffBatteryOptimize = sharedPreferences.getBoolean("turnOffBatteryOptimize", false);

        Constant.lockSetting = db.readAppAlwaysLockIsLock("Setting").equals("YES");
//        Constant.lockSetting = sharedPreferences.getBoolean("lockSetting", false);
        Constant.lockCall = db.readAppAlwaysLockIsLock("Call").equals("YES");
//        Constant.lockCall = sharedPreferences.getBoolean("lockCall", false);
        Constant.lockMessaging = db.readAppAlwaysLockIsLock("Messaging").equals("YES");
//        Constant.lockMessaging = sharedPreferences.getBoolean("lockMessaging", false);
        Constant.lockGallery = db.readAppAlwaysLockIsLock("Gallery").equals("YES");
//        Constant.lockGallery = sharedPreferences.getBoolean("lockGallery", false);
        Constant.lockFolder = db.readAppAlwaysLockIsLock("Folder").equals("YES");
//        Constant.lockFolder = sharedPreferences.getBoolean("lockFolder", false);
        Constant.lockCamera = db.readAppAlwaysLockIsLock("Camera").equals("YES");
//        Constant.lockCamera = sharedPreferences.getBoolean("lockCamera", false);
        Constant.lockGooglePlay = db.readAppAlwaysLockIsLock("GooglePlay").equals("YES");
//        Constant.lockGooglePlay = sharedPreferences.getBoolean("lockGooglePlay", false);
        Constant.lockFaceBook = db.readAppAlwaysLockIsLock("FaceBook").equals("YES");
//        Constant.lockFaceBook = sharedPreferences.getBoolean("lockFaceBook", false);
        Constant.lockChrome = db.readAppAlwaysLockIsLock("Chrome").equals("YES");
//        Constant.lockChrome = sharedPreferences.getBoolean("lockChrome", false);
        Constant.lockGmail = db.readAppAlwaysLockIsLock("Gmail").equals("YES");
//        Constant.lockGmail = sharedPreferences.getBoolean("lockGmail", false);
        Constant.lockBazaar = db.readAppAlwaysLockIsLock("Bazaar").equals("YES");
//        Constant.lockBazaar = sharedPreferences.getBoolean("lockBazaar", false);
        Constant.lockBazaar = db.readAppAlwaysLockIsLock("nightMode").equals("NO");
//        Constant.lockBazaar = sharedPreferences.getBoolean("lockBazaar", false);

    }


    public static void insteadOfUsageBroadCastLock(Context context) {

        String packageName = Utils.getTopActivityPackageNameFromLolipopOnwards(context);

        if (packageName != null) {
            DataBase db;
            db = new DataBase(context);
            db.openDB();
            long phoneStartTime = Utils.readPhoneStartMilliSecondTimeFromHourMinute(db);
            long phoneEndTime = Utils.readPhoneEndMilliSecondTimeFromHourMinute(db);
            long phoneAuthTime = db.readPhoneAuthTimMilliSecond();
            int phoneUsedTime = db.readPhoneUsedTim();
//            Log.i("packageNameL", Constant.screen_on + " " + packageName + " " + db.readAppAlwaysLockIsLock("Setting").equals("YES") +  " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
            Log.i("packageNameL", packageName + " Screen: " + Constant.screen_on + " " + Constant.startServiceFlag + " " + Build.MANUFACTURER.toLowerCase() + " " + "KidsLockService2 " + Constant.startServiceFlag + " " + Constant.accessEnabled + " " + Constant.bringTopEnabled);

            SharedPreferences sharedPreferences;
            sharedPreferences =
                    context.getSharedPreferences("startService", MODE_MULTI_PROCESS);

//            boolean lockAll = sharedPreferences.getBoolean("lockAll", false);
            boolean lockAll = db.readAppAlwaysLockIsLock("lockAll").equals("YES");
            Utils.readSharedPreferences(db);

            long usedTime = db.readAppUsedTimePackage(packageName.trim());//todo * 60000L;Attempt to invoke virtual method 'java.lang.String java.lang.String.trim()' on a null object reference

            long currentTime = System.currentTimeMillis();

            Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Setting"));
            if (!packageName.trim().contains("ir.etelli.kids")) {
                if (
//                            (packageName.contains("com.android.vending") && db.readAppIsLockPackage("com.android.vending").equals("YES")) ||
//                            (packageName.contains("com.facebook.katana") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            (packageName.contains("com.android.chrome") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            (packageName.contains("com.google.android.gm") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            (packageName.contains("com.farsitel.bazaar") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            (packageName.contains("camera") && db.readAppIsLockPackage("com.android.camera").equals("YES")) ||
//                                    (packageName.contains("settings") && db.readAppIsLockPackage("com.android.settings").equals("YES"))// ||
//                            ((packageName.contains("dialer") || (packageName.contains("contacts"))) && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            (packageName.contains("messaging") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            ((packageName.contains("gallery3d") || packageName.contains("gallery")) && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            ((packageName.contains("myfiles") || packageName.contains("hidisk") || packageName.contains("globalFileexplorer")) && db.readAppIsLockPackage(packageName.trim()).equals("YES"))
                        (packageName.contains("camera") && db.readAppAlwaysLockIsLock("Camera").equals("YES")) ||//Constant.lockCamera) ||
                                (packageName.contains("settings") && db.readAppAlwaysLockIsLock("Setting").equals("YES")) ||// Constant.lockSetting) ||
                                ((packageName.contains("gallery3d") || packageName.contains("gallery")) && db.readAppAlwaysLockIsLock("Gallery").equals("YES")) ||//Constant.lockGallery) ||
                                ((packageName.contains("myfiles") || packageName.contains("hidisk") || packageName.contains("globalFileexplorer")) && db.readAppAlwaysLockIsLock("Folder").equals("YES"))) {//Constant.lockFolder)) {
                    Utils.lockPhoneSettingsActivity(context,
                            phoneStartTime,
                            phoneAuthTime / 60000,
                            phoneUsedTime);
                    Log.i("phoneUsedTimeP2", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
                }


                if ((((currentTime < phoneStartTime) && ((db.readPhoneStartTimeHour() + db.readPhoneStartTimeMinute()) > 0)) ||
                        ((currentTime > phoneEndTime) && ((db.readPhoneEndTimeHour() + db.readPhoneEndTimeMinute()) > 0)) ||
                        ((phoneUsedTime >= (phoneAuthTime / 60000)) && phoneAuthTime > 0))) {
//                    lockAll = sharedPreferences.getBoolean("lockAll", false);
                    lockAll = db.readAppAlwaysLockIsLock("lockAll").equals("YES");
                    if (lockAll) {
                        Log.i("phoneUsedTimeFa", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
                        Utils.lockPhoneActivity(context,
                                phoneStartTime,
                                phoneEndTime,
                                phoneAuthTime / 60000,
                                phoneUsedTime);
                    }
                }


                if (db.readAppIsLockPackage(packageName.trim()).equals("YES")) {
                    long startMilliSecond = Utils.readAppStartMilliSecondTimeFromHourMinutePackage(packageName.trim(), db);
                    long authTime = db.readAppAuthMilliSecondTimePackage(packageName.trim());
                    if (authTime != -1) {
                        authTime = authTime / 60000L;
                    }
                    long startTime = db.readAppStartMilliSecondTime(db.readAppLabelFromPackageName(packageName));
                    Log.i("phoneUsedTimeFp1", Constant.screen_on + " " + " " + packageName + " " + usedTime + " " + authTime + " " + currentTime + " " + (startMilliSecond) + " " + (currentTime < (startMilliSecond - 25000)) + " " + (authTime != -1 && usedTime >= authTime) + " " + db.readAppStartTimeMinute(db.readAppLabelFromPackageName(packageName)) + " " + startTime);

                    if (currentTime < (startMilliSecond - 25000) ||
                            (authTime != -1 && usedTime >= authTime) ||
                            (authTime == -1 && startTime == -1)) {
                        Utils.lockScreenActivity(context, packageName, startMilliSecond, authTime, usedTime);
                        Log.i("phoneUsedTimeFp", Constant.screen_on + " " + " " + packageName + " " + usedTime + " " + authTime + " " + currentTime + " " + (startMilliSecond) + " " + (currentTime < (startMilliSecond - 25000)) + " " + (authTime != -1 && usedTime >= authTime));
                    }
                }


            }
            Log.i("phoneUsedTimeF2", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + lockAll);
        }
    }


    public static void insteadOfUsageBroadCastLog(Context context) {


//        SharedPreferences sharedPreferences;
//        sharedPreferences =
//                context.getSharedPreferences("startService", MODE_MULTI_PROCESS);
//        Constant.screen_on = sharedPreferences.getBoolean("screen_on", false);

        DisplayManager displayManager = (DisplayManager) context.getSystemService(DISPLAY_SERVICE);
        for (Display display : displayManager.getDisplays()) {
            if (display.getState() != Display.STATE_OFF)
                Constant.screen_on = true;
        }
        Log.i("phoneUsedTime33", "Constant.screen_on" + " " + Constant.screen_on + " ");

        if (Constant.screen_on) {
            DataBase db = new DataBase(context);
            db.openDB();
            String packageName = Utils.getTopActivityPackageNameFromLolipopOnwards(context);
            Log.i("phoneUsedTime30", Constant.screen_on + " " + packageName + "  " + db.readPhoneUsedTim() + " " + db.appPackageNameExistInDB(packageName.trim()) + " " + db.readAppUsedTimePackage(packageName.trim()));
            if (Utils.isMyServiceRunning(context, LockLogService.class)) {
                if (!packageName.trim().contains("ir.etelli.kids"))
                    if (!packageName.trim().contains("NoApp")) {
                        int phoneUsedTime = db.readPhoneUsedTim();
                        db.writePhoneUsedTime(db.readPhoneUsedTim() + 1);
                        Log.i("phoneUsedTime31", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim() + " " + db.appPackageNameExistInDB(packageName.trim()) + " " + db.readAppUsedTimePackage(packageName.trim()));

                        Log.i("getPackageName", "--------------------------getPackageName--------------------------");
                        if (db.appPackageNameExistInDB(packageName.trim())) {
                            db.writeAppUsedTimePackage(packageName.trim(), db.readAppUsedTimePackage(packageName.trim()) + 1);
                            Log.i("phoneUsedTime32", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
                        }
                    }
            }
        }

    }
}
