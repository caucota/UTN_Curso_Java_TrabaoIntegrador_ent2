package org.caucotafederico.exceptions;

@SuppressWarnings("serial")
public class FaltanDatosDePuntajes extends Exception {
	private String mensaje = null;

	public FaltanDatosDePuntajes(String mensajeError) {
		this.mensaje = mensajeError;
	}

	public String getMensaje() {
		return mensaje;
	}

}
