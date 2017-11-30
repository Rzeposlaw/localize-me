package com.example.rzeposlaw.localizeme.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rzeposlaw.localizeme.adapter.LoginRegisterPagerAdapter;
import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.data.User;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterActivity extends AppCompatActivity {

    private static final String TAG = LoginRegisterActivity.class.getName();
    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);

    private RozhaOneTextView loginButton;
    private RozhaOneTextView registerButton;
    private ViewPager viewPager;
    private EditText usernameLogin;
    private EditText passwordLogin;
    private EditText usernameRegister;
    private EditText emailRegister;
    private EditText passwordRegister;
    private EditText repeatPasswordRegister;
    private boolean loginClicked;
    private boolean registerClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        loginClicked = true;
        registerClicked = false;

        usernameLogin = (EditText) findViewById(R.id.input_username_login);
        passwordLogin = (EditText) findViewById(R.id.input_password_login);
        usernameRegister = (EditText) findViewById(R.id.input_username_register);
        emailRegister = (EditText) findViewById(R.id.input_email_register);
        passwordRegister = (EditText) findViewById(R.id.input_password_register);
        repeatPasswordRegister = (EditText) findViewById(R.id.input_repeat_password_register);

        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new LoginRegisterPagerAdapter(this));

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
                    validateLoginInputs();
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!registerClicked) {
                    viewPager.setCurrentItem(1);
                    loginButton.setBackgroundDrawable(getResources()
                            .getDrawable(R.drawable.buttonshapeleft));
                    registerButton.setBackgroundDrawable(getResources()
                            .getDrawable(R.drawable.buttonshaperightblue));
                    registerButton.setTextColor(getResources().getColor(R.color.colorAccent));
                    loginButton.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                    registerClicked = true;
                    loginClicked = false;
                } else {
                    validateRegisterInputs();
                }
            }
        });
    }

    private void showToast(String textToast) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));

        RozhaOneTextView text = (RozhaOneTextView) layout.findViewById(R.id.toast_text);
        text.setText(textToast);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void startListActivity(){
        Intent intent = new Intent(this, FriendListActivity.class);
        startActivity(intent);
    }

    private void validateLoginInputs() {
//        if (usernameLogin.getText().toString().equals("") || passwordLogin.getText().toString().equals("")) {
//            showToast(getResources().getString(R.string.empty_imputs));
//        }else{
        Log.d(TAG, usernameLogin.getText().toString());
            Call<User> call = apiService.login
                    (new User(usernameLogin.getText().toString(), passwordLogin.getText().toString()));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200){
                        startListActivity();
                    }else{
                        showToast(getResources().getString(R.string.wrong_input_data));
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
//        }
    }

    private void validateRegisterInputs() {
//        if (usernameRegister.getText().toString().isEmpty() || passwordRegister.getText().toString().isEmpty()
//                || repeatPasswordRegister.getText().toString().isEmpty()
//                || emailRegister.getText().toString().isEmpty()) {
//            if(!passwordRegister.getText().toString().equals(repeatPasswordRegister.getText().toString())){
//                showToast(getResources().getString(R.string.invalid_passwords));
//            }else
//                showToast(getResources().getString(R.string.empty_imputs));
//        } else{
            Call<User> call = apiService.register
                    (new User(usernameRegister.getText().toString(), passwordRegister.getText().toString()));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {

                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
//        }
    }
}
