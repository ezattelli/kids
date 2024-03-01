package ir.etelli.kids.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.etelli.kids.Adapter.AdapterAppList;
//import ir.etelli.kids.R;
import ir.etelli.kids.R;
import ir.etelli.kids.Service.KidsLockService;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.Utils.Utils;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProgram#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProgram extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 2323;
    public View v;
    int popupTime = 1;
    boolean isAdmin;
    FancyShowCaseView item1, item2, item3, item4;
    TextView tvPhoneAuthTime;
    TextView tvPhoneStartTime;
    TextView tvPhoneEndTime;
    TextView tvPhoneUsedTime;
    int hourAuthPhone = -1;
    int minuteAuthPhone = -1;
    Calendar calendarStart;
    Calendar calendarEnd;
    int phoneUsedTime = 0;

    ImageView ivLockSettings;
    ImageView ivLockCall;
    ImageView ivLockMessaging;
    ImageView ivLockGallery;
    ImageView ivLockFolder;
    ImageView ivLockCamera;
    ImageView ivLockAll;
    ImageView ivLockScreen;
    ImageView ivLockGooglePlay, ivLockFaceBook, ivLockChrome, ivLockGmail, ivLockBazaar;
    ImageView iv2LockSettings;
    DataBase db;


    AdapterAppList adapterAppList;

    ArrayList<Boolean> appIsLock = new ArrayList<>();
    ArrayList<String> edtAuthTime = new ArrayList<>();
    ArrayList<String> edtStartTime = new ArrayList<>();

    ArrayList<Long> authTimeMilliSecond = new ArrayList<>();
    ArrayList<Long> startTimMilliSecond = new ArrayList<>();
    ArrayList<Integer> usedTime = new ArrayList<>();

    int tempUsedPhoneMinute = 0;
    int tempUsedPhoneHour = 0;

    String sortType = "DESC";
    String tempSearch;
    int tabFilter;
    ExpandableLayout epPhoneLockSetting;
    ExpandableLayout epProgramLockSetting;
    ImageView ivPhoneLockSettings;
    ImageView ivProgramLockSettings;
    CardView cvLockPhone;
    CardView cvAllProgram;

    MaterialButton phoneAppLockHelp;
    MaterialButton mbtnPhoneLockHelp;
    MaterialButton mbtnPermanentLockHelp;
    MaterialButton phoneStartTimeLockHelp;
    MaterialButton phoneEndTimeLockHelp;
    MaterialButton phoneAuthTimeLockHelp;
    MaterialButton phoneUsedTimeLockHelp;
    MaterialButton phoneAllLockLockHelp;
    MaterialButton phoneScreenLockHelp;

    MaterialButton phoneSettingLockHelp;
    MaterialButton phoneCameraLockHelp;
    MaterialButton phonePlayStoreLockHelp;
    MaterialButton phoneFaceBookLockHelp;
    MaterialButton phoneChromeLockHelp;
    MaterialButton phoneGmailLockHelp;
    MaterialButton phoneBazaarLockHelp;


    ArrayList<Boolean> expand = new ArrayList<>();

    TextView tvProgramCount;
    int hourAuth = -1;
    int minuteAuth = -1;
    int hourStart = -1;
    int minuteStart = -1;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean turnOffDisplay;

    public FragmentProgram() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentParent.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProgram newInstance(String param1, String param2) {
        FragmentProgram fragment = new FragmentProgram();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();
//        Log.i("LockingForDelayzzz", "LockingForDelay - 30");
        filterProgram(0);


        totalTimeTextView();


        phoneUsedTime = db.readPhoneUsedTim();

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);


        if (sharedPreferences.getBoolean("rgShowHelp", true)) {
            phoneAppLockHelp.setVisibility(View.VISIBLE);
            mbtnPhoneLockHelp.setVisibility(View.VISIBLE);
            mbtnPermanentLockHelp.setVisibility(View.VISIBLE);
            phoneStartTimeLockHelp.setVisibility(View.VISIBLE);
            phoneEndTimeLockHelp.setVisibility(View.VISIBLE);
            phoneAuthTimeLockHelp.setVisibility(View.VISIBLE);
            phoneUsedTimeLockHelp.setVisibility(View.VISIBLE);
            phoneAllLockLockHelp.setVisibility(View.VISIBLE);
            phoneScreenLockHelp.setVisibility(View.VISIBLE);


            phoneSettingLockHelp.setVisibility(View.VISIBLE);
            phoneCameraLockHelp.setVisibility(View.VISIBLE);
            phonePlayStoreLockHelp.setVisibility(View.VISIBLE);
            phoneFaceBookLockHelp.setVisibility(View.VISIBLE);
            phoneChromeLockHelp.setVisibility(View.VISIBLE);
            phoneGmailLockHelp.setVisibility(View.VISIBLE);
            phoneBazaarLockHelp.setVisibility(View.VISIBLE);

        } else {
            phoneAppLockHelp.setVisibility(View.GONE);
            mbtnPhoneLockHelp.setVisibility(View.GONE);
            mbtnPermanentLockHelp.setVisibility(View.GONE);
            phoneStartTimeLockHelp.setVisibility(View.GONE);
            phoneEndTimeLockHelp.setVisibility(View.GONE);
            phoneAuthTimeLockHelp.setVisibility(View.GONE);
            phoneUsedTimeLockHelp.setVisibility(View.GONE);
            phoneAllLockLockHelp.setVisibility(View.GONE);
            phoneScreenLockHelp.setVisibility(View.GONE);


            phoneSettingLockHelp.setVisibility(View.GONE);
            phoneCameraLockHelp.setVisibility(View.GONE);
            phonePlayStoreLockHelp.setVisibility(View.GONE);
            phoneFaceBookLockHelp.setVisibility(View.GONE);
            phoneChromeLockHelp.setVisibility(View.GONE);
            phoneGmailLockHelp.setVisibility(View.GONE);
            phoneBazaarLockHelp.setVisibility(View.GONE);

        }

