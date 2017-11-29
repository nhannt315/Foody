package nhannt.foody.screen.addcomment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.selectimage.SelectImageActivity;
import nhannt.foody.utils.Constants;

/**
 * Created by nhannt on 29/11/2017.
 */
public class AddCommentActivity extends BaseActivity implements View.OnClickListener {
    private static final int SELECT_IMAGE_REQUEST_CODE = 123;
    private Toolbar mToolbar;
    private TextView mTvPlaceName, mTvPlaceAddress, mTvPostComment;
    private RecyclerView mRvSelectedImage;
    private EditText mEdtCommentSubject, mEdtCommentContent;
    private ImageButton mBtnSelectImage;
    private ArrayList<String> mSelectedImageList = new ArrayList<>();
    private String mPlaceName, mPlaceCode, mPlaceAddress;
    private ImageSelectedRvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_comment);
        initToolbar();
        initViews();
        initEvents();
        getDataFromIntent();
    }

    private void initEvents() {
        mBtnSelectImage.setOnClickListener(this);
    }

    private void initToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initViews() {
        mTvPlaceName = findViewById(R.id.tv_place_name);
        mTvPlaceAddress = findViewById(R.id.tv_place_address);
        mTvPostComment = findViewById(R.id.tv_post_comment);
        mEdtCommentSubject = findViewById(R.id.edt_comment_subject);
        mEdtCommentContent = findViewById(R.id.edt_comment_content);
        mBtnSelectImage = findViewById(R.id.img_btn_add_image);
        mRvSelectedImage = findViewById(R.id.rv_selected_image);
        // Recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager
            .HORIZONTAL, false);
        mRvSelectedImage.setLayoutManager(layoutManager);
        mAdapter = new ImageSelectedRvAdapter(this, mSelectedImageList);
        mRvSelectedImage.setAdapter(mAdapter);

    }

    private void getDataFromIntent() {
        mPlaceName = getIntent().getStringExtra(Constants.PLACE_NAME_KEY);
        mPlaceCode = getIntent().getStringExtra(Constants.PLACE_CODE_KEY);
        mPlaceAddress = getIntent().getStringExtra(Constants.PLACE_ADDRESS_KEY);
        mTvPlaceName.setText(mPlaceName);
        mTvPlaceAddress.setText(mPlaceAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            mSelectedImageList = data.getStringArrayListExtra(Constants.IMAGE_LIST_KEY);
            mAdapter.refresh(mSelectedImageList);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_btn_add_image:
                Intent intentSelectImage = new Intent(this, SelectImageActivity.class);
                startActivityForResult(intentSelectImage, SELECT_IMAGE_REQUEST_CODE);
                break;
        }
    }
}
