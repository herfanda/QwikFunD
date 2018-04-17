package affan.id.qwikfund.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import affan.id.qwikfund.R;
import affan.id.qwikfund.fragment.FragmentDashboardInvestor;
import affan.id.qwikfund.fragment.FragmentInvestorForm;

public class InvestorDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_investor_dashboard);
        initLeftDrawer();
        //initShowFragment();
    }

    private void initShowFragment(){
        fragment = null;
        fragmentManager = getFragmentManager();
        if (fragment == null){
            fragment = new FragmentDashboardInvestor();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame_investor, fragment)
                .commit();
    }

    private void initLeftDrawer(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_investor);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_investor);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = null;
        fragmentManager = getFragmentManager();

        if (id == R.id.nav_investor_dashboard) {
            fragment = new FragmentDashboardInvestor();

        } else if (id == R.id.nav_investor_daftar_pinjaman) {
            fragment = new FragmentInvestorForm();
        } else if (id == R.id.nav_investor_account) {
            startActivity(new Intent(InvestorDashboard.this,MainActivity.class));

        } else if (id == R.id.nav_investor_logout) {
            startActivity(new Intent(InvestorDashboard.this,MainActivity.class));
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame_investor, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_investor);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
