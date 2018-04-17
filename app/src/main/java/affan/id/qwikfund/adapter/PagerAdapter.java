package affan.id.qwikfund.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by yunus on 9/5/17.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    private ArrayList<String> titles;
    private ArrayList<Fragment> fragments;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        titles = new ArrayList<>(3);
        fragments = new ArrayList<>(3);
    }

    public void add(Fragment fragment, String title) {
        titles.add(title);
        fragments.add(fragment);
    }

    @Override public int getCount() {
        return fragments.size();
    }

    @Override public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
