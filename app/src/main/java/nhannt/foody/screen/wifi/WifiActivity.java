package nhannt.foody.screen.wifi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.updatewifi.UpdateWifiPopup;
import nhannt.foody.utils.Constants;

public class WifiActivity extends BaseActivity implements WifiDetailContract.View {
    private RecyclerView mRvWifiList;
    private Button mBtnUpdateWifi;
    private ProgressBar mProgressBar;
    private Toolbar mToolbar;
    private TextView mTvPlaceName;
    private WifiDetailContract.Presenter mPresenter;
    private WifiListAdapter mWifiListAdapter;
    private String mPlaceCode = "";
    private String mPlaceName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        mPresenter = new WifiDetailPresenter();
        initViews();
        initEvents();
        mPresenter.setView(this);
        mPlaceCode = getIntent().getStringExtra(Constants.PLACE_CODE_KEY);
        mPlaceName = getIntent().getStringExtra(Constants.PLACE_NAME_KEY);
        mTvPlaceName.setText(mPlaceName);

    }

    private void initEvents() {
        mBtnUpdateWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ipopup = new Intent(WifiActivity.this, UpdateWifiPopup.class);
                ipopup.putExtra(Constants.PLACE_CODE_KEY, mPlaceCode);
                startActivity(ipopup);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getWifiList(mPlaceCode);
    }

    private void initViews() {
        mRvWifiList = findViewById(R.id.rv_wifi_list);
        mBtnUpdateWifi = findViewById(R.id.btn_update_wifi);
        mProgressBar = findViewById(R.id.progress_bar);
        mTvPlaceName = findViewById(R.id.tv_place_name_toolbar);
        //Toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Recycler View
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRvWifiList.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onNavigateUp() {
        onBackPressed();
        return super.onNavigateUp();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void setWifiList(ArrayList<PlaceWifi> listWifi) {
        mWifiListAdapter = new WifiListAdapter(this, listWifi);
        mRvWifiList.setAdapter(mWifiListAdapter);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
