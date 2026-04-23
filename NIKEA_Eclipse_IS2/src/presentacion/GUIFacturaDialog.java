package presentacion;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GUIFacturaDialog  extends JDialog  {

	//CONSTRUCTORA
	
	public GUIFacturaDialog(JFrame owner) {
        super(owner, "Gestión de Factura [SIN HACER]", false);
        setResizable(false);
    	setSize(700, 150);
        setLocationRelativeTo(owner);
        
        initGUI();
	}
	
	// MÉTODOS
    
    private void initGUI() {
    	JPanel panel = new JPanel(new GridLayout(1, 6, 10, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JButton btnIniciar           = new JButton("Iniciar venta");
        JButton btnAnnadir           = new JButton("Annadir servicio");
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
    }
}
