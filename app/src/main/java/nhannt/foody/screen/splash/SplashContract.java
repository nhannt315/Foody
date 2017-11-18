package nhannt.foody.screen.splash;

import android.content.Context;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 17/11/2017.
 */
public interface SplashContract {
    interface View extends BaseView {
        void requireLocationPermission();
        boolean checkLocationPermission();
        void navigateToLogin();
    }

    interface Presenter extends BasePresenter<View> {
        void initGoogleApiClient(Context context);
    }
}
