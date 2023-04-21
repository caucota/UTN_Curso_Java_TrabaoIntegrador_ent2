package org.caucotafederico.exceptions;

public class FaltaListadoException extends Exception {
	private static String mensaje = null;
	
	public FaltaListadoException(int tipoListado) {
		String nombreListado = "";
		String metodoArmaListado = "";
		if (tipoListado == 1) {
			nombreListado = "Resultados";
			metodoArmaListado = "armarListadoResultados";
		}
		if (tipoListado == 2) {
			nombreListado = "Pronosticos";
			metodoArmaListado = "armarListadoPronosticos";
		}
		this.mensaje = "Aun no se ha definido el listado de " + nombreListado + ". Proporcione el Archivo Correspondiente utilizando el método \"" + metodoArmaListado + "\" ";
		
	}

	public String getMensaje() {
		return mensaje;
	}

}
