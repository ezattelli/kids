package ir.etelli.kids.SplashScreen;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

class AdaperSplash extends FragmentStateAdapter {
    public AdaperSplash(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment f = null;
        switch (position) {
            case 0:
                f = new Fragment1();
                break;
            case 1:
                f = new Fragment2();
                break;
            case 2:
                f = new Fragment3();
                break;
//            case 3:f = new Fragment4();break;
//            case 4:f = new Fragment5();break;
        }
        assert f != null;
        return f;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
