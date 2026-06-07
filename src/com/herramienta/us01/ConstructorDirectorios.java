package com.herramienta.us01;
import java.io.File;

public class ConstructorDirectorios {

	public boolean existeRuta(String ruta) {
		return new File(ruta).exists();
	}
	
	public String crearRutaEspejo(String clase, String metodo) {
		return clase + "/" + metodo;
	}
	
}
