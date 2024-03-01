package ir.etelli.kids;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.farsitel.bazaar.IUpdateCheckService;

//import com.farsitel.bazaar.IUpdateCheckService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import ir.etelli.kids.Activity.ActivityHelp;
import ir.etelli.kids.Adapter.MainViewPager;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.Fragment.FragmentKids;
import ir.etelli.kids.Fragment.FragmentProgram;
import ir.etelli.kids.Fragment.FragmentSetting;
import ir.etelli.kids.LogData.FragmentLogData;
import ir.etelli.kids.Pattern.ActivityLock;
import ir.etelli.kids.Service.KidsLockService;
import ir.etelli.kids.SplashScreen.ActivitySplash;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.Utils.Utils;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;

import static ir.etelli.kids.R.id.mainTabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 0;
    //    boolean startServiceFlag = false;
    boolean bootCompleted = false;
    ArrayList<String> appNameList = new ArrayList<>();
    ArrayList<String> appIconList = new ArrayList<>();
    DataBase db;
    String loginAdmin;
    String loginPattern;
    Toolbar toolbar1;
    String sms_send_Granted;
    View tabitem1, tabItem2, tabItem3, tabItem4;
    FancyShowCaseView item1, item2, item3, item4;
    MaterialButton btnTabMenu;
    CardView drawerMenu;
    int width;
    int high;
    PopupWindow popupWindow;
    //    View mainMenu;
    Dialog entranceDialog;


    WindowManager windowManager;
    ViewGroup floatView;
    LayoutInflater layoutInflater;
    WindowManager.LayoutParams layoutParams;

    IUpdateCheckService service;
    UpdateServiceConnection connection;


    DrawerLayout drawerLayout;
    NavigationView navigationView;

    MaterialButton menuScore, menuContact, menuShare, menuOther, lock_menu, setting_menu, introduce_menu, btnAparat, btnInstagram;

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar1 = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar1);

        SharedPreferences sharedPreferences =
                getSharedPreferences("startService", MODE_PRIVATE);
        db = new DataBase(this);
        db.openDB();

        Utils.readSharedPreferences(db);

//        if (sharedPreferences.getBoolean("nightMode", false))
        if (db.readAppSetting("nightMode").equals("YES"))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        db.writeAppSetting("adminEnabled", "NO", 1);
        Log.i("writeAppSetting", " " + db.readAppSetting("adminEnabled"));

        db.writeAppSetting("adminEnabled", "YES", 2);
        Log.i("writeAppSetting", " " + db.readAppSetting("adminEnabled"));

        drawerLayout = findViewById(R.id.clMainActivity);

        findViewById(R.id.menuScore).setOnClickListener(this);
        findViewById(R.id.menuContact).setOnClickListener(this);
        findViewById(R.id.menuShare).setOnClickListener(this);
        findViewById(R.id.menuOther).setOnClickListener(this);
        findViewById(R.id.lock_menu).setOnClickListener(this);
        findViewById(R.id.setting_menu).setOnClickListener(this);
        findViewById(R.id.introduce_menu).setOnClickListener(this);
        findViewById(R.id.btnAparat).setOnClickListener(this);
        findViewById(R.id.btnInstagram).setOnClickListener(this);
        findViewById(R.id.LAdrawerMenu).setOnClickListener(this);


        if (Build.VERSION.SDK_INT < Constant.apiVersionCode) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage("این برنامه با دستگاه شما سازگار نیست. از اندروید ورژن ۵.۱ و بالاتر استفاده کنید.");
            alert.setTitle("ناسازگار");
            alert.setPositiveButton("خروج", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            alert.setIcon(R.drawable.ic_baseline_warning_24);
            alert.show();
        }


        @SuppressLint("ResourceType")
        Dialog mainMenuDialog = new Dialog(MainActivity.this);
        mainMenuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mainMenuDialog.setContentView(R.layout.main_menu);
        mainMenuDialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);


        btnTabMenu = findViewById(R.id.ivHelp);
        btnTabMenu.startAnimation(AnimationUtils.loadAnimation(this, R.anim.emoji_infinit_move));
        btnTabMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
                WindowManager.LayoutParams wml = mainMenuDialog.getWindow().getAttributes();
                wml.gravity = Gravity.BOTTOM | Gravity.RIGHT;

                mainMenuDialog.show();

            }
        });

        mainMenuDialog.findViewById(R.id.menuScore).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.menuContact).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.menuShare).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.menuOther).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.lock_menu).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.setting_menu).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.introduce_menu).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.btnAparat).setOnClickListener(this);
        mainMenuDialog.findViewById(R.id.btnInstagram).setOnClickListener(this);


        loginAdmin = sharedPreferences.getString("loginAdmin", "NULL");
        loginPattern = sharedPreferences.getString("patternLock", "NULL");

        if (loginAdmin.equals("NULL") && loginPattern.equals("NULL")) {
            fragmentManagment(3);
//            btnTabMenu.setVisibility(View.VISIBLE);
        } else {
            fragmentManagment(1);
//            btnTabMenu.setVisibility(View.GONE);
        }

        Utils.listOffInstalledPackage(this, db);  // todo

