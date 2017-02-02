package pt.ipp.estg.ed.gui;

import pt.ipp.estg.ed.map.csvReader.GraphCSVReader;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
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
import java.io.IOException;
import pt.ipp.estg.ed.adt.graph.weight.Cost;
import pt.ipp.estg.ed.adt.graph.weight.Distance;
import pt.ipp.estg.ed.adt.graph.NetworkADT;
import pt.ipp.estg.ed.adt.graph.weight.Time;
import pt.ipp.estg.ed.adt.graph.weight.TotalTime;
import pt.ipp.estg.ed.adt.graph.weight.Weight;
import java.util.Iterator;
import java.util.Locale;
import java.util.Optional;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pt.ipp.estg.ed.adt.unorderedList.DoubleLinkedUnorderedList;
import pt.ipp.estg.ed.adt.unorderedList.UnorderedListADT;
import pt.ipp.estg.ed.exceptions.EmptyCollectionException;
import pt.ipp.estg.ed.map.mapPoint.MapPoint;

/**
 * <h3>
 * ESTG - Escola Superior de Tecnologia e Gestão<br>
 * ED - Trabalho Pratico<br>
 * </h3>
 * <p>
 * <strong>Nome:</strong> José Paulo de Almeida Bernardes<br>
 * <strong>Número:</strong> 8150148<br>
 * <strong>Turma:</strong> LEI2T3<br>
 * </p>
 * <p>
 * <strong>Descrição: </strong><br>
 * Classe que representa a execução da aplicação
 * </p>
 */
public class MainApplication extends Application implements MapComponentInitializedListener, EventHandler<ActionEvent> {

    protected GoogleMapView mapComponent;
    protected GoogleMap map;
    private NetworkADT<MapPoint> network;
    private UnorderedListADT<MapPoint> list;
    private Button calcular, start, config;
    private RadioButton distance, time, cost;
    private ListView<String> listView;
    private GridPane pane;
    private Label total, lblDistance, lblTime, lblVelocity, lblCost;
    private Stage mainStage;
    private BorderPane bp;

    @Override
    public void start(final Stage stage) throws Exception {
        mainStage = stage;
        network = GraphCSVReader.readGraph("GraphVertex.csv", "GraphEdges.csv");
        list = new DoubleLinkedUnorderedList<MapPoint>();
        start = new Button("Iniciar Nova Rota_");
        start.setOnAction(this);
        calcular = new Button("Calcular Rota_");
        calcular.setOnAction(this);
        config = new Button("", new ImageView(new Image("/config.png")));
        config.setOnAction(this);
        distance = new RadioButton("Distancia");
        distance.setSelected(true);
        time = new RadioButton("Tempo");
        cost = new RadioButton("Custo");
        ToggleGroup radio = new ToggleGroup();
        distance.setToggleGroup(radio);
        time.setToggleGroup(radio);
        cost.setToggleGroup(radio);
        listView = new ListView<String>();
        listView.setFocusTraversable(false);
        total = new Label("Totais");
        total.setFont(Font.font(null, FontWeight.BOLD, 16));
        total.setPadding(new Insets(0, 0, 0, 80));
        lblDistance = new Label("Distancia: ");
        lblTime = new Label("Tempo: ");
        lblVelocity = new Label("Velocidade: ");
        lblCost = new Label("Custo");
        bp = new BorderPane();

        pane = new GridPane();
        pane.setDisable(true);
      
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setPrefWidth(250);
        pane.setAlignment(Pos.BASELINE_CENTER);
        pane.add(calcular, 0, 1);
        pane.add(distance, 0, 2);
        pane.add(time, 0, 3);
        pane.add(cost, 0, 4);
        pane.add(listView, 0, 5);
        pane.add(total, 0, 6);
        pane.add(lblDistance, 0, 7);
        pane.add(lblTime, 0, 8);
        pane.add(lblVelocity, 0, 9);
        pane.add(lblCost, 0, 10);
        buildMap();

      

        BorderPane buttons = new BorderPane();
          buttons.setPadding(new Insets(10, 10, 10, 10));
        buttons.setLeft(start);
        buttons.setRight(config);
        buttons.setBottom(pane);

        bp.setRight(buttons);
        Scene scene = new Scene(bp);
        stage.setScene(scene);
        stage.setTitle("Self-Service Tour");
        stage.getIcons().add(new Image("/maps-icon.png"));
        stage.show();
        stage.setOnCloseRequest(we -> {
            System.out.println("Aplicação a fechar...");
            try {
                System.out.println("A escrever nos ficheiros...");
                GraphCSVReader.writeGraph("GraphVertex.csv", "GraphEdges.csv", network);
                System.out.println("Ficheiros escritos com sucesso");
            } catch (IOException ex) {
                System.err.println("Falha ao escrever nos ficheiros...");
            }

        });

    }

