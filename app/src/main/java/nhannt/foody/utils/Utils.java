package nhannt.foody.utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

import nhannt.foody.R;


/**
 * Created by nhannt on 22/08/2017.
 */
public class Utils {
    public static void showErrorDialog(Context context, String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.agree)
                .show();
    }

    public static void showSuccessDialog(Context context, String title, String message) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(message)
                .positiveText(R.string.agree)
                .show();
    }
}
