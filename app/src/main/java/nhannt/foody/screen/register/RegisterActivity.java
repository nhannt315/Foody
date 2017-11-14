package nhannt.foody.screen.register;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import nhannt.foody.R;
import nhannt.foody.data.source.UserRepository;
import nhannt.foody.data.source.remote.UserRemoteDataSource;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.home.HomeActivity;
import nhannt.foody.utils.Navigator;
import nhannt.foody.utils.Utils;

public class RegisterActivity extends BaseActivity implements View.OnClickListener,
    RegisterContract.View {
    private Button mBtnRegister;
    private EditText mEdtEmail, mEdtPass, mEdtRePass;
    private RegisterContract.Presenter mPresenter;
    private RelativeLayout mRlLoading;
    private Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViewsAndEvents();
        initOther();
    }

    private void initOther() {
        mNavigator = new Navigator(this);
        mPresenter = new RegisterPresenter(
            new UserRepository(null, new UserRemoteDataSource())
        );
        mPresenter.setView(this);
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

    private void initViewsAndEvents() {
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mEdtEmail = (EditText) findViewById(R.id.edt_email_register);
        mEdtPass = (EditText) findViewById(R.id.edt_password_register);
        mEdtRePass = (EditText) findViewById(R.id.edt_re_password_register);
        mRlLoading = (RelativeLayout) findViewById(R.id.rl_loading_register);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                registerUser();
                break;
            default:
                break;
        }
    }

    private void registerUser() {
        String email = mEdtEmail.getText().toString();
        String password = mEdtPass.getText().toString();
        String rePass = mEdtRePass.getText().toString();
        mPresenter.register(email, password, rePass);
    }

    @Override
    public void onRegisterSuccess() {
        Toast.makeText(this, getString(R.string.register_success), Toast.LENGTH_SHORT).show();
        mNavigator.startActivity(HomeActivity.class);
    }

    @Override
    public void onRegisterError(String message) {
            Utils.showErrorDialog(this, getString(R.string.error), message);
    }

    @Override
    public void onConnectionFailed() {
    }

    @Override
    public void showProgress() {
        mRlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mRlLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }
}
