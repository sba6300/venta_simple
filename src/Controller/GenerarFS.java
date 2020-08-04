/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Clases.DocumentoSunat;
import Clases.Empresa;
import Clases.Numero_Letras;
import Clases.Venta;
import Clases.VentaProducto;
import Clases.cl_conectar;
import Clases.cl_varios;
import Printer.leer_numeros;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 *
 * @author luis
 */
public class GenerarFS {

    Venta venta = new Venta();
    Empresa empresa = new Empresa();
    DocumentoSunat tido = new DocumentoSunat();
    Numero_Letras numero_Letras=new Numero_Letras();
    VentaProducto ventaProducto=new VentaProducto();

    cl_varios c_varios = new cl_varios();
    cl_conectar c_conectar = new cl_conectar();

    private int id;

    public GenerarFS(int id) {
        this.id = id;
        venta.setId(this.id);
        venta.obtenerDatos();
        empresa.obtenerDatos();
        tido.setId(venta.getIdtido());
        tido.obtenerDatos();
        ventaProducto.setIdVenta(this.id);
        empresa.setUrl("sunat");
        System.out.println(empresa.toString());
    }

    public int getId() {
        return id;
    }
    private void generarTRI(){
    //1000|IGV|VAT|100.00|18.00|
    double base =venta.getTotal() /1.18;
    double igv =base *0.18;
    String titulo = empresa.getRuc() + "-" + tido.getCodsunat() + "-" + venta.getSerie() + "-" + venta.getNumero() + ".TRI";
        String TRI ="1000|IGV|VAT|"+c_varios.formato_totales(base)+"|"+c_varios.formato_totales(igv)+"|";
        
         String sdirectorio = empresa.getUrl();
        File directorio = new File(sdirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        FileWriter fichero = null;
        PrintWriter pw = null;
        
        try {
            fichero = new FileWriter(directorio + File.separator + titulo);
            pw = new PrintWriter(fichero);
            System.out.println(TRI);
            pw.println(TRI);
        } catch (IOException ex) {
            Logger.getLogger(GenerarFS.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    private void generarLEY(){
    //1000|SON:CIENTO DIECIOCHO CON 00/100 SOLES |
    String titulo = empresa.getRuc() + "-" + tido.getCodsunat() + "-" + venta.getSerie() + "-" + venta.getNumero() + ".LEY";
    String ley="1000|SON:"+numero_Letras.Convertir(venta.getTotal()+"", true)+" |";
        
     
        String sdirectorio = empresa.getUrl();
        File directorio = new File(sdirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        FileWriter fichero = null;
        PrintWriter pw = null;
        
        try {
            fichero = new FileWriter(directorio + File.separator + titulo);
            pw = new PrintWriter(fichero);
            System.out.println(ley);
            pw.println(ley);
        } catch (IOException ex) {
            Logger.getLogger(GenerarFS.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
            
    }
    private void generarDET(){
        //4A|1|44||PRODUCTO 1, 1212, 121|100.00|18.00|1000|18.00|100.00|IGV|VAT|10|18|0000|0.00|0.00|||01|0|-|0.00|0.00|||0|118.00|100.00|0.00|
        String titulo = empresa.getRuc() + "-" + tido.getCodsunat() + "-" + venta.getSerie() + "-" + venta.getNumero() + ".DET";
        String detalle = ventaProducto.genDetString(); 
     
        String sdirectorio = empresa.getUrl();
        File directorio = new File(sdirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }
        FileWriter fichero = null;
        PrintWriter pw = null;
        
        try {
            fichero = new FileWriter(directorio + File.separator + titulo);
            pw = new PrintWriter(fichero);
            System.out.print(detalle);
            pw.println(detalle);
        } catch (IOException ex) {
            Logger.getLogger(GenerarFS.class.getName()).log(Level.SEVERE, null, ex);
        }finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
        
        
    }
    private void generarCAB() {
        String titulo = empresa.getRuc() + "-" + tido.getCodsunat() + "-" + venta.getSerie() + "-" + venta.getNumero() + ".CAB";

        String sdirectorio = empresa.getUrl();
        File directorio = new File(sdirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            String tipo_cliente = "0";

            if (venta.getDoc()!=null&&venta.getDoc().length() == 8) {
                tipo_cliente = "1";
            }
            if (venta.getDoc()!=null&&venta.getDoc().length() == 11) {
                tipo_cliente = "6";
            }

            if (venta.getDoc()== null||venta.getDoc().equals("")) {
                venta.setDoc("0");
            }

            String igv = c_varios.formato_numero(venta.getTotal() / 1.18 * 0.18);
            String subtotal = c_varios.formato_numero(venta.getTotal() / 1.18);
            String total = c_varios.formato_numero(venta.getTotal());

            fichero = new FileWriter(directorio + File.separator + titulo);
            pw = new PrintWriter(fichero);
            String linea = "0101 |"
                    + venta.getFecha() + "|"
                    + venta.getHora() + "|"
                    + venta.getFecha() + "|"
                    + "0000 |"
                    + tipo_cliente + "|"
                    + venta.getDoc() + "|"
                    + venta.getNombre() + "|"
                    + "PEN|"
                    + igv + "|"
                    + subtotal + "|"
                    + total + "|"
                    + "0|"
                    + "0|"
                    + "0|"
                    + total + "|"
                    + "2.1|"
                    + "2.0|";
            System.out.println(linea);
            pw.println(linea);
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }
    
    public void generar_archivos () {
        System.out.println(venta.toString());
        generarCAB();
        generarDET();
        generarTRI();
        generarLEY();
    }
}
