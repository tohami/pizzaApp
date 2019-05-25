package com.etisalat.sampletask.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etisalat.sampletask.R;
import com.etisalat.sampletask.api.PizzaApiResponse;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PizzaAdapter extends RecyclerView.Adapter<PizzaAdapter.CustomViewHolder> {

    private ArrayList<PizzaApiResponse.PizzaItem> pizzaItems;


    public PizzaAdapter() {
        this.pizzaItems = new ArrayList<>();
    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pizza_list_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NotNull final CustomViewHolder holder, final int position) {
        holder.bind(pizzaItems.get(position));
    }

    @Override
    public int getItemCount() {
        return pizzaItems == null ? 0 : pizzaItems.size();
    }

    static class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView pizzaName;
        TextView pizzaCost;
        TextView pizzaDesc;

        CustomViewHolder(View itemView) {
            super(itemView);
            pizzaName = itemView.findViewById(R.id.tvPizzaName);
            pizzaCost = itemView.findViewById(R.id.tvPrice);
            pizzaDesc = itemView.findViewById(R.id.tvPizzaDesc);

        }

        void bind(PizzaApiResponse.PizzaItem item){
            pizzaName.setText(item.getName());
            pizzaCost.setText(item.getCost());
            pizzaDesc.setText(item.getDescription());
        }
    }

    public void setData(List<PizzaApiResponse.PizzaItem> data) {
            pizzaItems.clear();
            pizzaItems.addAll(data);
            notifyDataSetChanged();

    }
}
