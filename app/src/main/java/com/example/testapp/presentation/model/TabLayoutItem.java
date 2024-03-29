package com.example.testapp.presentation.model;

import com.example.testapp.presentation.utils.BaseFragment;
import com.example.testapp.presentation.view.petslist.PetsFragment;

public class TabLayoutItem {
    private String title;
    private PetsFragment fragment;
    private String requestQuery;

    public TabLayoutItem(String title, String query) {
        this.title = title;
        this.requestQuery = query;
        this.fragment = PetsFragment.newInstance(query);
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
