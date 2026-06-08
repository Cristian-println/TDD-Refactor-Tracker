package com.herramienta.us02;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GestorMetadatosTDD {

    public boolean inyectarVeredicto(String rutaArchivo, String test, boolean estado, String revisor) {
        try {
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                return false;
            }

            FileWriter escritor = new FileWriter(archivo, true);
            String metadato = "\n\n<!-- tdd: " + test + "|" + estado + "|" + revisor + " -->";  
            escritor.write(metadato);
            escritor.close();

            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String extraerEtiquetaTooltip(String rutaArchivo) {
        try {
            List<String> lineas = Files.readAllLines(Path.of(rutaArchivo));

            for (String linea : lineas) {

                if (linea.contains("<!-- tdd:")) {                                      
                    String datosPuros = linea.replace("<!-- tdd:", "")                  
                                             .replace("-->", "")
                                             .trim();
                    String[] partes = datosPuros.split("\\|");

                    if (partes.length == 3) {
                        String nombreTest   = partes[0].trim();
                        String estadoStr    = partes[1].trim().equals("true") ? "Aprobado" : "Fallido";
                        String nombreRevisor = partes[2].trim();

                        return "Test: " + nombreTest + " | Estado: " + estadoStr + " | Revisor: " + nombreRevisor;
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Sin metadatos de revisión";
    }
}