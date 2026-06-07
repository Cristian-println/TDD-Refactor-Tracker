package com.herramienta.us01;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CapturadorEntornoTest {
	
	@Test
	public void deberiaQuitarEspaciosDelTexto() {
		
		CapturadorEntorno capturador = new CapturadorEntorno();
		String textoBruto = "  Texto_Sucio  " ;
		
		String textoLimpio = capturador.obtenerClaseLimpiada(textoBruto);
		
		assertEquals("Texto_Sucio", textoLimpio);
	}
	
	@Test 
	public void deberiaObtenerLimpioTexto() {
		
		CapturadorEntorno capturador = new CapturadorEntorno();
		String textoBruto = " Texto_sucio() ";
		
		String textoLimpio = capturador.obtenerMetodoLimpio(textoBruto);
		
		assertEquals("Texto_sucio", textoLimpio);
		
	}
	
	@Test 
	public void deberiaVerificarElTexto() {
		
		CapturadorEntorno capturador = new CapturadorEntorno();
		String textoVacio = "";
		
		boolean resultado = capturador.validarTextoCapturado(textoVacio);
		
		assertFalse(resultado);
			
	}
	
}
