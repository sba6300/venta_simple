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
public class Empresa {

    cl_conectar conectar = new cl_conectar();

    private String ruc;
    private String razon;
    private String nombre;
    private String direccion;
    private String sucursal;
    private String linea1;
    private String linea2;
    private String url;

    public Empresa() {
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazon() {
        return razon;
    }

    public void setRazon(String razon) {
        this.razon = razon;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getLinea1() {
        return linea1;
    }

    public void setLinea1(String linea1) {
        this.linea1 = linea1;
    }

    public String getLinea2() {
        return linea2;
    }

    public void setLinea2(String linea2) {
        this.linea2 = linea2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean actualizar() {
        boolean registrado = false;

        Statement st = conectar.conexion();
        String query = "update generales "
                + "set empresa = '" + razon + "',"
                + "ruc = '" + ruc + "',"
                + "direccion = '" + direccion + "',"
                + "nombre = '" + nombre + "',"
                + "sucursal = '" + sucursal + "',"
                + "linea1 = '" + linea1 + "',"
                + "linea2 = '" + linea2 + "',"
                + "url = '" + url + "'"
                + "where id = '1'";
        System.out.println(query);
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
                    + "from generales "
                    + "where id = '1'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.razon = rs.getString("empresa");
                this.ruc = rs.getString("ruc");
                this.direccion = rs.getString("direccion");
                this.nombre = rs.getString("nombre");
                this.sucursal = rs.getString("sucursal");
                this.linea1 = rs.getString("linea1");
                this.linea2 = rs.getString("linea2");
                this.url = rs.getString("url");
            }
            conectar.cerrar(rs);
            conectar.cerrar(st);
        } catch (SQLException ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }

}
