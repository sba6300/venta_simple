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
public class VentaProducto {

    cl_conectar conectar = new cl_conectar();
    cl_varios c_varios = new cl_varios();

    private int idProducto;
    private int idVenta;
    private double cantidad;
    private double precio;
    private double costo;

    public VentaProducto() {
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
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

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "INSERT INTO ventas_productos "
                + "VALUES ('" + idVenta + "',"
                + "        '" + idProducto + "',"
                + "        '" + cantidad + "',"
                + "        '" + precio + "',"
                + "        '" + costo + "')";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public int contarLineas() {
        int cuenta = 0;
        try {
            Statement st = conectar.conexion();
            String query = "select count(*) as contar  "
                    + "from ventas_productos "
                    + "where id_ventas = '" + idVenta + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                cuenta = rs.getInt("contar");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return cuenta;
    }

    public String genDetString(){
        String det="";
        try {
            
            Statement st = conectar.conexion();
            String query = "SELECT p.id, p.nombre, pv.cantidad, pv.costo, "
                    + " pv.precio FROM ventas_productos AS pv "
                    + "INNER JOIN productos AS p ON p.id = pv.id_productos "
                    + "WHERE  pv.id_ventas = '" + idVenta + "'";
            ResultSet rs = conectar.consulta(st, query);
 
            while (rs.next()) {
                double base= rs.getDouble("precio")/1.18;
                double igv = base*0.18;
                        
                 det += "NIU|"+
                         rs.getDouble("cantidad")+"|"+
                         rs.getString("id")+"||"+
                         rs.getString("nombre")+"|"+
                         c_varios.formato_totales(rs.getDouble("precio"))+"|"+
                         c_varios.formato_totales(igv)+"|1000|"+
                         c_varios.formato_totales(igv)+"|"+c_varios.formato_totales(base)+
                         "|IGV|VAT|10|18.00|0000|0.00|0.00|||01|0|-|0.00|0.00|||0|"+
                         c_varios.formato_totales(rs.getDouble("precio"))+"|"+c_varios.formato_totales(base)+"|0.00|\n";
                
               
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            
            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        return det;
    }
    
    public void verVentas(JTable tabla) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = conectar.conexion();
            String query = "SELECT v.fecha, sum(v.total) as suma FROM ventas_productos AS pv "
                    + "INNER JOIN ventas AS v ON v.id = pv.id_ventas "
                    + "WHERE  strftime('%m', v.fecha) = strftime('%m', CURRENT_DATE) and v.estado != 3 "
                    + "group by v.fecha ";
            ResultSet rs = conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Dia");
            mostrar.addColumn("Venta");

            while (rs.next()) {

                Object fila[] = new Object[2];

                fila[0] = c_varios.formato_fecha_vista(rs.getString("fecha"));
                fila[1] = c_varios.formato_totales(rs.getDouble("suma"));
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(60);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(70);
            c_varios.centrar_celda(tabla, 0);
            c_varios.derecha_celda(tabla, 1);
            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    
    public void verFilas(JTable tabla) {
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = conectar.conexion();
            String query = "SELECT p.id, p.nombre, pv.cantidad, pv.costo, "
                    + " pv.precio FROM ventas_productos AS pv "
                    + "INNER JOIN productos AS p ON p.id = pv.id_productos "
                    + "WHERE  pv.id_ventas = '" + idVenta + "'";
            ResultSet rs = conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Item");
            mostrar.addColumn("Nombre");
            mostrar.addColumn("Costo");
            mostrar.addColumn("P. Venta");
            mostrar.addColumn("Cantidad");
            mostrar.addColumn("P. Venta");

            while (rs.next()) {

                Object fila[] = new Object[6];

                fila[0] = rs.getString("id");
                fila[1] = rs.getString("nombre");
                fila[2] = c_varios.formato_totales(rs.getDouble("costo"));
                fila[3] = c_varios.formato_totales(rs.getDouble("precio"));
                fila[4] = c_varios.formato_totales(rs.getDouble("cantidad"));
                fila[5] = c_varios.formato_totales(rs.getDouble("cantidad") * rs.getDouble("precio"));
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(5);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(4).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(5).setPreferredWidth(80);
            c_varios.derecha_celda(tabla, 5);
            c_varios.derecha_celda(tabla, 4);
            c_varios.derecha_celda(tabla, 3);
            c_varios.derecha_celda(tabla, 2);
            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    
}
