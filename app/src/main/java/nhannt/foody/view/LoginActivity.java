package nhannt.foody.view;

import android.content.Intent;
import android.hardware.camera2.params.Face;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import nhannt.foody.Navigator;
import nhannt.foody.R;

public class LoginActivity extends AppCompatActivity implements
    GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,
    FirebaseAuth.AuthStateListener {
    public static final int LOGIN_GOOGLE_REQUEST_CODE = 3333;
    private static final int GOOGLE_LOGIN = 1;
    private static final int FACEBOOK_LOGIN = 2;
    private FirebaseAuth mFirebaseAuth;
    private Navigator mNavigator;
    private Button mBtnLoginGoogle;
    private GoogleSignInOptions mSignInOptions;
    private GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mNavigator = new Navigator(this);
        mFirebaseAuth = FirebaseAuth.getInstance();
        initViews();
        initEvents();
        initGoogleClient();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFirebaseAuth.removeAuthStateListener(this);
    }

    private void initEvents() {
        mBtnLoginGoogle.setOnClickListener(this);
    }

    private void initViews() {
        mBtnLoginGoogle = (Button) findViewById(R.id.btn_login_google);
    }

    private void initGoogleClient() {
        mSignInOptions = new GoogleSignInOptions.Builder()
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build();
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
            .enableAutoManage(this, this)
            .addApi(Auth.GOOGLE_SIGN_IN_API, mSignInOptions)
            .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_GOOGLE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);
                GoogleSignInAccount account = signInResult.getSignInAccount();
                String tokenId = account.getIdToken();
                firebaseLoginAuth(GOOGLE_LOGIN, tokenId);
            }
        }
    }

    private void firebaseLoginAuth(int signInType, String tokenId) {
        switch (signInType) {
            case GOOGLE_LOGIN:
                AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenId, null);
                mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(
                    new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        }
                    });
                break;
            case FACEBOOK_LOGIN:
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    private void loginGoogle() {
        Intent iLoginGoogle = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        mNavigator.startActivityForResult(iLoginGoogle, LOGIN_GOOGLE_REQUEST_CODE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_google:
                loginGoogle();
                break;
        }
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            mNavigator.startActivity(HomeActivity.class);
        } else {
            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }
}
