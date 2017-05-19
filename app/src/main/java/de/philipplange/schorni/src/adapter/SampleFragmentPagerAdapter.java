package de.philipplange.schorni.src.adapter;


import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import de.philipplange.schorni.src.fragments.PageFragment;

/**
 * Created by Philipp on 15.05.2017.
 */

public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {

    int pageCount = 0;
    private ArrayList<Long> tableIDs;
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
        return PageFragment.newInstance(position + 1);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "# " + tableIDs.get(position).toString();
    }


}
