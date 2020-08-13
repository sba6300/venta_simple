/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author luis
 */
public class Caja {

    cl_conectar conectar = new cl_conectar();
    cl_varios varios = new cl_varios();

    private String fecha;
    private double venta;
    private double salida;
    private double inicio;
    private double cierre;

    public Caja() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getVenta() {
        return venta;
    }

    public void setVenta(double venta) {
        this.venta = venta;
    }

    public double getSalida() {
        return salida;
    }

    public void setSalida(double salida) {
        this.salida = salida;
    }

    public double getInicio() {
        return inicio;
    }

    public void setInicio(double inicio) {
        this.inicio = inicio;
    }

    public double getCierre() {
        return cierre;
    }

    public void setCierre(double cierre) {
        this.cierre = cierre;
    }

    public boolean insertar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "INSERT INTO caja_chica "
                + "VALUES ('" + fecha + "',"
                + "'" + venta + "',"
                + "'" + salida + "',"
                + "'" + cierre + "',"
                + "'" + inicio + "');";
        int resultado = conectar.actualiza(st, query);
        if (resultado > -1) {
            registrado = true;
        }
        conectar.cerrar(st);

        return registrado;
    }

    public boolean obtenerDatos() {
        boolean existe = false;
        try {
            Statement st = conectar.conexion();
            String query = "select *  "
                    + "from caja_chica "
                    + "where fecha = '" + fecha + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                existe = true;
                this.venta = rs.getDouble("ventas");
                this.salida = rs.getDouble("egresos");
                this.inicio = rs.getDouble("cuadre");
                this.cierre = rs.getDouble("apertura");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return existe;
    }
}
