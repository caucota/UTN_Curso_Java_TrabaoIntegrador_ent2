package org.caucotafederico.models;

import org.caucotafederico.conexion.*;

public class ArchivoConfiguracion {
	public ConectorSQL datosConexionBD;
	public Puntaje puntajesApuestas;
	
	public final ConectorSQL getDatosConexionBD() {
		return datosConexionBD;
	}
	public final void setDatosConexionBD(ConectorSQL datosConexionBD) {
		this.datosConexionBD = datosConexionBD;
	}
	public final Puntaje getPuntajesApuestas() {
		return puntajesApuestas;
	}
	public final void setPuntajesApuestas(Puntaje puntajesApuestas) {
		this.puntajesApuestas = puntajesApuestas;
	}
	
}
