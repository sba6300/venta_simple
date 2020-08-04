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

}
