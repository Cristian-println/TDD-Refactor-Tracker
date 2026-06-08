package com.herramienta.us02;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExploradorArchivosTest {

	private final String RUTA_PRUEBA = "carpeta_test_us02";

	@BeforeEach
	public void prepararArchivosFalsos() throws IOException {
		
		new File(RUTA_PRUEBA).mkdirs();
		new File(RUTA_PRUEBA + "/refactor1.md").createNewFile();
		new File(RUTA_PRUEBA + "/refactor2.md").createNewFile();
		new File(RUTA_PRUEBA + "/apunte.txt").createNewFile(); 
	}

	@AfterEach
	public void limpiarArchivosFalsos() {
		
		File carpeta = new File(RUTA_PRUEBA);
		if (carpeta.exists()) {
			for (File f : carpeta.listFiles()) f.delete();
			carpeta.delete();
		}
	}

	@Test
	public void deberiaValidarSiUnArchivoExiste() {
		ExploradorArchivos explorador = new ExploradorArchivos();
		
		assertTrue(explorador.validarExistencia(RUTA_PRUEBA + "/refactor1.md"), "El archivo debería existir");
		assertFalse(explorador.validarExistencia(RUTA_PRUEBA + "/fantasma.md"), "Debería devolver false para un archivo que no existe");
	}

	@Test
	public void deberiaListarSoloArchivosMarkdown() {
		
		ExploradorArchivos explorador = new ExploradorArchivos(RUTA_PRUEBA);
		
		List<String> archivos = explorador.listarArchivosRefactorizados();
		
		assertEquals(2, archivos.size(), "Debería encontrar exactamente 2 archivos .md");
		assertTrue(archivos.contains("refactor1.md"));
		assertTrue(archivos.contains("refactor2.md"));
	}
}