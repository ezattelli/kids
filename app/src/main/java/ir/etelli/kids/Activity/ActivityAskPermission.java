package ir.etelli.kids.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.Fragment.FragmentSetting;
import ir.etelli.kids.R;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.Utils.Utils;

public class ActivityAskPermission extends AppCompatActivity {
    LottieAnimationView btnLottieUsage, btnLottieAdmin, btnLottieBringToTop, btnLottieBatteryOptimize;
    View v;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PowerManager pm;
    MaterialButton mbtnAddToAutoStart;
    LinearLayout llAddToAutoStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_permission);


        btnLottieUsage = findViewById(R.id.lottie_btn_access);
        btnLottieAdmin = findViewById(R.id.lottie_btn_admin);
        btnLottieBringToTop = findViewById(R.id.lottie_btn_bring_top);
        btnLottieBatteryOptimize = findViewById(R.id.lottie_btn_battery_optimize);

        v = getWindow().getDecorView().getRootView();
        sharedPreferences = getSharedPreferences("startService", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        pm = (PowerManager) v.getContext().getSystemService(POWER_SERVICE);

        Dialog dialog = new Dialog(this, R.style.dialog_transparent_style_float);

        dialog.setContentView(R.layout.activity_setting_alertdialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.getWindow().setGravity(Gravity.BOTTOM);
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        layoutParams. = Gravity.BOTTOM;
//        dialog.getWindow().setAttributes(layoutParams);
        dialog.show();

//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
//        alertDialog.setTitle("دسترسی های مورد نیاز");
//        alertDialog.setMessage("قفل کودک برای عملکرد صحیح به دسترسی های اعلام شده نیاز دارد.");
//        alertDialog.setCancelable(false);
//        alertDialog.show();


//
//            if (Utils.getUsageStatsPermissionsStatus(this) == FragmentSetting.PermissionStatus.GRANTED) {
//
//                btnLottieUsage.setAnimation(R.raw.active_toggle);
//                btnLottieUsage.setMinAndMaxProgress(0f, 0.5f);
//                btnLottieUsage.playAnimation();
//                Constant.accessEnabled = true;
//                editor.putBoolean("accessEnabled", true);
//                editor.commit();
//            } else
//            {
//                btnLottieUsage.setAnimation(R.raw.active_toggle);
//                btnLottieUsage.setMinAndMaxProgress(0.5f, 1f);
//                btnLottieUsage.playAnimation();
//                Constant.accessEnabled = false;
//                editor.putBoolean("accessEnabled", false);
//                editor.commit();
//                if (Build.VERSION.SDK_INT <= 22) {
//                    Constant.accessEnabled = true;
//                    editor.putBoolean("accessEnabled", true);
//                    editor.commit();
//                }
//            }
//
//            if (Utils.isAppDeviceAdmin(v)) {
//
//                btnLottieAdmin.setAnimation(R.raw.active_toggle);
//                btnLottieAdmin.setMinAndMaxProgress(0f, 0.5f);
//                btnLottieAdmin.playAnimation();
//                Constant.adminEnabled = true;
//                editor.putBoolean("adminEnabled", true);
//                editor.commit();
//            } else
//            {
//
//                btnLottieAdmin.setAnimation(R.raw.active_toggle);
//                btnLottieAdmin.setMinAndMaxProgress(0.5f, 1f);
//                btnLottieAdmin.playAnimation();
//                editor.putBoolean("adminEnabled", false);
//                Constant.adminEnabled = false;
//                editor.commit();
//            }
//
//            if (Utils.checkDrawOverlayPermission(v.getContext())) {
//
//                btnLottieBringToTop.setAnimation(R.raw.active_toggle);
//                btnLottieBringToTop.setMinAndMaxProgress(0f, 0.5f);
//                btnLottieBringToTop.playAnimation();
//                Constant.bringTopEnabled = true;
//                editor.putBoolean("bringTopEnabled", true);
//                editor.commit();
//            } else
//            {
//                btnLottieBringToTop.setAnimation(R.raw.active_toggle);
//                btnLottieBringToTop.setMinAndMaxProgress(0.5f, 1f);
//                btnLottieBringToTop.playAnimation();
//                Constant.bringTopEnabled = false;
//                editor.putBoolean("bringTopEnabled", false);
//                editor.commit();
//            }
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                if (pm != null && pm.isIgnoringBatteryOptimizations(v.getContext().getPackageName())) {
//                    Constant.turnOffBatteryOptimize = true;
//                } else {
//                    Constant.turnOffBatteryOptimize = false;
//                }
//            }
//


        mbtnAddToAutoStart = v.findViewById(R.id.btnAddToAutoStart);

        llAddToAutoStart = v.findViewById(R.id.llAddToAutoStart);

        String manufacturer = android.os.Build.MANUFACTURER;
        if (manufacturer.toLowerCase().contains("xiaomi") ||
                manufacturer.toLowerCase().contains("oppo") ||
                manufacturer.toLowerCase().contains("vivo") ||
                manufacturer.toLowerCase().contains("letv") ||
                manufacturer.toLowerCase().contains("honor")) {
            llAddToAutoStart.setVisibility(View.VISIBLE);
        } else {
            llAddToAutoStart.setVisibility(View.GONE);
        }


        mbtnAddToAutoStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToAutoStart();
            }
        });


        btnLottieUsage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.usageDataAccess(v);
            }
        });
        btnLottieAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.deviceAdminApps(v);
            }
        });
        btnLottieBringToTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.appOnTop(v);
            }
        });
        btnLottieBatteryOptimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.turnOffBatteryOptimize(v);
            }
        });


        DisplayMetrics displayMetrics = new DisplayMetrics();

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

