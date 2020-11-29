package com.ryohandoko.restaurantuas.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentProfileBinding;
import com.ryohandoko.restaurantuas.view.LoginActivity;
import com.ryohandoko.restaurantuas.viewmodel.LoginViewModel;
import com.ryohandoko.restaurantuas.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        sp = getActivity().getSharedPreferences("SECRET", Context.MODE_PRIVATE);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(this);
        binding.setProfileVM(viewModel);

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                // berhentikan progress bar
                viewModel.setIsLoading(!viewModel.getIsLoading().get());

                if(s.equals("Successfully logged out")) {
                    SharedPreferences.Editor editor = sp.edit();

                    editor.remove("id");
                    editor.remove("token");

                    editor.apply();
                    loadActivity();
                }
            }
        });

        binding.executePendingBindings();
        return binding.getRoot();
    }

    public void loadActivity() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(i);
        getActivity().finish();
    }
}