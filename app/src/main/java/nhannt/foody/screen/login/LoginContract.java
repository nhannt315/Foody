package nhannt.foody.screen.login;

import android.content.Context;
import android.content.Intent;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 21/08/2017.
 */
public class LoginContract {
    /*
    View
     */
    interface View extends BaseView {
        void sendLoginIntent(int requestCode, Intent iGoogleLogin);
        void onLoginError();
        void onLoginSuccess();
        void showProgressBar();
        void hideProgressBar();
        void onConnectionFailed();
    }

    /*
    Presenter
     */
    interface Presenter extends BasePresenter<View> {
        void createGoogleClient(Context context);
        void loginGoogle();
        void loginEmail();
        void loginFacebook();
        void handleResult(int requestCode, Intent data);
    }
}
