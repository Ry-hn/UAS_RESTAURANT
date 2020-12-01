package com.ryohandoko.restaurantuas.view.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_show_product);
        viewModel = ViewModelProviders.of(this).get(ShowProductViewModel.class);
        binding.setVM(viewModel);
        binding.setLifecycleOwner(this);

        recyclerView = binding.getRoot().findViewById(R.id.admin_recyclerview_product);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        shimmerFrameLayout = binding.getRoot().findViewById(R.id.shimmerLayout);
        shimmerFrameLayout.startShimmer();

        viewModel.getProducts();

        viewModel.getErrorMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

                switch (s) {
                    case "Retrieve All Success":
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);

                        adapter = new ItemAdapter(viewModel.getProductsLiveData().getValue());
                        recyclerView.setAdapter(adapter);
                        break;
                }
            }
        });

        binding.executePendingBindings();
    }

    public void back(View view) { onBackPressed(); }
}