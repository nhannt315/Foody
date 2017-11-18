package nhannt.foody.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nhannt on 21/08/2017.
 */
public class UserRepository
    implements UserDataSource.LocalDataSource, UserDataSource.RemoteDataSource {
    private UserDataSource.LocalDataSource mLocalDataSource;
    private UserDataSource.RemoteDataSource mRemoteDataSource;

    public UserRepository(UserDataSource.LocalDataSource localDataSource,
                          UserDataSource.RemoteDataSource remoteDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Override
    public void registerAuthListener(FirebaseAuth.AuthStateListener listener) {
        mRemoteDataSource.registerAuthListener(listener);
    }

    @Override
    public void unregisterAuthListener(FirebaseAuth.AuthStateListener listener) {
        mRemoteDataSource.unregisterAuthListener(listener);
    }

    @Override
    public void loginEmail(String email, String password, OnCompleteListener listener) {
        mRemoteDataSource.loginEmail(email, password, listener);
    }

    @Override
    public void loginGoogle(String tokenId) {
        mRemoteDataSource.loginGoogle(tokenId);
    }

    @Override
    public void loginFacebook(String tokenId) {
        mRemoteDataSource.loginFacebook(tokenId);
    }

    @Override
    public void sendPasswordResetEmail(String email, OnCompleteListener listener) {
        mRemoteDataSource.sendPasswordResetEmail(email, listener);
    }

    @Override
    public void updateUserInfo(String UID, String name, String imagePath) {
        mRemoteDataSource.updateUserInfo(UID, name, imagePath);
    }

    @Override
    public void registerUser(String email, String password, OnCompleteListener listener) {
        mRemoteDataSource.registerUser(email, password, listener);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mRemoteDataSource.getCurrentUser();
    }

    @Override
    public boolean checkLogin() {
        return mRemoteDataSource.checkLogin();
    }

    @Override
    public void logout() {
        mRemoteDataSource.logout();
    }
}
