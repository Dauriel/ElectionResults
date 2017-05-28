
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
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;

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
    private JFXButton button2;
    @FXML
    private JFXButton button3;
    @FXML
    private JFXButton button4;
    @FXML
    private JFXButton button5;
    @FXML
    private JFXButton button6;
    @FXML
    private JFXButton button7;
    @FXML
    private JFXButton button8;
    @FXML
    private JFXButton button1;
    @FXML
    private StackedBarChart<?, ?> stackedBarChart;
    @FXML
    private LineChart<?, ?> lineChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setting task with spinner and veil 
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6)");
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
                } catch (NumberFormatException e) {}
            }
            generateBarChart();
            }
        };

        veil.visibleProperty().bind(task.runningProperty());
        spinner.visibleProperty().bind(task.runningProperty());
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    public void generateBarChart() {
        XYChart.Series<String, Double> barData1 = new XYChart.Series<String, Double>();
        ObservableList<XYChart.Series<String, Double>> auxListaa = FXCollections.observableArrayList();
        barData1.setName("Comunidad Valenciana");
        barData1.getData().add(new XYChart.Data("" + 1995, datos.get(1995).getMediaGeneral()));
        barData1.getData().add(new XYChart.Data("" + 1999, datos.get(1999).getMediaGeneral()));
        barData1.getData().add(new XYChart.Data("" + 2003, datos.get(2003).getMediaGeneral()));
        barData1.getData().add(new XYChart.Data("" + 2007, datos.get(2007).getMediaGeneral()));
        barData1.getData().add(new XYChart.Data("" + 2011, datos.get(2011).getMediaGeneral()));
        barData1.getData().add(new XYChart.Data("" + 2015, datos.get(2015).getMediaGeneral()));
        XYChart.Series<String, Double> barData2 = new XYChart.Series<String, Double>();
        barData2.setName("Valencia");
        barData2.getData().add(new XYChart.Data("" + 1995, datos.get(1995).getMediaValencia()));
        barData2.getData().add(new XYChart.Data("" + 1999, datos.get(1999).getMediaValencia()));
        barData2.getData().add(new XYChart.Data("" + 2003, datos.get(2003).getMediaValencia()));
        barData2.getData().add(new XYChart.Data("" + 2007, datos.get(2007).getMediaValencia()));
        barData2.getData().add(new XYChart.Data("" + 2011, datos.get(2011).getMediaValencia()));
        barData2.getData().add(new XYChart.Data("" + 2015, datos.get(2015).getMediaValencia()));
        XYChart.Series<String, Double> barData3 = new XYChart.Series<String, Double>();
        barData3.setName("Castellón");
        barData3.getData().add(new XYChart.Data("" + 1995, datos.get(1995).getMediaCastellon()));
        barData3.getData().add(new XYChart.Data("" + 1999, datos.get(1999).getMediaCastellon()));
        barData3.getData().add(new XYChart.Data("" + 2003, datos.get(2003).getMediaCastellon()));
        barData3.getData().add(new XYChart.Data("" + 2007, datos.get(2007).getMediaCastellon()));
        barData3.getData().add(new XYChart.Data("" + 2011, datos.get(2011).getMediaCastellon()));
        barData3.getData().add(new XYChart.Data("" + 2015, datos.get(2015).getMediaCastellon()));
        XYChart.Series<String, Double> barData4 = new XYChart.Series<String, Double>();
        barData4.setName("Alicante");
        barData4.getData().add(new XYChart.Data("" + 1995, datos.get(1995).getMediaAlicante()));
        barData4.getData().add(new XYChart.Data("" + 1999, datos.get(1999).getMediaAlicante()));
        barData4.getData().add(new XYChart.Data("" + 2003, datos.get(2003).getMediaAlicante()));
        barData4.getData().add(new XYChart.Data("" + 2007, datos.get(2007).getMediaAlicante()));
        barData4.getData().add(new XYChart.Data("" + 2011, datos.get(2011).getMediaAlicante()));
        barData4.getData().add(new XYChart.Data("" + 2015, datos.get(2015).getMediaAlicante()));

        auxListaa.add(barData1);
        auxListaa.add(barData2);
        auxListaa.add(barData3);
        auxListaa.add(barData4);
        barChart.setData(auxListaa);
    }
}
