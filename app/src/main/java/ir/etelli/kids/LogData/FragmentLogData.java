package ir.etelli.kids.LogData;

import android.annotation.SuppressLint;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.R;
import ir.etelli.kids.Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLogData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLogData extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    DataBase db;
    View v;
    Calendar calendarStart;
    Calendar calendarEnd;
    int hourStartTime;
    int minuteStartTime;
    int hourEndTime;
    int minuteEndTime;
    TextView tvStartTime;
    TextView tvEndTime;
    int phoneUsedTime = 0;
    ArrayList<BarEntry> entries0 = new ArrayList<>();
    ArrayList<String> labels0 = new ArrayList<String>();
    SeekBar seekBarBarChart;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentLogData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLogData.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLogData newInstance(String param1, String param2) {
        FragmentLogData fragment = new FragmentLogData();
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_log_data, container, false);

        v.findViewById(R.id.tvStartTime).setOnClickListener(this);
        v.findViewById(R.id.tvEndTime).setOnClickListener(this);
        seekBarBarChart = v.findViewById(R.id.seekBarBarChart);
        tvStartTime = v.findViewById(R.id.tvStartTime);
        tvEndTime = v.findViewById(R.id.tvEndTime);


        calendarStart = Calendar.getInstance();
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        calendarStart.set(Calendar.MILLISECOND, 0);

        calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(System.currentTimeMillis());


        int tempMinute = calendarStart.get(Calendar.MINUTE);
        int tempHour = calendarStart.get(Calendar.HOUR_OF_DAY);
        do {
            if (tempMinute > 59) {
                tempHour++;
                tempMinute -= 60;
            }

        } while (tempMinute > 59);

        String st = tempHour + " : " + tempMinute;
        tvStartTime.setText(st);


        tempMinute = calendarEnd.get(Calendar.MINUTE);
        tempHour = calendarEnd.get(Calendar.HOUR_OF_DAY);
        do {
            if (tempMinute > 59) {
                tempHour++;
                tempMinute -= 60;
            }

        } while (tempMinute > 59);

        st = tempHour + " : " + tempMinute;
        tvEndTime.setText(st);


//        ArrayList<String> logDataView = new ArrayList<>();

//        UsageStatsManager usageStatsManager = (UsageStatsManager) v.getContext().getSystemService(Context.USAGE_STATS_SERVICE);
//
//        List<UsageStats> states = usageStatsManager.queryUsageStats(
//                UsageStatsManager.INTERVAL_DAILY,
//                calendarStart.getTimeInMillis(),
//                calendarEnd.getTimeInMillis());
//
//
//        entries0.clear();
//        labels0.clear();
//
//
//        PackageManager pm = requireActivity().getPackageManager();
//        ApplicationInfo applicationInfo = null;
//        String lable = "";
//        int j =0;
//        for (int i = 0; i < states.size(); i++) {
//            try {
//                applicationInfo = pm.getApplicationInfo(states.get(i).getPackageName(),0);
//                lable = (String) pm.getApplicationLabel(applicationInfo);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            int usedTime = (int) states.get(i).getTotalTimeInForeground();
////            Log.i("states",lable
////                    + ":" + states.get(i).getPackageName()
////                    + ":" + states.get(i).getTotalTimeInForeground()
////                    + ":" + states.get(i).getLastTimeUsed()
////                    + ":" + states.get(i).getFirstTimeStamp()
////                    + ":" + states.get(i).getLastTimeStamp());
//
////            String lable = states.get(i).getPackageName();
//            if (usedTime>0){
//                usedTime = (usedTime/60000);
//                entries0.add(new BarEntry(j, usedTime));
//                labels0.add(lable);
//                j++;
//            }
//
////                    Log.i("clickListener", "" + view.getId() + ": " + position + "Count :" + cursor.getCount() +
////                            "(" + labels0.get(i - 1) + ")" +
////                            "(" + entries0.get(i - 1) + ")");
//        }


        logChartShow(calendarStart, calendarEnd);


