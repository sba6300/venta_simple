/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author luis
 */
public class Venta {

    cl_conectar conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id;
    private String fecha;
    String hora;
    private String doc;
    private String nombre;
    private double total;
    private String correo;
    private int idtido;
    private String serie;
    private int numero;
    private int estado;
    
           private String rci = "";
           private String trd = "";

    public Venta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDoc() {
        return doc;
    }

    public void setDoc(String doc) {
        this.doc = doc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getIdtido() {
        return idtido;
    }

    public void setIdtido(int idtido) {
        this.idtido = idtido;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getRci() {
        return rci;
    }

    public String getTrd() {
        return trd;
    }
    
    

    @Override
    public String toString() {
        return "Venta{" + "conectar=" + conectar + ", c_varios=" + c_varios + ", id=" + id + ", fecha=" + fecha + ", hora=" + hora + ", doc=" + doc + ", nombre=" + nombre + ", total=" + total + ", correo=" + correo + ", idtido=" + idtido + ", serie=" + serie + ", numero=" + numero + ", estado=" + estado + '}';
    }

    public void obtener_id() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(id) + 1, 1) as codigo "
                    + "from ventas";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.id = rs.getInt("codigo");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "INSERT INTO ventas "
                + "VALUES ('" + id + "',"
                + "        '" + fecha + "',"
                + "        '" + hora + "',"
                + "        '" + doc + "',"
                + "        '" + nombre + "',"
                + "        '" + total + "',"
                + "        '" + correo + "',"
                + "        '" + idtido + "',"
                + "        '" + serie + "',"
                + "        '" + numero + "',"
                + "        '" + estado + "');";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean actualizarEstado() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update ventas set estado = '" + estado + "' "
                + "where id = '" + id + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }
     public boolean cambiarEstadoAyer() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update ventas set estado = '1' "
                + "where estado = 2 and fecha = strftime('%Y-%m-%d', datetime('now','-1 day')) and id_tido = 2";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public void verFilas(JTable tabla, String query) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = conectar.conexion();
            ResultSet rs = conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Item");
            mostrar.addColumn("Fecha");
            mostrar.addColumn("Hora");
            mostrar.addColumn("Documento");
            mostrar.addColumn("Cliente");
            mostrar.addColumn("Total");
            mostrar.addColumn("Estado");

