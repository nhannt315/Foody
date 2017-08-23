package nhannt.foody.screen.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import nhannt.foody.utils.Utils;

public class LoginActivity extends BaseActivity
    implements View.OnClickListener, LoginContract.View {
    private Navigator mNavigator;
    private LoginContract.Presenter mPresenter;
    private Button mBtnLoginGoogle;
    private Button mBtnLoginFacebook;
    private Button mBtnLogin;
    private TextView mTvRegister;
    private TextView mTvForgotPass;
    private EditText mEdtEmail;
    private EditText mEdtPassword;
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
        mBtnLogin.setOnClickListener(this);
    }

    private void initViews() {
        mBtnLoginGoogle = (Button) findViewById(R.id.btn_login_google);
        mBtnLoginFacebook = (Button) findViewById(R.id.btn_login_facebook);
        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mTvForgotPass = (TextView) findViewById(R.id.tv_forgot_pass);
        mEdtEmail = (EditText) findViewById(R.id.edt_email_login);
        mEdtPassword = (EditText) findViewById(R.id.edt_password_login);
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
            case R.id.btn_login:
                String email = mEdtEmail.getText().toString().trim();
                String password = mEdtPassword.getText().toString().trim();
                mPresenter.loginEmail(email, password);
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
    public void onLoginError(String message) {
        Utils.showErrorDialog(this, getString(R.string.error), message);
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
