package com.eventium.eventium.TabFragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eventium.eventium.R;
import java.util.ArrayList;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<EventModel> mCountryModel;
    private List<EventModel> mOriginalCountryModel;

    public RVAdapter(List<EventModel> mCountryModel) {
        this.mCountryModel = mCountryModel;
        this.mOriginalCountryModel = mCountryModel;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        final EventModel model = mCountryModel.get(i);
        itemViewHolder.bind(model);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_events_row, viewGroup, false);
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mCountryModel.size();
    }

    public void setFilter(List<EventModel> countryModels){
        mCountryModel = new ArrayList<>();
        mCountryModel.addAll(countryModels);
        notifyDataSetChanged();
    }

}
