package org.caucotafederico.models;
import com.opencsv.bean.*;

public class Pronostico {

	/*
	@CsvBindByPosition (position = 0)
	private int ronda;
	
	@CsvBindByPosition (position = 1)
	private String apostador;

	@CsvBindByPosition (position = 2)
	private String equipo1;

	@CsvBindByPosition (position = 3)
	private String ganaLocal;

	@CsvBindByPosition (position = 4)
	private String empate;

	@CsvBindByPosition (position = 5)
	private String ganaVisitante;

	@CsvBindByPosition (position = 6)
	private String equipo2;
	 */
	
	private int ronda;

	@CsvBindByPosition (position = 0)
	private String apostador;
	
	@CsvBindByPosition (position = 1)
	private String equipo1;

	@CsvBindByPosition (position = 2)
	private String ganaLocal;

	@CsvBindByPosition (position = 3)
	private String empate;

	@CsvBindByPosition (position = 4)
	private String ganaVisitante;

	@CsvBindByPosition (position = 5)
	private String equipo2;
	
	public final int getRonda() {
		return ronda;
	}

	public final void setRonda(int ronda) {
		this.ronda = ronda;
	}

	public final String getApostador() {
		return apostador;
	}

	public final void setApostador(String apostador) {
		this.apostador = apostador;
	}

	public String getEquipo1() {
		return equipo1;
	}

	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}

	public String getGanaLocal() {
		return ganaLocal;
	}

	public void setGanaLocal(String ganaLocal) {
		this.ganaLocal = ganaLocal;
	}

	public String getEmpate() {
		return empate;
	}

	public void setEmpate(String empate) {
		this.empate = empate;
	}

	public String getGanaVisitante() {
		return ganaVisitante;
	}

	public void setGanaVisitante(String ganaVisitante) {
		this.ganaVisitante = ganaVisitante;
	}

	public String getEquipo2() {
		return equipo2;
	}

	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}

	public String EquipoGanador() {
		String equipo = "";
		if (this.getGanaLocal().equalsIgnoreCase("X")) {
			equipo = this.getEquipo1();
		} else {
			if (this.getGanaVisitante().equalsIgnoreCase("X")) {
				equipo = this.getEquipo2();
			}else {
				equipo = "EMPATE";
			}
		}
		return equipo; 
	}
	
	public int puntosObtenidosDelPartido(Resultado unResultado) {
		int puntos = 0;
		if (this.equipo1.equalsIgnoreCase(unResultado.getEquipo1()) && this.equipo2.equalsIgnoreCase(unResultado.getEquipo2())) {
			if (this.EquipoGanador().equalsIgnoreCase(unResultado.EquipoGanador())) {
				puntos ++;
			}
			
		}
		return puntos;
	}
}
