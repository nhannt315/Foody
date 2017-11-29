package nhannt.foody.screen.addcomment;

import android.support.annotation.Nullable;

import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.source.CommentRepository;
import nhannt.foody.data.source.PhotoVideoRepository;
import nhannt.foody.data.source.UserRepository;
import nhannt.foody.data.source.remote.UserRemoteDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class AddCommentPresenter implements AddCommentContract.Presenter {
    private AddCommentContract.View mView;
    private CommentRepository mCommentRepository;
    private PhotoVideoRepository mPhotoVideoRepository;
    private UserRepository mUserRepository;

    public AddCommentPresenter(){
        mCommentRepository = new CommentRepository();
        mPhotoVideoRepository = new PhotoVideoRepository();
        mUserRepository = new UserRepository(null,new UserRemoteDataSource());
    }

    @Override
    public void setView(AddCommentContract.View view) {
        mView = view;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void addComment(String placeCode, Comment comment, final ArrayList<String> lstImage) {
        if(mView == null)
            return;
        comment.setMauser(mUserRepository.getCurrentUser().getUid());
        mView.showProgress();
        mCommentRepository.addComment(placeCode, comment, lstImage,
            new OnCompleteListener<String>() {
                @Override
                public void onComplete(@Nullable String result) {
                    mPhotoVideoRepository.uploadListImage(lstImage, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@Nullable Void result) {
                            mView.hideProgress();
                        }

                        @Override
                        public void onError() {
                        }
                    });
                }

                @Override
                public void onError() {
                }
            });
    }
}
