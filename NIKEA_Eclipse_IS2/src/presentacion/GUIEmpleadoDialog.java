package presentacion;

import javax.swing.*;
import java.awt.*;
import presentacion.controlador.Controlador;
import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

public class GUIEmpleadoDialog extends JDialog {

    public GUIEmpleadoDialog(JFrame owner) {
        super(owner, "Gestión de Empleados", false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
        
        initGUI();
    }
    
    private void initGUI() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton btnAlta       = new JButton("Alta Empleado");
        JButton btnBaja       = new JButton("Baja Empleado");
        JButton btnActualizar = new JButton("Actualizar Empleado");
        JButton btnBuscar     = new JButton("Buscar Empleado");
        JButton btnListar     = new JButton("Listar Empleados");

        panel.add(btnAlta);
        panel.add(btnBaja);
        panel.add(btnActualizar);
        panel.add(btnBuscar);
        panel.add(btnListar);

        add(panel);

        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_EMPLEADO);
            ((JFrame) vista).setVisible(true);
        });

        btnBaja.addActionListener(e -> {
        	IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);
            ((JFrame) vista).setVisible(true);
        });

        btnActualizar.addActionListener(e -> {
        	IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_EMPLEADO);
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
