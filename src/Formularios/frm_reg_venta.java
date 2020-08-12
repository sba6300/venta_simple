/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Formularios;

import Clases.DocumentoSunat;
import Clases.Producto;
import Clases.Venta;
import Clases.VentaProducto;
import Clases.cl_varios;
import Controller.GenerarFS;
import Printer.Print_Venta_Nota;
import com.lowagie.text.BadElementException;
import com.lowagie.text.DocumentException;
import java.awt.event.KeyEvent;
import java.awt.print.PrinterException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import json.cl_json_entidad;
import models.m_tido;
import objects.tido;
import org.json.simple.parser.ParseException;
import reports.rptTicket;

/**
 *
 * @author luis
 */
public class frm_reg_venta extends javax.swing.JInternalFrame {

    Producto producto = new Producto();
    Venta venta = new Venta();
    VentaProducto detalle = new VentaProducto();
    cl_varios c_varios = new cl_varios();
    DocumentoSunat tido = new DocumentoSunat();

    m_tido mtido = new m_tido();

    Print_Venta_Nota printing = new Print_Venta_Nota();

    DefaultTableModel modelo = null;

    double total = 0;
    int fila_seleccionada;

    /**
     * Creates new form frm_reg_venta
     */
    public frm_reg_venta() {
        initComponents();
        cargar_tabla();
        txt_fecha.setText(c_varios.formato_fecha_vista(c_varios.getFechaActual()));
        venta.obtener_id();
        jTextField9.setText(venta.getId() + "");
        mtido.listar_combobox(cbx_canje);
        //obtenerDocumento();
        tido.setId(1);
        tido.obtenerDatos();
        jTextField9.setText(tido.getSerie() + " - " + c_varios.ceros_izquieda_numero(4, tido.getNumero()));
    }

    private void obtenerDocumento() {
        tido otido = (tido) cbx_canje.getSelectedItem();
        tido.setId(otido.getId());
        tido.obtenerDatos();
        jTextField9.setText(tido.getSerie() + " - " + c_varios.ceros_izquieda_numero(4, tido.getNumero()));

    }

    private void limpiar() {
        txt_codbarra.setText("");
        txt_cant.setText("");
        txt_parcial.setText("");
        txt_venta.setText("");
        txt_producto.setText("");
        producto.setId(0);
        txt_codbarra.requestFocus();
    }

    private void calcularTotal() {
        total = 0;
        int contarfilas = t_detalle.getRowCount();
        for (int i = 0; i < contarfilas; i++) {
            total += (Double.parseDouble(t_detalle.getValueAt(i, 4).toString()));
        }
        lbl_items.setText(contarfilas + "");
        lbl_total.setText("S/ " + c_varios.formato_totales(total));

    }

