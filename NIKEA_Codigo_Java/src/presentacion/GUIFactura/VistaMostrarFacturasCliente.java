package presentacion.GUIFactura;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.List;

import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings({ "serial" })
public class VistaMostrarFacturasCliente extends JDialog implements IGUI {

    private JTextField txtIdCliente;
    private JButton btnBuscar, btnCancelar;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTable tablaLineas;
    private DefaultTableModel modeloLineas;
    private List<TFactura> facturasCliente;

    public VistaMostrarFacturasCliente() {
        super(null, "Mostrar Facturas de Cliente", ModalityType.APPLICATION_MODAL);
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
        txtIdCliente.setText("");
        modelo.setRowCount(0);
        modeloLineas.setRowCount(0);
        facturasCliente = null;
    }

    private void initGUI() {

        setLayout(new BorderLayout());

        txtIdCliente = new JTextField(20);

        // BOTONES
        JPanel pBotones = new JPanel();
        btnBuscar = new JButton("BUSCAR");
        btnCancelar = new JButton("CANCELAR");

        pBotones.add(btnBuscar);
        pBotones.add(btnCancelar);

        // MODELO TABLA
        modelo = new DefaultTableModel(new Object[] { "ID Factura", "ID Cliente", "ID Vendedor", "Fecha", "Total" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(600, 200));

        modeloLineas = new DefaultTableModel(new Object[] { "ID Producto", "Cantidad", "Precio Unitario", "Subtotal" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaLineas = new JTable(modeloLineas);
        JScrollPane scrollLineas = new JScrollPane(tablaLineas);
        scrollLineas.setPreferredSize(new Dimension(600, 150));

        tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int fila = tabla.getSelectedRow();
                    if (fila >= 0 && facturasCliente != null && fila < facturasCliente.size()) {
                        cargarTablaLineas(facturasCliente.get(fila).getLineas());
                    } else {
                        cargarTablaLineas(null);
                    }
                }
            }
        });

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

        // PANEL NORTE: título, formulario, botones
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(lblTitulo);
        northPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        northPanel.add(formPanel);
        northPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        northPanel.add(pBotones);

        // PANEL SUR: etiqueta y tabla líneas
        JPanel southPanel = new JPanel();
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        southPanel.add(new JLabel("Líneas de la factura seleccionada:"));
        southPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        southPanel.add(scrollLineas);

        add(northPanel, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);

        setSize(800, 600);
        setResizable(true);
        setLocationRelativeTo(null);
    }

    // auxiliares carga datos
    private void cargarTabla(List<TFactura> facturas) {
        modelo.setRowCount(0);
        cargarTablaLineas(null);

        facturasCliente = facturas;

        if (facturas == null || facturas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No existen facturas para este cliente.", "Información",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (TFactura f : facturas) {
            modelo.addRow(
                    new Object[] { f.getId(), f.getIdCliente(), f.getIdVendedor(), sdf.format(f.getFecha()), String.format(java.util.Locale.US, "%.2f", f.getTotal()) });
        }

        // Seleccionar la primera fila automáticamente para mostrar sus líneas
        if (!facturas.isEmpty()) {
            tabla.setRowSelectionInterval(0, 0);
        }
    }

    private void cargarTablaLineas(List<TLineaFactura> lineas) {
        modeloLineas.setRowCount(0);

        if (lineas == null || lineas.isEmpty()) {
            return;
        }

        for (TLineaFactura l : lineas) {
            modeloLineas.addRow(
                    new Object[] { l.getIdServicio(), l.getCantidad(), l.getPrecioUnitario(),
                            l.getCantidad() * l.getPrecioUnitario() });
        }
    }

    @SuppressWarnings("unchecked") // el controlador devuelve List<TFacturas>
    @Override
    public void actualizar(int evento, Object datos) {

        SwingUtilities.invokeLater(() -> {

            switch (evento) {

                case Eventos.RES_MOSTRAR_FACTURAS_CLIENTE_OK:
                    cargarTabla((List<TFactura>) datos);
                    txtIdCliente.setText("");
                    break;

                case Eventos.RES_MOSTRAR_FACTURAS_CLIENTE_KO:
                    JOptionPane.showMessageDialog(this, "No se pudieron cargar las facturas del cliente.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    limpiarCampos();
                    break;
            }
        });
    }
}
