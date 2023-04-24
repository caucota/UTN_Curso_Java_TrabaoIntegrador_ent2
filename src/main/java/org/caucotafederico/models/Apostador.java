package org.caucotafederico.models;

import java.util.Collection;
import java.util.HashMap;

public class Apostador {
	private String nombre;
	private HashMap<Integer, Integer> aciertosPorRonda;
	
	public Apostador(String nombre) {
		this.nombre = nombre;
		this.aciertosPorRonda = new HashMap<Integer, Integer>();
	}
	
	public final String getNombre() {
		return nombre;
	}
	public final void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public final HashMap<Integer, Integer> getAciertosPorRonda() {
		return aciertosPorRonda;
	}
	public final void setAciertosPorRonda(HashMap<Integer, Integer> aciertosPorRonda) {
		this.aciertosPorRonda = aciertosPorRonda;
	}
	
	public int puntosTotales(Puntaje puntajes, HashMap<Integer, Integer> aciertosMaxPorRonda, int aciertosMaxPorFase)  {
		Integer cantAciertosRonda = 0;
		int puntosDeLaRonda = 0;
		int puntos = 0;
		Collection<Integer> rondasApost = this.aciertosPorRonda.keySet();
		for(int ronda : rondasApost ) {
			cantAciertosRonda = this.aciertosPorRonda.get(ronda);
			if (cantAciertosRonda == null) {
				cantAciertosRonda = 0;
			}
			puntosDeLaRonda = puntosDeLaRonda + cantAciertosRonda * puntajes.getPuntosPorAcierto();
			if (puntosDeLaRonda == aciertosMaxPorRonda.get(ronda)) {
				puntosDeLaRonda = puntosDeLaRonda + puntajes.getPuntosExtrasPorRondaCompleta();
			}
			puntos = puntos + puntosDeLaRonda;
			puntosDeLaRonda = 0;
		}
		if(aciertosMaxPorFase <= this.cantidadAciertos()) {
			puntos = puntos + puntajes.getPuntosExtrasPorFaseCompleta();
		}
			
		return puntos;
	}
	
	public int cantidadAciertos() {
		Integer cantAciertosRonda = 0;
		int cantTotal = 0;
		Collection<Integer> rondasApost = this.aciertosPorRonda.keySet();
		for(int ronda : rondasApost ) {
			cantAciertosRonda = this.aciertosPorRonda.get(ronda);
			if (cantAciertosRonda == null) {
				cantAciertosRonda = 0;
			}
			cantTotal = cantTotal + cantAciertosRonda;
		}
		return cantTotal;
	}
	
	public int listarPuntosPorRonda(Puntaje puntajes, HashMap<Integer, Integer> aciertosMaxPorRonda)  {
		int puntos = 0;
		Integer aciertoDeLaRonda = 0;
		int cantAciertosTotales = 0;
		int puntosDeLaRonda = 0;
		int puntosAdicionalesRonda = 0;
		System.out.println("--------------------------------");
		System.out.println(this.nombre);
		//System.out.println("--------------------------------");
		Collection<Integer> rondasApost = this.aciertosPorRonda.keySet();
		for(int ronda : rondasApost ) {
			puntosAdicionalesRonda = 0;
			aciertoDeLaRonda = this.aciertosPorRonda.get(ronda);
			if (aciertoDeLaRonda == null) {
				aciertoDeLaRonda = 0;
			}
			puntosDeLaRonda = aciertoDeLaRonda * puntajes.getPuntosPorAcierto();
			
			puntos = puntos + puntosDeLaRonda;
			if (aciertoDeLaRonda == aciertosMaxPorRonda.get(ronda)) {
				puntosAdicionalesRonda = puntajes.getPuntosExtrasPorRondaCompleta();
			}
			cantAciertosTotales = cantAciertosTotales + aciertoDeLaRonda;
			//System.out.println("Ronda " + ronda +  " --> " + resultadosPorRonda);
			System.out.println("Ronda " + ronda +  " - puntos --> " +  puntosDeLaRonda);
			if (puntosAdicionalesRonda > 0) {
				System.out.println("Ronda " + ronda +  " - Ptos.Adic.--> " +  puntosAdicionalesRonda);
				puntos = puntos + puntosAdicionalesRonda;
			}
		}
		//System.out.println("--------------------------------");
		System.out.println("                                  ");
		System.out.println("Aciertos Totales :" + cantAciertosTotales);
		System.out.println("Puntos Totales   :" + puntos);
		System.out.println("--------------------------------");
		return puntos;
	}

}
