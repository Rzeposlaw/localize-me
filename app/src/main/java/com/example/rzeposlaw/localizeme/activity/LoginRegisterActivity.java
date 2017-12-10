package com.example.rzeposlaw.localizeme.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rzeposlaw.localizeme.adapter.LoginRegisterPagerAdapter;
import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.data.User;
import com.example.rzeposlaw.localizeme.view.RozhaOneEditText;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterActivity extends AppCompatActivity {

    private RozhaOneTextView loginButton;
    private RozhaOneTextView registerButton;
    private ViewPager viewPager;
    private RozhaOneEditText usernameRegister;
    private RozhaOneEditText emailRegister;
    private RozhaOneEditText passwordRegister;
    private RozhaOneEditText repeatPasswordRegister;
    private boolean loginClicked;
    private boolean registerClicked;
    private LoginRegisterPagerAdapter loginRegisterPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        loginClicked = true;
        registerClicked = false;

        usernameRegister = (RozhaOneEditText) findViewById(R.id.input_username_register);
        emailRegister = (RozhaOneEditText) findViewById(R.id.input_email_register);
        passwordRegister = (RozhaOneEditText) findViewById(R.id.input_password_register);
        repeatPasswordRegister = (RozhaOneEditText) findViewById(R.id.input_repeat_password_register);

        viewPager = (ViewPager) findViewById(R.id.pager);
        loginRegisterPagerAdapter = new LoginRegisterPagerAdapter(this);
        viewPager.setAdapter(loginRegisterPagerAdapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0)
                    loginButton.performClick();
                else
                    registerButton.performClick();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        loginButton = (RozhaOneTextView) findViewById(R.id.login);
        registerButton = (RozhaOneTextView) findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!loginClicked) {
                    viewPager.setCurrentItem(0);
                    loginButton.setBackgroundDrawable(getResources()
                            .getDrawable(R.drawable.buttonshapeleftblue));
                    registerButton.setBackgroundDrawable(getResources()
                            .getDrawable(R.drawable.buttonshaperight));
                    loginButton.setTextColor(getResources().getColor(R.color.colorAccent));
                    registerButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    loginClicked = true;
                    registerClicked = false;
                } else {
                    loginRegisterPagerAdapter.validateLoginInputs();
                }
            }
        });

//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!registerClicked) {
//                    viewPager.setCurrentItem(1);
//                    loginButton.setBackgroundDrawable(getResources()
//                            .getDrawable(R.drawable.buttonshapeleft));
//                    registerButton.setBackgroundDrawable(getResources()
//                            .getDrawable(R.drawable.buttonshaperightblue));
//                    registerButton.setTextColor(getResources().getColor(R.color.colorAccent));
//                    loginButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
//                    registerClicked = true;
//                    loginClicked = false;
//                } else {
//                    validateRegisterInputs();
//                }
//            }
//        });
    }

//    private void validateRegisterInputs() {
//        if (usernameRegister.getText().toString().isEmpty() || passwordRegister.getText().toString().isEmpty()
//                || repeatPasswordRegister.getText().toString().isEmpty()
//                || emailRegister.getText().toString().isEmpty()) {
//            if (!passwordRegister.getText().toString().equals(repeatPasswordRegister.getText().toString())) {
//                showToast(getResources().getString(R.string.invalid_passwords));
//            } else
//                showToast(getResources().getString(R.string.empty_imputs));
//        } else {
//            Call<User> call = apiService.register
//                    (new User(usernameRegister.getText().toString(), passwordRegister.getText().toString()));
//            call.enqueue(new Callback<User>() {
//                @Override
//                public void onResponse(Call<User> call, Response<User> response) {
//
//                }
//
//                @Override
//                public void onFailure(Call<User> call, Throwable t) {
//
//                }
//            });
//        }
//    }
}
