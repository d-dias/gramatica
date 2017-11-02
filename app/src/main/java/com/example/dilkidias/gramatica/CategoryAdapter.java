package com.example.dilkidias.gramatica;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DILKI DIAS on 28-Jul-17.
 */


public class CategoryAdapter extends FragmentPagerAdapter{

    private static final int NUMBER_OF_PAGERS = 2;

    /**
     * Context of the app
     */
    private Context mContext;


/**
 * Create a new [CategoryAdapter] object.
 *
 * @param context is the context of the app
 * @param fm      is the fragment manager that will keep each fragment's state in the adapter
 *
 *
 * across swipes.
 */

public CategoryAdapter(Context context, FragmentManager fm) {
    super(fm);
    mContext = context;
}

//
//(
//    /**
//     * Context of the app
//     */
//    private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    /**
     * Return the [Fragment] that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new VerbFragment();
        }else{
            return new TensesFragment();
        }
    }


    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return NUMBER_OF_PAGERS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
           return mContext.getString(R.string.category_verbs);
        }else {
            return mContext.getString(R.string.category_tenses);
        }
    }
}
