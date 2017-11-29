package nhannt.foody.data.source.local;

import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;

import nhannt.foody.FoodyApplication;
import nhannt.foody.data.model.ImageSelect;
import nhannt.foody.data.source.PhotoVideoDataSource;
import nhannt.foody.interfaces.OnLoadListItemListener;

/**
 * Created by nhannt on 29/11/2017.
 */
public class PhotoVideoLocalDataSource implements PhotoVideoDataSource.Local {

    private GetAllImage mGetAllImage;
    private boolean isFinished = true;
    @Override
    public void getAllImageFromLocalStorage(OnLoadListItemListener<ImageSelect> itemListener) {
        if(isFinished){
            isFinished = false;
            mGetAllImage = new GetAllImage(itemListener);
            mGetAllImage.execute();
        }
    }

    class GetAllImage extends AsyncTask<Void, Void, ArrayList<ImageSelect>>{

        private OnLoadListItemListener<ImageSelect> listener;

        public GetAllImage(OnLoadListItemListener<ImageSelect> listener){
            this.listener = listener;
        }

        @Override
        protected ArrayList<ImageSelect> doInBackground(Void... voids) {
            ArrayList<ImageSelect> lstImage = new ArrayList<>();
            String [] projection = {MediaStore.Images.Media.DATA};
            Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = FoodyApplication.getAppContext().getContentResolver().query(uri,
                projection,null,null,
                null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                ImageSelect imageSelect = new ImageSelect(path,false);

                lstImage.add(imageSelect);
                cursor.moveToNext();
            }
            return lstImage;
        }

        @Override
        protected void onPostExecute(ArrayList<ImageSelect> imageSelects) {
            super.onPostExecute(imageSelects);
            isFinished = true;
            listener.onLoadItemComplete(imageSelects);
        }
    }

    @Override
    public void cancel() {
        if(mGetAllImage != null && !isFinished){
            mGetAllImage.cancel(true);
        }
    }
}
