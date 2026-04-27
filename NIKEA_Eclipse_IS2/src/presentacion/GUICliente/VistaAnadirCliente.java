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
					JOptionPane.showMessageDialog(null, "El teléfono debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
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
        
        // Label de título.
        JLabel lblTitulo = new JLabel("Introduzca el ID del Cliente a dar de alta:");
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
	@Override public void actualizar(int evento, Object datos) {
        // El controlador llama a este método tras la ejecución en el SA
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            switch (evento) {

	                case Eventos.RES_ALTA_CLIENTE_OK:
	                	VistaAnadirCliente.this.limpiarCampos(); // Limpia los campos para el siguiente alta
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Éxito: Cliente creado con ID: " + datos); 
	                    break;
	        
	                case Eventos.RES_ALTA_CLIENTE_YA_EXISTE_MISMO:
	                    // El SA ya nos confirmó que nombre y apellido coinciden
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, 
	                        "Este cliente ya existe en el sistema.", 
	                        "Aviso", JOptionPane.WARNING_MESSAGE);
	                    VistaAnadirCliente.this.txtDNI.requestFocus();
	                    break;

	                case Eventos.RES_ALTA_CLIENTE_YA_EXISTE_DISTINTO:
	                    // El SA nos confirmó que el DNI es de otra persona
	                    TCliente dup = (TCliente) datos;
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, 
	                        "El DNI introducido ya pertenece a: " + dup.getNombre() + " " + dup.getApellidos(), 
	                        "Conflicto de Identidad", JOptionPane.ERROR_MESSAGE);
	                    VistaAnadirCliente.this.txtDNI.requestFocus();
	                    break;
	                    
	                case Eventos.RES_ALTA_CLIENTE_CONFIRMAR_REACTIVACION:
	                    // El cliente existe inactivo con datos distintos (Caso -2)
	                    TCliente cliReac = (TCliente) datos;
	                    
	                    int respReac = JOptionPane.showConfirmDialog(VistaAnadirCliente.this, 
	                        "Existe un cliente inactivo con DNI " + cliReac.getDNI() + ".\n" +
	                        "¿Desea reactivarlo y actualizarlo con los nuevos datos introducidos?", 
	                        "Confirmar Reactivación y Modificación", 
	                        JOptionPane.YES_NO_OPTION, 
	                        JOptionPane.QUESTION_MESSAGE);
	                    
	                    if (respReac == JOptionPane.YES_OPTION) {
	                        // Al darle a SÍ, enviamos el Transfer con los datos nuevos al controlador
	                        Controlador.getInstance().accion(Eventos.REACTIVAR_CLIENTE, cliReac);
	                        VistaAnadirCliente.this.setVisible(false);
	                        VistaAnadirCliente.this.dispose();
	                    }
	                    break;
	                    
	                case Eventos.RES_ALTA_CLIENTE_KO:
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error en el sistema de persistencia.", "Error Grave", JOptionPane.ERROR_MESSAGE);
	                    break;
	        
	                case Eventos.RES_ALTA_CLIENTE_KO_DNI:
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "El DNI introducido no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    txtDNI.requestFocus();
	                    break;
	        
	                case Eventos.RES_ALTA_CLIENTE_KO_NOMBRE:
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "El nombre es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    txtNombre.requestFocus();
	                    break;
	        
	                case Eventos.RES_ALTA_CLIENTE_KO_APELLIDO:
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "El apellido es obligatorio para evitar duplicados.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    txtApellido.requestFocus();
	                    break;
	        
	                case Eventos.RES_ALTA_CLIENTE_KO_TELEFONO:
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "El teléfono debe ser un número válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    break;
	        
	                default:
	                    JOptionPane.showMessageDialog(VistaAnadirCliente.this, "Error no identificado.");
	                    break;
	            }
	        }
	    });
	}

	
}
