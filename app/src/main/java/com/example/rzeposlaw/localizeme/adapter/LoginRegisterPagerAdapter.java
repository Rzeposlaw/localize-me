package com.example.rzeposlaw.localizeme.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.activity.FriendListActivity;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.data.User;
import com.example.rzeposlaw.localizeme.view.RozhaOneEditText;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterPagerAdapter extends PagerAdapter {

    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);
    private Context mContext;
    private int[] layouts = {R.layout.view_pager_login, R.layout.view_pager_register};
    private View toastView;

    private RozhaOneEditText usernameLogin;
    private RozhaOneEditText passwordLogin;

    public LoginRegisterPagerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(layouts[position], collection, false);
        collection.addView(layout);
        toastView = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) layout.findViewById(R.id.toast_layout_root));
        if(position == 0){
            usernameLogin = (RozhaOneEditText) layout.findViewById(R.id.input_username_login);
            passwordLogin = (RozhaOneEditText) layout.findViewById(R.id.input_password_login);
        }
        else{

        }
        return layout;
    }

    private void showToast(String textToast) {
        RozhaOneTextView text = (RozhaOneTextView) toastView.findViewById(R.id.toast_text);
        text.setText(textToast);

        Toast toast = new Toast(mContext);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

    private void startListActivity() {
        Intent intent = new Intent(mContext, FriendListActivity.class);
        mContext.startActivity(intent);
    }

    public void validateLoginInputs() {
        if (usernameLogin.getText().toString().equals("") || passwordLogin.getText().toString().equals("")) {
            showToast(mContext.getResources().getString(R.string.empty_imputs));
        } else {
            Call<User> call = apiService.login
                    (new User(usernameLogin.getText().toString(), passwordLogin.getText().toString()));
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
                        startListActivity();
                    } else {
                        showToast(mContext.getResources().getString(R.string.wrong_input_data));
                        usernameLogin.setText("");
                        passwordLogin.setText("");
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public RozhaOneEditText getUsernameLogin() {
        return usernameLogin;
    }

    public RozhaOneEditText getPasswordLogin() {
        return passwordLogin;
    }
}
