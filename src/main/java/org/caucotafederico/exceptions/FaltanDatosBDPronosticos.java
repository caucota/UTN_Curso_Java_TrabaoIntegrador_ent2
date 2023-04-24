package org.caucotafederico.exceptions;

@SuppressWarnings("serial")
public class FaltanDatosBDPronosticos extends Exception {
	private String mensaje = null;

	public FaltanDatosBDPronosticos() {
		this.mensaje = "Aun no se ha definido en la Base de Datos el listado de Pronosticos";
	}

	public String getMensaje() {
		return mensaje;
	}

}
