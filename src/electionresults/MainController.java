
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import electionresults.model.ElectionResults;
import electionresults.model.Party;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Ian Ward
 */
public class MainController implements Initializable {

    @FXML
    private Region veil;
    @FXML
    private JFXSpinner spinner;
    private static Image[] images = new Image[4];
    private Pagination pagination;
    private ElectionResults electionResults;
    private int year;
    @FXML
    private JFXTabPane tabPane;
    DashboardController dController;
    Map<Integer, Data> datos = new HashMap<Integer, Data>();
    @FXML
    private Tab tab1995;
    @FXML
    private Tab tab1999;
    @FXML
    private BarChart<String, Double> barChart;
    @FXML
    private StackedBarChart<String, Double> stackedBarChart;
    @FXML
    private LineChart<String, Double> lineChart;
    @FXML
    private VBox loadingBox;
    @FXML
    private StackPane stackPane;
    private int[] years = {1995, 1999, 2003, 2007, 2011, 2015};
    private String[] partyNamesS = {"PP", "PSOE", "UPYD", "UV", "EU", "COMPROMIS", "CS", "PODEMOS"};
    private String[] partyNamesS2 = {"PP", "PSOE", "UPYD", "UV", "EU", "COMPROMIS", "CS", "PODEMOS"};
    private Set<String> partyNames = new HashSet<String>(Arrays.asList("PP", "PSOE", "UPYD", "UV", "EU", "COMPROMIS", "CS", "PODEMOS"));
    @FXML
    private JFXButton PSOE;
    @FXML
    private JFXButton PP;
    @FXML
    private JFXButton PODEMOS;
    @FXML
    private JFXButton COMPROMIS;
    @FXML
    private JFXButton CS;
    @FXML
    private JFXButton UV;
    @FXML
    private JFXButton UPYD;
    @FXML
    private JFXButton EU;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), veil);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        //Setting task with spinner and veil 
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.95)");
        Task<Map<Integer, Data>> task = new Task<Map<Integer, Data>>() {
            @Override
            protected Map<Integer, Data> call() throws Exception {
                Map<Integer, Data> auxil = new HashMap<Integer, Data>();
                auxil.put(1995, new Data(1995));
                auxil.put(1999, new Data(1999));
                auxil.put(2003, new Data(2003));
                auxil.put(2007, new Data(2007));
                auxil.put(2011, new Data(2011));
                auxil.put(2015, new Data(2015));
                return auxil;
            }

            @Override
            protected void succeeded() {
                super.succeeded();
                datos = getValue();
                year = Integer.parseInt(tabPane.getSelectionModel().getSelectedItem().getText());
                for (Tab t : tabPane.getTabs()) {
                    try {
                        int anyo = parseInt(t.getText());
                        dController = new DashboardController(datos.get(anyo));
                        t.setContent(dController);
                    } catch (NumberFormatException e) {
                    }
                }
                displayTutorial();
                barChart.getXAxis().setAnimated(false);
                stackedBarChart.getXAxis().setAnimated(false);
                generateBarChart();
                createStacked(partyNames, true);                
                createStacked(partyNames, false);
                initButtons();
            }
        };

        veil.visibleProperty().bind(task.runningProperty());
        loadingBox.visibleProperty().bind(task.runningProperty());
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void displayTutorial() {
        StackPane s = stackPane;
        Region r = new Region();
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), s);
        fadeIn.setFromValue(0.5);
        fadeIn.setToValue(1);
        fadeIn.setCycleCount(1);
        fadeIn.play();
        r.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6) ");
        r.setEffect(new GaussianBlur(100));
        VBox outerBox = new VBox();
        outerBox.setAlignment(Pos.CENTER);
        //Images for our pages
        images[0] = new Image("/images/anyo.gif");
        images[1] = new Image("/images/province.gif");
        images[2] = new Image("/images/region.gif");
        images[3] = new Image("/images/graph.gif");
        pagination = new Pagination(4);
        pagination.setPageFactory((Integer pageIndex) -> createTutorialPage(pageIndex));
        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
        Button styleButton = new Button("Start Now!");
        styleButton.setStyle("-fx-background-color: #009688; -fx-font-size: 20; -fx-font-weight: bold; -fx-text-fill: white;");
        styleButton.setOnAction((ActionEvent me) -> {
            r.setVisible(false);
            outerBox.setVisible(false);
        });
        Label welcome = new Label("Welcome to Election Results!");
        welcome.setStyle("-fx-font-size: 32; -fx-font-weight: bold; -fx-text-fill: white;");
        outerBox.getChildren().addAll(welcome, pagination, styleButton);
        s.getChildren().addAll(r, outerBox);
    }

    public void generateBarChart() {

        ObservableList<XYChart.Series<String, Double>> auxListaa = FXCollections.observableArrayList();
        XYChart.Series<String, Double> barData1 = new XYChart.Series<String, Double>();
        XYChart.Series<String, Double> barData2 = new XYChart.Series<String, Double>();
        XYChart.Series<String, Double> barData3 = new XYChart.Series<String, Double>();
        XYChart.Series<String, Double> barData4 = new XYChart.Series<String, Double>();
        barData4.setName("Alicante");
        barData3.setName("Castellón");
        barData2.setName("Valencia");
        barData1.setName("Comunidad Valenciana");

         for (int i = 0; i < years.length; i++) {
            barData1.getData().add(new XYChart.Data("" + years[i], datos.get(years[i]).getMediaGeneral()));
            barData1.getData().forEach(data -> {
                    Label label = new Label( data.getYValue().intValue() + "%");
                    data.setNode(new StackPane(label));
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
                });
            barData2.getData().add(new XYChart.Data("" + years[i], datos.get(years[i]).getMediaValencia()));
            barData2.getData().forEach(data -> {
                    Label label = new Label( data.getYValue().intValue() + "%");
                    data.setNode(new StackPane(label));
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
                });
            barData3.getData().add(new XYChart.Data("" + years[i], datos.get(years[i]).getMediaCastellon()));
            barData3.getData().forEach(data -> {
                    Label label = new Label( data.getYValue().intValue() + "%");
                    data.setNode(new StackPane(label));
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
                });
            barData4.getData().add(new XYChart.Data("" + years[i], datos.get(years[i]).getMediaAlicante()));
            barData4.getData().forEach(data -> {
                    Label label = new Label( data.getYValue().intValue() + "%");
                    data.setNode(new StackPane(label));
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
                });
        }

        auxListaa.add(barData1);
        auxListaa.add(barData2);
        auxListaa.add(barData3);
        auxListaa.add(barData4);
        barChart.setData(auxListaa);
    }

    private void createStacked(Set<String> partii, boolean on) {
        Set<String> partyParty = partii;
        ArrayList<Map<String, Double>> mapFinal = new ArrayList<Map<String, Double>>();
        for (Integer i : years) {
            if(on){
            mapFinal.add(datos.get(i).seatsPerParty(partyParty));
            }else{mapFinal.add(datos.get(i).votesPerParty(partyParty));}
        }
        ObservableList<XYChart.Series<String, Double>> auxListaa = FXCollections.observableArrayList();
        ArrayList<XYChart.Series<String, Double>> barDatas = new ArrayList<XYChart.Series<String, Double>>();
        for (String s : partyNamesS) {
            if (s != null) {
                XYChart.Series<String, Double> auxBar = new XYChart.Series<String, Double>();
                auxBar.setName(s);
                barDatas.add(auxBar);
            }
        }

        for (int i = 0; i < partyParty.size(); i++) {
            XYChart.Series<String, Double> auxBar = barDatas.get(i);
            for (int j = 0; j < years.length; j++) {
                Map<String, Double> auxMap = mapFinal.get(j);
                String s = auxBar.getName();
                if (auxMap.get(s) != null) {
                    XYChart.Data variable = new XYChart.Data("" + years[j], auxMap.get(s));
                    auxBar.getData().add(variable);
                }
            }
            auxListaa.add(auxBar);
        }
        if(on){
        stackedBarChart.setData(auxListaa);
        }else{lineChart.setData(auxListaa);}
    }

    private VBox createTutorialPage(Integer pageIndex) {
        VBox box = new VBox();
        Image img = images[pageIndex];
        ImageView iv = new ImageView(img);
        box.setAlignment(Pos.CENTER);
        Label desc = new Label();
        if (pageIndex == 0) {
            desc = new Label("Select the year");
        }
        if (pageIndex == 1) {
            desc = new Label("Choose the province or go back to community");
        }
        if (pageIndex == 2) {
            desc = new Label("Pick the region");
        }
        if (pageIndex == 3) {
            desc = new Label("Watch the results!");
        }
        desc.setStyle("-fx-font-size: 16; -fx-font-weight: bold; -fx-text-fill: white;");
        box.getChildren().addAll(iv, desc);
        return box;
    }

    @FXML
    private void toggleButton(ActionEvent event) {
        Button i = (Button) event.getSource();
        String s = i.getId();
        Set<String> aux = isInArray(s);
        createStacked(aux, true);
        createStacked(aux, false);
    }

    private Set<String> isInArray(String s) {
        int pointer = -1;
        int it = 0;
        for (String x : partyNamesS2) {
            if (x.equals(s)) {
                pointer = it;
            }
            it++;
        }
        Set<String> auxil = new HashSet<String>();
        if (pointer != -1) {
            if (partyNamesS[pointer] != null) {
                partyNamesS[pointer] = null;
            } else {
                partyNamesS[pointer] = s;
            }
            for (int i = 0; i < partyNamesS.length; i++) {
                if (partyNamesS[i] != null) {
                    auxil.add(partyNamesS[i]);
                }

            }
        }
        return auxil;

    }
    private void initButtons(){
        Image im = Party.PP.getLogo();
        ImageView imview = new ImageView(im);
        PP.setGraphic(imview);
        im = Party.PSOE.getLogo();
        imview = new ImageView(im);
        PSOE.setGraphic(imview);
        im = Party.PODEMOS.getLogo();
        imview = new ImageView(im);
        PODEMOS.setGraphic(imview);
        im = Party.COMPROMIS.getLogo();
        imview = new ImageView(im);
        COMPROMIS.setGraphic(imview);
        im = Party.CS.getLogo();
        imview = new ImageView(im);
        CS.setGraphic(imview);
        im = Party.UV.getLogo();
        imview = new ImageView(im);
         UV.setGraphic(imview);
        im = Party.UPYD.getLogo();
        imview = new ImageView(im);
        UPYD.setGraphic(imview);
        im = Party.EU.getLogo();
        imview = new ImageView(im);
        EU.setGraphic(imview);
    }

}
