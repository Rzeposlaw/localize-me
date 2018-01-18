package com.example.rzeposlaw.localizeme.adapter;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rzeposlaw.localizeme.R;
import com.example.rzeposlaw.localizeme.activity.FriendListActivity;
import com.example.rzeposlaw.localizeme.data.ApiClient;
import com.example.rzeposlaw.localizeme.data.Credentials;
import com.example.rzeposlaw.localizeme.data.LocationAPI;
import com.example.rzeposlaw.localizeme.data.LocationCommand;
import com.example.rzeposlaw.localizeme.data.User;
import com.example.rzeposlaw.localizeme.view.RozhaOneEditText;
import com.example.rzeposlaw.localizeme.view.RozhaOneTextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRegisterPagerAdapter extends PagerAdapter {

    public static final String USERS = "USERS";
    public static final String LOGGED_USER_ID = "LOGGED_USER_ID";

    private LocationAPI apiService =
            ApiClient.getClient().create(LocationAPI.class);
    private Context mContext;
    private int[] layouts = {R.layout.view_pager_login, R.layout.view_pager_register};
    private View toastView;
    private String lastUsernameLogin;
    private Long loggedInUserId = 999L;

    private RozhaOneEditText usernameLogin;
    private RozhaOneEditText passwordLogin;
    private RozhaOneEditText usernameRegister;
    private RozhaOneEditText emailRegister;
    private RozhaOneEditText passwordRegister;
    private RozhaOneEditText repeatPasswordRegister;

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
        if (position == 0) {
            usernameLogin = (RozhaOneEditText) layout.findViewById(R.id.input_username_login);
            passwordLogin = (RozhaOneEditText) layout.findViewById(R.id.input_password_login);
        } else {
            usernameRegister = (RozhaOneEditText) layout.findViewById(R.id.input_username_register);
            emailRegister = (RozhaOneEditText) layout.findViewById(R.id.input_email_register);
            passwordRegister = (RozhaOneEditText) layout.findViewById(R.id.input_password_register);
            repeatPasswordRegister = (RozhaOneEditText) layout.findViewById(R.id.input_repeat_password_register);
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
        Call<ArrayList<Credentials>> call = apiService.getAllUsers();
        call.enqueue(new Callback<ArrayList<Credentials>>() {
            @Override
            public void onResponse(Call<ArrayList<Credentials>> call, Response<ArrayList<Credentials>> response) {
                ArrayList<Credentials> users = response.body();
                for(int i = 0 ; i < users.size() ; i++){
                    if(users.get(i).getUsername().equals(lastUsernameLogin)){
                        loggedInUserId = users.get(i).getId();
                    }
                    if(users.get(i).getId() == loggedInUserId){
                        users.remove(i);
                    }
                }

                Intent intent = new Intent(mContext, FriendListActivity.class);
                intent.putParcelableArrayListExtra(USERS,users);
                intent.putExtra(LOGGED_USER_ID, Long.valueOf(loggedInUserId));
                mContext.startActivity(intent);
            }

            @Override
            public void onFailure(Call<ArrayList<Credentials>> call, Throwable t) {
            }

        });
    }

    public boolean validateRegisterInputs() {
        if (usernameRegister.getText().toString().equals("") || passwordRegister.getText().toString().equals("")
                || repeatPasswordRegister.getText().toString().equals("")
                || emailRegister.getText().toString().equals("")) {
            showToast(mContext.getResources().getString(R.string.empty_imputs));
            return false;
        } else {
            if (!passwordRegister.getText().toString().equals(repeatPasswordRegister.getText().toString())) {
                showToast(mContext.getResources().getString(R.string.invalid_passwords));
                return false;
            } else {
                Call<User> call = apiService.register
                        (new User(usernameRegister.getText().toString(), passwordRegister.getText().toString(),
                                emailRegister.getText().toString()));
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {
                            showToast(mContext.getResources().getString(R.string.properly_registered));
                            clearRegisterEdittexts();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                    }
                });
                return true;
            }
        }
    }

    private void clearRegisterEdittexts() {
        usernameRegister.setText("");
        emailRegister.setText("");
        passwordRegister.setText("");
        repeatPasswordRegister.setText("");
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
                        lastUsernameLogin = response.body().getUsername();
                        startListActivity();
                        clearLoginEdittexts();
                    } else {
                        showToast(mContext.getResources().getString(R.string.wrong_input_data));
                        clearLoginEdittexts();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
    }

    private void clearLoginEdittexts() {
        usernameLogin.setText("");
        passwordLogin.setText("");
    }

    public void updateUserLocation(Location location){
        LocationCommand locationCommand
                = new LocationCommand((int) location.getLongitude(), (int) location.getLatitude(), loggedInUserId);
        Call<com.example.rzeposlaw.localizeme.data.Location> call = apiService.updateUserLocation(locationCommand);
        call.enqueue(new Callback<com.example.rzeposlaw.localizeme.data.Location>() {
            @Override
            public void onResponse(Call<com.example.rzeposlaw.localizeme.data.Location> call,
                                   Response<com.example.rzeposlaw.localizeme.data.Location> response) {
            }

            @Override
            public void onFailure(Call<com.example.rzeposlaw.localizeme.data.Location> call, Throwable t) {

            }
        });
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
}
