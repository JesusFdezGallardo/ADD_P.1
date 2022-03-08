package dam2.add.p21.utilidades;

import java.util.InputMismatchException;
import java.util.Scanner;

import dam2.add.p21.Main;

public class Utilidades {

	public static boolean resumenPDF() {

		Scanner entrada = new Scanner(System.in);

		System.out.println(Main.rb.getString("utilidades.resumenpdf"));

		String respuestaUsuario = entrada.nextLine();
		boolean respuestaValida = comprobarRespuestaPDF(respuestaUsuario);
		while (respuestaValida == false) {
			respuestaUsuario = entrada.nextLine();
			respuestaValida = comprobarRespuestaPDF(respuestaUsuario);
		}
		if (respuestaUsuario.equals("1")) {
			System.out.println(Main.rb.getString("utilidades.si"));
			return true;
		} else {
			System.out.println(Main.rb.getString("utilidades.no"));
			return false;
		}
	}

	public static boolean comprobarRespuesta(String respuesta) {
		try {
			int respuestaEntero = Integer.parseInt(respuesta);
			if (respuestaEntero > 3 || respuestaEntero <= 0) {
				System.out.println(Main.rb.getString("menu.default.1"));
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println(Main.rb.getString("utilidades.valorentero"));
			return false;
		}
	}

	public static boolean comprobarRespuestaPDF(String respuesta) {
		try {
			int respuestaEntero = Integer.parseInt(respuesta);
			if (respuestaEntero > 2 || respuestaEntero <= 0) {
				System.out.println("Introduce respuesta entre 1 y 2");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println(Main.rb.getString("utilidades.valorentero"));
		}
		return false;
	}
	
	public static boolean comprobarRespuestaEntero(int respuesta) {
		try {
			if (respuesta > 2 || respuesta <= 0) {
				System.out.println("Introduce respuesta entre 1 y 2");
				return false;
			} else {
				return true;
			}
		} catch (NumberFormatException e) {
			System.out.println(Main.rb.getString("utilidades.valorentero"));
		}
		return false;
	}

}
