package nhannt.foody.data.source.remote;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import nhannt.foody.data.model.Comment;
import nhannt.foody.data.source.PhotoVideoDataSource;
import nhannt.foody.interfaces.OnCompleteListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class PhotoVideoRemoteDataSource implements PhotoVideoDataSource.Remote {
    @Override
    public void uploadListImage(ArrayList<String> lstImage,
                                final OnCompleteListener<Void> listener) {
        final StorageReference storageReferenceAll = FirebaseStorage.getInstance().getReference();
        for (String imagePath : lstImage) {
            Uri uri = Uri.fromFile(new File(imagePath));
            final StorageReference storageReference = FirebaseStorage.getInstance().getReference()
                .child("hinhanh/" + uri.getLastPathSegment());
            storageReference.putFile(uri).addOnCompleteListener(
                new com.google.android.gms.tasks.OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (storageReference.getActiveUploadTasks().size() == 0)
                            listener.onComplete(null);
                    }
                });
        }
    }

    @Override
    public void getVideoDownloadUrl(String link, final OnCompleteListener<String> callback) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child
            ("videos").child(link);
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                callback.onComplete(uri.toString());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onError();
            }
        });
    }
}
