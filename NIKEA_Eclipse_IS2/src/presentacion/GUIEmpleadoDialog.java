package presentacion;

import javax.swing.*;
import java.awt.*;

import presentacion.controlador.Controlador;
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
    	
    	JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Botones
        JButton btnAlta        = new JButton("Alta Empleado");
        JButton btnBaja        = new JButton("Baja Empleado");
        JButton btnActualizar  = new JButton("Modificar Empleado");
        JButton btnBuscar      = new JButton("Buscar Empleado");
        JButton btnListar      = new JButton("Listar Empleados");
        JButton btnVincular    = new JButton("Vincular montador-montaje");
        JButton btnDesvincular = new JButton("Desvincular montador-montaje");

        JButton[] botones = {
            btnAlta, btnBaja, btnActualizar,
            btnBuscar, btnListar, btnVincular,
            btnDesvincular
        };

        Dimension size = new Dimension(220, 40);
        for (JButton b : botones) {
            b.setFocusPainted(false);
            b.setPreferredSize(size);
            b.setMaximumSize(size);
            b.setMinimumSize(size);
        }

        // FILA 1 (3 botones)
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila1.add(btnAlta);
        fila1.add(btnBaja);
        fila1.add(btnActualizar);

        // FILA 2 (3 botones)
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila2.add(btnBuscar);
        fila2.add(btnListar);
        fila2.add(btnVincular);

        // FILA 3 (centrado)
        JPanel fila3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        fila3.add(btnDesvincular);

        mainPanel.add(fila1);
        mainPanel.add(fila2);
        mainPanel.add(fila3);

        add(mainPanel, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        
        // Listeners
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_EMPLEADO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_EMPLEADO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnActualizar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.VENTANA_BUSCAR_ID_EMPLEADO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_EMPLEADO);
            abrirVistaBloqueante((JDialog) vista);
        });

        btnListar.addActionListener(e -> { 
        	Controlador.getInstance().accion(Eventos.MOSTRAR_EMPLEADOS, null);
        });
        
        btnVincular.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.VINCULAR_MONTADOR_MONTAJE);
            abrirVistaBloqueante((JDialog) vista);
        });
        
        btnDesvincular.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.DESVINCULAR_MONTADOR_MONTAJE);
            abrirVistaBloqueante((JDialog) vista);
        });
    }

    private void abrirVistaBloqueante(JDialog vista) {
    	vista.setModal(true);
    	vista.setVisible(true);
    }
}