package affan.id.qwikfund.fragment;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import affan.id.qwikfund.R;
import affan.id.qwikfund.activity.InvestorForm;
import affan.id.qwikfund.activity.MainActivity;
import affan.id.qwikfund.adapter.CardAdapter;
import affan.id.qwikfund.util.AppConfig;
import affan.id.qwikfund.util.GetBitmap;

/**
 * Created by yunus on 9/5/17.
 */

public class FragmentListInvestor extends Fragment {
    private View view;
    private InvestorForm investorForm;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private AppConfig appConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null){
            view = inflater.inflate(R.layout.fragment_list_investor,container,false);
            investorForm = new InvestorForm();
            //initRecyclerView();
            //getData();
        }

        return view;
    }

    /*private void initRecyclerView(){
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(investorForm);

        recyclerView.setLayoutManager(layoutManager);
    }
*/
    private void getData(){
        class GetData extends AsyncTask<Void,Void,String> {
            ProgressDialog progressDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(investorForm, "Fetching Data", "Please wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                progressDialog.dismiss();
                parseJSON(s);
            }
            @Override
            protected String doInBackground(Void... params) {
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(AppConfig.GET_URL); // url diganti ke route list investor
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetData gd = new GetData();
        gd.execute();
    }

    public void showData(){
        adapter = new CardAdapter(AppConfig.names,AppConfig.urls, AppConfig.bitmaps);
        recyclerView.setAdapter(adapter);
    }

    private void parseJSON(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray array = jsonObject.getJSONArray(AppConfig.TAG_JSON_ARRAY);

            appConfig = new AppConfig(array.length());

            for(int i=0; i<array.length(); i++){
                JSONObject j = array.getJSONObject(i);
                AppConfig.names[i] = getName(j);
                AppConfig.urls[i] = getURL(j);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        GetBitmap gb = new GetBitmap(getContext(),investorForm, AppConfig.urls);
        gb.execute();
    }

    private String getName(JSONObject j){
        String name = null;
        try {
            name = j.getString(AppConfig.TAG_IMAGE_NAME);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return name;
    }

    private String getURL(JSONObject j){
        String url = null;
        try {
            url = j.getString(AppConfig.TAG_IMAGE_URL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }
}
