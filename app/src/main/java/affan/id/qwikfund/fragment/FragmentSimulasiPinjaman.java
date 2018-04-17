package affan.id.qwikfund.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import affan.id.qwikfund.R;
import affan.id.qwikfund.activity.BorrowerDashboard;
import affan.id.qwikfund.controller.AppController;
import affan.id.qwikfund.util.AppConfig;
import affan.id.qwikfund.util.ConvertMapToArrayList;
import affan.id.qwikfund.util.Global;
import affan.id.qwikfund.util.PrefManager;
import affan.id.qwikfund.util.StringWithTag;

import static affan.id.qwikfund.controller.AppController.TAG;

/**
 * Created by yunus on 9/5/17.
 */

public class FragmentSimulasiPinjaman extends Fragment {
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private View view;
    private Spinner spinTujuanPinjaman;
    private Spinner spinLamaPinjaman;
    private Spinner spinBunga;
    private ProgressDialog pDialog;
    private Button btnKalkulasi;
    private Button btnSubmit;

    private EditText edtJumlahPinjaman;
    private EditText edtNilaiPinjaman;
    private EditText edtPencairanDana;
    private EditText edtCicilanBulanan;
    private EditText edtBiayaAdministrasi;
    private EditText edtBunga;

    private JSONArray jsaTujuanPinjaman;
    private int intLamaPinjaman;
    private JSONArray jsaBunga;


    private List<StringWithTag> listTujuanPinjaman;
    private List<StringWithTag> listLamaPinjaman;
    private List<StringWithTag> listBungaPinjaman;


    private TreeMap<String, String> tujuanMap;
    private TreeMap<String, String> bungaMap;
    private TreeMap<String, String> lamaMap;

    private StringRequest strReq;
    private StringRequest strReqForCalculate;
    private BorrowerDashboard borrowerDashboard;
    private LinearLayout submitBorrowerLayout;

    private String inputSelectedTujuanAfterLogin;
    private String inputSelectedBungaAfterLogin;
    private String inputSelectedLamaAfterLogin;
    private String inputSelectedPinjamanAferLogin;
    private String inputTokenAfterLogin;
    private boolean simulasiAfterLogin;

