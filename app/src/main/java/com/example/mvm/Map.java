package com.example.mvm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Image;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mvm.authentication.AppProperties;
import com.example.mvm.map.DirectionsDaemon;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.example.mvm.services.Http;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.LocationListenerProxy;
import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.routing.OSRMRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.modules.MapTileFileStorageProviderBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.MinimapOverlay;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.io.IOException;
import java.nio.DoubleBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import okhttp3.Request;
import okhttp3.Response;

public class Map extends AppCompatActivity {

    private static final int PERMISSAO_REQUERIDA =1 ;
    private MapView osm;
    private MyLocationNewOverlay mlo;
    private IMapController mapController;
    private ImageButton myLocation;
    private ImageButton rotation;
    private ImageButton minimap;
    private ImageButton searchBtn;
    private ImageButton route;
    private AutoCompleteTextView searchInput;
    private RotationGestureOverlay mRotationGestureOverlay;
    private CompassOverlay compassOverlay;
    private ScaleBarOverlay scaleBarOverlay;
    private MinimapOverlay minimapOverlay;
    private RoadManager roadManager;
    private List<Polyline> roads;
    private List<Marker> markers;
    private FusedLocationProviderClient fusedLocationClient;
    private GeoPoint myLocationPoint;
    private ProgressDialog loading = null;
    private MyLocationNewOverlay locationOverlay;
    private Boolean started = false;
    private GpsMyLocationProvider prov;
    private Http http;
    private Boolean finished = false;
    private static ArrayList<String> fruits;
    private AsyncHttpClient client;
    private ArrayAdapter<String> adapter;
    private RelativeLayout modal;
    private TextView directionDescription;
    private ImageButton start;
    private ImageButton closeModal;

