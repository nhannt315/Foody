package nhannt.foody.screen.placedetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.Comment;
import nhannt.foody.screen.commentdetail.CommentDetailActivity;

/**
 * Created by nhannt on 18/11/2017.
 */
public class ImageCommentRecyclerViewAdapter extends RecyclerView
    .Adapter<ImageCommentRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mImageList;
    private Comment mComment;
    private boolean mIsDetail;
    private LayoutInflater mLayoutInflater;

    public ImageCommentRecyclerViewAdapter(Context context, ArrayList<String> imageList,
                                           Comment comment, boolean isDetail) {
        mContext = context;
        mImageList = imageList;
        mComment = comment;
        mIsDetail = isDetail;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_image_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        StorageReference imgStorage = FirebaseStorage.getInstance().getReference().child
            ("hinhanh").child(mImageList.get(position));
        imgStorage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri.toString()).into(holder.mImage);
            }
        });
        if (mIsDetail)
            return;
        if (position == 3) {
            int restImage = mImageList.size() - 4;
            if (restImage > 0) {
                holder.mFrameLayout.setVisibility(View.VISIBLE);
                holder.mTvTotalImage.setText("+" + restImage);
                holder.mImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iCommentDetail = new Intent(mContext, CommentDetailActivity.class);
                        iCommentDetail.putExtra("comment", mComment);
                        mContext.startActivity(iCommentDetail);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(!mIsDetail){
            if(mImageList.size() < 4){
                return mImageList.size();
            }else{
                return 4;
            }

        }else{
            return mImageList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mImage;
        TextView mTvTotalImage;
        FrameLayout mFrameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.img_comment);
            mTvTotalImage = itemView.findViewById(R.id.tv_more_image_comment_count);
            mFrameLayout = itemView.findViewById(R.id.frame_more_image);
        }
    }
}
