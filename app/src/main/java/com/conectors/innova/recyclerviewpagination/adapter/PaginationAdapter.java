package com.conectors.innova.recyclerviewpagination.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conectors.innova.recyclerviewpagination.R;
import com.conectors.innova.recyclerviewpagination.model.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by moi-chan on 22/09/17.
 */

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<Item> items;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM: {
                viewHolder = getViewHolder(parent, inflater);
            } break;

            case LOADING: {
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
            } break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Item item = items.get(position);

        switch (getItemViewType(position)) {
            case ITEM: {
                ItemVH itemVH = (ItemVH) holder;

                itemVH.textView.setText(item.getTitle());
            } break;

            case LOADING: {
                // Do nothing
            } break;
        }
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == items.size() -1 && isLoadingAdded) ? LOADING : ITEM;
    }

    protected class ItemVH extends RecyclerView.ViewHolder {
        private TextView textView;

        public ItemVH(View itemView) {
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.item_txt);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {
        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

    public void add(Item mc) {
        items.add(mc);
        notifyItemInserted(items.size() - 1);
    }

    public void addAll(List<Item> mcList) {
        for (Item mc : mcList) {
            add(mc);
        }
    }

    public void remove(Item city) {
        int position = items.indexOf(city);
        if (position > -1) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Item());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = items.size() -1;
        Item item = getItem(position);

        if(item != null) {
            items.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Item getItem(int position) {
        return items.get(position);
    }
}