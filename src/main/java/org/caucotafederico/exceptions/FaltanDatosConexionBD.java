package org.caucotafederico.exceptions;

@SuppressWarnings("serial")
public class FaltanDatosConexionBD  extends Exception {
	private String mensaje = null;

	public FaltanDatosConexionBD(String mensajeError) {
		this.mensaje = mensajeError;
	}

	public String getMensaje() {
		return mensaje;
	}

}
