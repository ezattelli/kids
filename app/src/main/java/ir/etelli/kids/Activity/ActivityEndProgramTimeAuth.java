package ir.etelli.kids.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.MainActivity;
import ir.etelli.kids.R;

public class ActivityEndProgramTimeAuth extends AppCompatActivity {
    TextView tvPhoneStartTime;
    TextView tvPhoneAuthTime;
    TextView tvPhoneUsedTime;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_program_time_auth);

        DataBase db = new DataBase(this);
        db.openDB();

        tvPhoneStartTime = findViewById(R.id.tvPhoneStartTime);
        tvPhoneAuthTime = findViewById(R.id.tvPhoneAuthTime);
        tvPhoneUsedTime = findViewById(R.id.tvPhoneUsedTime);


        Bundle b = getIntent().getExtras();
        String packageName = b.getString("packageName");
        long phoneStartTime = b.getLong("phoneStartTime");
        long phoneAuthTime = b.getLong("phoneAuthTime");
        long phoneUsedTime = b.getLong("phoneUsedTime");


        Calendar calendar = null;
        String startTimeString;
        if ((db.readAppStartTimeHour(db.readAppLabelFromPackageName(packageName)) +
                db.readAppStartTimeMinute(db.readAppLabelFromPackageName(packageName))) > 0) {
            calendar = Calendar.getInstance();

            if (calendar.getTimeInMillis() < phoneStartTime) {
                tvPhoneStartTime.setTextColor(Color.RED);
            } else {
                tvPhoneStartTime.setTextColor(R.color.ButtonTextColor);
            }

            calendar.setTimeInMillis(phoneStartTime);
            startTimeString = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        } else {
            startTimeString = "تنظیم نشده";
        }

        int tempMinute = (int) phoneUsedTime;
        int tempHour = 0;
        do {
            if (tempMinute > 59) {
                tempHour++;
                tempMinute -= 60;
            }
        } while (tempMinute > 59);
        String usedTimeString = tempHour + " : " + tempMinute;

        String authTimeString;
        if (phoneAuthTime > 0) {
            int authMinute = (int) phoneAuthTime;
            int authHour = 0;
            do {
                if (authMinute > 59) {
                    authHour++;
                    authMinute -= 60;
                }
            } while (authMinute > 59);
            authTimeString = authHour + " : " + authMinute;
        } else {
            authTimeString = "تنظیم نشده";
        }

//        Log.i("phoneAuthTime" , phoneAuthTime + ":" + phoneUsedTime);
        if (phoneUsedTime >= phoneAuthTime) {

            tvPhoneAuthTime.setTextColor(Color.RED);
        } else {
            tvPhoneAuthTime.setTextColor(R.color.ButtonTextColor);
        }


        tvPhoneStartTime.setText(startTimeString);
        tvPhoneAuthTime.setText(authTimeString);
        tvPhoneUsedTime.setText(usedTimeString);


        ImageView ivLogoFinishedTime = findViewById(R.id.ivLogoFinishedTime);
        try {
            ivLogoFinishedTime.setImageDrawable(this.getPackageManager().getApplicationIcon(packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        TextView tvTitleEndTime = findViewById(R.id.tvTitleEndTimee);
        tvTitleEndTime.setText(db.readAppLabelFromPackageName(packageName));
        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityEndProgramTimeAuth.this, MainActivity.class);
                startActivity(i);
                ActivityEndProgramTimeAuth.this.finish();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
//        Log.i("entranceDialogR", " R4");
//        Intent i = new Intent(ActivityEndProgramTimeAuth.this, MainActivity.class);
//        startActivity(i);
//        ActivityEndProgramTimeAuth.this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.i("entranceDialogR", " R5");
//        Intent i = new Intent(ActivityEndProgramTimeAuth.this, MainActivity.class);
//        startActivity(i);
//        ActivityEndProgramTimeAuth.this.finish();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Intent i = new Intent(ActivityEndProgramTimeAuth.this, MainActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.i("entranceDialogR", " R6");
//        Intent i = new Intent(ActivityEndProgramTimeAuth.this, MainActivity.class);
//        startActivity(i);
//        ActivityEndProgramTimeAuth.this.finish();
    }
}