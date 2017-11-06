package com.example.rzeposlaw.localizeme;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginRegisterActivity extends AppCompatActivity {

    private Button loginButton;
    private Button registerButton;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

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

        loginButton = (Button) findViewById(R.id.login);
        registerButton = (Button) findViewById(R.id.register);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(0);
                loginButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshapeleftorange));
                registerButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshaperight));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(1);
                loginButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshapeleft));
                registerButton.setBackgroundDrawable(getResources()
                        .getDrawable(R.drawable.buttonshaperightorange));
            }
        });
    }
}
