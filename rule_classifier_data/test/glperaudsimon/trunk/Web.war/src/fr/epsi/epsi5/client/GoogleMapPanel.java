package fr.epsi.epsi5.client;

import com.google.gwt.core.client.JavaScriptObject;
import com.gwtext.client.core.Function;
import com.gwtext.client.util.JavaScriptObjectHelper;
import com.gwtext.client.widgets.BoxComponent;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.PanelListenerAdapter;
import com.gwtext.client.widgets.layout.FitLayout;
import com.gwtext.client.widgets.map.GoogleMap;
import com.gwtext.client.widgets.map.LatLonPoint;
import com.gwtext.client.widgets.map.MapPanel;
import com.gwtext.client.widgets.map.Marker;

/**
 * @author Wilhelm Peraud-Loïc Simon
 */
public class GoogleMapPanel extends Panel {

  /**
   * Google map.
   */
  private MapPanel map;

  /**
   * Whether this map has been rendered or not.
   */
  private boolean isRendered;

  /**
   *
   * Defines a new GoogleMapPanel.
   *
   */
  public GoogleMapPanel() {
    this.setLayout(new FitLayout());
    createMapPanel();
    this.add(this.map);
    this.setHeight("100%");
    this.setTitle("Google map");
	this.setId("mapPanel");
	this.setVisible(false);
  }
  
  /**
  *
  * Creates a new GoogleMapPanel.
  *
  */
  private void createMapPanel() {
    this.map = new GoogleMap();
    
    this.map.setWidth("100%");
    this.map.addLargeControls();

    this.map.addListener(MapPanel.MAP_RENDERED_EVENT, new Function() {
      public void execute() {
        GoogleMapPanel.this.isRendered = true;
        GoogleMapPanel.this.setVisible(true);
      }
    });

    this.map.addListener(new PanelListenerAdapter() {
      public void onResize(BoxComponent component, int adjWidth,
          int adjHeight, int rawWidth, int rawHeight) {

        if (GoogleMapPanel.this.isRendered) {
          GoogleMapPanel.this.map.resizeTo(
              GoogleMapPanel.this.map.getInnerWidth(),
              GoogleMapPanel.this.map.getInnerHeight()
          );
        }
      }
    });
  }
  

  /**
  *
  * Updates the GoogleMapPanel.
  * @param map carte
  * @param llp objet javascript
  * @param locationAddress chaine de caractère pour le lieu
  */
  public native void updateMap(String locationAddress, JavaScriptObject llp, GoogleMapPanel map) /*-{
     var geo = new $wnd.GClientGeocoder();

     geo.getLocations(locationAddress,
       function(response) {    // callback method to be executed when result arrives from server

         if (!response || response.Status.code != 200) {
             alert("Unable to geocode that address");
         }
         else {
             var place = response.Placemark[0];
             llp.lat = place.Point.coordinates[1];
             llp.lon = place.Point.coordinates[0];

             map.@fr.epsi.epsi5.client.GoogleMapPanel::renderMap(Lcom/google/gwt/core/client/JavaScriptObject;)(llp);
         }
        }
      );
   }-*/;

  /**
   * Render the map using the call back object which contains the location longitude and altitude.
   *
   * @param jsObj location information
   */
  public void renderMap(JavaScriptObject jsObj) {
    double lat = Double.parseDouble(JavaScriptObjectHelper.getAttribute(jsObj, "lat"));
    double lon = Double.parseDouble(JavaScriptObjectHelper.getAttribute(jsObj, "lon"));

    LatLonPoint latLonPoint = new LatLonPoint(lat, lon);
    map.setCenterAndZoom(latLonPoint, 5);
    map.removeAllMarkers();
    map.addMarker(new Marker(latLonPoint));
  }
  
  /**
   * Méthode pour mettre à jour la carte.
   * @param destination pour la carte
   * @param ville booleen pour savoir si on zoom
   */
  public void majCarte(String destination, boolean ville){
	  if(this.isRendered) {
		  updateMap(destination, JavaScriptObjectHelper.createObject(), this);
	  }
  }

}
