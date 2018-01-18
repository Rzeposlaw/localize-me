package com.example.rzeposlaw.localizeme.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Credentials implements Parcelable{

    private long id;
    private String username;
    private String password;
    private String email;
    private String sex;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Credentials(Parcel in) {
        this.id = in.readLong();
        this.username = in.readString();
        this.password = in.readString();
        this.email = in.readString();
        this.sex = in.readString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(sex);
    }

    public static final Parcelable.Creator<Credentials> CREATOR = new Parcelable.Creator<Credentials>() {
        public Credentials createFromParcel(Parcel in) {
            return new Credentials(in);
        }

        public Credentials[] newArray(int size) {
            return new Credentials[size];
        }
    };
}
