package nhannt.foody.screen.eat_where;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.LocationRepository;
import nhannt.foody.data.model.Place;
import nhannt.foody.data.source.PlaceRepository;
import nhannt.foody.data.source.remote.PlaceRemoteDataSource;
import nhannt.foody.screen.BaseFragment;

/**
 * Created by nhannt on 09/11/2017.
 */
public class WhereToEatFragment extends BaseFragment implements WhereEatContract.View {
    private WhereEatContract.Presenter mPresenter;
    private RecyclerView mRvListPlace;
    private PlaceRecyclerViewAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Location mCurrentLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_where_to_eat, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = new WhereEatPresenter(new PlaceRepository(null, new PlaceRemoteDataSource()
        ), new LocationRepository());
        mPresenter.setView(this);
        initViews();
        mPresenter.getListPlace();
        mCurrentLocation = mPresenter.getCurrentLocation();
    }

    private void initViews() {
        mProgressBar = getView().findViewById(R.id.progress_bar);
        mRvListPlace = getView().findViewById(R.id.rv_where_eat);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRvListPlace.setLayoutManager(layoutManager);
        mRvListPlace.setNestedScrollingEnabled(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    public void setListPlace(ArrayList<Place> listPlace) {
        mAdapter = new PlaceRecyclerViewAdapter(getContext(), listPlace);
        mRvListPlace.setAdapter(mAdapter);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onGetListFailed() {
    }

    @Override
    public void onStop() {
        super.onStop();
        mPresenter.onStop();
    }
}
