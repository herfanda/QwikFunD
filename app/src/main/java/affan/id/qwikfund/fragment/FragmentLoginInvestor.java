package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import affan.id.qwikfund.activity.BorrowerAktivasi;
import affan.id.qwikfund.activity.BorrowerDashboard;
import affan.id.qwikfund.activity.InvestorDashboard;
import affan.id.qwikfund.activity.InvestorForm;
import affan.id.qwikfund.activity.MainActivity;
import affan.id.qwikfund.controller.AppController;
import affan.id.qwikfund.helper.SQLiteHandler;
import affan.id.qwikfund.util.AppConfig;
import affan.id.qwikfund.util.Global;
import affan.id.qwikfund.util.PrefManager;

import static affan.id.qwikfund.controller.AppController.TAG;

/**
 * Created by Herfanda on 9/29/2017 AD.
 */

public class FragmentLoginInvestor extends Fragment {
    private View view;
    private Button btnLoginInvestor;
    private Button btnAktivasiInvestor;
    private MainActivity mainActivity;
    private EditText edtInputEmail;
    private EditText edtInputPassword;
    private ProgressDialog progressDialog;
    private StringRequest strReq;
    private SQLiteHandler db;

    public FragmentLoginInvestor(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        if (view == null){
            view = inflater.inflate(R.layout.investor_login_layout,container,false);
        }

        initLayout();
        initEvent();
        return view;
    }

    private void initLayout(){
        btnLoginInvestor = (Button) view.findViewById(R.id.btn_login_investor);
        btnAktivasiInvestor = (Button) view.findViewById(R.id.btn_aktivasi_investor);
        edtInputEmail = (EditText) view.findViewById(R.id.edt_input_email);
        edtInputPassword = (EditText) view.findViewById(R.id.edt_input_password);

    }

    private void initEvent(){
        progressDialog = new ProgressDialog(mainActivity);
        progressDialog.setCancelable(false);

        btnLoginInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(mainActivity, InvestorForm.class));
                startActivity(new Intent(mainActivity, InvestorDashboard.class));

                String email = edtInputEmail.getText().toString().trim();
                String password = edtInputPassword.getText().toString().trim();
                String type= "investor";

                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLoginInvestor(email, password, type);
                } else {
                    // Prompt user to enter credentials
                    Toast.makeText(mainActivity,
                            "Please enter the credentials!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });
        btnAktivasiInvestor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mainActivity, BorrowerAktivasi.class));
            }
        });
    }

    private void checkLoginInvestor(final String email, final String password, final String type){

        // Tag used to cancel the request
        String TAG_STRING_REQ = "REQUEST_LOGIN_INVESTOR";

        progressDialog.setMessage("Logging in ...");
        showDialog();

        if (strReq == null){
            strReq = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    Log.d(TAG, "Login Response: " + response.toString());
                    hideDialog();

                    try {
                        JSONObject jObj = new JSONObject(response);
                        int status = jObj.getInt("status"); // 0 status error

                        // Check for error node in json
                        if (status == 1) {
                            // store the user in SQLite
                            JSONObject body = jObj.getJSONObject("body");
                            String token = body.getString("token");

                            // Inserting row in users table
                            //db.addUser(email, password, type);
                            //db.addInitDataLogin(token,type);
                            PrefManager.getInstance(mainActivity).putString(Global.KEY_TOKEN,token);

                            // Launch main activity
                            Intent intent = new Intent(mainActivity,
                                    InvestorDashboard.class);
                            startActivity(intent);
                            mainActivity.finish();
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
            },new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    String json = null;

                    Log.e(TAG, "Login Error: " + error.getMessage());

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
                        Toast.makeText(mainActivity,
                                error.getMessage(), Toast.LENGTH_LONG).show();
                        Toast.makeText(mainActivity,
                                "error response", Toast.LENGTH_LONG).show();
                        hideDialog();
                    }

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
                    /*params.put("email", "linar@danamon.co.id");
                    params.put("password", "rahasia");*/ // local
                    params.put("email",email);
                    params.put("password",password);
                    params.put("type",type);

                    return params;
                }

            };

        }

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, TAG_STRING_REQ);
        Log.d("TAG_LOGIN","CONFIG URL LOGIN "+strReq);

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

    //Somewhere that has access to a context
    public void displayMessage(String toastString){
        Toast.makeText(mainActivity, toastString, Toast.LENGTH_LONG).show();
    }

    private void showDialog() {
        if (!progressDialog.isShowing())
            progressDialog.show();
    }

    private void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}
