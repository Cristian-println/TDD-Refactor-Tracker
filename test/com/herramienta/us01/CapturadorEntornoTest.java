package com.herramienta.us01;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CapturadorEntornoTest {
	
	@Test
	public void deberiaQuitarEspaciosDelTexto() {
		// Preparar (Arrange)
		CapturadorEntorno capturador = new CapturadorEntorno();
		String textoBruto = "  Texto_Sucio  " ;
		
		// Actuar (Act)
		String textoLimpio = capturador.obtenerClaseLimpiada(textoBruto);
		
		// Comprobar (Assert)
		assertEquals("Texto_Sucio", textoLimpio);
	}
	
}
