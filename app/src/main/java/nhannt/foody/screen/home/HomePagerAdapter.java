package nhannt.foody.screen.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import nhannt.foody.screen.eat_what.WhatToEatFragment;
import nhannt.foody.screen.eat_where.WhereToEatFragment;

/**
 * Created by nhannt on 09/11/2017.
 */

public class HomePagerAdapter extends FragmentStatePagerAdapter {
    private WhatToEatFragment mWhatToEatFragment;
    private WhereToEatFragment mWhereToEatFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
        mWhatToEatFragment = new WhatToEatFragment();
        mWhereToEatFragment = new WhereToEatFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return mWhereToEatFragment;
            case 1:
                return mWhatToEatFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
