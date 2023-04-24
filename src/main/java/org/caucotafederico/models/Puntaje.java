package org.caucotafederico.models;

public class Puntaje {
	private int puntosPorAcierto;
	private int puntosExtrasPorRondaCompleta;
	private int puntosExtrasPorFaseCompleta;
	public Puntaje(int puntosPorAcierto, int puntosExtrasPorRondaCompleta, int puntosExtrasPorFaseCompleta) {
		this.setPuntosPorAcierto(puntosPorAcierto);
		this.setPuntosExtrasPorRondaCompleta(puntosExtrasPorRondaCompleta);
		this.setPuntosExtrasPorFaseCompleta(puntosExtrasPorFaseCompleta);
	}
	public int getPuntosPorAcierto() {
		return puntosPorAcierto;
	}
	public void setPuntosPorAcierto(int puntosPorAcierto) {
		this.puntosPorAcierto = puntosPorAcierto;
	}
	public int getPuntosExtrasPorRondaCompleta() {
		return puntosExtrasPorRondaCompleta;
	}
	public void setPuntosExtrasPorRondaCompleta(int puntosExtrasPorRondaCompleta) {
		this.puntosExtrasPorRondaCompleta = puntosExtrasPorRondaCompleta;
	}
	public int getPuntosExtrasPorFaseCompleta() {
		return puntosExtrasPorFaseCompleta;
	}
	public void setPuntosExtrasPorFaseCompleta(int puntosExtrasPorFaseCompleta) {
		this.puntosExtrasPorFaseCompleta = puntosExtrasPorFaseCompleta;
	}

}
