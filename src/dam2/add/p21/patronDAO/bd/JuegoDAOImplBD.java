package dam2.add.p21.patronDAO.bd;

import java.util.ArrayList;
import java.util.Scanner;

import dam2.add.p21.Main;
import dam2.add.p21.clasesPOJO.Pregunta;
import dam2.add.p21.clasesPOJO.Usuario;
import dam2.add.p21.patronDAO.Conexion;
import dam2.add.p21.pdf.FicheroPDF;
import dam2.add.p21.utilidades.Utilidades;
import interfaces.JuegoDAO;
import interfaces.PreguntasDAO;
import interfaces.UsuariosDAO;

public class JuegoDAOImplBD extends Conexion implements JuegoDAO {

	@Override
	public void juego() {

		// Variables Scanner
		Scanner respuesta = new Scanner(System.in);
		Scanner menu = new Scanner(System.in);
		Scanner usuario = new Scanner(System.in);

		int aciertos = 0;

		PreguntasDAOImplBD preguntasDAO = new PreguntasDAOImplBD();
		UsuariosDAOImpBD usuariosDAO = new UsuariosDAOImpBD();
		
		System.out.println(Main.rb.getString("juego.dao.bd.menu"));
		try {
			ArrayList<Pregunta> listaTotalPreguntas = preguntasDAO.getListaPreguntas();
			ArrayList<Usuario> listaUsuarios = usuariosDAO.getListaUsuarios();
					
			for (int i = 0; i < listaTotalPreguntas.size(); i++) {

				System.out.println(listaTotalPreguntas.get(i).toString());

				String respuestaUsuario = respuesta.nextLine();

				boolean respuestaValida = Utilidades.comprobarRespuesta(respuestaUsuario);
				while (!respuestaValida) {
					respuestaUsuario = respuesta.nextLine();
					respuestaValida = Utilidades.comprobarRespuesta(respuestaUsuario);
				}
				if (respuestaUsuario.equals(listaTotalPreguntas.get(i).getSolucion())) {
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
