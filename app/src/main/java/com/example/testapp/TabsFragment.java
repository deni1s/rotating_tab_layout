package com.example.testapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TabsFragment extends BaseFragment {

    private TabLayout tabLayout;
    private View rootView;
    private TabsPagerAdapter adapter;
    private List<TabLayoutItem> tabLayoutItems;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setTitle(getString(R.string.string_title_my_requests));
        rootView = inflater.inflate(R.layout.fragment_tabs, container, false);
        prepareViews(rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
           int lastTab = savedInstanceState.getInt("LastTab");
           tabLayout.getTabAt(lastTab).select();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState){
        outState.putInt("LastTab", tabLayout.getSelectedTabPosition());
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, tabLayoutItems.get(0).getFragment()).commitAllowingStateLoss();
    }

    private List<TabLayoutItem> getTabLayoutItems() {
        TabLayoutItem tabLayoutItemCat = new TabLayoutItem("Кошки", getPetFragment("Кошки", "cat"), "cat");
        TabLayoutItem tabLayoutItemDog = new TabLayoutItem("Dog", getPetFragment("Собаки", "dog"), "dog");
        List<TabLayoutItem> tabLayoutItemList = new ArrayList<>();
        tabLayoutItemList.add(tabLayoutItemCat);
        tabLayoutItemList.add(tabLayoutItemDog);
        return tabLayoutItemList;
    }

    private void prepareViews(final View view) {
        tabLayout = rootView.findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        tabLayoutItems = getTabLayoutItems();
        for (TabLayoutItem tabItem : tabLayoutItems) {
            tabLayout.addTab(tabLayout.newTab().setText(tabItem.getTitle()));
        }
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(R.color.white)
        );

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment, tabLayoutItems.get(tab.getPosition()).getFragment()).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, tabLayoutItems.get(0).getFragment()).commit();
    }

    private BaseFragment getPetFragment(String title, String query) {
        return PetsFragment.newInstance(title, query);
    }

}