//        Log.i("LockingForDelay", "LockingForDelay - 31");
    }

    @SuppressLint("SetTextI18n")
    private void totalTimeTextView() {
        {

//            phoneUsedTime = Utils.readPhoneUsedTotalTime(v.getContext());
//            Log.i("LockingForDelay", "LockingForDelay - 32");

            phoneUsedTime = db.readPhoneUsedTim();
            tempUsedPhoneMinute = phoneUsedTime;
            tempUsedPhoneHour = 0;
            do {
                if (tempUsedPhoneMinute > 59) {
                    tempUsedPhoneHour++;
                    tempUsedPhoneMinute -= 60;
                }

            } while (tempUsedPhoneMinute > 59);
            tvPhoneUsedTime.setText(tempUsedPhoneHour + " : " + tempUsedPhoneMinute);
        }
//        Log.i("LockingForDelay", "LockingForDelay - 33");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        Log.i("LockingForDelay", "LockingForDelay - 1");
        v = inflater.inflate(R.layout.fragment_program, container, false);
        tvProgramCount = v.findViewById(R.id.tvProgramCountP);
        ivPhoneLockSettings = v.findViewById(R.id.ivPhoneLockSettings);
        ivProgramLockSettings = v.findViewById(R.id.ivProgramLockSettings);
//        epPhoneLockSetting = v.findViewById(R.id.epPhoneLocklSetting);
//        epProgramLockSetting = v.findViewById(R.id.epProgramLocklSetting);

        phoneAppLockHelp = v.findViewById(R.id.mbtnAppLockHelp);
        mbtnPhoneLockHelp = v.findViewById(R.id.mbtnPhoneLockHelp);
        mbtnPermanentLockHelp = v.findViewById(R.id.mbtnPermanentLockHelp);
        phoneStartTimeLockHelp = v.findViewById(R.id.mbtnPhoneStartTimeHelp);
        phoneEndTimeLockHelp = v.findViewById(R.id.mbtnPhoneEndTimeHelp);
        phoneAuthTimeLockHelp = v.findViewById(R.id.mbtnPhoneAuthTimeHelp);
        phoneUsedTimeLockHelp = v.findViewById(R.id.mbtnPhoneUsedTimeHelp);
        phoneAllLockLockHelp = v.findViewById(R.id.mbtnPhoneAllLockHelp);
        phoneScreenLockHelp = v.findViewById(R.id.mbtnPhoneLockScreenHelp);

        phoneSettingLockHelp = v.findViewById(R.id.mbtnPhoneSettingLockHelp);
        phoneCameraLockHelp = v.findViewById(R.id.mbtnPhoneCameraLockHelp);
        phonePlayStoreLockHelp = v.findViewById(R.id.mbtnPhoneGooglePlayLockHelp);
        phoneFaceBookLockHelp = v.findViewById(R.id.mbtnPhoneFaceBookLockHelp);
        phoneChromeLockHelp = v.findViewById(R.id.mbtnPhoneChromeLockHelp);
        phoneGmailLockHelp = v.findViewById(R.id.mbtnPhoneGmailLockHelp);
        phoneBazaarLockHelp = v.findViewById(R.id.mbtnPhoneBazaarLockHelp);


        TabLayout tabLayoutFilter = v.findViewById(R.id.tblFilterP);
        tabLayoutFilter.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabFilter = tabLayoutFilter.getSelectedTabPosition();
                filterProgram(tabFilter);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                tabFilter = tabLayoutFilter.getSelectedTabPosition();
                filterProgram(tabFilter);

            }
        });


        db = new DataBase(v.getContext());
        db.openDB();


        EditText edtSearch = v.findViewById(R.id.edtSearchProgram);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

//                Log.i("LockingForDelay", "LockingForDelay - 41");
                tempSearch = edtSearch.getText().toString().trim();

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
                    expand.add(i, false);
                    appIsLock.add(db.readAppIsLock(Constant.appNameList.get(i)).equals("YES"));
                    Constant.appIconList.add(db.readAppPackageName(Constant.appNameList.get(i)));
                    edtAuthTime.add(db.readAppAuthTextTime(Constant.appNameList.get(i)));
                    edtStartTime.add(db.readAppStartTextTime(Constant.appNameList.get(i)));
                    authTimeMilliSecond.add(db.readAppAuthMilliSecondTime(Constant.appNameList.get(i)));
                    startTimMilliSecond.add(Utils.readAppStartMilliSecondTimeFromHourMinute(Constant.appNameList.get(i), db));
                    usedTime.add(db.readAppUsedTime(Constant.appNameList.get(i)));
                }


                tvProgramCount.setText(String.valueOf(Constant.appNameList.size()));
                appRecyclerListUpdate(
                        Constant.appNameList,
                        Constant.appIconList,
                        appIsLock,
                        edtAuthTime,
                        edtStartTime,
                        usedTime);
