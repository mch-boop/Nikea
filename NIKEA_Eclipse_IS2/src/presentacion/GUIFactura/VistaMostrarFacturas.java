package presentacion.GUIFactura;

import java.awt.BorderLayout;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import negocio.factura.TFactura;
import presentacion.IGUI;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaMostrarFacturas extends JFrame implements IGUI {

	// ATRIBUTOS
	private JTable tabla;
	private DefaultTableModel modelo;
	private JButton btnCerrar;

	// CONSTRUCTOR
	public VistaMostrarFacturas() {
		setTitle("Mostrar Facturas");
		initGUI();
	}

	private void initGUI() {

		setLayout(new BorderLayout());

		// MODELO TABLA
		modelo = new DefaultTableModel(new Object[] { "ID Factura", "ID Cliente", "ID Vendedor", "Fecha" }, 0);

		tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla);

		// BOTONES
		JPanel panelBotones = new JPanel();
		btnCerrar = new JButton("CERRAR");

		panelBotones.add(btnCerrar);

		// ACCIÓN CERRAR
		btnCerrar.addActionListener(e -> {
			setVisible(false);
			dispose();
		});

		add(scroll, BorderLayout.CENTER);
		add(panelBotones, BorderLayout.SOUTH);

		setSize(600, 300);
		setLocationRelativeTo(null);
	}

	// CARGAR DATOS
	private void cargarTabla(List<TFactura> facturas) {
		modelo.setRowCount(0);

		if (facturas == null || facturas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No existen facturas registradas.",
                "Información",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		for (TFactura f : facturas) {
			String fechaFormateada = sdf.format(f.getFecha());
			
			modelo.addRow(new Object[] { f.getId(), f.getIdCliente(), f.getIdVendedor(), fechaFormateada });
		}
	}

	// IGUI
	@SuppressWarnings("unchecked")
	@Override
	public void actualizar(int evento, Object datos) {

		javax.swing.SwingUtilities.invokeLater(() -> {

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