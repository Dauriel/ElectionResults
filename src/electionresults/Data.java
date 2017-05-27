/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import electionresults.model.ElectionResults;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author PABLO
 */
public class Data {
    private ElectionResults electionResult;
    private Set<String> comunidad = new HashSet<String>();
    private ObservableList<PieChart.Data> pieData;
    private XYChart.Series<Double, Double> barData;
    private RegionResults cvinfo;
    
    private Set<String> valencia = new HashSet<String>();
    private Set<String> castellon = new HashSet<String>();
    private Set<String> alicante = new HashSet<String>();
    private int x;

    public Set<String> getValencia() {
        return valencia;
    }
    public Set<String> getCastellon() {
        return castellon;
    }
    public Set<String> getAlicante() {
        return alicante;
    }
    

    public Set<String> getComunidad() {
        return comunidad;
    }

    public Data(int year) {
        x = year;
        electionResult = DataAccessLayer.getElectionResults(x);
        comunidad = electionResult.getProvinces().keySet();

        cvinfo = electionResult.getGlobalResults();
        for (Map.Entry<String, String> entry : electionResult.getRegionProvinces().entrySet()) {
            String aux = entry.getValue();
            if (aux.equals("Valencia")) {
                valencia.add(entry.getKey());
            } else if (aux.equals("Castellón")) {
                castellon.add(entry.getKey());
            } else {
                alicante.add(entry.getKey());
            }
        }
        
        

    }
}
