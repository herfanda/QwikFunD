package affan.id.qwikfund.controller;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Herfanda on 10/1/2017 AD.
 *
 * This class extends from Application which should be executed on app launch.
 * In this class we initiate all the volley core objects.
 */

public class AppController extends Application {

    public static final String TAG = AppController.class.getSimpleName();

    private RequestQueue requestQueue;

    private static AppController instanceController;

    @Override
    public void onCreate() {
        super.onCreate();
        instanceController = this;
    }

    public static synchronized AppController getInstance() {
        return instanceController;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }

}
