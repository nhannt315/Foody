package nhannt.foody.screen;

/**
 * Created by nhannt on 21/08/2017.
 */
public interface BasePresenter<V> {

    void setView(V view);

    void onStart();

    void onStop();

    void detachView();
}
