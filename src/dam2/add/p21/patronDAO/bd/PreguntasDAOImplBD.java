package dam2.add.p21.patronDAO.bd;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dam2.add.p21.clasesPOJO.Pregunta;
import dam2.add.p21.patronDAO.Conexion;
import interfaces.PreguntasDAO;
import interfaces.UsuariosDAO;

public class PreguntasDAOImplBD extends Conexion implements PreguntasDAO {
	
	@Override
	public void registrarPregunta(Pregunta pregunta) throws Exception {
		String query = "INSERT INTO preguntas (Enunciado, Respuesta1, Respuesta2, Respuesta3, Solucion) " + "VALUES ('"
				+ pregunta.getEnunciado() + "', '" + pregunta.getRespuesta1() + "', '" + pregunta.getRespuesta2()
				+ "', '" + pregunta.getRespuesta3() + "', '" + pregunta.getSolucion() + "');";
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
	public ArrayList<Pregunta> getListaPreguntas() throws Exception {
		String query = "SELECT Enunciado, Respuesta1, Respuesta2, Respuesta3, Solucion FROM preguntas";
		ArrayList<Pregunta> listaPreguntas = new ArrayList<Pregunta>();
		try {
			crearConexion();
			PreparedStatement st = conexion.prepareStatement(query);
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				Pregunta pregunta = new Pregunta(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				listaPreguntas.add(pregunta);
			}
			rs.close();
			st.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			desconectar();
		}
		return listaPreguntas;
	}

}
