package ir.etelli.kids.Service;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Calendar;

import ir.etelli.kids.Activity.ActivityPhoneSettingsRestricted;
import ir.etelli.kids.BroadCast.UsageBroadCast;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.Fragment.FragmentSetting;
import ir.etelli.kids.MainActivity;
import ir.etelli.kids.R;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.Utils.Utils;

public class LockLogService extends Service {

    Calendar calendarStart;
    Calendar calendarEnd;
//    boolean lockAll;

    SharedPreferences sharedPreferences;
    Handler handlerLock, handlerLog;

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate() {
        super.onCreate();


//                getSharedPreferences("startService", MODE_PRIVATE | MODE_MULTI_PROCESS);


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("NOT_YET_IMPLEMENTED");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        handlerLock = new Handler();
//        handlerLog = new Handler();
        lockServiceRunnable.run();
//        logServiceRunnable.run();
//        Log.i("ACTION_SCREEN333", "onStartCommand333");

        return START_STICKY;
    }


    Runnable lockServiceRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Utils.insteadOfUsageBroadCastLock(getBaseContext());
//                Intent i = new Intent(getApplicationContext(), UsageBroadCast.class);
//                i.putExtra("requestCode",Constant.alarmLockServiceRunnable);
//                getApplicationContext().sendBroadcast(i);
//                Utils.startAlarmLockService(getApplicationContext(), Constant.popupTimeDefault);
//                Log.i("getPackageNameL","getPackageName333" + Constant.popupTime);
            } catch (Exception ignored) {
            } finally {
                handlerLock.postDelayed(this, Constant.popupTime * 500L);
            }
        }
    };


//    Runnable logServiceRunnable = new Runnable() {
//        @Override
//        public void run() {
//            try {
//                Log.i("packageNameG", "packageName     + context.getPackageManager().getPackageInstaller().getAllSessions().get(0).getInstallerPackageName()");
//
//                Utils.insteadOfUsageBroadCastLog(getBaseContext());
////                Intent i = new Intent(getApplicationContext(), UsageBroadCast.class);
////                i.putExtra("requestCode",Constant.alarmLogServiceRunnable);
////                getApplicationContext().sendBroadcast(i);
////                Log.i("getPackageNameL","getPackageName666");
//            } catch (Exception ignored) {
//            } finally {
//                handlerLog.postDelayed(this, 60000);
//            }
//        }
//    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("onTaskRemoved23", "KidsLockService3");
        if (handlerLock != null) {
            handlerLock.removeCallbacks(lockServiceRunnable);
        }
//        if (handlerLog != null) {
//            handlerLog.removeCallbacks(logServiceRunnable);
//        }
    }


    @Override
    public void onTaskRemoved(Intent rootIntent) {

//        Context baseContext = getBaseContext();
//        baseContext.startService(new Intent(baseContext, KidsLockService.class));
//        baseContext.startService(new Intent(baseContext, LockLogService.class));
//        Utils.startAlarmLogToDBServiceStart(baseContext);


//        IntentFilter screenStateFilter = new IntentFilter();
//        screenStateFilter.setPriority(999);
//        screenStateFilter.addAction(Intent.ACTION_SCREEN_ON);
//        screenStateFilter.addAction(Intent.ACTION_SCREEN_OFF);
//        try {
//            baseContext.registerReceiver(Constant.usageBroadCast,screenStateFilter);
//        }catch (Exception e){
//            Log.i("registerReceiver","registerReceiver is ok"+e.getMessage());
//        }

//        PendingIntent pendingIntent = PendingIntent.getService(
//                getApplicationContext(),
//                123456,
//                new Intent(getBaseContext(), MainActivity.class),
//                PendingIntent.FLAG_ONE_SHOT);
//
//        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,500,pendingIntent);

        Log.i("onTaskRemoved2", rootIntent + " " + rootIntent.getAction() + " " + Build.MANUFACTURER.toLowerCase() + " " + "KidsLockService2 " + Constant.startServiceFlag + " " + Constant.accessEnabled + " " + Constant.bringTopEnabled);

//        if (rootIntent.getAction()!=null)
        {
//            String manufacture  = Build.MANUFACTURER.toLowerCase();
//            if (manufacture.equals("xiaomi"))
//            {
//                Intent i = new Intent(getBaseContext(), MainActivity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(i);
//            } else
            {
//                SharedPreferences sharedPreferences;
//                sharedPreferences =
//                        getSharedPreferences("startService", MODE_MULTI_PROCESS);

                DataBase db = new DataBase(getBaseContext());
                db.openDB();
                Utils.readSharedPreferences(db);
                Utils.startService(getBaseContext());
            }
        }


//        Log.i("onTaskRemoved3","KidsLockService3");
        super.onTaskRemoved(rootIntent);
    }
