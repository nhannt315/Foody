package nhannt.foody.screen.resetpassword;

import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 23/08/2017.
 */
public interface ResetPasswordContract {
    /*
    View
     */
    interface View extends BaseView{
        void showProgressBar();
        void hideProgressBar();
        void onResetError(String message);
        void onResetSuccess(String message);
    }

    /*
    Presenter
     */
    interface Presenter extends BasePresenter<View>{
        void sendResetPasswordEmail(String email);
    }
}
