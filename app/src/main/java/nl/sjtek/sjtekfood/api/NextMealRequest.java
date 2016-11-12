package nl.sjtek.sjtekfood.api;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import nl.sjtek.sjtekfood.models.Meal;

/**
 * Created by wouter on 12-11-16.
 */

public class NextMealRequest extends BaseRequest<Meal> {
    public NextMealRequest(Response.Listener<Meal> responseListener, Response.ErrorListener listener) {
        super(Method.GET, "/meals/next", responseListener, listener);
    }

    @Override
    protected Response<Meal> parseNetworkResponse(NetworkResponse response) {
        return Response.success(new Gson().fromJson(parseToString(response), Meal.class), HttpHeaderParser.parseCacheHeaders(response));
    }
}
