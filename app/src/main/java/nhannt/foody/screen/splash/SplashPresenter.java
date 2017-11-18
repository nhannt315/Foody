package nhannt.foody.screen.splash;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import nhannt.foody.data.LocationRepository;

/**
 * Created by nhannt on 17/11/2017.
 */
public class SplashPresenter implements SplashContract.Presenter, GoogleApiClient
    .ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private SplashContract.View mView;
    private GoogleApiClient mGoogleApiClient;
    private LocationRepository mLocationRepository;

    @Override
    public void setView(SplashContract.View view) {
        mView = view;
        mLocationRepository = new LocationRepository();
    }

    @Override
    public void initGoogleApiClient(Context context) {
        mGoogleApiClient = new GoogleApiClient.Builder(context)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
    }

    @Override
    public void onStart() {
        if (mView.checkLocationPermission()) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onStop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mView.checkLocationPermission()) {
            Location currentLocation = LocationServices.FusedLocationApi.getLastLocation
                (mGoogleApiClient);
            if (currentLocation != null) {
                mLocationRepository.saveLocation(currentLocation.getLongitude(),
                    currentLocation.getLatitude());
            }
        }
        mView.navigateToLogin();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}
