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
public class DocumentoSunat {

    cl_conectar conectar = new cl_conectar();
    cl_varios varios = new cl_varios();

    private int id;
    private String nombre;
    private String abreviatura;
    private String codsunat;
    private String serie;
    private int numero;

    public DocumentoSunat() {
    }

   

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getCodsunat() {
        return codsunat;
    }

    public void setCodsunat(String codsunat) {
        this.codsunat = codsunat;
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

    public void generarId() {
        try {
            Statement st = conectar.conexion();
            String query = "select ifnull(max(id_tido) + 1, 1) as codigo "
                    + "from documento_sunat";
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
        String query = "update documento_sunat "
                + "set nombre = '" + nombre + "',"
                + "abreviatura = '" + abreviatura + "',"
                + "cod_sunat = '" + codsunat + "',"
                + "serie = '" + serie + "',"
                + "numero = '" + numero + "' "
                + "where id_tido = '" + id + "'";
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
        String query = "INSERT INTO documento_sunat "
                + "VALUES ('" + id + "',"
                + "'" + nombre + "',"
                + "'" + abreviatura + "',"
                + "'" + codsunat + "',"
                + "'" + serie + "',"
                + "'" + numero + "');";
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
        String query = "delete from documento_sunat "
                + "where id_tido = '" + id + "'";
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
                    + "from documento_sunat "
                    + "where id_tido = '" + id + "'";
            ResultSet rs = conectar.consulta(st, query);

            if (rs.next()) {
                this.nombre = rs.getString("nombre");
                this.abreviatura = rs.getString("abreviatura");
                this.codsunat = rs.getString("cod_sunat");
                this.serie = rs.getString("serie");
                this.numero = rs.getInt("numero");
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

            RowSorter<TableModel> sorter = new TableRowSorter<>(mostrar);
            tabla.setRowSorter(sorter);

            mostrar.addColumn("Item");
            mostrar.addColumn("Nombre");
            mostrar.addColumn("Serie");
            mostrar.addColumn("Numero");

            while (rs.next()) {

                Object fila[] = new Object[4];

                fila[0] = rs.getString("id_tido");
                fila[1] = rs.getString("nombre") + " | " + rs.getString("abreviatura") + " | " + rs.getString("cod_sunat");
                fila[2] = rs.getString("serie"); 
                fila[3] = varios.ceros_izquieda_numero(6, rs.getInt("numero"));
                mostrar.addRow(fila);
            }

            conectar.cerrar(st);
            conectar.cerrar(rs);
            tabla.setModel(mostrar);
            tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
            tabla.getColumnModel().getColumn(1).setPreferredWidth(250);
            tabla.getColumnModel().getColumn(2).setPreferredWidth(50);
            tabla.getColumnModel().getColumn(3).setPreferredWidth(100);
            varios.centrar_celda(tabla, 3);
            varios.centrar_celda(tabla, 2);
            varios.centrar_celda(tabla, 0);

            //tabla.setDefaultRenderer(Object.class, new render_tables.render_clientes());
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

}
