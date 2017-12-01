package nhannt.foody.data.source.remote;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

import nhannt.foody.data.model.DishModel;
import nhannt.foody.data.model.MenuModel;
import nhannt.foody.data.source.MenuDataSource;
import nhannt.foody.interfaces.OnCompleteListener;
import timber.log.Timber;

/**
 * Created by nhannt on 29/11/2017.
 */
public class MenuRemoteDataSource implements MenuDataSource.Remote {
    private DatabaseReference nodeRoot;

    public MenuRemoteDataSource() {
        nodeRoot = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public void getMenuListOfPlace(String placeCode,
                                   final OnCompleteListener<ArrayList<MenuModel>> callback) {
        DatabaseReference nodeMenuPlace = nodeRoot.child("thucdonquanans").child(placeCode);
        nodeMenuPlace.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                final ArrayList<MenuModel> menuModelList = new ArrayList<>();
                for (DataSnapshot valueMenu : dataSnapshot.getChildren()) {
                    Log.d("testmenu", valueMenu + "");
                    final MenuModel menuModel = new MenuModel();
                    DatabaseReference nodeMenu = nodeRoot.child("thucdons").child(valueMenu
                        .getKey());
                    nodeMenu.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshotMenu) {
                            String menuModelCode = dataSnapshotMenu.getKey();
                            ArrayList<DishModel> lstDish = new ArrayList<>();
                            menuModel.setMathucdon(menuModelCode);
                            menuModel.setTenthucdon(dataSnapshotMenu.getValue(String.class));
                            for (DataSnapshot valueDish :
                                dataSnapshot.child(menuModelCode).getChildren()) {
                                DishModel dishModel = valueDish.getValue(DishModel.class);
                                dishModel.setMamon(valueDish.getKey());
                                lstDish.add(dishModel);
                            }
                            menuModel.setDishList(lstDish);
                            menuModelList.add(menuModel);
                            callback.onComplete(menuModelList);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            callback.onError();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onError();
            }
        });
    }

    @Override
    public void getAllMenu(final OnCompleteListener<ArrayList<MenuModel>> callback) {
        FirebaseDatabase.getInstance().getReference().child("thucdons")
            .addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ArrayList<MenuModel> menuModelList = new ArrayList<>();
                    for (DataSnapshot valueMenu : dataSnapshot.getChildren()) {
                        MenuModel menuModel = new MenuModel();
                        menuModel.setMathucdon(valueMenu.getKey());
                        menuModel.setTenthucdon(valueMenu.getValue(String.class));
                        menuModelList.add(menuModel);
                    }
                    callback.onComplete(menuModelList);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    callback.onError();
                }
            });
    }
}
