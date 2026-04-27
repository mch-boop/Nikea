package presentacion.GUIDescuento;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import negocio.descuento.TDescuento;
import negocio.empleado.TEmpleado;
import negocio.empleado.TMontador;
import negocio.empleado.TVendedor;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

public class VistaAltaDescuento extends JFrame implements IGUI {

	// ATRIBUTOS
	
	private JTextField txtCodigo, txtPorcentaje;
	private JTextArea txtDescripcion;
	private JSpinner importeMin, productosMin;
	private JRadioButton rbImporte, rbProductos;
	private JButton btnAceptar, btnCancelar; 
	
	// CONSTRUCTORA
	
	public VistaAltaDescuento() {
		setTitle("Alta descuento");
		initGUI();
		
	}


	// MÉTODOS
	
	private void limpiarCampos() {
		txtCodigo.setText("");
		txtPorcentaje.setText("");
		txtDescripcion.setText("");

	    if (importeMin != null) {
	        importeMin.setValue(100.0);
	    }

	    if (productosMin != null) {
	    	productosMin.setValue(10);
	    }
	    
	    if (rbImporte != null) {
	    	rbImporte.setSelected(true);
	    }
	    
	    if (rbProductos != null) {
	    	rbProductos.setSelected(false);
	    }

	    txtCodigo.requestFocus();
	    repaint();
	    revalidate();
    }
	
	
	private void initGUI() {

		// Panel principal
		JPanel mainPanel = new JPanel(); 
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Inicialización de campos
        txtCodigo = new JTextField(20);
		txtPorcentaje = new JTextField(20);
		txtDescripcion = new JTextArea(20, 20);
		
		// Configuración Spinners
		SpinnerNumberModel importeModel = new SpinnerNumberModel(100.0, 0.0, null, 25.0);
        importeMin = new JSpinner(importeModel);
        
        SpinnerNumberModel productosModel = new SpinnerNumberModel(10, 0, null, 1);
        productosMin = new JSpinner(productosModel);
		
        // Selección de tipo (Importe/Producto)
        rbImporte = new JRadioButton("Por importe", true);
        rbProductos = new JRadioButton("Por número de productos");
        ButtonGroup group = new ButtonGroup();
        group.add(rbImporte); 
        group.add(rbProductos);
        
        // Panel para los RadioButtons
        JPanel panelRadio = new JPanel();
        panelRadio.add(new JLabel("Tipo de Descuento: "));
        panelRadio.add(rbImporte);
        panelRadio.add(rbProductos);
        
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
                    if (txtCodigo.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El código es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtCodigo.requestFocus();
                        return;
                    }
                    if (txtPorcentaje.getText().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Error: El porcentaje es un campo obligatorio.", "Faltan datos", JOptionPane.WARNING_MESSAGE);
                        txtPorcentaje.requestFocus();
                        return;
                    }
                    
                    TDescuento td;
                    boolean importe = rbImporte.isSelected();
                    td = new TDescuento(importe);

                    // Asignación de atributos 
                    td.setCodigo(txtCodigo.getText());
                    td.setPorcentaje(txtPorcentaje.getText());
                    td.setDescripcion(txtDescripcion.getText());

                    // Comunicación con el Controlador (Singleton)
                    Controlador.getInstance().accion(Eventos.ALTA_DESCUENTO, td);
                    
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: El sueldo debe ser un número válido.");
                }
            }
        });
	}
	
	@Override
	public void actualizar(int evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}
