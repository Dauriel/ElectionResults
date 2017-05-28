/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import electionresults.model.PartyResults;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class DashboardController extends HBox {

    @FXML
    private JFXComboBox<String> regionBox;
    @FXML
    private PieChart pie;
    @FXML
    private BarChart<String, Integer> bar;
    @FXML
    private Font x1;
    @FXML
    private TableView<?> summaryTable;
    @FXML
    private TableColumn<?, ?> summaryCol;
    @FXML
    private TableColumn<?, ?> numbersCol;
    @FXML
    private TableColumn<?, ?> totalpercentageCol;
    @FXML
    private TableView<PartyResults> partyTable;
    @FXML
    private TableColumn<PartyResults, String> partyCol;
    @FXML
    private TableColumn<PartyResults, Integer> seatsCol;
    @FXML
    private TableColumn<PartyResults, Integer> votesCol;
    @FXML
    private TableColumn<PartyResults, Double> percentageCol;
    @FXML
    private JFXSlider slider;

    private JFXTabPane tabPane;
    private Data d;
    private int aux;
    @FXML
    private StackPane stackPane;
    private int counter1 = 0;
    private int counter2 = 0;
    private int counter3 = 0;
    private final double PERCENT = 2.5;
    Map<String, List<PartyResults>> currentBar;
    String currentLabel;
    @FXML
    private Label mapLabel;
    private StackPane region1Layer = new StackPane();
    private StackPane region2Layer = new StackPane();
    private StackPane region3Layer = new StackPane();
    private ImageView region1ImageView = new ImageView(new Image(getClass().getResource("/images/region1.png").toExternalForm()));
    private ImageView region2ImageView = new ImageView(new Image(getClass().getResource("/images/region2.png").toExternalForm()));
    private ImageView region3ImageView = new ImageView(new Image(getClass().getResource("/images/region3.png").toExternalForm()));
    private ImageView region1sImageView = new ImageView(new Image(getClass().getResource("/images/region1s.png").toExternalForm()));
    private ImageView region2sImageView = new ImageView(new Image(getClass().getResource("/images/region2s.png").toExternalForm()));
    private ImageView region3sImageView = new ImageView(new Image(getClass().getResource("/images/region3s.png").toExternalForm()));
    private ImageView region1oImageView = new ImageView(new Image(getClass().getResource("/images/region1o.png").toExternalForm()));
    private ImageView region2oImageView = new ImageView(new Image(getClass().getResource("/images/region2o.png").toExternalForm()));
    private ImageView region3oImageView = new ImageView(new Image(getClass().getResource("/images/region3o.png").toExternalForm()));

    public DashboardController(int datos) {
        aux = datos;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Dashboard.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        d = new Data(aux);
        regionSetter("Comunidad");
        initListeners();
        currentBar = d.getGeneral();
        currentLabel = "General";
        bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));

        pie.setData(d.getPieData(d.getResultadosGlobales(), d.getPartidosenOrdenGlobal()));
        initImage();
        initColPartyTable();
        setTableData(d.getListaResultadosGlobales());
        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                double value = t1.doubleValue();
                bar.setData(d.getBarData(currentBar, currentLabel, value));
            }
        });
    }

    private void initColPartyTable() {
        partyCol.setCellValueFactory(new PropertyValueFactory<>("party"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));
        votesCol.setCellValueFactory(new PropertyValueFactory<>("votes"));
        //percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));        
        percentageCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PartyResults, Double>, ObservableValue<Double>>() {
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<PartyResults, Double> p) {
                double value = p.getValue().getPercentage();
                value = Math.round(value * 100D) / 100D;
                ObservableValue<Double> aux = new ReadOnlyObjectWrapper<>(value);
                return aux;
            }
        });

    }

    private void loadDataPartyTable() {
        //need to finish
    }

    private void regionSetter(String s) {
        if (s.equals("Comunidad")) {
            setTableData(d.getListaResultadosGlobales());
            bar.setTitle("Party votes in Comunidad Valenciana");
            pie.setTitle("Seats Distribution for Comunidad Valenciana");
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getComunidad());
            mapLabel.setText("Comunidad Valenciana");
        } else if (s.equals("Valencia")) {
            setTableData(d.getPartyValencia());
            bar.setTitle("Party votes in Valencia");
            pie.setTitle("Seats Distribution for Valencia");
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getValencia());
            mapLabel.setText("Valencia");
        } else if (s.equals("Castellon")) {
            setTableData(d.getPartyCastellon());
            bar.setTitle("Party votes in Castellón");
            pie.setTitle("Seats Distribution for Castellón");
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getCastellon());
            mapLabel.setText("Castellón");
        } else {
            setTableData(d.getPartyAlicante());
            bar.setTitle("Party votes in Alicante");
            pie.setTitle("Seats Distribution for Alicante");
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getAlicante());
            mapLabel.setText("Alicante");
        }
    }

    private void initListeners() {
        regionBox.setOnAction(e -> loadRegionBox());
    }

    private void loadRegionBox() {
        String regionSelected = regionBox.getSelectionModel().getSelectedItem();
        if (regionSelected != null) {
            Set<String> auxv = d.getValencia();
            Set<String> auxc = d.getCastellon();
            Set<String> auxa = d.getAlicante();
            currentLabel = regionSelected;
            if (auxv.contains(regionSelected)) {
                currentBar = d.getPartyResultsValencia();
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
            } else if (auxc.contains(regionSelected)) {
                currentBar = d.getPartyResultsCastellon();
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
            } else {
                currentBar = d.getPartyResultsAlicante();
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
            }
            bar.setTitle("Party votes in " + regionSelected);
        }
    }

    private void initImage() {

        // add layers
        stackPane.getChildren().addAll(region1Layer, region2Layer, region3Layer);
        
        //add tooltips
        Tooltip tooltipCastellon = new Tooltip("Castellón");
        Tooltip tooltipValencia = new Tooltip("Valencia");
        Tooltip tooltipAlicante = new Tooltip("Alicante");
        Tooltip.install(region1Layer,tooltipCastellon);
        Tooltip.install(region2Layer,tooltipValencia);
        Tooltip.install(region3Layer,tooltipAlicante);
        
        //add images
        region1Layer.getChildren().add(region1ImageView);
        region2Layer.getChildren().add(region2ImageView);
        region3Layer.getChildren().add(region3ImageView);
        
        // mouse handler

        region1Layer.setOnMouseEntered(e -> {
            if (counter1 == 0) {
                region1Layer.getChildren().clear();
                region1Layer.getChildren().add(region1oImageView);
            }
        });
        region1Layer.setOnMouseExited(e -> {
            if (counter1 == 0) {
                region1Layer.getChildren().clear();
                region1Layer.getChildren().add(region1ImageView);
            }
        });
        region2Layer.setOnMouseEntered(e -> {
            if (counter2 == 0) {
                region2Layer.getChildren().clear();
                region2Layer.getChildren().add(region2oImageView);
            }
        });
        region2Layer.setOnMouseExited(e -> {
            if (counter2 == 0) {
                region2Layer.getChildren().clear();
                region2Layer.getChildren().add(region2ImageView);
            }
        });
        region3Layer.setOnMouseEntered(e -> {
            if (counter3 == 0) {
                region3Layer.getChildren().clear();
                region3Layer.getChildren().add(region3oImageView);
            }
        });
        region3Layer.setOnMouseExited(e -> {
            if (counter3 == 0) {
                region3Layer.getChildren().clear();
                region3Layer.getChildren().add(region3ImageView);
            }
        });
        region1Layer.setOnMousePressed(e -> {
            ImageView temp = (ImageView) region3Layer.getChildren().get(0);
            if (counter1 == 1) {
                regionSetter("Comunidad");
                pie.setClockwise(false);
                pie.setData(d.getPieData(d.getResultadosGlobales(), d.getPartidosenOrdenGlobal()));
                currentBar = d.getGeneral();
                currentLabel = "General";
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
                region1Layer.getChildren().clear();
                region1Layer.getChildren().add(region1ImageView);
                counter1--;
            } else {
                regionSetter("Castellon");
                pie.setClockwise(true);
                pie.setData(d.getPieData(d.getResultadosCastellon(), d.getPartidosenOrdenCastellon()));
                currentBar = d.getPartyComunidades();
                currentLabel = "Castellon";
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
                region1Layer.getChildren().clear();
                region1Layer.getChildren().add(region1sImageView);
                if (counter2 == 1) {
                    region2Layer.getChildren().clear();
                    region2Layer.getChildren().add(region2ImageView);
                    counter2--;
                }
                if (counter3 == 1) {
                    region3Layer.getChildren().clear();
                    region3Layer.getChildren().add(region3ImageView);
                    counter3--;
                }
                counter1++;
            }
        });
        region2Layer.setOnMousePressed(e -> {
            ImageView temp = (ImageView) region3Layer.getChildren().get(0);
            if (counter2 == 1) {
                regionSetter("Comunidad");
                pie.setClockwise(false);
                pie.setData(d.getPieData(d.getResultadosGlobales(), d.getPartidosenOrdenGlobal()));
                currentBar = d.getGeneral();
                currentLabel = "General";
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
                region2Layer.getChildren().clear();
                region2Layer.getChildren().add(region2ImageView);
                counter2--;
            } else {
                regionSetter("Valencia");
                currentBar = d.getPartyComunidades();
                currentLabel = "Valencia";
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
                pie.setClockwise(true);
                pie.setData(d.getPieData(d.getResultadosValencia(), d.getPartidosenOrdenValencia()));
                region2Layer.getChildren().clear();
                region2Layer.getChildren().add(region2sImageView);
                if (counter1 == 1) {
                    region1Layer.getChildren().clear();
                    region1Layer.getChildren().add(region1ImageView);
                    counter1--;
                }
                if (counter3 == 1) {
                    region3Layer.getChildren().clear();
                    region3Layer.getChildren().add(region3ImageView);
                    counter3--;
                }
                counter2++;
            }
        });
        region3Layer.setOnMousePressed(e -> {

            ImageView temp = (ImageView) region3Layer.getChildren().get(0);
            if (counter3 == 1) {
                regionSetter("Comunidad");
                pie.setClockwise(false);
                pie.setData(d.getPieData(d.getResultadosGlobales(), d.getPartidosenOrdenGlobal()));
                currentBar = d.getGeneral();
                currentLabel = "General";
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
                region3Layer.getChildren().clear();
                region3Layer.getChildren().add(region3ImageView);
                counter3--;
            } else {
                regionSetter("Alicante");
                currentBar = d.getPartyComunidades();
                currentLabel = "Alicante";
                bar.setData(d.getBarData(currentBar, currentLabel, PERCENT));
                pie.setClockwise(true);
                pie.setData(d.getPieData(d.getResultadosAlicante(), d.getPartidosenOrdenAlicante()));
                region3Layer.getChildren().clear();
                region3Layer.getChildren().add(region3sImageView);
                if (counter2 == 1) {
                    region2Layer.getChildren().clear();
                    region2Layer.getChildren().add(region2ImageView);
                    counter2--;
                }
                if (counter1 == 1) {
                    region1Layer.getChildren().clear();
                    region1Layer.getChildren().add(region1ImageView);
                    counter1--;
                }
                counter3++;
            }

        });

        // this is the magic that allows you to click on the separate layers, but ONLY(!) as long as the layer is transparent
        region1Layer.setPickOnBounds(false);
        region2Layer.setPickOnBounds(false);
        region3Layer.setPickOnBounds(false);
    }

    private void setTableData(List<PartyResults> listaResultados) {
        ObservableList<PartyResults> auxList = FXCollections.observableArrayList(listaResultados);
        partyTable.setItems(auxList);
    }
}
