package nhannt.foody.utils;

import android.content.Context;
import android.location.Location;

import com.afollestad.materialdialogs.MaterialDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import nhannt.foody.FoodyApplication;
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

    public static String getPlaceStatus(String openTime, String closeTime) {
        String result = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String now = dateFormat.format(calendar.getTime());
        try {
            Date dateNow = dateFormat.parse(now);
            Date dateOpen = dateFormat.parse(openTime);
            Date dateClose = dateFormat.parse(closeTime);
            if (dateNow.after(dateOpen) && dateNow.before(dateClose)) {
                result = FoodyApplication.getAppContext().getString(R.string.opening);
            } else {
                result = FoodyApplication.getAppContext().getString(R.string.closing);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
}
