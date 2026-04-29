package presentacion;

import javax.swing.*;
import java.awt.*;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIEmpleadoDialog extends JDialog {
	
	// CONSTRUCTORA

	public GUIEmpleadoDialog(JFrame owner) {
	    super(owner, "Gestión de Empleados", false);
	    setResizable(false); 
	    setSize(850, 120); 
	    setLocationRelativeTo(owner);
	    setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    
	    initGUI();
	}
    
    // MÉTODOS
    
    private void initGUI() {
    	
    	JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAlta       = new JButton("Alta Empleado");
        JButton btnBaja       = new JButton("Baja Empleado");
        JButton btnActualizar = new JButton("Modificar Empleado");
        JButton btnBuscar     = new JButton("Buscar Empleado");
        JButton btnListar     = new JButton("Listar Empleados");

        JButton[] botones = {btnAlta, btnBaja, btnActualizar, btnBuscar, btnListar};
        for (JButton b : botones) {
            b.setFocusPainted(false);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);

        // Listeners
        
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_EMPLEADO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnActualizar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.VENTANA_BUSCAR_ID_EMPLEADO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_EMPLEADO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnListar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_EMPLEADOS);
            abrirVistaBloqueante((JFrame) vista);
        });
    }

    private void abrirVistaBloqueante(Window ventanaSecundaria) {
        this.setEnabled(false);
        ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUIEmpleadoDialog.this.setEnabled(true);
                GUIEmpleadoDialog.this.toFront();
            }
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GUIEmpleadoDialog.this.setEnabled(true);
            }
        });
        ventanaSecundaria.setVisible(true);
    }
}