package ir.etelli.kids.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import ir.etelli.kids.MainActivity;
import ir.etelli.kids.R;

public class ActivityPhoneSettingsRestricted extends AppCompatActivity {
    TextView tvPhoneStartTime;
    TextView tvPhoneAuthTime;
    TextView tvPhoneUsedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_settings_restricted_);

        tvPhoneStartTime = findViewById(R.id.tvPhoneStartTime);
        tvPhoneAuthTime = findViewById(R.id.tvPhoneAuthTime);
        tvPhoneUsedTime = findViewById(R.id.tvPhoneUsedTime);


        Bundle b = getIntent().getExtras();
        long phoneStartTime = b.getLong("phoneStartTime");
        long phoneAuthTime = b.getLong("phoneAuthTime");
        long phoneUsedTime = b.getLong("phoneUsedTime");


        Calendar calendar = null;
        String startTimeString;
        if (phoneStartTime > 0) {
            calendar = Calendar.getInstance();
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

        tvPhoneStartTime.setText(startTimeString);
        tvPhoneAuthTime.setText(authTimeString);
        tvPhoneUsedTime.setText(usedTimeString);

        findViewById(R.id.btnReturn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityPhoneSettingsRestricted.this, MainActivity.class);
                startActivity(i);
                ActivityPhoneSettingsRestricted.this.finish();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
//        Log.i("entranceDialogR", " R7");
//        Intent i = new Intent(ActivityPhoneSettingsRestricted.this, MainActivity.class);
//        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Log.i("entranceDialogR", " R8");
//        Intent i = new Intent(ActivityPhoneSettingsRestricted.this, MainActivity.class);
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
//        Log.i("entranceDialogR", " R9");
//        Intent i = new Intent(ActivityPhoneSettingsRestricted.this, MainActivity.class);
//        startActivity(i);
    }
}