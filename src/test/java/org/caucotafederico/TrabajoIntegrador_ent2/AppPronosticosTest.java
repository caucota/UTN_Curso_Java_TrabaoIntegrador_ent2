
package org.caucotafederico.TrabajoIntegrador_ent2;



import java.util.ArrayList;
import java.util.List;

import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;
import org.caucotafederico.models.Apuesta;
import org.caucotafederico.models.Pronostico;
import org.caucotafederico.models.Resultado;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
public class AppPronosticosTest {

	@Test
	public void test_GetEquipoGanadorDeUnResultado(){
		Resultado resultadoUnPartido = new Resultado();
		resultadoUnPartido.setEquipo1("Argentina");
		resultadoUnPartido.setEquipo2("Bolivia");
		resultadoUnPartido.setGolesEquipo1(1);
		resultadoUnPartido.setGolesEquipo2(2);
		
		assertEquals("Argentina", resultadoUnPartido.EquipoGanador());
				
	}	
	
	@Test
	public void test_GetEquipoGandadorDeUnaApuesta(){
		Pronostico pronosticoUnPartido = new Pronostico();
		pronosticoUnPartido.setEquipo1("Argentina");
		pronosticoUnPartido.setEquipo2("Bolivia");
		pronosticoUnPartido.setGanaLocal("");
		pronosticoUnPartido.setEmpate("X");
		pronosticoUnPartido.setGanaVisitante("");
		
		assertEquals("EMPATE", pronosticoUnPartido.EquipoGanador());
				
	}
	
	@Test
	public void test_GetPuntosDeUnPronosticoDeUnPartido() {
		Pronostico pronosticoUnPartido = new Pronostico();
		pronosticoUnPartido.setEquipo1("Brasil");
		pronosticoUnPartido.setEquipo2("España");
		pronosticoUnPartido.setGanaLocal("");
		pronosticoUnPartido.setEmpate("X");
		pronosticoUnPartido.setGanaVisitante("");
		
		Resultado resultadoUnPartido = new Resultado();
		resultadoUnPartido.setEquipo1("Brasil");
		resultadoUnPartido.setEquipo2("España");
		resultadoUnPartido.setGolesEquipo1(2);
		resultadoUnPartido.setGolesEquipo2(2);

		assertEquals(1, pronosticoUnPartido.puntosObtenidosDelPartido(resultadoUnPartido));
		
	}
	
	@Test
	public void test_CalcularTotalPuntosUnApostadorEnTodasRondas() {
		List<Resultado> listaResultados = new ArrayList<>();
		Resultado unResultado = new Resultado();
		unResultado.setRonda(1);
		unResultado.setEquipo1("Peru");
		unResultado.setEquipo2("Alemania");
		unResultado.setGolesEquipo1(1);
		unResultado.setGolesEquipo2(2);
		listaResultados.add(unResultado);
		
		unResultado = new Resultado();
		unResultado.setRonda(1);
		unResultado.setEquipo1("Chile");
		unResultado.setEquipo2("Uruguay");
		unResultado.setGolesEquipo1(1);
		unResultado.setGolesEquipo2(2);
		listaResultados.add(unResultado);
		
		unResultado = new Resultado();
		unResultado.setRonda(2);
		unResultado.setEquipo1("Uruguay");
		unResultado.setEquipo2("Alemania");
		unResultado.setGolesEquipo1(2);
		unResultado.setGolesEquipo2(2);
		listaResultados.add(unResultado);
		
		
		List<Pronostico> listaPronosticos = new ArrayList<>();
		Pronostico unPronostico = new Pronostico();
		unPronostico.setApostador("Juan");
		unPronostico.setEquipo1("Peru");
		unPronostico.setEquipo2("Alemania");
		unPronostico.setGanaLocal("");
		unPronostico.setEmpate("X");
		unPronostico.setGanaVisitante("");
		listaPronosticos.add(unPronostico);
		
		
		unPronostico = new Pronostico();
		unPronostico.setApostador("Juan");
		unPronostico.setEquipo1("Chile");
		unPronostico.setEquipo2("Uruguay");
		unPronostico.setGanaLocal("");
		unPronostico.setEmpate("");
		unPronostico.setGanaVisitante("X");
		listaPronosticos.add(unPronostico);
		
		unPronostico = new Pronostico();
		unPronostico.setApostador("Juan");
		unPronostico.setEquipo1("Uruguay");
		unPronostico.setEquipo2("Alemania");
		unPronostico.setGanaLocal("");
		unPronostico.setEmpate("X");
		unPronostico.setGanaVisitante("");
		listaPronosticos.add(unPronostico);
		
		
		
        Apuesta apuestaProde = new Apuesta();
        apuestaProde.setListadoResultados(listaResultados);
        apuestaProde.setListadoPronosticos(listaPronosticos);
        
        
        
        String archivoResultados = "src\\resources\\resultados.csv";
        String archivoPronostico = "src\\resources\\pronostico.csv";
        
        try {
        	apuestaProde.armarListadosResultadosYPronostico(archivoResultados, archivoPronostico);
            assertEquals(4, apuestaProde.totalPuntosUnApostador("Mariana"));
        } catch (NroColumnasInvalidoException e) {
        	System.out.println(e.getMensaje());
        } catch (NroNoEnteroException e) {
        	System.out.println(e.getMensaje());
        } catch (FaltaListadoException e) {
        	System.out.println(e.getMensaje());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		
		
	}
	
	
}
