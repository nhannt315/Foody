package nhannt.foody.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import nhannt.foody.data.source.UserDataSource;

/**
 * Created by nhannt on 21/08/2017.
 */
public class UserRemoteDataSource implements UserDataSource.RemoteDataSource {

    private FirebaseAuth mAuth;

    public UserRemoteDataSource(){
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void registerAuthListener(FirebaseAuth.AuthStateListener listener) {
        mAuth.addAuthStateListener(listener);
    }

    @Override
    public void unregisterAuthListener(FirebaseAuth.AuthStateListener listener) {
        mAuth.removeAuthStateListener(listener);
    }

    @Override
    public void loginGoogle(String tokenId) {
        AuthCredential credential = GoogleAuthProvider.getCredential(tokenId, null);
        mAuth.signInWithCredential(credential);
    }

    @Override
    public void loginFacebook(String tokenId) {
        AuthCredential credential = FacebookAuthProvider.getCredential(tokenId);
        mAuth.signInWithCredential(credential);
    }

    @Override
    public FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }

    @Override
    public boolean checkLogin() {
        return mAuth.getCurrentUser() != null;
    }

    @Override
    public void logout() {
        mAuth.signOut();
    }
}
