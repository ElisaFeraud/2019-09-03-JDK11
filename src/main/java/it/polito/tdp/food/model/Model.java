package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao;
	Map<String,TypePortion> idMap;
	Graph<TypePortion,DefaultWeightedEdge> grafo;
	List<TypePortion> best;
	int pesoDaStampare;
	public Model() {
		dao = new FoodDao();
		
	}
	public void creaGrafo(int calorie) {
		idMap= new HashMap<>();
		dao.getVertici(idMap, calorie);
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
    	Graphs.addAllVertices(grafo, idMap.values());
    	for(Collegamento c: dao.getArchi(calorie,idMap)) {
    		if(this.grafo.containsVertex(c.getTp1()) && this.grafo.containsVertex(c.getTp2())) {
    			DefaultWeightedEdge e = this.grafo.getEdge(c.getTp1(),c.getTp2());
    			if(e==null) {
    				Graphs.addEdgeWithVertices(grafo,c.getTp1(),c.getTp2(),c.getPeso());
    			}
    		}
    	}

	}
	 public String infoGrafo() {
		 return "Grafo creato con "+ this.grafo.vertexSet().size()+ " vertici e " + this.grafo.edgeSet().size()+" archi\n";
	 }
	 public List<Corrispondenza> getNumberOfConnectedComponents(TypePortion tp) {
			if (grafo == null) {
				throw new RuntimeException("Grafo non esistente");
			}
			ConnectivityInspector<TypePortion, DefaultWeightedEdge> ci = new ConnectivityInspector<TypePortion, DefaultWeightedEdge>(grafo);
			List<TypePortion> tpr = new LinkedList<>();
			for(TypePortion t : ci.connectedSetOf(tp)) {
				tpr.add(t);
			}
			List<Corrispondenza> result = new LinkedList<>();
			for(TypePortion t: tpr) {
				for(DefaultWeightedEdge d: this.grafo.edgesOf(tp)) {
					int peso = (int) this.grafo.getEdgeWeight(d);
					Corrispondenza corrispondenza = new Corrispondenza(t,peso);
					result.add(corrispondenza);
				}
			}
			
			return result;
		}
	 public List<TypePortion> getPortionVertici(){
		 List<TypePortion> result= new LinkedList<>(this.grafo.vertexSet());
		 return result;
	 }
	 public List<TypePortion> camminoMassimo(int N, TypePortion tpStart) {
			List<TypePortion> parziale = new ArrayList<>();
			parziale.add(tpStart);

			this.best = new ArrayList<>();
			best.add(tpStart);

			cerca(parziale, 1, N);

			return best;

		}

	 private void cerca(List<TypePortion> parziale, int livello, int N) {
			if (livello == N) {
				// caso terminale
				if (peso(parziale) > peso(best)) {
					best = new ArrayList<>(parziale);
					System.out.println(parziale);
				}
				return;
			}
			// trova vertici adiacenti all'ultimo
			TypePortion ultimo = parziale.get(parziale.size() - 1);

			List<TypePortion> adiacenti = Graphs.neighborListOf(this.grafo, ultimo);

			for (TypePortion prova : adiacenti) {
				if (!parziale.contains(prova)) {
					parziale.add(prova);
					cerca(parziale, livello + 1, N);
					parziale.remove(parziale.size() - 1);
				}
			}

		}
	 private int peso(List<TypePortion> parziale) {
			int peso = 0;
			for (int i = 0; i < parziale.size() - 1; i++) {
				DefaultWeightedEdge e = grafo.getEdge(parziale.get(i), parziale.get(i + 1));
				int pesoarco = (int) grafo.getEdgeWeight(e);
				peso += pesoarco;
			}
			pesoDaStampare=peso;
			return peso;
		}
	 public int stampaPeso() {
		 return pesoDaStampare;
	 }
}
