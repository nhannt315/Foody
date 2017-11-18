package nhannt.foody.screen.placedetail;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import nhannt.foody.R;
import nhannt.foody.data.model.Place;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.utils.Utils;

public class PlaceDetailActivity extends BaseActivity implements PlaceDetailContract.View {
    private PlaceDetailContract.Presenter mPresenter;
    private TextView mTvPlaceName, mTvPlaceAddress, mTvOpenTime, mTvStatus, mTvTotalImage,
        mTvTotalCheckin, mTvTotalBookmark, mTvTotalComment, mTvPlaceNameToolbar;
    private ImageView mImgPlaceImage;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerViewComment;
    private NestedScrollView mNestedScrollView;
    private CommentRecyclerViewAdapter mCommentAdapter;
    private Place mPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_detail);
        mPresenter = new PlaceDetailPresenter();
        mPresenter.setView(this);
        initViews();
        mPlace = getIntent().getExtras().getParcelable("place");
        setToolbar();
        setViews();
        mPresenter.getPlaceImage(mPlace.getHinhanhquanan().get(0));
    }

    private void setToolbar() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setViews() {
        mTvPlaceNameToolbar.setText(mPlace.getTenquanan());
        mTvPlaceName.setText(mPlace.getTenquanan());
        mTvPlaceAddress.setText(mPlace.getLstBranch().get(0).getDiachi());
        mTvOpenTime.setText(mPlace.getGiomocua() + " - " + mPlace.getGiodongcua());
        mTvTotalImage.setText(mPlace.getHinhanhquanan().size() + "");
        mTvTotalComment.setText(mPlace.getBinhluanList().size() + "");
        mTvStatus.setText(Utils.getPlaceStatus(mPlace.getGiomocua(), mPlace.getGiodongcua()));
        // Setup recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerViewComment.setLayoutManager(layoutManager);
        mRecyclerViewComment.setNestedScrollingEnabled(false);
        mCommentAdapter = new CommentRecyclerViewAdapter(this, mPlace.getBinhluanList());
        mRecyclerViewComment.setAdapter(mCommentAdapter);
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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void setPlaceImage(String url) {
        Glide.with(this).load(url).into(mImgPlaceImage);
    }
}
