package presentacion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class GUIMain extends JFrame {

	// CONSTANTES
	
	private final static int HORIZONTAL = 500;	
	private final static int VERTICAL = 250;
	
	// CONSTRUCTORA
	
	public GUIMain() {
		super("[NIKEA - IS2]");  
		initGUI(); 
	}
	
	
	// MÉTODO INITGUI
	
	private void initGUI() {
		setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 3, 15, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 6 botones
        JButton btnEmpleados  = new JButton("Empleados");
        JButton btnFacturas   = new JButton("Facturas");
        JButton btnClientes   = new JButton("Clientes");
        JButton btnServicios  = new JButton("Servicios");
        JButton btnMarcas     = new JButton("Marcas");
        JButton btnDescuentos = new JButton("Descuentos");

        JButton[] botones = {btnEmpleados, btnFacturas, btnClientes,
                             btnServicios, btnMarcas, btnDescuentos};

        for (JButton b : botones) {
            b.setBackground(new Color(20, 20, 128));
            b.setForeground(Color.YELLOW);
            b.setFocusPainted(false);
        }

        // Logo / imagen central
        JLabel lblLogo = new JLabel("NIKEA", SwingConstants.CENTER);

        // Listeners: abrir JDialog de cada subsistema
        btnClientes.addActionListener(e -> new GUIClienteDialog(this).setVisible(true));
        btnEmpleados.addActionListener(e -> new GUIEmpleadoDialog(this).setVisible(true));
        btnFacturas.addActionListener(e -> new GUIFacturaDialog(this).setVisible(true));
        btnServicios.addActionListener(e -> new GUIServicioDialog(this).setVisible(true));
        btnMarcas.addActionListener(e -> new GUIMarcaDialog(this).setVisible(true));
        btnDescuentos.addActionListener(e -> new GUIDescuentoDialog(this).setVisible(true));

        // Rellenar cuadrícula 3x3 (botones alrededor del centro)
        panel.add(new JLabel());
        panel.add(btnEmpleados);
        panel.add(btnFacturas);

        panel.add(btnClientes);
        panel.add(lblLogo);
        panel.add(btnServicios);

        panel.add(btnDescuentos);
        panel.add(btnMarcas);
        panel.add(new JLabel());

        add(panel);
	}
}
