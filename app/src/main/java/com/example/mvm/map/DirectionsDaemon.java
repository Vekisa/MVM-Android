package com.example.mvm.map;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.text.Html;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.mvm.Map;

import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.Polyline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DirectionsDaemon implements Runnable {

    private Road road;
    private ArrayList<GeoPoint> wayPoints;
    private RoadManager roadManager;
    private ProgressDialog loading;
    private MapView osm;
    private List<Polyline> roads;
    private List<Marker> markers;
    private GeoPoint end;
    private Map myLocation;
    private ArrayList<Road> directions;
    private TextView directionDescription;
    private RelativeLayout modal;

    public DirectionsDaemon(Road road, ArrayList<GeoPoint> wayPoints, RoadManager roadManager, ProgressDialog loading, MapView osm,
                            List<Polyline> roads, List<Marker> markers, GeoPoint end, Map myLocation, TextView directionDescription, RelativeLayout modal) {
        this.road = road;
        this.wayPoints = wayPoints;
        this.roadManager = roadManager;
        this.loading = loading;
        this.osm = osm;
        this.roads = roads;
        this.markers = markers;
        this.end = end;
        this.myLocation = myLocation;
        this.directions = new ArrayList<>();
        this.directionDescription = directionDescription;
        this.modal = modal;
    }

    public void run() {

        findRoad();
        loading.dismiss();
        myLocation.clear();
        //createRoadDescription();
        drawRoad();
    }

    public void findRoad(){
        do {
            try {
                System.out.println("POINTS " + wayPoints.size() );
                //System.out.println("A" + wayPoints.get(0).getLongitude() + " " + wayPoints.get(0).getLatitude());
                System.out.println("A" + wayPoints.get(1).getLongitude() + " " + wayPoints.get(1).getLatitude());
                directions = new ArrayList<>(Arrays.asList(roadManager.getRoads(wayPoints)));
            }catch (Exception e){
                System.out.println("Nema jos puta!");
                e.printStackTrace();
            }
        }while(directions.get(0) == null || directions.get(0).mStatus != Road.STATUS_OK);
    }

    private void createRoadDescription(){
        Marker startMarker = new Marker(osm);
        startMarker.setPanToView(true);
        startMarker.setPosition(end);
        startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        osm.getOverlays().add(startMarker);
        startMarker.setTitle("Info");
        markers.add(startMarker);
    }

    private void drawRoad(){
        Road theBesDirection = theBestDirection();
        //directionDescription.setText("Distance: " + theBesDirection.mLength + "\n Duration: " + theBesDirection.mDuration);

        createRoadDescription();
        for(final Road direction : directions) {
            final Polyline roadOverlay = RoadManager.buildRoadOverlay(direction);
            roadOverlay.setOnClickListener(new Polyline.OnClickListener() {
                @Override
                public boolean onClick(Polyline polyline, MapView mapView, GeoPoint eventPos) {
                    System.out.println("KLIKNUO SI ME JEBEM TI VSE!");
                    setAllRoadsToBlue();
                    int km = (int) Math.round(direction.mLength);
                    int h = (int) Math.round(direction.mDuration/ 3600);
                    directionDescription.setText( Html.fromHtml("<b>Distance:</b><br>&nbsp;" + km + "&nbsp;<b>km</b><br><br> <b>Duration: </b><br>&nbsp;" + h + "&nbsp;<b>h</b>") );
                    roadOverlay.setColor(Color.BLUE);
                    osm.invalidate();
                    return true;
                }
            });
            roadOverlay.setWidth(15);
            roadOverlay.setDensityMultiplier(1);
            if(theBesDirection == direction)
                roadOverlay.setColor(Color.BLUE);
            else
                roadOverlay.setColor(Color.RED);
            osm.getOverlays().add(roadOverlay);
            osm.invalidate();
            roads.add(roadOverlay);
        }
    }

    private Road theBestDirection(){
        Road road = directions.get(0);
        double duration = road.mDuration;
        if(directions.size() > 1) {
            for (int i = 1; i < directions.size(); i++) {
                if (directions.get(i).mDuration < duration){
                    road = directions.get(i);
                    duration = road.mDuration;
                }
            }
        }
        return road;
    }

    private void setAllRoadsToBlue(){
        for(Overlay o : osm.getOverlays()){
            if(o instanceof Polyline)
                ((Polyline) o).setColor(Color.RED);
        }
    }
}