//        BarChart barChart = v.findViewById(R.id.barChartID);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            barChart.setNestedScrollingEnabled(true);
//        }
//
//        seekBarBarChart.setMax(entries0.size());
//        seekBarBarChart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                barChart.moveViewToX(i);
//                seekBar.invalidate();
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
//
//        barChart.setOnChartGestureListener(new OnChartGestureListener() {
//            @Override
//            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//
//
//            }
//
//            @Override
//            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//
//            }
//
//            @Override
//            public void onChartLongPressed(MotionEvent me) {
//
//            }
//
//            @Override
//            public void onChartDoubleTapped(MotionEvent me) {
//
//            }
//
//            @Override
//            public void onChartSingleTapped(MotionEvent me) {
//
//            }
//
//            @Override
//            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//
//            }
//
//            @Override
//            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//
//            }
//
//            @Override
//            public void onChartTranslate(MotionEvent me, float dX, float dY) {
//                float lowX = barChart.getLowestVisibleX();
//                float xrange = barChart.getVisibleXRange(); //better to fix x-ranges, getVisibleXRange may be inaccurate
//                float centerX = lowX + xrange / 2;
////                        Log.i("onProgressChanged",":" +   Math.round(centerX) );
//                seekBarBarChart.setProgress(Math.round(centerX));
//            }
//        });
//
//        TextView tvCountChartApp = v.findViewById(R.id.tvCountChartApp);
//        tvCountChartApp.setText(String.valueOf(entries0.size()));
//
//        BarDataSet bardataset = new BarDataSet(entries0, "");
//        BarData data = new BarData(bardataset);
//        barChart.setData(data); // set the data and list of lables into chart
//        Description description = new Description();
//        description.setText("نمودار را به چپ و راست حرکت دهید");
//        barChart.setDescription(description);  // set the description
//
//        barChart.setVisibleXRangeMaximum(8);
//
//        XAxis xAxis = barChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setGranularity(1f);
//        xAxis.setGranularityEnabled(true);
//        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
//        xAxis.setLabelRotationAngle(90);
//        xAxis.setValueFormatter(new ValueFormatter() {
//            @Override
//            public String getFormattedValue(float value) {
////                        Log.i("clickListener","labels0"+labels0.get(((int) value))) ;
//                if ((int) value < entries0.size() && (int) value > 0) {
////                            Log.i("clickListener", "value" + (int) value);
//                    return labels0.get((int) value - 1);
//                } else {
////                            Log.i("clickListener", "value" + (int) value);
//                    return labels0.get(0);
//                }
//            }
//        });
//        barChart.animateY(2000);
//        barChart.invalidate();


