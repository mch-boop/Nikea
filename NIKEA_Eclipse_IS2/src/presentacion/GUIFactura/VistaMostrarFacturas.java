package presentacion.GUIFactura;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import negocio.factura.TFactura;
import negocio.factura.TLineaFactura;
import presentacion.IGUI;
import presentacion.controlador.Eventos;

@SuppressWarnings({ "serial", "this-escape" })
public class VistaMostrarFacturas extends JDialog implements IGUI {

	private JTable tabla;
	private DefaultTableModel modelo;
	private JTable tablaLineas;
	private DefaultTableModel modeloLineas;
	private JButton btnCerrar;
	private List<TFactura> facturas;

	public VistaMostrarFacturas() {
		super(null, "Mostrar Facturas", ModalityType.APPLICATION_MODAL);
		initGUI();

		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				limpiarCampos();
			}
		});
	}

	private void initGUI() {
		setLayout(new BorderLayout());

		modelo = new DefaultTableModel(new Object[] { "ID Factura", "ID Cliente", "ID Vendedor", "Fecha", "Total" },
				0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla);
		scroll.setPreferredSize(new Dimension(600, 250));

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
					if (fila >= 0 && facturas != null && fila < facturas.size()) {
						cargarTablaLineas(facturas.get(fila).getLineas());
					} else {
						cargarTablaLineas(null);
					}
				}
			}
		});

		JPanel panelSur = new JPanel();
		panelSur.setLayout(new BoxLayout(panelSur, BoxLayout.Y_AXIS));
		panelSur.add(new JLabel("Lineas de la factura seleccionada:"));
		panelSur.add(Box.createRigidArea(new Dimension(0, 5)));
		panelSur.add(scrollLineas);

		JPanel panelBotones = new JPanel();
		btnCerrar = new JButton("CERRAR");
		panelBotones.add(btnCerrar);
		panelSur.add(panelBotones);

		btnCerrar.addActionListener(e -> {
			limpiarCampos();
			setVisible(false);
			dispose();
		});

		add(scroll, BorderLayout.CENTER);
		add(panelSur, BorderLayout.SOUTH);

		setSize(800, 600);
		setResizable(true);
		setLocationRelativeTo(null);
	}

	private void limpiarCampos() {
		modelo.setRowCount(0);
		modeloLineas.setRowCount(0);
		facturas = null;
	}

	private void cargarTabla(List<TFactura> facturas) {
		modelo.setRowCount(0);
		cargarTablaLineas(null);

		this.facturas = facturas;

		if (facturas == null || facturas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No existen facturas registradas.", "Informacion",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (TFactura f : facturas) {
			modelo.addRow(new Object[] { f.getId(), f.getIdCliente(), f.getIdVendedor(), sdf.format(f.getFecha()),
					String.format(java.util.Locale.US, "%.2f", f.getTotal()) });
		}

		tabla.setRowSelectionInterval(0, 0);
	}

	private void cargarTablaLineas(List<TLineaFactura> lineas) {
		modeloLineas.setRowCount(0);

		if (lineas == null || lineas.isEmpty()) {
			return;
		}

		for (TLineaFactura l : lineas) {
			modeloLineas.addRow(
					new Object[] { l.getIdProducto(), l.getCantidad(), l.getPrecioUnitario(), l.getSubtotal() });
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(int evento, Object datos) {
		SwingUtilities.invokeLater(() -> {
			switch (evento) {
			case Eventos.RES_MOSTRAR_FACTURAS_OK:
				cargarTabla((List<TFactura>) datos);
				setVisible(true);
				break;

			case Eventos.RES_MOSTRAR_FACTURAS_KO:
				JOptionPane.showMessageDialog(this, "No se pudieron cargar las facturas.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;
			}
		});
	}
}
