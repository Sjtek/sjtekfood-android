package nl.sjtek.sjtekfood.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.sjtek.sjtekfood.R;
import nl.sjtek.sjtekfood.adapters.DinnerAdapter;
import nl.sjtek.sjtekfood.api.API;
import nl.sjtek.sjtekfood.api.AddDinnerRequest;
import nl.sjtek.sjtekfood.api.DinnerRequest;
import nl.sjtek.sjtekfood.api.MealRequest;
import nl.sjtek.sjtekfood.models.Dinner;
import nl.sjtek.sjtekfood.models.Meal;
import nl.sjtek.sjtekfood.views.AddDinnerView;

/**
 * Created by wouter on 12-11-16.
 */

public class DinnerFragment extends Fragment implements Response.ErrorListener {

    private final List<Dinner> dinnerList = new ArrayList<>();
    private final List<Meal> mealList = new ArrayList<>();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private DinnerAdapter adapter;

    private Response.Listener<List<Meal>> mealResponseListener = new Response.Listener<List<Meal>>() {
        @Override
        public void onResponse(List<Meal> response) {
            mealList.clear();
            mealList.addAll(response);
        }
    };

    private Response.Listener<List<Dinner>> dinnerResponseListener = new Response.Listener<List<Dinner>>() {
        @Override
        public void onResponse(List<Dinner> response) {
            dinnerList.clear();
            dinnerList.addAll(response);
            adapter.notifyDataSetChanged();
        }
    };

    private Response.Listener<Dinner> addDinnerResponseListener = new Response.Listener<Dinner>() {
        @Override
        public void onResponse(Dinner response) {
            dinnerList.add(response);
            adapter.notifyItemInserted(dinnerList.size() - 1);
        }
    };

    public static DinnerFragment newInstance() {
        return new DinnerFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dinner, container, false);
        ButterKnife.bind(this, view);
        adapter = new DinnerAdapter(dinnerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        API.getInstance(getContext()).add(new DinnerRequest(dinnerResponseListener, this));
        API.getInstance(getContext()).add(new MealRequest(mealResponseListener, this));
    }

    @OnClick(R.id.fab)
    public void onAddClick() {

        new MaterialDialog.Builder(getContext())
                .title("Add dinner")
                .positiveText("Add")
                .neutralText("Cancel")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        AddDinnerView view = (AddDinnerView) dialog.getCustomView();
                        if (view.getMeal() != null) {
                            API.getInstance(getContext()).add(new AddDinnerRequest(view.getMeal(), view.getDate(), addDinnerResponseListener, DinnerFragment.this));
                        } else if (getView() != null) {
                            Snackbar.make(getView(), "Oops, invalid meal name", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .customView(new AddDinnerView(getContext(), mealList), false)
                .build().show();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        dinnerList.clear();
    }
}
