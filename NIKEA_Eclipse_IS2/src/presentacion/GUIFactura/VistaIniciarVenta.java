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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import negocio.factura.TFactura;

@SuppressWarnings("serial")
public class VistaIniciarVenta extends JDialog implements IGUI {

	private JTextField txtIdVendedor;
	private JButton btnAceptar, btnCancelar;

	public VistaIniciarVenta() {
		setTitle("Iniciar Venta");
		initGUI();
	}

	private void limpiarCampos() {
		txtIdVendedor.setText("");
	}

	private void initGUI() {

		JPanel viewPanel = new JPanel();
		viewPanel.setLayout(new BoxLayout(viewPanel, BoxLayout.Y_AXIS));
		viewPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		txtIdVendedor = new JTextField(20);

		// BOTONES
		JPanel pBotones = new JPanel();
		btnAceptar = new JButton("ACEPTAR");
		btnCancelar = new JButton("CANCELAR");

		pBotones.add(btnAceptar);
		pBotones.add(btnCancelar);

		// ACEPTAR
		btnAceptar.addActionListener(e -> {

		    try {

		        int idVendedor = Integer.parseInt(txtIdVendedor.getText().trim());

		        TFactura tFactura = new TFactura();
		        tFactura.setIdVendedor(idVendedor);

		        Controlador.getInstance().accion(Eventos.INICIAR_VENTA, tFactura);

		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(this,
		            "El ID del vendedor debe ser numérico.",
		            "Error",
		            JOptionPane.ERROR_MESSAGE);
		    }
		});

		// CANCELAR
		btnCancelar.addActionListener(e -> {
			limpiarCampos();
			setVisible(false);
			dispose();
		});

		// FORMULARIO
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints ajuste = new GridBagConstraints();
		ajuste.fill = GridBagConstraints.HORIZONTAL;
		ajuste.insets = new Insets(5, 5, 5, 5);

		// ID Vendedor
		ajuste.gridx = 0;
		ajuste.gridy = 0;
		formPanel.add(new JLabel("ID Vendedor:"), ajuste);
		ajuste.gridx = 1;
		formPanel.add(txtIdVendedor, ajuste);

		// TÍTULO
		JLabel lblTitulo = new JLabel("Introduzca vendedor:");
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

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

	@Override
	public void actualizar(int evento, Object datos) {

		SwingUtilities.invokeLater(() -> {

			switch (evento) {

			case Eventos.RES_INICIAR_VENTA_OK:
				JOptionPane.showMessageDialog(this, "Venta iniciada correctamente" );
				limpiarCampos();
				break;

			case Eventos.RES_INICIAR_VENTA_KO:

				int error = (int) datos;
				String mensaje;

				switch (error) {

				case Eventos.RES_INICIAR_VENTA_KO_VENDEDOR_NO_EXISTE:
					mensaje = "El vendedor no existe.";
					break;

				case Eventos.RES_INICIAR_VENTA_KO_VENDEDOR_INACTIVO:
					mensaje = "El vendedor está inactivo.";
					break;
				case Eventos.RES_INICIAR_VENTA_KO_YA_EN_CURSO:
					mensaje = "Ya hay una venta en curso";
					break;
				default:
					mensaje = "Error al iniciar la venta.";
					break;
				}

				JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
				break;
			}
		});
	}
}
