/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTabPane;
import electionresults.model.PartyResults;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author PABLO
 */
public class DashboardController extends HBox {

    @FXML
    private HBox hbox;
    @FXML
    private ChoiceBox<String> regionBox;
    @FXML
    private PieChart pie;
    @FXML
    private BarChart<?, ?> bar;
    @FXML
    private Font x1;
    @FXML
    private ImageView mapImg;
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
        regionSetter("comunidad");
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
        if (s.equals("comunidad")) {
            System.out.println(d.getComunidad().toString());
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getComunidad());
        } else if (s.equals("valencia")) {
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getValencia());
        } else if (s.equals("castellon")) {
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getCastellon());
        } else {
            regionBox.getItems().clear();
            regionBox.getItems().addAll(d.getAlicante());
        }
    }

    private void pieSetter() {

    }
}
