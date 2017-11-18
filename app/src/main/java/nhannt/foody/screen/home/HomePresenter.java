package nhannt.foody.screen.home;

/**
 * Created by nhannt on 09/11/2017.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;

    @Override
    public void setView(HomeContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void detachView() {
        this.mView = null;
    }
}
