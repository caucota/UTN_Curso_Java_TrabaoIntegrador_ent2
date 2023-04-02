package org.caucotafederico.exceptions;

public class NroNoEnteroException extends Exception {
	private final String mensaje;
	
	public NroNoEnteroException(int nroColumna, String nombreColumna, int nroRenglonError, String valorError) {
		this.mensaje = "Error: En el archivo resultados.csv la columna " + nroColumna + ", " + nombreColumna  +", debe contener un valor numérico. Verifique la linea " + nroRenglonError + " contiene el siguiente valor: " + valorError;
	}

	public final String getMensaje() {
		return mensaje;
	}
}
