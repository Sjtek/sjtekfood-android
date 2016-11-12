package nl.sjtek.sjtekfood.api;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by wouter on 12-11-16.
 */

public class API {

    private static API instance;
    private final RequestQueue requestQueue;

    private API(Context context) {
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.start();
    }

    public synchronized static API getInstance(Context context) {
        if (instance == null) {
            instance = new API(context);
        }

        return instance;
    }

    public void add(BaseRequest request) {
        requestQueue.add(request);
    }
}
