package org.caucotafederico.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.caucotafederico.conexion.ConectorSQL;
import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.FaltanDatosBDPronosticos;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

public class Apuesta {
	
	private HashMap<String, Apostador> apostadores;
	private HashMap<Integer, Integer> aciertosMaxPorRonda;
	private int aciertosMaxPorFase;
    private List<Resultado> listadoResultados;
    private List<Pronostico> listadoPronosticos;
    private Puntaje puntajes;
    
    public Apuesta(int puntosPorAcierto, int puntosExtrasPorRonda, int puntosExtrasPorFase) {
    	this.apostadores = new HashMap<String, Apostador>();
    	this.listadoResultados = new ArrayList<>();
    	this.listadoPronosticos = new ArrayList<>();
        this.setAciertosMaxPorFase(0);
        this.setAciertosMaxPorRonda(new HashMap<Integer, Integer>());
        this.puntajes = new Puntaje(puntosPorAcierto, puntosExtrasPorRonda, puntosExtrasPorFase);
    }
    
    
    public final List<Resultado> getListadoResultados() {
		return listadoResultados;
	}


	public final void setListadoResultados(List<Resultado> listadoResultados) {
		this.listadoResultados = listadoResultados;
	    this.calcularAciertosMaxPorRonda();
	}


	public final List<Pronostico> getListadoPronosticos() {
		return listadoPronosticos;
	}

	public final void setListadoPronosticos(List<Pronostico> listadoPronosticos) {
		this.listadoPronosticos = listadoPronosticos;
	}
	
	

	public HashMap<Integer, Integer> getAciertosMaxPorRonda() {
		return aciertosMaxPorRonda;
	}


	public void setAciertosMaxPorRonda(HashMap<Integer, Integer> aciertosMaxPorRonda) {
		this.aciertosMaxPorRonda = aciertosMaxPorRonda;
	}


	public int getAciertosMaxPorFase() {
		return aciertosMaxPorFase;
	}


	public void setAciertosMaxPorFase(int aciertosMaxPorFase) {
		this.aciertosMaxPorFase = aciertosMaxPorFase;
	}


	public void armarListadosResultadosYPronostico(String rutaResultados, String rutaPronosticos ) throws Exception{
		this.armarListadoResultados(rutaResultados);
		this.armarListadoPronosticos(rutaPronosticos);
		this.calcularAciertosCadaApostador();
	}

	public void armarListadoResultados (String rutaResultados ) throws Exception{
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
	    this.calcularAciertosMaxPorRonda();
    	
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
    
    public void armarListadoPronosticosDesdeBD() throws ClassNotFoundException, SQLException, FaltanDatosBDPronosticos {
        Connection con = null;
			Class.forName(ConectorSQL.JDBC_DRIVER);
    		//abrir la conexion
			con = DriverManager.getConnection(ConectorSQL.DB_URL, ConectorSQL.USUARIO, ConectorSQL.CLAVE);
    		 
    		//Ejecutar la conexion
    		Statement consulta;
			consulta = con.createStatement();
    		
			ResultSet rs;
			rs = consulta.executeQuery("SELECT * FROM pronostico ORDER BY id");
			
			while (rs.next()){
				System.out.println(rs.getString(2));
	    		Pronostico unPronostico = new Pronostico();
	    		unPronostico.setApostador(rs.getString(2) );
	    		unPronostico.setEquipo1(rs.getString(3).trim().toUpperCase());
	    		unPronostico.setEquipo2(rs.getString(4).trim().toUpperCase());
	    		unPronostico.setGanaLocal(rs.getString(5).trim().toUpperCase());
	    		unPronostico.setEmpate(rs.getString(6).trim().toUpperCase());
	    		unPronostico.setGanaVisitante(rs.getString(7).trim().toUpperCase());
	    		listadoPronosticos.add(unPronostico);
			}
			con.close();
			if(listadoPronosticos.size() == 0) {
				throw new FaltanDatosBDPronosticos();
			}
    }
    
    private void calcularAciertosMaxPorRonda() { /// creo este metodo para calcular cuantos puntos se pueden obtener como max en una ronda.. luego lo llamo desde el metodo que lee el archivo y desde el set deResultados
    	Integer aciertosMax = 0;
    	this.aciertosMaxPorFase = 0;
    	
    	for(Resultado resultadoPartido : getListadoResultados()) {
    		
    		aciertosMax = this.getAciertosMaxPorRonda().get(resultadoPartido.getRonda());
    		if (aciertosMax == null) {
    			aciertosMax = 0;
    		};
    		aciertosMax ++;
    		
    		this.aciertosMaxPorRonda.put(resultadoPartido.getRonda(), aciertosMax);
    		
    		this.aciertosMaxPorFase ++;
    	}
    }
    
    public void calcularAciertosCadaApostador() {
    	try {
			validarListadosArmados();
			
	    	Apostador unApostador;
	    	Integer aciertosRonda = 0;
	    	HashMap<Integer, Integer> rondaApostador = new HashMap<Integer, Integer>();
	    	
	    	for(Resultado resultadoPartido : this.listadoResultados) {
	    		
				
	    		for(Pronostico pron : this.listadoPronosticos) {
	    			
	        		unApostador = apostadores.get(pron.getApostador());
	        		if (unApostador == null) {
	        			unApostador = new Apostador(pron.getApostador());
	        		}
	        		rondaApostador = unApostador.getAciertosPorRonda();
	        		aciertosRonda = rondaApostador.get(resultadoPartido.getRonda());
	        		if (aciertosRonda == null) {
	        			aciertosRonda = 0;
	        		}
	        		aciertosRonda = aciertosRonda + pron.aciertoDelPartido(resultadoPartido);
	    			rondaApostador.put(resultadoPartido.getRonda(), aciertosRonda);
	    			unApostador.setAciertosPorRonda(rondaApostador);
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
				puntosRonda = new_Map.getValue().listarPuntosPorRonda(this.puntajes, this.getAciertosMaxPorRonda() ); // Mostramos los puntos de cada uno por ronda y total de aciertos
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
    			puntos = unApostador.puntosTotales(this.puntajes, this.getAciertosMaxPorRonda(), this.aciertosMaxPorFase);
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
