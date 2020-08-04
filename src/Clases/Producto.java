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
public class Producto {

    cl_conectar conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int id;
    private String codbarra;
    private String nombre;
    private double precio;
    private double costo;

    public Producto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodbarra() {
        return codbarra;
    }

    public void setCodbarra(String codbarra) {
        this.codbarra = codbarra;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public void obtener_id() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(id) + 1, 1) as codigo "
                    + "from productos";
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
        String query = "INSERT INTO productos "
                + "VALUES ('" + id + "',"
                + "        '" + codbarra + "',"
                + "        '" + nombre + "',"
                + "        '" + costo + "',"
                + "        '" + precio + "');";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean eliminar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "delete from productos "
                + "where id = '" + id + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean actualizar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update productos "
                + "set codbarra = '" + codbarra + "',"
                + "nombre = '" + nombre + "',"
                + "costo = '" + costo + "',"
                + "precio = '" + precio + "'"
                + "where id = '" + id + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean buscarBarra() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select id  "
                    + "from productos "
                    + "where codbarra = '" + codbarra + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                existe = true;
                this.id = rs.getInt("id");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

        return existe;
    }

    public void obtenerDatos() {
        try {
            Statement st = conectar.conexion();
            String query = "select *  "
                    + "from productos "
                    + "where id = '" + id + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.codbarra = rs.getString("codbarra");
                this.nombre = rs.getString("nombre");
                this.precio = rs.getDouble("precio");
                this.costo = rs.getDouble("costo");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

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
            mostrar.addColumn("Cod Barras");
            mostrar.addColumn("Nombre");
            mostrar.addColumn("Costo");
            mostrar.addColumn("P. Venta");

            while (rs.next()) {

                Object fila[] = new Object[5];

                fila[0] = rs.getString("id");
                fila[1] = rs.getString("codbarra");
                fila[2] = rs.getString("nombre");
                fila[3] = c_varios.formato_numero(rs.getDouble("costo"));
                fila[4] = c_varios.formato_numero(rs.getDouble("precio"));
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(5);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(100);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(50);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 3);
            c_varios.centrar_celda(tabla, 1);
            c_varios.centrar_celda(tabla, 0);

            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}