//        Log.i("onTaskRemoved2","KidsLockService111 " + Constant.startServiceFlag  + " " +  Constant.accessEnabled  + " " + Constant.bringTopEnabled  );

        if (Constant.startServiceFlag && Constant.accessEnabled && Constant.bringTopEnabled) {
            Utils.startService(MainActivity.this);
        }

        if ((!Constant.startServiceFlag || !Constant.accessEnabled || !Constant.bringTopEnabled)) {
            Utils.stopService(MainActivity.this);
        }

        if ((!Constant.accessEnabled || !Constant.bringTopEnabled)) {
            Utils.showFragmentSettingDialog(this.getWindow().getDecorView());
        }

        if (Utils.isNetworkConnected(MainActivity.this))
            initServiceUpdateCheck();
    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (String.valueOf(result.getData()).contains("OK")) {
                        btnTabMenu.setVisibility(View.VISIBLE);
                        fragmentManagment(3);
                    }
                }
            });

    @Override
    protected void onStart() {
        super.onStart();

        if ((!Constant.accessEnabled || !Constant.bringTopEnabled)) {
            Utils.showFragmentSettingDialog(this.getWindow().getDecorView());
        }

//        startActivityForResult(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS), 1);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.LAdrawerMenu: {
//                NavigationView navigationView =
                Log.i("CVdrawerMenu", "drawerLayoutLeft");
                DrawerLayout drawerLayout = findViewById(R.id.clMainActivity);
                drawerLayout.openDrawer(Gravity.LEFT);

            }
            break;

            case R.id.introduce_menu:
                introduceHelp();
                break;
            case R.id.setting_menu:
                settingHelp();
                break;
            case R.id.lock_menu:
                lockHelp();
                break;

//            case R.id.ivInstagram: {
            case R.id.btnInstagram: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setPackage("com.instagram.android");
                i.setData(Uri.parse("https://www.instagram.com/tv/CVAo-EyFQvR/?utm_source=ig_web_copy_link"));

                try {
                    startActivity(i);
                } catch (ActivityNotFoundException e) {
                    try {
                        startActivity(i);
                    } catch (Exception e2) {
                        Toast.makeText(MainActivity.this, "قابل اجرا نمی باشد", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;


//            case R.id.ivAparat: {
            case R.id.btnAparat: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://www.aparat.com/v/9JoAr"));
                try {
                    startActivity(i);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "قابل اجرا نمی باشد", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.menuContact: {
                Toast.makeText(MainActivity.this, "Ezattelli@gmail.com", Toast.LENGTH_LONG).show();
            }
            break;

            case R.id.menuScore: {
                Intent intent = new Intent(Intent.ACTION_EDIT);
                intent.setData(Uri.parse("bazaar://details?id=" + "ir.etelli.kids"));
                intent.setPackage("com.farsitel.bazaar");
                try {
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "قابل اجرا نمی باشد", Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case R.id.menuOther: {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("bazaar://collection?slug=by_author&aid=" + "etelli"));
                intent.setPackage("com.farsitel.bazaar");
                try {
                    startActivity(intent);

                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "قابل اجرا نمی باشد", Toast.LENGTH_SHORT).show();
                }

            }
            break;

            case R.id.menuShare: {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TITLE, "قفل کودک");
                i.putExtra(Intent.EXTRA_TEXT, "https://cafebazaar.ir/app/ir.etelli.kids");
                startActivity(Intent.createChooser(i, "Kids"));
            }
            break;

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuAdminPassword:
                adminPassword();
                break;
            case R.id.menuAdminPattern:
                adminPattern();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void adminPattern() {
        Intent intent = new Intent(MainActivity.this, ActivityLock.class);
        intent.putExtra("unlock", "YES");
        activityResultLauncher.launch(intent);
    }

    private void adminPassword() {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.user_login);
        dialog.findViewById(R.id.btnParentLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences =
                        getSharedPreferences("startService", MODE_PRIVATE);
                loginAdmin = sharedPreferences.getString("loginAdmin", "NULL");
                TextView textView = dialog.findViewById(R.id.edtParentLogin);
                if (textView.getText().toString().trim().equals(loginAdmin.trim())) {
                    btnTabMenu.setVisibility(View.VISIBLE);
                    fragmentManagment(3);
                    dialog.dismiss();
                    dialog.cancel();

                    toolbar1.setVisibility(View.GONE);
                }
//                Log.i("fragmentManagment", textView.getText().toString().trim() + "(" + loginAdmin + ")");
            }
        });

        TextView tvForgetPass = dialog.findViewById(R.id.tvForgetPass);
        tvForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpandableLayout expandableLayout = dialog.findViewById(R.id.epForgetPass);
                if (expandableLayout.isExpanded())
                    expandableLayout.setExpanded(false);
                else
                    expandableLayout.setExpanded(true);
            }
        });

        Button button = dialog.findViewById(R.id.btn_accessSms);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                requestSmsPermission();

                SharedPreferences sharedPreferences = getSharedPreferences("startService", MODE_PRIVATE);


                EditText edtPhone = dialog.findViewById(R.id.edtPhone);
