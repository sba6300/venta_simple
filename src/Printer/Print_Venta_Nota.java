/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Printer;

import Clases.Empresa;
import Clases.Venta;
import Clases.VentaProducto;
import Clases.cl_conectar;
import Clases.cl_varios;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;

/**
 *
 * @author gerenciatecnica
 */
public class Print_Venta_Nota {

    cl_conectar c_conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    Venta c_venta = new Venta();
    VentaProducto c_detalle = new VentaProducto();
    Empresa empresa = new Empresa();
    leer_numeros leer = new leer_numeros();

    private int id_venta;

    public Print_Venta_Nota() {
    }

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public void generar_ticket() {
        PrinterMatrix printer = new PrinterMatrix();

        c_venta.setId(id_venta);
        c_venta.obtenerDatos();

        empresa.obtenerDatos();

        c_detalle.setIdVenta(id_venta);
        int contar = c_detalle.contarLineas();
        //  Extenso e = new Extenso();
        //   e.setNumber(101.85);
        printer.setOutSize(31 + contar, 40);

        //imprimir cabezera
        printer.printTextLinCol(1, 1, varios_impresion.centrar_texto(40, "** " + empresa.getNombre() + " **"));
        printer.printTextLinCol(2, 1, varios_impresion.centrar_texto(40, "RUC: " + empresa.getRuc()));
        printer.printTextLinCol(3, 1, varios_impresion.centrar_texto(40, empresa.getRazon()));
        printer.printTextWrap(3, 4, 0, 40, empresa.getDireccion());

        printer.printTextLinCol(7, 1, varios_impresion.centrar_texto(40, "SUCURSAL: " + empresa.getSucursal()));

        printer.printTextLinCol(8, 1, varios_impresion.centrar_texto(40, "NOTA DE VENTA"));
        printer.printTextLinCol(9, 1, varios_impresion.centrar_texto(40, "001 - " + c_varios.ceros_izquieda_numero(7, c_venta.getId())));
        printer.printTextLinCol(10, 1, "FECHA EMISION: " + c_varios.getFechaHora());
        printer.printTextLinCol(11, 1, "CLIENTE DOC: " + c_venta.getDoc());
        printer.printTextLinCol(12, 1, c_venta.getNombre());

        //cargar detalle de productos
        int add_filas = 0;
        double suma_total = 0;
        try {
            Statement st = c_conectar.conexion();

            /*String query = "select p.id_producto, p.descripcion, p.marca, p.modelo, pv.cantidad, pv.precio "
                    + "from productos_ventas as pv "
                    + "inner join productos as p on p.id_producto = pv.id_producto "
                    + "where pv.id_almacen = '" + c_venta.getId_almacen() + "' and id_ventas = '" + c_venta.getId_venta() + "'";*/
            String query = "SELECT p.id, p.nombre, pv.cantidad,"
                    + " pv.precio FROM ventas_productos AS pv "
                    + "INNER JOIN productos AS p ON p.id = pv.id_productos "
                    + "WHERE  pv.id_ventas = '" + id_venta + "'";

            ResultSet rs = c_conectar.consulta(st, query);

            while (rs.next()) {
                String pdescripcion = (rs.getString("nombre").trim()).trim();
                //si cantidad de letras de descripcion es mayor a 68 , aplicar substring a 67
                if (pdescripcion.length() > 68) {
                    pdescripcion = pdescripcion.substring(0, 66);
                }
                if (pdescripcion.length() > 26 & pdescripcion.length() < 29) {
                    pdescripcion = pdescripcion.substring(0, 26);
                }

                int pcantidad = rs.getInt("cantidad");
                double pprecio = rs.getDouble("precio");
                double pparcial = pprecio * pcantidad;
                suma_total += pparcial;
                String sparcial = c_varios.formato_totales(pparcial);

                String texto_linea = pcantidad + " " + pdescripcion;

                //imprimir linea producto
                printer.printTextWrap(13 + add_filas, 15 + add_filas + 1, 0, 40, texto_linea);
                add_filas++;

                //si cantidad de letras de descripcion es mayor a 28 saltar una linea
                if (texto_linea.length() > 28) {
                    add_filas++;
                }

                //imprimir linea parcial
                printer.printTextLinCol(13 + add_filas, 29, " x " + varios_impresion.texto_derecha(9, sparcial));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        //imprimir pie de ticket
        double total = suma_total;
        double subtotal = total / 1.18;
        double igv = total / 1.18 * 0.18;
        String numeros_texto = leer.Convertir(total + "", true) + " SOLES";

        add_filas++;
        add_filas++;

        printer.printTextLinCol(13 + add_filas, 1, varios_impresion.texto_derecha(30, "SUB TOTAL"));
        printer.printTextLinCol(13 + add_filas, 31, varios_impresion.texto_derecha(10, c_varios.formato_totales(subtotal)));
        add_filas++;

        printer.printTextLinCol(13 + add_filas, 1, varios_impresion.texto_derecha(30, "IGV"));
        printer.printTextLinCol(13 + add_filas, 31, varios_impresion.texto_derecha(10, c_varios.formato_totales(igv)));
        add_filas++;

        printer.printTextLinCol(13 + add_filas, 1, varios_impresion.texto_derecha(30, "TOTAL"));
        printer.printTextLinCol(13 + add_filas, 31, varios_impresion.texto_derecha(10, c_varios.formato_totales(total)));

        add_filas++;
        printer.printTextWrap(13 + add_filas, 14 + add_filas + 1, 0, 40, numeros_texto);

        int tipo_canje = c_venta.getIdtido();
        String nombrecanje = "";
        if (tipo_canje == 1) {
            nombrecanje = "NINGUNO";
        }
        if (tipo_canje == 2) {
            nombrecanje = "BOLETA";
        }
        if (tipo_canje == 3) {
            nombrecanje = "FACTURA";
        }
        //add_filas++;
        add_filas++;
        add_filas++;
        printer.printTextLinCol(13 + add_filas, 1, varios_impresion.texto_derecha(30, "CANJEAR POR: "));
        printer.printTextLinCol(13 + add_filas, 31, varios_impresion.texto_derecha(10, nombrecanje));

        add_filas++;
        printer.printTextWrap(13 + add_filas, 4, 0, 40, empresa.getLinea1());

        add_filas++;
        add_filas++;
        add_filas++;
        printer.printTextWrap(13 + add_filas, 4, 0, 40, varios_impresion.centrar_texto(40, empresa.getLinea2()));

        //mostrar en consola
        printer.show();

        //grabar en txt
        printer.toFile("impresion.txt");

        //enviar a imprimir
        //leer archivo
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("impresion.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }

        //comandos impresora para reiniciar y cortar
        byte[] initEP = new byte[]{0x1b, '@'};
        byte[] cutP = new byte[]{0x1d, 'V', 1};

        //inciiar servicio impresion
        PrinterService printerService = new PrinterService();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        printerService.printBytes(defaultPrintService.getName(), initEP);

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;

        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);

            } catch (PrintException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "error al imprimir \n" + ex.getLocalizedMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No existen impresoras instaladas");
            System.err.println("No existen impresoras instaladas");
        }
        //enviar comando de corte
        printerService.printBytes(defaultPrintService.getName(), cutP);
    }
}