//                Log.i("LockingForDelay", "LockingForDelay - 42");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        v.findViewById(R.id.ivSortP).setOnClickListener(this);
        v.findViewById(R.id.ivPhoneLockSettingsIcon).setOnClickListener(this);
        v.findViewById(R.id.ivPhoneLockSettings).setOnClickListener(this);
        v.findViewById(R.id.tvExpandLockPhoneSetting).setOnClickListener(this);
        v.findViewById(R.id.ivProgramLockSettings).setOnClickListener(this);
        v.findViewById(R.id.tvExpandLockProgramSetting).setOnClickListener(this);
        v.findViewById(R.id.tvAuthPhone).setOnClickListener(this);
        v.findViewById(R.id.tvStartPhone).setOnClickListener(this);
        v.findViewById(R.id.tvEndPhone).setOnClickListener(this);
        v.findViewById(R.id.btnResetPhoneLockTime).setOnClickListener(this);

        v.findViewById(R.id.iv2LockCall).setOnClickListener(this);
        v.findViewById(R.id.tvLockCall).setOnClickListener(this);
        v.findViewById(R.id.iv2LockMessaging).setOnClickListener(this);
        v.findViewById(R.id.tvLockMessaging).setOnClickListener(this);
        v.findViewById(R.id.iv2LockGallery).setOnClickListener(this);
        v.findViewById(R.id.tvLockGallery).setOnClickListener(this);
        v.findViewById(R.id.iv2LockFolder).setOnClickListener(this);
        v.findViewById(R.id.tvLockFolder).setOnClickListener(this);
        v.findViewById(R.id.iv2LockAll).setOnClickListener(this);
        v.findViewById(R.id.tvLockAll).setOnClickListener(this);
        v.findViewById(R.id.iv2LockScreen).setOnClickListener(this);
        v.findViewById(R.id.tvLockScreen).setOnClickListener(this);
        iv2LockSettings = v.findViewById(R.id.iv2LockSettings);
        iv2LockSettings.setOnClickListener(this);
        v.findViewById(R.id.tvLockSettings).setOnClickListener(this);
        v.findViewById(R.id.iv2LockCamera).setOnClickListener(this);
        v.findViewById(R.id.tvLockCamera).setOnClickListener(this);
        v.findViewById(R.id.iv2LockGooglePlay).setOnClickListener(this);
        v.findViewById(R.id.tvLockGooglePlay).setOnClickListener(this);
        v.findViewById(R.id.iv2LockFaceBook).setOnClickListener(this);
        v.findViewById(R.id.tvLockFaceBook).setOnClickListener(this);
        v.findViewById(R.id.iv2LockChrome).setOnClickListener(this);
        v.findViewById(R.id.tvLockChrome).setOnClickListener(this);
        v.findViewById(R.id.iv2LockGmail).setOnClickListener(this);
        v.findViewById(R.id.tvLockGmail).setOnClickListener(this);
        v.findViewById(R.id.iv2LockBazaar).setOnClickListener(this);
        v.findViewById(R.id.tvLockBazaar).setOnClickListener(this);


        ivLockSettings = v.findViewById(R.id.ivLockSettings);
        ivLockCall = v.findViewById(R.id.ivLockCall);
        ivLockMessaging = v.findViewById(R.id.ivLockMessaging);
        ivLockGallery = v.findViewById(R.id.ivLockGallery);
        ivLockFolder = v.findViewById(R.id.ivLockFolder);
        ivLockCamera = v.findViewById(R.id.ivLockCamera);
        ivLockAll = v.findViewById(R.id.ivLockAll);
        ivLockScreen = v.findViewById(R.id.ivLockScreen);
        ivLockGooglePlay = v.findViewById(R.id.ivLockGooglePlay);
        ivLockFaceBook = v.findViewById(R.id.ivLockFaceBook);
        ivLockChrome = v.findViewById(R.id.ivLockChrome);
        ivLockGmail = v.findViewById(R.id.ivLockGmail);
        ivLockBazaar = v.findViewById(R.id.ivLockBazaar);


        ivLockSettings.setOnClickListener(this);
        ivLockCall.setOnClickListener(this);
        ivLockMessaging.setOnClickListener(this);
        ivLockGallery.setOnClickListener(this);
        ivLockFolder.setOnClickListener(this);
        ivLockCamera.setOnClickListener(this);
        ivLockAll.setOnClickListener(this);
        ivLockScreen.setOnClickListener(this);
        ivLockGooglePlay.setOnClickListener(this);
        ivLockFaceBook.setOnClickListener(this);
        ivLockChrome.setOnClickListener(this);
        ivLockGmail.setOnClickListener(this);
        ivLockBazaar.setOnClickListener(this);


        mbtnPhoneLockHelp.setOnClickListener(this);
        mbtnPermanentLockHelp.setOnClickListener(this);
        phoneAppLockHelp.setOnClickListener(this);


        tvPhoneAuthTime = v.findViewById(R.id.tvAuthPhone);
        tvPhoneStartTime = v.findViewById(R.id.tvStartPhone);
        tvPhoneEndTime = v.findViewById(R.id.tvEndPhone);
        tvPhoneUsedTime = v.findViewById(R.id.tvUsedTimePhone);
        tvPhoneAuthTime.setText(db.readPhoneAuthTim());
        tvPhoneStartTime.setText(db.readPhoneStartTim());
        tvPhoneEndTime.setText(db.readPhoneEndTim());

        phoneAppLockHelp = v.findViewById(R.id.mbtnAppLockHelp);
        mbtnPhoneLockHelp = v.findViewById(R.id.mbtnPhoneLockHelp);
        phoneStartTimeLockHelp.findViewById(R.id.mbtnPhoneStartTimeHelp);
        phoneEndTimeLockHelp.findViewById(R.id.mbtnPhoneEndTimeHelp);
        phoneAuthTimeLockHelp.findViewById(R.id.mbtnPhoneAuthTimeHelp);
        phoneUsedTimeLockHelp.findViewById(R.id.mbtnPhoneUsedTimeHelp);
        phoneAllLockLockHelp.findViewById(R.id.mbtnPhoneAllLockHelp);
        phoneCameraLockHelp.findViewById(R.id.mbtnPhoneCameraLockHelp);
        phoneScreenLockHelp.findViewById(R.id.mbtnPhoneLockScreenHelp);
        phoneSettingLockHelp.findViewById(R.id.mbtnPhoneLockHelp);

        phoneAppLockHelp.setOnClickListener(this);
        mbtnPhoneLockHelp.setOnClickListener(this);
        phoneStartTimeLockHelp.setOnClickListener(this);
        phoneEndTimeLockHelp.setOnClickListener(this);
        phoneAuthTimeLockHelp.setOnClickListener(this);
        phoneUsedTimeLockHelp.setOnClickListener(this);
        phoneAllLockLockHelp.setOnClickListener(this);
        phoneCameraLockHelp.setOnClickListener(this);
        phoneScreenLockHelp.setOnClickListener(this);
        phoneSettingLockHelp.setOnClickListener(this);


        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);


        try {
            iv2LockSettings.setImageDrawable(getContext().
                    getPackageManager().getApplicationIcon("com.android.settings"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if (db.readAppAlwaysLockIsLock("Setting").equals("YES")) {
            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }

//        if (sharedPreferences.getBoolean("lockSetting", false)) {
//            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Call").equals("YES")) {
            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockCall", false)) {
//            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Messaging").equals("YES")) {
            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockMessaging", false)) {
//            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Gallery").equals("YES")) {
            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }

//        if (sharedPreferences.getBoolean("lockGallery", false)) {
//            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Folder").equals("YES")) {
            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }

//        if (sharedPreferences.getBoolean("lockFolder", false)) {
//            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("lockAll").equals("YES")) {
            ivLockAll.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockAll.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockAll", false)) { //todo
//            ivLockAll.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockAll.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Camera").equals("YES")) {
            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockCamera", false)) {
//            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("lockScreen").equals("YES")) {
            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockScreen", false)) {  //todo
//            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_24);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
//            editor.putBoolean("lockScreen", false);
//            editor.commit();
//        } else {
//            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("GooglePlay").equals("YES")) {
            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockGooglePlay", false)) {
//            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("FaceBook").equals("YES")) {
            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockFaceBook", false)) {
//            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Chrome").equals("YES")) {
            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }

//        if (sharedPreferences.getBoolean("lockChrome", false)) {
//            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Gmail").equals("YES")) {
            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockGmail", false)) {
//            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (db.readAppAlwaysLockIsLock("Bazaar").equals("YES")) {
            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_24);
        } else {
            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }


