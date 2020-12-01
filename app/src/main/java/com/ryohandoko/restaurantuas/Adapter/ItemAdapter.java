package com.ryohandoko.restaurantuas.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.databinding.AdapterProductAdminBinding;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder>{
    private AdapterProductAdminBinding binding;
    private List<Item> listItem;

    public ItemAdapter(List<Item> listItem) {
        this.listItem = listItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        binding = AdapterProductAdminBinding.inflate(layoutInflater, parent, false);

        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = listItem.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() { return listItem.size(); }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private AdapterProductAdminBinding binding;

        public ItemViewHolder(AdapterProductAdminBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Item item) {
            binding.setItem(item);
            binding.executePendingBindings();
        }
    }
}
