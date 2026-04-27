package presentacion.GUIDescuento;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

	    // Campos
	    txtCodigo = new JTextField(20);
	    txtPorcentaje = new JTextField(20);
	    txtDescripcion = new JTextArea(3, 20);

	    // Spinners
	    SpinnerNumberModel importeModel = new SpinnerNumberModel(100.0, 0.0, null, 10.0);
	    importeMin = new JSpinner(importeModel);
	    importeMin.setPreferredSize(new Dimension(120, 25));

	    SpinnerNumberModel productosModel = new SpinnerNumberModel(10, 0, null, 1);
	    productosMin = new JSpinner(productosModel);
	    productosMin.setPreferredSize(new Dimension(120, 25));

	    // RadioButtons
	    rbImporte = new JRadioButton("Por importe", true);
	    rbProductos = new JRadioButton("Por número de productos");

	    ButtonGroup group = new ButtonGroup();
	    group.add(rbImporte);
	    group.add(rbProductos);

	    JPanel panelRadio = new JPanel();
	    panelRadio.add(new JLabel("Tipo de Descuento: "));
	    panelRadio.add(rbImporte);
	    panelRadio.add(rbProductos);

	    // Card Layout
	    JPanel panelDinamico = new JPanel(new CardLayout());

	    JPanel cardImporte = new JPanel();
	    cardImporte.add(new JLabel("Importe mínimo:"));
	    cardImporte.add(importeMin);

	    JPanel cardProductos = new JPanel();
	    cardProductos.add(new JLabel("Productos mínimos:"));
	    cardProductos.add(productosMin);

	    panelDinamico.add(cardImporte, "IMPORTE");
	    panelDinamico.add(cardProductos, "PRODUCTOS");

	    // Fijar tamaño
	    Dimension d1 = cardImporte.getPreferredSize();
	    Dimension d2 = cardProductos.getPreferredSize();
	    int width = Math.max(d1.width, d2.width);
	    int height = Math.max(d1.height, d2.height);

	    Dimension fixed = new Dimension(width, height);
	    panelDinamico.setPreferredSize(fixed);
	    panelDinamico.setMinimumSize(fixed);
	    panelDinamico.setMaximumSize(fixed);

	    // Listener radios
	    CardLayout cl = (CardLayout) panelDinamico.getLayout();

	    rbImporte.addActionListener(e -> cl.show(panelDinamico, "IMPORTE"));
	    rbProductos.addActionListener(e -> cl.show(panelDinamico, "PRODUCTOS"));

	    // ALINEACIÓN
	    JPanel formPanel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.fill = GridBagConstraints.HORIZONTAL;
	    gbc.insets = new Insets(5, 5, 5, 5);

	    // Código
	    gbc.gridx = 0; gbc.gridy = 0;
	    formPanel.add(new JLabel("Código:"), gbc);
	    gbc.gridx = 1;
	    formPanel.add(txtCodigo, gbc);

	    // Porcentaje
	    gbc.gridx = 0; gbc.gridy = 1;
	    formPanel.add(new JLabel("Porcentaje:"), gbc);
	    gbc.gridx = 1;
	    formPanel.add(txtPorcentaje, gbc);

	    // Descripción
	    gbc.gridx = 0; gbc.gridy = 2;
	    formPanel.add(new JLabel("Descripción:"), gbc);
	    gbc.gridx = 1;
	    formPanel.add(txtDescripcion, gbc);

	    // Condición (única fila)
	    gbc.gridx = 0; gbc.gridy = 3;
	    formPanel.add(new JLabel("Tipo:"), gbc);
	    gbc.gridx = 1;
	    formPanel.add(panelDinamico, gbc);

	    // Botones
	    JPanel panelBotones = new JPanel();
	    btnAceptar = new JButton("ACEPTAR");
	    btnCancelar = new JButton("CANCELAR");

	    panelBotones.add(btnAceptar);
	    panelBotones.add(btnCancelar);

	    // Acción aceptar
	    btnAceptar.addActionListener(e -> {
	        try {
	            if (txtCodigo.getText().trim().isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Código obligatorio");
	                return;
	            }

	            if (txtPorcentaje.getText().trim().isEmpty()) {
	                JOptionPane.showMessageDialog(null, "Porcentaje obligatorio");
	                return;
	            }

	            boolean esImporte = rbImporte.isSelected();
	            TDescuento td = new TDescuento(esImporte);

	            td.setCodigo(txtCodigo.getText());
	            td.setPorcentaje(txtPorcentaje.getText());
	            td.setDescripcion(txtDescripcion.getText());

	            if (esImporte) {
	                td.setImporteMin((Double) importeMin.getValue());
	            } else {
	                td.setProductosMin((Integer) productosMin.getValue());
	            }

	            Controlador.getInstance().accion(Eventos.ALTA_DESCUENTO, td);

	        } catch (Exception ex) {
	            JOptionPane.showMessageDialog(null, "Error en los datos");
	        }
	    });

	    // Añadir elementos
	    mainPanel.add(formPanel);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    mainPanel.add(panelRadio);
	    mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
	    mainPanel.add(panelBotones);

	    setContentPane(mainPanel);
	    pack();
	    setResizable(false);
	    setLocationRelativeTo(null);
	}
	
	@Override
	public void actualizar(int evento, Object datos) {
		// TODO Auto-generated method stub
		
	}

}
