package presentacion.GUIDescuento;

import java.awt.*;
import javax.swing.*;

import negocio.descuento.TDescuento;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarDescuento extends JDialog implements IGUI {

	// === ATRIBUTOS ===
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

	private int idActual = -1;
	// Guardamos el objeto original para los placeholders y comparación
	private TDescuento descuentoOriginal;

	// === CONSTRUCTORA ===
	public VistaModificarDescuento() {
		super(null, "Modificar Descuento", ModalityType.APPLICATION_MODAL);
		setTitle("Modificar Descuento");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	// === MÉTODOS DE PLACEHOLDER ===
	public void configurarPlaceholder(JTextField textField, String texto) {
		textField.setText(texto);
		textField.setForeground(Color.GRAY);

		textField.addFocusListener(new java.awt.event.FocusAdapter() {
			@Override
			public void focusGained(java.awt.event.FocusEvent e) {
				if (textField.getText().equals(texto)) {
					textField.setText("");
					textField.setForeground(Color.BLACK);
				}
			}

			@Override
			public void focusLost(java.awt.event.FocusEvent e) {
				if (textField.getText().isEmpty()) {
					textField.setText(texto);
					textField.setForeground(Color.GRAY);
				}
			}
		});
	}

	// === INICIALIZACIÓN ===
	private void initGUI() {
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout) {
			@Override
			public Dimension getPreferredSize() {
				for (Component comp : getComponents()) {
					if (comp.isVisible()) {
						return comp.getPreferredSize();
					}
				}
				return super.getPreferredSize();
			}
		};

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

	private JPanel buildPanelFormulario() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Campos
		txtCodigo = new JTextField(20);
		txtDescuento = new JTextField(20);

		areaDescripcion = new JTextArea(3, 20);
		areaDescripcion.setLineWrap(true);
		areaDescripcion.setWrapStyleWord(true);
		JScrollPane scrollDesc = new JScrollPane(areaDescripcion);

		// Spinners
		SpinnerNumberModel importeModel = new SpinnerNumberModel(100.0, 0.0, 1000000.0, 10.0);
		importeMin = new JSpinner(importeModel);
		JSpinner.NumberEditor editor = new JSpinner.NumberEditor(importeMin, "0.00");
		importeMin.setEditor(editor);
		editor.getTextField().setColumns(10);

		SpinnerNumberModel productosModel = new SpinnerNumberModel(10, 0, 10000, 1);
		productosMin = new JSpinner(productosModel);
		((JSpinner.NumberEditor) productosMin.getEditor()).getTextField().setColumns(10);

		panelDinamico = new JPanel(new CardLayout());
		JPanel cardImporte = new JPanel();
		cardImporte.add(new JLabel("Importe mínimo (€):"));
		cardImporte.add(importeMin);
		JPanel cardProductos = new JPanel();
		cardProductos.add(new JLabel("Productos mínimos (uds):"));
		cardProductos.add(productosMin);
		panelDinamico.add(cardImporte, "IMPORTE");
		panelDinamico.add(cardProductos, "PRODUCTOS");

		rbImporte = new JRadioButton("Por importe", true);
		rbProductos = new JRadioButton("Por cantidad");
		ButtonGroup group = new ButtonGroup();
		group.add(rbImporte);
		group.add(rbProductos);

		CardLayout cl = (CardLayout) panelDinamico.getLayout();
		rbImporte.addActionListener(e -> cl.show(panelDinamico, "IMPORTE"));
		rbProductos.addActionListener(e -> cl.show(panelDinamico, "PRODUCTOS"));

		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;

		gbc.gridx = 0; gbc.gridy = 0; formPanel.add(new JLabel("Código:"), gbc);
		gbc.gridx = 1; formPanel.add(txtCodigo, gbc);
		gbc.gridx = 0; gbc.gridy = 1; formPanel.add(new JLabel("Descripción:"), gbc);
		gbc.gridx = 1; formPanel.add(scrollDesc, gbc);
		gbc.gridx = 0; gbc.gridy = 2; formPanel.add(new JLabel("Descuento (%):"), gbc);
		gbc.gridx = 1; formPanel.add(txtDescuento, gbc);

		JPanel panelRadio = new JPanel();
		panelRadio.add(rbImporte); panelRadio.add(rbProductos);
		gbc.gridx = 0; gbc.gridy = 3; formPanel.add(new JLabel("Tipo:"), gbc);
		gbc.gridx = 1; formPanel.add(panelRadio, gbc);
		gbc.gridx = 0; gbc.gridy = 4; formPanel.add(new JLabel("Condición:"), gbc);
		gbc.gridx = 1; formPanel.add(panelDinamico, gbc);

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
				// Lógica de Placeholder: si el texto es el placeholder o vacío, se usa el original
				String finalCodigo = (txtCodigo.getText().equals(descuentoOriginal.getCodigo()) || txtCodigo.getText().isEmpty()) 
						? descuentoOriginal.getCodigo() : txtCodigo.getText().trim();
				
				String finalDescuentoStr = (txtDescuento.getText().equals(String.valueOf(descuentoOriginal.getPorcentaje())) || txtDescuento.getText().isEmpty())
						? String.valueOf(descuentoOriginal.getPorcentaje()) : txtDescuento.getText().trim();
				
				String finalNombre = areaDescripcion.getText().trim().isEmpty() ? descuentoOriginal.getNombre() : areaDescripcion.getText().trim();

				boolean esImporte = rbImporte.isSelected();
				double finalCondicion = esImporte ? (Double) importeMin.getValue() : (Integer) productosMin.getValue();

				// Ventana de Confirmación con el resumen de datos
				String resumen = String.format(
					"¿Desea confirmar los siguientes cambios?\n\n" +
					"ID: %d\n" +
					"Código: %s\n" +
					"Descripción: %s\n" +
					"Descuento: %s%%\n" +
					"Tipo: %s\n" +
					"Condición: %.2f",
					idActual, finalCodigo, finalNombre, finalDescuentoStr, 
					(esImporte ? "Importe Mínimo" : "Cantidad Mínima"), finalCondicion
				);

				int confirm = JOptionPane.showConfirmDialog(this, resumen, "Confirmar Modificación", JOptionPane.YES_NO_OPTION);
				
				if (confirm == JOptionPane.YES_OPTION) {
					TDescuento td = new TDescuento(esImporte);
					td.setId(idActual);
					td.setCodigo(finalCodigo);
					td.setNombre(finalNombre);
					td.setPorcentaje(Integer.parseInt(finalDescuentoStr));
					td.setActivo(true);

					if (esImporte) td.setImporteMin((Double) importeMin.getValue());
					else td.setProductosMin((Integer) productosMin.getValue());

					Controlador.getInstance().accion(Eventos.MODIFICAR_DESCUENTO, td);
				}

			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "El descuento debe ser un número entero.", "Error de formato", JOptionPane.ERROR_MESSAGE);
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

	// === CARGAR DESCUENTO ===
	private void cargarDescuento(TDescuento td) {
		this.descuentoOriginal = td;
		idActual = td.getId();

		// Configuramos placeholders con los datos actuales
		configurarPlaceholder(txtCodigo, td.getCodigo());
		configurarPlaceholder(txtDescuento, String.valueOf(td.getPorcentaje()));
		
		areaDescripcion.setText(""); // Se deja vacío para que escriba si quiere, si no se mantiene el nombre en el guardado
		areaDescripcion.setToolTipText("Actual: " + td.getNombre());

		CardLayout cl = (CardLayout) panelDinamico.getLayout();
		if (td.isTipo()) {
			rbImporte.setSelected(true);
			importeMin.setValue(td.getImporteMin());
			cl.show(panelDinamico, "IMPORTE");
		} else {
			rbProductos.setSelected(true);
			productosMin.setValue(td.getProductosMin());
			cl.show(panelDinamico, "PRODUCTOS");
		}

		cardLayout.show(cardPanel, CARD_FORMULARIO);
		pack();
		setLocationRelativeTo(null);
	}

	@Override
	public void actualizar(int evento, Object datos) {
		SwingUtilities.invokeLater(() -> {
			switch (evento) {
			case Eventos.RES_CARGAR_DESCUENTO_MOD_OK:
				cargarDescuento((TDescuento) datos);
				break;
			case Eventos.RES_CARGAR_DESCUENTO_MOD_KO:
				JOptionPane.showMessageDialog(this, "No se encontró ningún descuento activo con ese ID.");
				break;
			case Eventos.RES_MODIFICAR_DESCUENTO_OK:
				JOptionPane.showMessageDialog(this, "Modificado con éxito.");
				limpiarCampos();
				break;
			case Eventos.RES_MODIFICAR_DESCUENTO_KO:
				JOptionPane.showMessageDialog(this, "Error al guardar los cambios.");
				break;
			}
		});
	}

	@Override
	public void setVisible(boolean b) {
		if (b) limpiarCampos();
		super.setVisible(b);
	}

	private void limpiarCampos() {
		txtIdBuscar.setText("");
		idActual = -1;
		descuentoOriginal = null;
		cardLayout.show(cardPanel, CARD_BUSQUEDA);
		pack();
		setLocationRelativeTo(null);
	}
}