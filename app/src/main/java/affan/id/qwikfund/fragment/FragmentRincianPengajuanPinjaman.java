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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import affan.id.qwikfund.R;
import affan.id.qwikfund.activity.BorrowerDashboard;
import affan.id.qwikfund.controller.AppController;
import affan.id.qwikfund.util.AppConfig;
import affan.id.qwikfund.util.Global;
import affan.id.qwikfund.util.PrefManager;

import static affan.id.qwikfund.controller.AppController.TAG;

/**
 * Created by Herfanda on 10/9/2017 AD.
 */

public class FragmentRincianPengajuanPinjaman extends Fragment {
    private View view;
    private Button btnKembali;
    private Button btnSubmit;
    private Fragment fragment;
    private FragmentManager fragmentManager;

    private ProgressDialog pDialog;

    private String getTujuanPinjaman;
    private String getNilaiPinjaman;
    private String getLamaPinjaman;
    private String getBiayaAdministrasi;
    private String getPencairanDana;

    private EditText edtTujuanPinjaman;
    private EditText edtNilaiPinjaman;
    private EditText edtLamaPinjaman;
    private EditText edtBiayaAdministrasi;
    private EditText edtPencairanDana;

    private EditText edtDeskripsiPengajuanPinjaman;
    private StringRequest strReqForCalculate;
    private String inputTokenAfterLogin;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.rincian_pengajuan_pinjaman,container,false);
        }

        initLayout();
        initData();
        initEvent();

        return view;
    }

    private void initLayout(){
        edtTujuanPinjaman = (EditText) view.findViewById(R.id.input_tujuan_pinjaman_pengajuan);
        edtTujuanPinjaman.setEnabled(false);

        edtNilaiPinjaman = (EditText) view.findViewById(R.id.input_nilai_pinjaman_pengajuan);
        edtNilaiPinjaman.setEnabled(false);

        edtLamaPinjaman = (EditText) view.findViewById(R.id.input_lama_pinjaman);
        edtLamaPinjaman.setEnabled(false);

        edtBiayaAdministrasi = (EditText) view.findViewById(R.id.input_biaya_administrasi_pengajuan);
        edtBiayaAdministrasi.setEnabled(false);

        edtPencairanDana = (EditText) view.findViewById(R.id.input_nilai_pencairan_dana);
        edtPencairanDana.setEnabled(false);

        edtDeskripsiPengajuanPinjaman = (EditText) view.findViewById(R.id.deskripsi_pengajuan_pinjaman);


        btnKembali = (Button) view.findViewById(R.id.btn_kembali);
        btnSubmit = (Button) view.findViewById(R.id.btn_submit_rincian_pengajuan);

    }

    private void initData(){
        getTujuanPinjaman = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_TUJUAN,Global.STRING_DEFAULT_VALUE);
        getNilaiPinjaman = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_PINJAMAN,Global.STRING_DEFAULT_VALUE);
        getLamaPinjaman = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_LAMA,Global.STRING_DEFAULT_VALUE);
        getBiayaAdministrasi = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_BIAYA_ADM,Global.STRING_DEFAULT_VALUE);
        getPencairanDana = PrefManager.getInstance(getContext()).getString(Global.KEY_SELECTED_PENCAIRAN_DANA,Global.STRING_DEFAULT_VALUE);


        edtTujuanPinjaman.setText(getTujuanPinjaman);
        edtNilaiPinjaman.setText(getNilaiPinjaman);
        edtLamaPinjaman.setText(getLamaPinjaman);
        edtBiayaAdministrasi.setText(getBiayaAdministrasi);
        edtPencairanDana.setText(getPencairanDana);


    }

    private void initEvent(){
        fragment = null;
        fragmentManager = getFragmentManager();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String deskripsiPengajuan = edtDeskripsiPengajuanPinjaman.getText().toString();
                PrefManager.getInstance(getContext()).putString(Global.KEY_INPUT_DESKRIPSI,deskripsiPengajuan);

                if (!deskripsiPengajuan.isEmpty()){
                    if (fragment == null){
                        //requestProcess();
                        fragment = new FragmentListPengajuan();
                    }

                    fragmentManager.beginTransaction()
                            .replace(R.id.content_frame_borrower, fragment)
                            .commit();
                } else {
                    Toast.makeText(getContext(),"Kolom Deskripsi Wajib Diisi",Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fragment == null){
                    fragment = new FragmentDashboardBorrower();
                }

                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame_borrower, fragment)
                        .commit();

            }
        });

    }
}
