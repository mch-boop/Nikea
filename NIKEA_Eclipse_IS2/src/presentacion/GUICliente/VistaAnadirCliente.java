package presentacion.GUICliente;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

public class VistaAnadirCliente extends JFrame implements IGUI {

	// ATRIBUTOS	
	private JTextField txtNombre, txtApellido, txtDNI, txtTelefono;
	private JButton btnAceptar, btnCancelar; 	
	
	// CONSTRUCTORA 
	
	public VistaAnadirCliente() {
		setTitle("Alta Cliente");
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
	
	// MÉTODO INITGUI.
	void initGUI() {
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
					//Forma el transfer con los datos introducidos en la vista: tCliente.
					TCliente tCliente = new TCliente();
					
					// Asignación de atributos leyendo directamente de los JTextField correspondientes.
	                tCliente.setNombre(txtNombre.getText());
	                tCliente.setApellidos(txtApellido.getText());
	                tCliente.setDNI(txtDNI.getText());
	                tCliente.setTelefono(Integer.valueOf(txtTelefono.getText()));
	                tCliente.setActivo(true); // Se da de alta siempre como activo
	
					//El Controlador también es singleton.
					Controlador.getInstance().accion(Eventos.ALTA_CLIENTE, tCliente);
				
					// Limpiar campos para poder dar de alta otro cliente.
                    limpiarCampos();
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Error: El teléfono debe ser un número válido.", "Número no admitido", JOptionPane.ERROR_MESSAGE);
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
		
		// --- CÓDIGO AÑADIDO PARA ALINEACIÓN ---
        // Creamos un panel con GridBagLayout para alinear etiquetas y campos
		// trabajando con el ajuste de GridBagConstraints.
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints ajuste = new GridBagConstraints();
        ajuste.fill = GridBagConstraints.HORIZONTAL;
        ajuste.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Fila 0: Nombre
        ajuste.gridx = 0; ajuste.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtNombre, ajuste);

        // Fila 1: Apellido
        ajuste.gridx = 0; ajuste.gridy = 1;
        formPanel.add(new JLabel("Apellidos:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtApellido, ajuste);

        // Fila 2: DNI
        ajuste.gridx = 0; ajuste.gridy = 2;
        formPanel.add(new JLabel("DNI:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtDNI, ajuste);

        // Fila 3: Teléfono
        ajuste.gridx = 0; ajuste.gridy = 3;
        formPanel.add(new JLabel("Teléfono:"), ajuste);
        ajuste.gridx = 1;
        formPanel.add(txtTelefono, ajuste);

        getContentPane().add(viewPanel);
        pack();
        setResizable(false); // Recomendado para que no se desajuste al redimensionar
        setLocationRelativeTo(null); // Centrar en pantalla
	}
	
	
	// MÉTODO DE IGUI
	
	// Datos es el id del cliente.
	@Override public void actualizar(int evento, Object datos) {	
		// A este método lo llamará el controlador para actualizar la GUI

		if (evento == Eventos.RES_ALTA_CLIENTE_OK)
			JOptionPane.showMessageDialog(this, "Se ha creado correctamente el cliente con id " + (Integer) datos + ".");

		else if(evento == Eventos.RES_ALTA_CLIENTE_KO)
			JOptionPane.showMessageDialog(this, "No se ha podido crear el cliente.");
	}
	
}
