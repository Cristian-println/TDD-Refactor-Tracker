package com.herramienta.integracion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class RegistradorRefactorizacionTest {

    private final String CLASE        = "  MiClase  ";
    private final String METODO       = " miMetodo() ";
    private final String RUTA_ESPEJO  = "MiClase/miMetodo";

    @AfterEach
    public void limpiar() {
        File carpeta = new File(RUTA_ESPEJO);
        if (carpeta.exists()) {
            File[] archivos = carpeta.listFiles();
            if (archivos != null) {
                for (File f : archivos) f.delete();
            }
            carpeta.delete();
        }
        new File("MiClase").delete();
    }

    @Test
    public void deberiaCubrirElFlujoCOmpleto() {

        RegistradorRefactorizacion registrador = new RegistradorRefactorizacion();

        boolean resultado = registrador.registrar(
                CLASE, METODO, "int x = 5;", true, "Carlos");

        assertTrue(resultado, "El pipeline completo debería devolver true");

        List<String> historial = registrador.consultarHistorial(CLASE, METODO);
        assertEquals(1, historial.size(), "Debería existir 1 archivo en el historial");

        String nombreArchivo = historial.get(0);
        assertTrue(nombreArchivo.startsWith("miMetodo_"), "El archivo debería llevar el nombre del método como prefijo");
        assertTrue(nombreArchivo.endsWith(".md"), "El archivo debería tener extensión .md");

        String veredicto = registrador.leerVeredicto(RUTA_ESPEJO + "/" + nombreArchivo);
        assertTrue(veredicto.contains("Aprobado"), "El veredicto debería indicar Aprobado");
        assertTrue(veredicto.contains("Carlos"),   "El veredicto debería contener el nombre del revisor");
    }

    @Test
    public void deberiaFallarConEntradaVacia() {

        RegistradorRefactorizacion registrador = new RegistradorRefactorizacion();

        boolean resultado = registrador.registrar(
                "", " miMetodo() ", "int x = 5;", true, "Ana");

        assertFalse(resultado, "Debería devolver false si la clase está vacía");
    }

    @Test
    public void deberiaAcumularVariosRegistrosEnElHistorial() throws InterruptedException {

        RegistradorRefactorizacion registrador = new RegistradorRefactorizacion();

        registrador.registrar(CLASE, METODO, "int a = 1;", true,  "Ana");
        Thread.sleep(1100); 
        registrador.registrar(CLASE, METODO, "int a = 2;", false, "Luis");

        List<String> historial = registrador.consultarHistorial(CLASE, METODO);

        assertEquals(2, historial.size(), "Debería haber 2 versiones acumuladas");
    }

    @Test
    public void deberiaRegistrarVeredictoFallidoCorrectamente() {

        RegistradorRefactorizacion registrador = new RegistradorRefactorizacion();

        registrador.registrar(CLASE, METODO, "int z = 0;", false, "Pedro");

        List<String> historial = registrador.consultarHistorial(CLASE, METODO);
        String nombreArchivo   = historial.get(0);
        String veredicto       = registrador.leerVeredicto(RUTA_ESPEJO + "/" + nombreArchivo);

        assertTrue(veredicto.contains("Fallido"), "El veredicto debería indicar Fallido");
        assertTrue(veredicto.contains("Pedro"),   "El veredicto debería contener el nombre del revisor");
    }
}