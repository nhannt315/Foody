package nhannt.foody.screen.wifi;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.PlaceWifi;

/**
 * Created by nhannt on 28/11/2017.
 */
public class WifiListAdapter extends RecyclerView.Adapter<WifiListAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<PlaceWifi> mWifiList;
    private LayoutInflater mLayoutInflater;

    public WifiListAdapter(Context context,
                           ArrayList<PlaceWifi> wifiList) {
        mContext = context;
        mWifiList = wifiList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.layout_wifi_place_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlaceWifi wifi = mWifiList.get(position);
        holder.tvWifiName.setText(wifi.getTen());
        holder.tvWifiPassword.setText(wifi.getMatkhau());
        holder.tvWifiDate.setText(wifi.getNgaydang());
    }

    @Override
    public int getItemCount() {
        return mWifiList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvWifiName,tvWifiPassword, tvWifiDate;
        public ViewHolder(View itemView) {
            super(itemView);
            tvWifiDate = itemView.findViewById(R.id.tv_wifi_date);
            tvWifiName = itemView.findViewById(R.id.tv_wifi_name);
            tvWifiPassword = itemView.findViewById(R.id.tv_password_wifi);
        }
    }
}
