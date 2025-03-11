package PuntodeVenta;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class PuntoDeVenta extends JFrame {
    private JTextField txtCveCte, txtFolio, txtNombre, txtFecha, txtDireccion, txtRFC, txtArchivo;
    private JTextField txtSubtotal, txtDescuento, txtImpuesto, txtTotal;
    private DefaultTableModel modeloTabla;


    public PuntoDeVenta() {
        setTitle("PUNTO DE VENTA");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior (Datos del Cliente)
        JPanel panelCliente = new JPanel(new GridLayout(3, 4, 5, 5));
        panelCliente.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));

        panelCliente.add(new JLabel("Cve Cte:"));
        txtCveCte = new JTextField(10);
        panelCliente.add(txtCveCte);
        panelCliente.add(new JLabel("Folio:"));
        txtFolio = new JTextField(10);
        panelCliente.add(txtFolio);

        panelCliente.add(new JLabel("Nombre:"));
        txtNombre = new JTextField(10);
        panelCliente.add(txtNombre);
        panelCliente.add(new JLabel("Fecha:"));
        txtFecha = new JTextField(10);
        panelCliente.add(txtFecha);

        panelCliente.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField(10);
        panelCliente.add(txtDireccion);
        panelCliente.add(new JLabel("RFC:"));
        txtRFC = new JTextField(10);
        panelCliente.add(txtRFC);

        add(panelCliente, BorderLayout.NORTH);

        // Tabla de productos
        String[] columnas = {"CANTIDAD", "DESCRIPCIÓN", "P.U.", "IMPORTE"};
        modeloTabla = new DefaultTableModel(columnas, 10);
        JTable tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel inferior (Subtotal, impuestos, botones)
        JPanel panelInferior = new JPanel(new GridLayout(2, 1));
        JPanel panelTotales = new JPanel(new GridLayout(5, 2));
        txtSubtotal = new JTextField(8);
        txtDescuento = new JTextField(8);
        txtImpuesto = new JTextField(8);
        txtTotal = new JTextField(8);
        txtArchivo = new JTextField(8);

        panelTotales.add(new JLabel("Subtotal:"));
        panelTotales.add(txtSubtotal);
        panelTotales.add(new JLabel("Descuento:"));
        panelTotales.add(txtDescuento);
        panelTotales.add(new JLabel("Impuesto:"));
        panelTotales.add(txtImpuesto);
        panelTotales.add(new JLabel("Total:"));
        panelTotales.add(txtTotal);
        panelTotales.add(new JLabel("Nombre del archivo:"));
        panelTotales.add(txtArchivo);

        JPanel panelBotones = new JPanel();
        JButton btnNuevo = new JButton("Nuevo");
        JButton btnGuardar = new JButton("Guardar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnConsultar = new JButton("Consultar");

        panelBotones.add(btnNuevo);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnConsultar);
        panelInferior.add(panelTotales);
        panelInferior.add(panelBotones);

        add(panelInferior, BorderLayout.SOUTH);

        ImageIcon img = new ImageIcon("icono.jpg");
        setIconImage(img.getImage());

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nombreArchivo = txtArchivo.getText();
                StringBuilder datos = new StringBuilder();

                datos.append("Cve Cte: ").append(txtCveCte.getText()).append("\n")
                        .append("Folio: ").append(txtFolio.getText()).append("\n")
                        .append("Nombre: ").append(txtNombre.getText()).append("\n")
                        .append("Fecha: ").append(txtFecha.getText()).append("\n")
                        .append("Dirección: ").append(txtDireccion.getText()).append("\n")
                        .append("RFC: ").append(txtRFC.getText()).append("\n")
                        .append("Subtotal: ").append(txtSubtotal.getText()).append("\n")
                        .append("Descuento: ").append(txtDescuento.getText()).append("\n")
                        .append("Impuesto: ").append(txtImpuesto.getText()).append("\n")
                        .append("Total: ").append(txtTotal.getText()).append("\n\n");

                // Guardar los valores de la tabla
                datos.append("Productos:\n");
                for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                    Object cantidad = modeloTabla.getValueAt(i, 0);
                    Object descripcion = modeloTabla.getValueAt(i, 1);
                    Object precioUnitario = modeloTabla.getValueAt(i, 2);
                    Object importe = modeloTabla.getValueAt(i, 3);

                    if (cantidad != null && descripcion != null && precioUnitario != null && importe != null) {
                        datos.append("Cantidad: ").append(cantidad.toString())
                                .append(", Descripción: ").append(descripcion.toString())
                                .append(", P.U.: ").append(precioUnitario.toString())
                                .append(", Importe: ").append(importe.toString()).append("\n");
                    }
                }
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo + ".txt", true))) {
                    writer.write(datos.toString());
                    writer.flush();
                    JOptionPane.showMessageDialog(null, "Datos guardados en " + nombreArchivo + ".txt");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error al guardar los datos", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnEliminar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int filaSeleccionada = tabla.getSelectedRow();

                if (filaSeleccionada != -1) {
                    int confirmacion = JOptionPane.showConfirmDialog(null,
                            "¿Estás seguro de que deseas eliminar esta fila?",
                            "Confirmar eliminación",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmacion == JOptionPane.YES_OPTION) {
                        modeloTabla.removeRow(filaSeleccionada);
                        modeloTabla.addRow(new Object[]{"", "", "", ""});
                    }
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Por favor, selecciona una fila para eliminar.",
                            "Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        btnNuevo.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                txtCveCte.setText(null);
                txtNombre.setText(null);
                txtDireccion.setText(null);
                txtFolio.setText(null);
                txtFecha.setText(null);
                txtRFC.setText(null);
                txtSubtotal.setText(null);
                txtDescuento.setText(null);
                txtImpuesto.setText(null);
                txtTotal.setText(null);
                txtArchivo.setText(null);

                modeloTabla.setRowCount(0);
                modeloTabla.setRowCount(10);
            }
        });

        btnConsultar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccionar archivo");
                int seleccion = fileChooser.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File archivoSeleccionado = fileChooser.getSelectedFile();
                    txtArchivo.setText(archivoSeleccionado.getAbsolutePath());
                }
            }
        });

        btnModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                double importe = 0, suma = 0, total = 0;

                modeloTabla.getValueAt(0,0);
                for(int i = 0; i<modeloTabla.getRowCount(); i++){
                    Object cantidadObjeto = modeloTabla.getValueAt(i, 0);
                    Object precioObjeto = modeloTabla.getValueAt(i, 2);
                    Object importeObjeto = modeloTabla.getValueAt(i, 3);

                    if(cantidadObjeto != null && precioObjeto != null){
                        double cantidad = Double.parseDouble(cantidadObjeto.toString());
                        double precio = Double.parseDouble(precioObjeto.toString());
                        importe = cantidad * precio;
                        modeloTabla.setValueAt(importe, i, 3);

                        if(importeObjeto != null){
                            suma = suma + Double.parseDouble(importeObjeto.toString());
                        }
                    }
                }
                txtSubtotal.setText(String.valueOf(suma));

                total = suma;

                if(!txtDescuento.getText().isEmpty()){
                    double descuento = Double.parseDouble(txtDescuento.getText());
                    total = total - ((descuento/100) * suma);
                }
                if(!txtImpuesto.getText().isEmpty()){
                    double impuesto = Double.parseDouble(txtImpuesto.getText());
                    total = total + ((impuesto/100) * suma);
                }
                txtTotal.setText(String.valueOf(total));




            }
        });
    }

    public static void main(String[] args) {
        new PuntoDeVenta().setVisible(true);
    }
}
