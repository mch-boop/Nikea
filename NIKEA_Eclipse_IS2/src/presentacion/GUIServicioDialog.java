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
        initGUI();
        pack();
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }
    
    // MÉTODOS
    
    private void initGUI() {
    	
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- SERVICIO GENERAL ---
        JPanel Servicios = new JPanel(new GridLayout(1, 4, 10, 10));
        Servicios.setBorder(BorderFactory.createTitledBorder("Servicios"));

        JButton btnAlta = new JButton("Alta Servicio");
        JButton btnBaja = new JButton("Baja Servicio");
        JButton btnMostrar = new JButton("Mostrar Todos");
        JButton btnBuscar = new JButton("Buscar Servicio");

        Servicios.add(btnAlta);
        Servicios.add(btnBaja);
        Servicios.add(btnMostrar);
        Servicios.add(btnBuscar);

        // --- CONCRETOS ---
        JPanel Abajo = new JPanel(new GridLayout(1, 2, 10, 10));

        // Producto
        JPanel Producto = new JPanel(new GridLayout(1, 2, 10, 10));
        Producto.setBorder(BorderFactory.createTitledBorder("Producto"));
        
        JButton btnMejor = new JButton("Mejor Articulo");
        JButton btnModificar = new JButton("Modificar Articulo");
        
        Producto.add(btnMejor);
        Producto.add(btnModificar);

        // Montaje
        JPanel Montaje = new JPanel(new GridLayout(1, 1, 10, 10));
        Montaje.setBorder(BorderFactory.createTitledBorder("Montaje"));
        
        JButton btnOrganizar = new JButton("Organizar Montaje");
        Montaje.add(btnOrganizar);

        Abajo.add(Producto);
        Abajo.add(Montaje);

        JButton[] todosLosBotones = {btnAlta, btnBaja, btnMostrar, btnBuscar, btnMejor, btnModificar, btnOrganizar};
        for (JButton b : todosLosBotones) {
            b.setFocusPainted(false);
        }

        // Unir
        mainPanel.add(Servicios);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(Abajo);

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
        
        btnOrganizar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ORGANIZAR_MONTAJE);
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