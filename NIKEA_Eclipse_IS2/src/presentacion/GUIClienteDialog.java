package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import presentacion.controlador.Eventos;
import presentacion.factoria.FactoriaAbstractaPresentacion;

@SuppressWarnings("serial")
public class GUIClienteDialog extends JDialog {
	
	// CONSTRUCTORA
	
	public GUIClienteDialog(JFrame owner) {
        super(owner, "Gestión de Cliente", false);
        // Evita que el usuario pueda cambiar el tamaño del diálogo
        setResizable(false); 
        setSize(600, 120); 
        setLocationRelativeTo(owner);
        
        initGUI();
	}
	
	// MÉTODO INITGUI
	
	private void initGUI() {
		// Creo botón principal, con dos filas (para organizar los botones centrados en cada fila).
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder());

        // Fila 1: 4 botones
        JPanel fila1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnAlta = new JButton("Alta Cliente");
        JButton btnBaja = new JButton("Baja Cliente");
        JButton btnModificar = new JButton("Modificar Cliente");
        JButton btnBuscar = new JButton("Buscar Cliente");
        
        fila1.add(btnAlta);
        fila1.add(btnBaja);
        fila1.add(btnModificar);
        fila1.add(btnBuscar);

        // Fila 2: 3 botones (centrados por uso de FlowLayout).
        JPanel fila2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnListar = new JButton("Listar Cliente");
        JButton btnMostrarMejor = new JButton("Mostrar Mejor Cliente");
        JButton btnMostrarFacturas = new JButton("Mostrar Facturas Cliente");
        
        fila2.add(btnListar);
        fila2.add(btnMostrarMejor);
        fila2.add(btnMostrarFacturas);

        // Añado las dos filas al panel principal.
        mainPanel.add(fila1);
        mainPanel.add(fila2);

        add(mainPanel, BorderLayout.CENTER);
        

        // Listeners de los botones.
        btnAlta.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.ALTA_CLIENTE);
            ((JFrame) vista).setVisible(true);
        });

        btnBaja.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BAJA_CLIENTE);
            ((JFrame) vista).setVisible(true);
        });

        btnModificar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MODIFICAR_CLIENTE);
            ((JFrame) vista).setVisible(true);
        });

        btnBuscar.addActionListener(e -> {
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.BUSCAR_CLIENTE);
            ((JFrame) vista).setVisible(true);
        });

        btnListar.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_CLIENTES);
            ((JFrame) vista).setVisible(true);
        });
        
        btnMostrarMejor.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_MEJOR_CLIENTE);
            ((JFrame) vista).setVisible(true);
        });
        
        btnMostrarFacturas.addActionListener(e -> { 
            IGUI vista = FactoriaAbstractaPresentacion.getInstance().createVista(Eventos.MOSTRAR_FACTURAS_CLIENTE_POR_ID);
            ((JFrame) vista).setVisible(true);
        });
    }
}
