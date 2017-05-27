
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
import java.util.List;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //Setting task with spinner and veil 
        veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6)");
        Task<List<ElectionResults>> task = new DataTask();
        veil.visibleProperty().bind(task.runningProperty());
        spinner.visibleProperty().bind(task.runningProperty());
        new Thread(task).start();

        //Get ElectionResults of the year of the selectedtab
        year = Integer.parseInt(tabPane.getSelectionModel().getSelectedItem().getText());
        /*for (ElectionResults e : task.getValue()) {
            if (e.getYear() == year) {
                electionResults = e;
            }
        }*/
        //tab1999.setContent((Node) FXMLLoader.load(this.getClass().getResource("Sample.fxml")));
        for (Tab t : tabPane.getTabs()) {
            DashboardController c;
            
            try {
                parseInt(t.getText());
                c = new DashboardController();                
                t.setContent(c);
            } catch (NumberFormatException e) {} 

        }
    }
}
