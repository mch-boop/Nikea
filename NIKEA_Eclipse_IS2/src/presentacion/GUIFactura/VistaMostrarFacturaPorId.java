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

import negocio.factura.TFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarFacturaPorId extends JFrame implements IGUI {

    private JTextField txtIdFactura;
    private JButton btnBuscar, btnCancelar;

    public VistaMostrarFacturaPorId() {
        setTitle("Buscar Factura por ID");
        initGUI();
    }

    private void limpiarCampos() {
        txtIdFactura.setText("");
    }

    private void initGUI() {

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdFactura = new JTextField(20);

        // BOTONES
        JPanel pBotones = new JPanel();
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");

        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        // BUSCAR
        btnBuscar.addActionListener(e -> {

            try {

                if (txtIdFactura.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El ID de la factura es obligatorio.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idFactura = Integer.parseInt(txtIdFactura.getText());

                if (idFactura <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID de la factura debe ser mayor que 0.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Controlador.getInstance().accion(Eventos.BUSCAR_FACTURA, idFactura);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID de la factura debe ser numérico.", "Error",
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

        // ID Factura
        ajuste.gridx = 0;
        ajuste.gridy = 0;
        formPanel.add(new JLabel("ID Factura:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtIdFactura, ajuste);

        // TÍTULO
        JLabel lblTitulo = new JLabel("Introduzca el ID de la factura a buscar:");
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

                case Eventos.RES_BUSCAR_FACTURA_OK:
                    TFactura factura = (TFactura) datos;
                    String info = "ID: " + factura.getId() + "\n" +
                            "Vendedor: " + factura.getIdVendedor() + "\n" +
                            "Cliente: " + factura.getIdCliente() + "\n" +
                            "Descuento: " + factura.getIdDescuento() + "\n" +
                            "Fecha: " + factura.getFecha() + "\n" +
                            "Total: " + factura.getTotal() + "\n" +
                            "Cerrada: " + factura.isCerrada();
                    JOptionPane.showMessageDialog(this, info, "Factura encontrada", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    break;

                case Eventos.RES_BUSCAR_FACTURA_KO:
                    JOptionPane.showMessageDialog(this, "Factura no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}
