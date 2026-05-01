package presentacion;

import javax.swing.*;
import java.awt.*;
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
    
        JPanel mainPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnAlta = new JButton("Alta Servicio");
        JButton btnBaja = new JButton("Baja Servicio");
        JButton btnMostrar = new JButton("Mostrar Todos");
        JButton btnBuscar = new JButton("Buscar Servicio");
        JButton btnMejor = new JButton("Mejor Articulo");
        JButton btnModificar = new JButton("Modificar Servicio");

        JButton[] todosLosBotones = {btnAlta, btnBaja, btnMostrar, btnBuscar, btnMejor, btnModificar};
        for (JButton b : todosLosBotones) {
            b.setFocusPainted(false);
            mainPanel.add(b);
        }

        add(mainPanel, BorderLayout.CENTER);

        // Listeners
        
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_SERVICIO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_SERVICIO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnMostrar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_SERVICIOS);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_SERVICIO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnMejor.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_MEJOR_ARTICULO);
            abrirVistaBloqueante((JFrame) vista);
        });
        
        btnModificar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_SERVICIO);
            abrirVistaBloqueante((JFrame) vista);
        });
        
    }

    private void abrirVistaBloqueante(Window ventanaSecundaria) {
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