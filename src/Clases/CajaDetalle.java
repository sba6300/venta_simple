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
public class CajaDetalle {

    cl_conectar conectar = new cl_conectar();
    cl_varios varios = new cl_varios();

    private int id;
    private String fecha;
    private String motivo;
    private double monto;

    public CajaDetalle() {
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

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public void generarId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(id) + 1, 1) as codigo "
                    + "from caja_detalle";
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

    public boolean actualizar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update caja_detalle "
                + "set motivo = '" + motivo + "',"
                + "monto = '" + monto + "'"
                + "where id = '" + id + "'";
        System.out.println(query);
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "INSERT INTO caja_detalle "
                + "VALUES ('" + id + "',"
                + "'" + fecha + "',"
                + "'" + motivo + "',"
                + "'" + monto + "');";
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
        String query = "delete from caja_detalle "
                + "where id = '" + id + "'";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public void obtenerDatos() {
        try {
            Statement st = conectar.conexion();
            String query = "select *  "
                    + "from caja_detalle "
                    + "where id = '" + id + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.fecha = rs.getString("fecha");
                this.motivo = rs.getString("motivo");
                this.monto = rs.getDouble("monto");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

    public double verFilas(JTable tabla) {
        double suma = 0;
        try {
            DefaultTableModel mostrar = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int fila, int columna) {
                    return false;
                }
            };
            Statement st = conectar.conexion();
            String query = "select * from caja_detalle where fecha = '" + this.fecha + "' order by id";
            ResultSet rs = conectar.consulta(st, query);

            RowSorter<TableModel> sorter = new TableRowSorter<>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Item");
            mostrar.addColumn("Motivo");
            mostrar.addColumn("Monto");

            while (rs.next()) {

                Object fila[] = new Object[3];

                fila[0] = rs.getString("id");
                fila[1] = rs.getString("motivo");
                fila[2] = varios.formato_numero(rs.getDouble("monto"));
                mostrar.addRow(fila);

                suma += rs.getDouble("monto");
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(350);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            varios.derecha_celda(tabla, 2);

            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
        return suma;
    }

}
