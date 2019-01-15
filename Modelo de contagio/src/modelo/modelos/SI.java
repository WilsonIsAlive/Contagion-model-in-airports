package modelo.modelos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import modelo.red.Nodo;
import modelo.red.Red;

public class SI implements Modelo {
	private ArrayList<Nodo> nodosContagiadosFin;
	private ArrayList<AristaContagiadaSimple> aristasHanProvocadoInfeccion;
	private Red red;
	private double tasaContagio;

	private ArrayList<ArrayList<Integer>> nodosSusceptibles;
	// ej pos0 -> array con todos los aeropuertos susceptibles en el instante 0
	private ArrayList<ArrayList<Integer>> nodosInfectados; 
	// ej pos0 -> array con todos los aeropuertos infectados en el instante 0

	public SI(Red red, double tasaContagio) {
		this.red = red;
		this.tasaContagio = tasaContagio;
		nodosSusceptibles = new ArrayList<>();
		nodosInfectados = new ArrayList<>();
	}

	/**
	 * Modelo de contagio simple tasa de contagio = 0.6
	 */
	@Override
	public void simular(Nodo foco) {
		nodosContagiadosFin = new ArrayList<>();
		aristasHanProvocadoInfeccion = new ArrayList<>();

		nodosContagiadosFin.add(foco);

		foco.setInfectado(true);

		int instante = 0;

		ArrayList<Integer> instanteCero = new ArrayList<Integer>();
		instanteCero.add(foco.getId());
		nodosInfectados.add(instante, instanteCero);

		while (nodosInfectados.get(instante) != null && nodosInfectados.get(instante).size() > 0) {

			ArrayList<Integer> nodosInfectadosInstante = new ArrayList<>();

			for (Integer i : nodosInfectados.get(instante)) {
				HashMap<Integer, Integer> listaAeropuertosALosQueVuela = red.getNodo(i).getAeropuertosALosQueVuela();
				for (Map.Entry<Integer, Integer> entry : listaAeropuertosALosQueVuela.entrySet()) {
					Random r = new Random();
					Nodo aux = red.getNodos().get(entry.getKey());
					if (r.nextDouble() < this.tasaContagio && !aux.isInfectado()) {
						this.aristasHanProvocadoInfeccion
								.add(new AristaContagiadaSimple(red.getNodo(i).getId(), aux.getId(), 1));
						nodosContagiadosFin.add(aux);
						aux.setInfectado(true);
						nodosInfectadosInstante.add(aux.getId());
					}
				}

			}
			instante += 1;
			nodosInfectados.add(instante, nodosInfectadosInstante);

		}

		System.out.println("PAUSA");
	}

	@Override
	public ArrayList<Nodo> getNodosContagiados() {
		return this.nodosContagiadosFin;
	}

	@Override
	public ArrayList<AristaContagiadaSimple> getAristasContagiadas() {
		return this.aristasHanProvocadoInfeccion;
	}

	private void actualizarNodosInfectados(int instante) {
		ArrayList<Integer> nodosInfectadosEnInstante = new ArrayList<>();
		for (Nodo n : this.nodosContagiadosFin) {
			nodosInfectadosEnInstante.add(n.getId());
		}
		this.nodosInfectados.add(instante, nodosInfectadosEnInstante);

	}

	private void actualizarNodosSusceptibles(int instante) {
		ArrayList<Integer> nodosSusceptiblesEnInstante = new ArrayList<>();
		for (Nodo nodo : this.red.getNodos()) {
			if (!nodosContagiadosFin.contains(nodo)) {
				nodosSusceptiblesEnInstante.add(nodo.getId());
			}
		}

	}

	@Override
	public ArrayList<ArrayList<Integer>> getInfeccionTiempo() {
		return this.nodosInfectados;
	}

}