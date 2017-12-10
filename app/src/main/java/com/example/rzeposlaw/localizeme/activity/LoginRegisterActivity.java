package com.example.rzeposlaw.localizeme.activity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.rzeposlaw.localizeme.adapter.LoginRegisterPagerAdapter;
import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

public class LoginRegisterActivity extends AppCompatActivity {

    private RozhaOneTextView loginButton;
    private RozhaOneTextView registerButton;
    private ViewPager viewPager;
    private boolean loginClicked;
    private boolean registerClicked;
    private LoginRegisterPagerAdapter loginRegisterPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        loginClicked = true;
        registerClicked = false;

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
                    loginRegisterPagerAdapter.validateRegisterInputs();
                }
            }
        });
    }
}
