package nhannt.foody.screen.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import nhannt.foody.FoodyApplication;
import nhannt.foody.R;
import nhannt.foody.data.source.UserRepository;

/**
 * Created by nhannt on 21/08/2017.
 */
public class LoginPresenter implements LoginContract.Presenter,
    GoogleApiClient.OnConnectionFailedListener, FirebaseAuth.AuthStateListener {
    private static final int GOOGLE_LOGIN_REQUEST = 1234;
    private static final int FACEBOOK_LOGIN_REQUEST = 1235;
    private UserRepository mUserRepository;
    private GoogleSignInOptions mSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private LoginContract.View mView;

    public LoginPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
    }

    @Override
    public void setView(LoginContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
        mUserRepository.logout();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
        mUserRepository.registerAuthListener(this);
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
        mUserRepository.unregisterAuthListener(this);
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void createGoogleClient(Context context) {
        mSignInOptions = new GoogleSignInOptions.Builder()
            .requestIdToken(
                FoodyApplication.getAppContext().getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
            .enableAutoManage((AppCompatActivity) context, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions)
            .build();
    }

    @Override
    public void loginGoogle() {
        Intent iGoogleLogin = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mView.sendLoginIntent(GOOGLE_LOGIN_REQUEST, iGoogleLogin);
    }

    @Override
    public void loginEmail() {
    }

    @Override
    public void loginFacebook() {
    }

    @Override
    public void handleResult(int requestCode, Intent data) {
        switch (requestCode) {
            case GOOGLE_LOGIN_REQUEST:
                GoogleSignInResult signInResult = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
                mUserRepository.loginGoogle(signInAccount.getIdToken());
                break;
            case FACEBOOK_LOGIN_REQUEST:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mView.onConnectionFailed();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            mView.onLoginSuccess();
        }
    }
}
