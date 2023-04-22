package org.caucotafederico.TrabajoIntegrador_ent2;

import org.caucotafederico.exceptions.FaltaListadoException;
import org.caucotafederico.exceptions.NroColumnasInvalidoException;
import org.caucotafederico.exceptions.NroNoEnteroException;
import org.caucotafederico.models.Apuesta;



public class App 
{
    public static void main( String[] args )
    {
        Apuesta apuestaProde = new Apuesta();
        String archivoResultados = "src\\resources\\resultados.csv";
        String archivoPronostico = "src\\resources\\pronostico.csv";
        
        
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

}