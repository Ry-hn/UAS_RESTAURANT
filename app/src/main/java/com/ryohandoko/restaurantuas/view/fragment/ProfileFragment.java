package com.ryohandoko.restaurantuas.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.ryohandoko.restaurantuas.Model.User;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentProfileBinding;
import com.ryohandoko.restaurantuas.view.LoginActivity;
import com.ryohandoko.restaurantuas.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private SharedPreferences sp;

    private static final int CAMERA_REQUEST = 1888;
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setLifecycleOwner(this);
        binding.setProfileVM(viewModel);
        binding.setProfileView(this);

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

                Log.i("GEDHANG", "onChanged: String: " + s);

                viewModel.setIsLoading(false);

                switch(s) {
                    case "Successfully logged out":
                        removeSharedPreferences();
                        break;
                    case "Success":
                        User user = viewModel.getUserLiveData().getValue();

                        viewModel.setGambar(user.getGambar());
                        viewModel.setName(user.getNama_user());
                        viewModel.setEmail(user.getEmail());
                        viewModel.setTelepon(user.getTelepon());

                        Log.i("GEDHANG", "onChanged: name" + viewModel.getName());
                        break;
                    default:
                        Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        binding.executePendingBindings();
        return binding.getRoot();
    }

    @BindingAdapter("ProfileImage")
    public static void loadImage(ImageView view, String imgUrl) {
        Log.i("GLIDE", "loadImage: " + imgUrl);
        Glide.with(view.getContext())
                .applyDefaultRequestOptions(new RequestOptions()
                        .placeholder(R.drawable.bebek_vector))
                .load(imgUrl)
                .apply(RequestOptions.skipMemoryCacheOf(true))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                .into(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.setIsLoading(false);
    }


    public void Capture(View view) {
        if(checkPermission()) {
            Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(captureIntent, CAMERA_REQUEST);
        }
        else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        return  (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.CAMERA},
                PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(getContext(), "Permission Granted", Toast.LENGTH_SHORT).show();
                else {
                    getActivity().finish();
                }
                break;
        }
    }

    public void LogOut(View view) {
        
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        viewModel.LogOut();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Logout ?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }

    private void removeSharedPreferences() {
        sp = getActivity().getSharedPreferences("SECRET", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        editor.remove("id");
        editor.remove("token");

        editor.apply();
        loadActivity();
    }

    private void loadActivity() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(i);
        getActivity().finish();
    }
}