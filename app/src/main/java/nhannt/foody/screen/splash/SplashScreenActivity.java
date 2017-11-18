package nhannt.foody.screen.splash;

import android.Manifest;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import nhannt.foody.screen.BaseActivity;
import nhannt.foody.utils.Navigator;
import nhannt.foody.R;
import nhannt.foody.screen.login.LoginActivity;

public class SplashScreenActivity extends BaseActivity implements SplashContract.View {
    public static final int REQUEST_LOCATION_PERMISSION = 1;
    private static final long DELAY_TIME = 2000;
    private TextView mTvVersion;
    private Navigator mNavigator;
    private SplashContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mNavigator = new Navigator(SplashScreenActivity.this);
        mPresenter = new SplashPresenter();
        mPresenter.setView(this);
        mPresenter.initGoogleApiClient(this);
        requireLocationPermission();
        setupViews();
    }

    @Override
    public void requireLocationPermission() {
        if (!checkLocationPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                .ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
        }
    }

    @Override
    public boolean checkLocationPermission() {
        int checkPermissionCoarseLocation = ContextCompat.checkSelfPermission(this, Manifest
            .permission.ACCESS_COARSE_LOCATION);
        int checkPermissionFineLocation = ContextCompat.checkSelfPermission(this, Manifest
            .permission.ACCESS_FINE_LOCATION);
        return checkPermissionCoarseLocation == PackageManager.PERMISSION_GRANTED &&
            checkPermissionFineLocation == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        mPresenter.onStart();
        super.onStart();
    }

    @Override
    public void navigateToLogin() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNavigator.startActivity(LoginActivity.class);
                finish();
            }
        }, DELAY_TIME);
    }

    private void setupViews() {
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            mTvVersion.setText(getString(R.string.version_splash, packageInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        mPresenter.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
