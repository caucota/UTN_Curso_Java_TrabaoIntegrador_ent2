package org.caucotafederico.exceptions;

public class NroColumnasInvalidoException extends Exception {
	private final String mensaje;

	public NroColumnasInvalidoException(int nroColumnasLeidas, int nroRenglonError) {
		this.mensaje = "Error: El archivo resultados.csv sólo puede tener 5 columnas: Ronda - Equipo1 - Goles Equipo1 - Goles Equipo2 - Equipo2. Cantidad actual de columnas " + nroColumnasLeidas + " en el renglón " + nroRenglonError ;
	}
	public String getMensaje() {
		return mensaje;
	}
	

}