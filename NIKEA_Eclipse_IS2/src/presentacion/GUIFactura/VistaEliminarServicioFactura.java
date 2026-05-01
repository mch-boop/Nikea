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
import javax.swing.SwingUtilities;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.factura.TLineaFactura;

@SuppressWarnings("serial")
public class VistaEliminarServicioFactura extends JFrame implements IGUI {

    private JTextField txtIdServicio;
    private JTextField txtCantidad;
    private JButton btnAceptar, btnCancelar;

    public VistaEliminarServicioFactura() {
        setTitle("Eliminar Servicio");
        initGUI();
    }

    private void limpiarCampos() {
        txtIdServicio.setText("");
        txtCantidad.setText("");
    }

    private void initGUI() {

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdServicio = new JTextField(20);
        txtCantidad = new JTextField(20);

        // BOTONES
        JPanel pBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");

        pBotones.add(btnAceptar);
        pBotones.add(btnCancelar);

        // ACEPTAR
        btnAceptar.addActionListener(e -> {

            try {

                if (txtIdServicio.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El ID del servicio es obligatorio.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idServicio = Integer.parseInt(txtIdServicio.getText());

                if (idServicio <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID del servicio debe ser mayor que 0.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (txtCantidad.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "La cantidad es obligatoria.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int cantidad = Integer.parseInt(txtCantidad.getText());

                if (cantidad <= 0) {
                    JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor que 0.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                TLineaFactura tLinea = new TLineaFactura();
                tLinea.setIdProducto(idServicio);
                tLinea.setCantidad(cantidad);

                Controlador.getInstance().accion(Eventos.ELIMINAR_SERVICIO, tLinea);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los campos numéricos deben contener valores válidos.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        // CANCELAR
        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            setVisible(false);
            dispose();
        });

        // FORMULARIO
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ajuste = new GridBagConstraints();
        ajuste.fill = GridBagConstraints.HORIZONTAL;
        ajuste.insets = new Insets(5, 5, 5, 5);

        // ID Servicio
        ajuste.gridx = 0;
        ajuste.gridy = 0;
        formPanel.add(new JLabel("ID Servicio:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtIdServicio, ajuste);

        // Cantidad
        ajuste.gridx = 0;
        ajuste.gridy = 1;
        formPanel.add(new JLabel("Cantidad:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtCantidad, ajuste);

        // TÍTULO
        JLabel lblTitulo = new JLabel("Introduzca los datos del servicio a eliminar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

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

    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {

                case Eventos.RES_ELIMINAR_SERVICIO_OK:
                    JOptionPane.showMessageDialog(this, "Servicio eliminado correctamente.");
                    limpiarCampos();
                    break;

                case Eventos.RES_ELIMINAR_SERVICIO_KO_NO_EXISTE:
                    JOptionPane.showMessageDialog(this, "El servicio no existe en la venta.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;

                case Eventos.RES_ELIMINAR_SERVICIO_KO_BORRADO_DE_MAS:
                    JOptionPane.showMessageDialog(this, "Se intentó eliminar más cantidad de la existente.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;

                case Eventos.RES_ELIMINAR_SERVICIO_KO:
                default:
                    JOptionPane.showMessageDialog(this, "Error al eliminar el servicio.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}
