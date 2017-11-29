package nhannt.foody.data.source;

import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.source.remote.CommentRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class CommentRepository implements CommentDataSource.Remote {
    CommentRemoteDataSource mCommentRemoteDataSource;

    public CommentRepository() {
        mCommentRemoteDataSource = new CommentRemoteDataSource();
    }

    @Override
    public void addComment(String placeCode, Comment comment, ArrayList<String> lstImage,
                           OnCompleteListener<String> listener) {
        mCommentRemoteDataSource.addComment(placeCode, comment, lstImage, listener);
    }

    @Override
    public void getCommentListOfPlace(String placeCode,
                                      OnCompleteListener<ArrayList<Comment>> callback) {
        mCommentRemoteDataSource.getCommentListOfPlace(placeCode, callback);
    }
}
