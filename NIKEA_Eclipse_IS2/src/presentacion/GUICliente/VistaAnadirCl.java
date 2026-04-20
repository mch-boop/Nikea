package presentacion.GUICliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import negocio.cliente.TCliente;
import presentacion.IGUI;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;

public class VistaAnadirCl extends JFrame implements IGUI {

	// ATRIBUTOS
	
	private JLabel lDNI;
	private JTextField tDNI;
	private JLabel lNombre;
	private JTextField tNombre;
	//...
	private JButton aceptar;
	//....
	
	
	// CONSTRUCTORA 
	
	public VistaAnadirCl() {
		setTitle("Alta Cliente");
		JPanel panel = new JPanel();
		lDNI = new JLabel("DNI:");
		tDNI = new JTextField(20);
		//...
		aceptar = new JButton("ACEPTAR");
		
		aceptar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				String DNIT = tDNI.getText();
				String nombre = tNombre.getText();
				//...
				//Forma el transfer con los datos introducidos
				//en la vista: tCliente.
				TCliente tCliente = null;
				//El Controlador también es singleton.
	
				Controlador.getInstance().accion(Eventos.ALTA_CLIENTE, tCliente);
	
				//...
			}
		});
	}
	
	
	// MÉTODO DE IGUI
	
	@Override
	public void actualizar(int evento, Object datos) {
		// A este método lo llamará el controlador para actualizar la GUI

		if (evento == Eventos.RES_ALTA_CLIENTE_OK)
			JOptionPane.showMessageDialog(this, "Se ha creado correctamente el cliente con id " + (Integer) datos);

		else if(evento == Eventos.RES_ALTA_CLIENTE_KO)
			JOptionPane.showMessageDialog(this, "No se ha podido crear el cliente");
	}
	
}
