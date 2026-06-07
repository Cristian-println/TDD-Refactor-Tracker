package com.herramienta.us01;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MarcadorTiempo {
	
	public String generarMarcaSegura() {
		
		LocalDateTime ahora = LocalDateTime.now();
		DateTimeFormatter formatoSeguro = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		return ahora.format(formatoSeguro);
		
	}

}
