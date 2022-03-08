package dam2.add.p21.patronDAO;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import dam2.add.p21.propiedades.Propiedades;

public class Conexion {

	protected static Connection conexion; //atributo para guardar el objeto Connection
	
    public static Connection getConexion() {
	    if (conexion == null) {
	    	crearConexion();
	    }
	    return conexion;
    }
    
    // devuelve true si se ha creado correctamente
    protected static boolean crearConexion() { 	
    	Properties propiedadesBD = new Properties();
    	try {
			propiedadesBD.load(new FileReader("./properties/bd.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	
    	String bd = propiedadesBD.getProperty("bd.nombre");
    	String login = propiedadesBD.getProperty("bd.login");
    	String password = propiedadesBD.getProperty("bd.password");
    	String host = propiedadesBD.getProperty("bd.host");
    	
    	try {
	        Class.forName("com.mysql.jdbc.Driver");
	    	String url = "jdbc:mysql://";
	    	conexion = DriverManager.getConnection(url + host + "/"+ bd, login, password);
	        
	    	conexion.setAutoCommit(false);
//	        System.out.println("Conexión correcta a la base de datos");
	        
	    } catch (SQLException e) {
	    	System.out.println(e);
	    	return false;
	    }
	    catch (Exception e) {
	    	System.out.println(e);
	    	return false;
	    }
	    return true;
    }

    public static void desconectar(){
    	try {
    		conexion.close();
            conexion = null;
//            System.out.println("La conexion a la base de datos ha terminado");
    	
    	} catch (SQLException e) {
    		System.out.println("Error al cerrar la conexion");
        }
    }
   
    public static void main(String[] args) {
//    	String parametroBD = Propiedades.getParametro(FICHEROPROPIEDADESBD,"bd.nombre");
//    	System.out.println(parametroBD);
		Connection c = Conexion.getConexion();
    	desconectar();

    }

}
