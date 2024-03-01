package ir.etelli.kids.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import ir.etelli.kids.Service.KidsLockService;
import ir.etelli.kids.Service.LockLogService;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.MainActivity;
import ir.etelli.kids.Pattern.ActivityLock;
import ir.etelli.kids.R;
import ir.etelli.kids.Utils.Utils;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.POWER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentSetting#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentSetting extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //    boolean allAppLockedState;
    static int doNothing = 0;
    static int lockAndWipeData = 1;
    static int freeAndWipeData = 2;
    static int lockAndKeepData = 3;
    static int freeAndKeepData = 4;
    public View v;
    //    boolean startLogServiceFlag = false;
    boolean isAdmin;

    NestedScrollView svParent;
    ExpandableLayout epGeneralSetting;
    ExpandableLayout epAccessSetting;
    ExpandableLayout epParentLogin;
    CardView cvActivateSetting;
    //    CardView cvLogSetting;
    LinearLayout llAddToAutoStart;
    LinearLayout llLockCondition;
    //    LinearLayout llLogCondition;
    LinearLayout llLoginSettings;
    LinearLayout llAccessSettings;
    LinearLayout llGeneralSettings;
    ImageView ivLoginSettings;
    ImageView ivAccessSettings;
    ImageView ivGeneralSettings;
    ImageView ivAccessArrow;
    ImageView ivAdminArrow;
    ImageView ivBringTopArrow;
    ImageView ivBatteryOptimizeArrow;

    MaterialButton mbtnActivateHelp;
    MaterialButton mbtnParentLoginHelp;
    MaterialButton mbtnAccessHelp;
    MaterialButton mbtnGeneralSettingHelp;

    MaterialButton mbtnAddToAutoStart;


    LottieAnimationView btnLottieUsage, btnLottieAdmin, btnLottieBringToTop, btnLottieBatteryOptimize;


    Button btnTbActive;

    LottieAnimationView laActivateService, laAccessSetting, laGeneralSettings, laLogin;
    TextView tvLockState;
    //    Button btnTbLogActive;
//    TextView tvPhoneAuthTime;
//    TextView tvPhoneStartTime;
//    TextView tvPhoneUsedTime;
    TextView txtAccessExpandText;
    TextView txtAdminExpandText;
    TextView txtLockScreenExpandText;
    //    TextView txtTurnOffExpandText;
    TextView txtBringToFrontExpandText;
    TextView txtBatteryOptimizeExpandText;
    TextInputEditText tvParentPassword;
    TextView tvPatternLock;
    ImageView ivLockIsActive;
    CardView cvSetting;
    RadioGroup rgShowHelp;
    RadioButton rbShowHelp;
    RadioButton rbHiddenHelp;

    MaterialButton phoneActivateHelp;
    MaterialButton phoneLoginHelp;
    MaterialButton phoneAccessHelp;
    MaterialButton phoneSettingHelp;
    //    ImageView ivLogIsActive;
    DataBase db;
    //    ArrayList<String> appNameList = new ArrayList<>();
//    ArrayList<String> appIconList = new ArrayList<>();
    ArrayList<Boolean> appIsLock = new ArrayList<>();
    ArrayList<String> edtAuthTime = new ArrayList<>();
    ArrayList<String> edtStartTime = new ArrayList<>();
    ArrayList<Long> authTimeMilliSecond = new ArrayList<>();
    ArrayList<Long> startTimMilliSecond = new ArrayList<>();
    ArrayList<Integer> usedTime = new ArrayList<>();
    //    AdapterAppList adapterAppListSearch;
    ArrayList<String> activeLockAppLable = new ArrayList<>();     //لیست برنامه های قفل شده برای دسترسی راحت تر
    String sortType = "DESC";
    String tempSearch;
    int tabFilter;
    TextView tvProgramCount;
    int enableActiveButton = 0;
//    boolean accessEnabled = false;
//    boolean adminEnabled = false;
//    boolean bringTopEnabled = false;
//    private boolean turnOffBatteryOptimize = false;

    Animation animation;
    Animation animationButtonShake;

    //    int hourAuthPhone = -1;
