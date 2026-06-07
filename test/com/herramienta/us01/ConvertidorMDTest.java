package com.herramienta.us01;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class ConvertidorMDTest {

	@Test
	public void deberiaGuardarElCodigoEnUnArchivoMarkdown() throws Exception {
		
		ConvertidorMD convertidor = new ConvertidorMD();
		String ruta = "test_ruta";
		String nombreArchivo = "prueba_refactor.md";
		String codigoBruto = "int suma = 2 + 2;";
		
		boolean resultado = convertidor.guardarRefactorizacion(ruta, nombreArchivo, codigoBruto);
		
		assertTrue(resultado, "Debería devolver true si guardó el archivo sin errores");
		
		File archivoFisico = new File(ruta + "/" + nombreArchivo);
		assertTrue(archivoFisico.exists(), "El archivo .md debe existir físicamente en el disco");
		
		String contenidoLeido = Files.readString(Path.of(archivoFisico.getAbsolutePath()));
		String contenidoEsperado = "```java\n" + codigoBruto + "\n```";
		
		assertEquals(contenidoEsperado.trim(), contenidoLeido.trim());
		
		archivoFisico.delete();
		new File(ruta).delete();
	}
}