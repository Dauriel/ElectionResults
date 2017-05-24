/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class SampleController extends HBox {

    final static String austria = "Austria";
    final static String brazil = "Brazil";
    final static String france = "France";
    final static String italy = "Italy";
    final static String usa = "USA";
    @FXML
    private ImageView imageBox;
    @FXML
    private Label mapLabel;
    @FXML
    private JFXComboBox<?> regionBox;
    @FXML
    private PieChart pie;
    @FXML
    private BarChart<?, ?> bar;

    public SampleController() {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        loader.setRoot(this);
        loader.setController(this);
        try {
            loader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

            XYChart.Series series1 = new XYChart.Series();
            series1.setName("2003");
            series1.getData().add(new XYChart.Data(austria, 25601.34));
            series1.getData().add(new XYChart.Data(brazil, 20148.82));
            series1.getData().add(new XYChart.Data(france, 10000));
            series1.getData().add(new XYChart.Data(italy, 35407.15));
            series1.getData().add(new XYChart.Data(usa, 12000));
            bar.getData().add(series1);

        }

    }