//            Log.i("LockingForDelay",displayMetrics.widthPixels + " " + displayMetrics.heightPixels);

//            getWindow().setLayout((int) (displayMetrics.widthPixels * 0.9), (int) (displayMetrics.heightPixels * 0.9));
//            dialog.show();


    }


    private void addToAutoStart() {
        try {
            Intent intent = new Intent();
            String manufacturer = android.os.Build.MANUFACTURER;
            if ("xiaomi".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity"));
            } else if ("oppo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"));
            } else if ("vivo".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"));
            } else if ("Letv".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.AutobootManageActivity"));
            } else if ("Honor".equalsIgnoreCase(manufacturer)) {
                intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity"));
            }

            List<ResolveInfo> list = v.getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (list.size() > 0) {
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e("exc", String.valueOf(e));
            Toast.makeText(v.getContext(), "قابل اجرا نمی باشد.", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (Constant.accessEnabled && Constant.bringTopEnabled) {
            this.finish();
        }

        DataBase db;
        db = new DataBase(getBaseContext());
        db.openDB();


        Log.i("onStart123", "onStart----");

        if (Utils.getUsageStatsPermissionsStatus(this) == FragmentSetting.PermissionStatus.GRANTED) {

            btnLottieUsage.setAnimation(R.raw.active_toggle);
            btnLottieUsage.setMinAndMaxProgress(0f, 0.5f);
            btnLottieUsage.playAnimation();
            Constant.accessEnabled = true;
            db.writeAppSetting("accessEnabled", "YES", 3);
//            editor.putBoolean("accessEnabled", true);
//            editor.commit();
        } else {
            btnLottieUsage.setAnimation(R.raw.active_toggle);
            btnLottieUsage.setMinAndMaxProgress(0.5f, 1f);
            btnLottieUsage.playAnimation();
            Constant.accessEnabled = false;
            db.writeAppSetting("accessEnabled", "NO", 4);
//            editor.putBoolean("accessEnabled", false);
//            editor.commit();
        }
        if (Build.VERSION.SDK_INT <= 22) {
            Constant.accessEnabled = true;
            db.writeAppSetting("accessEnabled", "YES", 5);
//            editor.putBoolean("accessEnabled", true);
//            editor.commit();
        }

        if (Utils.isAppDeviceAdmin(v)) {

            btnLottieAdmin.setAnimation(R.raw.active_toggle);
            btnLottieAdmin.setMinAndMaxProgress(0f, 0.5f);
            btnLottieAdmin.playAnimation();
            Constant.adminEnabled = true;
            db.writeAppSetting("adminEnabled", "YES", 6);
//            editor.putBoolean("adminEnabled", true);
//            editor.commit();
        } else {

            btnLottieAdmin.setAnimation(R.raw.active_toggle);
            btnLottieAdmin.setMinAndMaxProgress(0.5f, 1f);
            btnLottieAdmin.playAnimation();
            db.writeAppSetting("adminEnabled", "NO", 7);
            Constant.adminEnabled = false;
//            editor.putBoolean("adminEnabled", false);
//            editor.commit();
        }

        if (Utils.checkDrawOverlayPermission(v.getContext())) {

            btnLottieBringToTop.setAnimation(R.raw.active_toggle);
            btnLottieBringToTop.setMinAndMaxProgress(0f, 0.5f);
            btnLottieBringToTop.playAnimation();
            Constant.bringTopEnabled = true;
            db.writeAppSetting("bringTopEnabled", "YES", 8);
//            editor.putBoolean("bringTopEnabled", true);
//            editor.commit();
        } else {
            btnLottieBringToTop.setAnimation(R.raw.active_toggle);
            btnLottieBringToTop.setMinAndMaxProgress(0.5f, 1f);
            btnLottieBringToTop.playAnimation();
            Constant.bringTopEnabled = false;
            db.writeAppSetting("bringTopEnabled", "NO", 9);
//            editor.putBoolean("bringTopEnabled", false);
//            editor.commit();
        }

        pm = (PowerManager) v.getContext().getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && pm.isIgnoringBatteryOptimizations(v.getContext().getPackageName())) {
                Constant.turnOffBatteryOptimize = true;
            } else {
                Constant.turnOffBatteryOptimize = false;
            }
        }


        if (Constant.turnOffBatteryOptimize) {

            btnLottieBatteryOptimize.setAnimation(R.raw.active_toggle);
            btnLottieBatteryOptimize.setMinAndMaxProgress(0f, 0.5f);
            btnLottieBatteryOptimize.playAnimation();
        } else {
            btnLottieBatteryOptimize.setAnimation(R.raw.active_toggle);
            btnLottieBatteryOptimize.setMinAndMaxProgress(0.5f, 1f);
            btnLottieBatteryOptimize.playAnimation();
        }

