package presentacion.GUIDescuento;

import java.awt.*;
import javax.swing.*;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaAnnadirDescuento extends JDialog implements IGUI {

    // ATRIBUTOS
    private JTextField txtIdFactura;
    private JTextField txtIdDescuento;
    private JButton btnAceptar, btnCancelar;

    // CONSTRUCTORA
    public VistaAnnadirDescuento() {
        super(null, "Añadir Descuento a Factura", ModalityType.APPLICATION_MODAL);
        setTitle("Añadir Descuento a Factura");
        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        initGUI();
    }

    // MÉTODOS
    private void limpiarCampos() {
        txtIdFactura.setText("");
        txtIdDescuento.setText("");
        txtIdFactura.requestFocus();
    }

    private void initGUI() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.NORTHWEST;

        txtIdFactura = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID de Factura:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdFactura, gbc);

        txtIdDescuento = new JTextField(15);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("ID de Descuento:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtIdDescuento, gbc);

        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            dispose();
        });

        btnAceptar.addActionListener(e -> {
            try {
                if (txtIdFactura.getText().trim().isEmpty() || txtIdDescuento.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ambos campos (Factura y Descuento) son obligatorios.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idFactura = Integer.parseInt(txtIdFactura.getText().trim());
                int idDescuento = Integer.parseInt(txtIdDescuento.getText().trim());

                int[] datos = new int[]{idFactura, idDescuento};
                
                Controlador.getInstance().accion(Eventos.ANNADIR_DESCUENTO_FACTURA, datos);

            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(this, "Los IDs deben ser números enteros válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelBotones);

        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    @Override
    public void actualizar(int evento, Object datos) {
        SwingUtilities.invokeLater(() -> {
            switch (evento) {
                case Eventos.RES_ANNADIR_DESCUENTO_FACTURA_OK:
                    limpiarCampos();
                    JOptionPane.showMessageDialog(this, "Descuento aplicado con éxito a la factura.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    break;

                case Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_FACTURA_NO_EXISTE:
                    JOptionPane.showMessageDialog(this, "La factura indicada no existe.", "Error", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtIdFactura.requestFocus());

                    break;

                case Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_DESCUENTO_NO_EXISTE:
                    JOptionPane.showMessageDialog(this, "El descuento indicado no existe o se encuentra inactivo.", "Error", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtIdDescuento.requestFocus());
                    break;

                case Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_REQUISITOS:
                    JOptionPane.showMessageDialog(this, "La factura no cumple los requisitos (importe mínimo o cantidad de productos) para aplicar este descuento.", "Requisitos no cumplidos", JOptionPane.WARNING_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtIdFactura.requestFocus());
                    break;

                case Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO:
                    JOptionPane.showMessageDialog(this, "Error al añadir el descuento a la factura.", "Error Grave", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtIdFactura.requestFocus());
                    break;
                    
                case Eventos.RES_ANNADIR_DESCUENTO_FACTURA_KO_YA_TIENE_DESCUENTO:
                    JOptionPane.showMessageDialog(this, "Esta factura ya tiene un descuento aplicado. Solo se permite uno por factura.", "Descuento ya aplicado", JOptionPane.WARNING_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtIdFactura.requestFocus());
                    break;

                default:
                    JOptionPane.showMessageDialog(this, "Error desconocido / descontrolado", "Error Grave", JOptionPane.ERROR_MESSAGE);
                    SwingUtilities.invokeLater(() -> txtIdFactura.requestFocus());
                    break;
            }
        });
    }
    
    // Reset al mostrar
    @Override
    public void setVisible(boolean b) {
        if (b) limpiarCampos();
        super.setVisible(b);
    }
}