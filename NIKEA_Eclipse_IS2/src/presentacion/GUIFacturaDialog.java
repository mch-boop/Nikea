package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
            ((JFrame) vista).setVisible(true);
        });

        btnAnnadir.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ANNADIR_SERVICIO);
            ((JFrame) vista).setVisible(true);
        });

        btnCerrar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.CERRAR_VENTA);
            ((JFrame) vista).setVisible(true);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_FACTURA);
            ((JFrame) vista).setVisible(true);
        });

        btnListar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_FACTURAS);
            ((JFrame) vista).setVisible(true);
        });
        
        btnListarCliente.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_FACTURAS_CLIENTE);
            ((JFrame) vista).setVisible(true);
        });
    }
}
