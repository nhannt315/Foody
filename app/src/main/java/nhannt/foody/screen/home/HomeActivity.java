package nhannt.foody.screen.home;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import nhannt.foody.R;
import nhannt.foody.screen.BaseActivity;

public class HomeActivity extends BaseActivity implements HomeContract.View,View.OnClickListener {

    private ViewPager mHomeViewPager;
    private RadioButton mRdWhereEat;
    private RadioButton mRdWhatEat;
    private HomePagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
        setupViewPager();
        initEvents();
    }

    private void initEvents() {
        mRdWhereEat.setOnClickListener(this);
        mRdWhatEat.setOnClickListener(this);
    }

    private void setupViewPager() {
        mPagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        mHomeViewPager.setAdapter(mPagerAdapter);
        mHomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mRdWhereEat.setChecked(true);
                        break;
                    case 1:
                        mRdWhatEat.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        mHomeViewPager = findViewById(R.id.viewpager_home);
        mRdWhatEat = findViewById(R.id.rd_what_to_eat);
        mRdWhereEat = findViewById(R.id.rd_where_to_eat);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rd_where_to_eat:
                this.mHomeViewPager.setCurrentItem(0);
                break;
            case R.id.rd_what_to_eat:
                this.mHomeViewPager.setCurrentItem(1);
                break;
        }
    }
}
