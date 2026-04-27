package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TVendedor;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

@SuppressWarnings("serial")
public class VistaModificarEmpleado extends JFrame implements IGUI {

	// ATRIBUTOS

	private JTextField txtId, txtNombre, txtApellido, txtSueldo, txtVentas;
	private JLabel lblVentas;
	private JButton btnModificar, btnCancelar;
	private JComboBox<String> comboTipo;
	private final String[] tipos = {"Montador", "Vendedor"};
	private TEmpleado empleadoEncontrado; // Guardamos el original para las tarjetas
	private static TEmpleado datosNuevosParaConfirmar;

	// CONSTRUCTORA

	public VistaModificarEmpleado() {
		setTitle("Modificar Empleado");
		initGUI();
	}

	// MÉTODOS

	private void initGUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// El ID es obligatorio para saber a quién modificar
		txtId = new JTextField(20);

		// Campos opcionales
		txtNombre = new JTextField(20);
		txtApellido = new JTextField(20);
		txtSueldo = new JTextField(20);

		txtNombre.setToolTipText("Deje este campo vacío para conservar el nombre actual");
		txtApellido.setToolTipText("Deje este campo vacío para conservar el apellido actual");
		txtSueldo.setToolTipText("Deje este campo vacío para conservar el sueldo actual");

		lblVentas = new JLabel("Número de Ventas:");
		txtVentas = new JTextField(20);
		txtVentas.setToolTipText("Deje este campo vacío para conservar el número de ventas actual");

		// Por defecto no sabemos qué es el empleado, así que ocultamos
		lblVentas.setVisible(false);
		txtVentas.setVisible(false);
		
		comboTipo = new JComboBox<>(tipos);
		// Listener para mostrar/ocultar campos dinámicamente según el combo
		comboTipo.addActionListener(e -> {
		    boolean esVendedor = comboTipo.getSelectedIndex() == 1; // Vendedor es índice 1
		    lblVentas.setVisible(esVendedor);
		    txtVentas.setVisible(esVendedor);
		    this.pack();
		});

		btnModificar = new JButton("GUARDAR CAMBIOS");
		btnCancelar = new JButton("CANCELAR");

		btnModificar.addActionListener(e -> {
		    try {
		        // Validar que tenemos un empleado original cargado
		        if (empleadoEncontrado == null) return;

		        // Obtenemos el tipo del combo, no del empleado antiguo
		        int nuevoTipo = comboTipo.getSelectedIndex(); 
		        int valorTipoNegocio;
		        
		        // Instanciar el transfer correcto según el tipo seleccionado en el combo
		        if (nuevoTipo == 1) {
		        	valorTipoNegocio = 1; // Vendedor
		            datosNuevosParaConfirmar = new TVendedor();
		            // Casteamos para rellenar el campo específico de vendedor
		            int v = !txtVentas.getText().trim().isEmpty() ? Integer.parseInt(txtVentas.getText().trim()) : -1;
		            ((TVendedor) datosNuevosParaConfirmar).setNumeroVentas(v);
		        } else { // Montador
		        	valorTipoNegocio = 2; // Montador
		            datosNuevosParaConfirmar = new TMontador(); 
		        }

		        // RELLENAR DATOS COMUNES 
		        datosNuevosParaConfirmar.setId(empleadoEncontrado.getId());
		        datosNuevosParaConfirmar.setTipo(valorTipoNegocio); // Asignamos el nuevo tipo seleccionado
		        
		        datosNuevosParaConfirmar.setNombre(!txtNombre.getText().trim().isEmpty() ? txtNombre.getText().trim() : null);
		        datosNuevosParaConfirmar.setApellido(!txtApellido.getText().trim().isEmpty() ? txtApellido.getText().trim() : null);
		        
		        try {
		            double s = !txtSueldo.getText().trim().isEmpty() ? Double.parseDouble(txtSueldo.getText().trim()) : -1.0;
		            datosNuevosParaConfirmar.setSueldo(s);
		        } catch (NumberFormatException nfe) {
		            throw new Exception("El sueldo debe ser un número válido.");
		        }

		        // Abrir confirmación
		        GUIConfirmarModificar dialog = new GUIConfirmarModificar(this, empleadoEncontrado, datosNuevosParaConfirmar);
		        dialog.setVisible(true);

		        if (dialog.isConfirmado()) {
		            Controlador.getInstance().accion(Eventos.MODIFICAR_EMPLEADO, datosNuevosParaConfirmar);
		        }
		    } catch (Exception ex) {
		        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		    }
		});

		// Lógica de Cancelar
		btnCancelar.addActionListener(e -> {
			setVisible(false);
			dispose();
		});

