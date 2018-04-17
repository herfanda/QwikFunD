package affan.id.qwikfund.util;

import android.graphics.Bitmap;

/**
 * Created by Herfanda on 10/1/2017 AD.
 */

public class AppConfig {

    private static String URL_MAIN_LOCAL = "http://192.168.1.19/qwikfund";
    private static String URL_MAIN_HOSTING = "http://qwikfund.000webhostapp.com";

    // Server user login url
    public static String URL_LOGIN = URL_MAIN_HOSTING+"/api/login";

    public static String URL_LOGIN_SIMULASI_BORROWER_CALCULATE = URL_MAIN_HOSTING+"/api/borrower/simulasi/calculate";

    public static String URL_LOGIN_SIMULASI_VIEW = URL_MAIN_HOSTING+"/api/borrower/simulasi";

    public static String URL_LIST_PENGAJUAN_BORROWER = URL_MAIN_HOSTING+"/api/borrower/list";

    public static String URL_REQUEST_PROCESS = URL_MAIN_HOSTING+"/api/borrower/request_process";



    // Server user aktivasi url
    public static String URL_BORROWER_AKTIVASI = "http://qwikfund.000webhostapp.com/auth/borrower_aktivasi";

    public static String URL_INVESTOR_AKTIVASI = "http://qwikfund.000webhostapp.com/auth/investor_aktivasi";

    public static String[] names;
    public static String[] urls;
    public static Bitmap[] bitmaps;

    public static final String GET_URL = "http://url";
    public static final String TAG_IMAGE_URL = "url";
    public static final String TAG_IMAGE_NAME = "name";
    public static final String TAG_JSON_ARRAY="result";

    //JSON array name
    public static final String JSON_ARRAY = "result";


    public AppConfig(int i){
        names = new String[i];
        urls = new String[i];
        bitmaps = new Bitmap[i];
    }

}
