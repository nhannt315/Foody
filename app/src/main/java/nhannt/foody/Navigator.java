package nhannt.foody;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;

/**
 * Created by nhannt on 20/08/2017.
 */
public class Navigator {
    public static final int NONE = 0;
    public static final int RIGHT_LEFT = 1;
    public static final int BOTTOM_UP = 2;
    public static final int FADED = 3;
    public static final int LEFT_RIGHT = 4;
    @NonNull
    private Activity mActivity;
    private Fragment mFragment;

    public Navigator(@NonNull Activity activity) {
        mActivity = activity;
    }

    public Navigator(Fragment fragment) {
        mFragment = fragment;
        mActivity = fragment.getActivity();
    }

    public Context getContext() {
        return mActivity != null ? mActivity : mFragment != null ? mFragment.getContext() : null;
    }

    public void startActivity(@NonNull Intent intent) {
        mActivity.startActivity(intent);
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz) {
        mActivity.startActivity(new Intent(mActivity, clazz));
    }

    public void finishActivity() {
        if (mActivity != null) {
            mActivity.finish();
        }
        if (mFragment != null) {
            mFragment.getActivity().finish();
        }
    }

    public void startActivity(@NonNull Class<? extends Activity> clazz, Bundle args) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivity(intent);
    }

    public void startActivityAtRoot(@NonNull Class<? extends Activity> clazz) {
        Intent intent = new Intent(mActivity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode) {
        mActivity.startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Class<? extends Activity> clazz, Bundle args,
                                       int requestCode) {
        Intent intent = new Intent(mActivity, clazz);
        intent.putExtras(args);
        startActivityForResult(intent, requestCode);
    }

    public void finishActivityWithResult(Intent intent, int resultCode) {
        mActivity.setResult(resultCode, intent);
        mActivity.finish();
    }

    public void openUrl(String url) {
        if (TextUtils.isEmpty(url) || !Patterns.WEB_URL.matcher(url).matches()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse(url));
        mActivity.startActivity(intent);
    }

    @IntDef({RIGHT_LEFT, BOTTOM_UP, FADED, NONE, LEFT_RIGHT})
    public @interface NavigateAnim {
    }

    @IntDef({ActivityTransition.START, ActivityTransition.FINISH})
    @interface ActivityTransition {
        int START = 0x00;
        int FINISH = 0x01;
    }

    public void showToast(@StringRes int stringId) {
        Activity activity =
            mActivity != null ? mActivity : mFragment != null ? mFragment.getActivity() : null;
        if (activity == null) return;
        Snackbar.make(activity.findViewById(android.R.id.content), stringId, Snackbar.LENGTH_LONG)
            .show();
    }

    public void showToast(String message) {
        Activity activity =
            mActivity != null ? mActivity : mFragment != null ? mFragment.getActivity() : null;
        if (activity == null) return;
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
            .show();
    }

    public String getStringById(int stringId) {
        return mActivity.getResources().getString(stringId);
    }
}
