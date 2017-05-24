/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import electionresults.model.ElectionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.util.List;
import javafx.concurrent.Task;

/**
 *
 * @author Ian Ward
 */
public class DataTask extends Task<List<ElectionResults>>{
    
    @Override
    protected List<ElectionResults> call() throws Exception {       
        return generateData();
    }
    public static List<ElectionResults> generateData() {
        return  DataAccessLayer.getAllElectionResults();
    }
}
