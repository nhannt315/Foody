package nhannt.foody.screen.register;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import nhannt.foody.FoodyApplication;
import nhannt.foody.R;
import nhannt.foody.data.source.UserRepository;
import nhannt.foody.utils.Validators;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nhannt on 22/08/2017.
 */
public class RegisterPresenter implements RegisterContract.Presenter {
    private UserRepository mUserRepository;
    private RegisterContract.View mView;

    public RegisterPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(RegisterContract.View view) {
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
    public void register(String email, String password1, String password2) {
        if (email.trim().length() == 0
            || password1.trim().length() == 0
            || password2.trim().length() == 0) {
            mView.onRegisterError(
                FoodyApplication.getAppContext().getString(R.string.please_fill_all)
            );
            return;
        }
        if (!Validators.validateEmail(email.trim())) {
            mView.onRegisterError(
                FoodyApplication.getAppContext().getString(R.string.please_type_right_email)
            );
        }
        if (!password1.equals(password2)) {
            mView.onRegisterError(
                FoodyApplication.getAppContext().getString(R.string.two_pass_must_match)
            );
            return;
        }
        if (!Validators.validatePassword(password1)) {
            mView.onRegisterError(
                FoodyApplication.getAppContext().getString(R.string.password_error)
            );
        }
        mView.showProgress();
        mUserRepository.registerUser(email, password1, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult().getUser();
                    mUserRepository.updateUserInfo(user.getUid(), user.getEmail(), "user.png");
                    mView.hideProgress();
                    mView.onRegisterSuccess();
                }
            }
        });
    }
}
