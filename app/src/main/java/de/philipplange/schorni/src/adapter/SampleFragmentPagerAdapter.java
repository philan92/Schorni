package de.philipplange.schorni.src.adapter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import de.philipplange.schorni.src.fragments.PageFragment;


/**
 * Adapter für die Fragmente, die in den verschiedenen Tabs angezeigt werden
 */
public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    private int pageCount = 0;
    private ArrayList<Long> tableIDs; // Liste mit den ListIDs, die geziegt werden
    private Context context;

    public SampleFragmentPagerAdapter(FragmentManager fm, Context context, ArrayList<Long> tableIDs) {
        super(fm);
        this.context = context;
        this.pageCount = tableIDs.size();
        this.tableIDs = tableIDs;
    }

    @Override
    public int getCount() {
        return pageCount;
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return PageFragment.newInstance(position + 1, tableIDs.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "# " + tableIDs.get(position).toString();
    }


}
