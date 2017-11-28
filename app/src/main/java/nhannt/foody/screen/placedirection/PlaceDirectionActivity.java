package nhannt.foody.screen.placedirection;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.Polyline;

import java.util.List;

import nhannt.foody.R;
import nhannt.foody.screen.BaseActivity;
import nhannt.foody.utils.Constants;

public class PlaceDirectionActivity extends BaseActivity
    implements PlaceDirectionContract.View, OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private Toolbar mToolbar;
    private TextView mTvPlaceName;
    private MapFragment mMapFragment;
    private PlaceDirectionContract.Presenter mPresenter;
    private double mDesLongitude, mDesLatitude;
    private String mPlaceName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_direction);
        mPresenter = new PlaceDirectionPresenter();
        mPresenter.setView(this);
        setUpToolbar();
        getInfoFromIntentAndSetup();
        initMaps();
    }

    private void getInfoFromIntentAndSetup() {
        Intent intent = getIntent();
        mPlaceName = intent.getStringExtra(Constants.PLACE_NAME_KEY);
        mDesLatitude = intent.getDoubleExtra(Constants.LATITUDE_KEY, 0);
        mDesLongitude = intent.getDoubleExtra(Constants.LONGITUDE_KEY, 0);
        mTvPlaceName.setText(mPlaceName);
    }

    private void setUpToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mTvPlaceName = findViewById(R.id.tv_place_name_toolbar);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void initMaps() {
        mMapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mMapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        Location currentLocation = mPresenter.getCurrentLocation();
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        markerOptions.position(latLng);
        markerOptions.title(getResources().getString(R.string.current_location));
        googleMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
        googleMap.moveCamera(cameraUpdate);
        mPresenter.getDirection(mDesLatitude, mDesLongitude);
    }

    @Override
    public void getDirectionComplete(List<LatLng> latLngsList) {
        PolylineOptions polylineOptions = new PolylineOptions().width(6).color(getResources()
            .getColor(R.color.color_foody));;
        for (LatLng position : latLngsList) {
            polylineOptions.add(position);
        }
        mGoogleMap.addPolyline(polylineOptions);
        LatLng desLatLng = new LatLng(mDesLatitude, mDesLongitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(mPlaceName);
        markerOptions.position(desLatLng);
        mGoogleMap.addMarker(markerOptions);
    }
}