//                Log.i("sms_send_Granted", sms_send_Granted + edtPhone + sharedPreferences.getString("phoneAdmin", "null"));
                if (edtPhone != null) {
                    if (sharedPreferences.getString("phoneAdmin", "null").equals(edtPhone.getText().toString().trim())) {
                        if (sms_send_Granted.equals("YES")) {
                            SmsManager sms = SmsManager.getDefault();
                            Toast.makeText(MainActivity.this, "رمز ارسال شد", Toast.LENGTH_LONG).show();
                            sms.sendTextMessage(edtPhone.getText().toString().trim(), null, loginAdmin, null, null);
//                            Log.i("sms_send_Granted", sms_send_Granted);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "پیامک ارسال نشد مجددا سعی کنید", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (sharedPreferences.getString("phoneAdmin", "null").equals("null")) {
                            Toast.makeText(MainActivity.this, "شماره بازیابی یافت نشد", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MainActivity.this, "شماره وارد شده اشتباه است", Toast.LENGTH_LONG).show();
                        }
                        dialog.dismiss();
                    }
                }
            }
        });
        dialog.show();
    }


    public void fullScreencall() {
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    private void releaseService() {
        if (connection != null) {
            unbindService(connection);
            connection = null;
        }
    }


    class UpdateServiceConnection implements ServiceConnection {
        @SuppressLint("UseCompatLoadingForDrawables")
        public void onServiceConnected(ComponentName name, IBinder boundService) {
            service = IUpdateCheckService.Stub.asInterface((IBinder) boundService);
            try {
                long vCode = service.getVersionCode(getPackageName());
//                Toast.makeText(MainActivity.this, "Version Code:" + vCode,
//                        Toast.LENGTH_LONG).show();
                if (vCode != -1) {
                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
                    alertdialog.setIcon(getDrawable(R.drawable.ic_baseline_warning_24));
                    alertdialog.setTitle("نسخه جدید");
                    alertdialog.setMessage("برنامه را به روز رسانی کنید. \n توجه: جهت به روز رسانی و یا حذف برنامه حالت سرپرست برنامه را غیر فعال کنید.");
                    alertdialog.setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cafebazaar.ir/app/ir.etelli.kids"));
                                startActivity(intent);
                            } catch (Exception e) {
                                Toast.makeText(MainActivity.this, "در بارگذاری خطایی رخ داد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertdialog.show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    }

    private void initServiceUpdateCheck() {
        connection = new UpdateServiceConnection();
        Intent i = new Intent(
                "com.farsitel.bazaar.service.UpdateCheckService.BIND");
        i.setPackage("com.farsitel.bazaar");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.i("UpdateCheck23", "bound value: " + ret);
    }


    private void lockHelp() {
        Intent i = new Intent(MainActivity.this, ActivityHelp.class);
        i.putExtra("html", "file:///android_asset/lock_help.htm");
        startActivity(i);
    }

    private void settingHelp() {
        Intent i = new Intent(MainActivity.this, ActivityHelp.class);
        i.putExtra("html", "file:///android_asset/settings_help.htm");
        startActivity(i);
    }

    private void introduceHelp() {
        Intent i = new Intent(MainActivity.this, ActivityHelp.class);
        i.putExtra("html", "file:///android_asset/introduce.htm");
        startActivity(i);
    }

    private void introduce() {

        SharedPreferences sharedPreferences =
                getSharedPreferences("startService", MODE_PRIVATE);

        boolean introduceFragments = sharedPreferences.getBoolean("introduceFragments", false);

        if (!introduceFragments) {
            Intent i = new Intent(this, ActivitySplash.class);
            startActivity(i);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("introduceFragments", true);
            editor.commit();
        }
    }

    private void fragmentManagment(int fragmentCount) {

        ArrayList<Fragment> fragments = new ArrayList<>();
        if (fragmentCount == 1) {
            this.getSupportActionBar().show();
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            fragments.add(new FragmentKids());
        } else {
            this.getSupportActionBar().hide();
            fragments.add(new FragmentProgram());
            fragments.add(new FragmentSetting());
            fragments.add(new FragmentKids());
            fragments.add(new FragmentLogData());
        }

        ViewPager2 viewPager2 = findViewById(R.id.vpMain);
        MainViewPager mainViewPager = new MainViewPager(this, fragments);

        viewPager2.setAdapter(mainViewPager);

        viewPager2.setUserInputEnabled(false);

        viewPager2.setPageTransformer(new ZoomOutPageTransformer());
//        viewPager2.setPageTransformer(new DepthPageTransformer());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up_animation);
        viewPager2.setAnimation(animation);

        TabLayout tabLayout = findViewById(mainTabLayout);
        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0: {
                                tab.setIcon(R.drawable.ic_baseline_settings_applications_24);
                                tab.setText("قفل ها");
                                tabItem3 = tab.view;
                            }
                            break;
                            case 1: {
                                tab.setIcon(R.drawable.ic_baseline_apps_24);
                                tab.setText("تنظیمات");
                                tabItem2 = tab.view;
                            }
                            break;
                            case 2: {
                                tab.setIcon(R.drawable.ic_baseline_face_retouching_natural_24);
                                tab.setText("فرزند");
                                tabitem1 = tab.view;
                            }
                            break;
                            case 3: {
                                tab.setIcon(R.drawable.ic_baseline_auto_graph_24);
                                tab.setText("گزارش");
                                tabItem4 = tab.view;
                            }
                            break;
                        }

                    }


                }).attach();

//        showHelp();

    }

    private void showHelp() {

        SharedPreferences sharedPreferences =
                getSharedPreferences("startService", MODE_PRIVATE);

        boolean showHelpMain = sharedPreferences.getBoolean("showHelpMain", false);
//        Log.i("showHelpMain","showHelpMain" + showHelpMain);

        if (!showHelpMain) {

            if (tabItem2 != null) {

                item1 = new FancyShowCaseView.Builder(MainActivity.this)
                        .focusOn(tabitem1)
                        .title("برنامه های مجاز")
                        .backgroundColor(getResources().getColor(R.color.help_search_icon))
                        .build();
                item2 = new FancyShowCaseView.Builder(MainActivity.this)
                        .focusOn(tabItem2)
                        .title("تنظیمات مدیریتی برنامه")
                        .backgroundColor(getResources().getColor(R.color.help_access_icon))
                        .build();
                item3 = new FancyShowCaseView.Builder(MainActivity.this)
                        .focusOn(tabItem3)
                        .title("تنظیمات برنامه های نصب شده")
                        .backgroundColor(getResources().getColor(R.color.help_sort_icon))
                        .build();
                item4 = new FancyShowCaseView.Builder(MainActivity.this)
                        .focusOn(tabItem4)
                        .title("گزارش برنامه های استفاده شده")
                        .backgroundColor(getResources().getColor(R.color.help_loginicon))
                        .build();


                new FancyShowCaseQueue()
                        .add(item1)
                        .add(item2)
                        .add(item3)
                        .add(item4)
                        .show();

            } else {
                item1 = new FancyShowCaseView.Builder(MainActivity.this)
                        .focusOn(tabitem1)
                        .title("برنامه های مجاز")
                        .backgroundColor(getResources().getColor(R.color.help_search_icon))
                        .build();

                new FancyShowCaseQueue()
                        .add(item1)
                        .show();
            }


            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("showHelpMain", true);
            editor.commit();
        }

    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this, permission);
        if (grant != PackageManager.PERMISSION_GRANTED) {
            String[] permission_list = new String[1];
            permission_list[0] = permission;
            ActivityCompat.requestPermissions(this, permission_list, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        SharedPreferences sharedPreferences = getSharedPreferences("startService", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "YES", Toast.LENGTH_SHORT).show();

                editor.putString("sms_send_Granted", "YES");
                editor.apply();
                sms_send_Granted = "YES";

            } else {
                editor.putString("sms_send_Granted", "NO");
                editor.apply();
                sms_send_Granted = "NO";
                Toast.makeText(MainActivity.this, "NO", Toast.LENGTH_SHORT).show();

            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        releaseService();

    }


    public class ZoomOutPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.5f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }


    @RequiresApi(21)
    public class DepthPageTransformer implements ViewPager2.PageTransformer {
        private static final float MIN_SCALE = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0f);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left page
                view.setAlpha(1f);
                view.setTranslationX(0f);
                view.setTranslationZ(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);

            } else if (position <= 1) { // (0,1]
                // Fade the page out.
                view.setAlpha(1 - position);

                // Counteract the default slide transition
                view.setTranslationX(pageWidth * -position);
                // Move it behind the left page
                view.setTranslationZ(-1f);

                // Scale the page down (between MIN_SCALE and 1)
                float scaleFactor = MIN_SCALE
                        + (1 - MIN_SCALE) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0f);
            }
        }
    }

}