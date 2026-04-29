package presentacion.GUIDescuento;

import java.awt.*;
import javax.swing.*;
import negocio.descuento.TDescuento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarDescuento extends JFrame implements IGUI {

	private JTextField txtIdBuscar;
	private JButton btnBuscar, btnCancelarBusqueda;

	private JTextField txtCodigo, txtDescuento;
	private JTextArea areaDescripcion;
	private JSpinner importeMin, productosMin;
	private JRadioButton rbImporte, rbProductos;
	private JButton btnGuardar, btnCancelarForm;
	private JPanel panelDinamico;

	private JPanel cardPanel;
	private CardLayout cardLayout;
	private static final String CARD_BUSQUEDA = "busqueda";
	private static final String CARD_FORMULARIO = "formulario";

	// ID del descuento que se está editando
	private int idActual = -1;

	public VistaModificarDescuento() {
		setTitle("Modificar Descuento");
		initGUI();
	}

	private void initGUI() {
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);

		cardPanel.add(buildPanelBusqueda(), CARD_BUSQUEDA);
		cardPanel.add(buildPanelFormulario(), CARD_FORMULARIO);

		add(cardPanel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private JPanel buildPanelBusqueda() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JLabel lblInfo = new JLabel("Introduce el ID del descuento a modificar:");
		lblInfo.setAlignmentX(CENTER_ALIGNMENT);

		txtIdBuscar = new JTextField(10);
		txtIdBuscar.setMaximumSize(new Dimension(200, 30));
		txtIdBuscar.setAlignmentX(CENTER_ALIGNMENT);

		btnBuscar = new JButton("CARGAR");
		btnCancelarBusqueda = new JButton("CANCELAR");

		btnCancelarBusqueda.addActionListener(e -> {
			txtIdBuscar.setText("");
			dispose();
		});

		btnBuscar.addActionListener(e -> {
			String idStr = txtIdBuscar.getText().trim();
			if (idStr.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Por favor, introduce un ID válido.", "Aviso",
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			try {
				int id = Integer.parseInt(idStr);
				Controlador.getInstance().accion(Eventos.CARGAR_DESCUENTO_MODIFICAR, id);
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.", "Error de formato",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		botones.add(btnBuscar);
		botones.add(btnCancelarBusqueda);

		panel.add(lblInfo);
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		panel.add(txtIdBuscar);
		panel.add(Box.createRigidArea(new Dimension(0, 15)));
		panel.add(botones);

		return panel;
	}

	// Edicion
	private JPanel buildPanelFormulario() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Campos
		txtCodigo = new JTextField(20);

		areaDescripcion = new JTextArea(3, 20);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		JScrollPane scrollDesc = new JScrollPane(areaDescripcion);

		txtDescuento = new JTextField(20);

		// Spinners
		SpinnerNumberModel importeModel = new SpinnerNumberModel(100.0, 0.0, null, 10.0);
		importeMin = new JSpinner(importeModel);
		((JSpinner.NumberEditor) importeMin.getEditor()).getTextField().setColumns(10);

		SpinnerNumberModel productosModel = new SpinnerNumberModel(10, 0, null, 1);
		productosMin = new JSpinner(productosModel);
		((JSpinner.NumberEditor) productosMin.getEditor()).getTextField().setColumns(10);

		// Panel dinámico
		panelDinamico = new JPanel(new CardLayout());

		JPanel cardImporte = new JPanel();
		cardImporte.add(new JLabel("Importe mínimo (€):"));
		cardImporte.add(importeMin);

		JPanel cardProductos = new JPanel();
		cardProductos.add(new JLabel("Productos mínimos (uds):"));
		cardProductos.add(productosMin);

		panelDinamico.add(cardImporte, "IMPORTE");
		panelDinamico.add(cardProductos, "PRODUCTOS");

		// Radio buttons tipo
		rbImporte = new JRadioButton("Por importe", true);
		rbProductos = new JRadioButton("Por cantidad");
		ButtonGroup group = new ButtonGroup();
		group.add(rbImporte);
		group.add(rbProductos);

		CardLayout cl = (CardLayout) panelDinamico.getLayout();
		rbImporte.addActionListener(e -> cl.show(panelDinamico, "IMPORTE"));
		rbProductos.addActionListener(e -> cl.show(panelDinamico, "PRODUCTOS"));

		// Grid del formulario
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(new JLabel("Código:"), gbc);
		gbc.gridx = 1;
		formPanel.add(txtCodigo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		formPanel.add(new JLabel("Descripción:"), gbc);
		gbc.gridx = 1;
		formPanel.add(scrollDesc, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(new JLabel("Descuento (%):"), gbc);
		gbc.gridx = 1;
		formPanel.add(txtDescuento, gbc);

		JPanel panelRadio = new JPanel();
		panelRadio.add(rbImporte);
		panelRadio.add(rbProductos);
		gbc.gridx = 0;
		gbc.gridy = 3;
		formPanel.add(new JLabel("Tipo:"), gbc);
		gbc.gridx = 1;
		formPanel.add(panelRadio, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		formPanel.add(new JLabel("Condición:"), gbc);
		gbc.gridx = 1;
		formPanel.add(panelDinamico, gbc);

		// Botones
		btnGuardar = new JButton("GUARDAR");
		btnCancelarForm = new JButton("CANCELAR");

		btnCancelarForm.addActionListener(e -> {
			idActual = -1;
			txtIdBuscar.setText("");
			cardLayout.show(cardPanel, CARD_BUSQUEDA);
			pack();
		});

		btnGuardar.addActionListener(e -> {
			try {
				if (txtCodigo.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "El código es obligatorio.", "Faltan datos",
							JOptionPane.WARNING_MESSAGE);
					txtCodigo.requestFocusInWindow();
					return;
				}
				if (txtDescuento.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Debe indicar un porcentaje.", "Faltan datos",
							JOptionPane.WARNING_MESSAGE);
					txtDescuento.requestFocusInWindow();
					return;
				}

				boolean esImporte = rbImporte.isSelected();
				TDescuento td = new TDescuento(esImporte);
				td.setId(idActual);
				td.setCodigo(txtCodigo.getText().trim());
				td.setNombre(areaDescripcion.getText().trim());
				td.setPorcentaje(Integer.parseInt(txtDescuento.getText().trim()));
				td.setActivo(true); // Sigue activo tras modificar

				if (esImporte) {
					td.setImporteMin((Double) importeMin.getValue());
				} else {
					td.setProductosMin((Integer) productosMin.getValue());
				}

				Controlador.getInstance().accion(Eventos.MODIFICAR_DESCUENTO, td);

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "El descuento debe ser un número entero (ej: 15).",
						"Error de formato", JOptionPane.ERROR_MESSAGE);
				txtDescuento.requestFocusInWindow();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage());
			}
		});

		JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelarForm);

		mainPanel.add(formPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(panelBotones);

		return mainPanel;
	}

	private void cargarDescuento(TDescuento td) {
		idActual = td.getId();
		txtCodigo.setText(td.getCodigo());
		areaDescripcion.setText(td.getNombre());
		txtDescuento.setText(String.valueOf(td.getPorcentaje()));

		CardLayout cl = (CardLayout) panelDinamico.getLayout();
		if (td.isTipo()) { // true = por importe
			rbImporte.setSelected(true);
			importeMin.setValue(td.getCantidad());
			cl.show(panelDinamico, "IMPORTE");
		} else { // false = por productos
			rbProductos.setSelected(true);
			productosMin.setValue((int) td.getCantidad());
			cl.show(panelDinamico, "PRODUCTOS");
		}

		cardLayout.show(cardPanel, CARD_FORMULARIO);
		pack();
	}

	@Override
	public void actualizar(int evento, Object datos) {
		SwingUtilities.invokeLater(() -> {
			switch (evento) {

			case Eventos.RES_CARGAR_DESCUENTO_MOD_OK:
			    cargarDescuento((TDescuento) datos);
			    break;

			case Eventos.RES_CARGAR_DESCUENTO_MOD_KO:
			    JOptionPane.showMessageDialog(this,
			        "No se encontró ningún descuento activo con ese ID.",
			        "No encontrado", JOptionPane.WARNING_MESSAGE);
			    break;

			// Resultado del guardado
			case Eventos.RES_MODIFICAR_DESCUENTO_OK:
				JOptionPane.showMessageDialog(this, "Descuento modificado correctamente. ID: " + datos, "Éxito",
						JOptionPane.INFORMATION_MESSAGE);
				idActual = -1;
				txtIdBuscar.setText("");
				cardLayout.show(cardPanel, CARD_BUSQUEDA);
				pack();
				break;

			case Eventos.RES_MODIFICAR_DESCUENTO_NO_ENCONTRADO:
				JOptionPane.showMessageDialog(this, "No se encontró el descuento a modificar.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;

			case Eventos.RES_MODIFICAR_DESCUENTO_KO_CODIGO:
				JOptionPane.showMessageDialog(this, "El código no es válido o está vacío.", "Error",
						JOptionPane.ERROR_MESSAGE);
				txtCodigo.requestFocus();
				break;

			case Eventos.RES_MODIFICAR_DESCUENTO_KO_PORCENTAJE:
				JOptionPane.showMessageDialog(this, "El porcentaje debe estar entre 1 y 100.", "Error",
						JOptionPane.ERROR_MESSAGE);
				txtDescuento.requestFocus();
				break;

			case Eventos.RES_MODIFICAR_DESCUENTO_KO:
				JOptionPane.showMessageDialog(this, "Error al guardar los cambios.", "Error grave",
						JOptionPane.ERROR_MESSAGE);
				break;

			default:
				break;
			}
		});
	}
}