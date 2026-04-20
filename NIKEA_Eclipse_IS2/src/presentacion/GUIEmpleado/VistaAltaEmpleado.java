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
import presentacion.controlador.Eventos;

public class VistaAltaEmpleado extends JFrame implements IGUI {
	
	// ATRIBUTOS
	
	private JTextField txtNombre, txtApellido, txtDNI, txtSueldo;
	private JRadioButton rbVendedor, rbMontador; // Selección de tipo
	private JButton btnAceptar, btnCancelar; 
	
	// CONSTRUCTORA
	
	public VistaAltaEmpleado() {
		setTitle("Alta Empleado"); 
		initGUI(); 
	}
	
	
	// MÉTODOS
	
	private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtDNI.setText("");
        txtSueldo.setText("");
        rbVendedor.setSelected(true);
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
        txtSueldo = new JTextField(20); 
        
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
                    te.setSueldo(Double.parseDouble(txtSueldo.getText()));
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
        
        // Añadir componentes al panel principal
        mainPanel.add(new JLabel("Nombre:")); 
        mainPanel.add(txtNombre);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espaciado
        
        mainPanel.add(new JLabel("Apellido:")); 
        mainPanel.add(txtApellido);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("DNI:")); 
        mainPanel.add(txtDNI);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(new JLabel("Sueldo Bruto:")); 
        mainPanel.add(txtSueldo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(panelRadio);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(panelBotones);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null); // Centrar en pantalla
	}
	
	@Override
    public void actualizar(int evento, Object datos) {
        // El controlador llama a este método tras la ejecución en el SA
		switch (evento) {

        case Eventos.RES_ALTA_EMPLEADO_OK:
            JOptionPane.showMessageDialog(this, "Empleado creado con ID: " + datos);
            limpiarCampos();
            break;

        case Eventos.RES_ALTA_EMPLEADO_YA_EXISTE:
            TEmpleado existente = (TEmpleado) datos; 
            String msgExiste = "Error: El DNI ya pertenece a: " + existente.getNombre() + " " + existente.getApellido() + 
                               "\nEstado: " + (existente.isActivo() ? "Activo" : "Inactivo");
            JOptionPane.showMessageDialog(this, msgExiste, "DNI Duplicado", JOptionPane.WARNING_MESSAGE);
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
            txtSueldo.requestFocus();
            break;

        default:
            JOptionPane.showMessageDialog(this, "Error no identificado.");
            break;
    }
    }
}