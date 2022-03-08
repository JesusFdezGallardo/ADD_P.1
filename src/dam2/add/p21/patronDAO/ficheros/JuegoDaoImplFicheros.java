package dam2.add.p21.patronDAO.ficheros;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import dam2.add.p21.Main;
import dam2.add.p21.clasesPOJO.Pregunta;
import dam2.add.p21.clasesPOJO.Usuario;
import dam2.add.p21.pdf.FicheroPDF;
import dam2.add.p21.utilidades.Utilidades;
import interfaces.JuegoDAO;

public class JuegoDaoImplFicheros implements JuegoDAO {

	final static String RUTAPREGUNTAS = "ficheros/preguntas.xml";

	@Override
	public void juego() {
		SAXBuilder builder = new SAXBuilder();

		File xmlFile = new File(RUTAPREGUNTAS);

		// Variables Scanner
		Scanner respuesta = new Scanner(System.in);
		Scanner menu = new Scanner(System.in);
		Scanner usuario = new Scanner(System.in);

		// variables
		int aciertos = 0;
		try {

			Document document = builder.build(xmlFile);

			// Se obtiene la raiz 'juego'
			Element rootNode = document.getRootElement();

			// Se obtiene el nombre del nodo raiz, "Juego"
			String nombreNodo = rootNode.getName();

			// Se obtiene la lista de hijos <preguntas> del nodo raiz
			List listaPreguntas = rootNode.getChildren("pregunta");
			Pregunta preguntaConstructor;

			PreguntasDAOImplFicheros preguntasDAO = new PreguntasDAOImplFicheros();
			UsuariosDAOImpFicheros usuariosDAO = new UsuariosDAOImpFicheros();

			ArrayList<Pregunta> listaTotalPreguntas = preguntasDAO.getListaPreguntas();
			ArrayList<Usuario> listaUsuarios = usuariosDAO.getListaUsuarios();

//			System.out.println(Main.rb.getString("juego.dao.bd.menu"));

			for (int i = 0; i < listaPreguntas.size(); i++) {

				Element pregunta = (Element) listaPreguntas.get(i);
				String txtPregunta = pregunta.getChildText("texto");

				String respuesta1 = pregunta.getChildText("respuesta1");

				String respuesta2 = pregunta.getChildText("respuesta2");

				String respuesta3 = pregunta.getChildText("respuesta3");

				String solucion = pregunta.getChildText("correcta");

				preguntaConstructor = new Pregunta(txtPregunta, respuesta1, respuesta2, respuesta3, solucion);
				System.out.println(preguntaConstructor.toString());

				// Creo arrayList con las preguntas
				listaTotalPreguntas.add(preguntaConstructor);

				String respuestaUsuario = respuesta.nextLine();
				boolean respuestaValida = Utilidades.comprobarRespuesta(respuestaUsuario);
				while (!respuestaValida) {
					respuestaUsuario = respuesta.nextLine();
					respuestaValida = Utilidades.comprobarRespuesta(respuestaUsuario);
				}
				if (respuestaUsuario.equals(preguntaConstructor.getSolucion())) {
					aciertos++;
					System.out.println(Main.rb.getString("utilidades.respuestaok"));
				} else {
					System.out.println(Main.rb.getString("utilidades.respuestano"));
				}
			}
			System.out.println(Main.rb.getString("juego.dao.bd.totalaciertos") + aciertos);

			System.out.println(Main.rb.getString("juego.dao.bd.nombre"));
			String user = usuario.nextLine();

			Usuario nuevoUsuario = new Usuario(user, aciertos);

			boolean usuarioExiste = usuariosDAO.comprobarUsuario(nuevoUsuario);

			if (usuarioExiste) {
				System.out.println(Main.rb.getString("juego.dao.bd.userOK"));
				usuariosDAO.comprobarRecord(listaUsuarios, nuevoUsuario);
			} else {
				System.out.println(Main.rb.getString("juego.dao.bd.userNuevo"));
				usuariosDAO.registrarUsuario(nuevoUsuario);
			}

			boolean opcionResumen = Utilidades.resumenPDF();
			if (opcionResumen) {
				FicheroPDF.mostrarPDF(listaTotalPreguntas, nuevoUsuario);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
