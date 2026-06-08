package com.herramienta.integracion;

import com.herramienta.us01.CapturadorEntorno;
import com.herramienta.us01.ConstructorDirectorios;
import com.herramienta.us01.ConvertidorMD;
import com.herramienta.us01.MarcadorTiempo;
import com.herramienta.us02.ExploradorArchivos;
import com.herramienta.us02.GestorMetadatosTDD;
import java.util.List;

public class RegistradorRefactorizacion {

    private final CapturadorEntorno    capturador;
    private final ConstructorDirectorios constructor;
    private final ConvertidorMD        convertidor;
    private final MarcadorTiempo       marcador;
    private final GestorMetadatosTDD   gestor;

    public RegistradorRefactorizacion() {
        this.capturador   = new CapturadorEntorno();
        this.constructor  = new ConstructorDirectorios();
        this.convertidor  = new ConvertidorMD();
        this.marcador     = new MarcadorTiempo();
        this.gestor       = new GestorMetadatosTDD();
    }

    public boolean registrar(
            String claseBruta,
            String metodoBruto,
            String codigo,
            boolean estadoTest,
            String revisor) {

        if (!capturador.validarTextoCapturado(claseBruta)
                || !capturador.validarTextoCapturado(metodoBruto)) {
            return false;
        }

        String claseLimpia   = capturador.obtenerClaseLimpiada(claseBruta);
        String metodoLimpio  = capturador.obtenerMetodoLimpio(metodoBruto);

        String ruta          = constructor.crearRutaEspejo(claseLimpia, metodoLimpio);
        String marca         = marcador.generarMarcaSegura();
        String nombreArchivo = metodoLimpio + "_" + marca + ".md";

        boolean guardado = convertidor.guardarRefactorizacion(ruta, nombreArchivo, codigo);
        if (!guardado) {
            return false;
        }

        return gestor.inyectarVeredicto(ruta + "/" + nombreArchivo, metodoLimpio, estadoTest, revisor);
    }

    public List<String> consultarHistorial(String claseBruta, String metodoBruto) {

        String claseLimpia  = capturador.obtenerClaseLimpiada(claseBruta);
        String metodoLimpio = capturador.obtenerMetodoLimpio(metodoBruto);
        String ruta         = constructor.crearRutaEspejo(claseLimpia, metodoLimpio);

        ExploradorArchivos explorador = new ExploradorArchivos(ruta);
        return explorador.listarArchivosRefactorizados();
    }

    public String leerVeredicto(String rutaCompleta) {
        return gestor.extraerEtiquetaTooltip(rutaCompleta);
    }
}
