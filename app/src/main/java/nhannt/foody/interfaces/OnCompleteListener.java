package nhannt.foody.interfaces;

import android.support.annotation.Nullable;

/**
 * Created by nhannt on 28/11/2017.
 */
public interface OnCompleteListener<T> {
    void onComplete(@Nullable T result);
    void onError();
}
