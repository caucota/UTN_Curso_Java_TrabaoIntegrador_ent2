package org.caucotafederico.models;
import com.opencsv.bean.*;

public class Resultado {
	
	@CsvBindByPosition(position = 0)
	private int ronda;

	@CsvBindByPosition(position = 1)
	private String equipo1;
	
	@CsvBindByPosition(position = 4)
	private String equipo2;
	
	@CsvBindByPosition(position = 2)
	private int golesEquipo1;
	
	@CsvBindByPosition(position = 3)
	private int golesEquipo2;
	
	
	public final int getRonda() {
		return ronda;
	}
	public final void setRonda(int ronda) {
		this.ronda = ronda;
	}
	
	public String getEquipo1() {
		return equipo1;
	}
	public void setEquipo1(String equipo1) {
		this.equipo1 = equipo1;
	}
	public String getEquipo2() {
		return equipo2;
	}
	public void setEquipo2(String equipo2) {
		this.equipo2 = equipo2;
	}
	public int getGolesEquipo1() {
		return golesEquipo1;
	}
	public void setGolesEquipo1(int golesEquipo1) {
		this.golesEquipo1 = golesEquipo1;
	}
	public int getGolesEquipo2() {
		return golesEquipo2;
	}
	public void setGolesEquipo2(int golesEquipo2) {
		this.golesEquipo2 = golesEquipo2;
	}
	
	public String EquipoGanador() {
		String equipo = "";
		if (this.getGolesEquipo1() > this.getGolesEquipo2()) {
			equipo = this.getEquipo1();
		} else {
			if (this.getGolesEquipo1() < this.getGolesEquipo2()) {
				equipo = this.getEquipo2();
			} else {
				equipo = "EMPATE";
			}
			
		}
		
		return equipo; 
	}
}
