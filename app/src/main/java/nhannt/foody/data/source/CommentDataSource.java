package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public interface CommentDataSource {
    interface Remote {
        void addComment(String placeCode, Comment comment,
                        ArrayList<String> lstImage, OnCompleteListener<String> listener);
        void getCommentListOfPlace(String placeCode,
                                   OnCompleteListener<ArrayList<Comment>> callback);
    }
}
