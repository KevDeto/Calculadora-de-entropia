package Proyecto01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Calcular {
	private String texto;
	private ArrayList<String> listaLetras;
	private ArrayList<Integer> listaCantRep;
	private ArrayList<Double> listaProbSimbolo;
	private ArrayList<Double> listaCantBitSimbolo;
	private ArrayList<Double> listaSolProbPorBits;
	private Double sumaFinal;

	public Calcular() {
	}

	public Calcular(String texto) {
		this.texto = texto;
		this.listaLetras = new ArrayList<>();
		this.listaCantRep = new ArrayList<>();
		this.listaProbSimbolo = new ArrayList<>();
		this.listaCantBitSimbolo = new ArrayList<>();
		this.listaSolProbPorBits = new ArrayList<>();
		this.sumaFinal = 0.0;
	}

	public void separarCadena() {
		for (int i = 0; i < devolverTexto().length(); i++) {
			devolverListaLetras().add(String.valueOf(devolverTexto().charAt(i)));
		}
	}

	private void quitarRepetidas() {
		ArrayList<String> auxList = new ArrayList<>(devolverListaLetras());
		Set<String> listaSinRepe = new HashSet<>(auxList);
		auxList.clear();
		auxList.addAll(listaSinRepe);
		devolverListaLetras().clear();
		devolverListaLetras().addAll(auxList);
	}

	public void contarRepePorLetra() {
		quitarRepetidas();
		for (int i = 0; i < devolverListaLetras().size(); i++) {
			Integer cant = 0;
			for (int j = 0; j < devolverTexto().length(); j++) {
				if (devolverListaLetras().get(i).equals(String.valueOf(devolverTexto().charAt(j)))) {
					cant++;
				}
			}
			devolverListaCantRep().add(cant);
		}
	}

	public void calcularProbabilidadSimbolo() {
		for (int i = 0; i < devolverListaLetras().size(); i++) {
			double cantRepe = devolverListaCantRep().get(i);
			double probDiv = cantRepe / devolverTexto().length();
			Double probabilidad = probDiv;
			devolverListaProbSimbolo().add(probabilidad);
		}
	}

	public void calcularCantBitsPorSimbolo() {
		for (int i = 0; i < devolverListaLetras().size(); i++) {
			Double probabilidad = devolverListaProbSimbolo().get(i);
			Double logaritmo = log2(probabilidad, 2);
			devolverListaCantBitSimbolo().add(logaritmo);
		}
	}

	private Double log2(Double num, int base) {
		return (Math.log10(num) / Math.log10(base));
	}

	public void calcularProbPorCantBits() {
		for (int i = 0; i < devolverListaLetras().size(); i++) {
			Double sol = devolverListaProbSimbolo().get(i) * devolverListaCantBitSimbolo().get(i);
			devolverListaSolProbPorBits().add(sol);
		}
	}

	public void calcularSumarResultado() {
		for (int i = 0; i < devolverListaLetras().size(); i++) {
			sumaFinal(devolverListaSolProbPorBits().get(i));
		}
	}

	public String devolverTexto() {
		return this.texto;
	}

	public ArrayList<String> devolverListaLetras() {
		return this.listaLetras;
	}

	public ArrayList<Integer> devolverListaCantRep() {
		return this.listaCantRep;
	}

	public ArrayList<Double> devolverListaProbSimbolo() {
		return this.listaProbSimbolo;
	}

	public ArrayList<Double> devolverListaCantBitSimbolo() {
		return this.listaCantBitSimbolo;
	}

	public ArrayList<Double> devolverListaSolProbPorBits() {
		return this.listaSolProbPorBits;
	}

	public Double sumaFinal(Double n) {
		return this.sumaFinal += n;
	}

	public Double devolverSumaFinal() {
		return -(this.sumaFinal);
	}

}