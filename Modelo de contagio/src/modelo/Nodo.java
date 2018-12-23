package modelo;

import java.util.HashMap;

public class Nodo {
	private int id;
	private int degree;
	private int outdegree;
	private int indegree;
	private TAirport tAirport;
	private Double umbral;
	//PARA LA INFECCI�N
	HashMap<Integer, Integer> aeropuertos_comunicados; //<IDAeropuerto, Peso>
	private Integer aeropuetosComunicadosInfectados = 0;
	
	public Nodo(int i, int g, Double umbral, TAirport info, int indegree, int outdegree) {
		this.id = i;
		this.degree = g;
		this.tAirport = info;
		this.umbral = umbral;
		this.indegree = indegree;
		this.outdegree = outdegree;
		aeropuertos_comunicados = new HashMap<Integer, Integer>();
	}
	
	public void addAeropuertoComunicado(Integer aeropuerto, Integer peso) {
		this.aeropuertos_comunicados.put(aeropuerto, peso);
	}
	
	public Integer getPesoAeropuertoComunicado(Integer aeropuerto) {
		return aeropuertos_comunicados.get(aeropuerto);
	}
	
	public int getValue() {
		return id;
	}
	public void setValue(int value) {
		this.id = value;
	}
	public int getDegree() {
		return degree;
	}
	public void setDegree(int degree) {
		this.degree = degree;
	}
	
	public void incrementDegree() {
		this.degree += 1;
	}
	
	@Override
	public boolean equals(Object o) {
		return o != null &&
				o instanceof Nodo && ((Nodo) o).id == this.id && ((Nodo) o).degree == this.degree
				&& ((Nodo) o).tAirport.getIata() == this.tAirport.getIata()
				&& ((Nodo) o).tAirport.getName() == this.tAirport.getName();
	}
	
	@Override
	public String toString() {
		return this.tAirport.getName() + " " + this.tAirport.getIata() + " idNodo " + 
	this.id + " : " + " grado " + this.degree;
	}

	public Double getUmbral() {
		return umbral;
	}

	public void setUmbral(Double umbral) {
		this.umbral = umbral;
	}
	public TAirport getInfo() {
		return this.tAirport;
	}
	public void setInfoAeropuertos(TAirport info) {
		this.tAirport = info;
	}

	public int getOutdegree() {
		return outdegree;
	}

	public void setOutdegree(int outdegree) {
		this.outdegree = outdegree;
	}

	public int getIndegree() {
		return indegree;
	}

	public void setIndegree(int indegree) {
		this.indegree = indegree;
	}

	public Integer getAeropuetosComunicadosInfectados() {
		return aeropuetosComunicadosInfectados;
	}

	public void setAeropuetosComunicadosInfectados(Integer aeropuetosComunicadosInfectados) {
		this.aeropuetosComunicadosInfectados = aeropuetosComunicadosInfectados;
	}
}
