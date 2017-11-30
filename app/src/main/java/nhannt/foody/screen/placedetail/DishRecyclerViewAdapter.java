package nhannt.foody.screen.placedetail;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.DishModel;
import nhannt.foody.data.model.OrderFoodModel;

/**
 * Created by nhannt on 29/11/2017.
 */
public class DishRecyclerViewAdapter
    extends RecyclerView.Adapter<DishRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<DishModel> mDishModelList;
    private LayoutInflater mLayoutInflater;
    public static ArrayList<OrderFoodModel> sOrderList;

    public DishRecyclerViewAdapter(Context context,
                                   ArrayList<DishModel> dishModelList) {
        mContext = context;
        mDishModelList = dishModelList;
        mLayoutInflater = LayoutInflater.from(mContext);
        if (sOrderList == null)
            sOrderList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_dish_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.position = position;
        DishModel dishModel = mDishModelList.get(position);
        holder.tvDishName.setText(dishModel.getTenmon());
        FirebaseStorage.getInstance().getReference().child("hinhanh").child(dishModel
            .getHinhanh()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(mContext).load(uri.toString()).into(holder.imgDish);
            }
        });
        holder.tvCurrentOrder.setText(holder.orderCount + "");
    }

    @Override
    public int getItemCount() {
        return mDishModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int position, orderCount = 0;
        TextView tvDishName, tvCurrentOrder;
        ImageView imgDish, imgIncrease, imgDecrease;
        OrderFoodModel mOrderFood = new OrderFoodModel();

        public ViewHolder(View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.tv_dish_title);
            imgDish = itemView.findViewById(R.id.img_dish_item);
            tvCurrentOrder = itemView.findViewById(R.id.tv_current_order_number);
            imgIncrease = itemView.findViewById(R.id.img_increase_order);
            imgDecrease = itemView.findViewById(R.id.img_decrease_order);
            // Events
            imgIncrease.setOnClickListener(this);
            imgDecrease.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_increase_order:
                    orderCount++;
                    break;
                case R.id.img_decrease_order:
                    if (orderCount > 0)
                        orderCount--;
                    break;
            }
            tvCurrentOrder.setText(orderCount + "");
            mOrderFood.setDishName(mDishModelList.get(position).getTenmon());
            mOrderFood.setCount(orderCount);
            if (orderCount == 0) {
                sOrderList.remove(mOrderFood);
            } else {
                if (!sOrderList.contains(mOrderFood))
                    sOrderList.add(mOrderFood);
            }
        }
    }
}