//        if (sharedPreferences.getBoolean("lockBazaar", false)) {
//            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_24);
//        } else {
//            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        }


        if (sharedPreferences.getBoolean("rgShowHelp", true)) {
            phoneAppLockHelp.setVisibility(View.VISIBLE);
            mbtnPhoneLockHelp.setVisibility(View.VISIBLE);
            phoneStartTimeLockHelp.setVisibility(View.VISIBLE);
            phoneEndTimeLockHelp.setVisibility(View.VISIBLE);
            phoneAuthTimeLockHelp.setVisibility(View.VISIBLE);
            phoneUsedTimeLockHelp.setVisibility(View.VISIBLE);
            phoneAllLockLockHelp.setVisibility(View.VISIBLE);
            phoneCameraLockHelp.setVisibility(View.VISIBLE);
            phoneScreenLockHelp.setVisibility(View.VISIBLE);
            phoneSettingLockHelp.setVisibility(View.VISIBLE);
        } else {
            phoneAppLockHelp.setVisibility(View.GONE);
            mbtnPhoneLockHelp.setVisibility(View.GONE);
            phoneStartTimeLockHelp.setVisibility(View.GONE);
            phoneEndTimeLockHelp.setVisibility(View.GONE);
            phoneAuthTimeLockHelp.setVisibility(View.GONE);
            phoneUsedTimeLockHelp.setVisibility(View.GONE);
            phoneAllLockLockHelp.setVisibility(View.GONE);
            phoneCameraLockHelp.setVisibility(View.GONE);
            phoneScreenLockHelp.setVisibility(View.GONE);
            phoneSettingLockHelp.setVisibility(View.GONE);
        }

        totalTimeTextView();

//        Log.i("LockingForDelay", "LockingForDelay - 2");

