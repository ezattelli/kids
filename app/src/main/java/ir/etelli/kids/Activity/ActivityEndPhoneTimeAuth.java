package ir.etelli.kids.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import ir.etelli.kids.DataBase.DataBase;
import ir.etelli.kids.MainActivity;
import ir.etelli.kids.R;

public class ActivityEndPhoneTimeAuth extends AppCompatActivity {
    TextView tvPhoneStartTime;
    TextView tvPhoneEndTime;
    TextView tvPhoneAuthTime;
    TextView tvPhoneUsedTime;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_phone_time_auth);

        DataBase db = new DataBase(this);
        db.openDB();

        tvPhoneStartTime = findViewById(R.id.tvPhoneStartTime);
        tvPhoneEndTime = findViewById(R.id.tvPhoneEndTime);
        tvPhoneAuthTime = findViewById(R.id.tvPhoneAuthTime);
        tvPhoneUsedTime = findViewById(R.id.tvPhoneUsedTime);


        Bundle b = getIntent().getExtras();
        long phoneStartTime = b.getLong("phoneStartTime");
        long phoneEndTime = b.getLong("phoneEndTime");
        long phoneAuthTime = b.getLong("phoneAuthTime");
        long phoneUsedTime = b.getLong("phoneUsedTime");

//        Log.i("phoneUsedTimeA"," " + phoneUsedTime);

        Calendar calendar = null;
        String startTimeString;
        if ((db.readPhoneStartTimeHour() + db.readPhoneStartTimeMinute()) > 0) {
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

        String endTimeString;
        if ((db.readPhoneEndTimeHour() + db.readPhoneEndTimeMinute()) > 0) {
            calendar = Calendar.getInstance();
            if (calendar.getTimeInMillis() > phoneEndTime) {
                tvPhoneEndTime.setTextColor(Color.RED);
            } else {
                tvPhoneEndTime.setTextColor(R.color.ButtonTextColor);
            }

            calendar.setTimeInMillis(phoneEndTime);
            endTimeString = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);
        } else {
            endTimeString = "تنظیم نشده";
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
            tvPhoneAuthTime.setTextColor(Color.RED);
        } else {
            authTimeString = "تنظیم نشده";
        }

        tvPhoneStartTime.setText(startTimeString);
        tvPhoneEndTime.setText(endTimeString);
        tvPhoneAuthTime.setText(authTimeString);
        tvPhoneUsedTime.setText(usedTimeString);

        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityEndPhoneTimeAuth.this, MainActivity.class);
                startActivity(i);
                ActivityEndPhoneTimeAuth.this.finish();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
//        Log.i("entranceDialogR", " R1");
//        Intent i = new Intent(ActivityEndPhoneTimeAuth.this, MainActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.i("entranceDialogR", " R2");
//        Intent i = new Intent(ActivityEndPhoneTimeAuth.this, MainActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
//        Intent i = new Intent(ActivityEndPhoneTimeAuth.this, MainActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Log.i("entranceDialogR", " R3");
//        Intent i = new Intent(ActivityEndPhoneTimeAuth.this, MainActivity.class);
//        startActivity(i);
    }
}