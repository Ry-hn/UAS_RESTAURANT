package com.ryohandoko.restaurantuas.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryohandoko.restaurantuas.databinding.AdapterProductUserBinding;
import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.view.admin.DetailProductFragment;
import com.ryohandoko.restaurantuas.view.fragment.DetailProductUser;


import java.util.List;
import java.util.stream.Collectors;

public class ItemUserAdapter extends RecyclerView.Adapter<ItemUserAdapter.ItemUserViewHolder> implements Filterable {
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

        holder.itemView.setOnClickListener(v -> {
            FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
            DetailProductUser dialog = new DetailProductUser();
            dialog.show(manager, "dialog");

            Bundle args = new Bundle();
            args.putString("id", item.getId());
            dialog.setArguments(args);
        });

    }

    @Override
    public int getItemCount() {
        return filteredDataList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(final CharSequence charSequence) {
                filteredDataList = charSequence == null ? listItem :
                        listItem.stream().filter(data -> data.getNama_product().toLowerCase().contains(charSequence) ||
                                data.getId().contains(charSequence)).collect(Collectors.toList());

                FilterResults results = new FilterResults();
                results.values = filteredDataList;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredDataList = (List<Item>) filterResults.values;
                notifyDataSetChanged();
            }
        };
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