//        Log.i("showFragmentSetting4", Constant.bringTopEnabled + " " + Constant.adminEnabled + " " + Constant.accessEnabled);
        if ((!Constant.accessEnabled || !Constant.bringTopEnabled)) {
            Utils.showFragmentSettingDialog(v);
        }
        return v;
    }


    @SuppressLint("NotifyDataSetChanged")
    private void appRecyclerListUpdate(ArrayList<String> appNameList_,
                                       ArrayList<String> appIconList_,
                                       ArrayList<Boolean> appIsLock_,
                                       ArrayList<String> edtAuthTime_,
                                       ArrayList<String> edtStartTime_,
                                       ArrayList<Integer> usedTime_) {
        Log.i("LockingForDelay", "LockingForDelay - 6");

        RecyclerView recyclerView;
        recyclerView = v.findViewById(R.id.rvAppListP);
        adapterAppList = new AdapterAppList(v.getContext(),
                appNameList_,
                appIconList_,
                appIsLock_,
                edtAuthTime_,
                edtStartTime_,
                usedTime_,
                expand);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        Log.i("recyclerView1", "recyclerView111");
        recyclerView.setAdapter(adapterAppList);
//        recyclerView.setNestedScrollingEnabled(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
        if (v.getDisplay() != null) {
            v.getDisplay().getMetrics(displayMetrics);
            layoutParams.height = displayMetrics.heightPixels * 2 / 3;
        }
        recyclerView.setLayoutParams(layoutParams);

        Log.i("recyclerView2", "recyclerView222" + displayMetrics.widthPixels + " h " + displayMetrics.heightPixels);


        adapterAppList.setOnClickListener(new AdapterAppList.OnClick() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void setOnClickInterface(View view, int position) {

                switch (view.getId()) {

                    case R.id.tvAuthAppTime:
                        setAuthTimePicker(position);
                        break;
                    case R.id.tvStartedAppTime:
                        setStartTimePicker(position);
                        break;
                    case R.id.ivActivate:
                        saveAppLockStatus(view, position);
                        break;
                    case R.id.clMainRow:
                        expandRowData(view, position);
                        break;
                    case R.id.btnResetAppLockTime:
                        resetAppTimeSetting(view, position);
                        break;
                }
                adapterAppList.notifyItemChanged(position);
            }
        });
        Log.i("LockingForDelay", "LockingForDelay - 7");

    }

    private void resetAppTimeSetting(View view, int position) {
        Log.i("LockingForDelay", "LockingForDelay - 28");
//        db.writeAppAuthTimeText(Constant.appNameList.get(position).trim(), "24H:60m");
        db.writeAppAuthTimeText(Constant.appNameList.get(position).trim(), "تنظیم نشده");
        db.writeAppAuthTime(Constant.appNameList.get(position).trim(), -1);

//        db.writeAppStartTimeText(Constant.appNameList.get(position).trim(), "24H:60m");
        db.writeAppStartTimeText(Constant.appNameList.get(position).trim(), "تنظیم نشده");
        db.writeAppStartTime(Constant.appNameList.get(position).trim(), -1);
        db.writeAppStartTime(Constant.appNameList.get(position).trim(), 0, 0);

//        edtAuthTime.add(position, "24H:60m");
//        edtStartTime.add(position, "24H:60m");

        edtAuthTime.add(position, "تنظیم نشده");
        edtStartTime.add(position, "تنظیم نشده");
        Log.i("LockingForDelay", "LockingForDelay - 29");


    }

    private void expandRowData(View view, int position) {
        Log.i("LockingForDelay", "LockingForDelay - 26");

        if (expand.get(position)) {
            expand.set(position, false);
        } else {
            expand.set(position, true);
        }
//        Log.i("setOnClicInterface4", position + "::" + expand.get(position));

        adapterAppList.notifyItemChanged(position);
//        LogAllData();
        Log.i("LockingForDelay", "LockingForDelay - 27");
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivSortP:
                sortApp();
                break;
            case R.id.tvExpandLockPhoneSetting:
            case R.id.ivPhoneLockSettings:
//                expandPhoneLockSetting();
                break;
            case R.id.tvExpandLockProgramSetting:
            case R.id.ivProgramLockSettings:
//                expandProgramLockSetting();
                break;
            case R.id.tvAuthPhone:
                authPhoneTimeSave();
                break;
            case R.id.tvStartPhone:
                startPhoneTimeSave();
                break;
            case R.id.tvEndPhone:
                endPhoneTimeSave();
                break;
            case R.id.btnResetPhoneLockTime:
                resetPhoneLockTime(v);
                break;
            case R.id.ivLockSettings:
            case R.id.iv2LockSettings:
            case R.id.tvLockSettings:
                lockSetting(v);
                break;
            case R.id.ivLockCall:
            case R.id.iv2LockCall:
            case R.id.tvLockCall:
                lockCall(v);
                break;
            case R.id.ivLockMessaging:
            case R.id.iv2LockMessaging:
            case R.id.tvLockMessaging:
                lockMessaging(v);
                break;
            case R.id.ivLockGallery:
            case R.id.iv2LockGallery:
            case R.id.tvLockGallery:
                lockGallery(v);
                break;
            case R.id.ivLockFolder:
            case R.id.iv2LockFolder:
            case R.id.tvLockFolder:
                lockFolder(v);
                break;
            case R.id.iv2LockAll:
            case R.id.tvLockAll:
            case R.id.ivLockAll:
                lockAll(v);
                break;
            case R.id.ivLockCamera:
            case R.id.iv2LockCamera:
            case R.id.tvLockCamera:
                lockCamera(v);
                break;
            case R.id.ivLockScreen:
            case R.id.iv2LockScreen:
            case R.id.tvLockScreen:
                lockScreen(v);
                break;
            case R.id.ivLockGooglePlay:
            case R.id.iv2LockGooglePlay:
            case R.id.tvLockGooglePlay:
                lockGooglePlay(v);
                break;
            case R.id.ivLockFaceBook:
            case R.id.iv2LockFaceBook:
            case R.id.tvLockFaceBook:
                lockFaceBook(v);
                break;
            case R.id.ivLockChrome:
            case R.id.iv2LockChrome:
            case R.id.tvLockChrome:
                lockChrome(v);
                break;
            case R.id.ivLockGmail:
            case R.id.iv2LockGmail:
            case R.id.tvLockGmail:
                lockGmail(v);
                break;
            case R.id.ivLockBazaar:
            case R.id.iv2LockBazaar:
            case R.id.tvLockBazaar:
                lockBazaar(v);
                break;

            case R.id.mbtnPhoneLockHelp:
                showPhoneLockHelp();
                break;
            case R.id.mbtnAppLockHelp:
                showAppLockHelp();
                break;
            case R.id.mbtnPhoneStartTimeHelp:
                showPhoneStartTimeHelp();
                break;
            case R.id.mbtnPhoneEndTimeHelp:
                showPhoneEndTimeHelp();
                break;
            case R.id.mbtnPhoneAuthTimeHelp:
                showPhoneAuthTimeHelp();
                break;
            case R.id.mbtnPhoneAllLockHelp:
                showPhoneAllLockHelp();
                break;
            case R.id.mbtnPhoneLockScreenHelp:
                showPhoneLockScreenHelp();
                break;
            case R.id.mbtnPhoneCameraLockHelp:
                showPhoneCameraLockHelp();
                break;
            case R.id.mbtnPhoneSettingLockHelp:
                showPhoneSettingLockHelp();
                break;


        }
    }

    private void showPhoneSettingLockHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_lock_open_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.SettingHelpTitle);
        tvDialogHelp.setText(R.string.PhonSettingLoackHelp);
        dialog.show();
    }

    private void showPhoneCameraLockHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_lock_open_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.CameraHelpTitle);
        tvDialogHelp.setText(R.string.CameraHelp);
        dialog.show();
    }

    private void showPhoneLockScreenHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_lock_open_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.PhoneLockScreenHelpTitle);
        tvDialogHelp.setText(R.string.PhoneLockScreenHelp);
        dialog.show();
    }

    private void showPhoneAllLockHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_lock_open_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.PhoneAllLockTitle);
        tvDialogHelp.setText(R.string.PhoneAllLock);
        dialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void showPhoneAuthTimeHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_phone_locked_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.PhoneAuthTimeHelpTitle);
        tvDialogHelp.setText(R.string.PhoneAuthTimeHelp);
        dialog.show();
    }

    private void showPhoneEndTimeHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_phone_locked_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.PhoneEndTimeHelpTitle);
        tvDialogHelp.setText(R.string.PhoneEndTimeHelp);
        dialog.show();
    }

    private void showPhoneStartTimeHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_av_timer_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.PhoneStartTimeHelpTitle);
        tvDialogHelp.setText(R.string.PhoneStartTimeHelp);
        dialog.show();
    }

    private void showPhoneLockHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_phone_locked_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.PhoneLockHelpTitle);
        tvDialogHelp.setText(R.string.PhoneLockHelp);
        dialog.show();
    }

    private void showAppLockHelp() {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setContentView(R.layout.dialog_help);
        ImageView ivDialogImageViewGelp = dialog.findViewById(R.id.ivDialoghelp);
        ivDialogImageViewGelp.setImageResource(R.drawable.ic_baseline_apps_24);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setWindowAnimations(R.style.SlidingDialogAnimation);
        TextView tvDialogHelpTitle = dialog.findViewById(R.id.tvDialogHelpTitle);
        TextView tvDialogHelp = dialog.findViewById(R.id.tvDialogHelp);
        tvDialogHelpTitle.setText(R.string.showAppLockHelpTitle);
        tvDialogHelp.setText(R.string.showAppLockHelp);
        dialog.show();
    }

    private void lockScreen(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("LockScreen").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("LockScreen", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("LockScreen"));

        Constant.lockSetting = lockSetting;


//        if (sharedPreferences.getBoolean("lockScreen", false)) {
//            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
////            lockScreen = true;
//            lockScreen = false;
//            ivLockScreen.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockScreen", lockScreen);
//        editor.commit();
    }

    private void lockCamera(View v) {
//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Camera").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Camera", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Camera"));

        Constant.lockSetting = lockSetting;


//        if (sharedPreferences.getBoolean("lockCamera", false)) {
//            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockCamera = true;
//            ivLockCamera.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockCamera = lockCamera;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockCamera", lockCamera);
//        editor.commit();
    }

    private void lockAll(View v) {
//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;

        lockSetting = db.readAppAlwaysLockIsLock("lockAll").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("lockAll", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("lockAll"));

        Constant.lockSetting = lockSetting;


//        if (sharedPreferences.getBoolean("lockAll", false))
//        if (db.readAppAlwaysLockIsLock("lockAll").equals("YES")) {
//            ivLockAll.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockAll = true;
//            ivLockAll.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockAll", lockAll);
//        editor.commit();
    }


    private void lockAllEnable(View v) {

        db.writeAppAlwaysLockIsLock("lockAll", "YES");
        Constant.lockAll = true;
        ivLockAll.setImageResource(R.drawable.ic_baseline_lock_24);

//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//        Constant.lockAll = true;
//        ivLockAll.setImageResource(R.drawable.ic_baseline_lock_24);
//
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockAll", Constant.lockAll);
//        editor.commit();
    }


    private void lockAllDisable(View v) {
        db.writeAppAlwaysLockIsLock("lockAll", "NO");
        Constant.lockAll = false;
        ivLockAll.setImageResource(R.drawable.ic_baseline_lock_open_24);

//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//        Constant.lockAll = false;
//
//        ivLockAll.setImageResource(R.drawable.ic_baseline_lock_open_24);
//
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockAll", Constant.lockAll);
//        editor.commit();
    }


    private void lockSetting(View v) {

//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;
//
//        if (sharedPreferences.getBoolean("lockSetting", false)) {
//            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_24);
//        }


        lockSetting = db.readAppAlwaysLockIsLock("Setting").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockSettings.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Setting", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Setting"));

        Constant.lockSetting = lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockSetting", lockSetting);
