package com.herramienta.us01;

public class CapturadorEntorno {
	
	public String obtenerClaseLimpiada(String textoBruto) {
		return textoBruto.trim();
	}
	
	public String obtenerMetodoLimpio(String textoBruto) {
		return textoBruto.trim().replace("()", "");
	}
	
	public boolean validarTextoCapturado(String texto) {
		return !texto.isBlank();
	}

}
