package ir.etelli.kids.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class DataBase extends SQLiteOpenHelper {
    static final String DB_NAME = "kidsLock.db";
    static final String DB_NAME_JOURNAL = "kidsLock.db-journal";
    static SQLiteDatabase.CursorFactory DB_FACTORY = null;
    static int DB_VERSION = 4;   // لازم است ورژن دیتابیس را در دیتابیس تغییر داد تا با این شماره یکی باشد
    Context context;
    String DB_PATH;
    String DB_PATH_JURNAL;
    SQLiteDatabase db;


    public DataBase(Context context) {
        super(context, DB_NAME, DB_FACTORY, DB_VERSION);
        this.context = context;
        DB_PATH = context.getFilesDir().getAbsolutePath() + "/" + DB_NAME;
        DB_PATH_JURNAL = context.getFilesDir().getAbsolutePath() + "/" + DB_NAME_JOURNAL;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
    }

    public void copyDB() {
        File f = new File(DB_PATH);
        if (!f.exists()) {
            Toast.makeText(context, "در حال کپی کردن پایگاه داده...", Toast.LENGTH_SHORT).show();
            try {
                InputStream databaseFromAssets = context.getAssets().open(DB_NAME);
                FileOutputStream writeDbAssetsToMemory = new FileOutputStream(DB_PATH);
                byte[] bufer = new byte[1024];
                int lenght;
                while ((lenght = databaseFromAssets.read(bufer)) > 0) {
                    writeDbAssetsToMemory.write(bufer, 0, lenght);
                }
                databaseFromAssets.close();
                writeDbAssetsToMemory.flush();
                writeDbAssetsToMemory.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openDB() {
        File f = new File(DB_PATH);
        if (f.exists()) {
            File temp = new File(DB_PATH);
            db = SQLiteDatabase.openDatabase(
                    temp.getAbsolutePath()
                    , DB_FACTORY, SQLiteDatabase.OPEN_READWRITE);

            int db_version = db.getVersion();
            Log.i("sqLiteDatabase", DB_VERSION + " getVersion " + db_version);


            if (DB_VERSION > db_version) {
//                Log.i("sqLiteDatabase", DB_VERSION + " getVersion "
//                        + db_version + " : "
//                        + temp.getAbsolutePath() + " delete : " + temp.delete());

                File fj = new File(DB_PATH_JURNAL);
                if (fj.exists()) {
                    boolean deleteJournal = fj.delete();
                }

                boolean deleteFile = temp.delete();
                if (deleteFile) {
                    Log.i("sqLiteDatabase", DB_VERSION + " > " + db_version + " : " +
                            "در حال حذف دیتابیس قدیمی، کلیه تنظیمات پاک می شوند");
                    copyDB();
                    openDB();
                }

//                SharedPreferences sharedPreferences =
//                        context.getSharedPreferences("startService", MODE_PRIVATE);
//                @SuppressLint("CommitPrefEdits")
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("service", false);
//                editor.commit();


            }
        } else {
            copyDB();
            openDB();
        }
    }

    public void writeAllAppListLabel(ArrayList<String> appNameList) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < appNameList.size(); i++) {
            cv.put("appLabel", appNameList.get(i));
            if (db.update("appsInfo", cv, "appLabel = ?", new String[]{appNameList.get(i)}) == 0)
                db.insert("appsInfo", null, cv);

        }
    }

    public void writeAllAppIcon(ArrayList<Drawable> appIconList) {
        ContentValues cv = new ContentValues();
        for (int i = 0; i < appIconList.size(); i++) {
            String icon = String.valueOf(appIconList.get(i));
            cv.put("appIcon", icon);
            if (db.update("appsInfo", cv, "appIcon = ?", new String[]{icon}) == 0)
                db.insert("appsInfo", null, cv);
        }
    }

    public void writeAppLabel(String appLabel) {
        ContentValues cv = new ContentValues();
        cv.put("appLabel", appLabel);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }
    }


    public void writeAppPackageName(String packageName) {
        ContentValues cv = new ContentValues();
        cv.put("packageName", packageName);

        if (db.update("appsInfo", cv, "packageName =?", new String[]{packageName}) == 0) {
            db.insert("appsInfo", null, cv);
//            Log.i("appLabel", appLabel);
        }
//        Log.i("appLabel3", appLabel);
    }

    public void writeAppPackageName(String appLabel, String pkName) {
        ContentValues cv = new ContentValues();
        cv.put("packageName", pkName);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
//            Log.i("appLabel", appLabel + ":" + pkName);
        }
//        Log.i("appLabel4", appLabel + ":" + pkName);
    }


    public void writeAppPackageLabel(String packageName, String appLabel) {
        ContentValues cv = new ContentValues();
        cv.put("appLabel", appLabel);

        if (db.update("appsInfo", cv, "packageName =?", new String[]{packageName}) == 0) {
            db.insert("appsInfo", null, cv);
//            Log.i("appLabel", appLabel + ":" + pkName);
        }
//        Log.i("appLabel4", appLabel + ":" + pkName);
    }

    public void writeAppIsLock(String appLabel, String isLock) {
        ContentValues cv = new ContentValues();

        cv.put("isLock", isLock);

//        Log.i("readAppIsLockZ"," "+ isLock);
        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }


    public void writeAppIsLockPackage(String packageName, String isLock) {
        ContentValues cv = new ContentValues();

        cv.put("isLock", isLock);

        if (db.update("appsInfo", cv, "packageName =?", new String[]{packageName}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }

    public void writeAppAuthTimeText(String appLabel, String authTimeText) {
        ContentValues cv = new ContentValues();
        cv.put("authTimeText", authTimeText);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }

    public void writeAppStartTimeText(String appLabel, String startTimeText) {
        ContentValues cv = new ContentValues();
        cv.put("startTimeText", startTimeText);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }

    public void writeAppAuthTime(String appLabel, long authTimeMilliSecond) {
        ContentValues cv = new ContentValues();
        cv.put("authTimeMilliSecond", authTimeMilliSecond);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }

    public void writeAppStartTime(String appLabel, long startTimeMilliSecond) {
        ContentValues cv = new ContentValues();
        cv.put("startTimeMilliSecond", startTimeMilliSecond);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }

    public void writeAppStartTime(String appLabel, int startTimeHour, int startTimeMinute) {
        ContentValues cv = new ContentValues();
        cv.put("startTimeHour", startTimeHour);
        cv.put("startTimeMinute", startTimeMinute);

//        Log.i("TimeFromHourMinute", "" +
//                System.currentTimeMillis() + ":" +
//                (appLabel) + ":" +
//                startTimeHour + ":" +
//                startTimeMinute);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }

    }

    public void writeAllAppLockStateT(int isAllAppLock) {
        ContentValues cv = new ContentValues();
        cv.put("isAllAppLock", isAllAppLock);

        if (db.update("appsInfo", cv, null, null) == 0) {
            db.insert("appsInfo", null, cv);
        }
    }

    public void writeAppUsedTime(String appLabel, int usedTime) {
        ContentValues cv = new ContentValues();
        cv.put("usedTime", usedTime);

        if (db.update("appsInfo", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("appsInfo", null, cv);
        }
    }


    public void writeAppUsedTimePackage(String packageName, int usedTime) {
        ContentValues cv = new ContentValues();
        cv.put("usedTime", usedTime);

        if (db.update("appsInfo", cv, "packageName =?", new String[]{packageName}) == 0) {
            db.insert("appsInfo", null, cv);
        }
    }

    public void writeAppUsedTimeLog(String Year, String Month, String Day,
                                    String appLabel, String usedTime,
                                    int dayIndex) {

        ContentValues cv = new ContentValues();
        cv.put("Year", Year);
        cv.put("Month", Month);
        cv.put("Day", Day);
        cv.put("usedTime", usedTime);
        cv.put("appLabel", appLabel);
        cv.put("dayIndex", dayIndex);

//        Log.i("Calendar2", appLabel + " - " + usedTime + " - " + Year + "/" + Month + ":" + Day);
        db.insert("appsLog", null, cv);

        ContentValues cv2 = new ContentValues();
        String dayIndexString = String.valueOf(dayIndex);
        cv2.put("indexes", dayIndexString);
        cv2.put("Year", Year);
        cv2.put("Month", Month);
        cv2.put("Day", Day);


        if (db.update("indexTable", cv2, "indexes=?", new String[]{String.valueOf(dayIndex)}) == 0) {
            db.insert("indexTable", null, cv2);
        }

    }

    public void writeAppUsedTimeLog(String appLabel, int usedTime, int dayIndex) {
        Calendar calendar = Calendar.getInstance();
        int Year = calendar.get(Calendar.YEAR);
        int Month = calendar.get(Calendar.MONTH);
        int Day = calendar.get(Calendar.DAY_OF_MONTH);

        ContentValues cv = new ContentValues();
        cv.put("Year", Year);
        cv.put("Month", Month);
        cv.put("Day", Day);
        cv.put("usedTime", usedTime);
        cv.put("appLabel", appLabel);
        cv.put("dayIndex", dayIndex);

//        Log.i("Calendar2", appLabel + " - " + usedTime + " - " + Year + "/" + Month + ":" + Day);
        db.insert("appsLog", null, cv);

        ContentValues cv2 = new ContentValues();
        String dayIndexString = String.valueOf(dayIndex);
        cv2.put("indexes", dayIndexString);
        cv2.put("Year", Year);
        cv2.put("Month", Month);
        cv2.put("Day", Day);


        if (db.update("indexTable", cv2, "indexes=?", new String[]{String.valueOf(dayIndex)}) == 0) {
            db.insert("indexTable", null, cv2);
        }


    }


    public void writeAppAlwaysLockPackageLabel(String packageName, String appLabel) {
        ContentValues cv = new ContentValues();
        cv.put("appLabel", appLabel);

        if (db.update("alwaysLock", cv, "packageName =?", new String[]{packageName}) == 0) {
            db.insert("alwaysLock", null, cv);
//            Log.i("appLabel", appLabel + ":" + pkName);
        }
//        Log.i("appLabel4", appLabel + ":" + pkName);
    }

    public void writeAppAlwaysLockIsLock(String appLabel, String isLock) {
        ContentValues cv = new ContentValues();

        cv.put("isLock", isLock);

//        Log.i("readAppIsLockZ"," "+ isLock);
        if (db.update("alwaysLock", cv, "appLabel =?", new String[]{appLabel}) == 0) {
            db.insert("alwaysLock", null, cv);
        }

    }


    public void writeAppSetting(String appLabel, String value, int i) {
        ContentValues cv = new ContentValues();

        cv.put(appLabel, value);

        Log.i("readAppIsLockZ", appLabel + " " + value + " " + i);
        if (db.update("settings", cv, "stringID =?", new String[]{"1"}) == 0) {
            db.insert("settings", null, cv);
        }

    }


    public String readAppSetting(String appLabel) {
        String query = "SELECT " + appLabel + " FROM settings";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        Log.i("readAppIsLockZR", appLabel);

        String temp = "NoAPP";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }


    public String readAppAlwaysLockIsLock(String appLabel) {
        String query = "SELECT isLock FROM alwaysLock WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        String temp = "NoAPP";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }


    public int readLatestDayIndexCountLog() {
        String query = "SELECT dayIndex FROM appsLog";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToLast();

        if (cursor.getCount() > 0)
            return cursor.getInt(0);
        return -1;
    }

    public Cursor readAllDayIndexesLog() {
        String query = "SELECT * FROM indexTable";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

    public int readAppCountLog() {
        String query = "SELECT * FROM appsLog";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor.getCount();
    }

    public Cursor readAppDataFromDateLog(String Year, String Month, String Day) {
//        String query = "SELECT * FROM appsLog WHERE (Year =? AND Month =? AND Day =?)";
//        Cursor cursor = db.rawQuery(query,new String[]{Year,Month,Day});
        String query = "SELECT * FROM appsLog " +
                "WHERE " +
                "(Year='" + Year + "' AND " +
                "Month='" + Month + "' AND " +
                "Day='" + Day + "')";//AND Month =? AND Day =?)";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

    public Cursor readAppDataFromLogByIndex(int dayIndex) {
        String query = "SELECT * FROM appsLog WHERE dayIndex= '" + dayIndex + "' ORDER BY dayIndex DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

    public Cursor readAppDataFromLog() {
        String query = "SELECT * FROM appsLog";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;
    }

    public Cursor readAppAllDayFromLog() {
        String query = "SELECT usedTime FROM appsLog ORDER BY Day DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;

    }

    public Cursor readAppLabelListFromLog() {
        String query = "SELECT appLabel FROM appsLog ORDER BY usedTime DESC";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        return cursor;

    }

    public int readAppUsedTimeFromLogByIndex(String appLabel, int dayIndex) {
        String query = "SELECT usedTime FROM appsLog WHERE (appLabel ='" + appLabel + "' AND dayIndex='" + dayIndex + "')";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            return cursor.getInt(0);
        }
        return -1;
    }

    public int readAppUsedTime(String appLabel) {
        String query = "SELECT usedTime FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int temp = 0;
        if (cursor.getCount() > 0) {
            temp = cursor.getInt(0);
            cursor.close();
        }
        return temp;
    }


    public int readAppUsedTimePackage(String packageName) {
        String query = "SELECT usedTime FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int temp = 0;
        if (cursor.getCount() > 0) {
            temp = cursor.getInt(0);
            cursor.close();
        }
        return temp;
    }

    public boolean appExistInDB(String appLabel) {
        String query = "SELECT appLabel FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        boolean temp = cursor.getCount() > 0;
        cursor.close();

        return temp;
    }

    public boolean appPackageNameExistInDB(String packageName) {
        String query = "SELECT appLabel FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        boolean temp = cursor.getCount() > 0;
        cursor.close();
        return temp;
    }

    public int readAllAppLockState() {
        String query = "SELECT isAllAppLock FROM appsInfo";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int temp = cursor.getInt(0);

        cursor.close();
        return temp;
    }

    public int readAppCount() {
        String query = "SELECT * FROM appsInfo";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int temp = cursor.getCount();
        cursor.close();

        return temp;
    }

    public String readAppLabel(int id) {
        String query = "SELECT appLabel FROM appsInfo WHERE ID ='" + id + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        String temp = "";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }

        return temp;
    }

    public String readAppIcon(String appLabel) {
        String query = "SELECT appIcon FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return cursor.getString(0);
        return "";
    }

    public String readAppPackageName(String appLabel) {
        String query = "SELECT packageName FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        String temp = "";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }


    public String readAppLabelFromPackageName(String packageName) {
        String query = "SELECT appLabel FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        String temp = "";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }

    public String readAppIsLock(String appLabel) {
        String query = "SELECT isLock FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        String temp = "NoAPP";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }


    public String readAppIsLockPackage(String packageName) {
        String query = "SELECT isLock FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        String temp = "NoAPP";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }


    public String readAppAuthTextTime(String appLabel) {
        String query = "SELECT authTimeText FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        String temp = "";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }

    public String readAppStartTextTime(String appLabel) {
        String query = "SELECT startTimeText FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        String temp = "NoAPP";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }

    public long readAppAuthMilliSecondTime(String appLabel) {
        String query = "SELECT authTimeMilliSecond FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        long temp = -1;
        if (cursor.getCount() > 0) {
            temp = cursor.getLong(0);
            cursor.close();
        }
        return temp;
    }


    public long readAppAuthMilliSecondTimePackage(String packageName) {
        String query = "SELECT authTimeMilliSecond FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        long temp = -1;
        if (cursor.getCount() > 0) {
            temp = cursor.getLong(0);
            cursor.close();
        }
        return temp;
    }

    public long readAppStartMilliSecondTime(String appLabel) {
        String query = "SELECT startTimeMilliSecond FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        long temp = -1;
        if (cursor.getCount() > 0) {
            temp = cursor.getLong(0);
            cursor.close();
        }
        return temp;
    }

    public int readAppStartTimeHour(String appLabel) {
        String query = "SELECT startTimeHour FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);

        int hour = 0;
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            hour = cursor.getInt(0);

        cursor.close();

        return hour;
    }


    public int readAppStartTimeHourPackage(String packageName) {
        String query = "SELECT startTimeHour FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);

        int hour = 0;
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            hour = cursor.getInt(0);

        cursor.close();

        return hour;
    }

    public int readAppStartTimeMinute(String appLabel) {
        String query = "SELECT startTimeMinute FROM appsInfo WHERE appLabel ='" + appLabel + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
            return cursor.getInt(0);
        return -1;
    }


    public int readAppStartTimeMinutePackage(String packageName) {
        String query = "SELECT startTimeMinute FROM appsInfo WHERE packageName ='" + packageName + "'";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int temp = -1;
        if (cursor.getCount() > 0) {
            temp = cursor.getInt(0);
            cursor.close();
        }
        return temp;
    }

    public ArrayList<String> readAllAppListLabel() {
        ArrayList<String> appList = new ArrayList<>();
        String query = "SELECT appLabel FROM appsInfo ";
        Cursor cursor = db.rawQuery(query, null);

        cursor.moveToFirst();
        do {
            appList.add(cursor.getString(0));
        } while (cursor.moveToNext());

        return appList;
    }

    public ArrayList<String> searchAppListLabel(String appLabel, int filter, String sortType) {
        if (appLabel == null) {
            appLabel = "";
        }

        String[] a = new String[1];
        Cursor cursor;
        ArrayList<String> appList = new ArrayList<>();
        String query = "SELECT appLabel FROM appsInfo";
        if (filter == 0) {
            if (appLabel.equals("")) {
                query = "SELECT appLabel FROM appsInfo  ORDER BY appLabel " + sortType;//" ORDER BY appLabel DESC";
            } else {
                query = "SELECT appLabel FROM appsInfo WHERE appLabel like  '%" + appLabel + "%'  ORDER BY appLabel " + sortType;
            }
        }

        if (filter == 1) {
            if (appLabel.equals(""))
                query = "SELECT appLabel FROM appsInfo WHERE isLock='YES'  ORDER BY appLabel " + sortType;
            else
                query = "SELECT appLabel FROM appsInfo WHERE (isLock='YES' AND appLabel LIKE '%" + appLabel + "%')  ORDER BY appLabel " + sortType;

        }


        if (filter == 2) {
            if (appLabel.equals(""))
                query = "SELECT appLabel FROM appsInfo WHERE (isLock='NO' OR startTimeMilliSecond>0 OR authTimeMilliSecond>0)  ORDER BY appLabel " + sortType;
            else
                query = "SELECT appLabel FROM appsInfo WHERE ((isLock='NO' OR startTimeMilliSecond>0 OR authTimeMilliSecond>0) AND appLabel LIKE '%" + appLabel + "%')  ORDER BY appLabel " + sortType;

        }

        if (filter == 3) {
            if (appLabel.equals(""))
                query = "SELECT appLabel FROM appsInfo WHERE CAST(usedTime as int) > 0 ORDER BY usedTime " + sortType;
            else
                query = "SELECT appLabel FROM appsInfo WHERE (appLabel LIKE '%" + appLabel + "%' AND usedTime > 0) ORDER BY usedTime " + sortType;
        }

//        if(filter==3){
//            if(appLabel.equals(""))
//                query = "SELECT appLabel FROM appsInfo WHERE (isLock='NO' OR (usedTime < authTimeMilliSecond)) ORDER BY appLabel " +  sortType;//"  ORDER BY isLock " +  sortType;
//            else
//                query = "SELECT appLabel FROM appsInfo WHERE ((isLock='NO' OR (usedTime < authTimeMilliSecond)) AND appLabel LIKE '%" + appLabel + "%' )  ORDER BY isLock " +  sortType;
//
//        }


        cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        do {

            if (cursor.getCount() > 0) {
                String st = cursor.getString(0);
                appList.add(st);
            }
        } while (cursor.moveToNext());
        return appList;
    }

    public String readPhoneAuthTim() {

        String query = "SELECT authPhoneTime FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
//        Log.i("Cursor3indexTable", " : " + cursor.getCount());

//        String temp = "24H:60m";
        String temp = "تنظیم نشده";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }

    public int readPhoneAuthTimMilliSecond() {

        String query = "SELECT authPhoneTimeMillisecond FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
//        Log.i("Cursor3indexTable", " : " + cursor.getCount());

        int time = 0;
        if (cursor.getCount() > 0)
            time = cursor.getInt(0);

        cursor.close();
        return time;
    }

    public int readPhoneUsedTim() {

        String query = "SELECT usedTimePhone FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int temp = 0;
        if (cursor.getCount() > 0) {
            temp = cursor.getInt(0);
            cursor.close();
        }
        return temp;
    }

    public void writePhoneUsedTime(int usedTimePhone) {

        int ID = 1;
        ContentValues cv = new ContentValues();
        cv.put("ID", "1");
        cv.put("usedTimePhone", usedTimePhone);

        if (db.update("phoneTable", cv, "ID=?",
                new String[]{String.valueOf(ID)}) == 0) {
            db.insert("phoneTable", null, cv);
//            Log.i("Cursor3indexTableUsedI", "(" + usedTimePhone + ")");
        }
//        Log.i("Cursor3indexTableUsedU", "(" + usedTimePhone + ")");
    }

    public void writePhoneAuthTime(String timeText, long timeMilliSecond) {
        String authPhoneTime = readPhoneAuthTim();

        int ID = 1;
        ContentValues cv = new ContentValues();
        cv.put("ID", 1);
        cv.put("authPhoneTime", timeText);
        cv.put("authPhoneTimeMillisecond", timeMilliSecond);

        if (db.update("phoneTable", cv, "ID=?",
                new String[]{String.valueOf(ID)}) == 0) {
            db.insert("phoneTable", null, cv);
        }
//        Log.i("Cursor3indexTable", " (" + authPhoneTime + ") (" + timeText + ")" + timeMilliSecond);
    }

    public String readPhoneStartTim() {

        String query = "SELECT startPhoneTime FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
//        Log.i("Cursor3indexTable", " : " + cursor.getCount());

//        String temp = "24H:60m";
        String temp = "تنظیم نشده";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }


    public String readPhoneEndTim() {

        String query = "SELECT endPhoneTime FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
//        Log.i("Cursor3indexTable", " : " + cursor.getCount());

//        String temp = "24H:60m";
        String temp = "تنظیم نشده";
        if (cursor.getCount() > 0) {
            temp = cursor.getString(0);
            cursor.close();
        }
        return temp;
    }

    public long readPhoneStartTimeMilliSecond() {
        String query = "SELECT startPhoneTimeMillisecond FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getLong(0);
        }
        return 0;
    }

    public int readPhoneStartTimeHour() {
        String query = "SELECT startPhoneHour FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        int temp = 0;
        if (cursor.getCount() > 0) {
            temp = cursor.getInt(0);
            cursor.close();
        }
        return temp;
    }

    public int readPhoneStartTimeMinute() {
        String query = "SELECT startPhoneMinute FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int minute = 0;
        if (cursor.getCount() > 0) {
            minute = cursor.getInt(0);
            cursor.close();
        }
        return minute;
    }


    public int readPhoneEndTimeHour() {
        String query = "SELECT endPhoneHour FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();


        int temp = 0;
        if (cursor.getCount() > 0) {
            temp = cursor.getInt(0);
            cursor.close();
        }
        return temp;
    }

    public int readPhoneEndTimeMinute() {
        String query = "SELECT endPhoneMinute FROM phoneTable WHERE ID = 1";
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int minute = 0;
        if (cursor.getCount() > 0) {
            minute = cursor.getInt(0);
            cursor.close();
        }
        return minute;
    }

    public void writePhoneStartTime(String timeText, long timeMilliSecond) {

        int ID = 1;
        ContentValues cv = new ContentValues();
        cv.put("ID", 1);
        cv.put("startPhoneTime", timeText);
        cv.put("startPhoneTimeMillisecond", timeMilliSecond);

        if (db.update(
                "phoneTable",
                cv,
                "ID=?",
                new String[]{String.valueOf(ID)}) == 0) {
            db.insert("phoneTable", null, cv);
        }
    }

    public void writePhoneStartTime(String timeText, int startPhoneHour, int startPhoneMinute) {

        int ID = 1;
        ContentValues cv = new ContentValues();
        cv.put("ID", 1);
        cv.put("startPhoneTime", timeText);
        cv.put("startPhoneHour", startPhoneHour);
        cv.put("startPhoneMinute", startPhoneMinute);

        if (db.update(
                "phoneTable",
                cv,
                "ID=?",
                new String[]{String.valueOf(ID)}) == 0) {
            db.insert("phoneTable", null, cv);
        }
    }


    public void writePhoneEndTime(String timeText, long timeMilliSecond) {

        int ID = 1;
        ContentValues cv = new ContentValues();
        cv.put("ID", 1);
        cv.put("endPhoneTime", timeText);
        cv.put("endPhoneTimeMillisecond", timeMilliSecond);

        if (db.update(
                "phoneTable",
                cv,
                "ID=?",
                new String[]{String.valueOf(ID)}) == 0) {
            db.insert("phoneTable", null, cv);
        }
    }

    public void writePhoneEndTime(String timeText, int startPhoneHour, int startPhoneMinute) {

        int ID = 1;
        ContentValues cv = new ContentValues();
        cv.put("ID", 1);
        cv.put("endPhoneTime", timeText);
        cv.put("endPhoneHour", startPhoneHour);
        cv.put("endPhoneMinute", startPhoneMinute);

        if (db.update(
                "phoneTable",
                cv,
                "ID=?",
                new String[]{String.valueOf(ID)}) == 0) {
            db.insert("phoneTable", null, cv);
        }
    }


}
