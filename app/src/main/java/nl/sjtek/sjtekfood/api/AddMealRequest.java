package nl.sjtek.sjtekfood.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import nl.sjtek.sjtekfood.models.Meal;

/**
 * Created by wouter on 12-11-16.
 */

public class AddMealRequest extends BaseRequest<Meal> {

    private final String mealName;

    public AddMealRequest(String name, Response.Listener<Meal> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, "/meals", responseListener, errorListener);
        mealName = name;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> body = new HashMap<>();
        body.put("name", mealName);
        return body;
    }

    @Override
    protected Response<Meal> parseNetworkResponse(NetworkResponse response) {
        return Response.success(getGson().fromJson(parseToString(response), Meal.class), null);
    }
}
