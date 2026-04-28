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
            ((JFrame) vista).setVisible(true);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);
            ((JFrame) vista).setVisible(true);
        });

        btnActualizar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.VENTANA_BUSCAR_ID_EMPLEADO);
            ((JFrame) vista).setVisible(true);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_EMPLEADO);
            ((JFrame) vista).setVisible(true);
        });

        btnListar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_EMPLEADOS);
            ((JFrame) vista).setVisible(true);
        });
    }
}
