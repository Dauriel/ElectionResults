/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electionresults;

import electionresults.model.ElectionResults;
import electionresults.model.Party;
import electionresults.model.PartyResults;
import electionresults.model.RegionResults;
import electionresults.persistence.io.DataAccessLayer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

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
    private Map<String, String> color = new HashMap<String, String>();

    //PRESCINDIBLES 
    private ArrayList<String> partidosenOrdenGlobal = new ArrayList();
    private ArrayList<String> partidosenOrdenValencia = new ArrayList();
    private ArrayList<String> partidosenOrdenCastellon = new ArrayList();
    private ArrayList<String> partidosenOrdenAlicante = new ArrayList();
    private Map<String, Integer> resultadosGlobales = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosValencia = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosAlicante = new HashMap<String, Integer>();
    private Map<String, Integer> resultadosCastellon = new HashMap<String, Integer>();
    //IMPRESCINDIBLES
    private Map<String, List<PartyResults>> general = new HashMap<String, List<PartyResults>>();
    private Map<String, List<PartyResults>> partyResultsCastellon = new HashMap<String, List<PartyResults>>();
    private Map<String, List<PartyResults>> partyComunidades = new HashMap<String, List<PartyResults>>();
    private Map<String, List<PartyResults>> partyResultsValencia = new HashMap<String, List<PartyResults>>();
    private Map<String, List<PartyResults>> partyResultsAlicante = new HashMap<String, List<PartyResults>>();
    private List<PartyResults> partyValencia;
    private List<PartyResults> partyCastellon;
    private List<PartyResults> partyAlicante;
    private int x;
    private List<PartyResults> listaResultadosGlobales;
    private Set<String> valencia = new HashSet<String>();
    private Set<String> castellon = new HashSet<String>();
    private Set<String> alicante = new HashSet<String>();
    private double mediaGeneral, mediaValencia, mediaCastellon, mediaAlicante;
    private RegionResults regionValencia;

    public RegionResults getRegionValencia() {
        return regionValencia;
    }

    public RegionResults getRegionCastellon() {
        return regionCastellon;
    }

    public RegionResults getRegionAlicante() {
        return regionAlicante;
    }
    private RegionResults regionCastellon;
    private RegionResults regionAlicante;

    public List<PartyResults> getPartyValencia() {
        return partyValencia;
    }

    public List<PartyResults> getPartyCastellon() {
        return partyCastellon;
    }

    public List<PartyResults> getPartyAlicante() {
        return partyAlicante;
    }

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
        mediaGeneral = (double) (cvinfo.getPollData().getVotes() * 100) / (cvinfo.getPollData().getCensus());
        general.put("General", cvinfo.getPartyResultsSorted());
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
        }
        regionValencia = electionResult.getProvinceResults("Valencia");
        mediaValencia = (double) (regionValencia.getPollData().getVotes() * 100) / (regionValencia.getPollData().getCensus());
        partyValencia = electionResult.getProvinceResults("Valencia").getPartyResultsSorted();
        for (PartyResults p : partyValencia) {
            partidosenOrdenValencia.add(p.getParty());
            resultadosValencia.put(p.getParty(), p.getSeats());

        }
        regionCastellon = electionResult.getProvinceResults("Castellón");
        mediaCastellon = (double) (regionCastellon.getPollData().getVotes() * 100) / (regionCastellon.getPollData().getCensus());
        partyCastellon = electionResult.getProvinceResults("Castellón").getPartyResultsSorted();
        for (PartyResults p : partyCastellon) {
            partidosenOrdenCastellon.add(p.getParty());
            resultadosCastellon.put(p.getParty(), p.getSeats());
        }
        regionAlicante = electionResult.getProvinceResults("Alicante");
        mediaAlicante = (double) (regionAlicante.getPollData().getVotes() * 100) / (regionAlicante.getPollData().getCensus());
        partyAlicante = electionResult.getProvinceResults("Alicante").getPartyResultsSorted();
        for (PartyResults p : partyAlicante) {
            partidosenOrdenAlicante.add(p.getParty());
            resultadosAlicante.put(p.getParty(), p.getSeats());
        }

        for (String s : valencia) {
            RegionResults r = electionResult.getRegionResults(s);
            partyResultsValencia.put(s, r.getPartyResultsSorted());
        }
        for (String s : alicante) {
            RegionResults r = electionResult.getRegionResults(s);
            partyResultsAlicante.put(s, r.getPartyResultsSorted());
        }
        for (String s : castellon) {
            RegionResults r = electionResult.getRegionResults(s);
            partyResultsCastellon.put(s, r.getPartyResultsSorted());
        }
        partyComunidades.put("Valencia", electionResult.getProvinceResults("Valencia").getPartyResultsSorted());
        partyComunidades.put("Castellon", electionResult.getProvinceResults("Castellón").getPartyResultsSorted());
        partyComunidades.put("Alicante", electionResult.getProvinceResults("Alicante").getPartyResultsSorted());
        
        
    }

    public double getMediaGeneral() {
        return mediaGeneral;
    }

    public double getMediaValencia() {
        return mediaValencia;
    }

    public double getMediaCastellon() {
        return mediaCastellon;
    }

    public double getMediaAlicante() {
        return mediaAlicante;
    }

    public RegionResults getCvinfo() {
        return cvinfo;
    }

    public Map<String, List<PartyResults>> getGeneral() {
        return general;
    }

    public Map<String, List<PartyResults>> getPartyResultsCastellon() {
        return partyResultsCastellon;
    }

    public Map<String, List<PartyResults>> getPartyComunidades() {
        return partyComunidades;
    }

    public Map<String, List<PartyResults>> getPartyResultsValencia() {
        return partyResultsValencia;
    }

    public Map<String, List<PartyResults>> getPartyResultsAlicante() {
        return partyResultsAlicante;
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
                PieChart.Data variable = new PieChart.Data(partido + " (" + seats + ")", seats);                
                variable.nodeProperty().addListener(new ChangeListener<Node>() {
                        @Override
                        public void changed(ObservableValue<? extends Node> ov, Node oldNode, Node newNode) {
                            if (newNode != null) {
                                
                                newNode.setStyle("-fx-pie-color: " + Party.getPartyByName(partido).getHexColor() + ";");                                
                            }

                        }
                    });
                pieData.add(variable);
            }
        }
        return pieData;
    }

    public ObservableList<XYChart.Series<String, Integer>> getBarData(Map<String, List<PartyResults>> aux, String comarca, double percent) {
        ObservableList<XYChart.Series<String, Integer>> auxList = FXCollections.observableArrayList();
        List<PartyResults> auxArray = aux.get(comarca);
        for (PartyResults p : auxArray) {
            if (p.getPercentage() >= percent) {
                barData = new XYChart.Series<String, Integer>();
                barData.setName(p.getParty());
                barData.getData().add(new XYChart.Data("" + x, p.getVotes()));
                barData.getData().forEach(data -> {
                    Label label = new Label(data.getYValue() + "");
                    data.setNode(new StackPane(label));
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: white;");
                });
                auxList.add(barData);
            }
        }
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

    public Map<String, Double> votesPerParty(Set<String> partidos) {
        Map<String, Double> mapFinal = new HashMap<String, Double>();
        for (String aux : partidos) {
            double finalvalue = 0;            
            List<String> acron = new ArrayList<String>();
            if(aux.equals("PP")){
            acron = Party.PP.getAcronyms();
            color.put(aux,Party.PP.getHexColor());
            }if(aux.equals("UV")){
            acron = Party.UV.getAcronyms();
            color.put(aux,Party.UV.getHexColor());
            }if(aux.equals("PSOE")){
            acron = Party.PSOE.getAcronyms();
            color.put(aux,Party.PSOE.getHexColor());
            }if(aux.equals("PODEMOS")){
            acron = Party.PODEMOS.getAcronyms();
            color.put(aux,Party.PODEMOS.getHexColor());
            }if(aux.equals("CS")){
            acron = Party.CS.getAcronyms();
            color.put(aux,Party.CS.getHexColor());
            }if(aux.equals("UPYD")){
            acron = Party.UPYD.getAcronyms();
            color.put(aux,Party.UPYD.getHexColor());
            }if(aux.equals("EU")){
            acron = Party.EU.getAcronyms();
            color.put(aux,Party.EU.getHexColor());
            }if(aux.equals("COMPROMIS")){
            acron = Party.COMPROMIS.getAcronyms();
            color.put(aux,Party.COMPROMIS.getHexColor());
            }
            
            
            for (String s : acron) {
                if(cvinfo.getPartyResults(s) != null){
                finalvalue += cvinfo.getPartyResults(s).getSeats();}         
            }
            if(finalvalue != 0){
            mapFinal.put(aux, finalvalue);
            }
            
        }
        return mapFinal;
    }
    
    public String getColor(String s){
        System.out.println(color.toString());
      return color.get(s);
    }
    /*public String acc(String s){
        
       return partyAc.get(s);
    }*/
}
