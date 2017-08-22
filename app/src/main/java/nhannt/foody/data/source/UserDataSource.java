package nhannt.foody.data.source;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by nhannt on 21/08/2017.
 */
public interface UserDataSource {

    interface LocalDataSource{

    }

    interface RemoteDataSource{
        void registerAuthListener(FirebaseAuth.AuthStateListener listener);
        void unregisterAuthListener(FirebaseAuth.AuthStateListener listener);
        void loginGoogle(String tokenId);
        void loginFacebook(String tokenId);
        FirebaseUser getCurrentUser();
        boolean checkLogin();
        void logout();
    }

}
