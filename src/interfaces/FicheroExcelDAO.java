package interfaces;

import dam2.add.p21.clasesPOJO.Pregunta;

public interface FicheroExcelDAO {

	public void crearExcel (Pregunta pregunta) throws Exception;
	public Pregunta leexExcel() throws Exception;
	public void convertirExcel(String fichero) throws Exception;
	
}
