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
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	
	// MÉTODOS
	
	private void limpiarCampos() {
		txtNombre.setText("");
	    txtApellido.setText("");
	    txtDNI.setText("");

	    if (spSueldo != null) {
	        spSueldo.setValue(1200.0);
	    }

	    if (rbVendedor != null) {
	        rbVendedor.setSelected(true);
	    }

	    txtNombre.requestFocus();
	    repaint();
	    revalidate();
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
        SpinnerNumberModel sueldoModel = new SpinnerNumberModel(1200.0, 1200.0, null, 500.0);
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
            //dispose();
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
		SwingUtilities.invokeLater(new Runnable() {
	        @Override
	        public void run() {
	            switch (evento) {

	                case Eventos.RES_ALTA_EMPLEADO_OK:
	                	VistaAnadirEmpleado.this.limpiarCampos(); // Limpia los campos para el siguiente alta
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "Éxito: Empleado creado con ID: " + datos); 
	                    VistaAnadirEmpleado.this.setVisible(false);
	                    //VistaAnadirEmpleado.this.dispose();
	                    break;
	        
	                case Eventos.RES_ALTA_EMPLEADO_YA_EXISTE_MISMO:
	                    // El SA ya nos confirmó que nombre y apellido coinciden
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, 
	                        "Este empleado ya existe en el sistema.", 
	                        "Aviso", JOptionPane.WARNING_MESSAGE);
	                    VistaAnadirEmpleado.this.txtDNI.requestFocus();
	                    break;

	                case Eventos.RES_ALTA_EMPLEADO_YA_EXISTE_DISTINTO:
	                    // El SA nos confirmó que el DNI es de otra persona
	                    TEmpleado dup = (TEmpleado) datos;
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, 
	                        "El DNI introducido ya pertenece a: " + dup.getNombre() + " " + dup.getApellido(), 
	                        "Conflicto de Identidad", JOptionPane.ERROR_MESSAGE);
	                    VistaAnadirEmpleado.this.txtDNI.requestFocus();
	                    break;
	                    
	                case Eventos.RES_ALTA_EMPLEADO_CONFIRMAR_REACTIVACION:
	                    // El empleado existe inactivo con datos distintos (Caso -2)
	                    TEmpleado empReac = (TEmpleado) datos;
	                    
	                    int respReac = JOptionPane.showConfirmDialog(VistaAnadirEmpleado.this, 
	                        "Existe un empleado inactivo con DNI " + empReac.getDNI() + ".\n" +
	                        "¿Desea reactivarlo y actualizarlo con los nuevos datos introducidos?", 
	                        "Confirmar Reactivación y Modificación", 
	                        JOptionPane.YES_NO_OPTION, 
	                        JOptionPane.QUESTION_MESSAGE);
	                    
	                    if (respReac == JOptionPane.YES_OPTION) {
	                        // Al darle a SÍ, enviamos el Transfer con los datos nuevos al controlador
	                        Controlador.getInstance().accion(Eventos.REACTIVAR_EMPLEADO, empReac);
	                        VistaAnadirEmpleado.this.setVisible(false);
		                    //VistaAnadirEmpleado.this.dispose();
	                    }
	                    break;

	                case Eventos.RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_ACTIVO:
	                    // El empleado ya está trabajando pero con otro cargo
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, 
	                        "Este empleado ya figura en el sistema pero con un cargo distinto.\n" +
	                        "Para cambiar su tipo (ej. de Vendedor a Montador), use el botón 'Actualizar Empleado' del menú.", 
	                        "Empleado Activo - Cambio de Tipo", 
	                        JOptionPane.INFORMATION_MESSAGE);
	                    VistaAnadirEmpleado.this.dispose();
	                    break;

	                case Eventos.RES_ALTA_EMPLEADO_CAMBIO_TIPO_REQUERIDO_INACTIVO:
	                    // El empleado fue borrado con otro cargo
	                    TEmpleado inactivo = (TEmpleado) datos;
	                    String tipoOriginal = (inactivo.getTipo() == 1) ? "Vendedor" : "Montador";
	                    
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, 
	                        "El empleado existe en el histórico como inactivo con el cargo de: " + tipoOriginal + ".\n\n" +
	                        "PASOS A SEGUIR:\n" +
	                        "1. Vuelva a darle de alta seleccionando el tipo '" + tipoOriginal + "' para reactivarlo.\n" +
	                        "2. Una vez reactivado, use el botón 'Actualizar Empleado' para cambiar su cargo actual.", 
	                        "Reactivación Requerida", 
	                        JOptionPane.WARNING_MESSAGE);
	                    VistaAnadirEmpleado.this.setVisible(false);
	                    //VistaAnadirEmpleado.this.dispose();
	                    break;
	                    
	                case Eventos.RES_ALTA_EMPLEADO_KO:
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "Error en el sistema de persistencia.", "Error Grave", JOptionPane.ERROR_MESSAGE);
	                    break;
	        
	                case Eventos.RES_ALTA_EMPLEADO_KO_DNI:
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "El DNI introducido no es válido.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    txtDNI.requestFocus();
	                    break;
	        
	                case Eventos.RES_ALTA_EMPLEADO_KO_NOMBRE:
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "El nombre es obligatorio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    txtNombre.requestFocus();
	                    break;
	        
	                case Eventos.RES_ALTA_EMPLEADO_KO_APELLIDO:
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "El apellido es obligatorio para evitar duplicados.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    txtApellido.requestFocus();
	                    break;
	        
	                case Eventos.RES_ALTA_EMPLEADO_KO_SUELDO:
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "El sueldo debe ser un número positivo.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
	                    break;
	        
	                default:
	                    JOptionPane.showMessageDialog(VistaAnadirEmpleado.this, "Error no identificado.");
	                    break;
	            }
	        }
	    });
	}
}