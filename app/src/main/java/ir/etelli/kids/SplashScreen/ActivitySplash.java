package ir.etelli.kids.SplashScreen;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import ir.etelli.kids.R;

public class ActivitySplash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ViewPager2 vpSplash = findViewById(R.id.vpSplash);
        AdaperSplash adaperSplash = new AdaperSplash(this);
        vpSplash.setAdapter(adaperSplash);


        TabLayout tabLayout = findViewById(R.id.tabLayout);
        new TabLayoutMediator(tabLayout, vpSplash,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        switch (position) {
                            case 0:
                                tab.setText(".");
                                break;
                            case 1:
                                tab.setText(".");
                                break;
                            case 2:
                                tab.setText(".");
                                break;
//                            case 3:
//                                tab.setText(".");
//                                break;
//                            case 4:
//                                tab.setText(".");
//                                break;
                        }

                    }
                }).attach();

    }
}