		// ALINEACIÓN
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);

		// Fila 0: ID
		gbc.gridx = 0;
		gbc.gridy = 0;
		formPanel.add(new JLabel("ID Empleado (Obligatorio):"), gbc);
		gbc.gridx = 1;
		formPanel.add(txtId, gbc);

		// Fila 1: Separador (dentro del GridBag)
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 2;
		formPanel.add(new JSeparator(), gbc);
		gbc.gridwidth = 1; // Reset

		// Fila 2: Nombre
		gbc.gridx = 0;
		gbc.gridy = 2;
		formPanel.add(new JLabel("Nuevo Nombre:"), gbc);
		gbc.gridx = 1;
		formPanel.add(txtNombre, gbc);

		// Fila 3: Apellido
		gbc.gridx = 0;
		gbc.gridy = 3;
		formPanel.add(new JLabel("Nuevo Apellido:"), gbc);
		gbc.gridx = 1;
		formPanel.add(txtApellido, gbc);

		// Fila 4: Sueldo
		gbc.gridx = 0;
		gbc.gridy = 4;
		formPanel.add(new JLabel("Nuevo Sueldo:"), gbc);
		gbc.gridx = 1;
		formPanel.add(txtSueldo, gbc);

		// Fila 5: Número de ventas
		gbc.gridx = 0;
		gbc.gridy = 5;
		formPanel.add(lblVentas, gbc);
		gbc.gridx = 1;
		formPanel.add(txtVentas, gbc);
		
		// Fila 6: Tipo
		gbc.gridx = 0; gbc.gridy = -1; // Ponlo al principio
		formPanel.add(new JLabel("Cambiar Tipo:"), gbc);
		gbc.gridx = 1;
		formPanel.add(comboTipo, gbc);

		// Añadir componentes al panel principal
		mainPanel.add(formPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

		// Panel de botones
		JPanel pBotones = new JPanel();
		pBotones.add(btnModificar);
		pBotones.add(btnCancelar);
		mainPanel.add(pBotones);

		getContentPane().add(mainPanel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public void configurarPlaceholder(JTextField textField, String texto) {
	    // Configuramos el estado inicial
	    textField.setText(texto);
	    textField.setForeground(Color.GRAY);

	    textField.addFocusListener(new java.awt.event.FocusAdapter() {
	        @Override
	        public void focusGained(java.awt.event.FocusEvent e) {
	            // Si al entrar el texto es el del placeholder, lo limpiamos para escribir
	            if (textField.getText().equals(texto)) {
	                textField.setText("");
	                textField.setForeground(Color.BLACK);
	            }
	        }

	        @Override
	        public void focusLost(java.awt.event.FocusEvent e) {
	            // Si al salir el usuario no ha escrito nada, restauramos el placeholder
	            if (textField.getText().isEmpty()) {
	                textField.setText(texto);
	                textField.setForeground(Color.GRAY);
	            }
	        }
	    });
	}

	@Override
	public void actualizar(int evento, Object datos) {
		SwingUtilities.invokeLater(() -> {
			switch (evento) {
			// Recibimos los datos del empleado que queremos modificar
			case Eventos.RES_BUSCAR_EMPLEADO_PARA_MODIFICAR_OK:
                empleadoEncontrado = (TEmpleado) datos;
                
                // Rellenamos el ID y lo bloqueamos
                txtId.setText(String.valueOf(empleadoEncontrado.getId()));
                txtId.setEditable(false);
                
                // Traducimos el tipo de negocio al índice del JComboBox
                // Recordamos: Índice 0 = Montador, Índice 1 = Vendedor
                if (empleadoEncontrado.getTipo() == 1) { // Caso Vendedor
                    comboTipo.setSelectedIndex(1);
                    configurarPlaceholder(txtVentas, ((TVendedor)empleadoEncontrado).getNumeroVentas().toString()); 
                    lblVentas.setVisible(true);
                    txtVentas.setVisible(true);
                } 
                else if (empleadoEncontrado.getTipo() == 2) { // Caso Montador
                    comboTipo.setSelectedIndex(0);
                    lblVentas.setVisible(false);
                    txtVentas.setVisible(false);
                }
                
                configurarPlaceholder(txtNombre, empleadoEncontrado.getNombre());
                configurarPlaceholder(txtApellido, empleadoEncontrado.getApellido()); 
                configurarPlaceholder(txtSueldo, empleadoEncontrado.getSueldo().toString()); 

                // Mostramos la ventana y ajustamos tamaño
                this.pack();
                this.setLocationRelativeTo(null);
                this.setVisible(true);
                break;

			case Eventos.RES_MODIFICAR_EMPLEADO_OK:
				JOptionPane.showMessageDialog(this, "Empleado actualizado correctamente.");
				this.dispose(); // Cerramos al terminar
				break;

			case Eventos.RES_MODIFICAR_EMPLEADO_KO_NO_EXISTE:
				JOptionPane.showMessageDialog(this, "El empleado ha dejado de existir durante la operación.", "Error",
						JOptionPane.ERROR_MESSAGE);
				break;

			case Eventos.RES_MODIFICAR_EMPLEADO_KO_DATOS_INVALIDOS:
				JOptionPane.showMessageDialog(this, "Datos no válidos para la actualización.", "Error de validación",
						JOptionPane.WARNING_MESSAGE);
				break;
			}
		});
	}

	private void limpiarCampos() {
		txtId.setText("");
		txtNombre.setText("");
		txtApellido.setText("");
		txtSueldo.setText("");
	}
}