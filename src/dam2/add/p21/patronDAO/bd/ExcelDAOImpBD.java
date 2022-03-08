package dam2.add.p21.patronDAO.bd;

import java.io.File;

import dam2.add.p21.clasesPOJO.Pregunta;
import interfaces.FicheroExcelDAO;
import interfaces.PreguntasDAO;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelDAOImpBD implements FicheroExcelDAO{
	final static String RUTAEXCEL = "./ficheros/prueba.xls";

	@Override
	public void crearExcel(Pregunta ejemploPregunta) throws Exception {
		File fichero = new File(RUTAEXCEL);

		try {
			WritableWorkbook w = Workbook.createWorkbook(fichero);

			// Nombre de la hoja
			WritableSheet sheet = w.createSheet("Pregunta", 0);

			// Columna fila contenido
			Label pregunta = new Label(0, 0, "Pregunta");
			sheet.addCell(pregunta);
			Label respuesta1 = new Label(1, 0, "respuesta1");
			sheet.addCell(respuesta1);
			Label respuesta2 = new Label(2, 0, "respuesta2");
			sheet.addCell(respuesta2);
			Label respuesta3 = new Label(3, 0, "respuesta3");
			sheet.addCell(respuesta3);
			Label solucion = new Label(4, 0, "solucion");
			sheet.addCell(solucion);

			Label enunciado = new Label(0, 1, ejemploPregunta.getEnunciado());
			sheet.addCell(enunciado);
			Label pregunta1 = new Label(1, 1, ejemploPregunta.getRespuesta1());
			sheet.addCell(pregunta1);
			Label pregunta2 = new Label(2, 1, ejemploPregunta.getRespuesta2());
			sheet.addCell(pregunta2);
			Label pregunta3 = new Label(3, 1, ejemploPregunta.getRespuesta3());
			sheet.addCell(pregunta3);
			Label pregunta4 = new Label(4, 1, ejemploPregunta.getSolucion());
			sheet.addCell(pregunta4);

			w.write();
			w.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Pregunta leexExcel() throws Exception {
		File fichero = new File(RUTAEXCEL);

		Pregunta preguntaEjemplo = null;
		try {
			Workbook w = Workbook.getWorkbook(fichero);

			// Lectura primera hoja
			Sheet sheet = w.getSheet(0);

//			Pregunta pregunta = new Pregunta(sheet.getRow(0)); 
			String pregunta = sheet.getCell(0, 1).getContents();
			String pregunta1 = sheet.getCell(1, 1).getContents();
			String pregunta2 = sheet.getCell(2, 1).getContents();
			String pregunta3 = sheet.getCell(3, 1).getContents();
			String pregunta4 = sheet.getCell(4, 1).getContents();

			preguntaEjemplo = new Pregunta(pregunta, pregunta1, pregunta2, pregunta3, pregunta4);

			System.out.println(preguntaEjemplo.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return preguntaEjemplo;
	}

	@Override
	public void convertirExcel(String fichero) throws Exception {
		File file = new File(fichero);
		PreguntasDAO preguntaDao = new PreguntasDAOImplBD();
		
		Workbook wb = null;
		try {
			// Obtener el objeto del libro
			wb = Workbook.getWorkbook(file);
			if (wb != null) {
				// Obtenga el objeto del libro para obtener el objeto de la hoja de trabajo en
				// el libro
				Sheet[] sheets = wb.getSheets();
				if (sheets != null && sheets.length != 0) {
					// recorre todas las hojas de trabajo en el libro de trabajo
					for (int i = 0; i < sheets.length; i++) {
						// Obtenga el número de filas en la hoja de trabajo
						int rows = sheets[i].getRows();
						// atraviesa las líneas
						for (int j = 1; j < rows; j++) {
							// Obtener todas las celdas en la fila actual
							Cell[] cells = sheets[i].getRow(j);
							Pregunta nuevaPregunta = new Pregunta(String.valueOf(cells[0].getContents()),
									String.valueOf(cells[1].getContents()), String.valueOf(cells[2].getContents()),
									String.valueOf(cells[3].getContents()), String.valueOf(cells[4].getContents()));
							System.out.println(nuevaPregunta.toString());
							preguntaDao.registrarPregunta(nuevaPregunta);
						}
					}
				}
//				System.out.println("Leído correctamente:" + fichero + "\n");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			wb.close();
		}

		
	}

}
