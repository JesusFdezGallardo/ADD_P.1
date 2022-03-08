package interfaces;

import java.util.ArrayList;

import dam2.add.p21.clasesPOJO.Pregunta;
import dam2.add.p21.clasesPOJO.Usuario;

public interface PreguntasDAO {

	public void registrarPregunta (Pregunta pregunta) throws Exception;
	public ArrayList<Pregunta> getListaPreguntas() throws Exception; 
	
}
