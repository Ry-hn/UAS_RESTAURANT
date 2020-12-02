package com.ryohandoko.restaurantuas.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.ryohandoko.restaurantuas.Adapter.ItemAdapter;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.ActivityShowProductBinding;
import com.ryohandoko.restaurantuas.viewmodel.LoginViewModel;
import com.ryohandoko.restaurantuas.viewmodel.ShowProductViewModel;

public class ShowProductActivity extends AppCompatActivity {

    private ItemAdapter adapter;
    private RecyclerView recyclerView;
    private ActivityShowProductBinding binding;
    private ShowProductViewModel viewModel;
    private ShimmerFrameLayout shimmerFrameLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_product);
        viewModel = ViewModelProviders.of(this).get(ShowProductViewModel.class);

        binding.setVM(viewModel);
        binding.setLifecycleOwner(this);

        recyclerView = binding.getRoot().findViewById(R.id.admin_recyclerview_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchView = binding.getRoot().findViewById(R.id.searchProduct);
        swipeRefreshLayout = binding.getRoot().findViewById(R.id.swipeRefresh);
        shimmerFrameLayout = binding.getRoot().findViewById(R.id.shimmerLayout);

        shimmerFrameLayout.startShimmer();
        swipeRefreshLayout.setRefreshing(true);

        viewModel.getProducts();
        
        swipeRefreshLayout.setOnRefreshListener( () -> {
            viewModel.getProducts();
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                swipeRefreshLayout.setRefreshing(false);

                switch (s) {
                    case "Retrieve All Success":
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                        adapter = new ItemAdapter(ShowProductActivity.this, viewModel.getProductsLiveData().getValue());
                        recyclerView.setAdapter(adapter);
                        break;
                }
            }
        });


        binding.executePendingBindings();
    }

    public void back(View view) { onBackPressed(); }
}