//        editor.commit();
    }

    private void lockCall(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Call").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Settings", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Settings"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockCall", false)) {
//            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockCall.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockCall=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockCall", lockSetting);
//        editor.commit();
    }

    private void lockMessaging(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Messaging").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Messaging", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Messaging"));

        Constant.lockCall = lockSetting;


//
//        if (sharedPreferences.getBoolean("lockMessaging", false)) {
//            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockMessaging.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockMessaging=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockMessaging", lockSetting);
//        editor.commit();
    }

    private void lockGallery(View v) {
//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Gallery").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Gallery", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Gallery"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockGallery", false)) {
//            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockGallery.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockGallery=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockGallery", lockSetting);
//        editor.commit();
    }

    private void lockFolder(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Folder").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Folder", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Folder"));

        Constant.lockCall = lockSetting;


//
//        if (sharedPreferences.getBoolean("lockFolder", false)) {
//            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockFolder.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockFolder=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockFolder", lockSetting);
//        editor.commit();
    }

    private void lockBazaar(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Bazaar").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Bazaar", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Bazaar"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockBazaar", false)) {
//            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockBazaar.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockBazaar=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockBazaar", lockSetting);
//        editor.commit();
    }

    private void lockGmail(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


//        package name Gmail ---- com.google.android.gm

        lockSetting = db.readAppAlwaysLockIsLock("Gmail").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Gmail", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Gmail"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockGmail", false)) {
//            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockGmail.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockGmail=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockGmail", lockSetting);
//        editor.commit();
    }

    private void lockChrome(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("Chrome").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("Chrome", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("Chrome"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockChrome", false)) {
//            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockChrome.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockChrome=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockChrome", lockSetting);
//        editor.commit();
    }

    private void lockFaceBook(View v) {
//        SharedPreferences sharedPreferences =
//                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("FaceBook").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("FaceBook", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("FaceBook"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockFaceBook", false)) {
//            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockFaceBook.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockFaceBook=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockFaceBook", lockSetting);
//        editor.commit();
    }

    private void lockGooglePlay(View v) {
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        boolean lockSetting = false;


        lockSetting = db.readAppAlwaysLockIsLock("GooglePlay").equals("YES");
        Log.i("readAppIsLock", " " + lockSetting);

        if (lockSetting) {
            lockSetting = false;
            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_open_24);
        } else {
            lockSetting = true;
            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_24);
        }
        Log.i("readAppIsLock", " " + lockSetting);

        db.writeAppAlwaysLockIsLock("GooglePlay", (lockSetting ? "YES" : "NO"));
        Log.i("readAppAlwaysLockIsLock", " " + db.readAppAlwaysLockIsLock("GooglePlay"));

        Constant.lockCall = lockSetting;


//        if (sharedPreferences.getBoolean("lockGooglePlay", false)) {
//            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_open_24);
//        } else {
//            lockSetting = true;
//            ivLockGooglePlay.setImageResource(R.drawable.ic_baseline_lock_24);
//        }
//        Constant.lockGooglePlay=lockSetting;
//        @SuppressLint("CommitPrefEdits")
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putBoolean("lockGooglePlay", lockSetting);
//        editor.commit();
    }


    @SuppressLint("NotifyDataSetChanged")
    private void sortApp() {
        Log.i("LockingForDelay", "LockingForDelay - 4");
        ImageView imageView = v.findViewById(R.id.ivSortP);
        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits")
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (sortType.equals("DESC")) {
            editor.putString("sortType", "ASC");
            sortType = "ASC";
            imageView.setImageResource(R.drawable.sort_asc);
        } else {
            editor.putString("sortType", "DESC");
            sortType = "DESC";
            imageView.setImageResource(R.drawable.sort_desc);
        }


        filterProgram(tabFilter);

        adapterAppList.notifyDataSetChanged();
        editor.apply();
        Log.i("LockingForDelay", "LockingForDelay - 8");
    }

    private void filterProgram(int tabFilter) {

        Log.i("LockingForDelay", "LockingForDelay - 9");

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
            expand.add(i, false);
            appIsLock.add(db.readAppIsLock(Constant.appNameList.get(i)).equals("YES"));
            Constant.appIconList.add(db.readAppPackageName(Constant.appNameList.get(i)));
            edtAuthTime.add(db.readAppAuthTextTime(Constant.appNameList.get(i)));
            edtStartTime.add(db.readAppStartTextTime(Constant.appNameList.get(i)));
            authTimeMilliSecond.add(db.readAppAuthMilliSecondTime(Constant.appNameList.get(i)));
            startTimMilliSecond.add(Utils.readAppStartMilliSecondTimeFromHourMinute(Constant.appNameList.get(i), db));
            usedTime.add(db.readAppUsedTime(Constant.appNameList.get(i)));

        }


        tvProgramCount.setText(String.valueOf(Constant.appNameList.size()));
        appRecyclerListUpdate(
                Constant.appNameList,
                Constant.appIconList,
                appIsLock,
                edtAuthTime,
                edtStartTime,
                usedTime);


        Log.i("LockingForDelay", "LockingForDelay - 10");

    }

    @SuppressLint("SetTextI18n")
    private void setAuthTimePicker(int position) {

        Log.i("LockingForDelay", "LockingForDelay - 13");

        String appLable = Constant.appNameList.get(position).trim();

        int tempUsedAppMinute = db.readAppUsedTime(appLable);
        int tempUsedAppHour = 0;
        do {
            if (tempUsedAppMinute > 59) {
                tempUsedAppHour++;
                tempUsedAppMinute -= 60;
            }

        } while (tempUsedAppMinute > 59);


        Calendar calendar = Calendar.getInstance();
        Long currentTimeMiliSecond = calendar.getTimeInMillis();
        calendar.set(Calendar.HOUR, tempUsedAppHour);
        calendar.set(Calendar.MINUTE, tempUsedAppMinute);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);


        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();


        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourAuth = materialTimePicker.getHour();
                minuteAuth = materialTimePicker.getMinute();
                String st = hourAuth + " : " + minuteAuth;
                edtAuthTime.add(position, st.trim());

                calendar.set(Calendar.HOUR, hourAuth);
                calendar.set(Calendar.MINUTE, minuteAuth);
                db.writeAppAuthTimeText(Constant.appNameList.get(position).trim(), st.trim());
                db.writeAppAuthTime(Constant.appNameList.get(position).trim(),
                        (((long) hourAuth * 60 * 60 * 1000) + ((long) minuteAuth * 60 * 1000)));
                adapterAppList.notifyItemChanged(position);

            }
        });

        materialTimePicker.show(getChildFragmentManager(), "زمان مجاز استفاده از برنامه را مشخص کنید.");
        Log.i("LockingForDelay", "LockingForDelay - 14");

    }

    private void setStartTimePicker(int position) {
        Log.i("LockingForDelay", "LockingForDelay - 15");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourStart = materialTimePicker.getHour();
                minuteStart = materialTimePicker.getMinute();
                String st = hourStart + " : " + minuteStart;
                edtStartTime.add(position, st.trim());

                calendar.set(Calendar.HOUR, hourStart);
                calendar.set(Calendar.MINUTE, minuteStart);
                long startTime = (System.currentTimeMillis() + ((long) (hourStart - hour) * 1000 * 60 * 60) + ((long) (minuteStart - minute) * 1000 * 60));
                db.writeAppStartTimeText(Constant.appNameList.get(position).trim(), st.trim());
                db.writeAppStartTime(Constant.appNameList.get(position).trim(), startTime);
                db.writeAppStartTime(Constant.appNameList.get(position).trim(), hourStart, minuteStart);
                adapterAppList.notifyItemChanged(position);


            }
        });

        materialTimePicker.show(getChildFragmentManager(), "برنامه از چه ساعتی مجاز می شود ؟");

        Log.i("LockingForDelay", "LockingForDelay - 16");

    }

    private boolean saveAppLockStatus(View view, int position) {
        Log.i("LockingForDelay", "LockingForDelay - 17");
        ImageView ivActivate = view.findViewById(R.id.ivActivate);
        boolean check = appIsLock.get(position);

        check = !check;
        Log.i("readAppIsLock", " " + Constant.appNameList.get(position).trim());

        db.writeAppIsLock(Constant.appNameList.get(position).trim(), (check ? "YES" : "NO"));
        appIsLock.add(position, check);

        if (check) {
            ivActivate.setImageResource(R.drawable.ic_baseline_lock_24);


//            isLockActive();

        } else {
            ivActivate.setImageResource(R.drawable.ic_baseline_lock_open_24);
        }

        adapterAppList.notifyItemChanged(position);
        Log.i("LockingForDelay", "LockingForDelay - 18");
        return check;
    }

    private void isLockActive() {
        Log.i("LockingForDelay", "LockingForDelay - 11");
//        if (Constant.startServiceFlag && Constant.accessEnabled && Constant.bringTopEnabled)
//        {
        if (Utils.checkDrawOverlayPermission(v.getContext()) &&
//                    Utils.isAppDeviceAdmin(v) &&
                (Utils.getUsageStatsPermissionsStatus(getContext()) == FragmentSetting.PermissionStatus.GRANTED) &&
                !Utils.isMyServiceRunning(v.getContext(), KidsLockService.class)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
//            alertDialog.setTitle("محافظ غیر فعال است");


            alertDialog.setMessage("با فشردن دکمه موافق محافظ  فعال می شود در غیر این صورت از طریق منوی تنظیمات مراحل فعال کردن محافظ را انجام دهید.");

            alertDialog.setPositiveButton("موافق", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Utils.startService(v.getContext());
                    db.writeAppSetting("service", "YES", 10);
                    db.writeAppSetting("logging", "YES", 11);
//                    SharedPreferences sharedPreferences =
//                            v.getContext().getSharedPreferences("startService", MODE_PRIVATE);
//                    @SuppressLint("CommitPrefEdits")
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putBoolean("service", true);
//                    editor.putBoolean("logging", true);
//                    editor.commit();
                }
            });

