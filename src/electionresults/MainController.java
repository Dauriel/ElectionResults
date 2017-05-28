
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import com.jfoenix.controls.JFXSpinner;
import com.jfoenix.controls.JFXTabPane;
import electionresults.model.ElectionResults;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    ArrayList<Data> datos = new ArrayList<Data>();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setting task with spinner and veil 
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6)");
        /*Task<ArrayList<Data>> nuestroTask = new Task<ArrayList<Data>>() {
            @Override
            protected ArrayList<Data> call() throws Exception {
                ArrayList<Data> auxil = new ArrayList<Data>();
                auxil.add(new Data(1995));
                System.out.println(auxil.toString());
                auxil.add(new Data(1999));

                System.out.println(auxil.toString());
                auxil.add(new Data(2003));

                System.out.println(auxil.toString());
                auxil.add(new Data(2007));

                System.out.println(auxil.toString());
                auxil.add(new Data(2011));

                System.out.println(auxil.toString());
                auxil.add(new Data(2015));
                return auxil;
            }
        };

        nuestroTask.valueProperty().addListener(new ChangeListener<ArrayList<Data>>() {
            @Override
            public void changed(ObservableValue<? extends ArrayList<Data>> obs, ArrayList<Data> oldValue, ArrayList<Data> newValue) {
                datos = newValue;
                System.out.println(datos.toString());
            }
        });
        veil.visibleProperty().bind(nuestroTask.runningProperty());
        spinner.visibleProperty().bind(nuestroTask.runningProperty());
        Thread th = new Thread(nuestroTask);
        th.setDaemon(true);
        th.start();*/

 /*Task<List<ElectionResults>> task = new DataTask();
        
        new Thread(task).start();*/
        datos.add(new Data(1995));
        datos.add(new Data(1999));
        datos.add(new Data(2003));
        datos.add(new Data(2007));
        datos.add(new Data(2011));
        datos.add(new Data(2015));
        
        veil.setVisible(false);
        spinner.setVisible(false);
        Platform.runLater(() -> {
            year = Integer.parseInt(tabPane.getSelectionModel().getSelectedItem().getText());
            int i = 0;
            for (Tab t : tabPane.getTabs()) {
                try {
                    parseInt(t.getText());
                    dController = new DashboardController(datos.get(i));
                    t.setContent(dController);
                    i++;
                } catch (NumberFormatException e) {

                }

            }
        });
    }
}
