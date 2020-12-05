package com.ryohandoko.restaurantuas;

import android.app.Application;

import com.ryohandoko.restaurantuas.API.ApiClient;
import com.ryohandoko.restaurantuas.API.Interface.ApiUserInterface;
import com.ryohandoko.restaurantuas.API.Response.UserResponse;
import com.ryohandoko.restaurantuas.util.InputValidator;
import com.ryohandoko.restaurantuas.viewmodel.LoginViewModel;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;

public class LoginTest {

    private LoginViewModel vm;

    @Before
    public void setUp() throws Exception {
        vm = new LoginViewModel(Mockito.mock(Application.class));
    }

    // test input 1
    @Test
    public void shouldReturnValidEmaildanPassword() throws Exception {
        vm.setEmail("aditiaryo@gmail.com");
        vm.setPassword("123456");
        System.out.println("Testing semua benar\n Email: " + vm.getEmail().get() + "\nPassword: " + vm.getPassword().get());
        assertEquals(InputValidator.emailPasswordValidator(vm.getEmail().get(), vm.getPassword().get()), "Email dan Password Valid");
    }

    // test input 2
    @Test
    public void shouldReturnInvalidEmail() throws Exception {
        vm.setEmail("adityaryo");
        vm.setPassword("123456");
        System.out.println("Testing Email salah \n Email: " + vm.getEmail().get() + "\nPassword: " + vm.getPassword().get());
        assertEquals(InputValidator.emailPasswordValidator(vm.getEmail().get(), vm.getPassword().get()), "Email Tidak Valid");
    }

    // test input 3
    @Test
    public void shouldReturnInvalidPasswordFormat() throws Exception {
        vm.setEmail("adityaryo@gmail.com");
        vm.setPassword("12345");
        System.out.println("Testing Email salah \n Email: " + vm.getEmail().get() + "\nPassword: " + vm.getPassword().get());
        assertEquals(InputValidator.emailPasswordValidator(vm.getEmail().get(), vm.getPassword().get()), "Email dan Password Valid");
    }

    @Test
    public void shouldReturnValidLogin() throws IOException {
        vm.setEmail("aditiaryo@gmail.com");
        vm.setPassword("123456");

        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<UserResponse> request = apiService.userLogin(vm.getEmail().get(), vm.getPassword().get());

        Response<UserResponse> res = request.execute();

        System.out.println("Response: " + res.body().getMessage());
        assertEquals(res.body().getMessage(), "Authenticated");
    }


    @Test
    public void shouldReturnInvalidLogin() throws IOException {
        vm.setEmail("tidakada@email.com");
        vm.setPassword("123456");

        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<UserResponse> request = apiService.userLogin(vm.getEmail().get(), vm.getPassword().get());

        Response<UserResponse> res = request.execute();
        System.out.println("Response: " + res.message());
        assertEquals(res.message(), "Unauthorized");
    }

    @Test
    public void shouldReturnEmailNotVerified() throws IOException {
        vm.setEmail("aaa@co.com");
        vm.setPassword("123456");

        ApiUserInterface apiService = ApiClient.getClient().create(ApiUserInterface.class);
        Call<UserResponse> request = apiService.userLogin(vm.getEmail().get(), vm.getPassword().get());

        Response<UserResponse> res = request.execute();
        System.out.println("Response: email belum diverifikasi error code: " + res.code());
        assertEquals(res.code(), 421);
    }
}