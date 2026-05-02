package presentacion.GUIDescuento;

import java.awt.*;
import javax.swing.*;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaBajaDescuento extends JDialog implements IGUI {

	// ATRIBUTOS
    private JTextField txtId;
    private JButton btnAceptar, btnCancelar;

    // CONSTRUCTORA
    public VistaBajaDescuento() {
    	super(null, "Baja de Descuento", ModalityType.APPLICATION_MODAL);
        setTitle("Baja de Descuento");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    // INIT
    private void initGUI() {
        // Panel principal con margen
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Instrucciones y campo de texto
        JLabel lblInfo = new JLabel("Introduce el ID del descuento que deseas dar de baja:");
        lblInfo.setAlignmentX(CENTER_ALIGNMENT);
        
        txtId = new JTextField(10);
        txtId.setMaximumSize(new Dimension(200, 30));
        txtId.setAlignmentX(CENTER_ALIGNMENT);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAceptar = new JButton("DAR DE BAJA");
        btnCancelar = new JButton("CANCELAR");

        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        // Acción Cancelar
        btnCancelar.addActionListener(e -> {
            txtId.setText("");
            dispose();
        });

        // Acción Aceptar
        btnAceptar.addActionListener(e -> {
            String idStr = txtId.getText().trim();
            if (idStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, introduce un ID válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int id = Integer.parseInt(idStr);
                // Llamada al controlador para el borrado lógico
                Controlador.getInstance().accion(Eventos.BAJA_DESCUENTO, id);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Ensamblar
        mainPanel.add(lblInfo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(txtId);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(panelBotones);

        add(mainPanel, BorderLayout.CENTER);
        
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_BAJA_DESCUENTO_OK:
                    JOptionPane.showMessageDialog(this, "Descuento con ID " + datos + " dado de baja correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    txtId.setText("");
                    dispose();
                    break;

                case Eventos.RES_BAJA_DESCUENTO_KO:
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el descuento. Verifica que el ID exista y esté activo.", "Error", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtId.requestFocus());
                    break;

                default:
                    break;
            }
        });
    }
    
    // reset
    
    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }
    
    private void limpiarCampos() {
    	txtId.setText("");
    	txtId.requestFocus();
    	pack();
    }
}