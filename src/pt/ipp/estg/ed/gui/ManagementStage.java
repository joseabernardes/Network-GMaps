/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pt.ipp.estg.ed.gui;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventHandler;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import pt.ipp.estg.ed.adt.graph.NetworkADT;
import pt.ipp.estg.ed.adt.graph.weight.Distance;
import pt.ipp.estg.ed.adt.graph.weight.Time;
import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.map.mapPoint.Store;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;

/**
 *
 * @author José Bernardes
 */
public class ManagementStage extends Stage implements MapComponentInitializedListener {

    private final BorderPane board;
    private final GoogleMapView mapComponent;
    private GoogleMap map;
    private final NetworkADT<MapPoint> net;
    private final Stage mainStage;
    private boolean configStore;
    private MapPoint edgeCity;
    private final Label info, lastEvent;

    ManagementStage(Stage mainStage, NetworkADT<MapPoint> net) {
        super();
        configStore = true;
        this.mainStage = mainStage;
        this.net = net;
        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInializedListener(this);
        info = new Label("Clique no mapa para adicionar uma loja | Clique numa loja para a configurar | Clique numa ligação para a configurar");
        info.setPadding(new Insets(5, 0, 0, 0));
        info.setFont(Font.font(null, FontWeight.BOLD, 14));
        lastEvent = new Label("Ultimo evento: ");
        board = new BorderPane();
        board.setTop(mapComponent);
        board.setLeft(info);
        board.setBottom(lastEvent);
        this.initModality(Modality.WINDOW_MODAL);
        this.initOwner(mainStage);
        Scene storesScene = new Scene(board);
        this.setScene(storesScene);
        this.setTitle("Configuções");
        this.getIcons().add(new Image("/maps-icon.png"));
        mainStage.hide();

    }

    @Override
    public void mapInitialized() {
        LatLong center = new LatLong(41.1488162936, -8.1922356528);
        mapComponent.addMapReadyListener(() -> {
            Point2D p = map.fromLatLngToPoint(center);
        });
        MapOptions options = new MapOptions();
        options.center(center).mapMarker(true).zoom(10).overviewMapControl(false).panControl(false).rotateControl(false)
                .scaleControl(false).streetViewControl(false).zoomControl(false).mapType(MapTypeIdEnum.ROADMAP);
        map = mapComponent.createMap(options, false);
        addMarkers();
        map.addUIEventHandler(map, UIEventType.click, obj -> {
            if (configStore) {
                LatLong location = new LatLong((JSObject) obj.getMember("latLng"));
                TextInputDialog dialog = new TextInputDialog();
                dialog.initOwner(this);
                dialog.setTitle("Adicionar loja");
                dialog.setHeaderText("Indique o nome da loja");
                dialog.setContentText("Nome: ");
                Optional<String> result = dialog.showAndWait();
                if (result.isPresent() && !result.get().equals("")) {
                    MapPoint addPoint = new Store(result.get(), Store.ID + 1, location.getLatitude(), location.getLongitude());
                    net.addVertex(addPoint);
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(location).title(result.get()).animation(Animation.DROP).visible(true).icon("https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_red.png");
                    Marker myMarker = new Marker(markerOptions);
                    map.addMarker(myMarker);

                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Adicionar loja");
                    alert.initOwner(this);
                    alert.setHeaderText("Loja adicionada");
                    alert.setContentText("Ligue outra loja a esta para que a mesma seja considerada válida");
                    lastEvent.setText("Ultimo evento: Loja " + addPoint.getTitle() + " adicionada");
                    map.addUIEventHandler(myMarker, UIEventType.click, new MarkerClick(addPoint));
                    alert.showAndWait();

                }
            }
        });

    }

