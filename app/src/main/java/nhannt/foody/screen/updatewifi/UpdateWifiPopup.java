package nhannt.foody.screen.updatewifi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import nhannt.foody.R;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.BaseView;
import nhannt.foody.utils.Constants;

/**
 * Created by nhannt on 28/11/2017.
 */
public class UpdateWifiPopup extends BaseActivity implements UpdateWifiContract.View {
    private EditText mEdtWifiName, mEdtWifiPassword;
    private Button mBtnUpdateWifi;
    private UpdateWifiContract.Presenter mPresenter;
    private String mPlaceCode = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_popup_update_wifi);
        initViews();
        initEvents();
        mPlaceCode = getIntent().getStringExtra(Constants.PLACE_WIFI_KEY);
        mPresenter = new UpdateWifiPresenter();
        mPresenter.setView(this);
    }

    private void initEvents() {
        mBtnUpdateWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String wifiName = mEdtWifiName.getText().toString().trim();
                String wifiPassword = mEdtWifiPassword.getText().toString().trim();
                if (wifiName.length() > 0 && wifiPassword.length() > 0) {
                    mPresenter.updateWifi(mPlaceCode, wifiName, wifiPassword);
                } else {
                    Toast.makeText(UpdateWifiPopup.this,
                        getResources().getString(R.string.please_fill_all_wifi),
                        Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setScreenSize() {
        int screen_width = getResources().getDisplayMetrics().widthPixels;
        int new_window_width = screen_width * 90 / 100;
        WindowManager.LayoutParams layout = getWindow().getAttributes();
        layout.width = Math.max(layout.width, new_window_width);
        getWindow().setAttributes(layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setScreenSize();
        mPresenter.onStart();
    }

    private void initViews() {
        mEdtWifiName = findViewById(R.id.edt_wifi_name);
        mEdtWifiPassword = findViewById(R.id.edt_wifi_password);
        mBtnUpdateWifi = findViewById(R.id.btn_update_wifi);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void updateWifiDone() {
        Toast
            .makeText(this, getResources().getString(R.string.add_successfully), Toast.LENGTH_SHORT)
            .show();
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
