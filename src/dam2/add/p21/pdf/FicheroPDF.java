package dam2.add.p21.pdf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dam2.add.p21.Main;
import dam2.add.p21.clasesPOJO.Pregunta;
import dam2.add.p21.clasesPOJO.Usuario;

public class FicheroPDF {

	final static String RUTAPDF = "./ficheros/salida.pdf";
	final static String RUTAPREGUNTAS = "ficheros/preguntas.xml";
	
	public static void mostrarPDF(ArrayList<Pregunta> listaTotalPreguntas, Usuario usuario) {

		PdfWriter writer = null;
		Document documento = new Document(PageSize.A4, 20, 20, 70, 50);
		
	    try {      

	    	writer = PdfWriter.getInstance(documento, new FileOutputStream(RUTAPDF));
		    documento.open();
			Paragraph paragraph = new Paragraph();
			paragraph.setAlignment(Element.ALIGN_CENTER);
						
			paragraph.add(Main.rb.getString("fichero.pdf.informe"));
			paragraph.add("\n\n");
	    	documento.add(paragraph);
	    	
	    	//TABLAS cabecera
		    PdfPTable tabla = new PdfPTable(2);
		    Phrase texto = new Phrase(usuario.getUsuario());
			PdfPCell cabecera = new PdfPCell(texto);
					    
		    tabla.addCell(cabecera);
		    tabla.addCell(Main.rb.getString("fichero.pdf.aciertos") + String.valueOf(usuario.getAciertos()));
		    documento.add(tabla);
		    
		    //Cabecera titulos preguntas, respuestas y solucion
		    PdfPTable tablaCabecera = new PdfPTable(5);
		    tablaCabecera.addCell(Main.rb.getString("fichero.pdf.pregunta"));
		    tablaCabecera.addCell(Main.rb.getString("fichero.pdf.respuesta1"));
		    tablaCabecera.addCell(Main.rb.getString("fichero.pdf.respuesta2"));
		    tablaCabecera.addCell(Main.rb.getString("fichero.pdf.respuesta3"));
		    tablaCabecera.addCell(Main.rb.getString("fichero.pdf.respuestaok"));
		    documento.add(tablaCabecera);
	    			 
		    PdfPTable tablaPreguntas = new PdfPTable(5);
		    
		    for (int i = 0; i <listaTotalPreguntas.size(); i++) {
		    	tablaPreguntas.addCell(listaTotalPreguntas.get(i).getEnunciado());
		    	tablaPreguntas.addCell(listaTotalPreguntas.get(i).getRespuesta1());
		    	tablaPreguntas.addCell(listaTotalPreguntas.get(i).getRespuesta2());
		    	tablaPreguntas.addCell(listaTotalPreguntas.get(i).getRespuesta3());
		    	tablaPreguntas.addCell(listaTotalPreguntas.get(i).getSolucion());
		    }
	    	documento.add(tablaPreguntas);
		    
		    documento.close(); 
		    writer.close(); 

		    try {
		        File path = new File(RUTAPDF);
		        Desktop.getDesktop().open(path);
		    } catch (IOException ex) {
		        ex.printStackTrace();
		    }
			
	    } catch (Exception ex) {
	    	ex.getMessage();
	    }
	}
	

 public static void main(String[] args) {
	 Pregunta pregunta = new Pregunta("Hola", "1", "2", "3", "4"); 

	 Usuario usuario = new Usuario("pepe", 4);
//	 mostrarPDF(pregunta, usuario);
}
}
