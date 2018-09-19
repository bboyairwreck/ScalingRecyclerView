package com.ericchee.constraintoverlaytest;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CollapsedAdapter extends RecyclerView.Adapter<CollapsedAdapter.Holder> {


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.collapsed_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        if (position%2 == 0) {
            holder.icon.setBackgroundColor(holder.icon.getContext().getResources().getColor(R.color.colorPrimary));
        } else {
            holder.icon.setBackgroundColor(holder.icon.getContext().getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public int getItemCount() {
        return 100;
    }

    class Holder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon)
        View icon;

        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