//        if (Constant.accessEnabled && Constant.adminEnabled && Constant.bringTopEnabled && !Constant.startServiceFlag)// && turnOffBatteryOptimize )
        if (Constant.accessEnabled && Constant.bringTopEnabled)// && !Constant.startServiceFlag)// && turnOffBatteryOptimize )
        {


            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("تنظیمات");
            alertDialog.setMessage("تنظیمات ضروری انجام شده اند، برای عملکرد صحیح برنامه پیشنهاد میشود بقیه تنظیمات را نیز انجام دهید در غیر این صورت با انتخاب دکمه ورود به برنامه وارد شوید.");
            alertDialog.setPositiveButton("ورود", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.writeAppSetting("service", "YES", 191);
                    db.writeAppSetting("logging", "YES", 201);
                    Utils.startService(v.getContext());
                    Constant.startServiceFlag = true;

                    ActivityAskPermission.this.finish();

                }
            });
            alertDialog.setNegativeButton("ادامه", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }

//        if ((!Constant.accessEnabled || !Constant.adminEnabled || !Constant.bringTopEnabled) && Constant.startServiceFlag)// && turnOffBatteryOptimize )
        if ((!Constant.accessEnabled || !Constant.bringTopEnabled) && Constant.startServiceFlag)// && turnOffBatteryOptimize )
        {
            Utils.stopService(v.getContext());
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Constant.accessEnabled && Constant.bringTopEnabled)// && !Constant.startServiceFlag)// && turnOffBatteryOptimize )
        {
            DataBase db;
            db = new DataBase(getBaseContext());
            db.openDB();

            db.writeAppSetting("service", "YES", 192);
            db.writeAppSetting("logging", "YES", 202);
            Utils.startService(v.getContext());
            Constant.startServiceFlag = true;
            Log.i("onDestroy111", "onDestroy222");
        }
        Log.i("onDestroy111", "onDestroy111");
    }
}