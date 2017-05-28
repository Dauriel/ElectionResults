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
    private ArrayList<String> partidosenOrdenGlobal = new ArrayList();
    private ArrayList<String> partidosenOrdenValencia = new ArrayList();
    private ArrayList<String> partidosenOrdenCastellon = new ArrayList();
    private ArrayList<String> partidosenOrdenAlicante = new ArrayList();
    private Map<String, Integer> resultadosGlobales = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosValencia = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosAlicante = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosCastellon = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoGlobal = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoValencia = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoCastellon = new HashMap<String, Integer>();
    private Map<String, Integer> partidoAnyoAlicante = new HashMap<String, Integer>();
    private Map<Map<String, ArrayList<String>>, Map<String, Integer>> comarcaValencia = new HashMap<Map<String,ArrayList<String>>,Map<String, Integer>>();
    private Map<Map<String, ArrayList<String>>, Map<String, Integer>> comarcaAlicante = new HashMap<Map<String,ArrayList<String>>,Map<String, Integer>>();
    private Map<Map<String, ArrayList<String>>, Map<String, Integer>> comarcaCastellon = new HashMap<Map<String,ArrayList<String>>,Map<String, Integer>>();
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
            partidosenOrdenGlobal.add(p.getParty());
            resultadosGlobales.put(p.getParty(), p.getSeats());
            partidoAnyoGlobal.put(p.getParty(), p.getVotes());
        }

        for (PartyResults p : electionResult.getProvinceResults("Valencia").getPartyResultsSorted()) {
            partidosenOrdenValencia.add(p.getParty());
            partidoAnyoValencia.put(p.getParty(), p.getVotes());
            resultadosValencia.put(p.getParty(), p.getSeats());
        }
        for (PartyResults p : electionResult.getProvinceResults("Castellón").getPartyResultsSorted()) {
            partidosenOrdenCastellon.add(p.getParty());
            partidoAnyoCastellon.put(p.getParty(), p.getVotes());
            resultadosCastellon.put(p.getParty(), p.getSeats());
        }
        for (PartyResults p : electionResult.getProvinceResults("Alicante").getPartyResultsSorted()) {
            partidosenOrdenAlicante.add(p.getParty());
            partidoAnyoAlicante.put(p.getParty(), p.getVotes());
            resultadosAlicante.put(p.getParty(), p.getSeats());
        }
        for (String s : valencia){
            RegionResults r = electionResult.getRegionResults(s);
            Map<String, Integer> aux = new HashMap<String, Integer>();
            Map<String, ArrayList<String>> aux3 = new HashMap<String, ArrayList<String>>();
            ArrayList<String> aux2= new ArrayList<String>();
            for(PartyResults p : r.getPartyResultsSorted()){
               aux.put(p.getParty(), p.getVotes());
               aux2.add(p.getParty());
            }
            aux3.put(s, aux2);
            comarcaValencia.put(aux3, aux);
        }
        for (String s : alicante){
            RegionResults r = electionResult.getRegionResults(s);
            Map<String, Integer> aux = new HashMap<String, Integer>();
            Map<String, ArrayList<String>> aux3 = new HashMap<String, ArrayList<String>>();
            ArrayList<String> aux2= new ArrayList<String>();
            for(PartyResults p : r.getPartyResultsSorted()){
               aux.put(p.getParty(), p.getVotes());
               aux2.add(p.getParty());
            }
            aux3.put(s, aux2);
            comarcaAlicante.put(aux3, aux);
        }
        for (String s : castellon){
            RegionResults r = electionResult.getRegionResults(s);
            Map<String, Integer> aux = new HashMap<String, Integer>();
            Map<String, ArrayList<String>> aux3 = new HashMap<String, ArrayList<String>>();
            ArrayList<String> aux2= new ArrayList<String>();
            for(PartyResults p : r.getPartyResultsSorted()){
               aux.put(p.getParty(), p.getVotes());
               aux2.add(p.getParty());
            }
            aux3.put(s, aux2);
            comarcaCastellon.put(aux3, aux);
        }
    }

    public Map<Map<String, ArrayList<String>>, Map<String, Integer>> getComarcaValencia() {
        return comarcaValencia;
    }

    public Map<Map<String, ArrayList<String>>, Map<String, Integer>> getComarcaAlicante() {
        return comarcaAlicante;
    }

    public Map<Map<String, ArrayList<String>>, Map<String, Integer>> getComarcaCastellon() {
        return comarcaCastellon;
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

    public ObservableList<PieChart.Data> getPieData(Map<String, Integer> aux, ArrayList<String> orden) {
        pieData = FXCollections.observableArrayList();
        for (int i = 0; i < orden.size(); i++) {
            String partido = orden.get(i);
            int seats = aux.get(partido);
            if (seats > 0) {
                pieData.add(new PieChart.Data(partido + " (" + seats + ")", seats));
            }
        }
        /*for (String s : aux.keySet()) {
            int seats = aux.get(s);
            pieData.add(new PieChart.Data(s + " (" + seats + ")",seats));
        }*/
        return pieData;
    }

    public ObservableList<XYChart.Series<String, Integer>> getBarData(Map<String, Integer> aux, ArrayList<String> orden) {
        ObservableList<XYChart.Series<String, Integer>> auxList = FXCollections.observableArrayList();
        for (int i = 0; i < orden.size(); i++) {
            barData = new XYChart.Series<String, Integer>();
            String partido = orden.get(i);
            int votos = aux.get(partido);
            if (votos > 0) {
                barData.setName(partido);
                barData.getData().add(new XYChart.Data("" + x, votos));                
                auxList.add(barData);
            }            
        }
        
        /*for (String s : aux.keySet()) {
            barData = new XYChart.Series<String, Integer>();
            int votos = aux.get(s);
            barData.setName(s);
            barData.getData().add(new XYChart.Data(""+x, votos));
            auxList.add(barData);
        }*/
        return auxList;
    }

    public ArrayList<String> getPartidosenOrdenGlobal() {
        return partidosenOrdenGlobal;
    }

    public ArrayList<String> getPartidosenOrdenValencia() {
        return partidosenOrdenValencia;
    }

    public ArrayList<String> getPartidosenOrdenCastellon() {
        return partidosenOrdenCastellon;
    }

    public ArrayList<String> getPartidosenOrdenAlicante() {
        return partidosenOrdenAlicante;
    }
    
    

}