//    int minuteAuthPhone = -1;
//    int hourStart = -1;
//    int minuteStart = -1;
    FancyShowCaseView item1, item2, item3, item4;
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    Toast.makeText(MainActivity.this, "OK" + result.getResultCode(), Toast.LENGTH_SHORT).show();
                    SharedPreferences sharedPreferences =
                            v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
                    if (!sharedPreferences.getString("patternLock", "NULL").equals("NULL"))
                        tvPatternLock.setHint("الگو تنظیم شده است");
                }
            });
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentSetting() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSetting.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentSetting newInstance(String param1, String param2) {
        FragmentSetting fragment = new FragmentSetting();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint({"SetTextI18n", "SetJavaScriptEnabled"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_setting, container, false);


        db = new DataBase(v.getContext());
        db.openDB();

        animation = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
        animationButtonShake = AnimationUtils.loadAnimation(getContext(), R.anim.button_shake);

        tvLockState = v.findViewById(R.id.tvLockStatus);
        svParent = v.findViewById(R.id.svParent);
        ivLockIsActive = v.findViewById(R.id.ivLockActiveIcon);
        epGeneralSetting = v.findViewById(R.id.epGeneralSetting);
        epAccessSetting = v.findViewById(R.id.epAccess);
        epParentLogin = v.findViewById(R.id.epParentLogin);
        cvActivateSetting = v.findViewById(R.id.cvActivateSetting);
        llLockCondition = v.findViewById(R.id.llLockCondition);
        llLoginSettings = v.findViewById(R.id.llLoginSettings);
        llAccessSettings = v.findViewById(R.id.llAccessSettings);
        llGeneralSettings = v.findViewById(R.id.llGeneralSettings);
        ivLoginSettings = v.findViewById(R.id.ivLoginSettings);
        ivAccessSettings = v.findViewById(R.id.ivAccessSettings);
        ivGeneralSettings = v.findViewById(R.id.ivGeneralSettings);
        ivAccessArrow = v.findViewById(R.id.ivAccessArrow);
        ivAdminArrow = v.findViewById(R.id.ivAdminArrow);
        ivBringTopArrow = v.findViewById(R.id.ivBringTopArrow);
        ivBatteryOptimizeArrow = v.findViewById(R.id.ivBatteryOptimizeArrow);
        btnTbActive = v.findViewById(R.id.tbActive);
        laActivateService = v.findViewById(R.id.laActivateService);
        laAccessSetting = v.findViewById(R.id.laAccessSetting);
        laGeneralSettings = v.findViewById(R.id.laGeneralSettings);
        laLogin = v.findViewById(R.id.laLogin);
        tvParentPassword = v.findViewById(R.id.edtParentPass);
        tvPatternLock = v.findViewById(R.id.tvPatternLock);
        cvSetting = v.findViewById(R.id.cvSettings);
        rgShowHelp = v.findViewById(R.id.rgAppHelp);
        rbShowHelp = v.findViewById(R.id.rbShowHelp);
        rbHiddenHelp = v.findViewById(R.id.rbHiddenHelp);

        phoneActivateHelp = v.findViewById(R.id.mbtnActivateHelp);
        phoneLoginHelp = v.findViewById(R.id.mbtnParentLoginHelp);
        phoneAccessHelp = v.findViewById(R.id.mbtnAccessHelp);
        phoneSettingHelp = v.findViewById(R.id.mbtnGeneralSettingHelp);

        mbtnAddToAutoStart = v.findViewById(R.id.btnAddToAutoStart);
        mbtnAddToAutoStart.setOnClickListener(this);

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


        LinearLayout llPhoneNumber = v.findViewById(R.id.llPhoneNumber);
        for (int i = 0; i < llPhoneNumber.getChildCount(); i++) {
            llPhoneNumber.getChildAt(i).setEnabled(false);
        }

        RadioGroup rgNewAppLock = v.findViewById(R.id.rgNewAppLock);
        for (int i = 0; i < rgNewAppLock.getChildCount(); i++) {
            rgNewAppLock.getChildAt(i).setEnabled(false);
        }

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = sharedPreferences.edit();


        if (sharedPreferences.getBoolean("rgShowHelp", true)) {
            phoneActivateHelp.setVisibility(View.VISIBLE);
            phoneLoginHelp.setVisibility(View.VISIBLE);
            phoneAccessHelp.setVisibility(View.VISIBLE);
            phoneSettingHelp.setVisibility(View.VISIBLE);
        } else {
            phoneActivateHelp.setVisibility(View.GONE);
            phoneLoginHelp.setVisibility(View.GONE);
            phoneAccessHelp.setVisibility(View.GONE);
            phoneSettingHelp.setVisibility(View.GONE);
        }


        if (Constant.startServiceFlag &&
                Constant.accessEnabled &&
//                !Utils.isMyServiceRunning(v.getContext(), KidsLockService.class) &&
                Constant.bringTopEnabled
        ) {
            ivLockIsActive.setColorFilter(Color.argb(255, 255, 0, 0));
            btnTbActive.setText("غیر فعال");
            btnTbActive.clearAnimation();
            laActivateService.setAnimation(R.raw.success);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(0);

//            editor.putBoolean("service", true);
//            editor.putBoolean("logging", true);
//            editor.commit();
            llLockCondition.setVisibility(View.GONE);


//            Utils.startService(v.getContext());
//            Constant.startServiceFlag = true;


            TextView textView = v.findViewById(R.id.tvLockStatus);
            ivLockIsActive.setColorFilter(Color.argb(255, 255, 0, 0));
            llLockCondition.setVisibility(View.GONE);
            textView.setText("محافظ فعال است");
            btnTbActive.setText("غیر فعال");
            laActivateService.setAnimation(R.raw.success);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(0);
        }

        if ((!Constant.startServiceFlag ||
                !Constant.accessEnabled ||
                !Constant.bringTopEnabled)
//                && Utils.isMyServiceRunning(v.getContext(), KidsLockService.class)
        ) {

//            Log.i("entranceDialogR", " R35" + "  " + sharedPreferences.getBoolean("service", false));
            btnTbActive.startAnimation(animationButtonShake);
            ivLockIsActive.setColorFilter(R.color.ButtonTextColor);


//            editor.putBoolean("service", false);
//            editor.putBoolean("logging", false);
//            editor.commit();
            Constant.startServiceFlag = false;
            llLockCondition.setVisibility(View.VISIBLE);
            btnTbActive.setText("فعال");

            laActivateService.setAnimation(R.raw.play_button_blue);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(-1);

//            Toast.makeText(v.getContext(), "محافظ غیر فعال شد", Toast.LENGTH_SHORT).show();//todo

//            Utils.stopService(v.getContext());

            ivLockIsActive.setColorFilter(R.color.ButtonTextColor);
            llLockCondition.setVisibility(View.VISIBLE);
            tvLockState.setText("محافظ غیر فعال است");
            btnTbActive.setText("فعال");
            laActivateService.setAnimation(R.raw.play_button_blue);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(-1);
//            Log.i("entranceDialogR", " R36" + "  " + sharedPreferences.getBoolean("service", false));
        }

//        Log.i("entranceDialogR", " R37" + "  " + sharedPreferences.getBoolean("service", false));
        rbShowHelp.setChecked(sharedPreferences.getBoolean("rgShowHelp", true));
        rbHiddenHelp.setChecked(!sharedPreferences.getBoolean("rgShowHelp", true));
        rgShowHelp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rbShowHelp) {
                    editor.putBoolean("rgShowHelp", true);
                    phoneActivateHelp.setVisibility(View.VISIBLE);
                    phoneLoginHelp.setVisibility(View.VISIBLE);
                    phoneAccessHelp.setVisibility(View.VISIBLE);
                    phoneSettingHelp.setVisibility(View.VISIBLE);
                } else {
                    editor.putBoolean("rgShowHelp", false);
                    phoneActivateHelp.setVisibility(View.GONE);
                    phoneLoginHelp.setVisibility(View.GONE);
                    phoneAccessHelp.setVisibility(View.GONE);
                    phoneSettingHelp.setVisibility(View.GONE);
                }
//                Log.i("rgShowHelp", radioGroup.getCheckedRadioButtonId() + "");
                editor.commit();
            }
        });

//        Log.i("entranceDialogR", " R38" + "  " + sharedPreferences.getBoolean("service", false));

        btnLottieUsage = v.findViewById(R.id.lottie_btn_access);
        btnLottieUsage.setOnClickListener(this);
        btnLottieAdmin = v.findViewById(R.id.lottie_btn_admin);
        btnLottieAdmin.setOnClickListener(this);
        btnLottieBringToTop = v.findViewById(R.id.lottie_btn_bring_top);
        btnLottieBringToTop.setOnClickListener(this);
        btnLottieBatteryOptimize = v.findViewById(R.id.lottie_btn_battery_optimize);
        btnLottieBatteryOptimize.setOnClickListener(this);

        v.findViewById(R.id.btnUsage).setOnClickListener(this);
        v.findViewById(R.id.btnAdmin).setOnClickListener(this);
        v.findViewById(R.id.btnBringToTop).setOnClickListener(this);
        v.findViewById(R.id.btnTurnOffBatteryOptimize).setOnClickListener(this);
        v.findViewById(R.id.btnLockScreen).setOnClickListener(this);

        v.findViewById(R.id.btnParentPassSave).setOnClickListener(this);
        v.findViewById(R.id.btnParentEmailSave).setOnClickListener(this);
        v.findViewById(R.id.btnParentPhoneNumberSave).setOnClickListener(this);
        v.findViewById(R.id.btnTurnOffBatteryOptimize).setOnClickListener(this);
        v.findViewById(R.id.btnPopUpTime).setOnClickListener(this);
        v.findViewById(R.id.tvExpandAccessSetting).setOnClickListener(this);
        v.findViewById(R.id.tvExpandGeneralSetting).setOnClickListener(this);
        v.findViewById(R.id.btnLockAllProgram).setOnClickListener(this);
        v.findViewById(R.id.btnFreeAllProgram).setOnClickListener(this);
        v.findViewById(R.id.btnLightTheme).setOnClickListener(this);
        v.findViewById(R.id.btnDarkTheme).setOnClickListener(this);
        v.findViewById(R.id.cvExpandParentPassword).setOnClickListener(this);
        btnTbActive.setOnClickListener(this);
        laActivateService.setOnClickListener(this);
        v.findViewById(R.id.llAccessExpandText).setOnClickListener(this);
        v.findViewById(R.id.llAdminExpandText).setOnClickListener(this);
        v.findViewById(R.id.llLockScreenExpandText).setOnClickListener(this);
        v.findViewById(R.id.llTurnOffExpandText).setOnClickListener(this);
        v.findViewById(R.id.llBringToFrontExpandText).setOnClickListener(this);
        v.findViewById(R.id.btnPatternLockSetting).setOnClickListener(this);

        v.findViewById(R.id.mbtnActivateHelp).setOnClickListener(this);
        v.findViewById(R.id.mbtnParentLoginHelp).setOnClickListener(this);
        v.findViewById(R.id.mbtnAccessHelp).setOnClickListener(this);
        v.findViewById(R.id.mbtnGeneralSettingHelp).setOnClickListener(this);


        txtAccessExpandText = v.findViewById(R.id.txtAccessExpandText);
        txtAdminExpandText = v.findViewById(R.id.txtAdminExpandText);
        txtLockScreenExpandText = v.findViewById(R.id.txtLockScreenExpandText);
        txtBatteryOptimizeExpandText = v.findViewById(R.id.txtBatteryOptimizeExpandText);
        txtBringToFrontExpandText = v.findViewById(R.id.txtBringToFrontExpandText);

