package cs499android.com.cppmapbox;

import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.commons.models.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Richard on 5/3/17.
 */

public abstract class StaticVariables {
    protected static final String MAPBOX_ACCESS_TOKEN = "pk.eyJ1IjoicmljaGFyZGp1bmciLCJhIjoiY2oyOTdma3EwMDA2cTJxbXgwMGt1MWI1aCJ9.d2pGP-GfbVszdIzT-CdJHA";
    protected static final String TAG = "MainActivity";
    protected static final int PERMISSIONS_REQUEST_LOCATION = 99;
    protected static final String BASE_URL = "https://api.mapbox.com";
    protected static Position destination;
    protected static Marker destinationMarker;
    protected static boolean userLocationEnabled;
    protected static boolean speakDescriptions = true;
    protected static boolean speakDirections = true;
    protected static MapboxMap map;
    protected static List<List<LatLng>> polygons = new ArrayList<>();
    protected static List<List<Position>> positions = new ArrayList<>();
}
