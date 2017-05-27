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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

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
    private BarChart<?, ?> bar;
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
    @FXML
    private Label mapLabel;

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
        pie.setData(d.getPieData(d.getResultadosGlobales()));
        initImage();
    }

    private void initColPartyTable() {
        partyCol.setCellValueFactory(new PropertyValueFactory<>("party"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("seats"));
        votesCol.setCellValueFactory(new PropertyValueFactory<>("votes"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
    }

    private void loadDataPartyTable() {
        //need to finish
    }

    private void regionSetter(String s) {
        if (s.equals("Comunidad")) {
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getComunidad());
            mapLabel.setText("Comunidad Valenciana");
        } else if (s.equals("Valencia")) {
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getValencia());
            mapLabel.setText("Valencia");
        } else if (s.equals("Castellon")) {
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getCastellon());
            mapLabel.setText("Castellón");
        } else {
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
        System.out.println(regionSelected);
    }

    private void initImage() {
        Pane region1Layer = new Pane();
        Pane region2Layer = new Pane();
        Pane region3Layer = new Pane();

        // add layers
        stackPane.getChildren().addAll(region1Layer, region2Layer, region3Layer);

        // load images
        ImageView region1ImageView = new ImageView(new Image(getClass().getResource("/images/region1.png").toExternalForm()));
        ImageView region2ImageView = new ImageView(new Image(getClass().getResource("/images/region2.png").toExternalForm()));
        ImageView region3ImageView = new ImageView(new Image(getClass().getResource("/images/region3.png").toExternalForm()));
        ImageView region1sImageView = new ImageView(new Image(getClass().getResource("/images/region1s.png").toExternalForm()));
        ImageView region2sImageView = new ImageView(new Image(getClass().getResource("/images/region2s.png").toExternalForm()));
        ImageView region3sImageView = new ImageView(new Image(getClass().getResource("/images/region3s.png").toExternalForm()));

        // add images
        region1Layer.getChildren().add(region1ImageView);
        region2Layer.getChildren().add(region2ImageView);
        region3Layer.getChildren().add(region3ImageView);
        // mouse handler
        region1Layer.setOnMousePressed(e -> {
            ImageView temp = (ImageView) region3Layer.getChildren().get(0);
            if (counter1 == 1) {
                regionSetter("Comunidad");
                pie.setData(d.getPieData(d.getResultadosGlobales()));
                region1Layer.getChildren().clear();
                region1Layer.getChildren().add(region1ImageView);
                counter1--;
            } else {
                regionSetter("Castellon");
                pie.setData(d.getPieData(d.getResultadosCastellon()));
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
                pie.setData(d.getPieData(d.getResultadosGlobales()));
                region2Layer.getChildren().clear();
                region2Layer.getChildren().add(region2ImageView);
                counter2--;
            } else {
                regionSetter("Valencia");
                pie.setData(d.getPieData(d.getResultadosValencia()));
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
                pie.setData(d.getPieData(d.getResultadosGlobales()));
                region3Layer.getChildren().clear();
                region3Layer.getChildren().add(region3ImageView);
                counter3--;
            } else {
                regionSetter("Alicante");
                pie.setData(d.getPieData(d.getResultadosAlicante()));
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

}
