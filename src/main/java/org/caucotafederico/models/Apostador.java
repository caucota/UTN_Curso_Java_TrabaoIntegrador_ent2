package org.caucotafederico.models;

import java.util.Collection;
import java.util.HashMap;

public class Apostador {
	private String nombre;
	private HashMap<Integer, Integer> resultadosPorRonda;
	
	public Apostador(String nombre) {
		this.nombre = nombre;
		this.resultadosPorRonda = new HashMap<Integer, Integer>();
	}
	
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public final HashMap<Integer, Integer> getResultadosPorRonda() {
		return resultadosPorRonda;
	}
	public final void setResultadosPorRonda(HashMap<Integer, Integer> resultadosPorRonda) {
		this.resultadosPorRonda = resultadosPorRonda;
	}
	
	public int puntosTotales() {
		int puntos = 0;
		Collection<Integer> rondasApost = this.resultadosPorRonda.keySet();
		for(int ronda : rondasApost ) {
			puntos = puntos + this.resultadosPorRonda.get(ronda);
		}
		return puntos;
	}
	
	public int verPuntosPorRonda() {
		int puntos = 0;
		System.out.println("--------------------------------");
		System.out.println(this.nombre);
		//System.out.println("--------------------------------");
		Collection<Integer> rondasApost = this.resultadosPorRonda.keySet();
		for(int ronda : rondasApost ) {
			puntos = puntos + this.resultadosPorRonda.get(ronda);
			//System.out.println("Ronda " + ronda +  " --> " + resultadosPorRonda);
			System.out.println("Ronda " + ronda +  " --> " +  this.resultadosPorRonda.get(ronda));
		}
		//System.out.println("--------------------------------");
		System.out.println("                                  ");
		System.out.println("Puntos Totales y/o Aciertos :" + puntos);
		System.out.println("--------------------------------");
		return puntos;
	}

}
