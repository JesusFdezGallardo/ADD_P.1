package interfaces;

import java.util.ArrayList;

import dam2.add.p21.clasesPOJO.Usuario;

public interface UsuariosDAO {

	public void registrarUsuario (Usuario usuario) throws Exception;
	public ArrayList<Usuario> getListaUsuarios() throws Exception; 
	public void comprobarRecord(ArrayList<Usuario> usuariosLista, Usuario usuario) throws Exception;
	public void verRecords () throws Exception;
	public boolean comprobarUsuario(Usuario usuario) throws Exception;
}
