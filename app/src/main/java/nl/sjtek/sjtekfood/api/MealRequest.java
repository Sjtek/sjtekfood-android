package nl.sjtek.sjtekfood.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nl.sjtek.sjtekfood.models.Meal;

/**
 * Created by wouter on 12-11-16.
 */

public class MealRequest extends BaseRequest<List<Meal>> {

    public MealRequest(Response.Listener<List<Meal>> listener, Response.ErrorListener errorListener) {
        super(Method.GET, "/meals", listener, errorListener);
    }

    @Override
    protected Response<List<Meal>> parseNetworkResponse(NetworkResponse response) {
        List<Meal> meals = getGson().fromJson(parseToString(response), getGsonListToken());
        return Response.success(meals, HttpHeaderParser.parseCacheHeaders(response));
    }

    private Type getGsonListToken() {
        return new TypeToken<ArrayList<Meal>>() {
        }.getType();
    }
}
