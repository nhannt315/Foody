package nhannt.foody;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;

/**
 * Created by nhannt on 21/08/2017.
 */
public class FoodyApplication extends Application {

    private static Context mContext;
    private static FoodyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        FoodyApplication.mContext = getApplicationContext();
        mInstance = this;
    }

    public static FoodyApplication getInstance(){
        return mInstance;
    }

    public static Context getAppContext(){
        return mContext;
    }

    public boolean isNetworkOnline() {
        boolean status=false;
        try{
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.getState()==NetworkInfo.State.CONNECTED) {
                status= true;
            }else {
                netInfo = cm.getActiveNetworkInfo();
                if(netInfo!=null && netInfo.getState()==NetworkInfo.State.CONNECTED)
                    status= true;
            }
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        return status;

    }

    public static File getCacheDirectory(){
        return mInstance.getApplicationContext().getCacheDir();
    }
}
