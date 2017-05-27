/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import electionresults.model.ElectionResults;
import electionresults.model.PartyResults;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
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
    private List<PartyResults> listaResultadosGlobales;
    private Map<String, Integer> resultadosGlobales = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosValencia = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosAlicante = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosCastellon = new HashMap<String, Integer>();
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

    public List<PartyResults> getListaResultadosGlobales() {
        return listaResultadosGlobales;
    }

    public Data(int year) {
        x = year;
        electionResult = DataAccessLayer.getElectionResults(x);
        comunidad = electionResult.getRegionProvinces().keySet();        
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
        listaResultadosGlobales = cvinfo.getPartyResultsSorted();
        for (PartyResults p : listaResultadosGlobales) {
            resultadosGlobales.put(p.getParty(), p.getSeats());
        }
        
        for (PartyResults p : electionResult.getProvinceResults("Valencia").getPartyResultsSorted()) {
            resultadosValencia.put(p.getParty(), p.getSeats());
        }
        for (PartyResults p : electionResult.getProvinceResults("Castellón").getPartyResultsSorted()) {
            resultadosCastellon.put(p.getParty(), p.getSeats());
        }
        for (PartyResults p : electionResult.getProvinceResults("Alicante").getPartyResultsSorted()) {
            resultadosAlicante.put(p.getParty(), p.getSeats());
        }
    }

    public Map<String, Integer> getResultadosValencia() {
        return resultadosValencia;
    }

    public Map<String, Integer> getResultadosAlicante() {
        return resultadosAlicante;
    }

    public Map<String, Integer> getResultadosCastellon() {
        return resultadosCastellon;
    }

    public Map<String, Integer> getResultadosGlobales() {
        return resultadosGlobales;
    }

    public ObservableList<PieChart.Data> getPieData(Map<String, Integer> aux) {
        pieData = FXCollections.observableArrayList();
        for (String s : aux.keySet()) {
            int seats = aux.get(s);
            pieData.add(new PieChart.Data(s + " (" + seats + ")",seats));
        }
        return pieData;
    }
}