//
//    public void insteadOfUsageBroadCastLock(Context context) {
////        Utils.readSharedPreferences(sharedPreferences);
//        popupTime = sharedPreferences.getInt("popupTime", 1);
//        lockAll = sharedPreferences.getBoolean("lockAll", false);
//
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        String packageName = Utils.getTopActivityPackageNameFromLolipopOnwards(getApplicationContext());
////        if (Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_CHANGED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())
////        )
//
//
////        Log.i("packageName", packageName + " " + intent.getAction() + "  " + context.getPackageManager().getPackageInstaller().getAllSessions().get(0).getInstallerPackageName());
////
////        if (intent.getAction() != null) {
////            packageName = Utils.getTopActivityPackageNameFromLolipopOnwards(context);
//////            Toast.makeText(context, intent.getAction() + packageName, Toast.LENGTH_SHORT).show();
////            Log.i("ACTION_PACKAGE_RESTART", packageName + " " + intent.getAction());
////        }
//
//        if (packageName != null && Utils.isMyServiceRunning(context, KidsLockService.class)) {
//            db = new DataBase(context);
//            db.openDB();
//            int phoneUsedTime = db.readPhoneUsedTim();
//            Log.i("packageNameL", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
//
//            long usedTime = db.readAppUsedTimePackage(packageName.trim());//todo * 60000L;Attempt to invoke virtual method 'java.lang.String java.lang.String.trim()' on a null object reference
//
//            popupTime = sharedPreferences.getInt("popupTime", 1);
//            long currentTime = System.currentTimeMillis();
//
//
//            phoneStartTime = Utils.readPhoneStartMilliSecondTimeFromHourMinute(db);
//            phoneEndTime = Utils.readPhoneEndMilliSecondTimeFromHourMinute(db);
//            phoneAuthTime = db.readPhoneAuthTimMilliSecond();
//
//
////            if (requestCode == Constant.alarmLockServiceRunnable)
//            {
//                if (!packageName.trim().contains("ir.etelli.kids")) {
////                    Utils.readSharedPreferences(context.getSharedPreferences("startService", MODE_PRIVATE));
//
//
////                    boolean lockGooglePlay = sharedPreferences.getBoolean("lockGooglePlay", false);
////                    boolean lockFaceBook = sharedPreferences.getBoolean("lockFaceBook", false);
////                    boolean lockChrome = sharedPreferences.getBoolean("lockChrome", false);
////                    boolean lockGmail = sharedPreferences.getBoolean("lockGmail", false);
////                    boolean lockBazaar = sharedPreferences.getBoolean("lockBazaar", false);
////                    boolean lockCamera = sharedPreferences.getBoolean("lockCamera", false);
////                    boolean lockSetting = sharedPreferences.getBoolean("lockSetting", false);
////                    boolean lockCall = sharedPreferences.getBoolean("lockCall", false);
////                    boolean lockMessaging = sharedPreferences.getBoolean("lockMessaging", false);
////                    boolean lockGallery = sharedPreferences.getBoolean("lockGallery", false);
////                    boolean lockFolder = sharedPreferences.getBoolean("lockFolder", false);
////                    Log.i("getPackageName2", packageName + " " + lockCamera + " " +lockGallery + " " +lockCall + " " +lockMessaging);
//
//
////                    boolean lockGooglePlay = sharedPreferences.getBoolean("lockGooglePlay", false);
////                    boolean lockFaceBook = sharedPreferences.getBoolean("lockFaceBook", false);
////                    boolean lockChrome = sharedPreferences.getBoolean("lockChrome", false);
////                    boolean lockGmail = sharedPreferences.getBoolean("lockGmail", false);
////                    boolean lockBazaar = sharedPreferences.getBoolean("lockBazaar", false);
////                    boolean lockCamera = sharedPreferences.getBoolean("lockCamera", false);
////                    boolean lockSetting = sharedPreferences.getBoolean("lockSetting", false);
////                    boolean lockCall = sharedPreferences.getBoolean("lockCall", false);
////                    boolean lockMessaging = sharedPreferences.getBoolean("lockMessaging", false);
////                    boolean lockGallery = sharedPreferences.getBoolean("lockGallery", false);
////                    boolean lockFolder = sharedPreferences.getBoolean("lockFolder", false);
////                    Log.i("getPackageName2", packageName + " " + lockCamera + " " +lockGallery + " " +lockCall + " " +lockMessaging);
//
//                    if (
////                            (packageName.contains("com.android.vending") && db.readAppIsLockPackage("com.android.vending").equals("YES")) ||
////                            (packageName.contains("com.facebook.katana") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
////                            (packageName.contains("com.android.chrome") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
////                            (packageName.contains("com.google.android.gm") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
////                            (packageName.contains("com.farsitel.bazaar") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
//                            (packageName.contains("camera") && db.readAppIsLockPackage("com.android.camera").equals("YES")) ||
//                                    (packageName.contains("settings") && db.readAppIsLockPackage("com.android.settings").equals("YES"))// ||
////                            ((packageName.contains("dialer") || (packageName.contains("contacts"))) && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
////                            (packageName.contains("messaging") && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
////                            ((packageName.contains("gallery3d") || packageName.contains("gallery")) && db.readAppIsLockPackage(packageName.trim()).equals("YES")) ||
////                            ((packageName.contains("myfiles") || packageName.contains("hidisk") || packageName.contains("globalFileexplorer")) && db.readAppIsLockPackage(packageName.trim()).equals("YES"))
//                    ) {
//
//
//                        Utils.lockPhoneSettingsActivity(context,
//                                phoneStartTime,
//                                phoneAuthTime / 60000,
//                                phoneUsedTime);
//                        Log.i("phoneUsedTimeP2", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
//                    }
//
//
//                    if ((((currentTime < phoneStartTime) && ((db.readPhoneStartTimeHour() + db.readPhoneStartTimeMinute()) > 0)) ||
//                            ((currentTime > phoneEndTime) && ((db.readPhoneEndTimeHour() + db.readPhoneEndTimeMinute()) > 0)) ||
//                            ((phoneUsedTime >= (phoneAuthTime / 60000)) && phoneAuthTime > 0))
////                            && !packageName.trim().contains("ir.etelli.kids")
//                    ) {
//
//                        boolean lockAll = sharedPreferences.getBoolean("lockAll", false);
//                        if (lockAll) {
//                            Log.i("phoneUsedTimeF", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
//                            Utils.lockPhoneActivity(context,
//                                    phoneStartTime,
//                                    phoneEndTime,
//                                    phoneAuthTime / 60000,
//                                    phoneUsedTime);
//
////                            if (sharedPreferences.getBoolean("lockScreen", false)) {
////                                DevicePolicyManager dpm = (DevicePolicyManager)
////                                        context.getApplicationContext().
////                                                getSystemService(Context.DEVICE_POLICY_SERVICE);
////                                dpm.lockNow();
////                            }
//                        }
//                    }
//
//                    if (db.readAppIsLockPackage(packageName.trim()).equals("YES")) {
//                        long startMilliSecond = Utils.readAppStartMilliSecondTimeFromHourMinutePackage(packageName.trim(), db);
//                        long authTime = db.readAppAuthMilliSecondTimePackage(packageName.trim()) / 60000L;
//
//                        if (currentTime < (startMilliSecond - 25000) ||
//                                authTime == -1 ||
//                                usedTime >= authTime) {
//                            Utils.lockScreenActivity(context, packageName, startMilliSecond, authTime, usedTime);
//                        }
//                    }
//
//                }
//
//                Log.i("phoneUsedTimeF2", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + lockAll);
//
//                if ((((currentTime < phoneStartTime) && ((db.readPhoneStartTimeHour() + db.readPhoneStartTimeMinute()) > 0)) ||
//                        ((currentTime > phoneEndTime) && ((db.readPhoneEndTimeHour() + db.readPhoneEndTimeMinute()) > 0)) ||
//                        ((phoneUsedTime >= (phoneAuthTime / 60000)) && phoneAuthTime > 0))
//                        && !packageName.trim().contains("ir.etelli.kids")) {
//
//                    if (lockAll) {
//                        Log.i("phoneUsedTimeB", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
//                        Utils.lockPhoneActivity(context,
//                                phoneStartTime,
//                                phoneEndTime,
//                                phoneAuthTime / 60000,
//                                phoneUsedTime);
//
//                        if (sharedPreferences.getBoolean("lockScreen", false)) {
//                            DevicePolicyManager dpm = (DevicePolicyManager)
//                                    context.getApplicationContext().
//                                            getSystemService(Context.DEVICE_POLICY_SERVICE);
//                            dpm.lockNow();
//                        }
//
//                    }
//                }
//
//
//            }
//
//
//        }
//
//    }
//
//    public void insteadOfUsageBroadCastLog(Context context) {
//
//        SharedPreferences sharedPreferences;
//        sharedPreferences =
//                getSharedPreferences("startService", MODE_PRIVATE);
//        String packageName = Utils.getTopActivityPackageNameFromLolipopOnwards(context);
//
////        if (Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_ADDED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_CHANGED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_REPLACED.equals(intent.getAction()) ||
////                Intent.ACTION_PACKAGE_RESTARTED.equals(intent.getAction())
////        )
//
//
//        Log.i("packageNameG", packageName + "  " + "context.getPackageManager().getPackageInstaller().getAllSessions().get(0).getInstallerPackageName()");
////
////        if (intent.getAction() != null) {
////            packageName = Utils.getTopActivityPackageNameFromLolipopOnwards(context);
//////            Toast.makeText(context, intent.getAction() + packageName, Toast.LENGTH_SHORT).show();
////            Log.i("ACTION_PACKAGE_RESTART", packageName + " " + intent.getAction());
////        }
//
//
//        if (packageName != null && Utils.isMyServiceRunning(context, KidsLockService.class)) {
//            db = new DataBase(context);
//            db.openDB();
//            int phoneUsedTime = db.readPhoneUsedTim();
//            Log.i("phoneUsedTime", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
//
//            long usedTime = db.readAppUsedTimePackage(packageName.trim());//todo * 60000L;Attempt to invoke virtual method 'java.lang.String java.lang.String.trim()' on a null object reference
//
//            popupTime = sharedPreferences.getInt("popupTime", 1);
//            long currentTime = System.currentTimeMillis();
//
//
//            phoneStartTime = Utils.readPhoneStartMilliSecondTimeFromHourMinute(db);
//            phoneEndTime = Utils.readPhoneEndMilliSecondTimeFromHourMinute(db);
//            phoneAuthTime = db.readPhoneAuthTimMilliSecond();
//
//
////            if (requestCode == Constant.alarmLogService)
////            if (requestCode == Constant.alarmLogServiceRunnable)
//            {
//                if (!packageName.trim().contains("ir.etelli.kids"))
//                    if (!packageName.trim().contains("NoApp"))
//                        if (Constant.screen_on) {
//                            db.writePhoneUsedTime(db.readPhoneUsedTim() + 1);
//                            Log.i("phoneUsedTime3", Constant.screen_on + " " + packageName + " " + phoneUsedTime + "  " + db.readPhoneUsedTim());
//
//                        }
//
//                Log.i("getPackageName", "--------------------------getPackageName--------------------------");
//                if (db.appPackageNameExistInDB(packageName.trim())) {
//                    db.writeAppUsedTimePackage(packageName.trim(), db.readAppUsedTimePackage(packageName.trim()) + 1);
//                }
//            }
//
//        }
//
//    }
}