//            else {
//                alertDialog.setMessage("برای فعال شدن محافظ به صفحه تنظیمات رجوع کنید");
//            }

            alertDialog.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alertDialog.show();
//        }
        }


        Log.i("LockingForDelay", "LockingForDelay - 12");
    }

    private void showHelp() {

        SharedPreferences sharedPreferences =
                v.getContext().getSharedPreferences("startService", MODE_PRIVATE);

        boolean showHelpSetting = sharedPreferences.getBoolean("showHelpProgram", false);

        if (!showHelpSetting) {

            EditText editText = v.findViewById(R.id.edtSearchProgram);
            ImageView imageView2 = v.findViewById(R.id.ivSortP);
            TabLayout tabLayout = v.findViewById(R.id.tblFilterP);
            RecyclerView recyclerView = v.findViewById(R.id.rvAppListP);

            item1 = new FancyShowCaseView.Builder(requireActivity())
                    .focusOn(editText)
                    .focusCircleRadiusFactor(0.1)
                    .focusCircleAtPosition(60, 60, 100)
                    .backgroundColor(getResources().getColor(R.color.help_search_icon))
                    .title("جستجوی برنامه ها")
                    .build();
            item2 = new FancyShowCaseView.Builder(requireActivity())
                    .focusOn(imageView2)
                    .title("مرتب سازی صعودی و نزولی")
                    .backgroundColor(getResources().getColor(R.color.help_sort_icon))
                    .build();

            new FancyShowCaseQueue()
                    .add(item1)
                    .add(item2)
                    .show();

            @SuppressLint("CommitPrefEdits")
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("showHelpProgram", true);
            editor.commit();
        }

    }

    private void expandPhoneLockSetting() {
        if (epPhoneLockSetting.isExpanded()) {
            epPhoneLockSetting.setExpanded(false);
            ivPhoneLockSettings.setImageResource(R.drawable.ic_baseline_navigate_before_24);
        } else {
            epPhoneLockSetting.setExpanded(true);
            ivPhoneLockSettings.setImageResource(R.drawable.ic_baseline_clear_24);
        }
    }

    private void expandProgramLockSetting() {
        if (epProgramLockSetting.isExpanded()) {
            cvLockPhone.setVisibility(View.VISIBLE);
            epProgramLockSetting.setExpanded(false);
            ivPhoneLockSettings.setImageResource(R.drawable.ic_baseline_navigate_before_24);
            ivProgramLockSettings.setImageResource(R.drawable.ic_baseline_navigate_before_24);
        } else {
            cvLockPhone.setVisibility(View.GONE);
            epPhoneLockSetting.setExpanded(false);
            epProgramLockSetting.setExpanded(true);
            ivProgramLockSettings.setImageResource(R.drawable.ic_baseline_clear_24);
        }
    }

    private void resetPhoneLockTime(View v) {
        Log.i("LockingForDelay", "LockingForDelay - 25");
//        db.writePhoneAuthTime("24H:60m", 0);
//        db.writePhoneStartTime("24H:60m", 0);
//        db.writePhoneStartTime("24H:60m", 0, 0);
//        db.writePhoneEndTime("24H:60m", 0);
//        db.writePhoneEndTime("24H:60m", 0, 0);

        db.writePhoneAuthTime("تنظیم نشده", 0);
        db.writePhoneStartTime("تنظیم نشده", 0);
        db.writePhoneStartTime("تنظیم نشده", 0, 0);
        db.writePhoneEndTime("تنظیم نشده", 0);
        db.writePhoneEndTime("تنظیم نشده", 0, 0);
        tvPhoneAuthTime.setText(db.readPhoneAuthTim());
        tvPhoneStartTime.setText(db.readPhoneStartTim());
        tvPhoneEndTime.setText(db.readPhoneEndTim());
        Log.i("LockingForDelay", "LockingForDelay - 26");
        Log.i("long_startTime", " " + " " + hourStart + " " + minuteStart);
        lockAllDisable(v);
    }

    private void authPhoneTimeSave() {

        Log.i("LockingForDelay", "LockingForDelay - 19");


        phoneUsedTime = db.readPhoneUsedTim();
        tempUsedPhoneMinute = phoneUsedTime;
        tempUsedPhoneHour = 0;
        do {
            if (tempUsedPhoneMinute > 59) {
                tempUsedPhoneHour++;
                tempUsedPhoneMinute -= 60;
            }

        } while (tempUsedPhoneMinute > 59);


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, tempUsedPhoneHour);
        calendar.set(Calendar.MINUTE, tempUsedPhoneMinute);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TextView tvAuthPhone = v.findViewById(R.id.tvAuthPhone);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourAuthPhone = materialTimePicker.getHour();
                minuteAuthPhone = materialTimePicker.getMinute();
                String st = hourAuthPhone + " : " + minuteAuthPhone;

                db.writePhoneAuthTime(st,
                        (((long) hourAuthPhone * 60 * 60 * 1000) + ((long) minuteAuthPhone * 60 * 1000)));

                tvPhoneAuthTime.setText(db.readPhoneAuthTim());
                lockAllEnable(v);
            }
        });

        materialTimePicker.show(getChildFragmentManager(), "زمان مجاز استفاده از تلفن همراه را مشخص کنید.");
