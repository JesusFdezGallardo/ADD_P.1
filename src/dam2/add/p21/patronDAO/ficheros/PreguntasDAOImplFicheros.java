package dam2.add.p21.patronDAO.ficheros;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import dam2.add.p21.Main;
import dam2.add.p21.clasesPOJO.Pregunta;
import interfaces.PreguntasDAO;

public class PreguntasDAOImplFicheros implements PreguntasDAO {
	final static String RUTAPREGUNTAS = "ficheros/preguntas.xml";
	final static String RUTARECORDS = "./ficheros/records.txt";
	final static String RUTAEXCEL = "./ficheros/prueba.xls";

	@Override
	public void registrarPregunta(Pregunta nuevaPregunta) throws Exception {
		String docNuevoStr = "";

		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(RUTAPREGUNTAS);

		Document document = builder.build(xmlFile);

		// Obtengo raiz "juego"
		Element rootNode = document.getRootElement();

		Element raizPregunta = new Element("pregunta");
		rootNode.addContent(raizPregunta);
		Element contenidoPregunta = new Element("texto");
		raizPregunta.addContent(contenidoPregunta);
		contenidoPregunta.setText(nuevaPregunta.getEnunciado());

		Element nuevaRespuesta = new Element("respuesta1");
		raizPregunta.addContent(nuevaRespuesta);
		nuevaRespuesta.setText(nuevaPregunta.getRespuesta1());

		nuevaRespuesta = new Element("respuesta2");
		raizPregunta.addContent(nuevaRespuesta);
		nuevaRespuesta.setText(nuevaPregunta.getRespuesta2());

		nuevaRespuesta = new Element("respuesta3");
		raizPregunta.addContent(nuevaRespuesta);
		nuevaRespuesta.setText(nuevaPregunta.getRespuesta3());

		nuevaRespuesta = new Element("correcta");
		raizPregunta.addContent(nuevaRespuesta);
		nuevaRespuesta.setText(nuevaPregunta.getSolucion());

		Format format = Format.getPrettyFormat();

		XMLOutputter xmloutputter = new XMLOutputter(format);

		docNuevoStr = xmloutputter.outputString(document);

		System.out.println(docNuevoStr);

		FileWriter fichero = null;
		try {
			fichero = new FileWriter(RUTAPREGUNTAS);
			PrintWriter pw = new PrintWriter(fichero);
			pw.println(docNuevoStr);
			fichero.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("No existe el fichero");
		}
	}

	@Override
	public ArrayList<Pregunta> getListaPreguntas() throws Exception {
		ArrayList<Pregunta> listaTotalPreguntas = new ArrayList<Pregunta>();
		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(RUTAPREGUNTAS);
		Document document = builder.build(xmlFile);

		// Se obtiene la raiz 'juego'
		Element rootNode = document.getRootElement();

		// Se obtiene el nombre del nodo raiz, "Juego"
		String nombreNodo = rootNode.getName();

		// Se obtiene la lista de hijos <preguntas> del nodo raiz
		List listaPreguntas = rootNode.getChildren("pregunta");
		Pregunta preguntaConstructor;

		System.out.println(Main.rb.getString("juego.dao.bd.menu"));

		for (int i = 0; i < listaPreguntas.size(); i++) {

			Element pregunta = (Element) listaPreguntas.get(i);
			String txtPregunta = pregunta.getChildText("texto");

			String respuesta1 = pregunta.getChildText("respuesta1");

			String respuesta2 = pregunta.getChildText("respuesta2");

			String respuesta3 = pregunta.getChildText("respuesta3");

			String solucion = pregunta.getChildText("correcta");

			preguntaConstructor = new Pregunta(txtPregunta, respuesta1, respuesta2, respuesta3, solucion);

			// Creo arrayList con las preguntas
			listaTotalPreguntas.add(preguntaConstructor);
		}
		return listaTotalPreguntas;
	}
	
}
