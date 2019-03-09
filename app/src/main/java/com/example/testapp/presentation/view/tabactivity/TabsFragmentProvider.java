package com.example.testapp.presentation.view.tabactivity;

import android.content.Context;

import com.example.testapp.R;
import com.example.testapp.data.RetrofitSingleton;
import com.example.testapp.model.utils.ListHelper;
import com.example.testapp.presentation.model.TabLayoutItem;

import java.util.ArrayList;
import java.util.List;

public class TabsFragmentProvider {

    private static TabsFragmentProvider instance;
    private static List<TabLayoutItem> tabLayoutItemList;

    private TabsFragmentProvider() {
    }

    static TabsFragmentProvider getInstance() {
        if (instance == null) {
            instance = new TabsFragmentProvider();
        }
        return instance;
    }

    List<TabLayoutItem> getTabLayoutItems(Context context) {
        if (ListHelper.isEmpty(tabLayoutItemList)) {
            tabLayoutItemList = new ArrayList<>();
            TabLayoutItem tabLayoutItemCat = new TabLayoutItem(context.getString(R.string.cats), context.getString(R.string.cats_query));
            TabLayoutItem tabLayoutItemDog = new TabLayoutItem(context.getString(R.string.dogs), context.getString(R.string.dogs_query));
            tabLayoutItemList.add(tabLayoutItemCat);
            tabLayoutItemList.add(tabLayoutItemDog);
        }
        return tabLayoutItemList;
    }
}