//        txtAccessExpandText.setVisibility(View.GONE);
//        txtAdminExpandText.setVisibility(View.GONE);
//        txtLockScreenExpandText.setVisibility(View.GONE);
//        txtBringToFrontExpandText.setVisibility(View.GONE);
//        txtBatteryOptimizeExpandText.setVisibility(View.GONE);

        ivLoginSettings.setOnClickListener(this);
        ivAccessSettings.setOnClickListener(this);
        ivGeneralSettings.setOnClickListener(this);
        ivAccessArrow.setOnClickListener(this);
        ivAdminArrow.setOnClickListener(this);
        ivBringTopArrow.setOnClickListener(this);
        ivBatteryOptimizeArrow.setOnClickListener(this);

//        Log.i("entranceDialogR", " R39" + "  " + sharedPreferences.getBoolean("service", false));
        tvParentPassword.setHint(sharedPreferences.getString("loginAdmin", "رمز والدین را وارد کنید"));
        if (!sharedPreferences.getString("patternLock", "NULL").equals("NULL"))
            tvPatternLock.setHint("الگو تنظیم شده است");

//        Log.i("entranceDialogR", " R40" + "  " + sharedPreferences.getBoolean("service", false));

        return v;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.lottie_btn_access:
            case R.id.btnUsage:
                Utils.usageDataAccess(v);
                break;
            case R.id.lottie_btn_admin:
            case R.id.btnAdmin:
                Utils.deviceAdminApps(v);
                break;
            case R.id.btnLockScreen:
                screenLockType();
                break;
            case R.id.lottie_btn_bring_top:
            case R.id.btnBringToTop:
                Utils.appOnTop(v);
                break;
            case R.id.lottie_btn_battery_optimize:
            case R.id.btnTurnOffBatteryOptimize:
                Utils.turnOffBatteryOptimize(v);
                break;
            case R.id.btnParentPassSave:
                parentPassword();
                break;
            case R.id.btnParentEmailSave:
                parentEmail();
                break;
            case R.id.btnParentPhoneNumberSave:
                parentPhone();
                break;
            case R.id.btnPopUpTime:
                popupTime();
                break;
            case R.id.tvExpandAccessSetting:
            case R.id.ivAccessSettings:
                expandAccessSetting();
                break;
            case R.id.tvExpandGeneralSetting:
            case R.id.ivGeneralSettings:
                expandGeneralSetting();
                break;
            case R.id.btnLockAllProgram:
                lockAllApp();
                break;
            case R.id.btnFreeAllProgram:
                freeAllApp();
                break;
            case R.id.btnLightTheme:
                lightThem();
                break;
            case R.id.btnDarkTheme:
                darkTheme();
                break;
            case R.id.cvExpandParentPassword:
            case R.id.ivLoginSettings:
                expandParentLogin();
                break;
            case R.id.tbActive:
            case R.id.laActivateService:
                StartStopService(v);
                break;
            case R.id.llAccessExpandText:
            case R.id.ivAccessArrow:
                expandAccessText(v);
                break;
            case R.id.llAdminExpandText:
            case R.id.ivAdminArrow:
                expandAdminText(v);
                break;
            case R.id.llBringToFrontExpandText:
            case R.id.ivBringTopArrow:
                expandBringToFrontText(v);
                break;
            case R.id.ivBatteryOptimizeArrow:
                expandBatteryOptimizeArrow(v);
            case R.id.llLockScreenExpandText:
                expandLockScreenText(v);
                break;
//            case R.id.llTurnOffExpandText:
//                expandTurnOffDisplayText(v);
//                break;
            case R.id.btnPatternLockSetting:
                patternLock(v);
                break;
            case R.id.mbtnActivateHelp:
                ActivateHelp();
                break;
            case R.id.mbtnParentLoginHelp:
                ParentLoginHelp();
                break;
            case R.id.mbtnAccessHelp:
                AccessHelp();
                break;
            case R.id.mbtnGeneralSettingHelp:
                GeneralSettingHelp();
                break;
            case R.id.btnAddToAutoStart:
                addToAutoStart();
                break;

        }
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

    @SuppressLint("UseCompatLoadingForDrawables")
    private void GeneralSettingHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_settings_applications_24, null));
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.GeneralSettingHelpTitle);
        tvDialogHelp.setText(R.string.GeneralSettingHelp);
        dialog.show();
    }

    private void AccessHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_app_settings_alt_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.AccessHelpTitle);
        tvDialogHelp.setText(R.string.AccessHelp);
        dialog.show();
    }

    private void ParentLoginHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_login_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.ParentLoginHelpTitle);
        tvDialogHelp.setText(R.string.ParentLoginHelp);
        dialog.show();
    }

    private void ActivateHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_app_blocking_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.ActivateHelpTitle);
        tvDialogHelp.setText(R.string.ActivateHelp);
        dialog.show();
    }

    private void patternLock(View v) {

        Intent intent = new Intent(v.getContext(), ActivityLock.class);
        intent.putExtra("unlock", "NO");
        activityResultLauncher.launch(intent);
//        startActivity(intent);

    }

    private void expandLockScreenText(View v) {
        if (txtLockScreenExpandText.getVisibility() == View.VISIBLE)
            txtLockScreenExpandText.setVisibility(View.GONE);
        else
            txtLockScreenExpandText.setVisibility(View.VISIBLE);
    }

    private void expandBringToFrontText(View v) {
        if (txtBringToFrontExpandText.getVisibility() == View.VISIBLE) {
            txtBringToFrontExpandText.setVisibility(View.GONE);
            ivBringTopArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        } else {
            txtBringToFrontExpandText.setVisibility(View.VISIBLE);
            ivBringTopArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        }
    }

    private void expandBatteryOptimizeArrow(View v) {
        if (txtBatteryOptimizeExpandText.getVisibility() == View.VISIBLE) {
            txtBatteryOptimizeExpandText.setVisibility(View.GONE);
            ivBatteryOptimizeArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        } else {
            txtBatteryOptimizeExpandText.setVisibility(View.VISIBLE);
            ivBatteryOptimizeArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        }
    }

    private void expandAdminText(View v) {
        if (txtAdminExpandText.getVisibility() == View.VISIBLE) {
            txtAdminExpandText.setVisibility(View.GONE);
            ivAdminArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
        } else {
            txtAdminExpandText.setVisibility(View.VISIBLE);
            ivAdminArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
        }
    }

    private void expandAccessText(View v) {
//        if (txtAccessExpandText.getVisibility() == View.VISIBLE) {
//
//            txtAccessExpandText.setVisibility(View.GONE);
//            ivAccessArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
//        } else {
//            txtAccessExpandText.setVisibility(View.VISIBLE);
//            ivAccessArrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
//
//        }
    }

