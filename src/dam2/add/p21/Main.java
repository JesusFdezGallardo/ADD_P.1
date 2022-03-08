package dam2.add.p21;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.jdom2.Content;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.util.IteratorIterable;

import dam2.add.p21.clasesPOJO.Pregunta;
import dam2.add.p21.clasesPOJO.Usuario;
import dam2.add.p21.patronDAO.Conexion;
import dam2.add.p21.patronDAO.bd.ExcelDAOImpBD;
import dam2.add.p21.patronDAO.bd.JuegoDAOImplBD;
import dam2.add.p21.patronDAO.bd.PreguntasDAOImplBD;
import dam2.add.p21.patronDAO.bd.UsuariosDAOImpBD;
import dam2.add.p21.patronDAO.ficheros.ExcelDAOImpFicheros;
import dam2.add.p21.patronDAO.ficheros.JuegoDaoImplFicheros;
import dam2.add.p21.patronDAO.ficheros.PreguntasDAOImplFicheros;
import dam2.add.p21.patronDAO.ficheros.UsuariosDAOImpFicheros;
import dam2.add.p21.propiedades.Propiedades;
import dam2.add.p21.utilidades.Utilidades;
import interfaces.FicheroExcelDAO;
import interfaces.JuegoDAO;
import interfaces.PreguntasDAO;
import interfaces.UsuariosDAO;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	final static String RUTAEXCEL = "./ficheros/importExcel.xls";
	private static final String FICHEROPERSISTENCIA = "./properties/persistencia.properties";
	static String opcionFinalPersistencia = null;
	static String idiomaElegido;
	static JuegoDAO juego;
	static PreguntasDAO preguntaDAO; 
	static UsuariosDAO userDAO; 
	static FicheroExcelDAO excel;
	
	public static Locale locale; 
	public static ResourceBundle rb; 
	
	public static void main(String[] args) throws Exception {

		boolean cerrarMenu = false;
		int opcion;
		int opcionPersistencia;

//		//Idioma
		Properties propiedadesIdioma = new Properties();
		try {
			propiedadesIdioma.load(new FileReader("./properties/idioma.properties"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//Log
		PropertyConfigurator.configure("./properties/log4j.properties");
		
		Scanner idioma = new Scanner(System.in);
		System.out.println("Idioma: 1 Castellano / 2 Inglés");
		int opcionIdioma = idioma.nextInt();
		
		boolean respuestaIdioma = Utilidades.comprobarRespuestaEntero(opcionIdioma); 		
		while (!respuestaIdioma) {
			opcionIdioma = idioma.nextInt();
			respuestaIdioma = Utilidades.comprobarRespuestaEntero(opcionIdioma);
		}
		
		switch (opcionIdioma) {
		case 1:
			idiomaElegido =  "es"; //propiedadesIdioma.getProperty("language.es");
			log.info("Idioma elegido ->" + idiomaElegido);
			break;
		case 2: 
			idiomaElegido = "en"; //propiedadesIdioma.getProperty("language.en");
			log.info("Idioma elegido ->" + idiomaElegido);
			break;
		default:
			System.out.println(rb.getString("menu.idioma.default"));
			idiomaElegido = propiedadesIdioma.getProperty("language.es");
			log.warn("Error elección de lenguaje");
			break;
		}
		
		locale = new Locale(idiomaElegido);
		rb = ResourceBundle.getBundle("idioma", locale);	
		
		// Sistema elección persistencia de los datos
		Scanner modoPersistencia = new Scanner(System.in);
		System.out.println(rb.getString("menu"));
		opcionPersistencia = modoPersistencia.nextInt();

		boolean comprobarModoPersistencia = Utilidades.comprobarRespuestaEntero(opcionPersistencia); 		
		while (!comprobarModoPersistencia) {
			opcionPersistencia = modoPersistencia.nextInt();
			respuestaIdioma = Utilidades.comprobarRespuestaEntero(opcionPersistencia);
		}

		if (opcionPersistencia == 1) {
			opcionFinalPersistencia = Propiedades.getParametro(FICHEROPERSISTENCIA, "persistencia.bd");
			System.out.println(opcionFinalPersistencia);
			juego = new JuegoDAOImplBD();
			preguntaDAO = new PreguntasDAOImplBD();
			userDAO = new UsuariosDAOImpBD();
			excel = new ExcelDAOImpBD(); 
			log.info("Metodo persistencia ->" + opcionFinalPersistencia);
		} else {
			opcionFinalPersistencia = Propiedades.getParametro(FICHEROPERSISTENCIA, "persistencia.txt");
			System.out.println(opcionFinalPersistencia);
			juego = new JuegoDaoImplFicheros();
			preguntaDAO = new PreguntasDAOImplFicheros();
			userDAO = new UsuariosDAOImpFicheros();
			excel = new ExcelDAOImpFicheros(); 
			log.info("Metodo persistencia ->" + opcionFinalPersistencia);
		}

		while (!cerrarMenu) {

			System.out.println(rb.getString("menu.menu"));
			System.out.println(rb.getString("menu.menu.jugar"));
			System.out.println(rb.getString("menu.menu.nuevaPregunta"));
			System.out.println(rb.getString("menu.menu.importar"));
			System.out.println(rb.getString("menu.menu.records"));
			System.out.println(rb.getString("menu.menu.instrucciones"));
			System.out.println(rb.getString("menu.menu.cerrar"));

			Scanner entrada = new Scanner(System.in);
			opcion = entrada.nextInt();

			switch (opcion) {
			case 1:
				System.out.println(rb.getString("menu.menu.jugar"));
				juego.juego();
				log.info("Opción de usuario -> Juego");
				break;
			case 2:
				System.out.println(rb.getString("menu.menu.nuevaPregunta"));
				menuNuevaPreguntaBD(preguntaDAO);
				log.info("Opción de usuario -> Nueva Pregunta");
				break;
			case 3:
				System.out.println(rb.getString("menu.menu.importar"));
				menuImportarPregunta(excel, preguntaDAO);
				log.info("Opción de usuario -> Importar Pregunta");
				break;
			case 4:
				System.out.println(rb.getString("menu.menu.records"));
				userDAO.verRecords();
				log.info("Opción de usuario -> Ver Records");
				break;
			case 5:
				System.out.println(rb.getString("menu.menu.instrucciones"));
				instrucciones();
				log.info("Opción de usuario -> Instrucciones  ");
				break;
			case 0:
				System.out.println(rb.getString("menu.menu.cerrar"));
				cerrarMenu = true;
				break;
			default:
				System.out.println(rb.getString("menu.default"));
				log.warn("Error en la elección del menú");
			}
		} // Cierre While
	}

	public static void menuNuevaPreguntaBD(PreguntasDAO preguntaDAO) throws Exception {
		// Variables añadir por pantalla datos
		Scanner preguntaNueva = new Scanner(System.in);
		Scanner respuestaNueva1 = new Scanner(System.in);
		Scanner respuestaNueva2 = new Scanner(System.in);
		Scanner respuestaNueva3 = new Scanner(System.in);
		Scanner solucionNueva = new Scanner(System.in);

		// Pregunto usuario preguntas a rellenar
		System.out.println(rb.getString("menu.nuevapregunta"));
		String pregunta = preguntaNueva.nextLine();
		System.out.println(rb.getString("menu.nuevapregunta.respuesta1"));
		String respuesta1 = respuestaNueva1.nextLine();
		System.out.println(rb.getString("menu.nuevapregunta.respuesta2"));
		String respuesta2 = preguntaNueva.nextLine();
		System.out.println(rb.getString("menu.nuevapregunta.respuesta3"));
		String respuesta3 = preguntaNueva.nextLine();
		System.out.println(rb.getString("menu.nuevapregunta.solucion"));
		String solucion = preguntaNueva.nextLine();
		boolean respuestaValida = Utilidades.comprobarRespuesta(solucion);
		while (!respuestaValida) {
			solucion = solucionNueva.nextLine();
			respuestaValida = Utilidades.comprobarRespuesta(solucion);
		}

		Pregunta nuevaPregunta = new Pregunta(pregunta, respuesta1, respuesta2, respuesta3, solucion);

		preguntaDAO.registrarPregunta(nuevaPregunta);
	}

	public static void menuImportarPregunta(FicheroExcelDAO excel, PreguntasDAO preguntaDAo) throws Exception {
		Scanner entrada = new Scanner(System.in);
		boolean cerrarMenu = false;

		while (!cerrarMenu) {

			System.out.println(rb.getString("menu.importarpregunta"));
			int opcion = entrada.nextInt();

			switch (opcion) {
			case 1:
				System.out.println(rb.getString("menu.importarpregunta.crearExcel"));
				// Variables añadir por pantalla datos
				Scanner preguntaNueva = new Scanner(System.in);
				Scanner respuestaNueva1 = new Scanner(System.in);
				Scanner respuestaNueva2 = new Scanner(System.in);
				Scanner respuestaNueva3 = new Scanner(System.in);
				Scanner solucionNueva = new Scanner(System.in);

				// Pregunto usuario preguntas a rellenar
				System.out.println(rb.getString("menu.nuevapregunta"));
				String pregunta = preguntaNueva.nextLine();
				System.out.println(rb.getString("menu.nuevapregunta.respuesta1"));
				String respuesta1 = respuestaNueva1.nextLine();
				System.out.println(rb.getString("menu.nuevapregunta.respuesta2"));
				String respuesta2 = preguntaNueva.nextLine();
				System.out.println(rb.getString("menu.nuevapregunta.respuesta3"));
				String respuesta3 = preguntaNueva.nextLine();
				System.out.println(rb.getString("menu.nuevapregunta.solucion"));
				String solucion = preguntaNueva.nextLine();

				Pregunta nuevaPregunta = new Pregunta(pregunta, respuesta1, respuesta2, respuesta3, solucion);

				excel.crearExcel(nuevaPregunta);
				try {
					preguntaDAo.registrarPregunta(nuevaPregunta);
				} catch (NullPointerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case 2:
				excel.convertirExcel(RUTAEXCEL);
				break;

			case 3:
				cerrarMenu = true;
				break;
			default:
				System.out.println(rb.getString("menu.default.1"));
			}
		}
	}

	public static void instrucciones() {
		String mensaje = rb.getString("menu.instrucciones");
		System.out.println(mensaje);
	}
	
} // fin clase
