package nhannt.foody.screen.addcomment;

import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.screen.BasePresenter;
import nhannt.foody.screen.BaseView;

/**
 * Created by nhannt on 29/11/2017.
 */
public interface AddCommentContract {
    interface View extends BaseView{
        void showProgress();
        void hideProgress();
        void addCommentSuccess();
        void addCommentError();
    }
    interface Presenter extends BasePresenter<View>{
        void addComment(String placeCode,Comment comment, ArrayList<String> lstImage);
    }
}
