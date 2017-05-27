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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
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
    private XYChart.Series<String, Integer> barData;
    private RegionResults cvinfo;
    private BarChart<String, Integer> bar;

    private Set<String> valencia = new HashSet<String>();
    private Set<String> castellon = new HashSet<String>();
    private Set<String> alicante = new HashSet<String>();
    private List<PartyResults> listaResultadosGlobales;
    private Map<String, Integer> resultadosGlobales = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosValencia = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosAlicante = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosCastellon = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoGlobal = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoValencia = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoCastellon = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoAlicante = new HashMap<String, Integer>();
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
            partidoAnyoGlobal.put(p.getParty(),p.getVotes());
        }
        
        for (PartyResults p : electionResult.getProvinceResults("Valencia").getPartyResultsSorted()) {
            partidoAnyoGlobal.put(p.getParty(),p.getVotes());
            resultadosValencia.put(p.getParty(), p.getSeats());
        }
        for (PartyResults p : electionResult.getProvinceResults("Castellón").getPartyResultsSorted()) {
            partidoAnyoGlobal.put(p.getParty(),p.getVotes());
            resultadosCastellon.put(p.getParty(), p.getSeats());
        }
        for (PartyResults p : electionResult.getProvinceResults("Alicante").getPartyResultsSorted()) {
            partidoAnyoGlobal.put(p.getParty(),p.getVotes());
            resultadosAlicante.put(p.getParty(), p.getSeats());
        }
    }


    public Map<String, Integer> getPartidoAnyoGlobal() {
        return partidoAnyoGlobal;
    }

    public Map<String, Integer> getPartidoAnyoValencia() {
        return partidoAnyoValencia;
    }

    public Map<String, Integer> getPartidoAnyoCastellon() {
        return partidoAnyoCastellon;
    }

    public Map<String, Integer> getPartidoAnyoAlicante() {
        return partidoAnyoAlicante;
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

    public ObservableList<XYChart.Series<String,Integer>> getBarData(Map<String, Integer> aux) {  
        ObservableList<XYChart.Series<String,Integer>> auxList= FXCollections.observableArrayList();
        for (String s : aux.keySet()) {
            barData = new XYChart.Series<String, Integer>();
            int votos = aux.get(s);
            barData.setName(s);
            barData.getData().add(new XYChart.Data(""+x, votos));
            auxList.add(barData);
        }
        return auxList;
    }
    
    
}