    public final void cargar_tabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        modelo.addColumn("Item");
        modelo.addColumn("Nombre");
        modelo.addColumn("Cant.");
        modelo.addColumn("Precio");
        modelo.addColumn("Parcial");
        modelo.addColumn("Costo");
        t_detalle.setModel(modelo);
        t_detalle.getColumnModel().getColumn(0).setPreferredWidth(10);
        t_detalle.getColumnModel().getColumn(1).setPreferredWidth(250);
        t_detalle.getColumnModel().getColumn(2).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(3).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(4).setPreferredWidth(50);
        t_detalle.getColumnModel().getColumn(5).setPreferredWidth(1);
        c_varios.centrar_celda(t_detalle, 0);
        c_varios.derecha_celda(t_detalle, 2);
        c_varios.derecha_celda(t_detalle, 3);
        c_varios.derecha_celda(t_detalle, 4);
        c_varios.derecha_celda(t_detalle, 5);

    }

    private boolean valida_tabla(int producto) {
        //estado de ingreso
        boolean ingresar = false;
        int cuenta_iguales = 0;

        //verificar fila no se repite
        int contar_filas = t_detalle.getRowCount();
        if (contar_filas == 0) {
            ingresar = true;
        } else {
            for (int j = 0; j < contar_filas; j++) {
                int id_producto_fila = Integer.parseInt(t_detalle.getValueAt(j, 0).toString());
                if (producto == id_producto_fila) {
                    cuenta_iguales++;
                    JOptionPane.showMessageDialog(null, "El Producto a Ingresar ya existe en la lista");
                }
            }

            if (cuenta_iguales == 0) {
                ingresar = true;
            }
        }

        return ingresar;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codbarra = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_producto = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_cant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_venta = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_parcial = new javax.swing.JTextField();
        btn_addproducto = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        t_detalle = new javax.swing.JTable();
        jLabel13 = new javax.swing.JLabel();
        lbl_items = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txt_fecha = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextField9 = new javax.swing.JTextField();
        cbx_canje = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setClosable(true);
        setTitle("Agregar Venta");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("Cod. Barra:");

        txt_codbarra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codbarraKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_codbarraKeyPressed(evt);
            }
        });

        jLabel2.setText("Producto:");

        txt_producto.setEditable(false);
        txt_producto.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setText("Cant.:");

        txt_cant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cantKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cantKeyTyped(evt);
            }
        });

        jLabel4.setText("P. Venta:");

        txt_venta.setEditable(false);
        txt_venta.setBackground(new java.awt.Color(255, 255, 255));

        jLabel5.setText("Parcial");

        txt_parcial.setEditable(false);
        txt_parcial.setBackground(new java.awt.Color(255, 255, 255));

        btn_addproducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/add.png"))); // NOI18N
        btn_addproducto.setText("Agregar");
        btn_addproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addproductoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 144, Short.MAX_VALUE)
                                .addComponent(btn_addproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txt_parcial, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(txt_producto))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_codbarra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cant, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_addproducto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_producto, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_venta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_parcial, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        t_detalle.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        t_detalle.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                t_detalleMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(t_detalle);

        jLabel13.setText("Total Items:");

        lbl_items.setText("0");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/delete.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.setEnabled(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl_items)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(lbl_items)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("Fecha:");

        txt_fecha.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setText("Nro. Documento Cliente:");

        jTextField7.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField7KeyPressed(evt);
            }
        });

        jLabel8.setText("Nombre Cliente:");

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jLabel9.setText("Comprobante:");

        jLabel10.setText("Numeracion:");

        jTextField9.setEditable(false);

        cbx_canje.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NOTA DE VENTA", "BOLETA", "FACTURA" }));
        cbx_canje.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbx_canjeItemStateChanged(evt);
            }
        });
        cbx_canje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbx_canjeActionPerformed(evt);
            }
        });

        jLabel11.setText("Total:");

        lbl_total.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        lbl_total.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_total.setText("S/ 0.00");

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/printer.png"))); // NOI18N
        jButton2.setText("Guardar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Correo:");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/find.png"))); // NOI18N
        jButton1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1)
                    .addComponent(lbl_total, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_fecha))
                    .addComponent(jTextField8)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel12))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbx_canje, 0, 118, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_fecha, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbx_canje, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_total)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_codbarraKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codbarraKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_codbarra.getText().length() > 5) {
                producto.setCodbarra(txt_codbarra.getText().trim());
                if (producto.buscarBarra()) {
                    if (valida_tabla(producto.getId())) {
                        producto.obtenerDatos();
                        txt_producto.setText(producto.getNombre());
                        txt_venta.setText(c_varios.formato_precio(producto.getPrecio()));
                        txt_parcial.setText(c_varios.formato_precio(producto.getPrecio()));
                        txt_cant.setText("1");
                        txt_cant.requestFocus();
                        txt_cant.selectAll();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "CODIGO DE BARRAS NO ENCONTRADO");
                }

            } else {
                JOptionPane.showMessageDialog(null, "CODIGO DE BARRAS ES MENOR A 6 CINCO DIGITOS");
            }
        }
    }//GEN-LAST:event_txt_codbarraKeyPressed

    private void txt_codbarraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codbarraKeyTyped
        c_varios.solo_numeros(evt);
        c_varios.limitar_caracteres(evt, txt_codbarra, 14);
    }//GEN-LAST:event_txt_codbarraKeyTyped

    private void txt_cantKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_cant.getText().length() > 0) {
                double cantidad = Double.parseDouble(txt_cant.getText());
                double parcial = cantidad * producto.getPrecio();
                txt_parcial.setText(c_varios.formato_precio(parcial));
                btn_addproducto.requestFocus();
            }
        }
    }//GEN-LAST:event_txt_cantKeyPressed

    private void btn_addproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addproductoActionPerformed
        if (producto.getId() != 0) {
            int cantidad = Integer.parseInt(txt_cant.getText());
            double parcial = cantidad * producto.getPrecio();
            Object fila[] = new Object[6];
            fila[0] = producto.getId();
            fila[1] = producto.getNombre();
            fila[2] = cantidad;
            fila[3] = c_varios.formato_numero(producto.getPrecio());
            fila[4] = c_varios.formato_numero(parcial);
            fila[5] = c_varios.formato_numero(producto.getCosto());
            modelo.addRow(fila);
            calcularTotal();
            limpiar();
        }


    }//GEN-LAST:event_btn_addproductoActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int input = JOptionPane.showConfirmDialog(null, "Estas Seguro de Grabar?");

        if (input == 0) {
            int vestado = 2;

            obtenerDocumento();
            venta.obtener_id();
            venta.setDoc(jTextField7.getText());
            venta.setFecha(c_varios.formato_fecha_mysql(txt_fecha.getText()));
            venta.setHora(c_varios.getFechaHora());
            venta.setNombre(jTextField8.getText());
            venta.setCorreo(jTextField1.getText());
            venta.setTotal(total);
            venta.setIdtido(tido.getId());
            venta.setSerie(tido.getSerie());
            venta.setNumero(tido.getNumero());
            if (venta.getIdtido() == 1) {
                vestado = 1;
            }

            venta.setEstado(vestado);
            venta.insertar();

            detalle.setIdVenta(venta.getId());
            int contarFilas = t_detalle.getRowCount();
            for (int i = 0; i < contarFilas; i++) {
                detalle.setIdProducto(Integer.parseInt(t_detalle.getValueAt(i, 0).toString()));
                detalle.setCantidad(Double.parseDouble(t_detalle.getValueAt(i, 2).toString()));
                detalle.setPrecio(Double.parseDouble(t_detalle.getValueAt(i, 3).toString()));
                detalle.setCosto(Double.parseDouble(t_detalle.getValueAt(i, 5).toString()));
                detalle.insertar();
            }

            this.dispose();

            //proceso para imprimir ticket
            try {
                rptTicket ticket = new rptTicket(venta.getId());
                ticket.generarTicket();

            } catch (FileNotFoundException | DocumentException ex) {
                System.out.println(ex.getLocalizedMessage());
            } catch (IOException | SQLException | PrintException | PrinterException ex) {
                System.out.println(ex.getLocalizedMessage());
            }

            if (venta.getIdtido() == 3) {
                GenerarFS generate = new GenerarFS(venta.getId());
                generate.generar_archivos();
                venta.setEstado(1);
                venta.actualizarEstado();
            }

            frm_reg_venta formulario = new frm_reg_venta();
            c_varios.llamar_ventana(formulario);

        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField7KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField7KeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (jTextField7.getText().length()) {
                case 8: {
                    String json = cl_json_entidad.getJSONDNI_LUNASYSTEMS(jTextField7.getText());
                    try {
                        String persona = cl_json_entidad.showJSONDNIL(json);
                        jTextField8.setText(persona);
                        jTextField1.requestFocus();
                    } catch (ParseException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }
                    break;
                }
                case 11: {
                    String json = cl_json_entidad.getJSONRUC_LUNASYSTEMS(jTextField7.getText());
                    try {
                        String[] persona = cl_json_entidad.showJSONRUC_JMP(json);
                        jTextField8.setText(persona[0]);
                        jTextField1.requestFocus();
                    } catch (ParseException ex) {
                        System.out.println(ex.getLocalizedMessage());
                    }
                    break;
                }
                default:
                    JOptionPane.showMessageDialog(null, "DNI DEBE TENER 8 u 11 DIGITOS");
                    break;
            }
        }
    }//GEN-LAST:event_jTextField7KeyPressed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        switch (jTextField7.getText().length()) {
            case 8: {
                String json = cl_json_entidad.getJSONDNI_LUNASYSTEMS(jTextField7.getText());
                try {
                    String persona = cl_json_entidad.showJSONDNIL(json);
                    jTextField8.setText(persona);
                    jTextField1.requestFocus();
                } catch (ParseException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            }
            case 11: {
                String json = cl_json_entidad.getJSONRUC_LUNASYSTEMS(jTextField7.getText());
                try {
                    String[] persona = cl_json_entidad.showJSONRUC_JMP(json);
                    jTextField8.setText(persona[0]);
                    jTextField1.requestFocus();
                } catch (ParseException ex) {
                    System.out.println(ex.getLocalizedMessage());
                }
                break;
            }
            default:
                JOptionPane.showMessageDialog(null, "DNI DEBE TENER 8 u 11 DIGITOS");
                break;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void t_detalleMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_t_detalleMouseClicked
        if (evt.getClickCount() == 2) {
            fila_seleccionada = t_detalle.getSelectedRow();
            jButton3.setEnabled(true);
        }
    }//GEN-LAST:event_t_detalleMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        jButton3.setEnabled(false);
        modelo.removeRow(fila_seleccionada);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void cbx_canjeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbx_canjeActionPerformed
        // obtenerDocumento();
    }//GEN-LAST:event_cbx_canjeActionPerformed

    private void cbx_canjeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbx_canjeItemStateChanged
        if (cbx_canje.isValid()) {
            obtenerDocumento();
        }
    }//GEN-LAST:event_cbx_canjeItemStateChanged

    private void txt_cantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cantKeyTyped
        c_varios.solo_precio(evt);
        c_varios.limitar_caracteres(evt, txt_cant, 5);
    }//GEN-LAST:event_txt_cantKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_addproducto;
    private javax.swing.JComboBox<String> cbx_canje;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private javax.swing.JLabel lbl_items;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JTable t_detalle;
    private javax.swing.JTextField txt_cant;
    private javax.swing.JTextField txt_codbarra;
    private javax.swing.JTextField txt_fecha;
    private javax.swing.JTextField txt_parcial;
    private javax.swing.JTextField txt_producto;
    private javax.swing.JTextField txt_venta;
    // End of variables declaration//GEN-END:variables
}
