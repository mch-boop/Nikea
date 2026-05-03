package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.GUIServicio.VistaMostrarMejorArticulo;
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
        initGUI();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
			abrirVistaBloqueante((JDialog) vista);
		});

		btnBaja.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnModificar.addActionListener(e -> { 
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnBuscar.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_SERVICIO);
			abrirVistaBloqueante((JDialog) vista);
		});

		btnMostrar.addActionListener(e -> {
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_SERVICIOS);
			if (vista instanceof VistaMostrarServicios) {
				((VistaMostrarServicios) vista).cargarServicios();
			}
			abrirVistaBloqueante((JDialog) vista);
		});

		btnMejor.addActionListener(e -> { 
			IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_MEJOR_ARTICULO);
			if (vista instanceof VistaMostrarMejorArticulo) {
				((VistaMostrarMejorArticulo) vista).cargarMejorArticulo();
			}
			abrirVistaBloqueante((JDialog) vista);
		});
	}

	private void abrirVistaBloqueante(JDialog vista) {
    	vista.setModal(true);
    	vista.setVisible(true);
    }
}