package com.example.rzeposlaw.localizeme.activity;

import android.graphics.Typeface;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.rzeposlaw.localizeme.adapter.LoginRegisterPagerAdapter;
import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

public class LoginRegisterActivity extends AppCompatActivity {

    private RozhaOneTextView loginButton;
    private RozhaOneTextView registerButton;
    private ViewPager viewPager;
    private EditText usernameLogin;
    private EditText passwordLogin;
    private EditText usernameRegister;
    private EditText emailRegister;
    private EditText passwordRegister;
    private EditText repeatPasswordRegister;
    private boolean loginClicked = true;
    private boolean registerClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

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
                if(position == 0)
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
                if(loginClicked){
                    //TODO check inputted data if he can login
                }
                else{
                    loginClicked = true;
                }
                viewPager.setCurrentItem(0);
                loginButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshapeleftblue));
                registerButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshaperight));
                loginButton.setTextColor(getResources().getColor(R.color.buttonTextColor));
                registerButton.setTextColor(getResources().getColor(R.color.strokeButton));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(registerClicked){
                    //TODO check inputted data if he can register
                }
                else{
                    registerClicked = true;
                }
                viewPager.setCurrentItem(1);
                loginButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshapeleft));
                registerButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshaperightblue));
                registerButton.setTextColor(getResources().getColor(R.color.buttonTextColor));
                loginButton.setTextColor(getResources().getColor(R.color.strokeButton));
            }
        });

        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/RozhaOne-Regular.ttf");
        usernameLogin.setTypeface(tf);
        passwordLogin.setTypeface(tf);
    }

    private boolean validateLoginInputs(){
        //TODO
        return false;
    }

    private boolean validateRegisterInputs(){
        //TODO
        return false;
    }
}
