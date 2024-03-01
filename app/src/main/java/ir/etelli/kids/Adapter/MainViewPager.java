package ir.etelli.kids.Adapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import ir.etelli.kids.R;

public class MainViewPager extends FragmentStateAdapter {

    ArrayList<Fragment> fragment;

    public MainViewPager(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragments) {
        super(fragmentActivity);
        this.fragment = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        for (int i = 0; i < fragment.size(); i++) {
            if (i == position)
                return fragment.get(position);
        }
//        switch (position) {
//            case 0:
//                return new FragmentKids();
//            case 1:
//                return new FragmentSetting();
//            case 2:
//                return new FragmentProgram();
//        }
        return null;
    }

    @Override
    public int getItemCount() {
        return fragment.size();
    }


}
