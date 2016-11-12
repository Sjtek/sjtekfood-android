package nl.sjtek.sjtekfood.api;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import java.util.HashMap;
import java.util.Map;

import nl.sjtek.sjtekfood.models.Dinner;
import nl.sjtek.sjtekfood.models.Meal;

/**
 * Created by wouter on 12-11-16.
 */

public class AddDinnerRequest extends BaseRequest<Dinner> {

    private final long mealId;
    private final String dateString;

    public AddDinnerRequest(Meal meal, String dateString, Response.Listener<Dinner> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, "/dinners", responseListener, errorListener);
        this.mealId = meal.getId();
        this.dateString = dateString;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        Map<String, String> params = new HashMap<>();
        params.put("mealId", String.valueOf(mealId));
        params.put("date", dateString);
        return params;
    }

    @Override
    protected Response<Dinner> parseNetworkResponse(NetworkResponse response) {
        return Response.success(getGson().fromJson(parseToString(response), Dinner.class), null);
    }
}
