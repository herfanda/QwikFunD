package affan.id.qwikfund.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import affan.id.qwikfund.R;
import affan.id.qwikfund.activity.BorrowerDashboard;
import affan.id.qwikfund.activity.MainActivity;
import affan.id.qwikfund.adapter.PagerAdapter;
import affan.id.qwikfund.controller.AppController;
import affan.id.qwikfund.helper.SQLiteHandler;
import affan.id.qwikfund.util.AppConfig;
import affan.id.qwikfund.util.ConvertMapToArrayList;
import affan.id.qwikfund.util.Global;
import affan.id.qwikfund.util.PrefManager;
import affan.id.qwikfund.util.StringWithTag;
import affan.id.qwikfund.util.Utility;

import static affan.id.qwikfund.controller.AppController.TAG;

/**
 * Created by Herfanda on 10/1/2017 AD.
 */

public class FragmentWelcomeSimulasi extends Fragment {

    private View view;
    private Spinner spinTujuanPinjaman;
    private Spinner spinLamaPinjaman;
    private Spinner spinBunga;
    private MainActivity mainActivity;
    private ProgressDialog pDialog;
    private Button btnKalkulasi;
    private Button btnSubmit;
    private StringRequest strReq;
    private EditText edtJumlahPinjaman;
    private EditText edtNilaiPinjaman;
    private EditText edtPencairanDana;
    private EditText edtCicilanBulanan;
    private EditText edtBiayaAdministrasi;
    private EditText edtBunga;
    private SQLiteHandler db;

    private JSONArray jsaTujuanPinjaman;
    private int intLamaPinjaman;
    private JSONArray jsaBunga;

    private List<StringWithTag> listTujuanPinjaman;
    private List<StringWithTag> listLamaPinjaman;
    private List<StringWithTag> listBungaPinjaman;

    private LinearLayout submitLayout;

    private TreeMap<String, String> tujuanMap;
    private TreeMap<String, String> bungaMap;
    private TreeMap<String, String> lamaMap;
    private String inputSelectedTujuan;
    private String inputSelectedBunga;
    private String inputSelectedLama;
    private String inputSelectedPinjaman;
    private PrefManager prefManager;
    private boolean isFromWelcomeSimulation;

    private String sendValueTujuan;
    private String sendValueBunga;
    private String sendValueLama;
    private String sendValuePinjaman;
    private ScrollView scrollview;
    private boolean clickButtonCalculate;


