package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacion.GUIServicio.VistaMostrarServicios;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIServicioDialog extends JDialog {
	
	// CONSTRUCTORA

	public GUIServicioDialog(JFrame owner) {
		super(owner, "Gestión de Servicios", false);
		setResizable(false); 
		setSize(800, 150); 
		setLocationRelativeTo(owner);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		initGUI();
	}
	
	// MÉTODOS
	
	private void initGUI() {
		
		JPanel panel = new JPanel(new GridLayout(2, 3, 10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		JButton btnAlta      = new JButton("Alta Servicio");
		JButton btnBaja      = new JButton("Baja Servicio");
		JButton btnModificar = new JButton("Modificar Servicio");
		JButton btnBuscar    = new JButton("Buscar Servicio");
		JButton btnMostrar   = new JButton("Listar Servicios");
		JButton btnMejor     = new JButton("Mejor Artículo");

		JButton[] botones = { btnAlta, btnBaja, btnModificar, btnBuscar, btnMostrar, btnMejor };
		
		for (JButton b : botones) {
			b.setFocusPainted(false);
			panel.add(b);
		}

		add(panel, BorderLayout.CENTER);

		// Listeners de los botones
		btnAlta.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_SERVICIO);
			abrirVistaBloqueante((JFrame) vista);
		});

		btnBaja.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_SERVICIO);
			abrirVistaBloqueante((JFrame) vista);
		});

		btnModificar.addActionListener(e -> { 
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_SERVICIO);
			abrirVistaBloqueante((JFrame) vista);
		});

		btnBuscar.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_SERVICIO);
			abrirVistaBloqueante((JFrame) vista);
		});

		btnMostrar.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_SERVICIOS);
			if (vista instanceof VistaMostrarServicios) {
				((VistaMostrarServicios) vista).cargarServicios();
			}
			abrirVistaBloqueante((JFrame) vista);
		});

		btnMejor.addActionListener(e -> { 
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_MEJOR_ARTICULO);
			abrirVistaBloqueante((JFrame) vista);
		});
	}

	private void abrirVistaBloqueante(Window ventanaSecundaria) {
		if (ventanaSecundaria == null) {
			JOptionPane.showMessageDialog(this, "No se pudo abrir la ventana solicitada.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		this.setEnabled(false);

		ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent e) {
				GUIServicioDialog.this.setEnabled(true);
				GUIServicioDialog.this.toFront();
			}
			
			@Override
			public void windowClosing(java.awt.event.WindowEvent e) {
				GUIServicioDialog.this.setEnabled(true);
			}
		});

		ventanaSecundaria.setVisible(true);
	}
}