package nhannt.foody.utils;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by nhannt on 22/08/2017.
 */
public class Utils {
    public static void showErrorDialog(Context context,String title, String message){
        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .show();
    }
}
