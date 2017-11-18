package nhannt.foody;

import android.app.Application;
import android.content.Context;

/**
 * Created by nhannt on 21/08/2017.
 */
public class FoodyApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        FoodyApplication.mContext = getApplicationContext();
    }

    public static Context getAppContext(){
        return mContext;
    }
}
