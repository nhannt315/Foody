package nhannt.foody.screen.placedetail;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.Comment;
import nhannt.foody.widgets.CircleImageView;

/**
 * Created by nhannt on 18/11/2017.
 */
public class CommentRecyclerViewAdapter
    extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Comment> mCommentList;
    private LayoutInflater mLayoutInflater;

    public CommentRecyclerViewAdapter(Context context,
                                      ArrayList<Comment> commentList) {
        mContext = context;
        mCommentList = commentList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recyclerview_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Comment comment = mCommentList.get(position);
        holder.mTvCommentTitle.setText(comment.getTieude());
        holder.mTvCommentContent.setText(comment.getNoidung());
        holder.mTvRatePoint.setText(comment.getChamdiem() + "");
        StorageReference storageImgUser = FirebaseStorage.getInstance().getReference().child
            ("thanhvien").child(comment.getUser().getHinhanh());
        storageImgUser.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri.toString()).into(holder.mImgUser);
            }
        });
        if (comment.getListImage().size() > 0) {
            ImageCommentRecyclerViewAdapter imageAdapter = new ImageCommentRecyclerViewAdapter
                (mContext, comment.getListImage(), comment, false);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            holder.mRecyclerViewImage.setLayoutManager(layoutManager);
            holder.mRecyclerViewImage.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mCommentList.size() > 5)
            return 5;
        else
            return mCommentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView mImgUser;
        TextView mTvCommentTitle, mTvCommentContent, mTvRatePoint;
        RecyclerView mRecyclerViewImage;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgUser = itemView.findViewById(R.id.img_user_comment);
            mTvCommentTitle = itemView.findViewById(R.id.tv_title_comment);
            mTvCommentContent = itemView.findViewById(R.id.tv_content_comment);
            mTvRatePoint = itemView.findViewById(R.id.tv_comment_rate_item);
            mRecyclerViewImage = itemView.findViewById(R.id.rv_image_comment);
        }
    }
}
