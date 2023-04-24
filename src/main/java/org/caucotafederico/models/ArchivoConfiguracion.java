package org.caucotafederico.models;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.caucotafederico.conexion.*;
import org.caucotafederico.exceptions.*;

public class ArchivoConfiguracion {
	public ConectorSQL datosConexionBD;
	public Puntaje puntajesApuestas;
	
	public ArchivoConfiguracion() {
		datosConexionBD = new ConectorSQL();
		puntajesApuestas = new Puntaje(1, 0, 0);
	}
	
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
	
	public void LeerArchivoDeConfiguracion(String rutaArchivo) throws FaltanDatosConexionBD, FaltanDatosDePuntajes, FaltaArchivoDeConfiguracion  {
		
		Properties propiedades = new Properties();
		try {
			propiedades.load(new FileReader(rutaArchivo));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new FaltaArchivoDeConfiguracion();
		}
		datosConexionBD.setJdbc_Driver(propiedades.getProperty("jdbc_Driver"));
		if (datosConexionBD.getJdbc_Driver().equals("")) {
			throw new FaltanDatosConexionBD("Error al buscar el Driver JDBC");
		}
		datosConexionBD.setDB_URL(propiedades.getProperty("db_Url"));
		if (datosConexionBD.getDb_Url().equals("")) {
			throw new FaltanDatosConexionBD("Error al buscar la URL de la Base de Datos");
		}
		datosConexionBD.setUsuario(propiedades.getProperty("usuario"));
		if (datosConexionBD.getUsuario().equals("")) {
			throw new FaltanDatosConexionBD("Error al buscar el usuario de conexión a la Base de Datos");
		}
		datosConexionBD.setClave(propiedades.getProperty("clave"));
		if (datosConexionBD.getClave().equals("")) {
			throw new FaltanDatosConexionBD("Error al buscar la contraseña del usuario de conexión a la Base de Datos");
		}

		Integer puntos = 0;
		try {
			puntos = Integer.parseInt(propiedades.getProperty("puntosAcierto"));
		} catch(NumberFormatException  e) {
			puntos = null;
		}
		if (puntos == null) {
			throw new FaltanDatosDePuntajes("No se encuentra el valor del Puntaje por Acierto.");
		}
		puntajesApuestas.setPuntosPorAcierto(puntos);
		
		puntos = 0;
		try {
			puntos = Integer.parseInt(propiedades.getProperty("puntosExtrasRondaCompleta"));
		} catch(NumberFormatException  e) {
			puntos = null;
		}
		if (puntos == null) {
			throw new FaltanDatosDePuntajes("No se encuentra el valor del Puntaje Extra por Aciertos en toda una Ronda.");
		}
		puntajesApuestas.setPuntosExtrasPorRondaCompleta(puntos);
		
		puntos = 0;
		try {
			puntos = Integer.parseInt(propiedades.getProperty("puntosExtrasFaseCompleta"));
		} catch(NumberFormatException  e) {
			puntos = null;
		}
		if (puntos == null) {
			throw new FaltanDatosDePuntajes("No se encuentra el valor del Puntaje Extra por Aciertos en toda una Fase.");
		}
		puntajesApuestas.setPuntosExtrasPorFaseCompleta(puntos);
		
	}
	
}
