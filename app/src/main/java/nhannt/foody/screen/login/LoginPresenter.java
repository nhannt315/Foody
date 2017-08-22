package nhannt.foody.screen.login;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

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
    private LoginContract.View mView;
    private UserRepository mUserRepository;
    private GoogleSignInOptions mSignInOptions;
    private GoogleApiClient mGoogleApiClient;
    private LoginManager mFbLoginManager;
    private CallbackManager mFbCallbackManager;
    private List<String> mPermissionFb = Arrays.asList("email", "public_profile");

    public LoginPresenter(UserRepository userRepository) {
        mUserRepository = userRepository;
        mFbLoginManager = LoginManager.getInstance();
        mFbCallbackManager = CallbackManager.Factory.create();
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
    public void loginFacebook(Context context) {
        mFbLoginManager.logInWithReadPermissions((AppCompatActivity) context, mPermissionFb);
        mFbLoginManager.registerCallback(mFbCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Facebook Login", "Success");
                String tokenId = loginResult.getAccessToken().getToken();
                mUserRepository.loginFacebook(tokenId);
            }

            @Override
            public void onCancel() {
                Log.d("Facebook Login", "Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d("Facebook Login", "Error");
            }
        });
    }

    @Override
    public void handleResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case GOOGLE_LOGIN_REQUEST:
                GoogleSignInResult signInResult = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);
                GoogleSignInAccount signInAccount = signInResult.getSignInAccount();
                mUserRepository.loginGoogle(signInAccount.getIdToken());
                break;
            default:
                mFbCallbackManager.onActivityResult(requestCode, resultCode, data);
                break;
        }
        mView.showProgressBar();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        mView.onConnectionFailed();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            mView.hideProgressBar();
            mView.onLoginSuccess();
        }
    }
}
