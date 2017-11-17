package nhannt.foody.screen.eat_where;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import nhannt.foody.R;
import nhannt.foody.data.model.Branch;
import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.Place;
import nhannt.foody.widgets.CircleImageView;

/**
 * Created by nhannt on 09/11/2017.
 */
public class PlaceRecyclerViewAdapter
    extends RecyclerView.Adapter<PlaceRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Place> mData;

    public PlaceRecyclerViewAdapter(Context context, List<Place> data) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recyclerview_where_eat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Place place = mData.get(position);
        holder.mTvPlaceName.setText(place.getTenquanan());
        if (place.isGiaohang()) {
            holder.mBtnOrder.setVisibility(View.VISIBLE);
        } else {
            holder.mBtnOrder.setVisibility(View.GONE);
        }
        holder.mTvTotalComment.setText(place.getBinhluanList().size() + "");
        if (place.getHinhanhquanan().size() > 0) {
            StorageReference storageImage = FirebaseStorage.getInstance()
                .getReference().child("hinhanh").child(place.getHinhanhquanan().get(0));
            storageImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Glide.with(mContext)
                        .load(uri.toString())
                        .apply(new RequestOptions().placeholder(R.drawable.bg_login))
                        .into(holder.mImgPlace);
                }
            });
        }
        if (place.getBinhluanList().size() > 0) {
            Comment comment = place.getBinhluanList().get(0);
            holder.mLlComment1.setVisibility(View.VISIBLE);
            holder.mTvTitleComment1.setText(comment.getTieude());
            holder.mTvContentComment1.setText(comment.getNoidung());
            holder.mTvRateComment1.setText(comment.getChamdiem() + "");
            loadImageUser(holder.mImgUser1, comment);
            if (place.getBinhluanList().size() > 1) {
                holder.mLlComment2.setVisibility(View.VISIBLE);
                comment = place.getBinhluanList().get(1);
                holder.mTvContentComment2.setText(comment.getNoidung());
                holder.mTvTitleComment2.setText(comment.getTieude());
                holder.mTvRateComment2.setText(comment.getChamdiem() + "");
                loadImageUser(holder.mImgUser2, comment);
            }
            int totalImageComment = 0;
            float avgRate = 0;
            for (Comment comment1 : place.getBinhluanList()) {
                totalImageComment += comment1.getListImage().size();
                avgRate += comment1.getChamdiem();
            }
            avgRate = avgRate / place.getBinhluanList().size();
            holder.mTvAvgPoint.setText(String.format("%.1f", avgRate));
            holder.mTvTotalImage.setText(totalImageComment + "");
        } else {
        }
        if (place.getLstBranch().size() > 0) {
            Branch closetBranch = null;
            double distance = -1;
            for (Branch branch : place.getLstBranch()) {
                if (distance < branch.getDistanceToCurrent()) {
                    distance = branch.getDistanceToCurrent();
                    closetBranch = branch;
                }
            }
            holder.mTvAddress.setText(closetBranch.getDiachi());
            holder.mDistance.setText(String.format("%.1f km", distance));
        }
    }

    private void loadImageUser(final CircleImageView circleImageView, Comment comment) {
        StorageReference storageImgUser = FirebaseStorage.getInstance().getReference().child
            ("thanhvien").child(comment.getUser().getHinhanh());
        storageImgUser.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext)
                    .load(uri.toString())
                    .apply(new RequestOptions().placeholder(R.drawable.bg_login))
                    .into(circleImageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTvPlaceName, mTvAddress, mTvTitleComment1, mTvTitleComment2, mTvContentComment1,
            mTvContentComment2, mTvRateComment1, mTvRateComment2, mTvTotalComment, mTvTotalImage,
            mTvAvgPoint, mDistance;
        Button mBtnOrder;
        ImageView mImgPlace;
        CircleImageView mImgUser1, mImgUser2;
        LinearLayout mLlComment1, mLlComment2;

        public ViewHolder(View itemView) {
            super(itemView);
            mImgPlace = itemView.findViewById(R.id.img_place_image_item);
            mTvPlaceName = itemView.findViewById(R.id.tv_place_name_item);
            mTvAddress = itemView.findViewById(R.id.tv_place_address_item);
            mBtnOrder = itemView.findViewById(R.id.btn_order_where_eat);
            mTvTitleComment1 = itemView.findViewById(R.id.tv_title_comment_1);
            mTvTitleComment2 = itemView.findViewById(R.id.tv_title_comment_2);
            mTvContentComment1 = itemView.findViewById(R.id.tv_content_comment_1);
            mTvContentComment2 = itemView.findViewById(R.id.tv_content_comment_2);
            mImgUser1 = itemView.findViewById(R.id.img_user_comment_1);
            mImgUser2 = itemView.findViewById(R.id.img_user_comment_2);
            mLlComment1 = itemView.findViewById(R.id.ll_comment_1);
            mLlComment2 = itemView.findViewById(R.id.ll_comment_2);
            mTvRateComment1 = itemView.findViewById(R.id.tv_comment_rate_item_1);
            mTvRateComment2 = itemView.findViewById(R.id.tv_comment_rate_item_2);
            mTvTotalComment = itemView.findViewById(R.id.tv_total_comment);
            mTvTotalImage = itemView.findViewById(R.id.tv_total_image);
            mTvAvgPoint = itemView.findViewById(R.id.tv_avg_point_place);
            mDistance = itemView.findViewById(R.id.tv_distance_place_item);
        }
    }
}
