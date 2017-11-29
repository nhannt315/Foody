package nhannt.foody.screen.selectimage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.widgets.SquareImageView;

/**
 * Created by nhannt on 29/11/2017.
 */
public class ImageSelectRecyclerViewAdapter extends RecyclerView
    .Adapter<ImageSelectRecyclerViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<ImageSelect> mImageList;
    private LayoutInflater mLayoutInflater;

    public ImageSelectRecyclerViewAdapter(Context context,
                                          ArrayList<ImageSelect> imageList) {
        mContext = context;
        mImageList = imageList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_image_select, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.position = position;
        Glide.with(mContext).load(mImageList.get(position).getPath()).into(holder.mImageView);
        holder.mCheckBox.setChecked(mImageList.get(position).isChecked());
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        int position;
        SquareImageView mImageView;
        CheckBox mCheckBox;
        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img_select_image);
            mCheckBox = itemView.findViewById(R.id.cb_select_image);

            mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mImageList.get(position).setChecked(isChecked);
                }
            });
        }
    }
}
