package affan.id.qwikfund.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import affan.id.qwikfund.R;
import affan.id.qwikfund.adapter.PagerAdapter;
import affan.id.qwikfund.fragment.FragmentLoginBorrower;
import affan.id.qwikfund.fragment.FragmentLoginInvestor;
import affan.id.qwikfund.fragment.FragmentWelcomeSimulasi;


public class MainActivity extends AppCompatActivity {


    private FragmentLoginBorrower loginBorrower;
    private FragmentLoginInvestor loginInvestor;
    private FragmentWelcomeSimulasi welcomeSimulasi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_home);

        initFragment();
        initLayout();
        initData();

    }


    private void initLayout() {


        //Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_login_home);
        setSupportActionBar(toolbar);

    }

    private void initData() {

    }

    private void initFragment() {
        loginBorrower = new FragmentLoginBorrower(this);
        welcomeSimulasi = new FragmentWelcomeSimulasi(this);
        loginInvestor = new FragmentLoginInvestor(this);
    }

    private void showDialogCallCenter(String msg) {
        AlertDialog.Builder builder = null;
        AlertDialog dialog = null;
        //builder show dialog [S]
        if (builder == null) {
            builder = new AlertDialog.Builder(this);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        //builder show dialog [E]
        builder.setMessage(msg);
        builder.setCancelable(false);

        if (dialog == null) {
            dialog = builder.create();
            dialog.show();
        }

        Button btnPositive = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        btnPositive.setTextColor(getResources().getColor(R.color.default_system_green));
    }

    public void addFragment() {
        // Setup PagerAdapter
        PagerAdapter pagerAdapter = new PagerAdapter(getFragmentManager());
        pagerAdapter.add(loginBorrower, getResources().getString(R.string.tab_borrower));
        pagerAdapter.add(loginInvestor, getResources().getString(R.string.tab_investor));
        pagerAdapter.add(welcomeSimulasi, getResources().getString(R.string.tab_simulasi_pinjaman));

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager_login_home);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs_login_home);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        addFragment();
    }

    public FragmentLoginBorrower getLoginBorrower() {
        return loginBorrower;
    }

    public FragmentLoginInvestor getLoginInvestor() {
        return loginInvestor;
    }

    public FragmentWelcomeSimulasi getWelcomeSimulasi() {
        if (welcomeSimulasi == null) {
            welcomeSimulasi = new FragmentWelcomeSimulasi(this);
        }
        return welcomeSimulasi;
    }
}
