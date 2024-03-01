package ir.etelli.kids.Pattern;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import ir.etelli.kids.MainActivity;
import ir.etelli.kids.R;

public class ActivityLock extends AppCompatActivity {
    List<MaterialLockView.Cell> mPattern = new ArrayList<>();
    MaterialLockView materialLockView;
    int confirmFlag = 0;
    String intentPropose = "";
    private String confirmedPattern = "NULL";
    private String tempPattern = "NULL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        Bundle b = getIntent().getExtras();
        intentPropose = b.getString("unlock");

        TextView tvPatternDescription = findViewById(R.id.tvPatternDescription);
        if (intentPropose.equals("YES")) {
            tvPatternDescription.setText("برای باز شدن برنامه الگو را رسم کنید.");
        } else {
            tvPatternDescription.setText("الگوی جدید را رسم کنید.");
        }

        materialLockView = new MaterialLockView(ActivityLock.this);

        materialLockView = (MaterialLockView) findViewById(R.id.pattern);
        materialLockView.setOnPatternListener(new MaterialLockView.OnPatternListener() {
            @Override
            public void onPatternStart() {
                super.onPatternStart();
            }

            @Override
            public void onPatternCleared() {
                super.onPatternCleared();
            }

            @Override
            public void onPatternCellAdded(List<MaterialLockView.Cell> pattern, String SimplePattern) {
                super.onPatternCellAdded(pattern, SimplePattern);

                tempPattern = SimplePattern;
//                Log.i("mPattern","Column :" + SimplePattern);
                if (intentPropose.equals("YES")) {
                    SharedPreferences sharedPreferences =
                            getSharedPreferences("startService", MODE_PRIVATE);
                    if (sharedPreferences.getString("patternLock", "").equals(tempPattern)) {
//                        Toast.makeText(ActivityLock.this, "الگو درست است", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK, getIntent().setData(Uri.parse("OK")));
                        finish();
                    }
//                    else {
//                        Toast.makeText(ActivityLock.this, "الگو اشتباه است", Toast.LENGTH_SHORT).show();
//                    }

                }
            }

            @Override
            public void onPatternDetected(List<MaterialLockView.Cell> pattern, String SimplePattern) {
//                Log.i("mPattern","Column :" + SimplePattern);
                if (!SimplePattern.equals(confirmedPattern))
                    materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Wrong);
                else
                    materialLockView.setDisplayMode(MaterialLockView.DisplayMode.Correct);
                super.onPatternDetected(pattern, SimplePattern);
            }
        });

        findViewById(R.id.btnPatternLock).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (intentPropose.equals("YES")) {
                    SharedPreferences sharedPreferences =
                            getSharedPreferences("startService", MODE_PRIVATE);
                    if (sharedPreferences.getString("patternLock", "").equals(tempPattern)) {
//                        Toast.makeText(ActivityLock.this, "الگو درست است", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK, getIntent().setData(Uri.parse("OK")));
                        finish();
                    } else {
                        Toast.makeText(ActivityLock.this, "الگو اشتباه است", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (confirmFlag == 1) {
                        if (confirmedPattern.equals(tempPattern)) {
                            confirmedPattern = tempPattern;
                            SharedPreferences sharedPreferences =
                                    getSharedPreferences("startService", MODE_PRIVATE);
                            @SuppressLint("CommitPrefEdits")
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("patternLock", confirmedPattern);
                            editor.commit();
                            Toast.makeText(ActivityLock.this, "الگو ذخیره شد", Toast.LENGTH_LONG).show();
                            ActivityLock.this.finish();

                        } else {
                            Toast.makeText(ActivityLock.this, "الگوی رسم شده منطبق نیست", Toast.LENGTH_LONG).show();
                        }
                    }

                    if (confirmFlag == 0) {
                        confirmedPattern = tempPattern;
                        confirmFlag++;
                        Toast.makeText(ActivityLock.this, "الگو را مجددا رسم کنید", Toast.LENGTH_LONG).show();
                        tvPatternDescription.setText("الگو را مجددا رسم کنید");
                        materialLockView.clearPattern();
                    }
                }
            }
        });

    }


}


