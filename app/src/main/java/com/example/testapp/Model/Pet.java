package com.example.testapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Pet implements Parcelable {
    public static final Creator<Pet> CREATOR = new Creator<Pet>() {
        @Override
        public Pet createFromParcel(Parcel in) {
            return new Pet(in);
        }

        @Override
        public Pet[] newArray(int size) {
            return new Pet[size];
        }
    };
    private String url, title;

    public Pet() {
    }

    public Pet(Parcel in) {
        this.title = in.readString();
        this.url = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(url);
    }
}
