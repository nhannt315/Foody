package nhannt.foody.screen.resetpassword;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import nhannt.foody.R;
import nhannt.foody.data.source.UserRepository;
import nhannt.foody.data.source.remote.UserRemoteDataSource;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.screen.register.RegisterActivity;
import nhannt.foody.utils.Navigator;
import nhannt.foody.utils.Utils;

public class ResetPasswordActivity extends BaseActivity
    implements ResetPasswordContract.View, View.OnClickListener {
    private ResetPasswordContract.Presenter mPresenter;
    private Navigator mNavigator;
    private TextView mTvRegister;
    private EditText mEdtEmail;
    private Button mBtnSendEmail;
    private RelativeLayout mRlLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        mNavigator = new Navigator(this);
        mPresenter = new ResetPasswordPresenter(
            new UserRepository(null, new UserRemoteDataSource())
        );
        mPresenter.setView(this);
        initViewsAndEvents();
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
        mTvRegister = (TextView) findViewById(R.id.tv_register);
        mEdtEmail = (EditText) findViewById(R.id.edt_email_reset);
        mBtnSendEmail = (Button) findViewById(R.id.btn_send_email);
        mRlLoading = (RelativeLayout) findViewById(R.id.rl_loading);
        mBtnSendEmail.setOnClickListener(this);
        mTvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                mNavigator.startActivity(RegisterActivity.class);
                break;
            case R.id.btn_send_email:
                String email = mEdtEmail.getText().toString().trim();
                mPresenter.sendResetPasswordEmail(email);
                break;
        }
    }

    @Override
    public void showProgressBar() {
        mRlLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mRlLoading.setVisibility(View.GONE);
    }

    @Override
    public void onResetError(String message) {
        Utils.showErrorDialog(this, getString(R.string.error), message);
    }

    @Override
    public void onResetSuccess(String message) {
        Utils.showSuccessDialog(this, getString(R.string.success), message);
    }
}
