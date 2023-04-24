package org.caucotafederico.TrabajoIntegrador_ent2;

import org.caucotafederico.exceptions.FaltaArchivoDeConfiguracion;
import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;
import org.caucotafederico.models.Apuesta;


import org.caucotafederico.models.ArchivoConfiguracion;
import org.caucotafederico.exceptions.FaltanDatosConexionBD;
import org.caucotafederico.exceptions.FaltanDatosDePuntajes;

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
        ArchivoConfiguracion archConfig = new ArchivoConfiguracion();
        	
        try {
			archConfig.LeerArchivoDeConfiguracion(rutaarchivoConfiguracion);
        	Apuesta apuestaProde = new Apuesta(archConfig.getPuntajesApuestas().getPuntosPorAcierto(), archConfig.getPuntajesApuestas().getPuntosExtrasPorRondaCompleta(), archConfig.getPuntajesApuestas().getPuntosExtrasPorFaseCompleta());
        	
        	apuestaProde.armarListadoResultados(archivoResultados);
        	apuestaProde.armarListadoPronosticosDesdeBD(archConfig.getDatosConexionBD());
        	apuestaProde.calcularAciertosCadaApostador();
			apuestaProde.verResultadosPorApostadoryRonda();
        } catch (NroColumnasInvalidoException e) {
        	System.out.println(e.getMensaje());
        } catch (NroNoEnteroException e) {
        	System.out.println(e.getMensaje());
        } catch (FaltaListadoException e) {
        	System.out.println(e.getMensaje());
		} catch (FaltanDatosConexionBD | FaltanDatosDePuntajes | FaltaArchivoDeConfiguracion e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(e1.getMessage());
		} catch (Exception e2) {
        	System.out.println(e2.getMessage());
			e2.printStackTrace();
		}
       
    }
    


}