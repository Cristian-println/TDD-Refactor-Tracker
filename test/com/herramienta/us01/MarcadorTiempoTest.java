package com.herramienta.us01;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class MarcadorTiempoTest {

	@Test
	public void deberiaGenerarUnaMarcaDeTiempoSeguraParaArchivos() {

		MarcadorTiempo marcador = new MarcadorTiempo();
		
		String marca = marcador.generarMarcaSegura();
		
		assertNotNull(marca, "La marca de tiempo no debe ser nula");
		assertFalse(marca.isBlank(), "La marca de tiempo no debe estar vacía");
		
		assertTrue(marca.matches("\\d{8}_\\d{6}"), "La marca de tiempo debe cumplir con el formato seguro yyyyMMdd_HHmmss");
		
	}

}