    public void buildMap() {
        mapComponent = new GoogleMapView(Locale.getDefault().getLanguage(), null);
        mapComponent.addMapInializedListener(this);
        bp.setCenter(mapComponent);

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

        listView.getItems().add("1º->Escolha o StartPoint");
        listView.getItems().add("2º->Escolha o EndPoint");
        listView.getItems().add("3º->Escolha os locais a visitar");
        pane.setDisable(false);
    }

    public void addMarkers() {

        Iterator<MapPoint> it = network.iterator();
        while (it.hasNext()) {
            MapPoint city = it.next();

            LatLong markerLatLong = new LatLong(city.getLatitude(), city.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(markerLatLong).title(city.getTitle()).animation(Animation.DROP).visible(true).icon("https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_grey.png");
            Marker myMarker = new Marker(markerOptions);
            map.addMarker(myMarker);

            Iterator<MapPoint> edges = network.iteratorAllEdges(city);//criar linha entre cidades
            while (edges.hasNext()) {
                MapPoint next = edges.next();

                LatLong[] ary = new LatLong[]{new LatLong(city.getLatitude(), city.getLongitude()), new LatLong(next.getLatitude(), next.getLongitude())};
                MVCArray mvc = new MVCArray(ary);
                PolylineOptions polyOpts = new PolylineOptions().path(mvc).strokeColor("GRAY").strokeWeight(2).clickable(true);
                Polyline poly = new Polyline(polyOpts);
                map.addMapShape(poly);
                map.addUIEventHandler(poly, UIEventType.click, new EdgeCickEvent(city, next, network, mainStage));
            }

            map.addUIEventHandler(myMarker, UIEventType.click, (JSObject obj) -> {
                boolean flag;
                String res = "0";  //se for o start point, ou um ponto não confirmado, o stopTime é 0
                if (list.isEmpty()) {// start point
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.initOwner(mainStage);
                    alert.setTitle("Adicionar o ponto de partida");
                    alert.setHeaderText(city.getTitle());
                    alert.setContentText("Esta será o seu ponto de partida");
                    Optional<ButtonType> results = alert.showAndWait();
                    flag = (results.get() == ButtonType.OK); //if
                } else {
                    TextInputDialog dialog = new TextInputDialog("0.0");
                    dialog.initOwner(mainStage);
                    dialog.setTitle("Adicionar ao roteiro");
                    dialog.setHeaderText(city.getTitle());
                    dialog.setContentText("Tempo de paragem: (min)");
                    Optional<String> result = dialog.showAndWait();
                    if (result.isPresent()) {
                        flag = true;
                        res = result.get();
                    } else {
                        flag = false;
                    }
                }

                if (flag) {
//                    city.setStopTime(Double.parseDouble(res));
                    switch (list.size()) {
                        case 0:// start point
                            markerOptions.icon("https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_redS.png");  //não pôde ser um caminho local !
                            break;
                        case 1:// end point
                            markerOptions.icon("https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_redE.png");  //não pôde ser um caminho local !
                            break;
                        default:
                            markerOptions.icon("https://raw.githubusercontent.com/Concept211/Google-Maps-Markers/master/images/marker_red.png");  //não pôde ser um caminho local !
                            break;
                    }
                    myMarker.setOptions(markerOptions);
                    list.addToRear(city);
                }
            });
        }
    }

    public void markPath(Iterator<MapPoint> path, String color) {
        MapPoint lastCity;
        if (path.hasNext()) {
            lastCity = path.next(); //get the first city
        } else {
            return;
        }
        int i = 0;
        listView.getItems().add(++i + "º - " + lastCity.toString()); //adicionar O start á lista
        while (path.hasNext()) {
            MapPoint city = path.next();
            if (!city.equals(lastCity)) { //criar linha entre cidades
                LatLong[] ary = new LatLong[]{new LatLong(city.getLatitude(), city.getLongitude()), new LatLong(lastCity.getLatitude(), lastCity.getLongitude())};
                MVCArray mvc = new MVCArray(ary);
                PolylineOptions polyOpts = new PolylineOptions()
                        .path(mvc)
                        .strokeColor(color)
                        .strokeWeight(5)
                        .clickable(true);
                Polyline poly = new Polyline(polyOpts);
                map.addMapShape(poly);
                map.addUIEventHandler(poly, UIEventType.click, new EdgeCickEvent(city, lastCity, network, mainStage));
                listView.getItems().add(++i + "º - " + city.toString()); //adicionar a cidade á lista
            }
            lastCity = city;

        }
    }

    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == config) {
            Stage add = new ManagementStage(mainStage, network);
            add.showAndWait();
            buildMap();
            listView.getItems().setAll(new String[0]); //apagar a lista
            mainStage.show();
        }

