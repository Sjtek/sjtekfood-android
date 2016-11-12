package nl.sjtek.sjtekfood.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by wouter on 12-11-16.
 */

abstract class BaseRequest<T> extends Request<T> {

    private static final String BASE_URL = "https://sjtekfood.habets.io/api";

    private final Response.Listener<T> responseListener;

    BaseRequest(int method, String path, Response.Listener<T> responseListener, Response.ErrorListener listener) {
        super(method, BASE_URL + path, listener);
        this.responseListener = responseListener;
    }

    @Override
    protected void deliverResponse(T response) {
        responseListener.onResponse(response);
    }

    final String parseToString(NetworkResponse response) {
        try {
            return new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            return new String(response.data);
        }
    }

    final Gson getGson() {
        return new GsonBuilder()
                .setDateFormat("yyyyMMdd")
                .create();
    }
}