//
////        for (int i=0; i<cursor3.getCount(); i++){
////            lable.add(cursor3.getString(1)) ;
////            Year.add(cursor3.getString(2)) ;
////            Month.add(cursor3.getString(3)) ;
////            Day.add(cursor3.getString(4)) ;
////            usedTime.add(cursor3.getString(5)) ;
////            Log.i("Cursor3", " : " +
////                    lable + " : " +
////                    Year + ":" +
////                    Month+ ":" +
////                    Day+ ":" +
////                    "(" + usedTime + ")");
////            logDataView.add(Year+"/"+Month.get(i) +"/"+Day.get(i) + " : (" + lable.get(i) + " " + usedTime.get(i) + ")");
////
////            cursor3.moveToNext();
////
////        }
//
//
////        for (int i=0; i<10; i++){
////            lable.clear();
////            Year.clear();
////            Month.clear();
////            Day.clear();
////            usedTime.clear();
////
////            lable.add("Lable"+i) ;
////            Year.add("Y"+i) ;
////            Month.add("M"+i) ;
////            Day.add("D"+i) ;
////            usedTime.add("u"+i) ;
////            Log.i("Cursor3", " : " +
////                    lable + " : " +
////                    Year + ":" +
////                    Month+ ":" +
////                    Day+ ":" +
////                    "(" + usedTime + ")");
////            logDataView.add(Year+"/"+Month.get(i) +"/"+Day.get(i) + " : (" + lable.get(i) + " " + usedTime.get(i) + ")");
////
////            cursor3.moveToNext();
////
////        }
//
////        lable.add("WhatsApp"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("20") ;
////        lable.add("Instagram"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("23") ;
////        lable.add("IPS"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("44") ;
////        lable.add("بازار"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("67") ;
////        lable.add("SHAREit"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("11") ;
////        lable.add("باد صبا"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("1") ;
////        lable.add("جدولانه"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("73") ;
////        lable.add("Skype"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("137") ;
////        lable.add("My MCI"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("3") ;
////        lable.add("504 Word"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("5") ;
////        lable.add("زبان شناس"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("18") ;
////        lable.add("TED"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("43") ;
////        lable.add("IELTS"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("63") ;
////        lable.add("Note"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("75") ;
////        lable.add("CastBox"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("27") ;
////        lable.add("Penglish"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("94") ;
////        lable.add("Namava"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("11") ;
////        lable.add("Poweramp"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("96") ;
////        lable.add("فرهنگ لغت"); Year.add("2019") ; Month.add("3") ; Day.add("17") ; usedTime.add("34") ;
////
////
////
////        lable.add("WhatsApp"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("12") ;
////        lable.add("Instagram"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("123") ;
////        lable.add("IPS"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("4") ;
////        lable.add("بازار"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("27") ;
////        lable.add("SHAREit"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("31") ;
////        lable.add("باد صبا"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("19") ;
////        lable.add("جدولانه"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("3") ;
////        lable.add("Skype"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("17") ;
////        lable.add("My MCI"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("49") ;
////        lable.add("504 Word"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("50") ;
////        lable.add("زبان شناس"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("218") ;
////        lable.add("TED"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("93") ;
////        lable.add("IELTS"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("3") ;
////        lable.add("Note"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("7") ;
////        lable.add("CastBox"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("2") ;
////        lable.add("Penglish"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("4") ;
////        lable.add("Namava"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("1") ;
////        lable.add("Poweramp"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("16") ;
////        lable.add("فرهنگ لغت"); Year.add("2020") ; Month.add("7") ; Day.add("3") ; usedTime.add("41") ;
////
////
////
////        lable.add("WhatsApp"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("23") ;
////        lable.add("Instagram"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("13") ;
////        lable.add("IPS"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("11") ;
////        lable.add("بازار"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("37") ;
////        lable.add("SHAREit"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("41") ;
////        lable.add("باد صبا"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("9") ;
////        lable.add("جدولانه"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("13") ;
////        lable.add("Skype"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("17") ;
////        lable.add("My MCI"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("53") ;
////        lable.add("504 Word"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("51") ;
////        lable.add("زبان شناس"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("118") ;
////        lable.add("TED"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("73") ;
////        lable.add("IELTS"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("3") ;
////        lable.add("Note"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("7") ;
////        lable.add("CastBox"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("127") ;
////        lable.add("Penglish"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("14") ;
////        lable.add("Namava"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("19") ;
////        lable.add("Poweramp"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("6") ;
////        lable.add("فرهنگ لغت"); Year.add("2021") ; Month.add("6") ; Day.add("27") ; usedTime.add("134") ;
//
//
////        db.writeAppUsedTimeLog("2019","3","17","Instagram","23",1);
////        db.writeAppUsedTimeLog("2019","3","17","IPS","44",1);
////        db.writeAppUsedTimeLog("2019","3","17","بازار","64",1);
////        db.writeAppUsedTimeLog("2019","3","17","SHAREit","24",1);
////        db.writeAppUsedTimeLog("2019","3","17","باد صبا","35",1);
////        db.writeAppUsedTimeLog("2019","3","17","جدولانه","63",1);
////        db.writeAppUsedTimeLog("2019","3","17","Skype","76",1);
////        db.writeAppUsedTimeLog("2019","3","17","My MCI","32",1);
////        db.writeAppUsedTimeLog("2019","3","17","504 Word","22",1);
////        db.writeAppUsedTimeLog("2019","3","17","زبان شناس","10",1);
////        db.writeAppUsedTimeLog("2019","3","17","TED","31",1);
////        db.writeAppUsedTimeLog("2019","3","17","IELTS","9",1);
////        db.writeAppUsedTimeLog("2019","3","17","Note","31",1);
////        db.writeAppUsedTimeLog("2019","3","17","CastBox","11",1);
////        db.writeAppUsedTimeLog("2019","3","17","Penglish","17",1);
////        db.writeAppUsedTimeLog("2019","3","17","Namava","25",1);
////        db.writeAppUsedTimeLog("2019","3","17","Poweramp","43",1);
////        db.writeAppUsedTimeLog("2019","3","17","فرهنگ لغت","65",1);
////        db.writeAppUsedTimeLog("2019","3","17","WhatsApp","80",1);
////        db.writeAppUsedTimeLog("2019","3","17","OGWhatsApp","22",1);
////
////
////        db.writeAppUsedTimeLog("2020","7","11","Instagram","73",2);
////        db.writeAppUsedTimeLog("2020","7","11","IPS","14",2);
////        db.writeAppUsedTimeLog("2020","7","11","بازار","24",2);
////        db.writeAppUsedTimeLog("2020","7","11","SHAREit","53",2);
////        db.writeAppUsedTimeLog("2020","7","11","باد صبا","32",2);
////        db.writeAppUsedTimeLog("2020","7","11","جدولانه","43",2);
////        db.writeAppUsedTimeLog("2020","7","11","Skype","63",2);
////        db.writeAppUsedTimeLog("2020","7","11","My MCI","12",2);
////        db.writeAppUsedTimeLog("2020","7","11","504 Word","32",2);
////        db.writeAppUsedTimeLog("2020","7","11","زبان شناس","71",2);
////        db.writeAppUsedTimeLog("2020","7","11","TED","25",2);
////        db.writeAppUsedTimeLog("2020","7","11","IELTS","19",2);
////        db.writeAppUsedTimeLog("2020","7","11","Note","23",2);
////        db.writeAppUsedTimeLog("2020","7","11","CastBox","35",2);
////        db.writeAppUsedTimeLog("2020","7","11","Penglish","24",2);
////        db.writeAppUsedTimeLog("2020","7","11","Namava","67",2);
////        db.writeAppUsedTimeLog("2020","7","11","Poweramp","43",2);
////        db.writeAppUsedTimeLog("2020","7","11","فرهنگ لغت","54",2);
////        db.writeAppUsedTimeLog("2020","7","11","WhatsApp","23",2);
////        db.writeAppUsedTimeLog("2020","7","11","OGWhatsApp","57",2);
////
////
////        db.writeAppUsedTimeLog("2021","11","17","Instagram","21",3);
////        db.writeAppUsedTimeLog("2021","11","17","IPS","24",3);
////        db.writeAppUsedTimeLog("2021","11","17","بازار","14",3);
////        db.writeAppUsedTimeLog("2021","11","17","SHAREit","44",3);
////        db.writeAppUsedTimeLog("2021","11","17","باد صبا","25",3);
////        db.writeAppUsedTimeLog("2021","11","17","جدولانه","73",3);
////        db.writeAppUsedTimeLog("2021","11","17","Skype","18",3);
////        db.writeAppUsedTimeLog("2021","11","17","My MCI","176",3);
////        db.writeAppUsedTimeLog("2021","11","17","504 Word","212",3);
////        db.writeAppUsedTimeLog("2021","11","17","زبان شناس","33",3);
////        db.writeAppUsedTimeLog("2021","11","17","TED","87",3);
////        db.writeAppUsedTimeLog("2021","11","17","IELTS","29",3);
////        db.writeAppUsedTimeLog("2021","11","17","Note","36",3);
////        db.writeAppUsedTimeLog("2021","11","17","CastBox","31",3);
////        db.writeAppUsedTimeLog("2021","11","17","Penglish","74",3);
////        db.writeAppUsedTimeLog("2021","11","17","Namava","29",3);
////        db.writeAppUsedTimeLog("2021","11","17","Poweramp","33",3);
////        db.writeAppUsedTimeLog("2021","11","17","فرهنگ لغت","15",3);
////        db.writeAppUsedTimeLog("2021","11","17","WhatsApp","76",3);
////        db.writeAppUsedTimeLog("2021","11","17","OGWhatsApp","26",3);
//
////        ArrayList<String> lable = new ArrayList<>();
////        ArrayList<String> Year = new ArrayList<>();
////        ArrayList<String> Month = new ArrayList<>();
////        ArrayList<String> Day = new ArrayList<>();
////        ArrayList<String> usedTime = new ArrayList<>();
//
//
//        db = new DataBase(v.getContext());
//        db.openDB();
//        Cursor cursor3;
//        cursor3 = db.readAllDayIndexesLog();
////        cursor3 = db.readAppDataFromLogByIndex(3);
////        cursor3 = db.readAppDataFromDateLog("2019","3","17");
//
////                cursor3 = db.readAppDataFromLog();
//        cursor3.moveToFirst();
////        Log.i("Cursor3", " : " + cursor3.getCount() + ";" + db.readLatestDayIndexCountLog());
//
////        for (int i=0; i<cursor3.getCount(); i++){
////            lable.add(cursor3.getString(1)) ;
////            Year.add(cursor3.getString(2)) ;
////            Month.add(cursor3.getString(3)) ;
////            Day.add(cursor3.getString(4)) ;
////            usedTime.add(cursor3.getString(5)) ;
////            Log.i("Cursor3", " : " +
////                    lable + " : " +
////                    Year + ":" +
////                    Month+ ":" +
////                    Day+ ":" +
////                    "(" + usedTime + ")");
////            logDataView.add(Year+"/"+Month.get(i) +"/"+Day.get(i) + " : (" + lable.get(i) + " " + usedTime.get(i) + ")");
////
////            cursor3.moveToNext();
////        }
//
//
//
//
//        cursor3.moveToFirst();
//        for (int i = 0; i < cursor3.getCount(); i++) {
//            int dayIndex = cursor3.getInt(1);
//            String Year = cursor3.getString(2);
//            String Month = cursor3.getString(3);
//            String Day = cursor3.getString(4);
//
//            logDataView.add(Year + "/" + Month + "/" + Day);
//
//            cursor3.moveToNext();
//
//        }
//
//
//        RecyclerView rv = v.findViewById(R.id.rvLogData);
//        AdapterLogData adapterLogData = new AdapterLogData(v.getContext(), logDataView);
//        RecyclerView.LayoutManager layoutManager =
//                new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
//
//        rv.setLayoutManager(layoutManager);
//        rv.setAdapter(adapterLogData);
//
//        adapterLogData.setOnClickListener(new AdapterLogData.OnClickListener() {
//            @Override
//            public void clickListener(View view, int position) {
////                Log.i("clickListener", "" + view.getId() + ": " + position);
////                Toast.makeText(v.getContext(), ""
////                        + view.getId() + ": "
////                        + position, Toast.LENGTH_SHORT).show();
//
//                Cursor cursor =
//                        db.readAppDataFromLogByIndex(position);
//
//                cursor.moveToFirst();
//
//                entries0.clear();
//                labels0.clear();
//
//                for (int i = 1; i <= cursor.getCount(); i++) {
//                    String lable = cursor.getString(3);
//                    int usedTime = db.readAppUsedTimeFromLogByIndex(lable.trim(), position);
//                    entries0.add(new BarEntry(i, usedTime));
//                    labels0.add(lable);
////                    Log.i("clickListener", "" + view.getId() + ": " + position + "Count :" + cursor.getCount() +
////                            "(" + labels0.get(i - 1) + ")" +
////                            "(" + entries0.get(i - 1) + ")");
//                    cursor.moveToNext();
//                }
//
//                BarChart barChart = v.findViewById(R.id.barChartID);
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    barChart.setNestedScrollingEnabled(true);
//                }
//
//                seekBarBarChart.setMax(entries0.size());
//                seekBarBarChart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
//                        barChart.moveViewToX(i);
//                        seekBar.invalidate();
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//
//                    }
//                });
//
//
//                barChart.setOnChartGestureListener(new OnChartGestureListener() {
//                    @Override
//                    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//
//
//                    }
//
//                    @Override
//                    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
//
//                    }
//
//                    @Override
//                    public void onChartLongPressed(MotionEvent me) {
//
//                    }
//
//                    @Override
//                    public void onChartDoubleTapped(MotionEvent me) {
//
//                    }
//
//                    @Override
//                    public void onChartSingleTapped(MotionEvent me) {
//
//                    }
//
//                    @Override
//                    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
//
//                    }
//
//                    @Override
//                    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
//
//                    }
//
//                    @Override
//                    public void onChartTranslate(MotionEvent me, float dX, float dY) {
//                        float lowX = barChart.getLowestVisibleX();
//                        float xrange = barChart.getVisibleXRange(); //better to fix x-ranges, getVisibleXRange may be inaccurate
//                        float centerX = lowX + xrange / 2;
////                        Log.i("onProgressChanged",":" +   Math.round(centerX) );
//                        seekBarBarChart.setProgress(Math.round(centerX));
//                    }
//                });
//
//                TextView tvCountChartApp = v.findViewById(R.id.tvCountChartApp);
//                tvCountChartApp.setText(String.valueOf(entries0.size()));
//
//                BarDataSet bardataset = new BarDataSet(entries0, "");
//                BarData data = new BarData(bardataset);
//                barChart.setData(data); // set the data and list of lables into chart
//                Description description = new Description();
//                description.setText("نمودار را به چپ و راست حرکت دهید");
//                barChart.setDescription(description);  // set the description
//
//                barChart.setVisibleXRangeMaximum(8);
//
//                XAxis xAxis = barChart.getXAxis();
//                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//                xAxis.setGranularity(1f);
//                xAxis.setGranularityEnabled(true);
//                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
//                xAxis.setLabelRotationAngle(90);
//                xAxis.setValueFormatter(new ValueFormatter() {
//                    @Override
//                    public String getFormattedValue(float value) {
////                        Log.i("clickListener","labels0"+labels0.get(((int) value))) ;
//                        if ((int) value < entries0.size() && (int) value > 0) {
////                            Log.i("clickListener", "value" + (int) value);
//                            return labels0.get((int) value - 1);
//                        } else {
////                            Log.i("clickListener", "value" + (int) value);
//                            return labels0.get(0);
//                        }
//                    }
//                });
//                barChart.animateY(2000);
//                barChart.invalidate();
//
//
//            }
//        });
//
//
////        ArrayList<BarEntry> entries = new ArrayList<>();
////        entries.add(new BarEntry(38f, 0));
////        entries.add(new BarEntry(52f, 1));
////        entries.add(new BarEntry(65f, 2));
////        entries.add(new BarEntry(30f, 3));
////        entries.add(new BarEntry(85f, 4));
////        entries.add(new BarEntry(19f, 5));
////        entries.add(new BarEntry(75f, 6));
//
////        entries.add(new BarEntry(1f, 0));
////        entries.add(new BarEntry(2f, 1));
////        entries.add(new BarEntry(3f, 2));
////        entries.add(new BarEntry(4f, 3));
////        entries.add(new BarEntry(5f, 4));
////        entries.add(new BarEntry(6f, 5));
////        entries.add(new BarEntry(7f, 6));
////        entries.add(new BarEntry(8f, 7));
////        entries.add(new BarEntry(9f, 8));
////        entries.add(new BarEntry(10f, 9));
////        ArrayList<String> labels = new ArrayList<String>();
////        labels.add("Mon");
////        labels.add("Tue");
////        labels.add("Wed");
////        labels.add("Thus");
////        labels.add("Fri");
////        labels.add("Sat");
////        labels.add("Sun");
////        labels.add("Mon");
////        labels.add("Tue");
////        labels.add("Wed");
////        Cursor cursor2 = db.readAppAllDayFromLog();
////        int count2 = cursor2.getCount();
////        cursor2.moveToFirst();
////        for (int i=1;i<count2;i++){
////            Log.i("AppLableListFromLogInt", count2 + ":" + cursor2.getInt(0));
////            cursor2.moveToNext();
////        }
////
////
////        int count=8;
////        Cursor cursor = db.readAppLableListFromLog();
////
////        cursor.moveToFirst();
////        for (int i=1;i<=cursor.getCount();i++){
////            Log.i("AppLableListFromLog", cursor.getCount() + ":" + cursor.getString(0) + " : " + db.readAppUsedTimeFromLog(cursor.getString(0)));
////            cursor.moveToNext();
////        }
////
////        cursor.moveToFirst();
////        if (cursor.getCount()<8)
////            count=cursor.getCount();
////
////        cursor.moveToFirst();
////        for (int i=1;i<count;i++){
////            entries.add(new BarEntry(i, db.readAppUsedTimeFromLog(cursor.getString(0))));
////            labels.add(cursor.getString(0));
////            cursor.moveToNext();
////        }
////
////
////        Log.i("appsLog","" + db.readAppCountLog());
//

        return v;
    }


    private void logStartTime() {

//        int tempMinute = 0;
//        int tempHour = 0;
//        do {
//            if (tempMinute > 59) {
//                tempHour++;
//                tempMinute -= 60;
//            }
//
//        } while (tempMinute > 59);


        calendarStart = Calendar.getInstance();
//        calendarStart.set(Calendar.HOUR_OF_DAY, tempHour);
//        calendarStart.set(Calendar.MINUTE, tempMinute);
        int hour = calendarStart.get(Calendar.HOUR_OF_DAY);
        int minute = calendarStart.get(Calendar.MINUTE);


        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourStartTime = materialTimePicker.getHour();
                minuteStartTime = materialTimePicker.getMinute();
                calendarStart.set(Calendar.HOUR_OF_DAY, hourStartTime);
                calendarStart.set(Calendar.MINUTE, minuteStartTime);
                String st = hourStartTime + " : " + minuteStartTime;

//                Log.i("phoneUsedTime", "" + calendarStart.getTimeInMillis()/60000 + ":"+
//                        + calendarEnd.getTimeInMillis()/60000 + ":"+
//                        (calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis())/360000);
                logChartShow(calendarStart, calendarEnd);
//                calendar.set(Calendar.HOUR, hourAuth);
//                calendar.set(Calendar.MINUTE, minuteAuth);
//                db.writePhoneAuthTime(st,
//                        (((long) hourStartTime * 60 * 60 * 1000) + ((long) minuteStartTime * 60 * 1000)));
                tvStartTime.setText(st);
            }
        });

        materialTimePicker.show(getChildFragmentManager(), "زمان شروع گزارش گیری را مشخص کنید.");

    }


    private void logEndTime() {
        calendarEnd = Calendar.getInstance();
        int hour = calendarEnd.get(Calendar.HOUR_OF_DAY);
        int minute = calendarEnd.get(Calendar.MINUTE);

        MaterialTimePicker materialTimePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(minute)
                .build();

        materialTimePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hourEndTime = materialTimePicker.getHour();
                minuteEndTime = materialTimePicker.getMinute();
                String st = hourEndTime + " : " + minuteEndTime;

                calendarEnd.set(Calendar.HOUR_OF_DAY, hourEndTime);
                calendarEnd.set(Calendar.MINUTE, minuteEndTime);
                long startTime = (System.currentTimeMillis() +
                        ((long) (hourEndTime - hour) * 1000 * 60 * 60) +
                        ((long) (minuteEndTime - minute) * 1000 * 60));


                logChartShow(calendarStart, calendarEnd);
//                calendar.set(Calendar.HOUR, hourAuth);
//                calendar.set(Calendar.MINUTE, minuteAuth);

//
//                db.writePhoneStartTime(st, startTime);
                tvEndTime.setText(st);
            }
        });

        materialTimePicker.show(getChildFragmentManager(), "زمان پایان گزارش گیری را مشخص کنید.");

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvStartTime:
                logStartTime();
                break;
            case R.id.tvEndTime:
                logEndTime();
                break;

        }
    }


    public void logChartShow(Calendar calendarStart, Calendar calendarEnd) {

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();
//        SeekBar seekBarBarChart;
//        seekBarBarChart = v.findViewById(R.id.seekBarBarChart);
        UsageStatsManager usageStatsManager =
                (UsageStatsManager) v.getContext().getSystemService(Context.USAGE_STATS_SERVICE);


//        calendarStart.set(Calendar.DATE,calendarStart.get(Calendar.DAY_OF_MONTH)-1);
        calendarStart.add(Calendar.DATE, -1);
//        calendarStart = Calendar.getInstance();
//        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
//        calendarStart.set(Calendar.MINUTE, 0);
//        calendarStart.set(Calendar.SECOND, 0);
//        calendarStart.set(Calendar.MILLISECOND, 0);
//
//        calendarEnd = Calendar.getInstance();
//        calendarEnd.set(Calendar.HOUR_OF_DAY, 23);
//        calendarEnd.set(Calendar.MINUTE, 59);
//        calendarEnd.set(Calendar.SECOND, 59);
//        calendarEnd.set(Calendar.MILLISECOND, 0);


        calendarStart.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));
        calendarEnd.setTimeZone(TimeZone.getTimeZone("Asia/Tehran"));

        Map<String, UsageStats> mapUsageState = usageStatsManager.queryAndAggregateUsageStats(
                calendarStart.getTimeInMillis(),
                calendarEnd.getTimeInMillis());

        phoneUsedTime = 0;
        PackageManager pm = requireActivity().getPackageManager();
        ApplicationInfo applicationInfo = null;
        String lable = "";
        int j = 1;
        for (String key : mapUsageState.keySet()) {

            int usedTime = (int) Objects.requireNonNull(mapUsageState.get(key)).getTotalTimeInForeground();
//            Log.i("states",i +
//                    lable
//                    + ":" + states.get(i).getPackageName()
//                    + ":" + states.get(i).getTotalTimeInForeground()
//                    + ":" + states.get(i).getLastTimeUsed()
//                    + ":" + states.get(i).getFirstTimeStamp()
//                    + ":" + states.get(i).getLastTimeStamp());

//            String lable = states.get(i).getPackageName();


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


                    entries.add(new BarEntry(j, usedTime));
                    labels.add(lable);
                    Log.i("phoneUsedTime", lable + ":" + usedTime + ":" + j);
                    j++;
                }
            }
        }