//        isLockActive();
        Log.i("LockingForDelay", "LockingForDelay - 20");

    }

    private void startPhoneTimeSave() {
        Log.i("LockingForDelay", "LockingForDelay - 21");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourStart = materialTimePicker.getHour();
                minuteStart = materialTimePicker.getMinute();
                String st = hourStart + " : " + minuteStart;


                long startTime = (System.currentTimeMillis() + ((long) (hourStart - hour) * 1000 * 60 * 60) + ((long) (minuteStart - minute) * 1000 * 60));

                db.writePhoneStartTime(st, startTime);
                db.writePhoneStartTime(st, hourStart, minuteStart);

                tvPhoneStartTime.setText(db.readPhoneStartTim());
                lockAllEnable(v);
                Log.i("long_startTime", " " + startTime + " " + hourStart + " " + minuteStart);
            }
        });

        materialTimePicker.show(getChildFragmentManager(), "زمان مجاز استفاده از تلفن همراه را مشخص کنید.");

//        isLockActive();
        Log.i("LockingForDelay", "LockingForDelay - 22");
    }

    private void endPhoneTimeSave() {
        Log.i("LockingForDelay", "LockingForDelay - 23");
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
//        TextView tvAuthPhone = v.findViewById(R.id.tvAuthPhone);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourStart = materialTimePicker.getHour();
                minuteStart = materialTimePicker.getMinute();
                String st = hourStart + " : " + minuteStart;

//                calendar.set(Calendar.HOUR, hourAuth);
//                calendar.set(Calendar.MINUTE, minuteAuth);

                long endTime = (System.currentTimeMillis() + ((long) (hourStart - hour) * 1000 * 60 * 60) + ((long) (minuteStart - minute) * 1000 * 60));

                db.writePhoneEndTime(st, endTime);
                db.writePhoneEndTime(st, hourStart, minuteStart);

                tvPhoneEndTime.setText(db.readPhoneEndTim());
                lockAllEnable(v);
            }
        });

        materialTimePicker.show(getChildFragmentManager(), "زمان مجاز استفاده از تلفن همراه را مشخص کنید.");

//        isLockActive();
        Log.i("LockingForDelay", "LockingForDelay - 24");
    }

    private void writeAppUsedTimeToDataBase() {
//        Log.i("states","start");

        calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

//        calendarEnd = Calendar.getInstance();
//        calendarEnd.setTimeInMillis(System.currentTimeMillis());


        UsageStatsManager usageStatsManager =
                (UsageStatsManager) v.getContext().getSystemService(Context.USAGE_STATS_SERVICE);

        List<UsageStats> states = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY,
                calendarStart.getTimeInMillis(),
                System.currentTimeMillis());

        PackageManager pm = requireActivity().getPackageManager();
        ApplicationInfo applicationInfo = null;
        String lable = "";
        int appUsedTime = 0;
        int j = 1;
        for (int i = 0; i < states.size(); i++) {
            try {
                appUsedTime = (int) states.get(i).getTotalTimeInForeground() / 60000;
                if (((appUsedTime) > 0)) {
                    applicationInfo = pm.getApplicationInfo(states.get(i).getPackageName(), 0);
                    lable = (String) pm.getApplicationLabel(applicationInfo);
                    if (db.appExistInDB(lable)) {
                        db.writeAppUsedTime(lable, appUsedTime);
                        Log.i("states", lable + ":" + appUsedTime);
                    }
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        Log.i("states", "End");

    }
}
