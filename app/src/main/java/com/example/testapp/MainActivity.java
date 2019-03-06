package com.example.testapp;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private TabsPagerAdapter adapter;
    private List<TabLayoutItem> tabLayoutItems;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.string_title_my_requests));
        setContentView(R.layout.activity_main);
        int selectedTab = 0;
        if (savedInstanceState != null) {
            selectedTab = savedInstanceState.getInt("LastTab");
        }
        prepareViews(selectedTab);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putInt("LastTab", tabLayout.getSelectedTabPosition());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    private List<TabLayoutItem> getTabLayoutItems() {
        TabLayoutItem tabLayoutItemCat = new TabLayoutItem("Кошки", getPetFragment("Кошки", "cat"), "cat");
        TabLayoutItem tabLayoutItemDog = new TabLayoutItem("Dog", getPetFragment("Собаки", "dog"), "dog");
        List<TabLayoutItem> tabLayoutItemList = new ArrayList<>();
        tabLayoutItemList.add(tabLayoutItemCat);
        tabLayoutItemList.add(tabLayoutItemDog);
        return tabLayoutItemList;
    }

    private void prepareViews(int index) {
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.removeAllTabs();
        tabLayoutItems = getTabLayoutItems();
        for (TabLayoutItem tabItem : tabLayoutItems) {
            tabLayout.addTab(tabLayout.newTab().setText(tabItem.getTitle()));
        }
        tabLayout.setTabTextColors(
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.white)
        );

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_wrapper, tabLayoutItems.get(tab.getPosition()).getFragment()).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
      /*  getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_wrapper, tabLayoutItems.get(index).getFragment()).commit();
        tabLayout.getTabAt(index).select();*/
    }

    private BaseFragment getPetFragment(String title, String query) {
        return PetsFragment.newInstance(title, query);
    }
}
