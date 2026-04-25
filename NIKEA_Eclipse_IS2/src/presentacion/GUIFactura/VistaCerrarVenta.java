package presentacion.GUIFactura;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.factura.TFactura; 

@SuppressWarnings("serial")
public class VistaCerrarVenta extends JFrame implements IGUI {

    // ATRIBUTOS
    private JTextField txtIdCliente, txtIdDescuento, txtFecha;
    private JButton btnAceptar, btnCancelar;

    // CONSTRUCTORA
    public VistaCerrarVenta() {
        setTitle("Cerrar Venta");
        initGUI();
    }

    // Limpia los campos
    private void limpiarCampos() {
        txtIdCliente.setText("");
        txtIdDescuento.setText("");
        txtFecha.setText("");
    }

    // INIT GUI
    private void initGUI() {

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Campos
        txtIdCliente = new JTextField(20);
        txtIdDescuento = new JTextField(20);
        txtFecha = new JTextField(20); 
        
        // Panel botones
        JPanel pBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");

        pBotones.add(btnAceptar);
        pBotones.add(btnCancelar);

        // Acción aceptar
        btnAceptar.addActionListener(e -> {
            try {
                TFactura tFactura = new TFactura();

                //tFactura.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
                //tFactura.setIdVendedor(Integer.parseInt(txtIdVendedor.getText()));
                //tFactura.setFecha(txtFecha.getText());

                Controlador.getInstance().accion(Eventos.CERRAR_VENTA, tFactura);

                limpiarCampos();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, 
                    "Los IDs deben ser números válidos.", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción cancelar
        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            setVisible(false);
            dispose();
        });

        // Panel formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ajuste = new GridBagConstraints();
        ajuste.fill = GridBagConstraints.HORIZONTAL;
        ajuste.insets = new Insets(5, 5, 5, 5);

        // ID Cliente
        ajuste.gridx = 0; ajuste.gridy = 0;
        formPanel.add(new JLabel("ID Cliente:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtIdCliente, ajuste);

        // ID Vendedor
        ajuste.gridx = 0; ajuste.gridy = 1;
        formPanel.add(new JLabel("Descuento %:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtIdDescuento, ajuste);

        // Fecha
        ajuste.gridx = 0; ajuste.gridy = 2;
        formPanel.add(new JLabel("Fecha:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtFecha, ajuste);

        // Título
        JLabel lblTitulo = new JLabel("Introduzca los datos para cerrar la venta:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Añadir componentes
        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        viewPanel.add(formPanel);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        viewPanel.add(pBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // IGUI
    @Override
    public void actualizar(int evento, Object datos) {
    	
        if (evento == Eventos.RES_CERRAR_VENTA_OK) {
            JOptionPane.showMessageDialog(this, 
                "Venta cerrada correctamente con ID: " + (Integer) datos);
        } 
        else if (evento == Eventos.RES_CERRAR_VENTA_KO) {
            JOptionPane.showMessageDialog(this, 
                "No se ha podido cerrar la venta.");
        }
    }
}