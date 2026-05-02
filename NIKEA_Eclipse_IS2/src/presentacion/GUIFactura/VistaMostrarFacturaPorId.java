package presentacion.GUIFactura;

import java.awt.*;
import javax.swing.*;

import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
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

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                limpiarCampos();
            }
        });
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
                    StringBuilder infoBuilder = new StringBuilder();
                    infoBuilder.append("ID: ").append(factura.getId()).append("\n");
                    infoBuilder.append("Vendedor: ").append(factura.getIdVendedor()).append("\n");
                    infoBuilder.append("Cliente: ").append(factura.getIdCliente()).append("\n");
                    infoBuilder.append("Descuento: ").append(factura.getIdDescuento()).append("\n");
                    infoBuilder.append("Fecha: ").append(factura.getFecha()).append("\n");
                    infoBuilder.append("Total: ").append(factura.getTotal()).append("\n\n");
                    infoBuilder.append("Líneas de factura:\n");

                    if (factura.getLineas() == null || factura.getLineas().isEmpty()) {
                        infoBuilder.append("  No hay líneas de factura registradas.\n");
                    } else {
                        for (TLineaFactura linea : factura.getLineas()) {
                            infoBuilder.append("  Producto: ").append(linea.getIdProducto())
                                    .append(" | Cantidad: ").append(linea.getCantidad())
                                    .append(" | Precio unitario: ").append(linea.getPrecioUnitario())
                                    .append(" | Subtotal: ").append(linea.getSubtotal()).append("\n");
                        }
                    }

                    JTextArea textArea = new JTextArea(infoBuilder.toString());
                    textArea.setEditable(false);
                    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                    JOptionPane.showMessageDialog(this, new JScrollPane(textArea), "Factura encontrada",
                            JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    break;

                case Eventos.RES_BUSCAR_FACTURA_KO:
                    JOptionPane.showMessageDialog(this, "Factura no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                    break;
            }
        });
    }
}
