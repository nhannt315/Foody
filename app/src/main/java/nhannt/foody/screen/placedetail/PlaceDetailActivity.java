package nhannt.foody.screen.placedetail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.Branch;
import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.Place;
import nhannt.foody.data.model.PlaceWifi;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.addcomment.AddCommentActivity;
import nhannt.foody.screen.placedirection.PlaceDirectionActivity;
import nhannt.foody.screen.wifi.WifiActivity;
import nhannt.foody.utils.Constants;
import nhannt.foody.utils.Utils;

public class PlaceDetailActivity extends BaseActivity
    implements PlaceDetailContract.View, OnMapReadyCallback, View.OnClickListener {
    private PlaceDetailContract.Presenter mPresenter;
    private TextView mTvPlaceName, mTvPlaceAddress, mTvOpenTime, mTvStatus, mTvTotalImage,
        mTvTotalCheckin, mTvTotalBookmark, mTvTotalComment, mTvPlaceNameToolbar,
        mTvPriceRange, mTvWifiName, mTvWifiPassword, mTvWifiDate;
    private ImageView mImgPlayBtn;
    private Button mBtnAddComment;
    private LinearLayout mLLMap;
    private LinearLayout mLLUtils, mLLWifiContainer;
    private ImageView mImgPlaceImage;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewComment;
    private NestedScrollView mNestedScrollView;
    private CommentRecyclerViewAdapter mCommentAdapter;
    private Place mPlace;
    private GoogleMap mGoogleMap;
    private MapFragment mMapFragment;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        mPresenter = new PlaceDetailPresenter();
        mPresenter.setView(this);
        initViews();
        initEvents();
        mPlace = getIntent().getParcelableExtra("place");
        setToolbar();
        setViews();
        mPresenter.downloadUtilImage(mPlace.getTienich());
        if(mPlace.getVideogioithieu() != null){
            mPresenter.getVideoUrl(mPlace.getVideogioithieu());
            mImgPlaceImage.setVisibility(View.GONE);
            mImgPlayBtn.setVisibility(View.VISIBLE);
        }else{
            mPresenter.getPlaceImage(mPlace.getHinhanhquanan().get(0));
            mVideoView.setVisibility(View.GONE);
            mImgPlaceImage.setVisibility(View.VISIBLE);
            mImgPlayBtn.setVisibility(View.GONE);
        }
    }

    private void initEvents() {
        mLLWifiContainer.setOnClickListener(this);
        mLLMap.setOnClickListener(this);
        mBtnAddComment.setOnClickListener(this);
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getWifiList(mPlace.getMaquanan());
        if(mPlace.getVideogioithieu() != null)
            mImgPlayBtn.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mPlace.getVideogioithieu() != null){
            mVideoView.pause();
        }
    }

    private void setViews() {
        mTvPlaceNameToolbar.setText(mPlace.getTenquanan());
        mTvPlaceName.setText(mPlace.getTenquanan());
        if (mPlace.getLstBranch().size() > 0) {
            mTvPlaceAddress.setText(mPlace.getLstBranch().get(0).getDiachi());
        }
        mTvOpenTime.setText(mPlace.getGiomocua() + " - " + mPlace.getGiodongcua());
        mTvTotalImage.setText(mPlace.getHinhanhquanan().size() + "");
        mTvTotalComment.setText(mPlace.getBinhluanList().size() + "");
        mTvStatus.setText(Utils.getPlaceStatus(mPlace.getGiomocua(), mPlace.getGiodongcua()));
        if (mPlace.getGiatoida() != 0 && mPlace.getGiatoithieu() != 0) {
            NumberFormat numberFormat = new DecimalFormat("###,###");
            String minPrice = numberFormat.format(mPlace.getGiatoithieu()) + " VND";
            String maxPrice = numberFormat.format(mPlace.getGiatoida()) + "VND";
            mTvPriceRange.setText(minPrice + " - " + maxPrice);
        } else {
            mTvPriceRange.setVisibility(View.GONE);
        }
        // Setup recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewComment.setLayoutManager(layoutManager);
        mRecyclerViewComment.setNestedScrollingEnabled(false);
        // Map
        mMapFragment.getMapAsync(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mTvPlaceName = findViewById(R.id.tv_place_name);
        mTvPlaceAddress = findViewById(R.id.tv_place_address);
        mTvOpenTime = findViewById(R.id.tv_open_time);
        mTvStatus = findViewById(R.id.tv_status);
        mTvTotalImage = findViewById(R.id.tv_total_image);
        mTvTotalCheckin = findViewById(R.id.tv_total_checkin);
        mTvTotalBookmark = findViewById(R.id.tv_total_bookmark);
        mTvTotalComment = findViewById(R.id.tv_total_comment);
        mImgPlaceImage = findViewById(R.id.img_place);
        mTvPlaceNameToolbar = findViewById(R.id.tv_place_name_toolbar);
        mRecyclerViewComment = findViewById(R.id.rv_comment_detail);
        mNestedScrollView = findViewById(R.id.nested_scroll_view_place_detail);
        mTvPriceRange = findViewById(R.id.tv_price_range);
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mLLUtils = findViewById(R.id.ll_utils_place_detail);
        mTvWifiName = findViewById(R.id.tv_wifi_name);
        mTvWifiPassword = findViewById(R.id.tv_password_wifi);
        mTvWifiDate = findViewById(R.id.tv_wifi_date);
        mLLWifiContainer = findViewById(R.id.ll_wifi);
        mLLMap = findViewById(R.id.ll_map_place_detail);
        mBtnAddComment = findViewById(R.id.btn_add_comment);
        mVideoView = findViewById(R.id.video_trailer);
        mImgPlayBtn = findViewById(R.id.img_play_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.getListComment(mPlace.getMaquanan());
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void setPlaceImage(String url) {
        Glide.with(this).load(url).into(mImgPlaceImage);
    }

    @Override
    public void appendUtilImage(String url) {
        final ImageView imageUtil = new ImageView(PlaceDetailActivity.this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(100, 100);
        layoutParams.setMargins(10, 10, 10, 10);
        imageUtil.setLayoutParams(layoutParams);
        imageUtil.setScaleType(ImageView.ScaleType.FIT_XY);
        imageUtil.setPadding(5, 5, 5, 5);
        mLLUtils.addView(imageUtil);
        Glide.with(this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                        Target<Drawable> target,
                                        boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                           DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageUtil);
    }

    @Override
    public void showListWifi(ArrayList<PlaceWifi> lstWifi) {
        if (lstWifi.size() > 0) {
            mTvWifiName.setText(lstWifi.get(0).getTen());
            mTvWifiPassword.setText(lstWifi.get(0).getMatkhau());
            mTvWifiDate.setText(lstWifi.get(0).getNgaydang());
        } else {
            mTvWifiName.setText(getResources().getString(R.string.click_to_add_wifi));
        }
    }

    @Override
    public void showListComment(ArrayList<Comment> lstComment) {
        mCommentAdapter = new CommentRecyclerViewAdapter(this, lstComment);
        mRecyclerViewComment.setAdapter(mCommentAdapter);
    }

    @Override
    public void setVideo(String url) {
        mVideoView.setVisibility(View.VISIBLE);

        mVideoView.setVideoPath(url);
        mVideoView.seekTo(1);
        mImgPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideoView.start();
                mImgPlayBtn.setVisibility(View.GONE);
                MediaController mediaController = new MediaController(PlaceDetailActivity.this);
                mediaController.setAnchorView(mVideoView);
                mediaController.setMediaPlayer(mVideoView);
                mVideoView.setMediaController(mediaController);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        Branch branch = mPlace.getLstBranch().get(0);
        LatLng latLng = new LatLng(branch.getLatitude(), branch.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title(mPlace.getTenquanan());
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_wifi:
                if (mPlace != null) {
                    Intent intent = new Intent(PlaceDetailActivity.this, WifiActivity.class);
                    intent.putExtra(Constants.PLACE_CODE_KEY, mPlace.getMaquanan());
                    intent.putExtra(Constants.PLACE_NAME_KEY, mPlace.getTenquanan());
                    startActivity(intent);
                }
                break;
            case R.id.ll_map_place_detail:
                if (mPlace != null) {
                    Intent intentToMapDetail = new Intent(this, PlaceDirectionActivity.class);
                    intentToMapDetail.putExtra(Constants.PLACE_NAME_KEY,
                        mPlace.getTenquanan());
                    intentToMapDetail.putExtra(Constants.LATITUDE_KEY, Utils.getClosetBranch(mPlace)
                        .getLatitude());
                    intentToMapDetail
                        .putExtra(Constants.LONGITUDE_KEY, Utils.getClosetBranch(mPlace)
                            .getLongitude());
                    startActivity(intentToMapDetail);
                }
                break;
            case R.id.btn_add_comment:
                if (mPlace != null) {
                    Intent intentToComment = new Intent(this, AddCommentActivity.class);
                    intentToComment.putExtra(Constants.PLACE_CODE_KEY, mPlace.getMaquanan());
                    intentToComment.putExtra(Constants.PLACE_ADDRESS_KEY,
                        mTvPlaceAddress.getText().toString());
                    intentToComment.putExtra(Constants.PLACE_NAME_KEY, mPlace.getTenquanan());
                    startActivity(intentToComment);
                }
                break;
        }
    }
}
