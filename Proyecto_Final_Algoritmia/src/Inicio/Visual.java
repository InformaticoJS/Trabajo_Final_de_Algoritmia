package Inicio;

import Otros.Limpiar_txt;
import Otros.imgTabla;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.System.in;
import java.util.StringTokenizer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import sun.rmi.server.MarshalInputStream;

public class Visual extends javax.swing.JFrame {

    Limpiar_txt lt = new Limpiar_txt();

    private String ruta_txt = "mi.txt";

    Producto p;
    Proceso rp;

    int clic_tabla;

    public Visual() {
        initComponents();
        rp = new Proceso();

        try {
            cargar_txt();
            listarRegistro();
        } catch (Exception ex) {
            mensaje("No existe el archivo txt");
        }
    }

    public void cargar_txt() {
        //File ruta = new File(ruta_txt);
        try {
            FileInputStream fis = new FileInputStream(ruta_txt);
            ObjectInputStream in = new ObjectInputStream(fis);
            if (in != null) {
                rp = (Proceso) in.readObject();
                in.close();
            }
            /*
            FileReader fi = new FileReader(ruta);
            BufferedReader bu = new BufferedReader(fi);
            
            
            String linea = null;
            while((linea = bu.readLine())!=null){
                StringTokenizer st = new StringTokenizer(linea, ",");
                p = new Producto();
                p.setCodigo(Integer.parseInt(st.nextToken()));
                p.setNombre(st.nextToken());
                p.setPrecio(Double.parseDouble(st.nextToken()));
                p.setDescripcion(st.nextToken());
                rp.agregarRegistro(p);
            }
            bu.close();*/
        } catch (Exception ex) {
            mensaje("Error al cargar archivo: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public void grabar_txt() {
        //FileWriter fw;
        //PrintWriter pw;
        FileOutputStream fos;
        ObjectOutputStream out;
        try {
           // fw = new FileWriter(ruta_txt);
           // pw = new PrintWriter(fw);
            fos = new FileOutputStream(ruta_txt);
            out = new ObjectOutputStream(fos);
            if (out != null) {
                out.writeObject(rp);
                out.close();
            }

            /*for (int i = 0; i < rp.cantidadRegistro(); i++) {
                p = rp.obtenerRegistro(i);
                pw.println(String.valueOf(p.getCodigo() + ", " + p.getNombre() + ", " + p.getPrecio() + ", " + p.getDescripcion()));
            }
            pw.close();*/

        } catch (Exception ex) {
            mensaje("Error al grabar archivo: " + ex.getMessage());
            System.out.println(ex.getMessage());
        }
    }

    public void ingresarRegistro(File ruta) {
        try {
            if (leerCodigo() == -666) {
                mensaje("Ingresar codigo entero");
            } else if (leerNombre() == null) {
                mensaje("Ingresar Nombre");
            } else if (leerPrecio() == -666) {
                mensaje("Ingresar Precio");
            } else if (leerDescripcion() == null) {
                mensaje("Ingresar Descripcion");
            } else {
                p = new Producto(leerCodigo(), leerNombre(), leerPrecio(), leerDescripcion(),leerFoto(ruta));
                if (rp.buscaCodigo(p.getCodigo()) != -1) {
                    mensaje("Este codigo ya existe");
                } else {
                    rp.agregarRegistro(p);
                }

                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
            }
        } catch (Exception ex) {
            mensaje(ex.getMessage());
        }
    }

    public void modificarRegistro(File ruta) {
        try {
            if (leerCodigo() == -666) {
                mensaje("Ingresar codigo entero");
            } else if (leerNombre() == null) {
                mensaje("Ingresar Nombre");
            } else if (leerPrecio() == -666) {
                mensaje("Ingresar Precio");
            } else if (leerDescripcion() == null) {
                mensaje("Ingresar Descripcion");
            } else {
                int codigo = rp.buscaCodigo(leerCodigo());
                if(txtRuta.getText().equalsIgnoreCase("")){ 
                p = new Producto(leerCodigo(), leerNombre(), leerPrecio(), leerDescripcion(),leerFoto2(codigo));
                }else{
                p = new Producto(leerCodigo(), leerNombre(), leerPrecio(), leerDescripcion(),leerFoto(ruta));
                }

                if (codigo == -1) {
                    rp.agregarRegistro(p);
                } else {
                    rp.modificarRegistro(codigo, p);
                }

                grabar_txt();
                listarRegistro();
                lt.limpiar_texto(panel);
            }
        } catch (Exception ex) {
            mensaje(ex.getMessage());
        }
    }

    public void eliminarRegistro() {
        try {
            if (leerCodigo() == -666) {
                mensaje("Ingrese codigo entero");
            } else {
                int codigo = rp.buscaCodigo(leerCodigo());
                if (codigo == -1) {
                    mensaje("codigo no existe");
                } else {
                    int s = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar este producto", "Si/No", 0);
                    if (s == 0) {
                        rp.eliminarRegistro(codigo);

                        grabar_txt();
                        listarRegistro();
                        lt.limpiar_texto(panel);
                    }
                }

            }
        } catch (Exception ex) {
            mensaje(ex.getMessage());
        }
    }

    public void listarRegistro() {
        DefaultTableModel dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dt.addColumn("Codigo");
        dt.addColumn("Nombre");
        dt.addColumn("Precio");
        dt.addColumn("Descripcion");
        dt.addColumn("Foto");

        tabla.setDefaultRenderer(Object.class, new imgTabla());

        Object fila[] = new Object[dt.getColumnCount()];
        for (int i = 0; i < rp.cantidadRegistro(); i++) {
            p = rp.obtenerRegistro(i);
            fila[0] = p.getCodigo();
            fila[1] = p.getNombre();
            fila[2] = p.getPrecio();
            fila[3] = p.getDescripcion();
            try{
                byte[]bi=p.getFoto();
                BufferedImage image=null;
                InputStream in =new ByteArrayInputStream(bi);
                image=ImageIO.read(in);
                ImageIcon img = new ImageIcon(image.getScaledInstance(60, 60, 0));
                fila[4]=new JLabel(img);
                
            }catch(Exception ex){
                fila[4]="No imagen";
            }
            dt.addRow(fila);
        }
        tabla.setModel(dt);
        tabla.setRowHeight(60);
    }

    public int leerCodigo() {
        try {
            int codigo = Integer.parseInt(txtCodigo.getText().trim());
            return codigo;
        } catch (Exception ex) {
            return -666;
        }
    }

    public String leerNombre() {
        try {
            String nombre = txtNombre.getText().trim().replace(" ", "_");
            return nombre;
        } catch (Exception ex) {
            return null;
        }
    }

    public double leerPrecio() {
        try {
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            return precio;
        } catch (Exception ex) {
            return -666;
        }
    }

    public Object leerDescripcion() {
        try {
            Object descripcion = txtDescripcion.getText().trim();
            return descripcion;
        } catch (Exception ex) {
            return null;
        }
    }

    public byte[] leerFoto(File ruta) {
        try {
            byte[] icono = new byte[(int) ruta.length()];
            InputStream input = new FileInputStream(ruta);
            input.read(icono);
            return icono;
        } catch (Exception ex) {
            return null;
        }
    }

    
    public byte[] leerFoto2(int codigo){
            p = rp.obtenerRegistro(codigo);
            try{
               return p.getFoto();
            }catch(Exception ex){
               return null;
            }
        }
    public void mensaje(String texto) {
        JOptionPane.showMessageDialog(null, texto);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        txtDescripcion = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtPrecio = new javax.swing.JTextField();
        txtNombre = new javax.swing.JTextField();
        txtCodigo = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        txtRuta = new javax.swing.JTextField();
        lblFoto = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Base de Datos con Bloc de Notas .txt");

        panel.setBackground(new java.awt.Color(235, 235, 235));

        tabla.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla);

        jButton1.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/calcular.png"))); // NOI18N
        jButton1.setText("Guardar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/edit_32px.png"))); // NOI18N
        jButton2.setText("Modificar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/trash_26px.png"))); // NOI18N
        jButton3.setText("Eliminar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        txtDescripcion.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel4.setText("Descripcion:");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel3.setText("Precio:");

        txtPrecio.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtNombre.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtCodigo.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel1.setText("Codigo:");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setText("Foto:");

        jButton4.setText("Buscar foto...");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        txtRuta.setEditable(false);
        txtRuta.setFont(new java.awt.Font("Arial", 0, 10)); // NOI18N

        lblFoto.setBackground(new java.awt.Color(204, 255, 204));
        lblFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFoto.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/nuevo.png"))); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 0, 153));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("PROYECTO FINAL");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Iconos/logo_opt (1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addComponent(jLabel7)
                .addGap(31, 31, 31)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel6)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(txtDescripcion))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(82, 82, 82)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton5))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(53, 53, 53)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                                    .addComponent(txtPrecio))
                                .addGap(20, 20, 20)
                                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton4))
                                    .addComponent(txtRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jButton4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtRuta))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        File ruta = new File(txtRuta.getText());
        this.ingresarRegistro(ruta);


    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        File ruta = new File(txtRuta.getText());
        this.modificarRegistro(ruta);


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.eliminarRegistro();


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        JFileChooser jf = new JFileChooser();
        FileNameExtensionFilter fil = new FileNameExtensionFilter("JPG, PNG & GIF", "jpg", "png", "gif");
        jf.setFileFilter(fil);
        jf.setCurrentDirectory(new File("Fotos"));
        int el = jf.showOpenDialog(this);
        if (el == JFileChooser.APPROVE_OPTION) {
            txtRuta.setText(jf.getSelectedFile().getAbsolutePath());
            lblFoto.setIcon(new ImageIcon(txtRuta.getText()));
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaMouseClicked

        clic_tabla = tabla.rowAtPoint(evt.getPoint());

        int codigo = (int) tabla.getValueAt(clic_tabla, 0);
        String nombre = "" + tabla.getValueAt(clic_tabla, 1);
        double precio = (double) tabla.getValueAt(clic_tabla, 2);
        Object descripcion = "" + tabla.getValueAt(clic_tabla, 3);

        txtCodigo.setText(String.valueOf(codigo));
        txtNombre.setText(nombre);
        txtPrecio.setText(String.valueOf(precio));
        txtDescripcion.setText(String.valueOf(descripcion));

        try {
            JLabel lbl = (JLabel) tabla.getValueAt(clic_tabla, 4);
            lblFoto.setIcon(lbl.getIcon());
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_tablaMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Limpiar_txt lp = new Limpiar_txt();
        lp.limpiar_texto(panel);
    }//GEN-LAST:event_jButton5ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Visual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JPanel panel;
    private javax.swing.JTable tabla;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPrecio;
    private javax.swing.JTextField txtRuta;
    // End of variables declaration//GEN-END:variables
}
