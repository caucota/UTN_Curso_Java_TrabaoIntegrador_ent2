package org.caucotafederico.TrabajoIntegrador_ent2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;
import org.caucotafederico.models.Apuesta;
import org.caucotafederico.models.Pronostico;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.caucotafederico.models.ArchivoConfiguracion;
import org.caucotafederico.conexion.ConectorSQL;
import org.caucotafederico.exceptions.FaltanDatosBDPronosticos;

public class App 
{
    public static void main( String[] args )
    {
        String archivoResultados = "src\\resources\\resultados.csv";
        //String archivoPronostico = "src\\resources\\pronostico.csv";
        String rutaarchivoConfiguracion = "src\\resources\\configuracionProde.txt";
        
        if(args.length == 0) {
        //	System.out.print("Faltan las rutas de los archivos");
        //	System.exit(88);
        }else {
    		archivoResultados = args[0];
        	if (args.length > 1) {
        		//archivoPronostico = args[1];
        		rutaarchivoConfiguracion = args[1];
        	}
        		
        }
        ArchivoConfiguracion archConfig = leerArchivoConfiguracion(rutaarchivoConfiguracion);
        if (archConfig != null) {
        	Apuesta apuestaProde = new Apuesta(archConfig.getPuntajesApuestas().getPuntosPorAcierto(), archConfig.getPuntajesApuestas().getPuntosExtrasPorRondaCompleta(), archConfig.getPuntajesApuestas().getPuntosExtrasPorFaseCompleta());
        	//Apuesta apuestaProde = new Apuesta(1, 1, 1);
        	
	        try {
	        	apuestaProde.armarListadoResultados(archivoResultados);
            	apuestaProde.armarListadoPronosticosDesdeBD();
	        	apuestaProde.calcularAciertosCadaApostador();
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

       
    }
    
	public static ArchivoConfiguracion leerArchivoConfiguracion(String rutaArchivoConfiguracion) {
		
		String linea ;
		String json = "";
		BufferedReader lector;
		ArchivoConfiguracion archivoLeido;
		Object objectMapper = new ObjectMapper();
		try {
			lector = new BufferedReader(new FileReader(rutaArchivoConfiguracion));
			while((linea = lector.readLine())!=null) {//Este es un bucle que se repetirá mientras haya líneas por leer en el archivo.
				json = json + linea;
			}
			
			ArchivoConfiguracion archivoLeido = objectMapper.readValue(json, ArchivoConfiguracion.class);
			//System.out.println(archivoLeido);

			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return archivoLeido;
	}


}