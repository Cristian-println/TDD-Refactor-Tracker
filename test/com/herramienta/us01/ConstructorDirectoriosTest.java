package com.herramienta.us01;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class ConstructorDirectoriosTest {
	
	@Test
	public void deberiaDevolverFalseSiLaRutaNoExiste() {
	
		ConstructorDirectorios directorio = new ConstructorDirectorios();
		String rutaFalsa = "carpeta_que_no_existe_universo_paralelo";
		
		boolean resultado = directorio.existeRuta(rutaFalsa);
		
		assertFalse(resultado, "Debería devolver false porque la carpeta no existe en el disco");
		
	}
	
	@Test
	public void deberiaCrearLaRuta() { 
		
		ConstructorDirectorios directorio = new ConstructorDirectorios();
		
		String claseLimpia = "Utilidades"; 
		String metodoLimpio = "contarDigitosApagados"; 
		
		String rutaGenerada = directorio.crearRutaEspejo(claseLimpia, metodoLimpio);
		
		assertEquals("Utilidades/contarDigitosApagados", rutaGenerada);
		
	}

}
