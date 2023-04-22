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
	
	private HashMap<String, Apostador> apostadores;
    private List<Resultado> listadoResultados;
    private List<Pronostico> listadoPronosticos;
    
    public Apuesta() {
    	apostadores = new HashMap<String, Apostador>();
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
		calcularPuntosCadaApostador();
	}

	private void armarListadoResultados (String rutaResultados ) throws Exception{
        Resultado unResultado = null;
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
    
    private void calcularPuntosCadaApostador() {
    	try {
			validarListadosArmados();
			
	    	Apostador unApostador;
	    	Integer puntosRonda = 0;
	    	HashMap<Integer, Integer> rondaApostador = new HashMap<Integer, Integer>();
	    	
	    	for(Resultado resultadoPartido : this.listadoResultados) {
	    		
				
	    		for(Pronostico pron : this.listadoPronosticos) {
	    			
	        		unApostador = apostadores.get(pron.getApostador());
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
	    			apostadores.put(pron.getApostador(), unApostador);
	    		
	    		}
	    	}
    	
		} catch (FaltaListadoException e) {
			System.out.println(e.getMensaje());
        } ;

    }
    
    public void verResultadosPorApostadoryRonda()  {
    	HashSet<String> nombreGanadores = new HashSet<String>();
		int puntosGanador = 0;
		
		Integer puntosRonda = 0;
		if (apostadores != null) {
			
			Iterator<Entry<String, Apostador> > new_Iterator = apostadores.entrySet().iterator();

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
		} ;
    }
    
    public int totalPuntosUnApostador(String nombreApostador) {
    	int puntos = 0;
    	for ( String key : apostadores.keySet() ) {
    		if (key.equalsIgnoreCase(nombreApostador)) {
    			Apostador unApostador = apostadores.get(key);
    			puntos = unApostador.puntosTotales();
    		}
    	}
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
