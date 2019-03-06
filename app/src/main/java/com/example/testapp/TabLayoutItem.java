package com.example.testapp;

public class TabLayoutItem {
    private String title;
    private BaseFragment fragment;
    private String requestQuery;

    public TabLayoutItem(String title, BaseFragment baseFragment, String query) {
        this.title = title;
        this.fragment = baseFragment;
        this.requestQuery = query;
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
