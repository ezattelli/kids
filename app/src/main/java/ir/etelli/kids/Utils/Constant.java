package ir.etelli.kids.Utils;

import android.os.Build;

import java.util.ArrayList;

import ir.etelli.kids.BroadCast.UsageBroadCast;
import ir.etelli.kids.DataBase.DataBase;

public class Constant {

//    public static LockServiceRunnable lockServiceRunnable1;


    public static boolean startServiceFlag = false;

    public static boolean screen_on = true;
    public static boolean accessEnabled = false;
    public static boolean adminEnabled = false;
    public static boolean bringTopEnabled = false;
    public static boolean turnOffBatteryOptimize = false;

    public static boolean lockAll = true;
    public static boolean lockSetting = false;
    public static boolean lockCall = false;
    public static boolean lockMessaging = false;
    public static boolean lockGallery = false;
    public static boolean lockFolder = false;
    public static boolean lockCamera = false;
    public static boolean lockGooglePlay = false;
    public static boolean lockFaceBook = false;
    public static boolean lockChrome = false;
    public static boolean lockGmail = false;
    public static boolean lockBazaar = false;

    public static int touch_event = 11;
    public static int alarmLockServiceRunnable = 13;
    public static int alarmLogServiceRunnable = 14;
    public static int alarmLockService = 1;
    public static int alarmLogService = 2;
    public static int alarmLogToDBService = 3;
    public static int popupTimeDefault = 1;
    public static int popupTime = 1;

    public static long dailyLogTimeIntervalMinute = 24 * 60;

    public static int apiVersionCode = Build.VERSION_CODES.LOLLIPOP_MR1;

    public static ArrayList<String> appNameList = new ArrayList<>();
    public static ArrayList<String> appIconList = new ArrayList<>();

    public static String appLabel = "NoApp";

    public static UsageBroadCast usageBroadCast = new UsageBroadCast();
}