        if (event.getSource() == start) {
            list = new DoubleLinkedUnorderedList<MapPoint>();
            calcular.setVisible(true);
            mapInitialized();
            listView.getItems().setAll(new String[0]); //apagar a lista
            listView.getItems().add("1º->Escolha o StartPoint");
            listView.getItems().add("2º->Escolha o EndPoint");
            listView.getItems().add("3º->Escolha os locais a visitar");
        }
        if (event.getSource() == calcular) {
            try {
                System.out.println("A calcular percurso...");
                Weight rule;
                if (distance.isSelected()) {
                    rule = new Distance();
                } else if (time.isSelected()) {
                    rule = new TotalTime();
                } else if (cost.isSelected()) {
                    rule = new Cost();
                } else {
                    rule = new Distance(); //default
                }
                listView.getItems().setAll(new String[0]); //apagar a lista
                UnorderedListADT<MapPoint> stopCitys = new DoubleLinkedUnorderedList<MapPoint>();

                for (MapPoint city : list) { //guardar as cidades de paragem obrigatória
                    stopCitys.addToRear(city);
                }
                UnorderedListADT<MapPoint> resultList = new DoubleLinkedUnorderedList<MapPoint>();
                Iterator<MapPoint> iterator = network.iteratorShortestPathMultipleVertex(list.removeFirst(), list.removeFirst(), list, rule);
                while (iterator.hasNext()) {
                    resultList.addToRear(iterator.next());
                }
                markPath(resultList.iterator(),"#00b3fd");
                System.out.println("Percurso calculado");
                double weight;
                double dWeigh = network.shortestPathWeight(resultList.iterator(), new Distance());  //distancia total
                lblDistance.setText("Distancia: " + dWeigh + " Km");
                for (MapPoint stop : stopCitys) {
                    stop.setWeightWithStop(true);
                }
                weight = network.shortestPathWeight(resultList.iterator(), new TotalTime());  //tempo total
                lblTime.setText("Tempo: " + weight + " min");
                weight = network.shortestPathWeight(resultList.iterator(), new Time());  //tempo em andamento
                lblVelocity.setText("Velocidade: " + Math.round((((dWeigh / weight) * 60) * 100)) / 100f + " Km/h");  //velocidade do percurso
                weight = network.shortestPathWeight(resultList.iterator(), new Cost()); //custo total, parado e em andamento
                lblCost.setText("Custo: " + Math.round((weight * 100)) / 100f + "€");
                for (MapPoint stop : stopCitys) {
//                    stop.setStopTime(0.0);
                }

                calcular.setVisible(false);
            } catch (EmptyCollectionException ex) {
                System.out.println("Ainda não selecionou os pontos necessarios");
                start.fire();
            }

        }
    }

    public static void main(String[] args) {
        System.setProperty("java.net.useSystemProxies", "true");
        launch(args);
    }

}
