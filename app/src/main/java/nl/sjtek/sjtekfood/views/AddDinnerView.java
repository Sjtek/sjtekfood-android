package nl.sjtek.sjtekfood.views;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import nl.sjtek.sjtekfood.R;
import nl.sjtek.sjtekfood.models.Meal;

/**
 * Created by wouter on 12-11-16.
 */

public class AddDinnerView extends LinearLayout implements DatePickerDialog.OnDateSetListener {

    private final List<Meal> mealList;
    @BindView(R.id.textView)
    AutoCompleteTextView textView;
    @BindView(R.id.editTextDate)
    EditText editTextDate;
    private String dateString = "";

    public AddDinnerView(Context context) {
        this(context, new ArrayList<Meal>());
    }

    public AddDinnerView(Context context, List<Meal> mealList) {
        super(context, null, 0);
        inflate(context, R.layout.dialog_add_dinner, this);
        ButterKnife.bind(this);

        this.mealList = mealList;

        List<String> mealNames = new ArrayList<>();
        for (Meal meal : mealList) {
            mealNames.add(meal.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, mealNames);

        textView.setAdapter(adapter);
    }

    @OnClick(R.id.editTextDate)
    public void onDateClick() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        dateString = String.format("%04d%02d%02d", year, monthOfYear, dayOfMonth);
        editTextDate.setText(String.format("%02d - %02d - %04d", dayOfMonth, monthOfYear, year));
    }

    public String getDate() {
        return dateString;
    }

    public Meal getMeal() {
        String mealName = textView.getText().toString();
        for (Meal meal : mealList) {
            if (meal.getName().equals(mealName)) return meal;
        }

        return null;
    }
}