    public FragmentSimulasiPinjaman(BorrowerDashboard borrowerDashboard) {
        this.borrowerDashboard = borrowerDashboard;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_simulasi_pinjaman,container,false);
        }
        initLayout();
        initEvent();
        initData();
        return view;
    }

    private void initLayout(){
        spinTujuanPinjaman = (Spinner) view.findViewById(R.id.spin_tujuan_pinjaman_borrower);
        spinLamaPinjaman = (Spinner) view.findViewById(R.id.spin_lama_pinjaman_borrower);
        //spinBunga = (Spinner) view.findViewById(R.id.spin_bunga_pinjaman_borrower);

        edtJumlahPinjaman = (EditText) view.findViewById(R.id.jumlah_pinjaman_field);
        edtNilaiPinjaman = (EditText) view.findViewById(R.id.nilai_pinjaman_borrower_field);
        edtPencairanDana = (EditText) view.findViewById(R.id.nilai_pencairan_dana_borrowerfield);
        edtCicilanBulanan = (EditText) view.findViewById(R.id.cicilan_perbulan_borrower_field);
        edtBiayaAdministrasi = (EditText) view.findViewById(R.id.biaya_administrasi_borrower_field);
        //edtBunga = (EditText) view.findViewById(R.id.bunga_borrower_field);



        btnKalkulasi = (Button) view.findViewById(R.id.btn_kalkulasi_borrower);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit_borrower);


        submitBorrowerLayout = (LinearLayout) view.findViewById(R.id.submit_content);
        submitBorrowerLayout.setVisibility(View.GONE);
        //addItemsOnSpinnerPinjaman();
    }

    private void initEvent(){

        // Progress dialog
        pDialog = new ProgressDialog(borrowerDashboard);
        pDialog.setCancelable(false);

        btnKalkulasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perhitunganPinjaman();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = null;
                fragmentManager = getFragmentManager();
                if (fragment == null){
                    fragment = new FragmentRincianPengajuanPinjaman();
                    setSimulasiAfterLogin(true);
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_borrower, fragment)
                        .commit();

            }
        });

        spinTujuanPinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                String key = (String) swt.tag;
                /*inputSelectedTujuanAfterLogin = key;*/
                inputSelectedTujuanAfterLogin = spinTujuanPinjaman.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinLamaPinjaman.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                String key = (String) swt.tag;
                inputSelectedLamaAfterLogin = key;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*spinBunga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                String key = (String) swt.tag;
                inputSelectedBungaAfterLogin = key;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    private void initData(){

        listTujuanPinjaman = new ArrayList<>();
        listLamaPinjaman = new ArrayList<>();
        listBungaPinjaman = new ArrayList<>();

        tujuanMap = new TreeMap<>();
        bungaMap = new TreeMap<>();
        lamaMap = new TreeMap<>();

        getDataSpinner();
    }

    public void getDataSpinner(){
        String TAG_STRING_REQUEST_DATA = "REQUEST_DATA";

        if (strReq == null){
            strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN_SIMULASI_VIEW,new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "GET DATA Response: " + response.toString());
                    hideDialog();

                    JSONObject jsonObject = null;

                    try {

                        if (jsonObject == null){
                            jsonObject = new JSONObject(response);

                            JSONObject body = jsonObject.getJSONObject("body");
                            jsaTujuanPinjaman = body.getJSONArray("tujuan");
                            jsaBunga = body.getJSONArray("bungaList");
                            intLamaPinjaman = body.getInt("lamaPinjaman");

                            getTujuanPinjaman(jsaTujuanPinjaman);
                            //getBungaList(jsaBunga);
                            getLamaPinjaman(intLamaPinjaman);

                            strReq = null;

                        }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(borrowerDashboard, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = null;

                    Log.e(TAG, "Calculate Network Error: " + error.getMessage());

                    NetworkResponse response = error.networkResponse;
                    if(response != null && response.data != null){
                        switch(response.statusCode){
                            case 400:
                                json = new String(response.data);
                                json = trimMessage(json, "message");
                                if(json != null) displayMessage(json);
                                break;
                        }
                        //Additional cases
                    } else {
                        Toast.makeText(borrowerDashboard,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
            });

        }
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, TAG_STRING_REQUEST_DATA);
        Log.d("TAG_CALCULATE","CONFIG URL CALCULATE "+strReq);

    }

    private void getTujuanPinjaman(JSONArray jsonArray){
        //Traversing through all the items in the json array
        ConvertMapToArrayList convertToArrayList = new ConvertMapToArrayList();

        for(int i=0;i<jsonArray.length();i++){
            try {
                //Getting json object
                JSONObject json = jsonArray.getJSONObject(i);


                tujuanMap.put(json.getString("id_tujuan"),json.getString("tujuan_pinjaman"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listTujuanPinjaman = convertToArrayList.ConvertMapToArrayList(tujuanMap);
        spinTujuanPinjaman.setAdapter(new ArrayAdapter<>(borrowerDashboard, R.layout.spinner_item, listTujuanPinjaman));

    }

    /*private void getBungaList(JSONArray jsonArray){
        ConvertMapToArrayList convertToArrayList = new ConvertMapToArrayList();
        for (int i=0;i<jsonArray.length();i++ ){
            try {
                //Getting json object
                JSONObject json = jsonArray.getJSONObject(i);

                //Adding the name of the student to array list
                bungaMap.put(json.getString("bunga"),json.getString("bunga") + " %");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        listBungaPinjaman = convertToArrayList.ConvertMapToArrayList(bungaMap);
        spinBunga.setAdapter(new ArrayAdapter<>(borrowerDashboard, R.layout.spinner_item, listBungaPinjaman));
    }*/

    private void getLamaPinjaman(int intLamaPinjaman){
        ConvertMapToArrayList convertToArrayList = new ConvertMapToArrayList();
        for (int i = 1; i<intLamaPinjaman; i++){
            //Adding the name of the student to array list
            lamaMap.put(String.valueOf(i), String.valueOf(i)+" bulan");

        }
        listLamaPinjaman = convertToArrayList.ConvertMapToArrayList(lamaMap);
        spinLamaPinjaman.setAdapter(new ArrayAdapter<>(borrowerDashboard,R.layout.spinner_item, listLamaPinjaman));
    }

    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    private void perhitunganPinjaman(){
        String tag_string_req = "req_kalkulasi";

        pDialog.setMessage("Calculate...");
        showDialog();

        inputSelectedPinjamanAferLogin = edtJumlahPinjaman.getText().toString();

        inputTokenAfterLogin = PrefManager.getInstance(getContext()).getString(Global.KEY_TOKEN,Global.STRING_DEFAULT_VALUE);

        if (strReqForCalculate == null){
            strReqForCalculate = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN_SIMULASI_BORROWER_CALCULATE, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Calculate Response: " + response.toString());
                    Log.d(TAG, "Status  Request: " + strReq);

                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        int status = jObj.getInt("status"); // 0 status failed

                        if (status == 1){
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
                            //edtBunga.setText(bunga);

                            if (submitBorrowerLayout.getVisibility() == View.GONE){
                                submitBorrowerLayout.setVisibility(View.VISIBLE);
                            }

                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_SELECTED_TUJUAN,inputSelectedTujuanAfterLogin);
                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_SELECTED_PINJAMAN,nilai);
                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_SELECTED_LAMA,inputSelectedLamaAfterLogin);
                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_SELECTED_BIAYA_ADM,admin);
                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_SELECTED_PENCAIRAN_DANA,pencairan);

                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_ID_PINJAMAN,"Q625P4");
                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_STATUS_PIINJAMAN,"Menunggu Investor");
                            PrefManager.getInstance(borrowerDashboard).putString(Global.KEY_TANGGAL_PENGAJUAN,"11-Oct-2017");

                            Log.d(TAG, "Value Tujuan Pinjaman: " + inputSelectedTujuanAfterLogin);

                        }else {
                            // Error in login. Get the error message
                            String errorMsg = jObj.getString("message");
                            Toast.makeText(borrowerDashboard,
                                    errorMsg, Toast.LENGTH_LONG).show();
                        }

                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(borrowerDashboard, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = null;

                    Log.e(TAG, "Calculate Network Error: " + error.getMessage());

                    NetworkResponse response = error.networkResponse;
                    if(response != null && response.data != null){
                        switch(response.statusCode){
                            case 400:
                                json = new String(response.data);
                                json = trimMessage(json, "message");
                                if(json != null) displayMessage(json);
                                break;
                        }
                        //Additional cases
                    } else {
                        Toast.makeText(borrowerDashboard,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to simulasi url

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("pinjaman",inputSelectedPinjamanAferLogin);
                    params.put("lama", inputSelectedLamaAfterLogin);
                    //params.put("bunga_persen", inputSelectedBungaAfterLogin);
                    params.put("token",inputTokenAfterLogin);

                    return params;
                }

            };

        } else {
            Toast.makeText(borrowerDashboard,"strReq = "+strReqForCalculate,Toast.LENGTH_SHORT).show();
        }

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReqForCalculate, tag_string_req);
        Log.d("TAG_CALCULATE","CALCULATE SIMULATION "+strReqForCalculate);


    }

    //Somewhere that has access to a context
    public void displayMessage(String toastString){
        Toast.makeText(borrowerDashboard, toastString, Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public boolean isSimulasiAfterLogin() {
        return simulasiAfterLogin;
    }

    public void setSimulasiAfterLogin(boolean simulasiAfterLogin) {
        this.simulasiAfterLogin = simulasiAfterLogin;
    }
}
