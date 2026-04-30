package presentacion.GUIFactura;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.factura.TFactura;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner.DateEditor;
import java.util.Date;
import java.util.Calendar;

@SuppressWarnings("serial")
public class VistaCerrarVenta extends JFrame implements IGUI {

	// ATRIBUTOS
	private JTextField txtIdCliente, txtIdDescuento;
	private JButton btnAceptar, btnCancelar;
	private JSpinner spinnerDia, spinnerMes, spinnerAnyo;

	// CONSTRUCTORA
	public VistaCerrarVenta() {
		setTitle("Cerrar Venta");
		initGUI();
	}

	// Limpia los campos
	private void limpiarCampos() {
		txtIdCliente.setText("");
		txtIdDescuento.setText("");
	}

	// INIT GUI
	private void initGUI() {

		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Campos
		txtIdCliente = new JTextField(20);
		txtIdDescuento = new JTextField(20);

		// Panel botones
		JPanel pBotones = new JPanel();
		btnAceptar = new JButton("ACEPTAR");
		btnCancelar = new JButton("CANCELAR");

		pBotones.add(btnAceptar);
		pBotones.add(btnCancelar);

		//Spinners
		int anioActual = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		spinnerAnyo = new JSpinner(new SpinnerNumberModel(anioActual, 1900, anioActual, 1));
		
		int diaActual = java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH);
		spinnerDia = new JSpinner(new SpinnerNumberModel(diaActual, 1, 31, 1));

		int mesActual = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
		spinnerMes = new JSpinner(new SpinnerNumberModel(mesActual+1, 1, 12, 1));

		
		
		// Acción aceptar
		btnAceptar.addActionListener(e -> {
			try {
				TFactura tFactura = new TFactura();

				tFactura.setIdCliente(Integer.parseInt(txtIdCliente.getText()));
				tFactura.setIdDescuento(Integer.parseInt(txtIdDescuento.getText()));
				
				int dia = (Integer) spinnerDia.getValue();
				int mes = (Integer) spinnerMes.getValue();
				int anio = (Integer) spinnerAnyo.getValue();

				// Formato string
				String fecha = String.format("%02d/%02d/%04d", dia, mes, anio);

				tFactura.setFecha(fecha);

				Controlador.getInstance().accion(Eventos.CERRAR_VENTA, tFactura);

				limpiarCampos();

			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(null, "Los IDs deben ser números válidos.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		// Acción cancelar
		btnCancelar.addActionListener(e -> {
			limpiarCampos();
			setVisible(false);
			dispose();
		});

		// Panel formulario
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints ajuste = new GridBagConstraints();
		ajuste.fill = GridBagConstraints.HORIZONTAL;
		ajuste.insets = new Insets(5, 5, 5, 5);

		// ID Cliente
		ajuste.gridx = 0;
		ajuste.gridy = 0;
		formPanel.add(new JLabel("ID Cliente:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtIdCliente, ajuste);

		// ID Vendedor
		ajuste.gridx = 0;
		ajuste.gridy = 1;
		formPanel.add(new JLabel("Descuento %:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtIdDescuento, ajuste);

		// Fecha
		ajuste.gridx = 0;
		ajuste.gridy = 2;
		formPanel.add(new JLabel("Fecha:"), ajuste);

		ajuste.gridx = 1;

		JPanel panelFecha = new JPanel();
		panelFecha.add(spinnerDia);
		panelFecha.add(new JLabel("/"));
		panelFecha.add(spinnerMes);
		panelFecha.add(new JLabel("/"));
		panelFecha.add(spinnerAnyo);

		formPanel.add(panelFecha, ajuste);

		// Título
		JLabel lblTitulo = new JLabel("Introduzca los datos para cerrar la venta:");
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Añadir componentes
		viewPanel.add(lblTitulo);
		viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		viewPanel.add(formPanel);
		viewPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		viewPanel.add(pBotones);

		getContentPane().add(viewPanel);
		pack();
		setResizable(false);
		setLocationRelativeTo(null);
	}

	// IGUI
	@Override
	public void actualizar(int evento, Object datos) {

		if (evento == Eventos.RES_CERRAR_VENTA_OK) {
			JOptionPane.showMessageDialog(this, "Venta cerrada correctamente con ID: " + (Integer) datos);
		} else if (evento == Eventos.RES_CERRAR_VENTA_KO) {
			JOptionPane.showMessageDialog(this, "No se ha podido cerrar la venta.");
		}
	}
}