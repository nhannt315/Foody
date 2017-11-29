package nhannt.foody.screen.selectimage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.utils.Constants;
import nhannt.foody.widgets.ItemOffsetDecoration;

public class SelectImageActivity extends BaseActivity
    implements SelectImageContract.View, View.OnClickListener {
    private static final int REQUEST_CODE = 1234;
    private RecyclerView mRvImage;
    private TextView mTvDoneSelect;
    private ImageView mImgAddImage;
    private SelectImageContract.Presenter mPresenter;
    private ImageSelectRecyclerViewAdapter mAdapter;
    private ArrayList<ImageSelect> mLstImage = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        mPresenter = new SelectImagePresenter();
        mPresenter.setView(this);
        initViews();
        if (!checkReadStoragePermission()) {
            requirePermission();
        } else {
            mPresenter.getAllImage();
        }
    }

    private void initViews() {
        mTvDoneSelect = findViewById(R.id.tv_done_select_image);
        mImgAddImage = findViewById(R.id.add_image_from_camera);
        mRvImage = findViewById(R.id.rv_select_image);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRvImage.setLayoutManager(layoutManager);
        mRvImage.addItemDecoration(new ItemOffsetDecoration(this, R.dimen.item_image_spacing));
        mTvDoneSelect.setOnClickListener(this);
        mImgAddImage.setOnClickListener(this);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.getAllImage();
            }
        }
    }

    @Override
    public boolean checkReadStoragePermission() {
        int checkReadExStorage =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return checkReadExStorage == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void requirePermission() {
        ActivityCompat
            .requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                REQUEST_CODE);
    }

    @Override
    public void setImage(ArrayList<ImageSelect> listImage) {
        mLstImage = listImage;
        mAdapter = new ImageSelectRecyclerViewAdapter(this, listImage);
        mRvImage.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_done_select_image:
                ArrayList<String> lstSelectedImage = new ArrayList<>();
                for (ImageSelect imageSelect : mLstImage) {
                    if (imageSelect.isChecked())
                        lstSelectedImage.add(imageSelect.getPath());
                }
                Intent data = getIntent();
                data.putStringArrayListExtra(Constants.IMAGE_LIST_KEY, lstSelectedImage);
                setResult(RESULT_OK, data);
                onBackPressed();
                break;
            case R.id.add_image_from_camera:
                break;
        }
    }
}
