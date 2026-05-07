package presentacion.GUICliente;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaAnadirCliente extends JDialog implements IGUI {

	// ATRIBUTOS
	private JTextField txtNombre, txtApellido, txtDNI, txtTelefono;
	private JButton btnAceptar, btnCancelar;

	// CONSTRUCTORA

	public VistaAnadirCliente() {
		super(null, "Alta Cliente", ModalityType.APPLICATION_MODAL);
		setTitle("Alta Cliente");

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		initGUI();
	}

	// MÉTODOS.

	// Método para limpiar los campos de texto.
	private void limpiarCampos() {
		txtNombre.setText("");
		txtApellido.setText("");
		txtDNI.setText("");
		txtTelefono.setText("");
	}
	
	private void mostrarError(String msj, JTextField campo) {
        JOptionPane.showMessageDialog(VistaAnadirCliente.this, msj, "Faltan datos", JOptionPane.WARNING_MESSAGE);
        campo.requestFocus();
    }

	// MÉTODO INITGUI.
	private void initGUI() {
		// Creo panel de vista principal.
		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Inicializo atributos.
		txtDNI = new JTextField(20);
		txtNombre = new JTextField(20);
		txtApellido = new JTextField(20);
		txtTelefono = new JTextField(20);

		// Creo el panel de botones.
		JPanel pBotones = new JPanel();
		btnAceptar = new JButton("ACEPTAR");
		btnCancelar = new JButton("CANCELAR");
		pBotones.add(btnAceptar);
		pBotones.add(btnCancelar);

		// Listener del botón aceptar.
		btnAceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					// Validación previa de los campos.
					if (txtNombre.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error: El nombre es un campo obligatorio.", "Faltan datos",
								JOptionPane.WARNING_MESSAGE);
						txtNombre.requestFocus();
						return;
					}
					if (txtApellido.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error: Los apellidos son un campo obligatorio.",
								"Faltan datos", JOptionPane.WARNING_MESSAGE);
						txtApellido.requestFocus();
						return;
					}
					if (txtDNI.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error: El DNI es un campo obligatorio.", "Faltan datos",
								JOptionPane.WARNING_MESSAGE);
						txtDNI.requestFocus();
						return;
					}
					if (!esFormatoDNIValido(txtDNI.getText().trim())) {
					    mostrarError("DNI inválido. Formato: 8 dígitos + 1 mayúscula", txtDNI);
					    return;
					}
					if (txtTelefono.getText().trim().isEmpty()) {
						JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error: El teléfono es un campo obligatorio.",
								"Faltan datos", JOptionPane.WARNING_MESSAGE);
						txtTelefono.requestFocus();
						return;
					}
					if (!esFormatoTelefonoValido(txtTelefono.getText().trim())) {
					    mostrarError("Teléfono inválido. Debe tener 9 dígitos", txtTelefono);
					    return;
					}

					int tfno;
					try {
						
						long telLong = Long.parseLong(txtTelefono.getText());

						if (telLong <= 0) {
							mostrarError("El teléfono debe ser un número positivo.", txtTelefono);
							return;
						}

						if (telLong > Integer.MAX_VALUE) {
							mostrarError("El número de teléfono es demasiado largo.", txtTelefono);
							return;
						}

						tfno = (int) telLong;
					} catch (NumberFormatException ex) {
						mostrarError("El teléfono debe contener solo números (máximo 9 dígitos).", txtTelefono);
						return;
					}

					// Crear Transfer y enviar
					TCliente tCliente = new TCliente();
					tCliente.setNombre(txtNombre.getText().trim());
					tCliente.setApellidos(txtApellido.getText().trim());
					tCliente.setDNI(txtDNI.getText().trim());
					tCliente.setTelefono(tfno);
					tCliente.setActivo(true);

					Controlador.getInstance().accion(Eventos.ALTA_CLIENTE, tCliente);

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error inesperado: " + ex.getMessage());
				}
			}
		});

		// Listener del botón cancelar.
		btnCancelar.addActionListener(list -> {
			// Limpiar campos
			limpiarCampos();
			// Cerrar la ventana
			setVisible(false);
			dispose();
		});

		// Panel de datos: lo creamos con GridBagLayout para alinear etiquetas y
		// campos trabajando con el ajuste de GridBagConstraints.
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints ajuste = new GridBagConstraints();
		ajuste.fill = GridBagConstraints.HORIZONTAL;
		ajuste.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

		// Fila 0: Nombre
		ajuste.gridx = 0;
		ajuste.gridy = 0;
		formPanel.add(new JLabel("Nombre:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtNombre, ajuste);

		// Fila 1: Apellido
		ajuste.gridx = 0;
		ajuste.gridy = 1;
		formPanel.add(new JLabel("Apellidos:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtApellido, ajuste);

		// Fila 2: DNI
		ajuste.gridx = 0;
		ajuste.gridy = 2;
		formPanel.add(new JLabel("DNI:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtDNI, ajuste);

		// Fila 3: Teléfono
		ajuste.gridx = 0;
		ajuste.gridy = 3;
		formPanel.add(new JLabel("Teléfono:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtTelefono, ajuste);

		// Label de título.
		JLabel lblTitulo = new JLabel("Introduzca los datos del nuevo Cliente:");
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Añadir componentes.
		viewPanel.add(lblTitulo);
		viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		viewPanel.add(formPanel);
		viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		viewPanel.add(pBotones);

		getContentPane().add(viewPanel);
		pack();
		setResizable(false); // Recomendado para que no se desajuste al redimensionar
		setLocationRelativeTo(null); // Centrar en pantalla
	}

	// MÉTODO DE IGUI

	// Datos es el id del cliente
	@Override
	public void actualizar(int evento, Object datos) {
		// El controlador llama a este método tras la ejecución en el SA
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				switch (evento) {

				case Eventos.RES_ALTA_CLIENTE_OK:
					VistaAnadirCliente.this.limpiarCampos(); // Limpia los campos para el siguiente alta
					JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Éxito: Cliente creado con ID: " + datos);
					break;

				case Eventos.RES_ALTA_CLIENTE_YA_EXISTE:
					// El SA ya nos confirmó que nombre y apellido coinciden
					JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Ya existe un cliente con ese DNI en el sistema.",
							"Aviso", JOptionPane.WARNING_MESSAGE);
					VistaAnadirCliente.this.txtDNI.requestFocus();
					break;

				case Eventos.RES_ALTA_CLIENTE_KO:
					JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error en el sistema de persistencia.",
							"Error Grave", JOptionPane.ERROR_MESSAGE);
					break;

				default:
					JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error no identificado.");
					break;
				}
			}
		});
	}

	// Métodos auxiliares:
	
	private boolean esFormatoDNIValido(String dni) {
		String regex_dni = "^[0-9]{8}[A-Z]$";
		return dni.matches(regex_dni);
	}

	private boolean esFormatoTelefonoValido(String telefono) {
		String regex_telefono = "^[0-9]{9}$";
		return telefono.matches(regex_telefono);

	}
	
	// reset

	@Override
	public void setVisible(boolean b) {
		if (b)
			limpiarCampos();
		super.setVisible(b);
	}
}
