package org.caucotafederico.TrabajoIntegrador_ent2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;
import org.caucotafederico.models.Apostador;
import org.caucotafederico.models.Apuesta;
import org.caucotafederico.models.Pronostico;
import org.caucotafederico.models.Resultado;



public class App 
{
    public static void main( String[] args )
    {
        Apuesta apuestaProde = new Apuesta();
        String archivoResultados = "C:\\Users\\fcaucota\\eclipse-workspace\\TrabajoIntegrador_ent2\\src\\resources\\resultados.csv";
        String archivoPronostico = "C:\\Users\\fcaucota\\eclipse-workspace\\TrabajoIntegrador_ent2\\src\\resources\\pronostico.csv";
        
        
        if(args.length == 0) {
        //	System.out.print("Faltan las rutas de los archivos");
        //	System.exit(88);
        }else {
    		archivoResultados = args[0];
        	if (args.length > 1) {
        		archivoPronostico = args[1];
        	}
        		
        }
        
        try {
        	apuestaProde.armarListadosResultadosYPronostico(archivoResultados, archivoPronostico);
			apuestaProde.verResultadosPorApostadoryRonda();
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
    
    private static int calcularPuntaje(List<Pronostico> listadoPronosticos, List<Resultado> listadoResultados) {
    	int unPronostico = -1;
    	int difGoles = 0;
    	int puntos = 0;
    	Integer puntosAcumulados = 0;
    	HashMap<String, Integer> apostadores_HashMap = new HashMap<String, Integer>();
    	
    	for(Resultado resultadoPartido : listadoResultados) {
			difGoles = resultadoPartido.getGolesEquipo1() - resultadoPartido.getGolesEquipo2();
    		for(Pronostico pron : listadoPronosticos) {
    			
        		puntosAcumulados = apostadores_HashMap.get(pron.getApostador());
        		if (puntosAcumulados == null) {
        			puntosAcumulados = 0;
        		}
        				
    			//System.out.println(pron.getEquipo1().toUpperCase().trim() + " - " + resultadoPartido.getEquipo1().toUpperCase().trim());
    			//System.out.println(pron.getEquipo2().toUpperCase().trim() + " - " + resultadoPartido.getEquipo2().toUpperCase().trim());
    			if(pron.getEquipo1().toUpperCase().trim().equals(resultadoPartido.getEquipo1().toUpperCase().trim()) && 
    			   pron.getEquipo2().toUpperCase().trim().equals(resultadoPartido.getEquipo2().toUpperCase().trim())) {
    				
    				
    				if(pron.getGanaLocal().toUpperCase().equals("X")) {
    					unPronostico = 1;
    	        	}else {
    	            	if(pron.getGanaVisitante().toUpperCase().equals("X")) {
    	            		unPronostico = 2;
    	            	}else {
    	            		unPronostico = 0;
    	            	}
    	        	}
        			//System.out.println("Pronostico:" + unPronostico + " -- Dif Goles:" + difGoles );
    				if( ((difGoles > 0  ) && (unPronostico == 1)) || 
    					((difGoles == 0 ) && (unPronostico == 0)) ||
    					((difGoles < 0  ) && (unPronostico == 2)) ){
    					puntosAcumulados++;
    					puntos++;
    				}
    			}
    			apostadores_HashMap.put(pron.getApostador(), puntosAcumulados);
    		
    		}
    	}
    	
    	Iterator<Entry<String, Integer> > new_Iterator = apostadores_HashMap.entrySet().iterator();

    	while (new_Iterator.hasNext()) {
    		HashMap.Entry<String, Integer> new_Map = (HashMap.Entry<String, Integer>) new_Iterator.next();

        System.out.println(new_Map.getKey() + " = "
                           + new_Map.getValue());
    	}    	
    	
    	//System.out.println(apostadores_HashMap);
    	return puntos;
    	
    }
    
    public static void verResultadosPorApostadoryRonda(List<Pronostico> listadoPronosticos, List<Resultado> listadoResultados) {
    	HashMap<String, Apostador> apostadoresRondas = new HashMap<String, Apostador>();
    	Apostador unApostador;
    	HashSet<String> nombreGanadores = new HashSet<String>();
    	int puntosGanador = 0;
    	
    	int unPronostico = -1;
    	int difGoles = 0;
    	Integer puntosRonda = 0;
    	HashMap<Integer, Integer> rondaApostador = new HashMap<Integer, Integer>();
    	
    	for(Resultado resultadoPartido : listadoResultados) {
			difGoles = resultadoPartido.getGolesEquipo1() - resultadoPartido.getGolesEquipo2();
    		for(Pronostico pron : listadoPronosticos) {
    			
        		unApostador = apostadoresRondas.get(pron.getApostador());
        		if (unApostador == null) {
        			unApostador = new Apostador(pron.getApostador());
        		}
        		rondaApostador = unApostador.getResultadosPorRonda();
        		puntosRonda = rondaApostador.get(resultadoPartido.getRonda());
        		if (puntosRonda == null) {
        			puntosRonda = 0;
        		}
        				
    			//System.out.println(pron.getEquipo1().toUpperCase().trim() + " - " + resultadoPartido.getEquipo1().toUpperCase().trim());
    			//System.out.println(pron.getEquipo2().toUpperCase().trim() + " - " + resultadoPartido.getEquipo2().toUpperCase().trim());
    			if(pron.getEquipo1().toUpperCase().trim().equals(resultadoPartido.getEquipo1().toUpperCase().trim()) && 
    			   pron.getEquipo2().toUpperCase().trim().equals(resultadoPartido.getEquipo2().toUpperCase().trim())) {
    				
    				
    				if(pron.getGanaLocal().toUpperCase().equals("X")) {
    					unPronostico = 1;
    	        	}else {
    	            	if(pron.getGanaVisitante().toUpperCase().equals("X")) {
    	            		unPronostico = 2;
    	            	}else {
    	            		unPronostico = 0;
    	            	}
    	        	}
        			//System.out.println("Pronostico:" + unPronostico + " -- Dif Goles:" + difGoles );
    				if( ((difGoles > 0  ) && (unPronostico == 1)) || 
    					((difGoles == 0 ) && (unPronostico == 0)) ||
    					((difGoles < 0  ) && (unPronostico == 2)) ){
    					puntosRonda++;
    				}
    			}
    			rondaApostador.put(resultadoPartido.getRonda(), puntosRonda);
    			unApostador.setResultadosPorRonda(rondaApostador);
    			apostadoresRondas.put(pron.getApostador(), unApostador);
    		
    		}
    	}
    	
    	Iterator<Entry<String, Apostador> > new_Iterator = apostadoresRondas.entrySet().iterator();

    	while (new_Iterator.hasNext()) {
    		HashMap.Entry<String, Apostador> new_Map = (HashMap.Entry<String, Apostador>) new_Iterator.next();
    		puntosRonda = new_Map.getValue().verPuntosPorRonda();
    		if (puntosGanador < puntosRonda) {
    			puntosGanador = puntosRonda;
    			nombreGanadores.removeAll(nombreGanadores);
    			nombreGanadores.add(new_Map.getValue().getNombre());
    		};
    		if (puntosGanador == puntosRonda) {
    			nombreGanadores.add(new_Map.getValue().getNombre());
    		};
    	}
    	if ((nombreGanadores.size() > 0) && (puntosGanador > 0)) {
    		System.out.println("----------------------------------------");
    		System.out.println("----- GANADORES DE LOS PRONOSTICOS -----");
    		for(String unNombre: nombreGanadores) {
    			System.out.println("------     " + unNombre  +  "    --------");
    		}
    		System.out.println("----------------------------------------");
    		System.out.println("----------------------------------------");
    	}else {
    		System.out.println("NO HUBO GANADORES");
    	}
    	
    	
    }

    public static boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }    
}