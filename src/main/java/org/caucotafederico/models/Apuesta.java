package org.caucotafederico.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

public class Apuesta {
	
    private List<Resultado> listadoResultados;
    private List<Pronostico> listadoPronosticos;
    
    public Apuesta() {
        listadoResultados = new ArrayList<>();
        listadoPronosticos = new ArrayList<>();
    }
    
    
    public final List<Resultado> getListadoResultados() {
		return listadoResultados;
	}


	public final void setListadoResultados(List<Resultado> listadoResultados) {
		this.listadoResultados = listadoResultados;
	}


	public final List<Pronostico> getListadoPronosticos() {
		return listadoPronosticos;
	}


	public final void setListadoPronosticos(List<Pronostico> listadoPronosticos) {
		this.listadoPronosticos = listadoPronosticos;
	}

	public void armarListadosResultadosYPronostico(String rutaResultados, String rutaPronosticos ) throws Exception{
		armarListadoResultados(rutaResultados);
		armarListadoPronosticos(rutaPronosticos);
	}

	private void armarListadoResultados (String rutaResultados ) throws Exception{
        Resultado unResultado = null;
        /*
        try {
			listadoResultados = new CsvToBeanBuilder(new FileReader(archivoResultados))
					.withType(Resultado.class)
					.build()
					.parse();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		*/
	    try (Reader reader = Files.newBufferedReader(Paths.get(rutaResultados)) ) 
	    {
	        try (CSVReader csvReader = new CSVReader(reader))
	        {
	            String[] lineaLeida;
	            int nroLinea = 0;
	            while ((lineaLeida = csvReader.readNext()) != null) {
	            	nroLinea++;
	            	if (nroLinea == 1) {
	            		continue;
	            	}
	            	if(lineaLeida.length != 5) { /// controlamos cantidad de columnas
	            		throw new NroColumnasInvalidoException(lineaLeida.length , nroLinea);
	            	};
	            	if(!isInteger(lineaLeida[0])) { 
	            		throw new NroNoEnteroException(1, "Nro. de Ronda", nroLinea, lineaLeida[0]);
	            	}
	            	if(!isInteger(lineaLeida[2])) {
	            		throw new NroNoEnteroException(3, "Goles Local", nroLinea, lineaLeida[2]);
	            	}
	            	if(!isInteger(lineaLeida[3])) {
	            		throw new NroNoEnteroException(4, "Goles Visitante", nroLinea, lineaLeida[3]);
	            	}
	            	unResultado = new Resultado();
	            	unResultado.setRonda(Integer.parseInt(lineaLeida[0]));
	            	unResultado.setEquipo1(lineaLeida[1]);
	            	unResultado.setGolesEquipo1(Integer.parseInt(lineaLeida[2]));
	            	unResultado.setGolesEquipo2(Integer.parseInt(lineaLeida[3]));
	            	unResultado.setEquipo2(lineaLeida[4]);
	            	
	            	this.listadoResultados.add(unResultado);
	            }
	        }
	    } catch (IOException e1) {
			e1.printStackTrace();
		}
    	
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void armarListadoPronosticos(String rutaPronosticos ) throws Exception{
        try {
        	this.listadoPronosticos = new CsvToBeanBuilder(new FileReader(rutaPronosticos, StandardCharsets.UTF_8))
        			.withSkipLines(1)
					.withType(Pronostico.class)
					.build()
					.parse();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
    	
    }
    
    //public void verResultadosPorApostadoryRonda(String rutaResultados, String rutaPronosticos) throws Exception  {
    public void verResultadosPorApostadoryRonda()  {
    	try {
			validarListadosArmados();
			
	    	HashMap<String, Apostador> apostadoresRondas = new HashMap<String, Apostador>();
	    	Apostador unApostador;
	    	HashSet<String> nombreGanadores = new HashSet<String>();
	    	int puntosGanador = 0;
	    	
	    	//int unPronostico = -1;
	    	//int difGoles = 0;
	    	Integer puntosRonda = 0;
	    	HashMap<Integer, Integer> rondaApostador = new HashMap<Integer, Integer>();
	    	
	    	for(Resultado resultadoPartido : this.listadoResultados) {
	    		
				//difGoles = resultadoPartido.getGolesEquipo1() - resultadoPartido.getGolesEquipo2();
				
	    		for(Pronostico pron : this.listadoPronosticos) {
	    			
	        		unApostador = apostadoresRondas.get(pron.getApostador());
	        		if (unApostador == null) {
	        			unApostador = new Apostador(pron.getApostador());
	        		}
	        		rondaApostador = unApostador.getResultadosPorRonda();
	        		puntosRonda = rondaApostador.get(resultadoPartido.getRonda());
	        		if (puntosRonda == null) {
	        			puntosRonda = 0;
	        		}
	        		puntosRonda = puntosRonda + pron.puntosObtenidosDelPartido(resultadoPartido);
	    			rondaApostador.put(resultadoPartido.getRonda(), puntosRonda);
	    			unApostador.setResultadosPorRonda(rondaApostador);
	    			apostadoresRondas.put(pron.getApostador(), unApostador);
	    		
	    		}
	    	}
	    	
	    	Iterator<Entry<String, Apostador> > new_Iterator = apostadoresRondas.entrySet().iterator();

	    	while (new_Iterator.hasNext()) {
	    		HashMap.Entry<String, Apostador> new_Map = (HashMap.Entry<String, Apostador>) new_Iterator.next();
	    		puntosRonda = new_Map.getValue().verPuntosPorRonda(); // Mostramos los puntos de cada uno por ronda y total de aciertos
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
	    	
			
		} catch (FaltaListadoException e) {
			System.out.println(e.getMensaje());
        } ;
    }
    
    
    public int calcularPuntajesTotalesEnTotalRondas(String rutaResultados, String rutaPronosticos) throws Exception  {
    	int unPronostico = -1;
    	int difGoles = 0;
    	int puntos = 0;
    	Integer puntosAcumulados = 0;
    	HashMap<String, Integer> apostadores_HashMap = new HashMap<String, Integer>();
    	
    	//try {
    		armarListadoResultados(rutaResultados);
    		armarListadoPronosticos(rutaPronosticos);
			validarListadosArmados();
    	
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
	    	/*
		} catch (FaltaListadoException e) {
			System.out.println(e.getMensaje());
		} catch (NroColumnasInvalidoException e) {
			System.out.println(e.getMensaje());
		} catch (NroNoEnteroException e) {
			System.out.println(e.getMensaje());
		}
		*/
    	
    	//System.out.println(apostadores_HashMap);
    	return puntos;
    	
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

    private boolean validarListadosArmados() throws FaltaListadoException {
    	if (this.listadoResultados == null || this.listadoResultados.size() == 0) {
    		throw new FaltaListadoException(1);
    	}
    	if (this.listadoPronosticos == null || this.listadoPronosticos.size() == 0) {
    		throw new FaltaListadoException(2);
    	}
    	return true;
    }
}
