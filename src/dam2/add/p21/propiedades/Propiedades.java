package dam2.add.p21.propiedades;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class Propiedades {

	private static Properties propiedad = null; 
	
	private Propiedades(String ficheropropiedadesbd) {
		propiedad = new Properties();
		try {
			propiedad.load(new FileInputStream(ficheropropiedadesbd));
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	public static Propiedades getInstance (String fichero) {
		return new Propiedades(fichero);
	}
	
	public static String getParametro(String rutaPropiedades, String parametro) {
//		String ficheroPropiedades = rutaPropiedades;
		
		if(propiedad == null) {
			Propiedades.getInstance(rutaPropiedades);
		}
		String param = propiedad.getProperty(parametro);
		return param;
	}
	
	public static void main(String[] args) {
//		String fichero = "./properties/bd.properties";
//
//		Properties properties = new Properties();
//		
//		try {
//			properties.load(new FileInputStream(fichero));
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
//		String parametro = properties.getProperty("bd.nombre");
//		System.out.println(parametro);
		
//		String parametro = Propiedades.getParametro("bd.nombre");
//		System.out.println(parametro);
	}

}
