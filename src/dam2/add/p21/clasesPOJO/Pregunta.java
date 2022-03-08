package dam2.add.p21.clasesPOJO;

public class Pregunta {

	private String enunciado;
	private String respuesta1; 
	private String respuesta2; 
	private String respuesta3; 
	private String solucion;
	
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	public String getRespuesta1() {
		return respuesta1;
	}
	public void setRespuesta1(String respuesta1) {
		this.respuesta1 = respuesta1;
	}
	public String getRespuesta2() {
		return respuesta2;
	}
	public void setRespuesta2(String respuesta2) {
		this.respuesta2 = respuesta2;
	}
	public String getRespuesta3() {
		return respuesta3;
	}
	public void setRespuesta3(String respuesta3) {
		this.respuesta3 = respuesta3;
	}
	public String getSolucion() {
		return solucion;
	}
	public void setSolucion(String solucion) {
		this.solucion = solucion;
	}
	public Pregunta(String enunciado, String respuesta1, String respuesta2, String respuesta3, String solucion) {
		this.enunciado = enunciado;
		this.respuesta1 = respuesta1;
		this.respuesta2 = respuesta2;
		this.respuesta3 = respuesta3;
		this.solucion = solucion;
	}
	@Override
	public String toString() {
		return enunciado + "\n" + "1--> " + respuesta1 + "\n" + "2--> " + respuesta2
				+ "\n" + "3--> " + respuesta3;
	} 
	
	
}
