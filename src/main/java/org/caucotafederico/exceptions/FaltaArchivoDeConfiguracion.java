package org.caucotafederico.exceptions;

@SuppressWarnings("serial")
public class FaltaArchivoDeConfiguracion extends Exception {
	private String mensaje = null;

	public FaltaArchivoDeConfiguracion() {
		this.mensaje = "No se pudo abrir el Archivo de Configuración";
	}

	public String getMensaje() {
		return mensaje;
	}

}