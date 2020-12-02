package com.ryohandoko.restaurantuas.view.fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
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
import com.ryohandoko.restaurantuas.model.User;
import com.ryohandoko.restaurantuas.R;
import com.ryohandoko.restaurantuas.databinding.FragmentProfileBinding;
import com.ryohandoko.restaurantuas.view.LoginActivity;
import com.ryohandoko.restaurantuas.viewmodel.ProfileViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

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

                viewModel.setIsLoading(false);

                Log.i("GEDHANG", "onChanged: string: " + s);
                switch(s) {
                    case "Successfully logged out": removeSharedPreferences(); break;
                    case "Update Image Success":
                    case "Update Profile Success":
                        Toast.makeText(getContext(), "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                    case "Success": loadData(); break;
                    default: Toast.makeText(getContext(), "Kesalahan Jaringan", Toast.LENGTH_SHORT).show(); break;
                }
            }
        });

        binding.executePendingBindings();
        return binding.getRoot();
    }

    private void loadData() {
        User user = viewModel.getUserLiveData().getValue();

        viewModel.setName(user.getNama_user());
        viewModel.setEmail(user.getEmail());
        viewModel.setTelepon(user.getTelepon());
        viewModel.setGambar(user.getGambar());
    }




    @Override
    public void onResume() {
        super.onResume();
        viewModel.setIsLoading(false);
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

    public void Update(View view) {
        if(viewModel.isAllFieldInputted()) {
//            Log.i("GEDHANG", "DATA: " + viewModel.getName().get());
            viewModel.update();
        }
        else Toast.makeText(getContext(), "Input tidak benar!", Toast.LENGTH_SHORT).show();
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
        if (requestCode == CAMERA_REQUEST && resultCode != 0) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            File file = savebitmap(photo);
            viewModel.Capture(file);

            Log.i("GEDHANG", "onActivityResult: gambar dikirim");
        }
    }

    private File savebitmap(Bitmap bmp) {
        String extStorageDirectory = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        OutputStream outStream = null;
        // String temp = null;

        File file = new File(extStorageDirectory, "temp.png");

        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, "temp.png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();

        } catch (Exception e) {
            Log.i("GEDHANG", "savebitmap: " + e.getMessage());
            return null;
        }

        Log.i("GEDHANG", "savebitmap: file name: " + file.getName());

        return file;
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