//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void startLogging() {
//
//        Utils.startAlarmLogToDBServiceStart(v.getContext());
//        Toast.makeText(v.getContext(), "گزارش گیری فعال شد", Toast.LENGTH_LONG).show();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    private void stopLogging() {
//
//        Utils.stopAlarmLogToDBService(v.getContext());
//        Toast.makeText(v.getContext(), "گزارش گیری غیر فعال شد", Toast.LENGTH_LONG).show();
//
//    }
//

//    private void sendWhatsApp() {
//        SendWhatsApp.openWhatsApp(v.getContext(),"","");
//    }
//
//    private void sendEmail() {
//        SendEmail.send("ارسال مستقیم ایمیل با اندروید", "موضوع پیام ارسال شده برای آزمایش.");
//    }

    private void restartSelf() {

        Log.i("entranceDialogR", " R10");
        Intent intent = new Intent(getContext(), MainActivity.class);
        int mPendingIntentId = 123;//MAGICAL_NUMBER;
        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) requireContext().getSystemService(ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
        System.exit(0);
    }

    private void lightThem() {

//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("nightMode", false);
//        editor.commit();

        db.writeAppSetting("nightMode", "NO", 33);
        restartSelf();
    }

    private void darkTheme() {

//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("nightMode", true);
//        editor.commit();

        db.writeAppSetting("nightMode", "YES", 33);
        restartSelf();

    }

    private void filterProgram(int tabFilter) {
        Constant.appNameList.clear();
        Constant.appIconList.clear();
        appIsLock.clear();
        edtAuthTime.clear();
        edtStartTime.clear();
        edtAuthTime.clear();
        authTimeMilliSecond.clear();
        startTimMilliSecond.clear();
        usedTime.clear();
        Constant.appNameList = db.searchAppListLabel(tempSearch, tabFilter, sortType);

        for (int i = 0; i < Constant.appNameList.size(); i++) {
            appIsLock.add(db.readAppIsLock(Constant.appNameList.get(i)).equals("YES"));
            Constant.appIconList.add(db.readAppPackageName(Constant.appNameList.get(i)));
            edtAuthTime.add(db.readAppAuthTextTime(Constant.appNameList.get(i)));
            edtStartTime.add(db.readAppStartTextTime(Constant.appNameList.get(i)));
            authTimeMilliSecond.add(db.readAppAuthMilliSecondTime(Constant.appNameList.get(i)));
//            startTimMilliSecond.add(db.readAppStartMilliSecondTime(Constant.appNameList.get(i)));
            startTimMilliSecond.add(Utils.readAppStartMilliSecondTimeFromHourMinute(Constant.appNameList.get(i), db));
            usedTime.add(db.readAppUsedTime(Constant.appNameList.get(i)));
        }
    }

    private void freeAllApp() {

        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(v.getContext());
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle("اخطار");
        alertBuilder.setMessage("در صورت تایید تمام تنظیمات برنامه ها پاک خواهد شد. در صورتی که میخواهید تنظیمات باقی بمانند گزینه بدون تغییر را کلیک کنید.");
        alertBuilder.setIcon(R.drawable.kidslock_logo);

        alertBuilder.setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                filterProgram(0);

                for (int i1 = 0; i1 < Constant.appNameList.size(); i1++) {
                    appIsLock.add(false);
//                    edtAuthTime.add(" 24H : 60m ");
//                    edtStartTime.add(" 24H : 60m ");
                    edtAuthTime.add("تنظیم نشده");
                    edtStartTime.add("تنظیم نشده");
//                    db.writeAllAppLockState(0);
                    db.writeAppIsLock(Constant.appNameList.get(i1), "NO");
//                    db.writeAppAuthTimeText(Constant.appNameList.get(i1), " 24H : 60m ");
//                    db.writeAppStartTimeText(Constant.appNameList.get(i1), " 24H : 60m ");
                    db.writeAppAuthTimeText(Constant.appNameList.get(i1), "تنظیم نشده");
                    db.writeAppStartTimeText(Constant.appNameList.get(i1), "تنظیم نشده");
                    db.writeAppUsedTime(Constant.appNameList.get(i1), 0);
                    db.writeAppStartTime(Constant.appNameList.get(i1), ((long) -1));
                    db.writeAppStartTime(Constant.appNameList.get(i1), 0, 0);
                    db.writeAppAuthTime(Constant.appNameList.get(i1), ((long) -1));
                }


            }
        });
        alertBuilder.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.setNeutralButton("بدون تغییر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                listOffInstalledPackage(v.getContext());
//                allAppLockedState = (db.readAllAppLockState() == 1);

                filterProgram(0);

                for (int i1 = 0; i1 < Constant.appNameList.size(); i1++) {
                    appIsLock.add(false);
//                    db.writeAllAppLockState(0);
                    db.writeAppIsLock(Constant.appNameList.get(i1), "NO");
                }
            }
        });
        alertBuilder.show();
    }

    private void lockAllApp() {

        MaterialAlertDialogBuilder alertBuilder = new MaterialAlertDialogBuilder(v.getContext());

        alertBuilder.setMessage("در صورت تایید تمام تنظیمات برنامه ها پاک خواهد شد. در صورتی که میخواهید تنظیمات باقی بمانند گزینه بدون تغییر را کلیک کنید.");
        alertBuilder.setCancelable(false);
        alertBuilder.setTitle("اخطار");
        alertBuilder.setIcon(R.drawable.kidslock_logo);
        alertBuilder.setPositiveButton("موافقم", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                filterProgram(0);

                for (int i1 = 0; i1 < Constant.appNameList.size(); i1++) {
                    appIsLock.add(true);
//                    edtAuthTime.add(" 24H : 60m ");
//                    edtStartTime.add(" 24H : 60m ");
                    edtAuthTime.add("تنظیم نشده");
                    edtStartTime.add("تنظیم نشده");
//                    db.writeAllAppLockState(1);
                    db.writeAppIsLock(Constant.appNameList.get(i1), "YES");
//                    db.writeAppAuthTimeText(Constant.appNameList.get(i1), " 24H : 60m ");
//                    db.writeAppStartTimeText(Constant.appNameList.get(i1), " 24H : 60m ");
                    db.writeAppAuthTimeText(Constant.appNameList.get(i1), "تنظیم نشده");
                    db.writeAppStartTimeText(Constant.appNameList.get(i1), "تنظیم نشده");
                    db.writeAppUsedTime(Constant.appNameList.get(i1), 0);
                    db.writeAppStartTime(Constant.appNameList.get(i1), ((long) -1));
                    db.writeAppStartTime(Constant.appNameList.get(i1), 0, 0);
                    db.writeAppAuthTime(Constant.appNameList.get(i1), ((long) -1));
                }
            }

        });
        alertBuilder.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertBuilder.setNeutralButton("بدون تغییر", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                filterProgram(0);

                for (int i1 = 0; i1 < Constant.appNameList.size(); i1++) {
                    appIsLock.add(true);
//                    db.writeAllAppLockState(1);
                    db.writeAppIsLock(Constant.appNameList.get(i1), "YES");
                }

            }
        });
        alertBuilder.show();

    }

    private void expandGeneralSetting() {
        if (epGeneralSetting.isExpanded()) {
            epGeneralSetting.setExpanded(false);
//            cvActivateSetting.setVisibility(View.VISIBLE);
////            cvLogSetting.setVisibility(View.VISIBLE);
//            llLoginSettings.setVisibility(View.VISIBLE);
//            llAccessSettings.setVisibility(View.VISIBLE);
//            llGeneralSettings.setVisibility(View.VISIBLE);
//            laGeneralSettings.pauseAnimation();
            ivGeneralSettings.setImageResource(R.drawable.ic_baseline_navigate_before_24);
        } else {
            epGeneralSetting.setExpanded(true);
//            cvActivateSetting.setVisibility(View.GONE);
////            cvLogSetting.setVisibility(View.GONE);
//            llLoginSettings.setVisibility(View.GONE);
//            llAccessSettings.setVisibility(View.GONE);
//            llGeneralSettings.setVisibility(View.VISIBLE);
//            laGeneralSettings.playAnimation();
//            laGeneralSettings.setRepeatCount(-1);
            ivGeneralSettings.setImageResource(R.drawable.ic_baseline_clear_24);
        }
    }

    private void expandAccessSetting() {
        if (epAccessSetting.isExpanded()) {
            epAccessSetting.setExpanded(false);
//            cvActivateSetting.setVisibility(View.VISIBLE);
////            cvLogSetting.setVisibility(View.VISIBLE);
//            llLoginSettings.setVisibility(View.VISIBLE);
//            llGeneralSettings.setVisibility(View.VISIBLE);
//            llAccessSettings.setVisibility(View.VISIBLE);
//            laAccessSetting.pauseAnimation();
//            laAccessSetting.setProgress(0.5f);
            ivAccessSettings.setImageResource(R.drawable.ic_baseline_navigate_before_24);
        } else {
            epAccessSetting.setExpanded(true);
//            cvActivateSetting.setVisibility(View.GONE);
////            cvLogSetting.setVisibility(View.GONE);
//            llLoginSettings.setVisibility(View.GONE);
//            llGeneralSettings.setVisibility(View.GONE);
//            llAccessSettings.setVisibility(View.VISIBLE);
//            laAccessSetting.playAnimation();
//            laAccessSetting.setRepeatCount(-1);
            ivAccessSettings.setImageResource(R.drawable.ic_baseline_clear_24);
        }
    }

    private void expandParentLogin() {
        if (epParentLogin.isExpanded()) {
            epParentLogin.setExpanded(false);
//            cvActivateSetting.setVisibility(View.VISIBLE);
////            cvLogSetting.setVisibility(View.VISIBLE);
//            llAccessSettings.setVisibility(View.VISIBLE);
//            llGeneralSettings.setVisibility(View.VISIBLE);
//            llLoginSettings.setVisibility(View.VISIBLE);
//            laLogin.pauseAnimation();
            ivLoginSettings.setImageResource(R.drawable.ic_baseline_navigate_before_24);

        } else {
            epParentLogin.setExpanded(true);
//            cvActivateSetting.setVisibility(View.GONE);
////            cvLogSetting.setVisibility(View.GONE);
//            llAccessSettings.setVisibility(View.GONE);
//            llGeneralSettings.setVisibility(View.GONE);
//            llLoginSettings.setVisibility(View.VISIBLE);
//            laLogin.playAnimation();
//            laLogin.setRepeatCount(-1);
            ivLoginSettings.setImageResource(R.drawable.ic_baseline_clear_24);

        }
    }

    public void popupTime() {
        EditText editText = v.findViewById(R.id.edtPopupTime);
        String tvTime = editText.getText().toString();
        if (tvTime.equals(""))
            tvTime = "1";

        int i = Integer.parseInt(tvTime);

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("popupTime", i);
        editor.commit();

        if (Constant.startServiceFlag && (Constant.popupTime != i)) {
            v.getContext().stopService(new Intent(v.getContext(), LockLogService.class));
            v.getContext().startService(new Intent(v.getContext(), LockLogService.class));
        }
        Constant.popupTime = i;

        v.findViewById(R.id.ivPopupTimeSaved).setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                v.findViewById(R.id.ivPopupTimeSaved).setVisibility(View.INVISIBLE);
            }
        }, 200);

        Toast.makeText(v.getContext(), "ذخیره شد", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onStart() {
        super.onStart();

        Log.i("LockingForDelay", "displayMetrics.widthPixels +  + displayMetrics.heightPixels");
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        Button btnUsage = v.findViewById(R.id.btnUsage);
        Button btnAdmin = v.findViewById(R.id.btnAdmin);
        Button btnBringToTop = v.findViewById(R.id.btnBringToTop);
        Button btnTurnOffBatteryOptimize = v.findViewById(R.id.btnTurnOffBatteryOptimize);
        TextView tvUsage = v.findViewById(R.id.tvUssage);
        TextView tvAdmin = v.findViewById(R.id.tvAdmin);
        TextView tvUpToTop = v.findViewById(R.id.tvUpToTop);
        TextView tvBatteryOptimize = v.findViewById(R.id.tvBatteryOptimize);

//        btnAdmin.setBackgroundResource(R.drawable.button_focused);
//        btnBringToTop.setBackgroundResource(R.drawable.button_focused);

        if (Utils.getUsageStatsPermissionsStatus(getContext()) == PermissionStatus.GRANTED) {

            btnLottieUsage.setAnimation(R.raw.active_toggle);
            btnLottieUsage.setMinAndMaxProgress(0f, 0.5f);
            btnLottieUsage.playAnimation();

            btnUsage.setText("فعال");
//            btnUsage.setBackgroundResource(R.drawable.button_active);
            btnUsage.setTextColor(getResources().getColor(R.color.button_text_green));
            tvUsage.setTextColor(getResources().getColor(R.color.button_text_green));
            enableActiveButton = enableActiveButton + 1;
            Constant.accessEnabled = true;
            db.writeAppSetting("accessEnabled", "YES", 12);
//            editor.putBoolean("accessEnabled", true);
//            editor.commit();
            cvSetting.clearAnimation();
            laAccessSetting.pauseAnimation();
            laAccessSetting.setProgress(0.5f);
        } else {
            btnLottieUsage.setAnimation(R.raw.active_toggle);
            btnLottieUsage.setMinAndMaxProgress(0.5f, 1f);
            btnLottieUsage.playAnimation();

            btnUsage.setText("غیر فعال");
//            btnUsage.setBackgroundResource(R.drawable.button_passive);
            tvUsage.setTextColor(getResources().getColor(R.color.button_text_red));
            btnUsage.setTextColor(getResources().getColor(R.color.button_text_red));
            Constant.accessEnabled = false;
            db.writeAppSetting("accessEnabled", "NO", 13);
//            editor.putBoolean("accessEnabled", false);
//            editor.commit();
            if (Build.VERSION.SDK_INT <= 22) {
                enableActiveButton = enableActiveButton + 1;
                tvUsage.setTextColor(R.color.ButtonTextColor);
                Constant.accessEnabled = true;
                db.writeAppSetting("accessEnabled", "YES", 14);
//                editor.putBoolean("accessEnabled", true);
//                editor.commit();
            }
        }

        if (Utils.isAppDeviceAdmin(v)) {

            btnLottieAdmin.setAnimation(R.raw.active_toggle);
            btnLottieAdmin.setMinAndMaxProgress(0f, 0.5f);
            btnLottieAdmin.playAnimation();

            btnAdmin.setText("فعال");
            btnAdmin.setTextColor(getResources().getColor(R.color.button_text_green));
            tvAdmin.setTextColor(getResources().getColor(R.color.button_text_green));
            enableActiveButton = enableActiveButton + 1;
            Constant.adminEnabled = true;
            db.writeAppSetting("adminEnabled", "YES", 15);
//            editor.putBoolean("adminEnabled", true);
//            editor.commit();
            cvSetting.clearAnimation();
            laAccessSetting.pauseAnimation();
            laAccessSetting.setProgress(0.5f);
        } else {

            btnLottieAdmin.setAnimation(R.raw.active_toggle);
            btnLottieAdmin.setMinAndMaxProgress(0.5f, 1f);
            btnLottieAdmin.playAnimation();

            btnAdmin.setText("غیر فعال");
            btnAdmin.setTextColor(getResources().getColor(R.color.button_text_red));
            tvAdmin.setTextColor(getResources().getColor(R.color.button_text_red));
            Constant.adminEnabled = false;
            db.writeAppSetting("adminEnabled", "NO", 16);
//            editor.putBoolean("adminEnabled", false);
//            editor.commit();
        }

        if (Utils.checkDrawOverlayPermission(v.getContext())) {

            btnLottieBringToTop.setAnimation(R.raw.active_toggle);
            btnLottieBringToTop.setMinAndMaxProgress(0f, 0.5f);
            btnLottieBringToTop.playAnimation();

            btnBringToTop.setText("فعال");
            btnBringToTop.setTextColor(getResources().getColor(R.color.button_text_green));
            tvUpToTop.setTextColor(getResources().getColor(R.color.button_text_green));
            enableActiveButton = enableActiveButton + 1;
            Constant.bringTopEnabled = true;
            db.writeAppSetting("bringTopEnabled", "YES", 17);
//            editor.putBoolean("bringTopEnabled", true);
//            editor.commit();
            cvSetting.clearAnimation();
            laAccessSetting.pauseAnimation();
            laAccessSetting.setProgress(0.5f);
        } else {

            btnLottieBringToTop.setAnimation(R.raw.active_toggle);
            btnLottieBringToTop.setMinAndMaxProgress(0.5f, 1f);
            btnLottieBringToTop.playAnimation();

            btnBringToTop.setText("غیر فعال");
            btnBringToTop.setTextColor(getResources().getColor(R.color.button_text_red));
            tvUpToTop.setTextColor(getResources().getColor(R.color.button_text_red));
            Constant.bringTopEnabled = false;
            db.writeAppSetting("bringTopEnabled", "NO", 18);
//            editor.putBoolean("bringTopEnabled", false);
//            editor.commit();
        }

        PowerManager pm = (PowerManager) v.getContext().getSystemService(POWER_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (pm != null && pm.isIgnoringBatteryOptimizations(v.getContext().getPackageName())) {
                Constant.turnOffBatteryOptimize = true;
//                Toast.makeText(getContext(), "BatteryOptimizations", Toast.LENGTH_SHORT).show();
            } else {
                Constant.turnOffBatteryOptimize = false;
//                Toast.makeText(getContext(), "isIgnoringBatteryOptimizations", Toast.LENGTH_SHORT).show();
            }
        }


        if (Constant.turnOffBatteryOptimize) {

            btnLottieBatteryOptimize.setAnimation(R.raw.active_toggle);
            btnLottieBatteryOptimize.setMinAndMaxProgress(0f, 0.5f);
            btnLottieBatteryOptimize.playAnimation();

            btnTurnOffBatteryOptimize.setText("فعال");
            btnTurnOffBatteryOptimize.setTextColor(getResources().getColor(R.color.button_text_green));
            tvBatteryOptimize.setTextColor(getResources().getColor(R.color.button_text_green));
        } else {

            btnLottieBatteryOptimize.setAnimation(R.raw.active_toggle);
            btnLottieBatteryOptimize.setMinAndMaxProgress(0.5f, 1f);
            btnLottieBatteryOptimize.playAnimation();

            btnTurnOffBatteryOptimize.setText("غیر فعال");
            btnTurnOffBatteryOptimize.setTextColor(getResources().getColor(R.color.button_text_red));
            tvBatteryOptimize.setTextColor(getResources().getColor(R.color.button_text_red));
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
            btnBringToTop.setEnabled(false);


        Constant.popupTime = sharedPreferences.getInt("popupTime", Constant.popupTimeDefault);
        EditText editText = v.findViewById(R.id.edtPopupTime);
        editText.setText(String.valueOf(Constant.popupTime));

//        if (!Constant.accessEnabled || !Constant.adminEnabled || !Constant.bringTopEnabled)// ||  !turnOffBatteryOptimize)
//        if (!Constant.accessEnabled || !Constant.bringTopEnabled)// ||  !turnOffBatteryOptimize)
//        {
//            cvSetting.startAnimation(animation);
//            laAccessSetting.playAnimation();
//            laAccessSetting.setRepeatCount(-1);
//        }
//        if (Constant.accessEnabled && Constant.adminEnabled && Constant.bringTopEnabled)// && turnOffBatteryOptimize )
        if (Constant.accessEnabled && Constant.bringTopEnabled && !Utils.isMyServiceRunning(v.getContext(), KidsLockService.class))// && turnOffBatteryOptimize )
        {
//            btnTbActive.startAnimation(animationButtonShake);
            ivLockIsActive.setColorFilter(Color.argb(255, 255, 0, 0));
            btnTbActive.setText("غیر فعال");
            btnTbActive.clearAnimation();
            laActivateService.setAnimation(R.raw.success);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(0);

            tvLockState.setText("محافظ فعال است");
//            Toast.makeText(v.getContext(), "محافظ فعال شد", Toast.LENGTH_SHORT).show(); // todo
//            editor = sharedPreferences.edit();
            db.writeAppSetting("service", "YES", 19);
            db.writeAppSetting("logging", "YES", 20);
//            editor.putBoolean("service", true);
//            editor.putBoolean("logging", true);
//            editor.commit();
            llLockCondition.setVisibility(View.GONE);


            Utils.startService(v.getContext());
            Constant.startServiceFlag = true;
            Log.i("showFragmentSetting1", Constant.bringTopEnabled + " " + Constant.adminEnabled + " " + Constant.accessEnabled);
        }

//        if ((!Constant.accessEnabled || !Constant.adminEnabled || !Constant.bringTopEnabled))// && turnOffBatteryOptimize )
        if ((!Constant.accessEnabled || !Constant.bringTopEnabled))// && turnOffBatteryOptimize )
        {
            btnTbActive.startAnimation(animationButtonShake);
            ivLockIsActive.setColorFilter(R.color.ButtonTextColor);
            tvLockState.setText("محافظ غیر فعال است");

            db.writeAppSetting("service", "NO", 21);
            db.writeAppSetting("logging", "NO", 22);
//            editor = sharedPreferences.edit();
//            editor.putBoolean("service", false);
//            editor.putBoolean("logging", false);
//            editor.commit();
            Constant.startServiceFlag = false;
            llLockCondition.setVisibility(View.VISIBLE);
            btnTbActive.setText("فعال");

            laActivateService.setAnimation(R.raw.play_button_blue);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(-1);

            cvSetting.startAnimation(animation);
            laAccessSetting.playAnimation();
            laAccessSetting.setRepeatCount(-1);

//            Toast.makeText(v.getContext(), "محافظ غیر فعال شد", Toast.LENGTH_SHORT).show(); //todo

            Utils.stopService(v.getContext());
            btnTbActive.startAnimation(animationButtonShake);
            Log.i("showFragmentSetting2", Constant.bringTopEnabled + " " + Constant.adminEnabled + " " + Constant.accessEnabled);
        }

        Log.i("showFragmentSetting5", Constant.bringTopEnabled + " " + Constant.adminEnabled + " " + Constant.accessEnabled);
        if ((!Constant.accessEnabled || !Constant.bringTopEnabled)) {
            Utils.showFragmentSettingDialog(v);
        }

    }

    private void parentPassword() {

        if (!tvParentPassword.getText().toString().trim().equals("")) {
            SharedPreferences sharedPreferences =
                    v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("loginAdmin", tvParentPassword.getText().toString().trim());
            editor.apply();
            Toast.makeText(v.getContext(), "رمز ذخیره شد", Toast.LENGTH_SHORT).show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
            dialog.setTitle("تنظیم الگو");
            dialog.setMessage("با توجه به اینکه این برنامه اجازه حذف شدن را" +
                    " فقط به سرپرست میدهد پیشنهاد میشود" +
                    " دو گزینه رمز و الگو را تنظیم کنید.");
            dialog.setIcon(R.drawable.pattern_icon);

            dialog.setPositiveButton("تنظیم الگو", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    patternLock(v);
                }
            });
            dialog.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(v.getContext(), "تنظیم الگو پیشنهاد میشود", Toast.LENGTH_LONG).show();
                }
            });
            dialog.show();
        } else
            Toast.makeText(v.getContext(), "فیلد خالی است", Toast.LENGTH_SHORT).show();
    }

    private void parentEmail() {
        TextInputEditText textView = v.findViewById(R.id.edtParentEmail);

        if (!textView.getText().toString().trim().equals("")) {
            SharedPreferences sharedPreferences =
                    v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("emailAdmin", textView.getText().toString().trim());
            editor.apply();
            Toast.makeText(v.getContext(), "رمز ذخیره شد", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(v.getContext(), "فیلد خالی است", Toast.LENGTH_SHORT).show();
    }

    private void parentPhone() {
        TextInputEditText textView = v.findViewById(R.id.edtParentPhoneNumber);

        if (!textView.getText().toString().trim().equals("")) {
            SharedPreferences sharedPreferences =
                    v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("phoneAdmin", textView.getText().toString().trim());
            editor.apply();
            Toast.makeText(v.getContext(), "رمز ذخیره شد", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(v.getContext(), "فیلد خالی است", Toast.LENGTH_SHORT).show();
    }

    private void turnOffBatteryOptimize(View v) {

        DataBase db = new DataBase(v.getContext());
        db.openDB();

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
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(640, 340);
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

                db.writeAppSetting("turnOffBatteryOptimize", "YES", 23);
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    Intent intent = new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
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

    private void appOnTop() {


        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
        dialog.setContentView(R.layout.dialog_lootie_settings);
//        dialog.requestWindowFeature(R.style.dialog_transparent_style);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        View view = View.inflate(v.getContext(), R.layout.dialog_lootie_settings, null);
//        dialog.getWindow().setWindowAnimations(R.style.dialog_transparent_style);
//        dialog.setContentView(view, layoutParams);
        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);

        if (Build.MANUFACTURER.contains("Xiaomi") || Build.MANUFACTURER.contains("xiaomi")) {
            tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دو گزینه دسترسی را فعال نمائید");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(480, 700);
            ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
            imageView.setLayoutParams(layoutParams);
            if (db.readAppSetting("nightMode").equals("YES")) {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_dark_xiaomi).into(imageView);
            } else {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_light_xiaomi).into(imageView);
            }
        } else {

            tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دسترسی را فعال نمائید");
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(640, 140);
            ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
            imageView.setLayoutParams(layoutParams);

            if (db.readAppSetting("nightMode").equals("YES")) {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_dark).into(imageView);
            } else {
                Glide.with(v.getContext()).load(R.drawable.h_appear_on_top_light).into(imageView);
            }
        }


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String device = Build.MANUFACTURER;
                if (device.equals("Xiaomi")) {

                    Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
                    intent.setClassName("com.miui.securitycenter",
                            "com.miui.permcenter.permissions.PermissionsEditorActivity");
                    intent.putExtra("extra_pkgname", v.getContext().getPackageName());
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    RequestPermission();
                }
                dialog.dismiss();
            }
        });
        dialog.show();


    }

    private void screenLockType() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_SET_NEW_PASSWORD);
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست.", Toast.LENGTH_SHORT).show();
        }
    }

    private void deviceAdminApps() {

        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.dialog_lootie_settings, null);
