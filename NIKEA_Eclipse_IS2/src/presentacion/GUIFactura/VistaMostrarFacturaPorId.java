package presentacion.GUIFactura;

import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;

import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings({ "serial", "this-escape" })
public class VistaMostrarFacturaPorId extends JDialog implements IGUI {

    private JTextField txtIdFactura;
    private JTextArea areaDetalles;
    private JButton btnBuscar, btnLimpiar, btnCancelar;

    public VistaMostrarFacturaPorId() {
        super(null, "Buscar Factura por ID", ModalityType.APPLICATION_MODAL);
        initGUI();

        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                limpiarCampos();
            }
        });
    }

    @Override
    public void setVisible(boolean b) {
        if (b) {
            limpiarCampos();
        }
        super.setVisible(b);
    }

    private void limpiarCampos() {
        txtIdFactura.setText("");
        areaDetalles.setText("");
        areaDetalles.setPreferredSize(new Dimension(363, 200));
        pack();
    }

    private void initGUI() {

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Introduzca el ID de la factura a buscar:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // PANEL DE BÚSQUEDA
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txtIdFactura = new JTextField(10);
        btnBuscar = new JButton("BUSCAR");
        panelBusqueda.add(new JLabel("ID Factura:"));
        panelBusqueda.add(txtIdFactura);
        panelBusqueda.add(btnBuscar);

        areaDetalles = new JTextArea();
        areaDetalles.setEditable(false);
        areaDetalles.setBorder(BorderFactory.createTitledBorder("Detalles de la factura"));
        areaDetalles.setFont(new Font("Monospaced", Font.PLAIN, 13));
        areaDetalles.setPreferredSize(new Dimension(363, 200));
        JScrollPane scroll = new JScrollPane(areaDetalles);

        JPanel panelBotones = new JPanel();
        btnLimpiar = new JButton("LIMPIAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnLimpiar);
        panelBotones.add(btnCancelar);

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

        btnLimpiar.addActionListener(e -> limpiarCampos());

        btnCancelar.addActionListener(e -> {
            limpiarCampos();
            setVisible(false);
            dispose();
        });

        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBusqueda);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(scroll);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        viewPanel.add(panelBotones);

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
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    StringBuilder sb = new StringBuilder();
                    sb.append(" ------------------------------------------ \n");
                    sb.append("          DETALLES DE LA FACTURA           \n");
                    sb.append(" ------------------------------------------ \n");
                    sb.append("ID:          ").append(factura.getId()).append("\n");
                    sb.append("Vendedor:    ").append(factura.getIdVendedor()).append("\n");
                    sb.append("Cliente:     ").append(factura.getIdCliente()).append("\n");
                    sb.append("Descuento:   ").append(factura.getIdDescuento()).append("\n");
                    sb.append("Fecha:       ").append(sdf.format(factura.getFecha())).append("\n");
                    sb.append("Total:       ").append(String.format(java.util.Locale.US, "%.2f", factura.getTotal())).append("\n");
                    sb.append("Cerrada:     ").append(factura.isCerrada()).append("\n\n");
                    sb.append("Líneas de factura:\n");

                    if (factura.getLineas() == null || factura.getLineas().isEmpty()) {
                        sb.append("  No hay líneas de factura registradas.\n");
                    } else {
                        for (TLineaFactura linea : factura.getLineas()) {
                            sb.append("  Producto: ").append(linea.getIdProducto())
                                    .append(" | Cantidad: ").append(linea.getCantidad())
                                    .append(" | Precio unitario: ").append(linea.getPrecioUnitario())
                                    .append(" | Subtotal: ").append(linea.getSubtotal()).append("\n");
                        }
                    }

                    areaDetalles.setText(sb.toString());
                    areaDetalles.setCaretPosition(0);
                    areaDetalles.setPreferredSize(null);
                    pack();
                    break;

                case Eventos.RES_BUSCAR_FACTURA_KO:
                    areaDetalles.setText("");
                    areaDetalles.setPreferredSize(new Dimension(363, 200));
                    pack();
                    JOptionPane.showMessageDialog(this, "Factura no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
                    txtIdFactura.requestFocus();
                    break;
            }
        });
    }
}