    public final LocationListener gpsLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location){
        }
        public void onProviderDisabled(String provider){}
        public void onProviderEnabled(String provider){}
        public void onStatusChanged(String provider, int status, Bundle extras){}
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_map);

        client = new AsyncHttpClient();
        getElementsFromXml();
        enableFeatures();
        initialize();
        initializeProgessBar();
        addListeners();
        enableMyLocation();

        http = Http.getInstance(getApplication());

        /* AUTOCOMPLETE */
        fruits = new ArrayList<>();
        adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        searchInput.setThreshold(1); //will start working from first character
        searchInput.setAdapter(adapter);

        modal.setVisibility(View.GONE);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        LocationManager myLocationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        LocationListenerProxy llp=new LocationListenerProxy(myLocationManager);
        llp.startListening(gpsLocationListener, 1, 1) ;

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            GeoPoint gp = new GeoPoint(location.getLatitude(),location.getLongitude());
                            myLocationPoint = new GeoPoint(gp);
                            osm.getController().animateTo(gp);
                        }
                    }
                });

        //-------------------------------------------------------------
        String value = getIntent().getStringExtra("value");
        if(value.equals("pp")){

            Request request = new Request.Builder()
                    .url(AppProperties.getInstance().getServerUrl() + "/mocked/predicted_prices")
                    .build();

            try {
                Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
                if(response.code() == 200){
                    Gson gson = new Gson();
                    System.out.println("----------------------------------------------------------------------------------");
                    List<Object> list = Arrays.asList(new GsonBuilder().create().fromJson(response.body().string(), Object[].class));
                    for(Object o : list) {
                        LinkedTreeMap ltm = (LinkedTreeMap) o;
                        System.out.println("OVOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO " + ltm.get("city").toString());
                        Marker startMarker = new Marker(osm);
                        GeoPoint gp = new GeoPoint(Double.parseDouble(ltm.get("longitude").toString()),Double.parseDouble(ltm.get("latitude").toString()));
                        //startMarker.setPanToView(true);
                        startMarker.setPosition(gp);
                        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        osm.getOverlays().add(startMarker);
                        startMarker.setTextIcon(ltm.get("city").toString() + "\n" + ltm.get("price").toString() + " " + ltm.get("curr").toString()  +"/" + ltm.get("unit").toString());
                        markers.add(startMarker);
                    }
                    System.out.println(list);
                }else{
                    Toast.makeText(getApplication().getBaseContext (),"Problem pri dobavljanju resursa.",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }else if(value.equals("route")){


            Request request = new Request.Builder()
                    .url(AppProperties.getInstance().getServerUrl() + "/mocked/best_price")
                    .build();

            try {
                Response response = AppProperties.getInstance().getHttpClient().newCall(request).execute();
                if(response.code() == 200){
                    Gson gson = new Gson();
                    System.out.println("----------------------------------------------------------------------------------");
                    Object o = new GsonBuilder().create().fromJson(response.body().string(), Object.class);
                    LinkedTreeMap ltm = (LinkedTreeMap) o;
                    searchInput.setText(ltm.get("city").toString());
                    searchInput.dismissDropDown();
                    if(searchInput.getText().toString().equals(""))
                        return;
                    locationOverlay.disableFollowLocation();
                    hideKeyboard();
                    GeoPoint gp = getLocationFromAddress(getApplicationContext(), searchInput.getText().toString());
                    if(gp == null)
                        setMyLocationMarker(gp);

                    osm.getController().animateTo(gp);
                    Marker startMarker = new Marker(osm);
                    startMarker.setPosition(gp);
                    osm.getOverlays().add(startMarker);
                    markers.add(startMarker);
                    modal.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getApplication().getBaseContext (),"Problem pri dobavljanju resursa.",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


        //--------------------------------------------------------------
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        osm.onDetach();
        osm.getTileProvider().clearTileCache();
    }

    private void centralize(){

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            GeoPoint gp = new GeoPoint(location.getLatitude(),location.getLongitude());
                            myLocationPoint = new GeoPoint(gp);
                            osm.getController().animateTo(gp);
                        }
                    }
                });
    }

    public void addListeners(){

        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setVisibility(View.GONE);
                clear();
            }
        });

        searchInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                locationOverlay.disableFollowLocation();
                hideKeyboard();
                GeoPoint gp = getLocationFromAddress(getApplicationContext(), searchInput.getText().toString());
                setMyLocationMarker(gp);
                osm.getController().animateTo(gp);
                modal.setVisibility(View.VISIBLE);
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("KLIKNUO!");
                Polyline pom = null;
                for(Polyline road : roads){
                    if(road.getColor() == Color.BLUE)
                        pom = road;
                }

                for(Overlay overlay : osm.getOverlays())
                    if(overlay instanceof Polyline && overlay != pom)
                        osm.getOverlays().remove(overlay);

                osm.invalidate();
                modal.setVisibility(View.GONE);
                centralize();
                osm.getController().setZoom(16.0f);

            }
        });

        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println(s.toString());
                odradi(s.toString());
            }
        });

        route.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                route();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(searchInput.getText().toString().equals(""))
                    return;
                locationOverlay.disableFollowLocation();
                hideKeyboard();
                GeoPoint gp = getLocationFromAddress(getApplicationContext(), searchInput.getText().toString());
                if(gp == null)

                    setMyLocationMarker(gp);
                osm.getController().animateTo(gp);
                modal.setVisibility(View.VISIBLE);
            }
        });


        minimap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(osm.getOverlays().contains(minimapOverlay)) {
                    osm.getOverlays().remove(minimapOverlay);
                    osm.invalidate();
                }else {
                    osm.getOverlays().add(minimapOverlay);
                    osm.invalidate();
                }
            }
        });

        rotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRotationGestureOverlay.isEnabled())
                    mRotationGestureOverlay.setEnabled(false);
                else
                    mRotationGestureOverlay.setEnabled(true);
            }
        });

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(locationOverlay.isFollowLocationEnabled())
                    locationOverlay.disableFollowLocation();
                else
                    locationOverlay.enableFollowLocation();
            }
        });

        finished = true;
    }

    public void route(){
        hideKeyboard();
        GeoPoint gp = getLocationFromAddress(getApplicationContext(), searchInput.getText().toString());
        ArrayList<GeoPoint> wayPoints = new ArrayList<GeoPoint>();
        GeoPoint start = locationOverlay.getMyLocation();
        GeoPoint end = gp;
        wayPoints.add(myLocationPoint);
        wayPoints.add(end);
        Road road = null;
        loading.show();
        getRoad(road,wayPoints,roadManager,loading,osm, roads,markers,end,this, directionDescription, modal);
        modal.setVisibility(View.VISIBLE);
        clear();
        started = true;
    }

    void  routeToTheBestPrice(String address){
        GeoPoint gp = getLocationFromAddress(getApplicationContext(), address);
        ArrayList<GeoPoint> wayPoints = new ArrayList<GeoPoint>();
        GeoPoint start = locationOverlay.getMyLocation();
        wayPoints.add(myLocationPoint);
        wayPoints.add(gp);
        Road road = null;
        //loading.show();
        getRoad(road,wayPoints,roadManager,loading,osm, roads,markers,gp,this, directionDescription, modal);
        modal.setVisibility(View.VISIBLE);
        clear();
        started = true;
    }

    public void hideKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void clear(){
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                for(Polyline p : roads){
                    if(osm.getOverlays().contains(p))
                        osm.getOverlays().remove(p);
                }
                roads.clear();
                for(Marker m : markers){
                    if(osm.getOverlays().contains(m))
                        osm.getOverlays().remove(m);
                }
                markers.clear();
                osm.invalidate();
            }
        });
    }

    public GeoPoint getLocationFromAddress(Context context,String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        GeoPoint p1 = null;
        System.out.println("STRADDRESS: " + strAddress);
        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new GeoPoint(location.getLatitude(), location.getLongitude() );
        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void setMyLocationMarker(GeoPoint gp){
        clear();
        Marker startMarker = new Marker(osm);
        startMarker.setPosition(gp);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        osm.getOverlays().add(startMarker);
        markers.add(startMarker);
    }

    public void getRoad(Road road, ArrayList<GeoPoint> wayPoints, RoadManager roadManager, ProgressDialog loading, MapView osm,
                        List<Polyline> roads, List<Marker> markers, GeoPoint end, Map myLocation, TextView directionDescription, RelativeLayout relativeLayout){
        Thread t = new Thread(new DirectionsDaemon(road,wayPoints,roadManager,loading, osm, roads,markers,end, myLocation, directionDescription, relativeLayout));
        t.start();
    }

    public void getElementsFromXml(){
        osm = findViewById(R.id.map);
        rotation= findViewById(R.id.rotation);
        searchBtn = findViewById(R.id.btnS);
        searchInput = findViewById(R.id.inputS);
        route = findViewById(R.id.btnRouteTo);
        minimap = findViewById(R.id.minimap);
        myLocation = findViewById(R.id.myLocation);
        modal = findViewById(R.id.modal);
        directionDescription = findViewById(R.id.directionDescription);
        start = findViewById(R.id.start);
        closeModal = findViewById(R.id.closeModal);
    }

    public void enableFeatures(){
        enableRotation();
        enableScaleBar();
        enableMinimap();
        enableCompass();
    }

    public void enableCompass(){
        compassOverlay = new CompassOverlay(this, new InternalCompassOrientationProvider(this), osm);
        compassOverlay.enableCompass();
        osm.getOverlays().add(compassOverlay);
        osm.getOverlays().remove(minimapOverlay);
        osm.invalidate();
    }

    public void enableRotation(){
        mRotationGestureOverlay  = new RotationGestureOverlay(this, osm);
        mRotationGestureOverlay.setEnabled(true);
        osm.getOverlays().add(mRotationGestureOverlay);
    }

    public void enableScaleBar(){
        scaleBarOverlay = new ScaleBarOverlay(osm);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(osm.getLeft() + 150, osm.getTop() + 50);
        //osm.getOverlays().add(scaleBarOverlay);
    }

    public void enableMinimap(){
        minimapOverlay = new MinimapOverlay(this, osm.getTileRequestCompleteHandler());
        minimapOverlay.setHeight(300);
        minimapOverlay.setWidth(300);
        osm.getOverlays().add(minimapOverlay);
    }

    public void initialize(){
        roads  = new ArrayList<>();
        markers = new ArrayList<>();
        roadManager = new OSRMRoadManager(this);
        prov= new GpsMyLocationProvider(this);
        prov.addLocationSource(LocationManager.NETWORK_PROVIDER);
    }

    public void initializeProgessBar(){
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage("Waiting ...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void enableMyLocation(){
        locationOverlay = new MyLocationNewOverlay(prov, osm);
        locationOverlay.enableMyLocation();
        locationOverlay.enableFollowLocation();
        locationOverlay.setOptionsMenuEnabled(true);
        osm.getOverlayManager().add(locationOverlay);
        osm.setMultiTouchControls(true);
        osm.getController().animateTo(locationOverlay.getMyLocation());
        osm.getController().setZoom(8.0f);
    }

    public void getValuesFromJSON(String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("features");
        fruits.clear();
        for (int i = 0; i < arr.length(); i++)
        {
            String pom = getString(arr.getJSONObject(i));
            fruits.add(pom);

        }

        adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, fruits);
        searchInput.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    public String getString(JSONObject jsonObject) throws JSONException {
        String retValue = "";
        JSONObject propertiesObj = jsonObject.getJSONObject("properties");
        if(propertiesObj.getString("osm_key").equals("place") || propertiesObj.getString("osm_key").equals("highway") || propertiesObj.getString("osm_key").equals("landuse")) {
            System.out.println(jsonObject.getString("properties"));
            retValue = propertiesObj.getString("country") + ", "  + propertiesObj.getString("name");
        }else
            retValue = propertiesObj.getString("name");
        return retValue;
    }

    public void odradi(String s){
        client.get("http://photon.komoot.de/api/?q=" + s, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println(new String(responseBody, StandardCharsets.UTF_8));
                try {
                    getValuesFromJSON(new String(responseBody, StandardCharsets.UTF_8));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseBody, Throwable error) {
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
    }

}

