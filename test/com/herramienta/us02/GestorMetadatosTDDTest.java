package com.herramienta.us02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GestorMetadatosTDDTest {

	private final String RUTA_PRUEBA = "archivo_prueba_metadatos.md";

	@BeforeEach
	public void prepararArchivoFalso() throws IOException {
		
		FileWriter escritor = new FileWriter(RUTA_PRUEBA);
		escritor.write("```java\nint a = 1;\n```");
		escritor.close();
	}

	@AfterEach
	public void limpiarArchivoFalso() {
		new File(RUTA_PRUEBA).delete();
	}

	@Test
	public void deberiaInyectarVeredictoYExtraerTooltip() {
		GestorMetadatosTDD gestor = new GestorMetadatosTDD();
		
		boolean inyectado = gestor.inyectarVeredicto(RUTA_PRUEBA, "deberiaValidarTexto", true, "Profesor");
		assertTrue(inyectado, "Debería devolver true al inyectar el metadato con éxito");
		
		String tooltipEsperado = "Test: deberiaValidarTexto | Estado: Aprobado | Revisor: Profesor";
		String tooltipObtenido = gestor.extraerEtiquetaTooltip(RUTA_PRUEBA);
		
		assertEquals(tooltipEsperado, tooltipObtenido, "El tooltip extraído debe tener el formato correcto");
	}
	
	@Test
	public void deberiaIndicarFalloSiEstadoEsFalse() {
		GestorMetadatosTDD gestor = new GestorMetadatosTDD();
		gestor.inyectarVeredicto(RUTA_PRUEBA, "deberiaFallar", false, "Samuel");
		
		String tooltip = gestor.extraerEtiquetaTooltip(RUTA_PRUEBA);
		assertTrue(tooltip.contains("Estado: Fallido"), "Debería traducir el false a 'Fallido'");
	}
}
