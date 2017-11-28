package nhannt.foody.screen.commentdetail;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import nhannt.foody.R;
import nhannt.foody.data.model.Comment;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.placedetail.ImageCommentRecyclerViewAdapter;
import nhannt.foody.widgets.CircleImageView;

public class CommentDetailActivity extends BaseActivity {
    private CircleImageView mImgUser;
    private TextView mTvCommentTitle, mTvCommentContent, mTvRatePoint;
    private RecyclerView mRecyclerViewImage;
    private Comment mComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_recyclerview_comment);
        initViews();
        mComment = getIntent().getExtras().getParcelable("comment");
        loadData();
    }

    private void loadData() {
        mTvCommentTitle.setText(mComment.getTieude());
        mTvCommentContent.setText(mComment.getNoidung());
        mTvRatePoint.setText(mComment.getChamdiem() + "");
        StorageReference storageImgUser = FirebaseStorage.getInstance().getReference().child
            ("thanhvien").child(mComment.getUser().getHinhanh());
        storageImgUser.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getApplicationContext()).load(uri.toString()).into(mImgUser);
            }
        });
        if (mComment.getListImage().size() > 0) {
            ImageCommentRecyclerViewAdapter imageAdapter = new ImageCommentRecyclerViewAdapter
                (this, mComment.getListImage(), mComment, true);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
            mRecyclerViewImage.setLayoutManager(layoutManager);
            mRecyclerViewImage.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
        }
    }

    private void initViews() {
        mImgUser = findViewById(R.id.img_user_comment);
        mTvCommentTitle = findViewById(R.id.tv_title_comment);
        mTvCommentContent = findViewById(R.id.tv_content_comment);
        mTvRatePoint = findViewById(R.id.tv_comment_rate_item);
        mRecyclerViewImage = findViewById(R.id.rv_image_comment);
    }
}
