package ir.etelli.kids.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ir.etelli.kids.Adapter.AdapterAppList;
import ir.etelli.kids.Utils.Constant;
import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.R;
import ir.etelli.kids.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentKids#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentKids extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    AdapterAppList adapterAppList;
    View v;
    DataBase db;
    int tabFilter;


    //    ArrayList<String> appNameList = new ArrayList<>();
//    ArrayList<String> appIconList = new ArrayList<>();
    ArrayList<Boolean> appIsLock = new ArrayList<>();
    ArrayList<String> edtAuthTime = new ArrayList<>();
    ArrayList<String> edtStartTime = new ArrayList<>();

    ArrayList<Long> authTimeMilliSecond = new ArrayList<>();
    ArrayList<Long> startTimMilliSecond = new ArrayList<>();
    ArrayList<Integer> usedTime = new ArrayList<>();
    String sortType = "DESC";

    ArrayList<Boolean> expand = new ArrayList<>();


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentKids() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentKids.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentKids newInstance(String param1, String param2) {
        FragmentKids fragment = new FragmentKids();
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

    @Override
    public void onResume() {
        super.onResume();
        kidsProgramList();

        searchAppList();
    }

    private void searchAppList() {

        EditText edtSearch = v.findViewById(R.id.edtSearchProgram);
        edtSearch.addTextChangedListener(new TextWatcher() {
            String tempSearch;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

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
//                    startTimMilliSecond.add(db.readAppStartMilliSecondTime(Constant.appNameList.get(i)));
                    startTimMilliSecond.add(Utils.readAppStartMilliSecondTimeFromHourMinute(Constant.appNameList.get(i), db));
                    usedTime.add(db.readAppUsedTime(Constant.appNameList.get(i)));
                }


                appRecyclerListUpdate(
                        Constant.appNameList,
                        Constant.appIconList,
                        appIsLock,
                        edtAuthTime,
                        edtStartTime,
                        usedTime);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        v = inflater.inflate(R.layout.fragment_kids, container, false);
        db = new DataBase(v.getContext());
        db.openDB();


//        kidsProgramList();

        return v;
    }


    void kidsProgramList() {

        Constant.appNameList.clear();
        Constant.appIconList.clear();
        appIsLock.clear();
        edtAuthTime.clear();
        edtStartTime.clear();
        edtAuthTime.clear();
        authTimeMilliSecond.clear();
        startTimMilliSecond.clear();
        usedTime.clear();

        Constant.appNameList = db.searchAppListLabel("", 2, sortType);

        for (int i = 0; i < Constant.appNameList.size(); i++) {
            expand.add(i, false);
            appIsLock.add(db.readAppIsLock(Constant.appNameList.get(i)).equals("YES"));
            Constant.appIconList.add(db.readAppPackageName(Constant.appNameList.get(i)));
            edtAuthTime.add(db.readAppAuthTextTime(Constant.appNameList.get(i)));
            edtStartTime.add(db.readAppStartTextTime(Constant.appNameList.get(i)));
            authTimeMilliSecond.add(db.readAppAuthMilliSecondTime(Constant.appNameList.get(i)));
//            startTimMilliSecond.add(db.readAppStartMilliSecondTime(Constant.appNameList.get(i)));
            startTimMilliSecond.add(Utils.readAppStartMilliSecondTimeFromHourMinute(Constant.appNameList.get(i), db));
            usedTime.add(db.readAppUsedTime(Constant.appNameList.get(i)));
        }

        appRecyclerListUpdate(
                Constant.appNameList,
                Constant.appIconList,
                appIsLock,
                edtAuthTime,
                edtStartTime,
                usedTime);
    }


    @SuppressLint("NotifyDataSetChanged")
    private void appRecyclerListUpdate(ArrayList<String> appNameList_,
                                       ArrayList<String> appIconList_,
                                       ArrayList<Boolean> appIsLock_,
                                       ArrayList<String> edtAuthTime_,
                                       ArrayList<String> edtStartTime_,
                                       ArrayList<Integer> usedTime_) {

        RecyclerView recyclerView;
        recyclerView = v.findViewById(R.id.rvAppListKids);

        adapterAppList = new AdapterAppList(v.getContext(),
                appNameList_,
                appIconList_,
                appIsLock_,
                edtAuthTime_,
                edtStartTime_,
                usedTime_,
                expand);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapterAppList);

        adapterAppList.setOnClickListener(new AdapterAppList.OnClick() {
            @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
            @Override
            public void setOnClickInterface(View view, int position) {

//                switch (view.getId()) {
//                    case R.id.tvAuthAppTime:
////                        setAuthTimePicker(position);
//                        break;
//                    case R.id.tvStartedAppTime:
////                        setStartTimePicker(position);
//                        break;
//                    case R.id.ivActivate:
////                        saveAppLockStatus(view, position);
//                        break;
//                }

                int appAuth = (int) (db.readAppAuthMilliSecondTime(Constant.appNameList.get(position)) / 60000);
                int timeSumm = (appAuth - (db.readAppUsedTime(Constant.appNameList.get(position))));

//                long appStart = db.readAppStartMilliSecondTime(Constant.appNameList.get(position));
                long appStart = Utils.readAppStartMilliSecondTimeFromHourMinutePackage(
                        db.readAppPackageName(Constant.appNameList.get(position)), db);
                long startTime = appStart - System.currentTimeMillis();

//                Log.i("appLableUsed", "" + db.readAppPackageName(Constant.appNameList.get(position)));
//                Log.i("appLableUsed", "" + Constant.appNameList.get(position));
//                Log.i("appLableUsed", "" + db.readAppUsedTime(Constant.appNameList.get(position)));
//                Log.i("appLableUsed", "" + timeSumm);
//                Log.i("appLableUsed", "" + appAuth);
//                Log.i("appLableUsed", "" + startTime);

                if (db.readAppIsLockPackage(db.readAppPackageName(Constant.appNameList.get(position))).equals("NO")) {

                    Intent intent = v.getContext().
                            getPackageManager().
                            getLaunchIntentForPackage(db.readAppPackageName(Constant.appNameList.get(position)));
                    try {
                        startActivity(intent);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "اجرای این برنامه ممکن نیست", Toast.LENGTH_SHORT).show();
                    }
                } else if (db.readAppIsLockPackage(db.readAppPackageName(Constant.appNameList.get(position))).equals("YES")) {

                    if (appAuth == 0) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.alert_dialog_emoji);
                        TextView tvAlert = dialog.findViewById(R.id.tvAlert);
                        tvAlert.setText(" برنامه قفل است");
                        dialog.findViewById(R.id.ivEmoji).
                                startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.emoji_infinit_move));
                        dialog.show();

                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setMessage(" برنامه قفل است");
                        alert.setPositiveButton("بسیار خوب", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.show();
                    }

                    if (startTime > 0) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.alert_dialog_emoji);
                        TextView tvAlert = dialog.findViewById(R.id.tvAlert);
                        tvAlert.setText(((startTime / 60000) + 1) + " دقیقه به آزاد شدن برنامه باقیست");
                        dialog.findViewById(R.id.ivEmoji).
                                startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.emoji_infinit_move));
                        dialog.show();

                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setMessage(((startTime / 60000) + 1) + " دقیقه به آزاد شدن برنامه باقیست");
                        alert.setPositiveButton("بسیار خوب", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.show();
                    }

                    if (timeSumm < 0) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.alert_dialog_emoji);
                        TextView tvAlert = dialog.findViewById(R.id.tvAlert);
                        tvAlert.setText("زمان استفاده از برنامه به پایان رسیده است");
                        dialog.findViewById(R.id.ivEmoji).
                                startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.emoji_infinit_move));
                        dialog.show();

                        AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                        alert.setMessage("زمان استفاده از برنامه به پایان رسیده است");
                        alert.setPositiveButton("بسیار خوب", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        alert.show();
                    }

                    if (startTime < 0 && timeSumm > 0) {
                        Intent intent = v.getContext().
                                getPackageManager().
                                getLaunchIntentForPackage(db.readAppPackageName(Constant.appNameList.get(position)));
                        try {
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(getContext(), "اجرای این برنامه ممکن نیست", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

            }
        });

    }


}