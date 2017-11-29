package nhannt.foody.data.source.remote;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.model.Member;
import nhannt.foody.data.source.CommentDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class CommentRemoteDataSource implements CommentDataSource.Remote {
    DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public void addComment(String placeCode, Comment comment, ArrayList<String> lstImage, final
    OnCompleteListener<String> listener) {
        DatabaseReference nodeComment = mDatabaseReference.child
            ("binhluans");
        final String commentCode = nodeComment.child(placeCode).push().getKey();
        //Image
        if (lstImage.size() > 0) {
            for (String valueHinh : comment.getListImage()) {
                Uri uri = Uri.fromFile(new File(valueHinh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans")
                    .child(commentCode).push().setValue(uri.getLastPathSegment());
            }
        }
        //Comment
        nodeComment.child(placeCode).child(commentCode).setValue(comment).addOnCompleteListener
            (new com.google.android.gms.tasks.OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        listener.onComplete(commentCode);
                    else
                        listener.onError();
                }
            });
    }

    @Override
    public void getCommentListOfPlace(final String placeCode,
                                      final OnCompleteListener<ArrayList<Comment>> callback) {
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataRoot) {
                DataSnapshot dataComment = dataRoot.child("binhluans").child(placeCode);
                ArrayList<Comment> lstComment = new ArrayList<>();
                for (DataSnapshot valueComment : dataComment.getChildren()) {
                    Comment comment = valueComment.getValue(Comment.class);
                    comment.setMabinhluan(valueComment.getKey());
                    Member member = dataRoot.child("thanhviens").child(comment.getMauser
                        ()).getValue(Member.class);
                    comment.setUser(member);
                    ArrayList<String> listImage = new ArrayList<>();
                    DataSnapshot dataImageComment = dataRoot.child("hinhanhbinhluans").child
                        (comment.getMabinhluan());
                    for (DataSnapshot valueImageComment : dataImageComment.getChildren()) {
                        listImage.add(valueImageComment.getValue(String.class));
                    }
                    comment.setListImage(listImage);
                    lstComment.add(comment);
                }
                callback.onComplete(lstComment);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });
    }
}
