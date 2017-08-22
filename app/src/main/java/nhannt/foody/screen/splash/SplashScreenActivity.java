package nhannt.foody.screen.splash;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import nhannt.foody.screen.BaseActivity;
import nhannt.foody.utils.Navigator;
import nhannt.foody.R;
import nhannt.foody.screen.login.LoginActivity;

public class SplashScreenActivity extends BaseActivity   {
    private static final long DELAY_TIME = 2000;
    private TextView mTvVersion;
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        mNavigator = new Navigator(SplashScreenActivity.this);
        setupViews();
        navigateToLogin();
    }

    private void navigateToLogin() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mNavigator.startActivity(LoginActivity.class);
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
}
