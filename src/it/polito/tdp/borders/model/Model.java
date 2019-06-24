package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	Map<Integer,Country> MappaIdCountry;
	BordersDAO dao ;
	Graph<Country, DefaultEdge> grafo;
	
	public Model() {
		
		MappaIdCountry = new HashMap<Integer, Country>();
		dao = new BordersDAO();
		grafo = new SimpleGraph<>(DefaultEdge.class);
	}
	public void CreaGrafo(int anno) {
		List<Country> listapaesi = new ArrayList<Country>(dao.loadAllCountries());
	
	
	for(Country c : listapaesi) {
		
		MappaIdCountry.put(c.getcCode(), c);
		
	}
	
	Graphs.addAllVertices(grafo, dao.loadCountriesPerAnno(anno, MappaIdCountry));
	
	for(Confine conf : dao.loadCOnfini(anno, MappaIdCountry)) {
		if(!grafo.containsEdge(conf.getC1(),conf.getC2())) {
			
			grafo.addEdge(conf.getC1(), conf.getC2());
			
			
		}
		
		
		
	}
	
	System.out.format("creato grafo con %d vertici e %d archi", grafo.vertexSet().size(),grafo.edgeSet().size());
	
	
	
	
	
	
	}
	
	public List<Country> numvicini( ) {
		
		List<Country> paesi = new ArrayList<>();
		for(Country ctemp : grafo.vertexSet()) {
			
			List<Country> vicini = new ArrayList<>(Graphs.neighborListOf(grafo, ctemp));
			
			ctemp.setNumvicni(vicini.size());
			
			paesi.add(ctemp);
			
		}
		Collections.sort(paesi);
		
		return paesi;
		
		
		
	}
	
	public List<Country> getPaesi(){
		 List<Country> list = new ArrayList<>(grafo.vertexSet());
		return list;
	}
	
}
