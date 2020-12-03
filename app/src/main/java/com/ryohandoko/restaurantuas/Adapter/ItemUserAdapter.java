package com.ryohandoko.restaurantuas.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryohandoko.restaurantuas.databinding.AdapterProductUserBinding;
import com.ryohandoko.restaurantuas.model.Item;


import java.util.List;

public class ItemUserAdapter extends RecyclerView.Adapter<ItemUserAdapter.ItemUserViewHolder> {
    private AdapterProductUserBinding binding;

    private List<Item> listItem;
    private List<Item> filteredDataList;

    private Context context;

    public ItemUserAdapter(Context context, List<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.filteredDataList = listItem;
    }

    @NonNull
    @Override
    public ItemUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = AdapterProductUserBinding.inflate(layoutInflater, parent, false);

        return new ItemUserAdapter.ItemUserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemUserViewHolder holder, int position) {
        Item item = filteredDataList.get(position);
        holder.bind(item);

    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    static class ItemUserViewHolder extends RecyclerView.ViewHolder {
        private AdapterProductUserBinding binding;

        public ItemUserViewHolder(AdapterProductUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