    public FragmentWelcomeSimulasi(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.welcome_simulasi_pinjaman, container, false);
            prefManager = new PrefManager(mainActivity);
            initLayout();
            initEvent();
            initData();
        }

        return view;
    }


    private void initLayout() {
        spinTujuanPinjaman = (Spinner) view.findViewById(R.id.spin_welcome_tujuan_pinjaman);
        spinLamaPinjaman = (Spinner) view.findViewById(R.id.spin_welcome_lama_pinjaman);
        spinBunga = (Spinner) view.findViewById(R.id.spin_welcome_bunga_pinjaman);

        edtJumlahPinjaman = (EditText) view.findViewById(R.id.jumlah_pinjaman_field);
        setClickButtonCalculate(true);


        /*double jumlahPinjamanValue = Double.parseDouble(edtJumlahPinjaman.getText().toString());
        Locale mataUangIndo = new Locale("in","ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(mataUangIndo);
        edtJumlahPinjaman.setText(formatRupiah.format((double)jumlahPinjamanValue));*/
        btnSubmit = (Button) view.findViewById(R.id.btnSubmitSimulasi);

        submitLayout = (LinearLayout) view.findViewById(R.id.submit_content);
        submitLayout.setVisibility(View.GONE);

        edtNilaiPinjaman = (EditText) view.findViewById(R.id.welcome_nilai_pinjaman_field);
        edtNilaiPinjaman.setEnabled(false);
        edtPencairanDana = (EditText) view.findViewById(R.id.welcome_nilai_pencairan_dana_field);
        edtPencairanDana.setEnabled(false);
        edtCicilanBulanan = (EditText) view.findViewById(R.id.welcome_cicilan_perbulan_field);
        edtCicilanBulanan.setEnabled(false);
        edtBiayaAdministrasi = (EditText) view.findViewById(R.id.welcome_biaya_administrasi_field);
        edtBiayaAdministrasi.setEnabled(false);
        edtBunga = (EditText) view.findViewById(R.id.welcome_bunga_field);
        edtBunga.setEnabled(false);

        btnKalkulasi = (Button) view.findViewById(R.id.welcome_btn_kalkulasi);

        scrollview = (ScrollView) view.findViewById(R.id.scroll_view_welcome);

    }

    private void initEvent() {

        // Progress dialog
        pDialog = new ProgressDialog(mainActivity);
        pDialog.setCancelable(false);

        btnKalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setClickButtonCalculate(true);
                if (edtJumlahPinjaman.getText().toString().equals("")) {
                    Toast.makeText(mainActivity, "Kolom Jumlah Pinjaman Wajib Diisi", Toast.LENGTH_SHORT).show();
                } else {
                    simulasiPerhitungan();
                    if (isClickButtonCalculate()) {
                        scrollview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                            @Override
                            public void onGlobalLayout() {
                                scrollview.post(new Runnable() {
                                    public void run() {
                                        scrollview.fullScroll(View.FOCUS_DOWN);
                                    }
                                });
                            }
                        });
                    }
                }

            }
        });

        setClickButtonCalculate(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCallCenter();

            }
        });

        spinTujuanPinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                String key = (String) swt.tag;
                inputSelectedTujuan = key;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinBunga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                String key = (String) swt.tag;
                inputSelectedBunga = key;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

        spinLamaPinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                String key = (String) swt.tag;
                inputSelectedLama = key;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initData() {
        db = new SQLiteHandler(mainActivity);

        listTujuanPinjaman = new ArrayList<>();
        listLamaPinjaman = new ArrayList<>();
        listBungaPinjaman = new ArrayList<>();

        tujuanMap = new TreeMap<>();
        bungaMap = new TreeMap<>();
        lamaMap = new TreeMap<>();

        getDataSimulasi();
    }

    public void getDataSimulasi() {
        String TAG_STRING_REQUEST_DATA = "REQUEST_DATA";

        if (strReq == null) {
            strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN_SIMULASI_VIEW, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "GET DATA Response: " + response.toString());
                    hideDialog();

                    JSONObject jsonObject = null;

                    try {

                        if (jsonObject == null) {
                            jsonObject = new JSONObject(response);

                            JSONObject body = jsonObject.getJSONObject("body");
                            jsaTujuanPinjaman = body.getJSONArray("tujuan");
                            jsaBunga = body.getJSONArray("bungaList");
                            intLamaPinjaman = body.getInt("lamaPinjaman");


                            getTujuanPinjaman(jsaTujuanPinjaman);
                            getBungaList(jsaBunga);
                            getLamaPinjaman(intLamaPinjaman);

                            strReq = null;

                        }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(mainActivity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = null;

                    Log.e(TAG, "Calculate Network Error: " + error.getMessage());

                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        switch (response.statusCode) {
                            case 400:
                                json = new String(response.data);
                                json = trimMessage(json, "message");
                                if (json != null) displayMessage(json);
                                break;
                        }
                        //Additional cases
                    } else {
                        Toast.makeText(mainActivity,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
            });

        }
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, TAG_STRING_REQUEST_DATA);
        Log.d("TAG_CALCULATE", "CONFIG URL CALCULATE " + strReq);
    }

    private void getTujuanPinjaman(JSONArray jsonArray) {
        //Traversing through all the items in the json array
        ConvertMapToArrayList convertToArrayList = new ConvertMapToArrayList();

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                //Getting json object
                JSONObject json = jsonArray.getJSONObject(i);


                tujuanMap.put(json.getString("id_tujuan"), json.getString("tujuan_pinjaman"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listTujuanPinjaman = convertToArrayList.ConvertMapToArrayList(tujuanMap);
        spinTujuanPinjaman.setAdapter(new ArrayAdapter<>(mainActivity, R.layout.spinner_item, listTujuanPinjaman));

    }

    private void getBungaList(JSONArray jsonArray) {
        ConvertMapToArrayList convertToArrayList = new ConvertMapToArrayList();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                //Getting json object
                JSONObject json = jsonArray.getJSONObject(i);

                //Adding the name of the student to array list
                bungaMap.put(json.getString("bunga"), json.getString("bunga") + " %");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listBungaPinjaman = convertToArrayList.ConvertMapToArrayList(bungaMap);
        spinBunga.setAdapter(new ArrayAdapter<>(mainActivity, R.layout.spinner_item, listBungaPinjaman));
    }

    private void getLamaPinjaman(int intLamaPinjaman) {
        ConvertMapToArrayList convertToArrayList = new ConvertMapToArrayList();
        for (int i = 1; i < intLamaPinjaman; i++) {
            //Adding the name of the student to array list
            lamaMap.put(String.valueOf(i), String.valueOf(i) + " bulan");

        }
        listLamaPinjaman = convertToArrayList.ConvertMapToArrayList(lamaMap);
        spinLamaPinjaman.setAdapter(new ArrayAdapter<>(mainActivity, R.layout.spinner_item, listLamaPinjaman));
    }

    // kalkulasi
    private void simulasiPerhitungan() {
        String tag_string_req = "req_kalkulasi";

        pDialog.setMessage("Calculate...");
        showDialog();

        inputSelectedPinjaman = edtJumlahPinjaman.getText().toString();

        if (strReq == null) {
            strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN_SIMULASI_BORROWER_CALCULATE, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Calculate Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        int status = jObj.getInt("status"); // 0 status error

                        if (status == 1) {
                            JSONObject body = jObj.getJSONObject("body");
                            String nilai = body.getString("nilai");
                            String admin = body.getString("admin");
                            String pencairan = body.getString("pencairan");
                            String bunga = body.getString("bunga");
                            String cicilan = body.getString("cicilan");

                            edtNilaiPinjaman.setText(nilai);
                            edtPencairanDana.setText(pencairan);
                            edtCicilanBulanan.setText(cicilan);
                            edtBiayaAdministrasi.setText(admin);
                            edtBunga.setText(bunga);

                            if (submitLayout.getVisibility() == View.GONE) {
                                submitLayout.setVisibility(View.VISIBLE);
                            }

                        } else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("message");
                            Toast.makeText(mainActivity,
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(mainActivity, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = null;

                    Log.e(TAG, "Calculate Network Error: " + error.getMessage());

                    NetworkResponse response = error.networkResponse;
                    if (response != null && response.data != null) {
                        switch (response.statusCode) {
                            case 400:
                                json = new String(response.data);
                                json = trimMessage(json, "message");
                                if (json != null) displayMessage(json);
                                break;
                        }
                        //Additional cases
                    } else {
                        Toast.makeText(mainActivity,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to simulasi url

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("pinjaman", inputSelectedPinjaman);
                    params.put("lama", inputSelectedLama);
                    params.put("bunga_persen", inputSelectedBunga);

                    return params;
                }

            };

        } else {
            Toast.makeText(mainActivity, "strReq = " + strReq, Toast.LENGTH_SHORT).show();
        }

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        Log.d("TAG_LOGIN", "CONFIG URL LOGIN " + strReq);


    }

    public void collectData() {
        prefManager.putString(Global.KEY_SELECTED_TUJUAN, inputSelectedTujuan);
        prefManager.putString(Global.KEY_SELECTED_LAMA, inputSelectedLama);
        prefManager.putString(Global.KEY_SELECTED_PINJAMAN, inputSelectedPinjaman);
        prefManager.putString(Global.KEY_SELECTED_BUNGA, inputSelectedBunga);
        setFromWelcomeSimulation(true);

    }

    public String trimMessage(String json, String key) {
        String trimmedString = null;

        try {
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    //Somewhere that has access to a context
    public void displayMessage(String toastString) {
        Toast.makeText(mainActivity, toastString, Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinnerBunga() {

        List<String> list = new ArrayList<String>();
        list.add("1.00");
        list.add("1.50");
        list.add("2.00");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBunga.setAdapter(dataAdapter);
    }

    public void addItemOnSpinnerLamaPinjaman() {
        List<String> list = new ArrayList<String>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(mainActivity,
                R.layout.spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLamaPinjaman.setAdapter(dataAdapter);

    }

    private void showDialogCallCenter(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mainActivity);
        builder1.setTitle("INFO");
        builder1.setMessage("Untuk mengajukan pinjaman, Anda harus menjadi nasabah Bank Danamon. Silahkan Login / Aktivasi Akun Anda.");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mainActivity.addFragment();
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

    public boolean isFromWelcomeSimulation() {
        return isFromWelcomeSimulation;
    }

    public void setFromWelcomeSimulation(boolean fromWelcomeSimulation) {
        isFromWelcomeSimulation = fromWelcomeSimulation;
    }

    public String getInputSelectedTujuan() {
        return inputSelectedTujuan;
    }

    public String getInputSelectedBunga() {
        return inputSelectedBunga;
    }

    public String getInputSelectedLama() {
        return inputSelectedLama;
    }

    public String getInputSelectedPinjaman() {
        return inputSelectedPinjaman;
    }

    public boolean isClickButtonCalculate() {
        return clickButtonCalculate;
    }

    public void setClickButtonCalculate(boolean clickButtonCalculate) {
        this.clickButtonCalculate = clickButtonCalculate;
    }
}