//        dialog.setContentView(view, layoutParams);

        dialog.setContentView(R.layout.dialog_lootie_settings);

        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);

        tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دسترسی را فعال نمائید");

        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(400, 800);
        imageView.setLayoutParams(layoutParams);
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
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
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست.", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();


    }

    private void usageDataAccess() {


        Display display = v.getDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);


        Log.i("DisplayMetrics", " heightPixels: " + displayMetrics.heightPixels + " widthPixels: " + displayMetrics.widthPixels + " densityDpi: " + displayMetrics.densityDpi +
                " xdpi: " + displayMetrics.xdpi + " ydpi: " + displayMetrics.ydpi);

        //        dialog.getWindow().setLayout(640,480);
//        View view = View.inflate(v.getContext(), R.layout.dialog_lootie_settings, null);
//        dialog.setContentView(view, layoutParams);
        Dialog dialog = new Dialog(v.getContext(), R.style.dialog_transparent_style);
        dialog.setContentView(R.layout.dialog_lootie_settings);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(640, 480);
        ImageView imageView = dialog.findViewById(R.id.imageViewGlide);
        imageView.setLayoutParams(layoutParams);
        Button buttonOk = dialog.findViewById(R.id.btnOk);
        TextView tvDescription = dialog.findViewById(R.id.tvGlideTitle);
        tvDescription.setText("از لیست ظاهر شده برنامه Kids را پیدا کنید و دسترسی را فعال نمائید");

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

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {  // API28
//            action = Settings.ACTION_DATA_USAGE_SETTINGS;
//        }

                intent = new Intent(action);

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(),
                            "لطفا تنظیمات را به صورت دستی اجرا کنید برنامه قادر به اجرای تنظیمات نیست.",
                            Toast.LENGTH_LONG).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();


    }


    private void StartStopService(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);

        TextView tvLockStatus = v.findViewById(R.id.tvLockStatus);

        if (!Constant.startServiceFlag) {
//            if (Constant.accessEnabled && Constant.adminEnabled && Constant.bringTopEnabled)// && turnOffBatteryOptimize)
            if (Constant.accessEnabled && Constant.bringTopEnabled)// && turnOffBatteryOptimize)
            {
                ivLockIsActive.setColorFilter(Color.argb(255, 255, 0, 0));
                btnTbActive.setText("غیر فعال");
                btnTbActive.clearAnimation();
                laActivateService.setAnimation(R.raw.success);
                laActivateService.playAnimation();
                laActivateService.setRepeatCount(0);

                tvLockStatus.setText("محافظ فعال است");
//                Toast.makeText(v.getContext(), "محافظ فعال شد", Toast.LENGTH_SHORT).show(); //todo
                db.writeAppSetting("service", "YES", 24);
                db.writeAppSetting("logging", "YES", 25);
//                @SuppressLint("CommitPrefEdits")
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putBoolean("service", true);
//                editor.putBoolean("logging", true);
//                editor.commit();
                llLockCondition.setVisibility(View.GONE);


                Utils.startService(v.getContext());
                Constant.startServiceFlag = true;


            } else {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((Activity) v.getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                if (!Constant.accessEnabled)
                    svParent.smoothScrollTo(0, btnLottieUsage.getBottom() + (displayMetrics.heightPixels / displayMetrics.heightPixels));

                if (!Constant.adminEnabled)
                    svParent.smoothScrollTo(0, btnLottieAdmin.getBottom() + (displayMetrics.heightPixels / 3));

                if (!Constant.bringTopEnabled)
                    svParent.smoothScrollTo(0, btnLottieBringToTop.getBottom() + (displayMetrics.heightPixels / 4));

                Toast.makeText(v.getContext(), "لطفا دسترسی های مورد نیاز را فعال کنید.", Toast.LENGTH_LONG).show();
            }
        } else {

            btnTbActive.startAnimation(animationButtonShake);
            ivLockIsActive.setColorFilter(R.color.ButtonTextColor);
            tvLockStatus.setText("محافظ غیر فعال است");

            db.writeAppSetting("service", "NO", 26);
            db.writeAppSetting("logging", "NO", 27);
//            @SuppressLint("CommitPrefEdits")
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("service", false);
//            editor.putBoolean("logging", false);
//            editor.commit();
            Constant.startServiceFlag = false;
            llLockCondition.setVisibility(View.VISIBLE);
            btnTbActive.setText("فعال");

            laActivateService.setAnimation(R.raw.play_button_blue);
            laActivateService.playAnimation();
            laActivateService.setRepeatCount(-1);

//            Toast.makeText(v.getContext(), "محافظ غیر فعال شد", Toast.LENGTH_SHORT).show();//todo

            Utils.stopService(v.getContext());

        }
//        Log.i("entranceDialogR", " R16" + "  " + sharedPreferences.getBoolean("service", false));
    }


    private void RequestPermission() {
        // Check if Android M or higher


        String device = Build.MANUFACTURER;
        if (device.equals("Xiaomi")) {

//                        Log.i("RequestPermission", "- 2 -");
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter",
                    "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", v.getContext().getPackageName());
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
            }
        } else {
//                        Log.i("RequestPermission", "- 3 -");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + v.getContext().getPackageName()));
//                startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "دسترسی به تنظیمات امکان پذیر نیست", Toast.LENGTH_SHORT).show();
                }
