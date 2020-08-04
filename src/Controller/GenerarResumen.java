/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.Empresa;
import Clases.Venta;
import Clases.cl_varios;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author Mariela
 */
public class GenerarResumen {

    Venta venta = new Venta();
    Empresa empresa = new Empresa();
    cl_varios varios = new cl_varios();

    private String fecha;

    public GenerarResumen(String fecha) {
        this.fecha = fecha;
        empresa.obtenerDatos();
        venta.setFecha(fecha);
        venta.genRDIString();
    }

   private void generarRDI() {
        
        String detalle = venta.getRci().trim();
        

        String titulo = empresa.getRuc() + "-RC-" + varios.formato_FS(fecha) + "-001.rdi";

        String sdirectorio = empresa.getUrl();
        //System.out.println(sdirectorio);
        File directorio = new File(sdirectorio);
        if (!directorio.exists()) {
            JOptionPane.showMessageDialog(null, "ESTA CARPETA NO EXISTE");
        //    directorio.mkdirs();
        }
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            fichero = new FileWriter(directorio + File.separator + titulo);
            pw = new PrintWriter(fichero);
            System.out.print(detalle);
            pw.println(detalle);
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    private void generarTRD() {
        
        String detalle = venta.getTrd().trim();
        

        String titulo = empresa.getRuc() + "-RC-" + varios.formato_FS(fecha) + "-001.trd";

        String sdirectorio = empresa.getUrl();
        //System.out.println(sdirectorio);
        File directorio = new File(sdirectorio);
        if (!directorio.exists()) {
            JOptionPane.showMessageDialog(null, "ESTA CARPETA NO EXISTE");
        //    directorio.mkdirs();
        }
        FileWriter fichero = null;
        PrintWriter pw = null;

        try {
            fichero = new FileWriter(directorio + File.separator + titulo);
            pw = new PrintWriter(fichero);
            System.out.print(detalle);
            pw.println(detalle);
        } catch (IOException ex) {
            System.out.println(ex.getLocalizedMessage());
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public void generarArchivos () {
        generarRDI();
        generarTRD();
    }
}
