package org.caucotafederico.TrabajoIntegrador_ent2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.caucotafederico.models.Apuesta;
import org.caucotafederico.models.Pronostico;
import org.caucotafederico.models.Resultado;
import org.junit.Test;
//import org.junit.jupiter.api.Test;


class AppTestPronosticos {


	@Test
	public void test_pronostico_CalcularPuntosDeUnApostadorEnDosRondas() {
		
		Apuesta apuestaTest = new Apuesta();
    	Resultado unResultado = new Resultado();
    	Pronostico unPronostico = new Pronostico();
    	List<Resultado> listadoResultados = new ArrayList<>();
    	List<Pronostico> listadoPronosticos = new ArrayList<>();
    	String[] equipo =  new String[] {"Argentina", "Grecia", "Uruguay", "Paraguay", "Italia", "España"};
    	
    	unResultado.setRonda(1);
    	unResultado.setEquipo1(equipo[0]);
    	unResultado.setGolesEquipo1(1);
    	unResultado.setGolesEquipo2(0);
    	unResultado.setEquipo2(equipo[1]);
    	listadoResultados.add(unResultado);		
    	
    	unResultado.setRonda(1);
    	unResultado.setEquipo1(equipo[2]);
    	unResultado.setGolesEquipo1(0);
    	unResultado.setGolesEquipo2(0);
    	unResultado.setEquipo2(equipo[3]);
    	listadoResultados.add(unResultado);		
    	
    	unResultado.setRonda(1);
    	unResultado.setEquipo1(equipo[4]);
    	unResultado.setGolesEquipo1(0);
    	unResultado.setGolesEquipo2(4);
    	unResultado.setEquipo2(equipo[5]);
    	listadoResultados.add(unResultado);	
    	
    	unResultado.setRonda(2);
    	unResultado.setEquipo1(equipo[0]);
    	unResultado.setGolesEquipo1(0);
    	unResultado.setGolesEquipo2(0);
    	unResultado.setEquipo2(equipo[2]);
    	listadoResultados.add(unResultado);		
    	
    	unResultado.setRonda(2);
    	unResultado.setEquipo1(equipo[4]);
    	unResultado.setGolesEquipo1(1);
    	unResultado.setGolesEquipo2(0);
    	unResultado.setEquipo2(equipo[3]);
    	listadoResultados.add(unResultado);		
    	
    	unResultado.setRonda(1);
    	unResultado.setEquipo1(equipo[1]);
    	unResultado.setGolesEquipo1(1);
    	unResultado.setGolesEquipo2(0);
    	unResultado.setEquipo2(equipo[5]);
    	listadoResultados.add(unResultado);	
    	
    	apuestaTest.setListadoResultados(listadoResultados);
    	
    	unPronostico.setApostador("Federico");
    	unPronostico.setEquipo1(equipo[0]);
    	unPronostico.setEquipo2(equipo[1]);
    	unPronostico.setGanaLocal("X");
    	unPronostico.setEmpate("");
    	unPronostico.setGanaVisitante("");
    	listadoPronosticos.add(unPronostico);
    	
    	unPronostico.setApostador("Federico");
    	unPronostico.setEquipo1(equipo[2]);
    	unPronostico.setEquipo2(equipo[3]);
    	unPronostico.setGanaLocal("");
    	unPronostico.setEmpate("X");
    	unPronostico.setGanaVisitante("");
    	listadoPronosticos.add(unPronostico);
    	
    	unPronostico.setApostador("Federico");
    	unPronostico.setEquipo1(equipo[4]);
    	unPronostico.setEquipo2(equipo[5]);
    	unPronostico.setGanaLocal("");
    	unPronostico.setEmpate("");
    	unPronostico.setGanaVisitante("X");
    	listadoPronosticos.add(unPronostico);
    	
    	unPronostico.setApostador("Federico");
    	unPronostico.setEquipo1(equipo[0]);
    	unPronostico.setEquipo2(equipo[2]);
    	unPronostico.setGanaLocal("");
    	unPronostico.setEmpate("");
    	unPronostico.setGanaVisitante("X");
    	listadoPronosticos.add(unPronostico);
    	
    	unPronostico.setApostador("Federico");
    	unPronostico.setEquipo1(equipo[4]);
    	unPronostico.setEquipo2(equipo[3]);
    	unPronostico.setGanaLocal("X");
    	unPronostico.setEmpate("");
    	unPronostico.setGanaVisitante("");
    	listadoPronosticos.add(unPronostico);
    	
    	unPronostico.setApostador("Federico");
    	unPronostico.setEquipo1(equipo[1]);
    	unPronostico.setEquipo2(equipo[5]);
    	unPronostico.setGanaLocal("");
    	unPronostico.setEmpate("X");
    	unPronostico.setGanaVisitante("");
    	listadoPronosticos.add(unPronostico);
    	
    	apuestaTest.setListadoPronosticos(listadoPronosticos);
    	
    	assertEquals("GANADOR/ES: " + "Federico. Puntos=5"  , apuestaTest.verResultadosPorApostadoryRonda());
    	
    	

	}
	 	
}
