package com.example.testapp.TabActivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.testapp.BaseFragment;
import com.example.testapp.PetsList.PetsFragment;
import com.example.testapp.R;
import com.example.testapp.TabLayoutItem;

import java.util.ArrayList;
import java.util.List;

public class TabActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {
    private TabLayout tabLayout;
    private List<TabLayoutItem> tabLayoutItems;
    private final String BUNDLE_KEY_TAB_POSITION = "tabposition";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.string_title_my_requests));
        setContentView(R.layout.activity_main);
        initToolbar();
        prepareTabLayout();
        if (savedInstanceState != null) {
            int selectedTabPosition = savedInstanceState.getInt(BUNDLE_KEY_TAB_POSITION);
            tabLayout.getTabAt(selectedTabPosition).select();
        } else {
            openFragment(tabLayoutItems.get(0).getFragment());
        }
        tabLayout.addOnTabSelectedListener(this);
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(BUNDLE_KEY_TAB_POSITION, tabLayout.getSelectedTabPosition());
    }

    private List<TabLayoutItem> getTabLayoutItems() {
        TabLayoutItem tabLayoutItemCat = new TabLayoutItem(getString(R.string.cats), getString(R.string.cats_query));
        TabLayoutItem tabLayoutItemDog = new TabLayoutItem(getString(R.string.dogs), getString(R.string.dogs_query));
        List<TabLayoutItem> tabLayoutItemList = new ArrayList<>();
        tabLayoutItemList.add(tabLayoutItemCat);
        tabLayoutItemList.add(tabLayoutItemDog);
        return tabLayoutItemList;
    }

    private void prepareTabLayout() {
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.removeOnTabSelectedListener(this);
        tabLayout.removeAllTabs();
        tabLayoutItems = getTabLayoutItems();
        for (TabLayoutItem tabItem : tabLayoutItems) {
            tabLayout.addTab(tabLayout.newTab().setText(tabItem.getTitle()));
        }
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.white)
        );
    }

    private void openFragment(BaseFragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_wrapper, fragment).commit();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        openFragment(tabLayoutItems.get(tab.getPosition()).getFragment());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
