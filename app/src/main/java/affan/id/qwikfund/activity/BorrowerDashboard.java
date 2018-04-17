package affan.id.qwikfund.activity;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import affan.id.qwikfund.R;
import affan.id.qwikfund.fragment.FragmentAccountBorrower;
import affan.id.qwikfund.fragment.FragmentDashboardBorrower;
import affan.id.qwikfund.fragment.FragmentListPengajuan;
import affan.id.qwikfund.fragment.FragmentLoginBorrower;
import affan.id.qwikfund.fragment.FragmentSimulasiPinjaman;
import affan.id.qwikfund.util.Global;
import affan.id.qwikfund.util.PrefManager;

public class BorrowerDashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment;
    private FragmentManager fragmentManager;
    private MainActivity mainActivity;
    private String getValueTujuan;
    private String getValueLama;
    private String getValueBunga;
    private String getValuePinjaman;
    private FragmentListPengajuan listPengajuan;
    private FragmentSimulasiPinjaman simulasiPinjaman;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrower_dashboard);


        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                getValueTujuan = null;
                getValueLama = null;
                getValuePinjaman = null;
                getValueBunga = null;
            } else {
                getValueTujuan = extras.getString(Global.KEY_SELECTED_TUJUAN);
                getValueLama = extras.getString(Global.KEY_SELECTED_LAMA);
                getValuePinjaman = extras.getString(Global.KEY_SELECTED_PINJAMAN);
                getValueBunga = extras.getString(Global.KEY_SELECTED_BUNGA);
            }
        } else {
            getValueTujuan = (String) savedInstanceState.getSerializable(Global.KEY_SELECTED_TUJUAN);
            getValueLama = (String) savedInstanceState.getSerializable(Global.KEY_SELECTED_LAMA);
            getValuePinjaman = (String) savedInstanceState.getSerializable(Global.KEY_SELECTED_PINJAMAN);
            getValueBunga = (String) savedInstanceState.getSerializable(Global.KEY_SELECTED_BUNGA);
        }

        initLeftDrawerBorrower();
        initShowFragment();
        initClearData();
        //mainActivity = new MainActivity();
    }

    private void initClearData(){
        clearPrefManager();
        if (listPengajuan == null){
            listPengajuan = new FragmentListPengajuan();
        }

    }


    private void initLeftDrawerBorrower(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.nav_toolbar_borrower);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_borrower_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_borrower);
        navigationView.setNavigationItemSelectedListener(this);

        //Setup Toolbar
        Toolbar toolbarBorrower = (Toolbar) findViewById(R.id.toolbar_borrower);
        setSupportActionBar(toolbarBorrower);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_borrower_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void initShowFragment(){
        fragment = null;
        fragmentManager = getFragmentManager();
        if (fragment == null){
            fragment = new FragmentDashboardBorrower();
        }
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame_borrower, fragment)
                .commit();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        fragment = null;
        fragmentManager = getFragmentManager();

        // Dashboard
        if (id == R.id.menu_dashboard) {
            if (fragment == null){
                fragment = new FragmentDashboardBorrower();
            }

            // Daftar Pengajuan Pinjaman
        } else if (id == R.id.menu_pengajuan_pinjaman) {
            if (fragment == null){
                fragment = new FragmentSimulasiPinjaman(this);
            }

            // Pinjaman Saya
        } else if (id == R.id.menu_pinjaman_saya) {
            if (fragment == null){
                fragment = new FragmentListPengajuan();
            }

            // Account
        } else if (id == R.id.menu_account_borrower){
            if (fragment == null){
                fragment = new FragmentAccountBorrower();
            }

            // Logout
        } else {
            if (id == R.id.menu_logout_borrower){
                if (fragment == null){
                    fragment = new FragmentDashboardBorrower();
                    logoutWarning();
                }
            }
        }

        fragmentManager.beginTransaction()
                .replace(R.id.content_frame_borrower, fragment)
                .addToBackStack("Some String")
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_borrower_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void clearPrefManager() {
        PrefManager.getEditor().clear().commit();

    }


    public String getGetValueTujuan() {
        return getValueTujuan;
    }

    public String getGetValueLama() {
        return getValueLama;
    }

    public String getGetValueBunga() {
        return getValueBunga;
    }

    public String getGetValuePinjaman() {
        return getValuePinjaman;
    }

    public FragmentListPengajuan getListPengajuan() {
        return listPengajuan;
    }

    public FragmentSimulasiPinjaman getSimulasiPinjaman() {
        if (fragment == null){
            fragment = new FragmentSimulasiPinjaman(this);
        }
        return simulasiPinjaman;
    }

    private void logoutWarning(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("Peringatan");
        builder1.setMessage("Anda Yakin Keluar Aplikasi ? ");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(BorrowerDashboard.this,MainActivity.class));
                        mainActivity.finish();
                    }
                });

        builder1.setNegativeButton(
                "CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }
}
