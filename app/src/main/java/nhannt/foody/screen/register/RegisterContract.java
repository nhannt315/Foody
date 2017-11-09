package nhannt.foody.screen.register;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 22/08/2017.
 */
public interface RegisterContract {
    /*
    View
     */
    interface View extends BaseView {
        void onRegisterSuccess();
        void onRegisterError(String message);
        void onConnectionFailed();
        void showProgress();
        void hideProgress();
    }

    /*
    Presenter
     */

    interface Presenter extends BasePresenter<View> {
        void register(String email, String password1, String password2);
    }
}
