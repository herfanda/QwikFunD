package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import affan.id.qwikfund.R;
import affan.id.qwikfund.adapter.PagerAdapter;

/**
 * Created by yunus on 10/2/17.
 */

public class FragmentInvestorForm extends Fragment {
    private View view;
    private FragmentListInvestor fragmentListInvestor;
    private FragmentBidInvestor fragmentBidInvestor;
    private FragmentOnGoingFunding fragmentOnGoingFunding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.daftar_pengajuan_pinjaman_investor,container,false);
            initFragment();
        }

        return view;
    }

    private void initFragment(){
        fragmentListInvestor = new FragmentListInvestor();
        fragmentBidInvestor = new FragmentBidInvestor();
        fragmentOnGoingFunding = new FragmentOnGoingFunding();
    }

    private void addFragment() {
        // Setup PagerAdapter
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.add(fragmentListInvestor, "List");
        pagerAdapter.add(fragmentBidInvestor, "My Bid");
        pagerAdapter.add(fragmentOnGoingFunding, "On Going Funding");

        // Setup ViewPager
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager_investor);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs_investor);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onResume() {
        super.onResume();
        addFragment();
    }
}
