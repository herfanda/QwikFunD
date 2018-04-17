package affan.id.qwikfund.activity;
import android.app.FragmentManager;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import affan.id.qwikfund.R;
import affan.id.qwikfund.adapter.PagerAdapter;
import affan.id.qwikfund.fragment.FragmentBidInvestor;
import affan.id.qwikfund.fragment.FragmentDashboardInvestor;
import affan.id.qwikfund.fragment.FragmentListInvestor;
import affan.id.qwikfund.fragment.FragmentOnGoingFunding;
import affan.id.qwikfund.util.AppConfig;

/**
 * Created by yunus on 9/5/17.
 */

public class InvestorForm extends AppCompatActivity {
    private FragmentListInvestor fragmentListInvestor;
    private FragmentBidInvestor fragmentBidInvestor;
    private FragmentOnGoingFunding fragmentOnGoingFunding;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_pengajuan_pinjaman_investor);

        initFragment();
    }

    private void initRecyclerView(){

    }

    private void initFragment() {
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
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_investor);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_investor);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        addFragment();
    }

    public FragmentListInvestor getFragmentListInvestor() {
        return fragmentListInvestor;
    }
}
