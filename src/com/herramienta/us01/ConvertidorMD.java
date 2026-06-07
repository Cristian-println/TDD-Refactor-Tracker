package com.herramienta.us01;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConvertidorMD {

	public boolean guardarRefactorizacion(String ruta, String nombreArchivo, String codigo) {
		try {

			File directorio = new File(ruta);
			if (!directorio.exists()) {
				directorio.mkdirs();
			}

			File archivo = new File(directorio, nombreArchivo);
			
			FileWriter escritor = new FileWriter(archivo);
			escritor.write("```java\n");
			escritor.write(codigo + "\n");
			escritor.write("```");
			
			escritor.close();
			return true;
			
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
