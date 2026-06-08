package com.herramienta.us02;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ExploradorArchivos {

	private String rutaBase;

	public ExploradorArchivos() {
		this.rutaBase = "."; 
	}

	public ExploradorArchivos(String rutaBase) {
		this.rutaBase = rutaBase;
	}

	public boolean validarExistencia(String rutaArchivo) {
		return new File(rutaArchivo).exists();
	}

	public List<String> listarArchivosRefactorizados() {
		List<String> listaMd = new ArrayList<>();
		File carpeta = new File(rutaBase);

		if (carpeta.exists() && carpeta.isDirectory()) {
			File[] archivos = carpeta.listFiles();
			if (archivos != null) {
				for (File archivo : archivos) {
					if (archivo.getName().endsWith(".md")) {
						listaMd.add(archivo.getName());
					}
				}
			}
		}
		return listaMd;
	}
}
