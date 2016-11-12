package nl.sjtek.sjtekfood.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.sjtek.sjtekfood.models.Dinner;

/**
 * Created by wouter on 12-11-16.
 */

public class DinnerRequest extends BaseRequest<List<Dinner>> {

    public DinnerRequest(Response.Listener<List<Dinner>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "/dinners", listener, errorListener);
    }

    @Override
    protected Response<List<Dinner>> parseNetworkResponse(NetworkResponse response) {
        List<Dinner> dinners = getGson().fromJson(parseToString(response), getGsonListToken());
        return Response.success(dinners, HttpHeaderParser.parseCacheHeaders(response));
    }

    private Type getGsonListToken() {
        return new TypeToken<ArrayList<Dinner>>() {
        }.getType();
    }
}
