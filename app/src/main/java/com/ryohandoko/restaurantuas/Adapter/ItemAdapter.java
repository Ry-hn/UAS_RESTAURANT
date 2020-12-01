package com.ryohandoko.restaurantuas.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryohandoko.restaurantuas.model.Item;
import com.ryohandoko.restaurantuas.databinding.AdapterProductAdminBinding;

import java.util.List;
import java.util.stream.Collectors;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable {
    private AdapterProductAdminBinding binding;
    private List<Item> listItem;
    private List<Item> filteredDataList;

    private Context context;

    public ItemAdapter(Context context, List<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
        this.filteredDataList = listItem;
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
        Item item = filteredDataList.get(position);
        holder.bind(item);

        // this works
        holder.itemView.setOnClickListener( v -> {

        });
    }

    @Override
    public int getItemCount() { return filteredDataList.size(); }

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
