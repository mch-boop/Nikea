package presentacion.GUIEmpleado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import negocio.empleado.TEmpleado;
import negocio.empleado.TVendedor;
import negocio.empleado.TMontador;
import presentacion.IGUI;
import presentacion.controlador.Eventos;
import presentacion.controlador.Controlador;

@SuppressWarnings("serial")
public class VistaAnadirEmpleado extends JFrame implements IGUI {
	
	// ATRIBUTOS
	
	private JTextField txtNombre, txtApellido, txtDNI;
	private JSpinner spSueldo;
	private JRadioButton rbVendedor, rbMontador; // Selección de tipo
	private JButton btnAceptar, btnCancelar; 
	
	// CONSTRUCTORA
	
	public VistaAnadirEmpleado() {
		setTitle("Alta Empleado"); 
		initGUI(); 
	}
	
	
	// MÉTODOS
	
	private void limpiarCampos() {
		SwingUtilities.invokeLater(() -> {
	        txtNombre.setText("");
	        txtApellido.setText("");
	        txtDNI.setText("");

	        if (spSueldo != null) {
	            spSueldo.setValue(1200.0); 
	        }

	        if (rbVendedor != null) {
	            rbVendedor.setSelected(true);
	        }
	        
	        txtNombre.requestFocus(); // Ponemos el cursor en el primer campo
	    });
    }
	
	private void initGUI() {
		
		// Panel principal
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Inicialización de campos
        txtNombre = new JTextField(20); 
        txtApellido = new JTextField(20); 
        txtDNI = new JTextField(20); 
        
     // Configuración del Spinner para el sueldo (mínimo 1200, sin máximo, pasos de 500)
        SpinnerNumberModel sueldoModel = new SpinnerNumberModel(1200.0, 1000.0, null, 500.0);
        spSueldo = new JSpinner(sueldoModel);
        
        // Selección de tipo (Vendedor/Montador)
        rbVendedor = new JRadioButton("Vendedor", true);
        rbMontador = new JRadioButton("Montador");
        ButtonGroup group = new ButtonGroup();
        group.add(rbVendedor); 
        group.add(rbMontador);

        // Panel para los RadioButtons
        JPanel panelRadio = new JPanel();
        panelRadio.add(new JLabel("Tipo de Empleado: "));
        panelRadio.add(rbVendedor);
        panelRadio.add(rbMontador);

        // Panel de botones
        JPanel panelBotones = new JPanel();
        btnAceptar = new JButton("ACEPTAR");
        btnCancelar = new JButton("CANCELAR");
        panelBotones.add(btnAceptar);
        panelBotones.add(btnCancelar);
        
        // Lógica del botón Aceptar
        btnAceptar.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                try {
                	// Validación previa de campos obligatorios
                    if (txtNombre.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El nombre es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtNombre.requestFocus();
                        return;
                    }
                    if (txtDNI.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El DNI es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtDNI.requestFocus();
                        return;
                    }
                    TEmpleado te;
                    
                    // Decisión de instanciación del Transfer según el RadioButton
                    if (rbVendedor.isSelected()) {
                        te = new TVendedor();
                        te.setTipo(1); 
                    } else {
                        te = new TMontador();
                        te.setTipo(2); 
                    }

                    // Asignación de atributos 
                    te.setNombre(txtNombre.getText());
                    te.setApellido(txtApellido.getText());
                    te.setDNI(txtDNI.getText());
                    te.setSueldo((Double) spSueldo.getValue());
                    te.setActivo(true); // Se da de alta siempre como activo

                    // Comunicación con el Controlador (Singleton)
                    Controlador.getInstance().accion(Eventos.ALTA_EMPLEADO, te);
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El sueldo debe ser un número válido.");
                }
            }
        });
        
        // Lógica del botón Cancelar
        btnCancelar.addActionListener(al -> {
            // Limpiar campos
            limpiarCampos();
            // Cerrar la ventana
            setVisible(false);
            dispose();
        });
        
        // ALINEACIÓN 
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Espaciado entre componentes

        // Fila 0: Nombre
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtNombre, gbc);

        // Fila 1: Apellido
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtApellido, gbc);

        // Fila 2: DNI
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("DNI:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDNI, gbc);

        // Fila 3: Sueldo
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Sueldo Bruto:"), gbc);
        gbc.gridx = 1;
        formPanel.add(spSueldo, gbc);

        // Añadir el formulario alineado al panel principal
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(panelRadio);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setResizable(false); // Recomendado para que no se desajuste al redimensionar
        setLocationRelativeTo(null); // Centrar en pantalla
	}
	
	@Override
    public void actualizar(int evento, Object datos) {
        // El controlador llama a este método tras la ejecución en el SA
		switch (evento) {

	        case Eventos.RES_ALTA_EMPLEADO_OK:
	        	limpiarCampos(); // Limpia los campos para el siguiente alta
	            JOptionPane.showMessageDialog(this, "Éxito: Empleado creado con ID: " + datos); 
	            break;
	
	        case Eventos.RES_ALTA_EMPLEADO_YA_EXISTE:
	            TEmpleado existente = (TEmpleado) datos; 
	            String msgExiste = "Error: El DNI ya pertenece a: " + existente.getNombre() + " " + existente.getApellido() + 
	                               "\nEstado: " + (existente.isActivo() ? "Activo" : "Inactivo");
	            JOptionPane.showMessageDialog(this, msgExiste, "DNI Duplicado", JOptionPane.WARNING_MESSAGE);
	            break;
	            
	        case Eventos.RES_ALTA_EMPLEADO_KO:
	            JOptionPane.showMessageDialog(this, "Error en el sistema de persistencia (JSON).", "Error Grave", JOptionPane.ERROR_MESSAGE);
	            break;
	
	        case Eventos.RES_ALTA_EMPLEADO_KO_DNI:
	            JOptionPane.showMessageDialog(this, "El DNI introducido no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	            txtDNI.requestFocus();
	            break;
	
	        case Eventos.RES_ALTA_EMPLEADO_KO_NOMBRE:
	            JOptionPane.showMessageDialog(this, "El nombre es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	            txtNombre.requestFocus();
	            break;
	
	        case Eventos.RES_ALTA_EMPLEADO_KO_APELLIDO:
	            JOptionPane.showMessageDialog(this, "El apellido es obligatorio para evitar duplicados.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	            txtApellido.requestFocus();
	            break;
	
	        case Eventos.RES_ALTA_EMPLEADO_KO_SUELDO:
	            JOptionPane.showMessageDialog(this, "El sueldo debe ser un número positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	            break;
	
	        default:
	            JOptionPane.showMessageDialog(this, "Error no identificado.");
	            break;
		}
    }
}