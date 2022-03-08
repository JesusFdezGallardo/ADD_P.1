package dam2.add.p21.clasesPOJO;

public class Usuario implements Comparable<Usuario>{

	String usuario; 
	int aciertos;
	
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public int getAciertos() {
		return aciertos;
	}
	public void setAciertos(int aciertos) {
		this.aciertos = aciertos;
	}
	
	public Usuario(String usuario, int aciertos) {
		super();
		this.usuario = usuario;
		this.aciertos = aciertos;
	}
	@Override
	public String toString() {
		return usuario + ":" + aciertos;
	}
	@Override
	public int compareTo(Usuario o) {
		if (o.getAciertos() < aciertos) {
			return -1;
		} else if (o.getAciertos() < aciertos) {
			return 0;
		} else {
			return 1;
		}
	} 
	
	
}