//                            Log.i("RequestPermission", "- 4 -");
            }
        }

    }

    public boolean checkDrawOverlayPermission(Context context) {
        // check if we already  have permission to draw over other apps
        Log.i("apiVersionCode", " " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            return Settings.canDrawOverlays(context);

        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

//        if (!Constant.accessEnabled || !Constant.adminEnabled || !Constant.bringTopEnabled) {
        if (!Constant.accessEnabled || !Constant.bringTopEnabled) {
            laAccessSetting.playAnimation();
            laAccessSetting.setRepeatCount(-1);
            cvSetting.startAnimation(animation);
        }
//        if (Constant.accessEnabled && Constant.adminEnabled && Constant.bringTopEnabled && !Constant.startServiceFlag) {
        if (Constant.accessEnabled && Constant.bringTopEnabled && !Constant.startServiceFlag) {
            btnTbActive.startAnimation(animationButtonShake);
        }

//        showHelp();
    }

    private void showHelp() {

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);

        boolean showHelpSetting = sharedPreferences.getBoolean("showHelpSetting", false);

        if (!showHelpSetting) {
            ImageView imageView1 = v.findViewById(R.id.ivLockActiveIcon);
            ImageView imageView2 = v.findViewById(R.id.ivLoginSettingsIcon);
            ImageView imageView3 = v.findViewById(R.id.ivAccessSettingsIcon);
            ImageView imageView4 = v.findViewById(R.id.ivGeneralSettingsIcon);

            item1 = new FancyShowCaseView.Builder(requireActivity())
                    .focusOn(imageView1)
                    .title("فعال سازی برنامه")
                    .backgroundColor(getResources().getColor(R.color.help_lock_icon))
                    .build();
            item2 = new FancyShowCaseView.Builder(requireActivity())
                    .focusOn(imageView2)
                    .title("تعیین رمز ورود و اختصاص شماره جهت بازیابی رمز فراموش شده")
                    .backgroundColor(getResources().getColor(R.color.help_loginicon))
                    .build();
            item3 = new FancyShowCaseView.Builder(requireActivity())
                    .focusOn(imageView3)
                    .title("دسترسی های ضروری برای فعال شدن برنامه")
                    .backgroundColor(getResources().getColor(R.color.help_access_icon))
                    .build();
            item4 = new FancyShowCaseView.Builder(requireActivity())
                    .focusOn(imageView4)
                    .title("تنظیمات عمومی برنامه")
                    .backgroundColor(getResources().getColor(R.color.help_general_icon))
                    .build();

            new FancyShowCaseQueue()
                    .add(item1)
                    .add(item2)
                    .add(item3)
                    .add(item4).show();

            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("showHelpSetting", true);
            editor.commit();
        }


    }

    public enum PermissionStatus {
        GRANTED, DENIED, CANNOT_BE_GRANTED
    }

}