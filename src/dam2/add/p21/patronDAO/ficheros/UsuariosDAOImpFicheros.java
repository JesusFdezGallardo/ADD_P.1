package dam2.add.p21.patronDAO.ficheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.mysql.fabric.xmlrpc.base.Array;

import dam2.add.p21.clasesPOJO.Usuario;
import interfaces.UsuariosDAO;

public class UsuariosDAOImpFicheros implements UsuariosDAO{
	final static String RUTAPREGUNTAS = "ficheros/preguntas.xml";
	final static String RUTARECORDS = "./ficheros/records.txt";
	final static String RUTAEXCEL = "./ficheros/prueba.xls";
	
	@Override
	public void registrarUsuario(Usuario usuario) throws Exception {
		BufferedWriter bw = null;

		try {

			bw = new BufferedWriter(new FileWriter(new File(RUTARECORDS), true));

			bw.write(usuario.getUsuario() + ":" + usuario.getAciertos());

			bw.newLine();
		} catch (IOException errorDeFichero) {
			System.out.println("Ha habido problemas: " + errorDeFichero.getMessage());
		} finally {
			try {
				bw.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	@Override
	public ArrayList<Usuario> getListaUsuarios() throws Exception {
		String linea;
		Usuario usuario;
		ArrayList<Usuario> usuariosLista = new ArrayList<Usuario>();
		try {

			FileReader lectura = new FileReader(RUTARECORDS);
			BufferedReader bf = new BufferedReader(lectura);
			while ((linea = bf.readLine()) != null) {
				String[] listaUsuarios = linea.split(":");
				usuario = new Usuario(listaUsuarios[0], Integer.parseInt(listaUsuarios[1]));
				usuariosLista.add(usuario);
//				System.out.println(usuario.toString());
			}
			bf.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
		return usuariosLista;
	}

	@Override
	public void verRecords() throws Exception {
		ArrayList<Usuario> usuariosLista = getListaUsuarios();
		try {

			for (int i = 0; i < usuariosLista.size(); i++) {
				System.out.println(usuariosLista.get(i).getUsuario() + "--> " + usuariosLista.get(i).getAciertos());
			}

		} catch (Exception e) {
			System.out.println("No se encontró el archivo");
		}
	}

	@Override
	public void comprobarRecord(ArrayList<Usuario> usuariosLista, Usuario usuario) throws Exception {
		try {

			BufferedWriter escritura = new BufferedWriter(new FileWriter(RUTARECORDS));

			for (int i = 0; i < usuariosLista.size(); i++) {
				if (usuariosLista.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
					if (usuariosLista.get(i).getAciertos() < usuario.getAciertos()) {
						usuariosLista.get(i).setAciertos(usuario.getAciertos());
					}
				}
			} /// Cierra for

			Collections.sort(usuariosLista);

			for (int j = 0; j < usuariosLista.size(); j++) {
				escritura.write(usuariosLista.get(j).toString());
				escritura.newLine();
			}
			escritura.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean comprobarUsuario(Usuario usuario) throws Exception {
		ArrayList<Usuario> usuariosLista = getListaUsuarios();
		for (int i = 0; i < usuariosLista.size(); i++) {
			if (usuariosLista.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
				return true;
			}
		}
//		System.out.println("El usuario no coincide");
		return false;
	}
}
