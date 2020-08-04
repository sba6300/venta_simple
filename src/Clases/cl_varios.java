
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.sql.Connection;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author CALIDAD
 */
public class cl_varios {

    // cl_conectar con = new cl_conectar();
    DecimalFormatSymbols simbolo = new DecimalFormatSymbols();
    DecimalFormat formato = null;

    public void centrar_celda(JTable table, int col) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }

    public void derecha_celda(JTable table, int col) {
        DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();

        tcr.setHorizontalAlignment(SwingConstants.RIGHT);
        table.getColumnModel().getColumn(col).setCellRenderer(tcr);
    }

    public boolean esDecimal(String cad) {
        try {
            Double.parseDouble(cad);

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * da formato a fecha (2018-03-20)
     *
     * @param fecha fecha a ingresar
     * @return m_fecha fecha formateada a yyyy-mm-dd
     */
    public String formato_fecha_mysql(String fecha) {
        String m_fecha = null;

        try {
            DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fec = dt.parse(fecha);

            m_fecha = df.format(fec);
        } catch (Exception ex) {
            System.out.println(ex);
            m_fecha = "";
        }

        return m_fecha;
    }

    /**
     * da formato a fecha (20/03/2018)
     *
     * @param fecha fecha a ingresar
     */
    public String formato_fecha_vista(String fecha) {
        String m_fecha = null;

        try {
            DateFormat dt = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date fec = df.parse(fecha);

            m_fecha = dt.format(fec);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return m_fecha;
    }

    public String formato_fecha_ddmmaaaahhmmss(String fecha) {
        String m_fecha = null;

        try {
            DateFormat dt = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fec = df.parse(fecha);

            m_fecha = dt.format(fec);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return m_fecha;
    }

    /**
     * da formato a hora (05:20:30 PM)
     *
     * @param fecha fecha a ingresar
     * @return retorna la hora
     */
    public String formato_hora(String fecha) {
        String m_fecha = null;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat dt = new SimpleDateFormat("hh:mm:ss aa");
            Date fec = df.parse(fecha);

            m_fecha = dt.format(fec);
        } catch (Exception ex) {
            System.out.println(ex);
        }

        return m_fecha;
    }

    /**
     * da formato a periodo (aaaamm)
     *
     * @param fecha fecha a ingresar
     * @return retorna el periodo en formato 201905
     */
    public String formato_periodo(String fecha) {
        String m_fecha = null;

        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat dt = new SimpleDateFormat("yyyyMM");
            Date fec = df.parse(fecha);

            m_fecha = dt.format(fec);
        } catch (ParseException ex) {
            System.out.println(ex);
        }

        return m_fecha;
    }

    /**
     * da formato a numeros sin signos (1252.00)
     *
     * @param number numero a ingresar
     */
    public String formato_numero(Double number) {
        simbolo.setDecimalSeparator('.');
        formato = new DecimalFormat("######0.00", simbolo);

        String numero = "";

        numero = formato.format(number);

        return numero;
    }

    /**
     * da formato a numeros varios decimales sin signos (20.0002)
     *
     * @param number numero a ingresar
     */
    public String formato_precio(Double number) {
        simbolo.setDecimalSeparator('.');
        formato = new DecimalFormat("######0.0000", simbolo);

        String numero = "";

        numero = formato.format(number);

        return numero;
    }

    /**
     * da formato a tipo de cambio (3.235)
     *
     * @param number numero a ingresar
     */
    public String formato_tc(Double number) {
        simbolo.setDecimalSeparator('.');
        formato = new DecimalFormat("######0.000", simbolo);

        String numero = "";

        numero = formato.format(number);

        return numero;
    }

    /**
     * da formato a numero decimales (1,234,321.00)
     *
     * @param number numero a ingresar
     */
    public String formato_totales(Double number) {
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        formato = new DecimalFormat("#,###,##0.00", simbolo);

        String numero = "";

        numero = formato.format(number);

        return numero;
    }

    /**
     * da formato a cantidad sin decimales (1,234,321)
     *
     * @param number numero a ingresar
     * @return numero en formato #,###,##0
     */
    public String formato_cantidad(Double number) {
        simbolo.setDecimalSeparator('.');
        simbolo.setGroupingSeparator(',');
        formato = new DecimalFormat("#,###,##0", simbolo);

        String numero = "";

        numero = formato.format(number);

        return numero;
    }

    public String hora_actual() {
        Calendar calendario = new GregorianCalendar();
        int hora, minutos, segundos;

        hora = calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);
        segundos = calendario.get(Calendar.SECOND);

        String hora_exacta = hora + ":" + minutos + ":" + segundos;

        return hora_exacta;
    }

    public String leer_archivo(String nom_arc) {
        String linea = null;

        try {
            File Ffichero = new File(nom_arc);

            /* Si existe el fichero */
            if (Ffichero.exists()) {

                /* Abre un flujo de lectura a el fichero */
                BufferedReader Flee = new BufferedReader(new FileReader(Ffichero));
                String Slinea;

                /* Lee el fichero linea a linea hasta llegar a la ultima */
                while ((Slinea = Flee.readLine()) != null) {

                    /* Imprime la linea leida */
                    linea = Slinea;
                }

                /* Cierra el flujo */
                Flee.close();
            } else {
                System.out.println("Fichero No Existe");
                linea = "NO EXISTE ARCHIVO";
            }
        } catch (IOException ex) {

            /* Captura un posible error y le imprime en pantalla */
            System.out.println(ex.getMessage());
        }

        return linea;
    }

    public void limitar_caracteres(KeyEvent evt, JTextField txt, int longitud) {
        if (txt.getText().length() == longitud) {
            evt.consume();
        }
    }

    public void limpiarTabla(JTable tabla) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int filas = tabla.getRowCount();

            for (int i = 0; filas > i; i++) {
                modelo.removeRow(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR AL LIMPIAR LA TABLA");
        }
    }

    public void llamar_ventana(JInternalFrame ventana) {
        if (verificar_existencia(ventana)) {
            venta_simple.frm_menu.jDesktopPane1.add(ventana);
            ventana.show();

            Dimension desktopSize = venta_simple.frm_menu.jDesktopPane1.getSize();
            Dimension jInternalFrameSize = ventana.getSize();

            ventana.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);
        }
    }

    public void llamar_ventana_ancha(JInternalFrame ventana) {
        if (verificar_existencia(ventana)) {
            venta_simple.frm_menu.jDesktopPane1.add(ventana);
            ventana.show();
            Dimension desktopSize = venta_simple.frm_menu.jDesktopPane1.getSize();

            int ancho = (int) (desktopSize.getWidth() / 1.05);
            int alto = (int) (desktopSize.getHeight() / 1.05);
            ventana.setSize(ancho, alto);
            Dimension jInternalFrameSize = ventana.getSize();

            ventana.setLocation((desktopSize.width - jInternalFrameSize.width) / 2,
                    (desktopSize.height - jInternalFrameSize.height) / 2);

        }
    }

    public void solo_numeros(KeyEvent evt) {
        char car = evt.getKeyChar();

        if (((car < '0') || (car > '9'))) {
            evt.consume();
        }
    }

    public void solo_precio(KeyEvent evt) {
        char car = evt.getKeyChar();

        if (((car < '0') || (car > '9')) && (car != '.')) {
            evt.consume();
        }
    }

    // Suma los días recibidos a la fecha
    public Date suma_dia(String fecha, int dias) {
        Calendar calendar = Calendar.getInstance();

        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(fecha);

            calendar.setTime(date);                      // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, dias);    // numero de días a añadir, o restar en caso de días<0
        } catch (ParseException ex) {
            Logger.getLogger(cl_varios.class.getName()).log(Level.SEVERE, null, ex);
        }

        return calendar.getTime();    // Devuelve el objeto Date con los nuevos días añadidos
    }

    public static boolean verificar_existencia(JInternalFrame ventana1) {
        boolean mostrar = true;

        for (int a = 0; a < venta_simple.frm_menu.jDesktopPane1.getComponentCount(); a++) {

            // verificar si es instancia de algun componente que ya este en el jdesktoppane
            if (ventana1.getClass().isInstance(venta_simple.frm_menu.jDesktopPane1.getComponent(a))) {
                System.out.println("esta instanciado, no se debe mostrar");
                JOptionPane.showMessageDialog(null,
                        "ESTA VENTANA YA ESTA ABIERTA, PARA NO TENER PROBLEMAS BUSQUELA Y CIERRELA");
                mostrar = false;
            }
        }

        return mostrar;
    }

    /**
     * devuelva fecha actual en format yyyy-mm-dd
     *
     * @return fecha
     */
    public String getFechaActual() {
        Date ahora = new Date();
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

        return formateador.format(ahora);
    }

    public String obtener_periodo() {
        String periodo;
        Calendar now = Calendar.getInstance();
        periodo = now.get(Calendar.YEAR) + ceros_izquieda_numero(2, now.get(Calendar.MONTH) + 1);
        return periodo;
    }

    public String ceros_izquieda_numero(int cantidad, int numero) {
        return String.format("%0" + cantidad + "d", numero);

    }

    public String ceros_izquieda_letras(int cantidad, String numero) {
        while (numero.length() < cantidad) {
            numero = "0" + numero;
        }
        return numero;
    }

    public int getMes(String fecha) {
        int mes_;
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = null;

        try {
            date = formatter.parse(fecha);
        } catch (ParseException ex) {
            Logger.getLogger(cl_varios.class.getName()).log(Level.SEVERE, null, ex);
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        mes_ = calendar.get(Calendar.MONTH) + 1;

        return mes_;
    }

    public boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            System.out.println(nfe);
            return false;
        }
    }

    public String ver_periodo_actual() {
        Calendar fecha = Calendar.getInstance();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH) + 1;
        String periodo = anio + String.format("%02d", mes);
        return periodo;
    }

    public String obtenerDireccionCarpeta() {
        File midireccion = new File(".");
        String path = null;
        try {
            path = midireccion.getCanonicalPath();
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return path;
    }
    
    public String getFechaHora() {
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss");
        return hourdateFormat.format(date);

    }
}



//~ Formatted by Jindent --- http://www.jindent.com
