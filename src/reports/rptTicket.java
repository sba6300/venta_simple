/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reports;

import Clases.DocumentoSunat;
import Clases.Empresa;
import Clases.Venta;
import Clases.VentaProducto;
import Clases.cl_conectar;
import Clases.cl_varios;
import Printer.PrinterService;
import Printer.leer_numeros;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;

/**
 *
 * @author luis
 */
public class rptTicket {

    Venta venta = new Venta();
    DocumentoSunat tido = new DocumentoSunat();
    Empresa empresa = new Empresa();
    VentaProducto detalle = new VentaProducto();
    cl_varios varios = new cl_varios();
    cl_conectar conectar = new cl_conectar();
    leer_numeros leer = new leer_numeros();

    private int idventa;

    public rptTicket(int idventa) {
        this.idventa = idventa;
        venta.setId(idventa);
        venta.obtenerDatos();

        tido.setId(venta.getIdtido());
        tido.obtenerDatos();

        empresa.obtenerDatos();

        detalle.setIdVenta(idventa);

    }

    public int getIdventa() {
        return idventa;
    }

    public void setIdventa(int idventa) {
        this.idventa = idventa;
    }

    public void generarTicket() throws FileNotFoundException, DocumentException, BadElementException, IOException, SQLException, PrintException, PrinterException {
        Rectangle pagesize = new Rectangle(200f, 720f);
        Document documento = new Document(pagesize);
        documento.setMargins(11, 11, 11, 11);

        String direccion = varios.obtenerDireccionCarpeta();

        java.util.Date date = new java.util.Date();
        DateFormat hourdateFormat = new SimpleDateFormat("_dd_MM_yyyy_HH_mm_ss");
        String fechahora = hourdateFormat.format(date);

        // Se crea el OutputStream para el fichero donde queremos dejar el pdf.
        FileOutputStream ficheroPdf = new FileOutputStream(direccion + File.separator + "temp" + File.separator + "ticket_" + fechahora + ".pdf");

        // Se asocia el documento al OutputStream y se indica que el espaciado entre
        // lineas sera de 20. Esta llamada debe hacerse antes de abrir el documento
        PdfWriter.getInstance(documento, ficheroPdf).setInitialLeading(8);

        Image imagen = Image.getInstance("new_logo.png");
        imagen.scaleAbsolute(100, 41);

        // Se abre el documento.
        documento.open();

        Font font = FontFactory.getFont("Arial Narrow", 10);

        imagen.setAlignment(Element.ALIGN_CENTER);

        documento.add(imagen);

        Paragraph linea1 = new Paragraph("*** " + empresa.getNombre() + " ***", font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph("RUC: " + empresa.getRuc(), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph(empresa.getRazon(), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph(empresa.getDireccion(), font);
        documento.add(linea1);

        documento.add(new Paragraph(Chunk.NEWLINE));

        linea1 = new Paragraph(tido.getNombre(), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph(venta.getSerie() + " - " + varios.ceros_izquieda_numero(7, venta.getNumero()), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph("Fecha Emision: " + varios.formato_fecha_vista(venta.getFecha()) + " " + venta.getHora(), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph("Cliente: " + venta.getDoc(), font);
        linea1.setAlignment(Element.ALIGN_LEFT);
        documento.add(linea1);

        linea1 = new Paragraph(venta.getNombre(), font);
        linea1.setAlignment(Element.ALIGN_LEFT);
        documento.add(linea1);

        documento.add(new Paragraph(Chunk.NEWLINE));

        //cargar prodcutos
        Statement st = conectar.conexion();
        String query = "SELECT p.id, p.nombre, pv.cantidad, pv.costo, "
                + " pv.precio FROM ventas_productos AS pv "
                + "INNER JOIN productos AS p ON p.id = pv.id_productos "
                + "WHERE  pv.id_ventas = '" + idventa + "'";
        ResultSet rs = conectar.consulta(st, query);

        PdfPTable tabla = new PdfPTable(3);
        tabla.getDefaultCell().setBorderWidth(0f);
        tabla.getDefaultCell().setBorder(0);

        tabla.setWidthPercentage(100.0f);

        tabla.setWidths(new int[]{8, 72, 20});
        double subtotal = 0;

        while (rs.next()) {
            double parcial = rs.getDouble("precio") * rs.getDouble("cantidad");
            subtotal += parcial;
            PdfPCell celda_1 = new PdfPCell((Phrase) new Paragraph(rs.getInt("cantidad") + "", font));
            PdfPCell celda_2 = new PdfPCell((Phrase) new Paragraph(rs.getString("nombre"), font));
            PdfPCell celda_3 = new PdfPCell((Phrase) new Paragraph(varios.formato_totales(parcial), font));

            celda_1.setBorder(Rectangle.NO_BORDER);
            celda_2.setBorder(Rectangle.NO_BORDER);
            celda_3.setBorder(Rectangle.NO_BORDER);
            celda_3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);

            tabla.addCell(celda_1);
            tabla.addCell(celda_2);
            tabla.addCell(celda_3);
        }

        documento.add(tabla);

        documento.add(new Paragraph(Chunk.NEWLINE));

        PdfPTable tabla_pie = new PdfPTable(2);

        //tabla.getDefaultCell().setBorder(0);
        tabla_pie.setWidthPercentage(100.0f);

        tabla_pie.setWidths(new int[]{80, 20});

        PdfPCell celda_1 = new PdfPCell((Phrase) new Paragraph("SUB TOTAL", font));
        PdfPCell celda_2 = new PdfPCell((Phrase) new Paragraph(varios.formato_totales(subtotal / 1.18), font));

        celda_1.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celda_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celda_1.setBorder(Rectangle.NO_BORDER);
        celda_2.setBorder(Rectangle.NO_BORDER);

        tabla_pie.addCell(celda_1);
        tabla_pie.addCell(celda_2);

        PdfPCell celda_3 = new PdfPCell((Phrase) new Paragraph("IGV", font));
        PdfPCell celda_4 = new PdfPCell((Phrase) new Paragraph(varios.formato_totales(subtotal / 1.18 * 0.18), font));

        celda_3.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celda_4.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celda_3.setBorder(Rectangle.NO_BORDER);
        celda_4.setBorder(Rectangle.NO_BORDER);

        tabla_pie.addCell(celda_3);
        tabla_pie.addCell(celda_4);

        PdfPCell celda_5 = new PdfPCell((Phrase) new Paragraph("TOTAL", font));
        PdfPCell celda_6 = new PdfPCell((Phrase) new Paragraph(varios.formato_totales(subtotal), font));

        celda_5.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celda_6.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
        celda_5.setBorder(Rectangle.NO_BORDER);
        celda_6.setBorder(Rectangle.NO_BORDER);

        tabla_pie.addCell(celda_5);
        tabla_pie.addCell(celda_6);

        documento.add(tabla_pie);

        String numero = leer.Convertir(subtotal + "", true);

        linea1 = new Paragraph("SON: " + numero + " SOLES", font);
        linea1.setAlignment(Element.ALIGN_LEFT);
        documento.add(linea1);

        linea1 = new Paragraph(empresa.getLinea1(), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);

        linea1 = new Paragraph(empresa.getLinea2(), font);
        linea1.setAlignment(Element.ALIGN_CENTER);
        documento.add(linea1);
        documento.close();
        try {
            File file = new File(direccion + File.separator + "temp" + File.separator + "ticket_" + fechahora + ".pdf");
            imprimir(file.toString());
            //Desktop.getDesktop().open(file);
        } catch (IOException e) {
            System.out.print(e + " -- error io");
            JOptionPane.showMessageDialog(null, "Error al Generar el PDF -- \n" + e.getLocalizedMessage());
        }

    }

    public void imprimir(String archivo) throws PrinterException, IOException, PrintException {
        /*
        DocFlavor flavor = DocFlavor.SERVICE_FORMATTED.PAGEABLE;
        PrintRequestAttributeSet patts = new HashPrintRequestAttributeSet();
        patts.add(Sides.DUPLEX);
        PrintService[] ps = PrintServiceLookup.lookupPrintServices(flavor, patts);
        if (ps.length == 0) {
            throw new IllegalStateException("No Printer found");
        }
        System.out.println("Available printers: " + Arrays.asList(ps));

        PrinterService printerService = new PrinterService();
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();

        FileInputStream fis = new FileInputStream(archivo);
        Doc pdfDoc = new SimpleDoc(fis, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
        DocPrintJob printJob = defaultPrintService.createPrintJob();
        printJob.print(pdfDoc, new HashPrintRequestAttributeSet());
        fis.close();
        
         */

        // Indicamos el nombre del archivo Pdf que deseamos imprimir
        PDDocument document = PDDocument.load(new File(archivo));

        PrinterJob job = PrinterJob.getPrinterJob();
        System.out.println("Mostrando el dialogo de impresion");
        //LOGGER.log(Level.INFO, "Mostrando el dialogo de impresion");
//        if (job.printDialog() == true) {  
        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
        job.setPrintService(defaultPrintService);
        job.setPageable(new PDFPageable(document));
        System.out.println("Imprimiendo document");
        // LOGGER.log(Level.INFO, "Imprimiendo documento");
        job.print();
        //       }
    }
}
