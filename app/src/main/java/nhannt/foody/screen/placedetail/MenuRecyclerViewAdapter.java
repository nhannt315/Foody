package nhannt.foody.screen.placedetail;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.MenuModel;

/**
 * Created by nhannt on 29/11/2017.
 */
public class MenuRecyclerViewAdapter
    extends RecyclerView.Adapter<MenuRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<MenuModel> mMenuModelList;
    private LayoutInflater mLayoutInflater;

    public MenuRecyclerViewAdapter(Context context,
                                   ArrayList<MenuModel> menuModelList) {
        mContext = context;
        mMenuModelList = menuModelList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_menu_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MenuModel menuModel = mMenuModelList.get(position);
        holder.tvMenuTitle.setText(menuModel.getTenthucdon());
        holder.rvDishList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager
            .HORIZONTAL, false));
        DishRecyclerViewAdapter dishAdapter =
            new DishRecyclerViewAdapter(mContext, menuModel.getDishList());
        holder.rvDishList.setAdapter(dishAdapter);
    }

    @Override
    public int getItemCount() {
        return mMenuModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenuTitle;
        RecyclerView rvDishList;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMenuTitle = itemView.findViewById(R.id.tv_menu_title);
            rvDishList = itemView.findViewById(R.id.rv_dish_list_item);
        }
    }
}