            while (rs.next()) {

                int tipoestado = rs.getInt("estado");
                String nombreestado = "";
                if (tipoestado == 1) {
                    nombreestado = "ENVIADO A SUNAT";
                }
                if (tipoestado == 2) {
                    nombreestado = "POR ENVIAR";
                }
                if (tipoestado == 3) {
                    nombreestado = "ANULADO";
                }

                Object fila[] = new Object[7];

                fila[0] = rs.getString("id");
                fila[1] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila[2] = rs.getString("hora");
                fila[3] = rs.getString("abreviatura") + " | " + rs.getString("serie") + " - " + c_varios.ceros_izquieda_numero(6, rs.getInt("numero"));
                fila[4] = rs.getString("doc_cliente") + " | " + rs.getString("nom_cliente");
                fila[5] = c_varios.formato_totales(rs.getDouble("total"));
                fila[6] = nombreestado;
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(150);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(450);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(6).setPreferredWidth(120);

            tabla.setDefaultRenderer(Object.class, new renders.render_ventas());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public void obtenerDatos() {
        try {
            Statement st = conectar.conexion();
            String query = "select *  "
                    + "from ventas "
                    + "where id = '" + id + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.fecha = rs.getString("fecha");
                this.hora = rs.getString("hora");
                this.doc = rs.getString("doc_cliente");
                this.nombre = rs.getString("nom_cliente");
                this.total = rs.getDouble("total");
                this.idtido = rs.getInt("id_tido");
                this.serie = rs.getString("serie");
                this.numero = rs.getInt("numero");
                this.estado = rs.getInt("estado");
                this.correo = rs.getString("correo");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    public void genRDIString() {
        try {

            Statement st = conectar.conexion();
            String query = "select v.fecha, ds.cod_sunat, v.serie, v.numero, v.doc_cliente, v.total, v.estado "
                    + "from ventas as v "
                    + "inner join documento_sunat as ds on ds.id_tido= v.id_tido "
                    + "where v.id_tido = 2 and v.fecha = strftime('%Y-%m-%d', datetime('now','-1 day'))";
            System.out.println(query);
            ResultSet rs = conectar.consulta(st, query);

            int item = 0;
            while (rs.next()) {
                item ++;
                String tipo_cliente = "0";
                String doc_cliente = rs.getString("doc_cliente");

                if (doc_cliente != null && doc_cliente.length() == 8) {
                    tipo_cliente = "1";
                }
                if (doc_cliente != null && doc_cliente.length() == 11) {
                    tipo_cliente = "6";
                }

                if (doc_cliente == null || doc_cliente.equals("")) {
                    doc_cliente = "0";
                }

                String vestado = "1";
                if (rs.getString("estado").equals("3")) {
                    vestado = "3";
                }

                double base = rs.getDouble("total") / 1.18;
                double igv = base * 0.18;

                rci += rs.getString("fecha") + "|"
                        + fecha + "|"
                        + rs.getString("cod_sunat") + "|"
                        + rs.getString("serie") + "-" + rs.getString("numero") + "|"
                        + tipo_cliente + "|"
                        + doc_cliente + "|"
                        + "PEN|"
                        + c_varios.formato_numero(base) + "|"
                        + "0|"
                        + "0|"//10
                        + "0|"
                        + "0|"
                        + "0|"
                        + c_varios.formato_numero(rs.getDouble("total")) + "|"
                        + "|"
                        + "|"
                        + "|"
                        + "|"
                        + "|"
                        + "|"
                        + "|"
                        + "|"
                        + vestado + "\n";
                
                
                //generar el trd
                
                trd +=  item + "|"
                        + "1000|" 
                        + "IGV|" 
                        + "VAT|"
                        + c_varios.formato_numero(base) + "|"
                        + c_varios.formato_numero(igv) + "\n";
            }
            
            cambiarEstadoAyer();

            conectar.cerrar(st);
            conectar.cerrar(rs);

            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
     public void resumenVentasMes(JTable tabla) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = conectar.conexion();
            String query = "SELECT ds.nombre, sum(v.total) as suma FROM ventas AS v "
                    + "inner join documento_sunat as ds on ds.id_tido = v.id_tido "
                    + "WHERE  strftime('%Y%m', v.fecha) = strftime('%Y%m', CURRENT_DATE) and v.estado != 3 "
                    + "group by v.id_tido ";
            ResultSet rs = conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
          //  tabla.setRowSorter(sorter);

            mostrar.addColumn("Documento Venta");
            mostrar.addColumn("Monto S/");

            while (rs.next()) {

                Object fila[] = new Object[2];

                fila[0] = rs.getString("nombre");
                fila[1] = c_varios.formato_totales(rs.getDouble("suma"));
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(40);
            c_varios.derecha_celda(tabla, 1);
            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
     
     public void resumenVentasAño (JTable tabla) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = conectar.conexion();
            String query = "SELECT m.nombre as nromes, sum(v.total) as suma "
                    + "FROM ventas AS v "
                    + "inner join mes as m on m.id = strftime('%m', v.fecha) "
                    + "WHERE  strftime('%Y', v.fecha) = strftime('%Y', CURRENT_DATE) and v.estado != 3  "
                    + "group by strftime('%m', v.fecha) ";
            //System.out.println(query);
            ResultSet rs = conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
          //  tabla.setRowSorter(sorter);

            mostrar.addColumn("Mes");
            mostrar.addColumn("Monto S/");

            while (rs.next()) {

                Object fila[] = new Object[2];

                fila[0] = rs.getString("nromes");
                fila[1] = c_varios.formato_totales(rs.getDouble("suma"));
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(40);
            c_varios.derecha_celda(tabla, 1);
            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
