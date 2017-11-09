package nhannt.foody.screen.resetpassword;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import nhannt.foody.FoodyApplication;
import nhannt.foody.R;
import nhannt.foody.data.source.UserRepository;
import nhannt.foody.utils.Validators;

/**
 * Created by nhannt on 23/08/2017.
 */
public class ResetPasswordPresenter implements ResetPasswordContract.Presenter {
    private ResetPasswordContract.View mView;
    private UserRepository mUserRepository;

    public ResetPasswordPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(ResetPasswordContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void sendResetPasswordEmail(String email) {
        if (email.isEmpty()) {
            mView.onResetError(
                FoodyApplication.getAppContext().getString(R.string.please_fill_all)
            );
            return;
        }
        if(!Validators.validateEmail(email)){
            mView.onResetError(
                FoodyApplication.getAppContext().getString(R.string.please_type_right_email)
            );
            return;
        }
        mView.showProgressBar();
        mUserRepository.sendPasswordResetEmail(email, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                 mView.hideProgressBar();
                if (task.isSuccessful()){
                    mView.onResetSuccess(
                        FoodyApplication.getAppContext().getString(R.string.email_sended)
                    );
                }else{
                    mView.onResetError(
                        FoodyApplication.getAppContext().getString(R.string.email_not_found)
                    );
                }

            }
        });
    }
}