    public void addMarkers() {

        for (MapPoint store : net) {
            LatLong markerLatLong = new LatLong(store.getLatitude(), store.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(markerLatLong).title(store.getTitle()).animation(Animation.DROP).visible(true).icon("https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_red.png");
            Marker myMarker = new Marker(markerOptions);
            map.addMarker(myMarker);

            Iterator<MapPoint> edges = net.iteratorAllEdges(store);//criar linha entre lojas
            while (edges.hasNext()) {
                MapPoint next = edges.next();

                LatLong[] ary = new LatLong[]{new LatLong(store.getLatitude(), store.getLongitude()), new LatLong(next.getLatitude(), next.getLongitude())};
                MVCArray mvc = new MVCArray(ary);
                PolylineOptions polyOpts = new PolylineOptions().path(mvc).strokeColor("RED").strokeWeight(3).clickable(true);
                Polyline poly = new Polyline(polyOpts);
                map.addMapShape(poly);
                map.addUIEventHandler(poly, UIEventType.click, new EdgeCickEventManagement(store, next, net, mainStage));
            }
            map.addUIEventHandler(myMarker, UIEventType.click, new MarkerClick(store));
        }
    }

    private class MarkerClick implements UIEventHandler {

        private final MapPoint city;

        public MarkerClick(MapPoint city) {
            this.city = city;
        }

        @Override
        public void handle(JSObject obj) {
            if (configStore) {
                //indica que o click é para configurar loja
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.initOwner(ManagementStage.this);
                alert.setTitle("Configurar loja");
                alert.setHeaderText("Remova ou crie uma ligação a outra loja");
                alert.setContentText("Escolha uma opção");
                ButtonType remove = new ButtonType("Remover Loja");
                ButtonType edge = new ButtonType("Criar Ligação");

                ButtonType cancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(remove, edge, cancel);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == remove) {
                    Alert removeAlert = new Alert(AlertType.CONFIRMATION);
                    removeAlert.initOwner(ManagementStage.this);
                    removeAlert.setTitle("Remover Loja");
                    removeAlert.setHeaderText("Pretende remover a loja selecionada?");
                    removeAlert.setContentText("Todas as ligações que envolvam esta loja serão eliminadas!");
                    Optional<ButtonType> resultRemove = removeAlert.showAndWait();
                    if (resultRemove.get() == ButtonType.OK) {
                        try {
                            net.removeVertex(city);
                            mapInitialized();
                            lastEvent.setText("Ultimo evento: Loja " + city.getTitle() + " removida");
                        } catch (EmptyCollectionException ex) {
                            Logger.getLogger(ManagementStage.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else if (result.get() == edge) {
                    configStore = false;
                    edgeCity = city;
                    info.setText("Escolha a loja á qual pretende ligar a loja selecionada");
                    lastEvent.setText("Ultimo evento: Loja " + edgeCity.getTitle() + " selecionada");

                }
            } else {
                //indica que o click é para adicionar um edge entre a loja selecionada e esta

                TwoFieldsDialog dialog = new TwoFieldsDialog();
                dialog.initOwner(ManagementStage.this);
                dialog.setTitle("Adicionar Ligação");
                dialog.setHeaderText(edgeCity.getTitle() + " -> " + city.getTitle());
                dialog.setLabelOne("Distancia: ");
                dialog.setLabelTwo("Tempo: ");
                dialog.setPromptTextFieldOne("em quilometros");
                dialog.setPromptTextFieldTwo("em minutos");
                Optional<String> result = dialog.showAndWait();
                String[] split = dialog.getResults().split(",");
                if (result.isPresent() || !split[0].equals("") || !split[1].equals("")) {
                    net.addEdge(edgeCity, city, new Distance(Double.parseDouble(split[0])), new Time(Double.parseDouble(split[1])));
                    configStore = true;
                    mapInitialized();
                    info.setText("Clique no mapa para adicionar uma loja | Clique numa loja para a configurar | Clique numa ligação para a configurar");
                    lastEvent.setText("Ultimo evento: Ligação entre " + edgeCity.getTitle() + " -> " + city.getTitle() + " criada");
                }

            }
        }

    }

}
