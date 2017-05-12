package cs499android.com.cppmapbox;

import android.app.Activity;

import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by awing_000 on 5/8/2017.
 */

public class MarkerCluster
{
    private ArrayList<Marker> markers;
    private ArrayList<MarkerOptions> markerOptions;
    private boolean visible;
    private boolean selected;
    private String name;
    private Icon icon;
    private Activity activity;
    private String color;

    public MarkerCluster(Activity activity, String name, String color)
    {
        this.activity = activity;
        this.name = name;
        this.color = color;
        setIcon(color);
        markers = new ArrayList<>();
        markerOptions = new ArrayList<>();
        visible = false;
        selected = true;
    }

    public void createMarkers(String json)
    {
        try
        {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray features = jsonObject.getJSONArray("features");
            for(int i = 0; i < features.length(); i++) {
                JSONObject feature = features.getJSONObject(i);
                JSONObject properties = feature.getJSONObject("properties");
                JSONObject geometry = feature.getJSONObject("geometry");
                JSONArray coords = geometry.getJSONArray("coordinates");
                MarkerOptions m = new MarkerOptions();
                m.setTitle(properties.getString("name"));
                m.setSnippet(properties.getString("description") + "***" + properties.getString("picture"));
                m.setPosition(new LatLng(coords.getDouble(1), coords.getDouble(0)));
                if (icon != null)
                    m.setIcon(icon);
                markerOptions.add(m);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addMarkers()
    {
        if(!visible)
            for (int i = 0; i < markerOptions.size(); i++)
                markers.add(MainActivity.map.addMarker(markerOptions.get(i)));
        visible = true;
    }

    public void removeMarkers(Marker marker)
    {
        if(visible)
            for(int i = 0; i < markers.size(); i++){
                if(!markers.get(i).equals(marker))
                    MainActivity.map.removeMarker(markers.get(i));
            }
        visible = false;
    }

    public void setIcon(String color)
    {
        IconFactory iconFactory = IconFactory.getInstance(activity);
        if(color.toLowerCase().equals("blue"))
            icon = iconFactory.fromResource(R.drawable.blue_marker);
        else if(color.equals("green"))
            icon = iconFactory.fromResource(R.drawable.green_marker);
        else if(color.equals("red"))
            icon = iconFactory.fromResource(R.drawable.red_marker);
        else if(color.equals("yellow"))
            icon = iconFactory.fromResource(R.drawable.yellow_marker);
    }

    public String getColor() {return color;}

    public ArrayList<Marker> getMarkers() {return markers;}

    public boolean isVisible() {return visible;}

    public boolean isSelected() {return selected;}

    public String getName() {return name;}

    public void setColor(String color) {this.color = color;}

    public void setName(String name) {this.name = name;}

    public void setVisible(boolean visible) {this.visible = visible;}

    public void setSelected(boolean selected) {this.selected = selected;}
}