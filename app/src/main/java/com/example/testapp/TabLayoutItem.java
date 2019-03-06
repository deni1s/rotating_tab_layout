package com.example.testapp;

import com.example.testapp.PetsList.PetsFragment;

public class TabLayoutItem {
    private String title;
    private PetsFragment fragment;
    private String requestQuery;

    public TabLayoutItem(String title, String query) {
        this.title = title;
        this.requestQuery = query;
        this.fragment = PetsFragment.newInstance(title, query);
    }

    public String getTitle() {
        return title;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public String getRequestQuery() {
        return requestQuery;
    }
}