//        db = new DataBase(v.getContext());
//        db.openDB();
//
//        db.writePhoneUsedTime(phoneUsedTime);

        int tempMinute = phoneUsedTime;
        int tempHour = 0;
        do {
            if (tempMinute > 59) {
                tempHour++;
                tempMinute -= 60;
            }

        } while (tempMinute > 59);
        TextView tvTotalTime = v.findViewById(R.id.tvPhoneTotalTime);
        tvTotalTime.setText(tempHour + " : " + tempMinute);


//        List<UsageStats> states = usageStatsManager.queryUsageStats(
//                UsageStatsManager.INTERVAL_BEST,
//                calendarStart.getTimeInMillis(),
//                calendarEnd.getTimeInMillis());
//
//
//        PackageManager pm = requireActivity().getPackageManager();
//        ApplicationInfo applicationInfo = null;
//        String lable = "";
//        int j = 1;
//        for (int i = 0; i < states.size(); i++) {
//
//            int usedTime = (int) states.get(i).getTotalTimeInForeground();
////            Log.i("states",i +
////                    lable
////                    + ":" + states.get(i).getPackageName()
////                    + ":" + states.get(i).getTotalTimeInForeground()
////                    + ":" + states.get(i).getLastTimeUsed()
////                    + ":" + states.get(i).getFirstTimeStamp()
////                    + ":" + states.get(i).getLastTimeStamp());
//
////            String lable = states.get(i).getPackageName();
//            usedTime = (usedTime / 60000);
//            if (usedTime > 0) {
//                try {
//                    applicationInfo = pm.getApplicationInfo(states.get(i).getPackageName(), 0);
//                    lable = (String) pm.getApplicationLabel(applicationInfo);
//                } catch (PackageManager.NameNotFoundException e) {
//                    e.printStackTrace();
//                }
//                assert applicationInfo != null;
//                if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
//
//                    entries.add(new BarEntry(j, usedTime));
//                    labels.add(lable);
////              Log.i("states",lable +":"+usedTime + ":" + j);
//                    j++;
//                }
//            }
//        }
//
        BarChart barChart = v.findViewById(R.id.barChartID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            barChart.setNestedScrollingEnabled(true);
        }

        seekBarBarChart.setMax(entries.size());
        seekBarBarChart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                barChart.moveViewToX(i);
                seekBar.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        barChart.setOnChartGestureListener(new OnChartGestureListener() {
            @Override
            public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {


            }

            @Override
            public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

            }

            @Override
            public void onChartLongPressed(MotionEvent me) {

            }

            @Override
            public void onChartDoubleTapped(MotionEvent me) {

            }

            @Override
            public void onChartSingleTapped(MotionEvent me) {

            }

            @Override
            public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

            }

            @Override
            public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

            }

            @Override
            public void onChartTranslate(MotionEvent me, float dX, float dY) {
                float lowX = barChart.getLowestVisibleX();
                float xrange = barChart.getVisibleXRange(); //better to fix x-ranges, getVisibleXRange may be inaccurate
                float centerX = lowX + xrange / 2;
//                        Log.i("onProgressChanged",":" +   Math.round(centerX) );
                seekBarBarChart.setProgress(Math.round(centerX));
            }
        });

        TextView tvCountChartApp = v.findViewById(R.id.tvCountChartApp);
        tvCountChartApp.setText(String.valueOf(entries.size()));

        BarDataSet bardataset = new BarDataSet(entries, "");
        BarData data = new BarData(bardataset);
        barChart.setData(data); // set the data and list of lables into chart
        Description description = new Description();
        description.setText("نمودار را به چپ و راست حرکت دهید");
        barChart.setDescription(description);  // set the description

        barChart.setVisibleXRangeMaximum(8);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

        xAxis.setLabelRotationAngle(90);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
