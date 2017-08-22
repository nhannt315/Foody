package nhannt.foody.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;

import nhannt.foody.data.source.UserRepository;
import nhannt.foody.data.source.remote.UserRemoteDataSource;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.home.HomeActivity;
import nhannt.foody.screen.register.RegisterActivity;
import nhannt.foody.utils.Navigator;
import nhannt.foody.R;

public class LoginActivity extends BaseActivity
    implements View.OnClickListener, LoginContract.View {
    private Navigator mNavigator;
    private LoginContract.Presenter mPresenter;
    private Button mBtnLoginGoogle;
    private Button mBtnLoginFacebook;
    private TextView mTvRegister;
    private TextView mTvForgotPass;
    private RelativeLayout mLoadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNavigator = new Navigator(this);
        mPresenter = new LoginPresenter(new UserRepository(null, new UserRemoteDataSource()));
        mPresenter.setView(this);
        mPresenter.createGoogleClient(this);
        initViews();
        initEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    private void initEvents() {
        mBtnLoginGoogle.setOnClickListener(this);
        mBtnLoginFacebook.setOnClickListener(this);
        mTvForgotPass.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    private void initViews() {
        mBtnLoginGoogle = (Button) findViewById(R.id.btn_login_google);
        mBtnLoginFacebook = (Button) findViewById(R.id.btn_login_facebook);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvForgotPass = (TextView) findViewById(R.id.tv_forgot_pass);
        mLoadingBar = (RelativeLayout) findViewById(R.id.rl_loading_login);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            mPresenter.handleResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_google:
                mPresenter.loginGoogle();
                break;
            case R.id.btn_login_facebook:
                mPresenter.loginFacebook(this);
                break;
            case R.id.tv_forgot_pass:
                break;
            case R.id.tv_register:
                mNavigator.startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void sendLoginIntent(int requestCode, Intent iGoogleLogin) {
        mNavigator.startActivityForResult(iGoogleLogin, requestCode);
    }

    @Override
    public void onLoginError() {
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show();
        mNavigator.startActivity(HomeActivity.class);
    }

    @Override
    public void showProgressBar() {
        mLoadingBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mLoadingBar.setVisibility(View.GONE);
    }

    @Override
    public void onConnectionFailed() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
