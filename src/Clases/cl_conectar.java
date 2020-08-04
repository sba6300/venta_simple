/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Administrador
 */
public class cl_conectar {

    private static final cl_varios c_varios = new cl_varios();
    private static final String direccion = c_varios.obtenerDireccionCarpeta();

    private static final String url = direccion + "/data.sqlite";

    private static Connection conexion = null;
    //private static String bd = "bd_pesaje"; // Nombre de BD.
    // private static String user = "host"; // Usuario de BD.
    // private static String password = "1234"; // Password de BD.

    // Driver para MySQL en este caso.
    //private static String driver = "com.mysql.jdbc.Driver";
    //String server = "jdbc:mysql://localhost/" + bd;
    /**
     * ***** Prueba con SQLite ******
     */
    //private static String driver = "com.sqlite.jdbc.Driver";
    //private static String driver = "com.sqlite.jdbc.Driver";
    String server = "jdbc:sqlite:" + url;

    public boolean conectar() {
        boolean conectado;
        try {

            // Class.forName(driver);
            conexion = DriverManager.getConnection(server);
            //conexion = DriverManager.getConnection(server);
            conectado = true;
            System.out.println("Conectando al Servidor: " + server);

        } catch (SQLException e) {
            //JOptionPane.showMessageDialog(null, "Error: Imposible realizar la conexion a BD." + server + "," + user + "," + password);
            JOptionPane.showMessageDialog(null, "Error al conectar \n" + e.getLocalizedMessage());
            System.out.print(e);
            e.printStackTrace();
            //System.exit(0);
            conectado = false;
        }
        return conectado;
    }

    /**
     * Método para establecer la conexión con la base de datos.
     *
     * @return
     */
    public Connection conx() {
        return conexion;
    }

    public Statement conexion() {
        Statement st = null;
        try {
            st = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error: Conexión incorrecta.");
            e.printStackTrace();
        }
        return st;
    }

    /**
     * Método para realizar consultas del tipo: SELECT * FROM tabla WHERE..."
     *
     * @param st
     * @param cadena La consulta en concreto
     * @return
     */
    public ResultSet consulta(Statement st, String cadena) {
        ResultSet rs = null;
        try {
            rs = st.executeQuery(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Método para realizar consultas de actualización, creación o eliminación.
     *
     * @param st
     * @param cadena La consulta en concreto
     * @return
     */
    public int actualiza(Statement st, String cadena) {
        int rs = -1;
        try {
            rs = st.executeUpdate(cadena);
        } catch (SQLException e) {
            System.out.println("Error con: " + cadena);
            System.out.println("SQLException: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "SQLException: " + e.getMessage());
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * Método para cerrar la consula
     *
     * @param rs
     */
    public void cerrar(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la consulta.");
            }
        }
    }

    /**
     * Método para cerrar la conexión.
     *
     * @param st
     */
    public void cerrar(java.sql.Statement st) {
        if (st != null) {
            try {
                st.close();
            } catch (Exception e) {
                System.out.print("Error: No es posible cerrar la conexión.");
            }
        }
    }
}