//                        Log.i("clickListener","labels0"+labels0.get(((int) value))) ;
                if ((int) value < entries.size() && (int) value > 0) {
//                    Log.i("states1", "value" + (int) value + labels.get((int) value -1));
                    return labels.get((int) value - 1);
                } else {
//                            Log.i("states0", "value" + (int) value + labels.get(0));
                    return labels.get(entries.size() - 1);
                }
            }
        });
        barChart.animateY(2000);
        barChart.invalidate();


    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();


        db = new DataBase(v.getContext());
        db.openDB();


//        calendarStart = Calendar.getInstance();
//            calendarStart.set(Calendar.HOUR_OF_DAY, 0);
//            calendarStart.set(Calendar.MINUTE, 0);
//            calendarStart.set(Calendar.SECOND, 0);
//            calendarStart.set(Calendar.MILLISECOND, 0);
//
//            calendarEnd = Calendar.getInstance();
//            calendarEnd.setTimeInMillis(System.currentTimeMillis());
//
//            UsageStatsManager usageStatsManager =
//                    (UsageStatsManager) v.getContext().getSystemService(Context.USAGE_STATS_SERVICE);
//
//            UsageEvents usageEvents = usageStatsManager.queryEvents(
//                    calendarStart.getTimeInMillis(),calendarEnd.getTimeInMillis());
//
//            if (usageEvents != null){
//            UsageEvents.Event event = new UsageEvents.Event();
//            while (usageEvents.getNextEvent(event))
//
//                Log.i("usageEvents",event.getPackageName()
//                        + ":" + (event.getTimeStamp()/60000));
//            }
//
//

        phoneUsedTime = 0;
//            int appCount = db.readAppCount();
//            for (int i=0 ;i<appCount; i++){
//                String appLable = db.readAppLable(i);
//                phoneUsedTime = phoneUsedTime + db.readAppUsedTime(appLable);
//                Log.i("phoneUsedTime",appLable + " : " + phoneUsedTime );
//            }


//                    List<UsageStats> states = usageStatsManager.queryUsageStats(
//                    UsageStatsManager.INTERVAL_BEST,
//                    calendarStart.getTimeInMillis(),
//                    calendarEnd.getTimeInMillis());
//
//            for (int i = 0; i < states.size(); i++) {
//                phoneUsedTime = phoneUsedTime + (int) states.get(i).getTotalTimeInForeground();
//            }

        phoneUsedTime = Utils.readPhoneUsedTotalTime(v.getContext());
//        db.writePhoneUsedTime(phoneUsedTime);

        int tempMinute = phoneUsedTime;
        int tempHour = 0;
        do {
            if (tempMinute > 59) {
                tempHour++;
                tempMinute -= 60;
            }

        } while (tempMinute > 59);
        TextView tvTotalTime = v.findViewById(R.id.tvPhoneTotalTime);
        tvTotalTime.setText(tempHour + " : " + tempMinute);

    }
}