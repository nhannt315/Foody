package nhannt.foody.data.source;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;

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
        void registerUser(String email, String password, OnCompleteListener listener);
        FirebaseUser getCurrentUser();
        boolean checkLogin();
        void logout();
    }

}
