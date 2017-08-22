package nhannt.foody.data.source;

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
    public void loginGoogle(String tokenId) {
        mRemoteDataSource.loginGoogle(tokenId);
    }

    @Override
    public void loginFacebook(String tokenId) {
        mRemoteDataSource.loginFacebook(tokenId);
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
