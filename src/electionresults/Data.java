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

    private ElectionResults electionresults;
    private ObservableList<PieChart.Data> pieData;
    private XYChart.Series<Double, Double> barData;
    private RegionResults cvinfo;
    private Set<String> valencia = new HashSet<String>();
    private Set<String> castellon = new HashSet<String>();
    private Set<String> alicante = new HashSet<String>();
    private Set<RegionResults> resultadosregion = new HashSet<RegionResults>();
    private Set<RegionResults> resultadosprovincia = new HashSet<RegionResults>();
    
    public Data(int year) {
        electionresults = DataAccessLayer.getElectionResults(year);
        cvinfo = electionresults.getGlobalResults();
        for (Map.Entry<String, String> entry : electionresults.getRegionProvinces().entrySet()) {
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
