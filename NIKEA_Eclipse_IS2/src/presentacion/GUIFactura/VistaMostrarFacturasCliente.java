package presentacion.GUIFactura;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import negocio.factura.TFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarFacturasCliente extends JFrame implements IGUI {

    private JTextField txtIdCliente;
    private JButton btnBuscar, btnCancelar;
    private JTable tabla;
    private DefaultTableModel modelo;

    public VistaMostrarFacturasCliente() {
        setTitle("Mostrar Facturas de Cliente");
        initGUI();
    }

    private void limpiarCampos() {
        txtIdCliente.setText("");
        modelo.setRowCount(0);
    }

    private void initGUI() {

        JPanel viewPanel = new JPanel();
        viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
        viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtIdCliente = new JTextField(20);

        // BOTONES
        JPanel pBotones = new JPanel();
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");

        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        // MODELO TABLA
        modelo = new DefaultTableModel(new Object[] { "ID Factura", "ID Cliente", "ID Vendedor", "Fecha", "Total" }, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // BUSCAR
        btnBuscar.addActionListener(e -> {

            try {

                if (txtIdCliente.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El ID del cliente es obligatorio.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idCliente = Integer.parseInt(txtIdCliente.getText());

                if (idCliente <= 0) {
                    JOptionPane.showMessageDialog(this, "El ID del cliente debe ser mayor que 0.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Controlador.getInstance().accion(Eventos.MOSTRAR_FACTURAS_CLIENTE, idCliente);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID del cliente debe ser numérico.", "Error",
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

        // ID Cliente
        ajuste.gridx = 0;
        ajuste.gridy = 0;
        formPanel.add(new JLabel("ID Cliente:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtIdCliente, ajuste);

        // TÍTULO
        JLabel lblTitulo = new JLabel("Introduzca el ID del cliente:");
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        viewPanel.add(lblTitulo);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        viewPanel.add(formPanel);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        viewPanel.add(scroll);
        viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        viewPanel.add(pBotones);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
    }

    // CARGAR DATOS
    private void cargarTabla(List<TFactura> facturas) {
        modelo.setRowCount(0);

        if (facturas == null || facturas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existen facturas para este cliente.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        for (TFactura f : facturas) {
            String fechaFormateada = sdf.format(f.getFecha());

            modelo.addRow(
                    new Object[] { f.getId(), f.getIdCliente(), f.getIdVendedor(), fechaFormateada, f.getTotal() });
        }
    }

    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {

                case Eventos.RES_MOSTRAR_FACTURAS_CLIENTE_OK:
                    cargarTabla((List<TFactura>) datos);
                    break;

                case Eventos.RES_MOSTRAR_FACTURAS_CLIENTE_KO:
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar las facturas del cliente.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    break;
            }
        });
    }
}
