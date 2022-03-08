package dam2.add.p21.patronDAO.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;

import dam2.add.p21.clasesPOJO.Usuario;
import dam2.add.p21.patronDAO.Conexion;
import interfaces.UsuariosDAO;

public class UsuariosDAOImpBD extends Conexion implements UsuariosDAO {

	@Override
	public void registrarUsuario(Usuario usuario) throws Exception {
		String query = "INSERT INTO usuarios (nombre, aciertos) " + "VALUES ('" + usuario.getUsuario() + "', '"
				+ usuario.getAciertos() + "');";
		try {
			crearConexion();
			PreparedStatement st = conexion.prepareStatement(query);
			st.executeUpdate(query);
			conexion.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			desconectar();
		}
	}

	@Override
	public ArrayList<Usuario> getListaUsuarios() throws Exception {
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		String query = "SELECT nombre, aciertos FROM usuarios";
		try {
			crearConexion();
			PreparedStatement st = conexion.prepareStatement(query);
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Usuario usuario = new Usuario(rs.getString(1), Integer.parseInt(rs.getString(2)));
				listaUsuarios.add(usuario);
			}
			rs.close();
			st.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			desconectar();
		}
		return listaUsuarios;
	}

	@Override
	public void comprobarRecord(ArrayList<Usuario> usuariosLista, Usuario usuario) throws Exception {
		String query = "UPDATE usuarios SET aciertos =? WHERE nombre = ?";
		try {
			crearConexion();
			PreparedStatement ps = conexion.prepareStatement(query);
			for (int i = 0; i < usuariosLista.size(); i++) {
				if (usuariosLista.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
					if (usuariosLista.get(i).getAciertos() < usuario.getAciertos()) {
						String copiaAciertos = String.valueOf(usuario.getAciertos());
						ps.setString(1, copiaAciertos);
						ps.setString(2, usuario.getUsuario());
						int filasActualizadas = ps.executeUpdate();
						if (filasActualizadas > 0) {
							System.out.println("El usuario " + usuario.getUsuario() + " tenía "
									+ usuariosLista.get(i).getAciertos() + " y ahora tiene " + copiaAciertos);
							conexion.commit();
						}
					}
				}
			}
			ps.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			desconectar();
		}
	}

	@Override
	public void verRecords() throws Exception {
		ArrayList<Usuario> usuariosLista = getListaUsuarios();
		Collections.sort(usuariosLista);
		for (int i = 0; i < usuariosLista.size(); i++) {
			System.out.println(usuariosLista.get(i).getUsuario() + "--> " + usuariosLista.get(i).getAciertos());
		}
	}

	@Override
	public boolean comprobarUsuario(Usuario usuario) throws Exception {
		ArrayList<Usuario> usuariosLista = getListaUsuarios();
		for (int i = 0; i < usuariosLista.size(); i++) {
			if (usuariosLista.get(i).getUsuario().equalsIgnoreCase(usuario.getUsuario())) {
				return true;
			}
		}
		return false;
	}
}
