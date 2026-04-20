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
            String idStr = JOptionPane.showInputDialog(this, "ID del empleado a dar de baja:");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    Controlador.getInstance().accion(Eventos.BAJA_EMPLEADO, id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID numérico obligatorio.");
                }
            }
        });

        btnActualizar.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Modificar empleado (en desarrollo)");
        });

        btnBuscar.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog(this, "ID del empleado:");
            if (idStr != null && !idStr.trim().isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr.trim());
                    Controlador.getInstance().accion(Eventos.BUSCAR_EMPLEADO, id);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID numérico obligatorio.");
                }
            }
        });

        btnListar.addActionListener(e -> {
            Controlador.getInstance().accion(Eventos.MOSTRAR_EMPLEADOS, null);
        });
    }
}
