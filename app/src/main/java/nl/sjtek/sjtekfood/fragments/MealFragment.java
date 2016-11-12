package nl.sjtek.sjtekfood.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.sjtek.sjtekfood.R;
import nl.sjtek.sjtekfood.adapters.MealAdapter;
import nl.sjtek.sjtekfood.api.API;
import nl.sjtek.sjtekfood.api.AddMealRequest;
import nl.sjtek.sjtekfood.api.MealRequest;
import nl.sjtek.sjtekfood.models.Meal;

/**
 * Created by wouter on 12-11-16.
 */

public class MealFragment extends Fragment implements
        Response.Listener<List<Meal>>,
        Response.ErrorListener,
        MaterialDialog.InputCallback {

    private final List<Meal> mealList = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MealAdapter adapter;

    public static MealFragment newInstance() {
        return new MealFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal, container, false);
        ButterKnife.bind(this, view);
        adapter = new MealAdapter(mealList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        API.getInstance(getContext()).add(new MealRequest(this, this));
    }

    @OnClick(R.id.fab)
    public void onAddClick() {
        new MaterialDialog.Builder(getContext())
                .input("Name", "", false, this)
                .build()
                .show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        mealList.clear();
    }

    @Override
    public void onResponse(List<Meal> response) {
        mealList.clear();
        mealList.addAll(response);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
        if (TextUtils.isEmpty(input)) {
            if (getView() != null)
                Snackbar.make(getView(), "Oops, invalid name.", Snackbar.LENGTH_SHORT).show();
        } else {
            API.getInstance(getContext()).add(new AddMealRequest(input.toString(), new Response.Listener<Meal>() {
                @Override
                public void onResponse(Meal response) {
                    mealList.add(response);
                    adapter.notifyItemInserted(mealList.size() - 1);
                }
            }, this));
        }
    }
}
