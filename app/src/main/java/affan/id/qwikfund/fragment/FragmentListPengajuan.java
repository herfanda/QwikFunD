package affan.id.qwikfund.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import affan.id.qwikfund.R;
import affan.id.qwikfund.activity.BorrowerDashboard;
import affan.id.qwikfund.activity.MainActivity;
import affan.id.qwikfund.controller.AppController;
import affan.id.qwikfund.util.AppConfig;
import affan.id.qwikfund.util.Global;
import affan.id.qwikfund.util.PrefManager;

import static affan.id.qwikfund.controller.AppController.TAG;

/**
 * Created by Herfanda on 9/25/2017 AD.
 */

public class FragmentListPengajuan extends Fragment {

    private View view;
    private BorrowerDashboard borrowerDashboard;
    private String tujuanPinjaman;
    private String nilaiPinjaman;
    private String lamaPinjaman;
    private String bungaPersen;
    private String biayaAdministrasi;
    private String nilaiPencairanDana;
    private String bungaRupiah;
    private String cicilanBulanan;
    private TextView getDataIdPinjaman;
    private TextView getDataStatusPinjaman;
    private TextView getDataTujuan;
    private TextView getDataNilaiPinjaman;
    private TextView getDataLamaPinjaman;
    private TextView getDataBungaPinjaman;
    private TextView getDataPermohonan;
    private TextView getDataTanggalPengajuan;
    private PrefManager prefManager;
    private MainActivity mainActivity;
    private String inputToken;
    private StringRequest strRequestListPengajuan;


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.fragment_list_pengajuan,container,false);
            borrowerDashboard = (BorrowerDashboard) getActivity();
            initLayout();
            initData();
        }

        return view;
    }


    private void initLayout(){
        getDataIdPinjaman = (TextView) view.findViewById(R.id.input_id_pinjaman);
        getDataStatusPinjaman = (TextView) view.findViewById(R.id.txt_status_pengajuan_pinjaman);
        getDataTujuan = (TextView) view.findViewById(R.id.txt_pengajuan_tujuan_pinjaman);
        getDataNilaiPinjaman = (TextView) view.findViewById(R.id.txt_nilai_pinjaman);
        getDataLamaPinjaman = (TextView) view.findViewById(R.id.txt_lama_pinjaman);
        getDataPermohonan = (TextView) view.findViewById(R.id.txt_deskripsi_permohonan);
        getDataTanggalPengajuan = (TextView) view.findViewById(R.id.txt_tanggal_pengajuan_pinjaman);

    }

    private void initData(){
        getDataAfterLogin();


    }

    private void getDataAfterLogin(){

        String idPinjaman = PrefManager.getInstance(getContext()).getString(Global.KEY_ID_PINJAMAN,Global.STRING_DEFAULT_VALUE);
        String statusPinjaman = PrefManager.getInstance(getContext()).getString(Global.KEY_STATUS_PIINJAMAN, Global.STRING_DEFAULT_VALUE);
        String dataTujuanValue = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_TUJUAN,Global.STRING_DEFAULT_VALUE);
        String nilaiPinjamanValue = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_PINJAMAN,Global.STRING_DEFAULT_VALUE);
        String lamaPinjamanValue = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_LAMA,Global.STRING_DEFAULT_VALUE);
        String dataPermohonan = PrefManager.getInstance(getContext()).getString(Global.KEY_INPUT_DESKRIPSI,Global.STRING_DEFAULT_VALUE);
        String tanggalPengajuan = PrefManager.getInstance(getContext()).getString(Global.KEY_TANGGAL_PENGAJUAN, Global.STRING_DEFAULT_VALUE);


        getDataIdPinjaman.setText(idPinjaman);
        getDataStatusPinjaman.setText(statusPinjaman);
        getDataTujuan.setText(dataTujuanValue);
        getDataNilaiPinjaman.setText(nilaiPinjamanValue);
        getDataLamaPinjaman.setText(lamaPinjamanValue);
        getDataPermohonan.setText(dataPermohonan);
        getDataTanggalPengajuan.setText(tanggalPengajuan);


    }

    private void showListPengajuan(){
        String tag_string_req = "req_kalkulasi";
        inputToken = PrefManager.getInstance(getContext()).getString(Global.KEY_TOKEN,Global.STRING_DEFAULT_VALUE);

        if (strRequestListPengajuan == null){
            strRequestListPengajuan = new StringRequest(Request.Method.POST, AppConfig.URL_LIST_PENGAJUAN_BORROWER, new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Calculate Response: " + response.toString());
                    Log.d(TAG, "Status  Request: " + strRequestListPengajuan);

                    try{
                        JSONObject jObj = new JSONObject(response);
                        int status = jObj.getInt("status"); // 0 status failed

                        if (status == 1){
                            JSONObject body = jObj.getJSONObject("body");
                            JSONObject data_pinjaman = body.getJSONObject("dataPinjaman");


                            String kode_pinjaman = data_pinjaman.getString("kode_pinjaman");
                            String status_pinjaman = data_pinjaman.getString("status");
                            String data_tujuan = data_pinjaman.getString("tujuan_pinjaman");
                            String nilai_pinjaman = data_pinjaman.getString("nilai_pinjaman");
                            String lama_pinjaman = data_pinjaman.getString("lama_pinjaman");
                            String bunga_pinjaman = data_pinjaman.getString("bunga");
                            String permohonan_pinjaman = data_pinjaman.getString("deskripsi");
                            String tanggal_pengajuan = data_pinjaman.getString("date_request");

                            getDataIdPinjaman.setText(kode_pinjaman);
                            getDataStatusPinjaman.setText(status_pinjaman);
                            getDataTujuan.setText(data_tujuan);
                            getDataNilaiPinjaman.setText(nilai_pinjaman);
                            getDataLamaPinjaman.setText(lama_pinjaman);
                            getDataBungaPinjaman.setText(bunga_pinjaman);
                            getDataPermohonan.setText(permohonan_pinjaman);
                            getDataTanggalPengajuan.setText(tanggal_pengajuan);



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
            },new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = null;

                    Log.e(TAG, "Calculate Network Error: " + error.getMessage());

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to simulasi url

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("token",inputToken);

                    return params;
                }

            };

        } else {
            Toast.makeText(borrowerDashboard,"strReq = "+strRequestListPengajuan,Toast.LENGTH_SHORT).show();
        }

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strRequestListPengajuan, tag_string_req);
        Log.d("TAG_CALCULATE","CALCULATE SIMULATION "+strRequestListPengajuan);

    }

    public void setGetDataIdPinjaman(TextView getDataIdPinjaman) {
        this.getDataIdPinjaman = getDataIdPinjaman;
    }

    public void setGetDataStatusPinjaman(TextView getDataStatusPinjaman) {
        this.getDataStatusPinjaman = getDataStatusPinjaman;
    }

    public void setGetDataPermohonan(TextView getDataPermohonan) {
        this.getDataPermohonan = getDataPermohonan;
    }
}

