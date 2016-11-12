package nl.sjtek.sjtekfood.adapters;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nl.sjtek.sjtekfood.R;
import nl.sjtek.sjtekfood.models.Dinner;

/**
 * Created by wouter on 12-11-16.
 */

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.ViewHolder> {

    private final List<Dinner> dinnerList;

    public DinnerAdapter(List<Dinner> dinnerList) {
        this.dinnerList = dinnerList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinner, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setDinner(dinnerList.get(position));
    }

    @Override
    public int getItemCount() {
        return dinnerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textViewDate)
        TextView textViewDate;

        @BindView(R.id.textViewName)
        TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setDinner(Dinner dinner) {
            textViewName.setText(dinner.getMeal().getName());
            textViewDate.setText(getDateString(dinner));
        }

        @SuppressLint("SimpleDateFormat")
        public String getDateString(Dinner dinner) {
            Calendar now = Calendar.getInstance();
            Calendar dinnerCal = Calendar.getInstance();
            dinnerCal.setTime(dinner.getDate());
            int dinnerWeek = dinnerCal.get(Calendar.WEEK_OF_YEAR);
            int week = now.get(Calendar.WEEK_OF_YEAR);

            SimpleDateFormat format;
            if (week == dinnerWeek) {
                format = new SimpleDateFormat("EEEE");
            } else {
                format = new SimpleDateFormat("EEEE, d MMMM");
            }

            return format.format(dinner.getDate());
        }
    }
}
