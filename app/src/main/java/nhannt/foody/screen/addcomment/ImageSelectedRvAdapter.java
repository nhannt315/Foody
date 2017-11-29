package nhannt.foody.screen.addcomment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nhannt.foody.R;

/**
 * Created by nhannt on 29/11/2017.
 */
public class ImageSelectedRvAdapter extends RecyclerView.Adapter<ImageSelectedRvAdapter
    .ViewHolder> {

    private Context mContext;
    private ArrayList<String> mSelectedImage;
    private LayoutInflater mLayoutInflater;

    public ImageSelectedRvAdapter(Context context, ArrayList<String> seletedImage) {
        mContext = context;
        mSelectedImage = seletedImage;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_selected_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;
        Glide.with(mContext).load(mSelectedImage.get(position)).into(holder.mImgSelectedImage);
    }

    public void refresh(ArrayList<String> newList){
        mSelectedImage.clear();
        mSelectedImage.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mSelectedImage.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int position;
        ImageView mImgSelectedImage, mImgDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            mImgSelectedImage = itemView.findViewById(R.id.img_selected_image);
            mImgDelete = itemView.findViewById(R.id.img_delete_image);

            mImgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedImage.remove(position);
                    notifyDataSetChanged();
                }
            });
        }
    }
}
