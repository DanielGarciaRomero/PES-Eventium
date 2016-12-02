package com.eventium.eventium.TabFragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.eventium.eventium.R;
import java.util.ArrayList;
import java.util.List;

public class RVAdapter<T> extends RecyclerView.Adapter<ItemViewHolder> {

    List<EventModel> eventModel;
    List<UserModel> userModel;
    boolean RVE;

    public RVAdapter(boolean RVE) {
        this.RVE = RVE;
    }

    public void setRVE(List<EventModel> eventModel) {
        this.eventModel = eventModel;
    }

    public void setRVU(List<UserModel> userModel) {
        this.userModel = userModel;
    }

    public String getItemRVE(int position) {
        return eventModel.get(position).getId();
    }

    public String getItemRVU(int position) {
        return userModel.get(position).getUsername();
    }

    @Override
    public void onBindViewHolder(ItemViewHolder itemViewHolder, int i) {
        if (RVE) {
            final EventModel model = eventModel.get(i);
            itemViewHolder.bind(model);
        }
        else {
            final UserModel model = userModel.get(i);
            itemViewHolder.bind(model);
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view;
        if (RVE) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_events_row, viewGroup, false);
        }
        else {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_users_row, viewGroup, false);
        }
        return new ItemViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (RVE) return eventModel.size();
        else return userModel.size();
    }

    public void setFilterRVE(List<EventModel> models){
        eventModel = new ArrayList<>();
        eventModel.addAll(models);
        notifyDataSetChanged();
    }

    public void setFilterRVU(List<UserModel> models){
        userModel = new ArrayList<>();
        userModel.addAll(models);
        notifyDataSetChanged();
    }

}
