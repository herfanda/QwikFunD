package affan.id.qwikfund.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

import affan.id.qwikfund.activity.InvestorForm;
import affan.id.qwikfund.activity.MainActivity;

/**
 * Created by Herfanda on 10/3/2017 AD.
 */

public class GetBitmap extends AsyncTask<Void,Void,Void> {

    private Context context;
    private String[] urls;
    private ProgressDialog loading;
    private InvestorForm investorForm;

    public GetBitmap(Context context, InvestorForm investorForm, String[] urls){
        this.context = context;
        this.urls = urls;
        this.investorForm = investorForm;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loading = ProgressDialog.show(context,"Downloading Image","Please wait...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        loading.dismiss();
        investorForm.getFragmentListInvestor().showData();
    }

    @Override
    protected Void doInBackground(Void... params) {
        for(int i=0; i<urls.length; i++){
            AppConfig.bitmaps[i] = getImage(urls[i]);
        }
        return null;
    }

    private Bitmap getImage(String bitmapUrl){
        URL url;
        Bitmap image = null;
        try {
            url = new URL(bitmapUrl);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        }catch(Exception e){}
        return image;
    }

}
