package presentacion.GUIServicio;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import negocio.servicio.TArticulo;
import negocio.servicio.TMontaje;
import negocio.servicio.TServicio;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarServicio extends JFrame implements IGUI {

	private JTextField txtId;
	private JTextField txtNombre;
	private JTextArea txtDescripcion;
	private JTextField txtStock;
	private JTextField txtPrecioActual;
	private JComboBox<String> comboTipo;

	private JTextField txtNombreAct;
	private JTextArea txtDescripcionAct;
	private JTextField txtStockAct;
	private JTextField txtPrecioActualAct;
	private JTextField txtTipoAct;

	private JButton btnBuscar;
	private JButton btnModificar;
	private JButton btnCancelar;
	private JButton btnCancelarModif;

	private JPanel panelEdicion;
	private JPanel pBotones;

	private TServicio servicioEncontrado;

	public VistaModificarServicio() {
		setTitle("Modificar Servicio");
		initGUI();
	}

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JPanel pBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
		txtId = new JTextField(20);
		btnBuscar = new JButton("BUSCAR");
		btnCancelar = new JButton("CANCELAR");

		pBusqueda.add(new JLabel("ID Servicio a modificar:"));
		pBusqueda.add(txtId);
		pBusqueda.add(btnBuscar);
		pBusqueda.add(btnCancelar);

		btnBuscar.addActionListener(e -> {
			try {
				if (txtId.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "El ID es obligatorio para identificar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int id = Integer.parseInt(txtId.getText().trim());
				pBotones.setVisible(false);
				Controlador.getInstance().accion(Eventos.BUSCAR_SERVICIO_PARA_MODIFICAR, id);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Asegúrese de que el ID sea un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCancelar.addActionListener(e -> {
			limpiarTodo();
			setVisible(false);
			dispose();
		});

		JLabel lblTitulo = new JLabel("Introduzca el ID del Servicio a modificar:");
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		pBotones = new JPanel();
		pBotones.add(btnBuscar);
		pBotones.add(btnCancelar);

		crearPanelEdicion();

		mainPanel.add(lblTitulo);
		mainPanel.add(pBusqueda);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(pBotones);
		mainPanel.add(panelEdicion);

		getContentPane().add(mainPanel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void crearPanelEdicion() {
		panelEdicion = new JPanel();
		panelEdicion.setLayout(new BoxLayout(panelEdicion, BoxLayout.Y_AXIS));
		panelEdicion.setBorder(BorderFactory.createTitledBorder("Información del Servicio"));
		panelEdicion.setVisible(false);

		JPanel panelDatos = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		txtNombre = new JTextField(20);
		txtDescripcion = new JTextArea(4, 20);
		txtDescripcion.setLineWrap(true);
		txtDescripcion.setWrapStyleWord(true);
		txtStock = new JTextField(20);
		txtPrecioActual = new JTextField(20);
		comboTipo = new JComboBox<>(new String[] {"Artículo", "Montaje"});

		txtNombre.setToolTipText("Deje este campo vacío para conservar el nombre actual");
		txtDescripcion.setToolTipText("Deje este campo vacío para conservar la descripción actual");
		txtStock.setToolTipText("Deje este campo vacío para conservar el stock actual");
		txtPrecioActual.setToolTipText("Deje este campo vacío para conservar el precio actual");

		txtNombreAct = new JTextField(20);
		txtNombreAct.setEditable(false);
		txtDescripcionAct = new JTextArea(4, 20);
		txtDescripcionAct.setEditable(false);
		txtDescripcionAct.setLineWrap(true);
		txtDescripcionAct.setWrapStyleWord(true);
		txtStockAct = new JTextField(20);
		txtStockAct.setEditable(false);
		txtPrecioActualAct = new JTextField(20);
		txtPrecioActualAct.setEditable(false);
		txtTipoAct = new JTextField(20);
		txtTipoAct.setEditable(false);

		gbc.gridy = 0;
		gbc.gridx = 1;
		panelDatos.add(new JLabel("Dato actual"), gbc);
		gbc.gridx = 2;
		panelDatos.add(new JLabel("Dato nuevo"), gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		panelDatos.add(new JLabel("Nombre:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(txtNombreAct, gbc);
		gbc.gridx = 2;
		panelDatos.add(txtNombre, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		panelDatos.add(new JLabel("Descripción:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(new JScrollPane(txtDescripcionAct), gbc);
		gbc.gridx = 2;
		panelDatos.add(new JScrollPane(txtDescripcion), gbc);

		gbc.gridy = 3;
		gbc.gridx = 0;
		panelDatos.add(new JLabel("Stock:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(txtStockAct, gbc);
		gbc.gridx = 2;
		panelDatos.add(txtStock, gbc);

		gbc.gridy = 4;
		gbc.gridx = 0;
		panelDatos.add(new JLabel("Precio actual:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(txtPrecioActualAct, gbc);
		gbc.gridx = 2;
		panelDatos.add(txtPrecioActual, gbc);

		gbc.gridy = 5;
		gbc.gridx = 0;
		panelDatos.add(new JLabel("Tipo:"), gbc);
		gbc.gridx = 1;
		panelDatos.add(txtTipoAct, gbc);
		gbc.gridx = 2;
		panelDatos.add(comboTipo, gbc);

		JPanel panelBotones = new JPanel();
		btnModificar = new JButton("GUARDAR CAMBIOS");
		btnCancelarModif = new JButton("CANCELAR");

		btnModificar.addActionListener(e -> {
			try {
				if (servicioEncontrado == null) {
					return;
				}

				TServicio datosNuevos = comboTipo.getSelectedIndex() == 0 ? new TArticulo() : new TMontaje();
				datosNuevos.setId(servicioEncontrado.getId());
				datosNuevos.setTipo(comboTipo.getSelectedIndex() == 0 ? 1 : 2);

				datosNuevos.setNombre(txtNombre.getText().trim().isEmpty() ? null : txtNombre.getText().trim());
				datosNuevos.setDescripcion(txtDescripcion.getText().trim().isEmpty() ? null : txtDescripcion.getText().trim());

				if (txtStock.getText().trim().isEmpty()) {
					datosNuevos.setStock(null);
				} else {
					datosNuevos.setStock(Integer.parseInt(txtStock.getText().trim()));
				}

				if (txtPrecioActual.getText().trim().isEmpty()) {
					datosNuevos.setPrecioActual(null);
				} else {
					datosNuevos.setPrecioActual(Integer.parseInt(txtPrecioActual.getText().trim()));
				}

				Controlador.getInstance().accion(Eventos.MODIFICAR_SERVICIO, datosNuevos);
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "El stock y el precio deben ser números válidos.", "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		btnCancelarModif.addActionListener(e -> {
			txtId.setEditable(true);
			txtId.setText("");
			limpiarCamposEdicion();
			panelEdicion.setVisible(false);
			pBotones.setVisible(true);
			pack();
		});

		panelBotones.add(btnModificar);
		panelBotones.add(btnCancelarModif);

		panelEdicion.add(panelDatos);
		panelEdicion.add(panelBotones);
	}

	@Override
	public void actualizar(int evento, Object datos) {
		SwingUtilities.invokeLater(() -> {
			switch (evento) {
				case Eventos.RES_BUSCAR_SERVICIO_PARA_MODIFICAR_OK:
					servicioEncontrado = (TServicio) datos;
					limpiarCamposEdicion();

					txtNombreAct.setText(safe(servicioEncontrado.getNombre()));
					txtDescripcionAct.setText(safe(servicioEncontrado.getDescripcion()));
					txtStockAct.setText(servicioEncontrado.getStock() != null ? String.valueOf(servicioEncontrado.getStock()) : "");
					txtPrecioActualAct.setText(servicioEncontrado.getPrecioActual() != null ? String.valueOf(servicioEncontrado.getPrecioActual()) : "");
					txtTipoAct.setText(tipoToTexto(servicioEncontrado.getTipo()));

					comboTipo.setSelectedIndex(servicioEncontrado.getTipo() != null && servicioEncontrado.getTipo() == 2 ? 1 : 0);

					txtId.setText(String.valueOf(servicioEncontrado.getId()));
					txtId.setEditable(false);

					panelEdicion.setVisible(true);
					pBotones.setVisible(false);
					pack();
					setLocationRelativeTo(null);
					setVisible(true);
					break;

				case Eventos.RES_BUSCAR_SERVICIO_PARA_MODIFICAR_KO:
					JOptionPane.showMessageDialog(this, "Error: No se encontró ningún servicio con el ID especificado.", "Error", JOptionPane.ERROR_MESSAGE);
					txtId.requestFocus();
					break;

				case Eventos.RES_MODIFICAR_SERVICIO_OK:
					JOptionPane.showMessageDialog(this, "Servicio modificado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					txtId.setEditable(true);
					txtId.setText("");
					limpiarTodo();
					panelEdicion.setVisible(false);
					pBotones.setVisible(true);
					pack();
					break;

				case Eventos.RES_MODIFICAR_SERVICIO_KO:
					JOptionPane.showMessageDialog(this, "No se ha podido modificar el servicio.", "Error", JOptionPane.ERROR_MESSAGE);
					break;

				default:
					break;
			}
		});
	}

	private void limpiarCamposEdicion() {
		txtNombre.setText("");
		txtDescripcion.setText("");
		txtStock.setText("");
		txtPrecioActual.setText("");
		comboTipo.setSelectedIndex(0);

		txtNombreAct.setText("");
		txtDescripcionAct.setText("");
		txtStockAct.setText("");
		txtPrecioActualAct.setText("");
		txtTipoAct.setText("");
	}

	private void limpiarTodo() {
		servicioEncontrado = null;
		limpiarCamposEdicion();
	}

	private String safe(String valor) {
		return valor == null ? "" : valor;
	}

	private String tipoToTexto(Integer tipo) {
		if (tipo == null) {
			return "";
		}

		return tipo == 1 ? "Artículo" : "Montaje";
	}
}
