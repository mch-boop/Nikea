package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIFacturaDialog  extends JDialog  {

	//CONSTRUCTORA
	
	public GUIFacturaDialog(JFrame owner) {
        super(owner, "Gestión de Factura", false);
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
        
        JButton btnIniciar           = new JButton("Iniciar venta");
        JButton btnAnnadir           = new JButton("Añadir servicio");
        JButton btnCerrar            = new JButton("Cerrar venta");
        JButton btnBuscar            = new JButton("Buscar factura");
        JButton btnListar            = new JButton("Listar facturas");
        JButton btnListarCliente     = new JButton("Listar facturas de cliente");

        JButton[] botones = {btnIniciar, btnAnnadir, btnCerrar, btnBuscar, btnListar, btnListarCliente};
        for (JButton b : botones) {
            b.setFocusPainted(false);
            panel.add(b);
        }

        add(panel, BorderLayout.CENTER);
        
        // Listeners
        
        btnIniciar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.INICIAR_VENTA);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnAnnadir.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ANNADIR_SERVICIO);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnCerrar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.CERRAR_VENTA);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_FACTURA);
            abrirVistaBloqueante((JFrame) vista);
        });

        btnListar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_FACTURAS);
            abrirVistaBloqueante((JFrame) vista);
        });
        
        btnListarCliente.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_FACTURAS_CLIENTE);
            abrirVistaBloqueante((JFrame) vista);
        });
    }

    private void abrirVistaBloqueante(Window ventanaSecundaria) {
        this.setEnabled(false);
        ventanaSecundaria.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                GUIFacturaDialog.this.setEnabled(true);
                GUIFacturaDialog.this.toFront();
            }
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                GUIFacturaDialog.this.setEnabled(true);
            }
        });
        ventanaSecundaria.setVisible(true);
    }
}